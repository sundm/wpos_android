package com.zc.app.sebc.lx;

import com.zc.app.sebc.util.BasicUtil;

//see Longxing card specification

public final class TransType {

	public final static int TYPE_01 = 1;
	public final static int TYPE_02 = 2;
	public final static int TYPE_03 = 3;
	public final static int TYPE_04 = 4;
	public final static int TYPE_05 = 5;
	public final static int TYPE_06 = 6;
	public final static int TYPE_07 = 7;
	public final static int TYPE_08 = 8;
	
	public final static String READ_TYPE_01 = "ED圈存";
	public final static String READ_TYPE_02 = "EP圈存";
	public final static String READ_TYPE_03 = "圈提";
	public final static String READ_TYPE_04 = "ED取款";
	public final static String READ_TYPE_05 = "ED消费";
	public final static String READ_TYPE_06 = "EP消费";
	public final static String READ_TYPE_07 = "ED修改透支限额";
	public final static String READ_TYPE_08 = "信用消费";
	
	public final static String READ_TYPE_UNKNOWN = "未知";
	
	public static String toReadableTransactionType(String type) {
		
		if(BasicUtil.isEmptyString(type)) {
			return null;
		}
		
		if(type.length() > 2) {
			return READ_TYPE_UNKNOWN;
		}
		
		int typeId = Integer.parseInt(type);
		
		switch(typeId) {
		case TransType.TYPE_01:
			return TransType.READ_TYPE_01;
		case TransType.TYPE_02:
			return TransType.READ_TYPE_02;
		case TransType.TYPE_03:
			return TransType.READ_TYPE_03;
		case TransType.TYPE_04:
			return TransType.READ_TYPE_04;
		case TransType.TYPE_05:
			return TransType.READ_TYPE_05;
		case TransType.TYPE_06:
			return TransType.READ_TYPE_06;
		case TransType.TYPE_07:
			return TransType.READ_TYPE_07;
		case TransType.TYPE_08:
			return TransType.READ_TYPE_08;
			
		default:
			return TransType.READ_TYPE_UNKNOWN;
		}
	}
}
