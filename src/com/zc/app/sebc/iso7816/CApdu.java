package com.zc.app.sebc.iso7816;

import com.zc.app.sebc.util.BasicUtil;

public class CApdu {

	public static final byte[] ERR_APDU_NULL = {(byte)0xFC, (byte)0x01};
	public static final byte[] ERR_APDU_HEAD_LENGTH = {(byte)0xFC, (byte)0x02};
	public static final byte[] ERR_APDU_DATA_LENGTH = {(byte)0xFC, (byte)0x03};
	public static final byte[] ERR_APDU_LE_LENGTH = {(byte)0xFC, (byte)0x04};
	
	public byte[] apdu = null;
	
	public CApdu() {
		apdu = null;
	}
	
	public CApdu(byte[] apdu) {
		if(apdu != null) {
			int len = apdu.length;
			if(len > 3) {
				this.apdu = apdu;
			}
		}
	}
	
	public CApdu(String strApdu) {
		if(strApdu != null) {
			int len = strApdu.length();
			if(len > 7) {
				if(len%2 == 0) {
					byte[] bytApdu = BasicUtil.byteAsciiToByteBcd(strApdu.getBytes(), false, false);
					if(bytApdu != null) {
						if(bytApdu.length > 3) {
							apdu = bytApdu;
						}
					}
				}
			}
		}
	}
	
	public boolean isCApduOk() {
		if(apdu != null) {
			if(apdu.length > 3) {
				return true;
			}
		}
		return false;
	}
	
	public String getHexString() {
		return BasicUtil.bytesToHexString(apdu, 0, apdu.length);
	}
}
