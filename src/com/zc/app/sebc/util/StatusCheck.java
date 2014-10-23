package com.zc.app.sebc.util;

public class StatusCheck {

	//output human readable result according to status word
	public static String toReadableErrorMessage(String status) {
		
		if(status == null) {
			return ERR_UNKNOWN;
		}
		
		if(status.length() < 4) {
			return ERR_UNKNOWN;
		}
		
		status = status.substring(0, 4);
		
		if(status.equals(SW1SW2_OK)) {
			return OK_CARD;
		} else if(status.equals(SW1SW2_OK1)) {
			return OK_CARD1;
		} else if(status.equals(SW1SW2_6283)) {
			return ERR_CARD_APP_LOCK;
		} else if(status.equals(SW1SW2_6300)) {
			return ERR_EXTERNAL_AUTHENTICATE_FAIL;
		} else if(status.equals(SW1SW2_63CX)) {
			return ERR_CARD_VERIFY_PIN;
		} else if(status.equals(SW1SW2_6983)) {
			return ERR_CARD_PIN_LOCKED;
		} else if(status.equals(SW1SW2_6988)) {
			return ERR_CARD_MAC;
		} else if(status.equals(SW1SW2_6A81)) {
			return ERR_CARD_LOCK;
		} else if(status.equals(SW1SW2_6A82)) {
			return ERR_CARD_NO_APP;
		} else if(status.equals(SW1SW2_6D00)) {
			return ERR_CARD_APDU_INS;
		} else if(status.equals(SW1SW2_6F00)) {
			return ERR_CARD_UNKNOWN;
		} //else if(status.equals(SW1SW2_9403)) {
		//	return ERR_CARD_KEY_INDEX;
		//}
		
		  else if(status.equals(SW1SW2_F801)) {
			return ERR_CARD_APP_DATA;
		} else if(status.equals(SW1SW2_F802)) {
			return ERR_CARD_APP_CALCULATE;
		} else if(status.equals(SW1SW2_F805)) {
			return ERR_CARD_APP_OPERATION_NOT_SUPPORT;
		} else if(status.equals(SW1SW2_F808)) {
			return ERR_CARD_ECC_BALANCE;
		} else if(status.equals(SW1SW2_F809)) {
			return ERR_CARD_ECC_BALANCE_LIMIT;
		}
		
		  else if(status.equals(SW1SW2_FA01)) {
			return ERR_EC_BALANCE_LIMIT_EXCEED;
		} else if(status.equals(SW1SW2_FA05)) {
			return ERR_EC_LOAD_FAIL;
		}
		
		  else if(status.equals(SW1SW2_FC61)) {
			return ERR_ANDROID_APP_CALCULATE;
		} else if(status.equals(SW1SW2_FC62)) {
			return ERR_ANDROID_APP_DATA_MANIPULATED;
		} else if(status.equals(SW1SW2_FC63)) {
			return ERR_ANDROID_SESSION_ENDED;
		} else if(status.equals(SW1SW2_FC64)) {
			return ERR_ANDROID_SESSION_TIMEOUT;
		}
		
		  else if(status.equals(SW1SW2_FD01)) {
			return ERR_ANDROID_AMOUNT;
		} else if(status.equals(SW1SW2_FD07)) {
			return ERR_ANDROID_CHALLENGE;
		} else if(status.equals(SW1SW2_FD12)) {
			return ERR_ANDROID_ACCOUNT;
		}
		
		  else if(status.equals(SW1SW2_FE01)) {
			return ERR_SERVER_LOAD_DATA;
		}
		
		  else if(status.equals(SW1SW2_FF02)) {
			return ERR_NFC_NOT_SUPPORT;
		} else if(status.equals(SW1SW2_FF03)) {
			return ERR_NFC_NOT_OPEN;
		} else if(status.equals(SW1SW2_FF05)) {
			return ERR_NFC_NO_CARD;//ERR_NFC_GET_TAG;
		} else if(status.equals(SW1SW2_FF06)) {
			return ERR_NFC_UNKNOWN;
		} else if(status.equals(SW1SW2_FF07)) {
			return ERR_NFC_NO_CARD;
		}
		
		  else {
			return ERR_UNKNOWN;
		}
	}
    
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
	//public final static String SW1SW2_9403 = "9403";	//ERR_CARD_KEY_INDEX = "密钥索引不支持";
	
	//status words for card operation, but not included in card status words
	public final static String SW1SW2_F801 = "F801";	//ERR_CARD_APP_DATA = "IC卡数据错误";
	public final static String SW1SW2_F802 = "F802";	//ERR_CARD_APP_CALCULATE = "IC卡计算错误";
	public final static String SW1SW2_F805 = "F805";	//ERR_CARD_APP_OPERATION_NOT_SUPPORT = "IC卡不支持该操作";
	public final static String SW1SW2_F808 = "F808";	//ERR_CARD_ECC_BALANCE = "电子现金余额错误";
	public final static String SW1SW2_F809 = "F809";	//ERR_CARD_ECC_BALANCE_LIMIT = "电子现金余额上限错误";
	
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
	public final static String SW1SW2_FD07 = "FD07";	//ERR_ANDROID_CHALLENGE = "挑战码错误";
	public final static String SW1SW2_FD12 = "FD12";	//ERR_ANDROID_ACCOUNT = "转入帐号格式错误";
	
	//status words for server
	public final static String SW1SW2_FE01 = "FE01";	//ERR_SERVER_LOAD_DATA = "圈存下行数据错误";
	
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
	//public final static String ERR_CARD_KEY_INDEX = "密钥索引不支持";
	
	public final static String ERR_CARD_APP_DATA = "IC卡数据错误";
	public final static String ERR_CARD_APP_CALCULATE = "IC卡计算错误";
	public final static String ERR_CARD_APP_OPERATION_NOT_SUPPORT = "IC卡不支持该操作";
	public final static String ERR_CARD_ECC_BALANCE = "电子现金余额错误";
	public final static String ERR_CARD_ECC_BALANCE_LIMIT = "电子现金余额上限错误";
	
	public final static String ERR_EC_BALANCE_LIMIT_EXCEED = "电子现金余额上限超限";
	public final static String ERR_EC_LOAD_FAIL = "圈存失败";
	
	public final static String ERR_ANDROID_APP_CALCULATE = "程序计算错误";
	public final static String ERR_ANDROID_APP_DATA_MANIPULATED = "金额被篡改";
	public final static String ERR_ANDROID_SESSION_ENDED = "会话已中断";
	public final static String ERR_ANDROID_SESSION_TIMEOUT = "会话超时";
	
	public final static String ERR_ANDROID_AMOUNT = "金额非法";
	public final static String ERR_ANDROID_CHALLENGE = "挑战码错误";
	public final static String ERR_ANDROID_ACCOUNT = "转入帐号格式错误";
	
	public final static String ERR_SERVER_LOAD_DATA = "圈存下行数据错误";
	
	public final static String ERR_NFC_NOT_SUPPORT = "手机不支持NFC";	
	public final static String ERR_NFC_NOT_OPEN = "未开启NFC";	
	public final static String ERR_NFC_GET_TAG = "无效的NFC标签";
	public final static String ERR_NFC_UNKNOWN = "NFC未知错误";
	public final static String ERR_NFC_NO_CARD = "未检测到卡片";
}
