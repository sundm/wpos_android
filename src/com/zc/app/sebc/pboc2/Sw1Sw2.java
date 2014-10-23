package com.zc.app.sebc.pboc2;

public class Sw1Sw2 {
	
	public final static String SW1SW2_63C = "63C";
	
	//status words
	public final static String SW1SW2_OK = "9000";		//OK_CARD = "";
	public final static String SW1SW2_OK1 = "9001";		//OK_CARD1 = "未完成全部操作";
	public final static String SW1SW2_F9FF = "F9FF";	//ERR_UNKNOWN = "未知错误";
	
	//status words of card
	public final static String SW1SW2_6283 = "6283";	//ERR_CARD_APP_LOCK = "卡片应用已锁";
	public final static String SW1SW2_6300 = "6300";	//ERR_EXTERNAL_AUTHENTICATE_FAIL = "外部认证失败";
	public final static String SW1SW2_63CX = "63CX";	//ERR_CARD_VERIFY_PIN = "PIN验证失败";
	public final static String SW1SW2_6700 = "6700";	//ERR_CARD_APDU_DATA_LENGTH = "APDU长度错误";
	public final static String SW1SW2_6983 = "6983";	//ERR_CARD_PIN_LOCKED = "卡片PIN已锁";
	public final static String SW1SW2_6988 = "6988";	//ERR_CARD_MAC = "MAC错误";
	public final static String SW1SW2_6A81 = "6A81";	//ERR_CARD_LOCK = "卡片已锁";
	public final static String SW1SW2_6A82 = "6A82";	//ERR_CARD_NO_APP = "卡上无所选应用";
	public final static String SW1SW2_6A83 = "6A83";	//ERR_CARD_NO_SUCH_RECORD = "记录号不存在";
	public final static String SW1SW2_6A88 = "6A88";	//ERR_CARD_PRIVATE_DATA = "专有数据，拒绝访问";
	public final static String SW1SW2_6D00 = "6D00";	//ERR_CARD_APDU_INS = "INS无效";
	public final static String SW1SW2_6F00 = "6F00";	//ERR_CARD_UNKNOWN = "卡片未知错误";
	public final static String SW1SW2_9403 = "9403";	//ERR_CARD_KEY_INDEX = "密钥索引不支持";
	
	//status words for card operation, but not included in card status words
	public final static String SW1SW2_F801 = "F801";	//ERR_CARD_APP_DATA = "卡片数据错误";
	public final static String SW1SW2_F802 = "F802";	//ERR_CARD_APP_CALCULATE = "计算错误";
	public final static String SW1SW2_F803 = "F803";	//ERR_CARD_NO_TRANSACTION_DETAIL_ENTRY = "无明细入口";
	public final static String SW1SW2_F804 = "F804";	//ERR_CARD_TRANSACTION_DETAIL_ENTRY = "明细入口错误";
	public final static String SW1SW2_F805 = "F805";	//ERR_CARD_APP_SDA_NOT_SUPPORT = "卡片不支持SDA";
	public final static String SW1SW2_F806 = "F806";	//ERR_CARD_APP_DDA_NOT_SUPPORT = "卡片不支持DDA";
	public final static String SW1SW2_F807 = "F807";	//ERR_CARD_APP_SDA_DDA_NOT_SUPPORT = "卡片不支持SDA和DDA";
	public final static String SW1SW2_F808 = "F808";	//ERR_CARD_ECC_BALANCE = "电子现金余额错误";
	public final static String SW1SW2_F809 = "F809";	//ERR_CARD_ECC_BALANCE_LIMIT = "电子现金余额上限错误";
	
	//status words for ecc
	public final static String SW1SW2_FA01 = "FA01";	//ERR_EC_SINGLE_TRANSACTION_LIMIT_EXCEED = "电子现金单笔交易限额超限";
	public final static String SW1SW2_FA02 = "FA02";	//ERR_EC_BALANCE_LENGTH_EXCEED = "电子现金余额超限";
	public final static String SW1SW2_FA03 = "FA03";	//ERR_EC_AMOUNT_EXCEPTION = "电子现金余额异常";
	public final static String SW1SW2_FA04 = "FA04";	//ERR_EC_BALANCE_LIMIT_EXCEED = "电子现金余额上限超限";
	public final static String SW1SW2_FA05 = "FA05";	//ERR_EC_LOAD_FAIL = "圈存失败";
	
