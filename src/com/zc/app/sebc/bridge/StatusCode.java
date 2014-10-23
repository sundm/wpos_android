package com.zc.app.sebc.bridge;

import com.zc.app.sebc.pboc2.Sw1Sw2;

public class StatusCode {

	public final static String SW1SW2_63C = "63C";
	
	//status words
	public final static String SW1SW2_OK = "9000";		//OK_CARD = "";
	public final static String SW1SW2_OK1 = "9001";		//OK_CARD1 = "未读取全部IC卡交易日志";
	public final static String SW1SW2_F9FF = "F9FF";	//ERR_UNKNOWN = "未知错误";
	
	//status words of card
	public final static String SW1SW2_6283 = "6283";	//ERR_CARD_APP_LOCK = "IC卡应用已锁";
	public final static String SW1SW2_6300 = "6300";	//ERR_EXTERNAL_AUTHENTICATE_FAIL = "联机交易外部认证失败";
	public final static String SW1SW2_63CX = "63CX";	//ERR_CARD_VERIFY_PIN = "PIN验证失败";
	public final static String SW1SW2_6983 = "6983";	//ERR_CARD_PIN_LOCKED = "IC卡PIN已锁";
	public final static String SW1SW2_6988 = "6988";	//ERR_CARD_MAC = "MAC错误";
	public final static String SW1SW2_6A81 = "6A81";	//ERR_CARD_LOCK = "IC卡已锁";
	public final static String SW1SW2_6A82 = "6A82";	//ERR_CARD_NO_APP = "IC卡无借贷记应用";
	public final static String SW1SW2_6D00 = "6D00";	//ERR_CARD_APDU_INS = "IC卡无应用入口";
	public final static String SW1SW2_6F00 = "6F00";	//ERR_CARD_UNKNOWN = "IC卡其他错误";
	public final static String SW1SW2_9403 = "9403";	//ERR_CARD_KEY_INDEX = "密钥索引不支持";
	
	//status words for card operation, but not included in card status words
	public final static String SW1SW2_F801 = "F801";	//ERR_CARD_APP_DATA = "IC卡数据错误";
	public final static String SW1SW2_F802 = "F802";	//ERR_CARD_APP_CALCULATE = "IC卡计算错误";
	public final static String SW1SW2_F805 = "F805";	//ERR_CARD_APP_OPERATION_NOT_SUPPORT = "IC卡不支持该操作";
	
	//status words for ecc
	public final static String SW1SW2_FA01 = "FA01";	//ERR_EC_BALANCE_LIMIT_EXCEED = "电子现金余额上限超限";
	public final static String SW1SW2_FA05 = "FA05";	//ERR_EC_LOAD_FAIL = "圈存失败";
	
	//status words for jar package operation
	public final static String SW1SW2_FC61 = "FC61";	//ERR_ANDROID_APP_CALCULATE = "运行时错误";
	public final static String SW1SW2_FC62 = "FC62";	//ERR_ANDROID_APP_DATA_MANIPULATED = "金额被篡改";
	public final static String SW1SW2_FC63 = "FC63";	//ERR_ANDROID_SESSION_ENDED = "会话已中断";
	public final static String SW1SW2_FC64 = "FC64";	//ERR_ANDROID_SESSION_TIMEOUT = "会话超时";
	
	//status words for android
	//android ui
	public final static String SW1SW2_FD01 = "FD01";	//ERR_ANDROID_AMOUNT = "金额非法";
	public final static String SW1SW2_FD0A = "FD0A";	//ERR_ANDROID_PIN_FORMAT = "PIN格式错误";
	public final static String SW1SW2_FD0C = "FD0C";	//ERR_ANDROID_CARD_APP = "卡片应用名错误";
	public final static String SW1SW2_FD0E = "FD0E";	//ERR_ANDROID_TERMINAL_NUMBER = "终端编号非法";
	public final static String SW1SW2_FD15 = "FD15";	//ERR_ANDROID_KEY_INDEX = "密钥索引非法";
	
