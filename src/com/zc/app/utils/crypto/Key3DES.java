package com.zc.app.utils.crypto;

import com.zc.app.utils.crypto.util.CryptoErrorDef;
import com.zc.app.utils.crypto.util.CryptoUtil;

public class Key3DES {
	private byte[] m_keyvalue;
	private int OK = 0x00000000;
	/**
	 * 获取密钥值
	 */
	public byte[] GetKeyValue() {
		return CryptoUtil.SubBytes(m_keyvalue, 0, 16);
	}
	/**
	 * 设置密钥值
	 */
	public int SetKeyValue(String szKeyValue) {
		if (szKeyValue.length() != 32)
			return CryptoErrorDef.ERR_KEY_VALUE_LENGTH;
		
		szKeyValue = szKeyValue.concat(szKeyValue.substring(0, 16));
		m_keyvalue = CryptoUtil.hex2byte(szKeyValue);
		
		return OK;
	}
	
	/**
	 * 设置密钥值
	 */
	public int SetKeyValue(byte[] byteKeyValue) {
		if (byteKeyValue.length != 16)
			return CryptoErrorDef.ERR_KEY_VALUE_LENGTH;
		
		return SetKeyValue(CryptoUtil.byte2hex(byteKeyValue));
	}
	
	/**
	 * ECB 加密
	 * @throws Exception 
	 */
	public byte[] ECBEncrypt(byte[] data) throws Exception {
		if (0 == data.length)
			return new byte[0];
		
		if (data.length%8 != 0)
			throw new IllegalArgumentException("数据长度不是8的整数倍");
		if (m_keyvalue.length != 24)
			throw new IllegalArgumentException("密钥值无效");
		
		return Algorithm.ECBencrypt3DES(data, m_keyvalue);
		
	}

	/**
	 * ECB 解密
	 * @throws Exception 
	 */
	public byte[] ECBDecrypt(byte[] data) throws Exception {
		if (0 == data.length)
			return new byte[0];
		if (data.length%8 != 0)
			throw new IllegalArgumentException("数据长度不是8的整数倍");
		if (m_keyvalue.length != 24)
			throw new IllegalArgumentException("密钥值无效");
		
		return Algorithm.ECBdecrypt3DES(data, m_keyvalue);
	}
	
	/**
	 * CBC 加密
	 * @throws Exception 
	 */
	public byte[] CBCEncrypt(byte[] data) throws Exception {
		String szIV = "0000000000000000";
		return CBCEncrypt(data, szIV);
	}
	public byte[] CBCEncrypt(byte[] data, String szIV) throws Exception {
		if (0 == data.length)
			return new byte[0];
		if (data.length%8 != 0)
			throw new IllegalArgumentException("数据长度不是8的整数倍");
		if (m_keyvalue.length != 24)
			throw new IllegalArgumentException("密钥值无效");
		if (szIV.length() != 16)
			throw new IllegalArgumentException("初始向量长度错误");
		
		byte[] iv = CryptoUtil.hex2byte(szIV);
		
		return Algorithm.CBCencrypt3DES(data, m_keyvalue, iv);
	}
	
	/**
	 * CBC 解密
	 * @throws Exception 
	 */
	public byte[] CBCDecrypt(byte[] data, String szIV) throws Exception {
		if (0 == data.length)
			return new byte[0];
		if (data.length%8 != 0)
			throw new IllegalArgumentException("数据长度不是8的整数倍");
		if (m_keyvalue.length != 24)
			throw new IllegalArgumentException("密钥值无效");
		if (szIV.length() != 16)
			throw new IllegalArgumentException("初始向量长度错误");
		
		byte[] iv = CryptoUtil.hex2byte(szIV);
		
		return Algorithm.CBCdecrypt3DES(data, m_keyvalue, iv);
	}
	
	/**
	 * 根据PBOC规范分散密钥
	 * @throws Exception 
	 */
	public Key3DES PBOCDerive(byte[] divData) throws Exception {
		if (8 != divData.length)
			throw new IllegalArgumentException("分散数据长度错误");
		if (24 != m_keyvalue.length)
			throw new IllegalArgumentException("密钥值无效");
		
		byte[] div = new byte[16];
		for (int i=0; i<8; i++) {
			div[i] = divData[i];
			byte byteFF = (byte) 0xFF;
			div[i+8] = (byte) (divData[i]^byteFF);
		}
		
		Key3DES keyDerived = new Key3DES();
		byte[] keyValue = Algorithm.ECBencrypt3DES(div, m_keyvalue);
		keyDerived.SetKeyValue(keyValue);
		
		return keyDerived;
	}
	