	//status words for jar package operation
	public final static String SW1SW2_FC00 = "FC00";	//ERR_APDU_RESPONSE_UNKNOWN = "APDU响应未知错误";
	public final static String SW1SW2_FC01 = "FC01";	//ERR_APDU_COMMAND_EMPTY = "APDU命令不能为空";
	public final static String SW1SW2_FC02 = "FC02";	//ERR_APDU_COMMAND_HEAD_LENGTH = "APDU命令头长度错误";
	public final static String SW1SW2_FC03 = "FC03";	//ERR_APDU_COMMAND_DATA_LENGTH = "APDU命令体长度错误";
	public final static String SW1SW2_FC04 = "FC04";	//ERR_APDU_COMMAND_LE_LENGTH = "APDU命令Le错误";
	public final static String SW1SW2_FC60 = "FC60";	//ERR_ANDROID_APP_DATA = "数据错误";
	public final static String SW1SW2_FC61 = "FC61";	//ERR_ANDROID_APP_CALCULATE = "计算错误";
	public final static String SW1SW2_FC62 = "FC62";	//ERR_ANDROID_APP_DATA_MANIPULATED = "金额被篡改";
	public final static String SW1SW2_FC63 = "FC63";	//ERR_ANDROID_SESSION_ENDED = "会话已中断";
	public final static String SW1SW2_FC64 = "FC64";	//ERR_ANDROID_SESSION_TIMEOUT = "会话超时";
	
	//status words for android
	//android ui
	public final static String SW1SW2_FD01 = "FD01";	//ERR_ANDROID_AMOUNT_EMPTY = "金额不能为空";
	public final static String SW1SW2_FD02 = "FD02";	//ERR_ANDROID_AMOUNT_DIGIT_ONLY = "金额只能为数字";
	public final static String SW1SW2_FD03 = "FD03";	//ERR_ANDROID_AMOUNT_MORE_THAN_ONE_DOT = "金额格式错误，小数点多于1个";
	public final static String SW1SW2_FD04 = "FD04";	//ERR_ANDROID_AMOUNT_MORE_DIGITS_AFTER_DOT = "金额小数点后位数错误";
	public final static String SW1SW2_FD05 = "FD05";	//ERR_ANDROID_AMOUNT_MORE_DIGITS = "金额长度错误";
	public final static String SW1SW2_FD06 = "FD06";	//ERR_ANDROID_AMOUNT_ZERO = "金额不能为0";
	public final static String SW1SW2_FD07 = "FD07";	//ERR_ANDROID_CHALLENGE_EMPTY = "挑战码不能为空";
	public final static String SW1SW2_FD08 = "FD08";	//ERR_ANDROID_CHALLENGE_LENGTH = "挑战码长度错误";
	public final static String SW1SW2_FD09 = "FD09";	//ERR_ANDROID_CHALLENGE_FORMAT = "挑战码格式错误";
	public final static String SW1SW2_FD0A = "FD0A";	//ERR_ANDROID_PIN_EMPTY = "PIN不能为空";
	public final static String SW1SW2_FD0B = "FD0B";	//ERR_ANDROID_PIN_LENGTH = "PIN长度错误";
	public final static String SW1SW2_FD0C = "FD0C";	//ERR_ANDROID_CARD_APP_EMPTY = "卡片应用名不能为空";
	public final static String SW1SW2_FD0D = "FD0D";	//ERR_ANDROID_CARD_APP_LENGTH = "卡片应用名长度错误";
	public final static String SW1SW2_FD0E = "FD0E";	//ERR_ANDROID_POS_NUMBER_EMPTY = "POS机号码不能为空";
	public final static String SW1SW2_FD0F = "FD0F";	//ERR_ANDROID_POS_NUMBER_LENGTH = "POS机号码长度错误";
	public final static String SW1SW2_FD10 = "FD10";	//ERR_ANDROID_ORDER_NUMBER_EMPTY = "订单号不能为空";
	public final static String SW1SW2_FD11 = "FD11";	//ERR_ANDROID_ORDER_NUMBER_LENGTH = "订单号长度错误";
	public final static String SW1SW2_FD12 = "FD12";	//ERR_ANDROID_ACCOUNT_EMPTY = "帐号不能为空";
	public final static String SW1SW2_FD13 = "FD13";	//ERR_ANDROID_ACCOUNT_DIGIT_ONLY = "帐号只能为数字";
	public final static String SW1SW2_FD14 = "FD14";	//ERR_ANDROID_ACCOUNT_LENGTH = "帐号长度错误";
	public final static String SW1SW2_FD15 = "FD15";	//ERR_ANDROID_KEY_INDEX_EMPTY = "密钥索引不能为空";
	public final static String SW1SW2_FD16 = "FD16";	//ERR_ANDROID_KEY_INDEX_LENGTH = "密钥索引长度错误";
	public final static String SW1SW2_FD17 = "FD17";	//ERR_ANDROID_KEY_INDEX_FORMAT = "密钥索引格式错误";
	public final static String SW1SW2_FD18 = "FD18";	//ERR_ANDROID_INPUT_EMPTY = "输入不能为空";
	public final static String SW1SW2_FD19 = "FD19";	//ERR_ANDROID_INPUT_LENGTH = "输入长度错误";
	//android date and time
	public final static String SW1SW2_FD81 = "FD81";	//ERR_ANDROID_DATE_TIME = "系统日期或时间错误";
	//android security, e.g., rsa, des
	public final static String SW1SW2_FD91 = "FD91";	//ERR_ANDROID_MD5 = "MD5计算异常";
	
