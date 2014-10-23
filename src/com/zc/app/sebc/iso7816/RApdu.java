package com.zc.app.sebc.iso7816;

import com.zc.app.sebc.util.BasicUtil;

public class RApdu {
	
	public static final byte[] ERR_UNKNOWN = {(byte)0xFC, (byte)0x00};
	
	public byte[] rApdu;
	
	public RApdu(byte[] rApdu) {
		
		if(rApdu == null) {
			rApdu = ERR_UNKNOWN;
		} else if (rApdu.length < 2) {
			rApdu = ERR_UNKNOWN;
		} else {
			this.rApdu = rApdu;
		}
	}
	
	public RApdu(String strRApdu) {
		
		if(strRApdu == null) {
			rApdu = ERR_UNKNOWN;
		} else if (strRApdu.length() < 4) {
			rApdu = ERR_UNKNOWN;
		} else if ((strRApdu.length()%2) != 0) {
			rApdu = ERR_UNKNOWN;
		} else {
			byte[] rApdu = BasicUtil.byteAsciiToByteBcd(strRApdu.getBytes(), false, false);
			if(rApdu != null) {
				this.rApdu = rApdu;
			} else {
				this.rApdu = ERR_UNKNOWN;
			}
		}
	}
	
	public int getBytesLen() {
		return rApdu.length;
	}
	
	public int getHexStringLen() {
		return rApdu.length + rApdu.length;
	}
	
	public int getDataBytesLen() {
		return rApdu.length-2;
	}
	
	public int getDataHexStringLen() {
		return rApdu.length-2 + rApdu.length-2;
	}
	
	public byte[] getBytes() {
		return rApdu;
	}
	
	public String getHexString() {
		return BasicUtil.bytesToHexString(rApdu, 0, rApdu.length);
	}
	
	public byte[] getDataBytes() {
		if(rApdu.length < 3) {
			return null;
		} else {
			byte[] data = new byte[rApdu.length-2];
			BasicUtil.byteStringCopy(rApdu, 0, data, 0, rApdu.length-2);
			return data;
		}
	}
	
	public String getDataHexString() {
		String hexString = getHexString();
		if(hexString.length() > 4) {
			return hexString.substring(0, hexString.length()-4);
		} else {
			return null;
		}
	}
	
	public byte[] getSw1Sw2Bytes() {
		byte[] sw2sw2 = new byte[2];
		sw2sw2[0] = rApdu[rApdu.length-2];
		sw2sw2[1] = rApdu[rApdu.length-1];
		return sw2sw2;
	}
	
	public String getSw1Sw2String() {
		return BasicUtil.bytesToHexString(rApdu, rApdu.length-2, 2);
	}
}