	//status words for server
	public final static String SW1SW2_FE01 = "FE01";	//ERR_SERVER_LOAD_DATA = "圈存下行数据错误";
	public final static String SW1SW2_FE21 = "FE21";	//ERR_SERVER_DATE = "服务器日期错误";
	public final static String SW1SW2_FE22 = "FE22";	//ERR_SERVER_TIME = "服务器时间错误";
	
	//status words for media, including nfc
	public final static String SW1SW2_FF02 = "FF02";	//ERR_NFC_NOT_SUPPORT = "手机不支持NFC";	
	public final static String SW1SW2_FF03 = "FF03";	//ERR_NFC_NOT_OPEN = "未开启NFC";	
	public final static String SW1SW2_FF05 = "FF05";	//ERR_NFC_GET_TAG = "无效的NFC标签";
	public final static String SW1SW2_FF06 = "FF06";	//ERR_NFC_UNKNOWN = "NFC未知错误";
	public final static String SW1SW2_FF07 = "FF07";	//ERR_NFC_NO_CARD = "未检测到卡片";
	
	//readable
	public final static String OK_CARD = "";
	public final static String OK_CARD1 = "未读取全部IC卡交易日志";
	public final static String ERR_UNKNOWN = "未知错误";
	
	public final static String ERR_CARD_APP_LOCK = "IC卡应用已锁";
	public final static String ERR_EXTERNAL_AUTHENTICATE_FAIL = "联机交易外部认证失败";
	public final static String ERR_CARD_VERIFY_PIN = "PIN验证失败";
	public final static String ERR_CARD_PIN_LOCKED = "IC卡PIN已锁";
	public final static String ERR_CARD_MAC = "MAC错误";
	public final static String ERR_CARD_LOCK = "IC卡已锁";
	public final static String ERR_CARD_NO_APP = "IC卡无借贷记应用";
	public final static String ERR_CARD_APDU_INS = "IC卡无应用入口";
	public final static String ERR_CARD_UNKNOWN = "IC卡其他错误";
	public final static String ERR_CARD_KEY_INDEX = "密钥索引不支持";
	
	public final static String ERR_CARD_APP_DATA = "IC卡数据错误";
	public final static String ERR_CARD_APP_CALCULATE = "IC卡计算错误";
	public final static String ERR_CARD_APP_OPERATION_NOT_SUPPORT = "IC卡不支持该操作";
	
	public final static String ERR_EC_BALANCE_LIMIT_EXCEED = "电子现金余额上限超限";
	public final static String ERR_EC_LOAD_FAIL = "圈存失败";
	
	public final static String ERR_ANDROID_APP_CALCULATE = "程序计算错误";
	public final static String ERR_ANDROID_APP_DATA_MANIPULATED = "金额被篡改";
	public final static String ERR_ANDROID_SESSION_ENDED = "会话已中断";
	public final static String ERR_ANDROID_SESSION_TIMEOUT = "会话超时";
	
	public final static String ERR_ANDROID_AMOUNT = "金额非法";
	public final static String ERR_ANDROID_PIN_FORMAT = "PIN格式错误";
	public final static String ERR_ANDROID_CARD_APP = "卡片应用名错误";
	public final static String ERR_ANDROID_TERMINAL_NUMBER = "终端编号非法";
	public final static String ERR_ANDROID_KEY_INDEX = "密钥索引非法";
	
	public final static String ERR_SERVER_LOAD_DATA = "圈存下行数据错误";
	public final static String ERR_SERVER_DATE = "服务器日期错误";
	public final static String ERR_SERVER_TIME = "服务器时间错误";
	
	public final static String ERR_NFC_NOT_SUPPORT = "手机不支持NFC";	
	public final static String ERR_NFC_NOT_OPEN = "未开启NFC";	
	public final static String ERR_NFC_GET_TAG = "无效的NFC标签";
	public final static String ERR_NFC_UNKNOWN = "NFC未知错误";
	public final static String ERR_NFC_NO_CARD = "未检测到卡片";
	