	//status words for server
	public final static String SW1SW2_FE01 = "FE01";	//ERR_SERVER_LOAD_DATA = "服务器圈存数据错误";
	public final static String SW1SW2_FE21 = "FE21";	//ERR_SERVER_DATE = "服务器日期错误";
	public final static String SW1SW2_FE22 = "FE22";	//ERR_SERVER_TIME = "服务器时间错误";
	public final static String SW1SW2_FE23 = "FE23";	//ERR_SERVER_DATE_BEFORE_START_DATE = "日期早于IC卡应用生效日期";
	public final static String SW1SW2_FE24 = "FE24";	//ERR_SERVER_DATE_AFTER_FAIL_DATE = "日期晚于IC卡应用失效日期";
	
	//status words for media, including nfc
	public final static String SW1SW2_FF00 = "FF00";	//ERR_MEDIA_UNKNOWN = "所选媒介未知";
	public final static String SW1SW2_FF01 = "FF01";	//ERR_NO_MEDIA = "媒介未初始化";
	public final static String SW1SW2_FF02 = "FF02";	//ERR_NFC_NOT_SUPPORT = "手机不支持NFC";
	public final static String SW1SW2_FF03 = "FF03";	//ERR_NFC_NOT_OPEN = "请开启NFC";
	public final static String SW1SW2_FF04 = "FF04";	//ERR_NFC_INTENT = "非NFC Intent";
	public final static String SW1SW2_FF05 = "FF05";	//ERR_NFC_GET_TAG = "未能获得NFC标签";
	public final static String SW1SW2_FF06 = "FF06";	//ERR_NFC_UNKNOWN = "NFC未知错误";
	public final static String SW1SW2_FF07 = "FF07";	//ERR_NFC_NO_CARD = "请将卡片靠近感应区";
	public final static String SW1SW2_FF08 = "FF08";	//ERR_NFC_CARD_REMOVED_WHEN_OPERATING = "卡片已离开感应区，操作未完成";
	
	//readable
	public final static String OK_CARD = "";
	public final static String OK_CARD1 = "未完成全部操作";
	public final static String ERR_UNKNOWN = "未知错误";
	
	public final static String ERR_CARD_APP_LOCK = "卡片应用已锁";
	public final static String ERR_EXTERNAL_AUTHENTICATE_FAIL = "外部认证失败";
	public final static String ERR_CARD_VERIFY_PIN = "PIN验证失败";
	public final static String ERR_CARD_APDU_DATA_LENGTH = "APDU长度错误";
	public final static String ERR_CARD_PIN_LOCKED = "卡片PIN已锁";
	public final static String ERR_CARD_MAC = "MAC错误";
	public final static String ERR_CARD_LOCK = "卡片已锁";
	public final static String ERR_CARD_NO_APP = "卡上无所选应用";
	public final static String ERR_CARD_NO_SUCH_RECORD = "记录号不存在";
	public final static String ERR_CARD_PRIVATE_DATA = "专有数据，拒绝访问";
	public final static String ERR_CARD_APDU_INS = "INS无效";
	public final static String ERR_CARD_UNKNOWN = "卡片未知错误";
	public final static String ERR_CARD_KEY_INDEX = "密钥索引不支持";
	
