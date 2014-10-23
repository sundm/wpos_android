package com.zc.app.utils.crypto.util;

import java.util.Arrays;

public class CryptoUtil {
	public static final int ISO_PADDING_1 = 0;
	public static final int ISO_PADDING_2 = 1;
	
	public static final Boolean isDebug = true;
	
	public static void echo (String str) {
		if (isDebug) {
			System.out.println(str);
		}
	}
	
	public static byte[] Padding(byte[] data, int iMode) throws Exception {
		byte[] byteRet = null;
		String szData = CryptoUtil.byte2hex(data);
		switch(iMode) {
		case ISO_PADDING_1:
			{
				while(szData.length()%16 != 0)
				{
					szData += "00";
				}
				byteRet = CryptoUtil.hex2byte(szData);
			}
			break;
		case ISO_PADDING_2:
			{
				szData += "80";
				while(szData.length()%16 != 0)
				{
					szData += "00";
				}
				byteRet = CryptoUtil.hex2byte(szData);
			}
			break;
		default:
			throw new IllegalArgumentException("Padding模式不支持");
		}
		
		return byteRet;
	}
	
	/**
	 * 截取byte数组中某一段
	 * @param byteSrc
	 * @param iStart
	 * @param iEnd
	 * @return
	 */
	public static byte[] SubBytes(byte[] byteSrc, int iStart, int iEnd) {
//		if (byteSrc.length <= 0)
//			return new byte[0];
//		String szSrc = byte2hex(byteSrc);
//		szSrc = szSrc.substring(iStart*2, iEnd*2);
//		
//		byte[] byteRet = hex2byte(szSrc);
//		return byteRet;
		byte[] byteRet = new byte[iEnd-iStart];
		for (int iPos=iStart; iPos<iEnd; iPos+=1)
		{
			byteRet[iPos-iStart] = byteSrc[iPos];
		}
		return byteRet;
	}
	// 二进制转字符串   
	public static String byte2hex(byte[] b, int len) 
	{
		StringBuffer sb = new StringBuffer();   
		String tmp = "";   
		for (int i = 0; i < b.length; i++)
		{
			tmp = Integer.toHexString(b[i] & 0XFF);
			if (tmp.length() == 1)
			{
				sb.append("0" + tmp);
			}else
			{
				sb.append(tmp);
			}
		}
		String str = sb.toString();
		str = str.toUpperCase();
		return str; 	
	}
	public static String byte2hex(byte[] b)    
	{   
	   return byte2hex(b, b.length);
	} 
	
	// 字符串转二进制   
	public static byte[] hex2byte(String str)
	{    
		if (str == null)
		{
			return null;   
		}  
		int len = str.length();   
	     
		if (len == 0)
		{   
			return null;   
		}
		if (len % 2 == 1)
		{
			throw new IllegalArgumentException("长度不是偶数");
		}
	     
		byte[] b = new byte[len / 2];
		for (int i = 0; i < str.length(); i += 2) {
			b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
		}
		return b;
	} 
	
	// 数组异或
	static public void arrayXOR(byte[] des, int desoffset, byte[] src, 
			int srcoffset, int len)
	{
	    int i;
	    for(i=0; i<len; i++)
	    {
	      des[desoffset+i] = (byte)(des[desoffset+i] ^ src[srcoffset+i]);
	    }
	}
	
	// 将数组转成整数
	static public int Bytes2Int(byte[] bArray)
	{
		return Bytes2Int(bArray, 16);
	}
	static public int Bytes2Int(byte[] bArray, int base)
	{
		return Bytes2Int(bArray, bArray.length, base);
	}
	static public int Bytes2Int(byte[] bArray, int iLen, int base)
	{
		String szBalance = byte2hex(bArray, iLen);
		int sum = Integer.valueOf(szBalance, base);
		return sum;
	}
	
	public static String tagTrace(short sTag, byte[] buffer, int iOffset, int iLen, String charsetName, boolean IsHex)
	{
	    int iMove; // increase one "TLV" length after each loop
	    int iTagValueLen; //length of value
	    short sTempTag; //tag from buffer
	    
	    while(iLen > 0 )
	    {
	      sTempTag = sGetTag(buffer, iOffset);
	      iMove = sGetTagLen(sTempTag);//smove for soffset
	      iOffset += iMove;
	      
	      //added by DingJi 070727
	      if (sTempTag == (short)0x70)
	      {
	    	  if (buffer[iOffset]==(byte)0x81)
	    	  {
	    		  iLen=(short)(iLen - iMove - 2);
	    		  iOffset+=(short)2;
	    	  }
	    	  else
	    	  {
		    	  iLen = (short)(iLen - iMove - 1);
		    	  iOffset++;
	    	  }
	    	  continue;
	      }
	      
	      //get tag's value length
	      byte byTemp =  buffer[iOffset];
    	  if (byTemp == (byte)0x81)
    	  {
    		  iTagValueLen = (short)(buffer[++iOffset] & 0x00ff);
    		  iOffset++;
    	  }
    	  else
    	  {
    		  iTagValueLen = (short)(byTemp & 0x00ff);
    		  iOffset++;
    	  }
	      
    	  //check and return
    	  if(sTempTag == sTag)//get tag's value
    	  {
    		  //return toHexString(buffer, iOffset, iTagValueLen);
//    		  String sztemp = new String(buffer, iOffset, iTagValueLen);
//    		  return sztemp;
    		  try
    		  {
    			  String sztemp = null;
    			  if (IsHex)
    			  {
    				  sztemp = String.format("%02X", buffer[iOffset]);
    				  for (int iT=1; iT<iTagValueLen; iT++)
    				  {
    					  sztemp = sztemp.concat(String.format("%02X", buffer[iOffset+iT]));
    					  
    				  }
    				  
    			  }
    			  else
    			  {
    				  sztemp = new String(Arrays.copyOfRange(buffer, iOffset, iOffset+iTagValueLen), charsetName);
    			  }
    			  return sztemp;
    		  }
    		  catch(Exception e)
    		  {
    			  return null;
    		  }
    	  }
    	  
	      //move to next
	      iMove = (short)(iTagValueLen + 1 + iMove);//smove for lc
	      iOffset = (short)(iTagValueLen + iOffset);
	      if(iTagValueLen >= 128 ) //0x80
	      {
	        iMove++;
	        iOffset++;
	      }
	      iLen = (short)(iLen - iMove);
	    }
	    
	    return null;
	}
	
	static short sGetTag(byte[] buffer, int iOffset)
	{
	    short sTag;
	    if((byte)(buffer[iOffset]& (byte)0x1F) == (byte)0x1F)
	    {
	    	sTag = (short)toInt(buffer, iOffset, 2);
	    }
	    else
	    {
	    	sTag = (short)buffer[iOffset];
	    }
	    return sTag;
	}
  
	static short sGetTagLen(short sTag)
	{
	    if ( ((short)(sTag & (short)0xff00))!=(short)0)
	      return (short)2;
	    return (short)1;
	}
	
	static int toInt(byte[] b, int s, int n) {
		int ret = 0;

		final int e = s + n;
		for (int i = s; i < e; ++i) {
			ret <<= 8;
			ret |= b[i] & 0xFF;
		}
		return ret;
	}
}