	public static String toOuterStatusCode(String sw1sw2) {
		
		if(sw1sw2 == null) {
			return SW1SW2_F9FF;
		}
		
		if(sw1sw2.length() < 4) {
			return SW1SW2_F9FF;
		}
		
		sw1sw2 = sw1sw2.substring(0, 4);
		
		if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK)) {
			return SW1SW2_OK;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK1)) {
			return SW1SW2_OK1;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6283)) {
			return SW1SW2_6283;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6300)) {
			return SW1SW2_6300;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_63CX)) {
			return SW1SW2_63CX;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6700)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6983)) {
			return SW1SW2_6983;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6988)) {
			return SW1SW2_6988;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A81)) {
			return SW1SW2_6A81;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A82)) {
			return SW1SW2_6A82;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A83)) {
			return SW1SW2_F801;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A88)) {
			return SW1SW2_F805;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6D00)) {
			return SW1SW2_6D00;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6F00)) {
			return SW1SW2_6F00;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_9403)) {
			return SW1SW2_9403;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F801)) {
			return SW1SW2_F801;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F802)) {
			return SW1SW2_F802;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F803)) {
			return SW1SW2_F801;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F804)) {
			return SW1SW2_F801;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F805)) {
			return SW1SW2_F805;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F806)) {
			return SW1SW2_F805;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F807)) {
			return SW1SW2_F805;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F808)) {
			return SW1SW2_F801;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F809)) {
			return SW1SW2_F801;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA01)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA02)) {
			return SW1SW2_FA01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA03)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA04)) {
			return SW1SW2_FA01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA05)) {
			return SW1SW2_FA05;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC00)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC01)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC02)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC03)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC04)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC60)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC61)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC62)) {
			return SW1SW2_FC62;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC63)) {
			return SW1SW2_FC63;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC64)) {
			return SW1SW2_FC64;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD01)) {
			return SW1SW2_FD01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD02)) {
			return SW1SW2_FD01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD03)) {
			return SW1SW2_FD01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD04)) {
			return SW1SW2_FD01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD05)) {
			return SW1SW2_FD01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD06)) {
			return SW1SW2_FD01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD07)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD08)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD09)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0A)) {
			return SW1SW2_FD0A;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0B)) {
			return SW1SW2_FD0A;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0C)) {
			return SW1SW2_FD0C;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0D)) {
			return SW1SW2_FD0C;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0E)) {
			return SW1SW2_FD0E;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0F)) {
			return SW1SW2_FD0E;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD10)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD11)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD12)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD13)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD14)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD15)) {
			return SW1SW2_FD15;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD16)) {
			return SW1SW2_FD15;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD17)) {
			return SW1SW2_FD15;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD18)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD19)) {
			return SW1SW2_F9FF;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD81)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD91)) {
			return SW1SW2_FC61;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE01)) {
			return SW1SW2_FE01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE21)) {
			return SW1SW2_FE21;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE22)) {
			return SW1SW2_FE22;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE23)) {
			return SW1SW2_FE01;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE24)) {
			return SW1SW2_FE01;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF00)) {
			return SW1SW2_FC61;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF01)) {
			return SW1SW2_FF05;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF02)) {
			return SW1SW2_FF02;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF03)) {
			return SW1SW2_FF03;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF04)) {
			return SW1SW2_FF05;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF05)) {
			return SW1SW2_FF05;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF06)) {
			return SW1SW2_FF06;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF07)) {
			return SW1SW2_FF07;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF08)) {
			return SW1SW2_FF07;
		}
		
		  else {
			return SW1SW2_F9FF;
		}
	}
	
	//output human readable result according to status word, typically sw1 and sw2
	public static String toReadableSw1Sw2(String sw1sw2) {
		
		if(sw1sw2 == null) {
			return ERR_UNKNOWN;
		}
		
		if(sw1sw2.length() < 4) {
			return ERR_UNKNOWN;
		}
		
		sw1sw2 = sw1sw2.substring(0, 4);
		
		if(sw1sw2.equals(SW1SW2_OK)) {
			return OK_CARD;
		} else if(sw1sw2.equals(SW1SW2_OK1)) {
			return OK_CARD1;
		} else if(sw1sw2.equals(SW1SW2_6283)) {
			return ERR_CARD_APP_LOCK;
		} else if(sw1sw2.equals(SW1SW2_6300)) {
			return ERR_EXTERNAL_AUTHENTICATE_FAIL;
		} else if(sw1sw2.equals(SW1SW2_63CX)) {
			return ERR_CARD_VERIFY_PIN;
		} else if(sw1sw2.equals(SW1SW2_6983)) {
			return ERR_CARD_PIN_LOCKED;
		} else if(sw1sw2.equals(SW1SW2_6988)) {
			return ERR_CARD_MAC;
		} else if(sw1sw2.equals(SW1SW2_6A81)) {
			return ERR_CARD_LOCK;
		} else if(sw1sw2.equals(SW1SW2_6A82)) {
			return ERR_CARD_NO_APP;
		} else if(sw1sw2.equals(SW1SW2_6D00)) {
			return ERR_CARD_APDU_INS;
		} else if(sw1sw2.equals(SW1SW2_6F00)) {
			return ERR_CARD_UNKNOWN;
		} else if(sw1sw2.equals(SW1SW2_9403)) {
			return ERR_CARD_KEY_INDEX;
		}
		
		  else if(sw1sw2.equals(SW1SW2_F801)) {
			return ERR_CARD_APP_DATA;
		} else if(sw1sw2.equals(SW1SW2_F802)) {
			return ERR_CARD_APP_CALCULATE;
		} else if(sw1sw2.equals(SW1SW2_F805)) {
			return ERR_CARD_APP_OPERATION_NOT_SUPPORT;
		}
		
		  else if(sw1sw2.equals(SW1SW2_FA01)) {
			return ERR_EC_BALANCE_LIMIT_EXCEED;
		} else if(sw1sw2.equals(SW1SW2_FA05)) {
			return ERR_EC_LOAD_FAIL;
		}
		
		  else if(sw1sw2.equals(SW1SW2_FC61)) {
			return ERR_ANDROID_APP_CALCULATE;
		} else if(sw1sw2.equals(SW1SW2_FC62)) {
			return ERR_ANDROID_APP_DATA_MANIPULATED;
		} else if(sw1sw2.equals(SW1SW2_FC63)) {
			return ERR_ANDROID_SESSION_ENDED;
		} else if(sw1sw2.equals(SW1SW2_FC64)) {
			return ERR_ANDROID_SESSION_TIMEOUT;
		}
		
		  else if(sw1sw2.equals(SW1SW2_FD01)) {
			return ERR_ANDROID_AMOUNT;
		} else if(sw1sw2.equals(SW1SW2_FD0A)) {
			return ERR_ANDROID_PIN_FORMAT;
		} else if(sw1sw2.equals(SW1SW2_FD0C)) {
			return ERR_ANDROID_CARD_APP;
		} else if(sw1sw2.equals(SW1SW2_FD0E)) {
			return ERR_ANDROID_TERMINAL_NUMBER;
		} else if(sw1sw2.equals(SW1SW2_FD15)) {
			return ERR_ANDROID_KEY_INDEX;
		}
		
		  else if(sw1sw2.equals(SW1SW2_FE01)) {
			return ERR_SERVER_LOAD_DATA;
		} else if(sw1sw2.equals(SW1SW2_FE21)) {
			return ERR_SERVER_DATE;
		} else if(sw1sw2.equals(SW1SW2_FE22)) {
			return ERR_SERVER_TIME;
		}
		
		  else if(sw1sw2.equals(SW1SW2_FF02)) {
			return ERR_NFC_NOT_SUPPORT;
		} else if(sw1sw2.equals(SW1SW2_FF03)) {
			return ERR_NFC_NOT_OPEN;
		} else if(sw1sw2.equals(SW1SW2_FF05)) {
			return ERR_NFC_GET_TAG;
		} else if(sw1sw2.equals(SW1SW2_FF06)) {
			return ERR_NFC_UNKNOWN;
		} else if(sw1sw2.equals(SW1SW2_FF07)) {
			return ERR_NFC_NO_CARD;
		}
		
		  else {
			return ERR_UNKNOWN;
		}
	}
}