	/**
	 * 按照PBOC规范计算MAC
	 * @throws Exception 
	 */
	public byte[] PBOCMac(byte[] data) throws Exception {
		String iv = "0000000000000000";
		return PBOCMac(data, iv);
	}
	public byte[] PBOCMac(byte[] data, String iv) throws Exception {
		if (data.length == 0)
			throw new IllegalArgumentException("数据长度为0");
		if (iv.length() != 16)
			throw new IllegalArgumentException("初始向量长度错误");
		if (24 != m_keyvalue.length)
			throw new IllegalArgumentException("密钥值无效");
		
		byte[] byteIV = CryptoUtil.hex2byte(iv);
		byte[] byteData = CryptoUtil.Padding(data, CryptoUtil.ISO_PADDING_2);
		byte[] byteTemp = Algorithm.CBCencryptDES(byteData, CryptoUtil.SubBytes(m_keyvalue, 0, 8), byteIV);
		byteTemp = CryptoUtil.SubBytes(byteTemp, byteTemp.length-8, byteTemp.length);
		byteTemp = Algorithm.ECBdecryptDES(byteTemp, CryptoUtil.SubBytes(m_keyvalue, 8, 16));
		byteTemp = Algorithm.ECBencryptDES(byteTemp, CryptoUtil.SubBytes(m_keyvalue, 16, 24));
		
		return byteTemp;
	}
	
	/**
	 * 从虚拟加密机获取密钥
	 */
	public int GetKeyFromHSM(String szKeyIndex) {
		if (szKeyIndex.compareTo("0001") == 0)
		{
			SetKeyValue("00000000000000000000000000000000");
		}
		else if (szKeyIndex.compareTo("0101") == 0)
		{
			SetKeyValue("10101010101010101313131313131313");
		}
		else if (szKeyIndex.compareTo("0301") == 0)
		{
			SetKeyValue("20202020202020202323232323223323");
		}
		else if (szKeyIndex.compareTo("0401") == 0)
		{
			SetKeyValue("40404040404040404343434343434343");
		}
		else if (szKeyIndex.compareTo("1001") == 0)
		{
			SetKeyValue("31313131313131312323232323232323");
		}
		else
			throw new IllegalArgumentException("密钥找不到");
		return CryptoErrorDef.OK;
	}
	
	/**
	 * 生成随机数
	 */
	public byte[] GenerateRandom(int iLen) {
		String szRandom = "";
		if (iLen == 8)
			szRandom = "0D70002D8E5FE379";
		
		return CryptoUtil.hex2byte(szRandom);
	}
	
	/**
	 * Java卡外部认证
	 * @throws Exception 
	 */
	public String JCOPSecurityChannel(byte[] response, String szCmdHead, byte[] random) throws Exception {
		String szAPDUData = "";
		if (8 != random.length)
			throw new IllegalArgumentException("数据长度不是8的整数倍");
		if (10 != szCmdHead.length())
			throw new IllegalArgumentException("密钥头长度错误");
		if (response.length < 20)
			throw new IllegalArgumentException("数据长度错误");
		
		Key3DES keyKMC = new Key3DES();
		keyKMC.SetKeyValue("404142434445464748494A4B4C4D4E4F");
		
		String szDiv = "";
		byte[] byteTemp = null;
		szDiv = "0182"+CryptoUtil.byte2hex(CryptoUtil.SubBytes(response, 12, 14))+"000000000000000000000000";
		byteTemp = keyKMC.CBCEncrypt(CryptoUtil.hex2byte(szDiv));
		
		Key3DES keySessionEnc = new Key3DES();
		keySessionEnc.SetKeyValue(byteTemp);
		
		szDiv = "0101"+CryptoUtil.byte2hex(CryptoUtil.SubBytes(response, 12, 14))+"000000000000000000000000";
		byteTemp = keyKMC.CBCEncrypt(CryptoUtil.hex2byte(szDiv));
		
		Key3DES keySessionMac = new Key3DES();
		keySessionMac.SetKeyValue(byteTemp);
		
		szDiv = CryptoUtil.byte2hex(CryptoUtil.SubBytes(response, 12, 20))+CryptoUtil.byte2hex(random)+"8000000000000000";
		byteTemp = keySessionEnc.CBCEncrypt(CryptoUtil.hex2byte(szDiv));
		
		String szData = CryptoUtil.byte2hex(CryptoUtil.SubBytes(byteTemp, byteTemp.length-8, byteTemp.length));
		
		szDiv = szCmdHead+szData;
		byteTemp = keySessionMac.PBOCMac(CryptoUtil.hex2byte(szDiv));
		
		szData += CryptoUtil.byte2hex(byteTemp);
		szAPDUData = szData;
		
		return szAPDUData;
	}
}