	public final static String ERR_CARD_APP_DATA = "卡片数据错误";
	public final static String ERR_CARD_APP_CALCULATE = "计算错误";
	public final static String ERR_CARD_NO_TRANSACTION_DETAIL_ENTRY = "无明细入口";
	public final static String ERR_CARD_TRANSACTION_DETAIL_ENTRY = "明细入口错误";
	public final static String ERR_CARD_APP_SDA_NOT_SUPPORT = "卡片不支持SDA";
	public final static String ERR_CARD_APP_DDA_NOT_SUPPORT = "卡片不支持DDA";
	public final static String ERR_CARD_APP_SDA_DDA_NOT_SUPPORT = "卡片不支持SDA和DDA";
	public final static String ERR_CARD_ECC_BALANCE = "电子现金余额错误";
	public final static String ERR_CARD_ECC_BALANCE_LIMIT = "电子现金余额上限错误";
	
	public final static String ERR_EC_SINGLE_TRANSACTION_LIMIT_EXCEED = "电子现金单笔交易限额超限";
	public final static String ERR_EC_BALANCE_LENGTH_EXCEED = "电子现金余额超限";
	public final static String ERR_EC_AMOUNT_EXCEPTION = "电子现金余额异常";
	public final static String ERR_EC_BALANCE_LIMIT_EXCEED = "电子现金余额上限超限";
	public final static String ERR_EC_LOAD_FAIL = "圈存失败";
	
	public final static String ERR_APDU_RESPONSE_UNKNOWN = "APDU响应未知错误";
	public final static String ERR_APDU_COMMAND_EMPTY = "APDU命令不能为空";
	public final static String ERR_APDU_COMMAND_HEAD_LENGTH = "APDU命令头长度错误";
	public final static String ERR_APDU_COMMAND_DATA_LENGTH = "APDU命令体长度错误";
	public final static String ERR_APDU_COMMAND_LE_LENGTH = "APDU命令Le错误";
	public final static String ERR_ANDROID_APP_DATA = "数据错误";
	public final static String ERR_ANDROID_APP_CALCULATE = "计算错误";
	public final static String ERR_ANDROID_APP_DATA_MANIPULATED = "金额被篡改";
	public final static String ERR_ANDROID_SESSION_ENDED = "会话已中断";
	public final static String ERR_ANDROID_SESSION_TIMEOUT = "会话超时";
	
	public final static String ERR_ANDROID_AMOUNT_EMPTY = "金额不能为空";
	public final static String ERR_ANDROID_AMOUNT_DIGIT_ONLY = "金额只能为数字";
	public final static String ERR_ANDROID_AMOUNT_MORE_THAN_ONE_DOT = "金额格式错误，小数点多于1个";
	public final static String ERR_ANDROID_AMOUNT_MORE_DIGITS_AFTER_DOT = "金额小数点后位数错误";
	public final static String ERR_ANDROID_AMOUNT_MORE_DIGITS = "金额长度错误";
	public final static String ERR_ANDROID_AMOUNT_ZERO = "金额不能为0";
	public final static String ERR_ANDROID_CHALLENGE_EMPTY = "挑战码不能为空";
	public final static String ERR_ANDROID_CHALLENGE_LENGTH = "挑战码长度错误";
	public final static String ERR_ANDROID_CHALLENGE_FORMAT = "挑战码格式错误";
	public final static String ERR_ANDROID_PIN_EMPTY = "PIN不能为空";
	public final static String ERR_ANDROID_PIN_LENGTH = "PIN长度错误";
	public final static String ERR_ANDROID_CARD_APP_EMPTY = "卡片应用名不能为空";
	public final static String ERR_ANDROID_CARD_APP_LENGTH = "卡片应用名长度错误";
	public final static String ERR_ANDROID_POS_NUMBER_EMPTY = "POS机号码不能为空";
	public final static String ERR_ANDROID_POS_NUMBER_LENGTH = "POS机号码长度错误";
	public final static String ERR_ANDROID_ORDER_NUMBER_EMPTY = "订单号不能为空";
	public final static String ERR_ANDROID_ORDER_NUMBER_LENGTH = "订单号长度错误";
	public final static String ERR_ANDROID_ACCOUNT_EMPTY = "帐号不能为空";
	public final static String ERR_ANDROID_ACCOUNT_DIGIT_ONLY = "帐号只能为数字";
	public final static String ERR_ANDROID_ACCOUNT_LENGTH = "帐号长度错误";
	public final static String ERR_ANDROID_KEY_INDEX_EMPTY = "密钥索引不能为空";
	public final static String ERR_ANDROID_KEY_INDEX_LENGTH = "密钥索引长度错误";
	public final static String ERR_ANDROID_KEY_INDEX_FORMAT = "密钥索引格式错误";
	public final static String ERR_ANDROID_INPUT_EMPTY = "输入不能为空";
	public final static String ERR_ANDROID_INPUT_LENGTH = "输入长度错误";
	public final static String ERR_ANDROID_DATE_TIME = "系统日期或时间错误";
	public final static String ERR_ANDROID_MD5 = "MD5计算异常";
	
	public final static String ERR_SERVER_LOAD_DATA = "服务器圈存数据错误";
	public final static String ERR_SERVER_DATE = "服务器日期错误";
	public final static String ERR_SERVER_TIME = "服务器时间错误";
	public final static String ERR_SERVER_DATE_BEFORE_START_DATE = "日期早于IC卡应用生效日期";
	public final static String ERR_SERVER_DATE_AFTER_FAIL_DATE = "日期晚于IC卡应用失效日期";
	
	public final static String ERR_MEDIA_UNKNOWN = "所选媒介未知";
	public final static String ERR_NO_MEDIA = "媒介未初始化";
	public final static String ERR_NFC_NOT_SUPPORT = "手机不支持NFC";	
	public final static String ERR_NFC_NOT_OPEN = "请开启NFC";	
	public final static String ERR_NFC_INTENT = "非NFC Intent";
	public final static String ERR_NFC_GET_TAG = "未能获得NFC标签";
	public final static String ERR_NFC_UNKNOWN = "NFC未知错误";
	public final static String ERR_NFC_NO_CARD = "请将卡片靠近感应区";
	public final static String ERR_NFC_CARD_REMOVED_WHEN_OPERATING = "卡片已离开感应区，操作未完成";
	
	//output human readable result according to status word, typically sw1 and sw2
	public static String toReadableSw1Sw2(String sw1sw2) {
		
		if(sw1sw2 == null) {
			return ERR_UNKNOWN;
		}
		
		if(sw1sw2.length() < 4) {
			return ERR_UNKNOWN;
		}
		
		sw1sw2 = sw1sw2.substring(0, 4);
		
		if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK)) {
			return OK_CARD;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK1)) {
			return OK_CARD1;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6283)) {
			return ERR_CARD_APP_LOCK;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6300)) {
			return ERR_EXTERNAL_AUTHENTICATE_FAIL;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_63CX)) {
			return ERR_CARD_VERIFY_PIN;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6700)) {
			return ERR_CARD_APDU_DATA_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6983)) {
			return ERR_CARD_PIN_LOCKED;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6988)) {
			return ERR_CARD_MAC;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A81)) {
			return ERR_CARD_LOCK;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A82)) {
			return ERR_CARD_NO_APP;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A83)) {
			return ERR_CARD_NO_SUCH_RECORD;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A88)) {
			return ERR_CARD_PRIVATE_DATA;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6D00)) {
			return ERR_CARD_APDU_INS;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_6F00)) {
			return ERR_CARD_UNKNOWN;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_9403)) {
			return ERR_CARD_KEY_INDEX;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F801)) {
			return ERR_CARD_APP_DATA;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F802)) {
			return ERR_CARD_APP_CALCULATE;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F803)) {
			return ERR_CARD_NO_TRANSACTION_DETAIL_ENTRY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F804)) {
			return ERR_CARD_TRANSACTION_DETAIL_ENTRY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F805)) {
			return ERR_CARD_APP_SDA_NOT_SUPPORT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F806)) {
			return ERR_CARD_APP_DDA_NOT_SUPPORT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F807)) {
			return ERR_CARD_APP_SDA_DDA_NOT_SUPPORT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F808)) {
			return ERR_CARD_ECC_BALANCE;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_F809)) {
			return ERR_CARD_ECC_BALANCE_LIMIT;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA01)) {
			return ERR_EC_SINGLE_TRANSACTION_LIMIT_EXCEED;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA02)) {
			return ERR_EC_BALANCE_LENGTH_EXCEED;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA03)) {
			return ERR_EC_AMOUNT_EXCEPTION;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA04)) {
			return ERR_EC_BALANCE_LIMIT_EXCEED;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FA05)) {
			return ERR_EC_LOAD_FAIL;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC00)) {
			return ERR_APDU_RESPONSE_UNKNOWN;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC01)) {
			return ERR_APDU_COMMAND_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC02)) {
			return ERR_APDU_COMMAND_HEAD_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC03)) {
			return ERR_APDU_COMMAND_DATA_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC04)) {
			return ERR_APDU_COMMAND_LE_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC60)) {
			return ERR_ANDROID_APP_DATA;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC61)) {
			return ERR_ANDROID_APP_CALCULATE;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC62)) {
			return ERR_ANDROID_APP_DATA_MANIPULATED;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC63)) {
			return ERR_ANDROID_SESSION_ENDED;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FC64)) {
			return ERR_ANDROID_SESSION_TIMEOUT;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD01)) {
			return ERR_ANDROID_AMOUNT_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD02)) {
			return ERR_ANDROID_AMOUNT_DIGIT_ONLY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD03)) {
			return ERR_ANDROID_AMOUNT_MORE_THAN_ONE_DOT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD04)) {
			return ERR_ANDROID_AMOUNT_MORE_DIGITS_AFTER_DOT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD05)) {
			return ERR_ANDROID_AMOUNT_MORE_DIGITS;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD06)) {
			return ERR_ANDROID_AMOUNT_ZERO;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD07)) {
			return ERR_ANDROID_CHALLENGE_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD08)) {
			return ERR_ANDROID_CHALLENGE_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD09)) {
			return ERR_ANDROID_CHALLENGE_FORMAT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0A)) {
			return ERR_ANDROID_PIN_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0B)) {
			return ERR_ANDROID_PIN_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0C)) {
			return ERR_ANDROID_CARD_APP_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0D)) {
			return ERR_ANDROID_CARD_APP_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0E)) {
			return ERR_ANDROID_POS_NUMBER_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD0F)) {
			return ERR_ANDROID_POS_NUMBER_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD10)) {
			return ERR_ANDROID_ORDER_NUMBER_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD11)) {
			return ERR_ANDROID_ORDER_NUMBER_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD12)) {
			return ERR_ANDROID_ACCOUNT_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD13)) {
			return ERR_ANDROID_ACCOUNT_DIGIT_ONLY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD14)) {
			return ERR_ANDROID_ACCOUNT_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD15)) {
			return ERR_ANDROID_KEY_INDEX_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD16)) {
			return ERR_ANDROID_KEY_INDEX_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD17)) {
			return ERR_ANDROID_KEY_INDEX_FORMAT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD18)) {
			return ERR_ANDROID_INPUT_EMPTY;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD19)) {
			return ERR_ANDROID_INPUT_LENGTH;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD81)) {
			return ERR_ANDROID_DATE_TIME;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FD91)) {
			return ERR_ANDROID_MD5;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE01)) {
			return ERR_SERVER_LOAD_DATA;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE21)) {
			return ERR_SERVER_DATE;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE22)) {
			return ERR_SERVER_TIME;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE23)) {
			return ERR_SERVER_DATE_BEFORE_START_DATE;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FE24)) {
			return ERR_SERVER_DATE_AFTER_FAIL_DATE;
		}
		
		  else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF00)) {
			return ERR_MEDIA_UNKNOWN;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF01)) {
			return ERR_NO_MEDIA;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF02)) {
			return ERR_NFC_NOT_SUPPORT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF03)) {
			return ERR_NFC_NOT_OPEN;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF04)) {
			return ERR_NFC_INTENT;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF05)) {
			return ERR_NFC_GET_TAG;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF06)) {
			return ERR_NFC_UNKNOWN;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF07)) {
			return ERR_NFC_NO_CARD;
		} else if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF08)) {
			return ERR_NFC_CARD_REMOVED_WHEN_OPERATING;
		}
		
		  else {
			return ERR_UNKNOWN;
		}
	}
}
