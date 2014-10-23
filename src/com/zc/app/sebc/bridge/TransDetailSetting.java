package com.zc.app.sebc.bridge;

import com.zc.app.sebc.pboc2.TlvTag;
import com.zc.app.sebc.pboc2.TransType;

public final class TransDetailSetting {

	public static final byte[] ENTRY_TAG_B_DEFAULT = TlvTag.TAG_B_9F4D;
	public static final byte[] ENTRY_TAG_B_LOAD_DEFAULT = TlvTag.TAG_B_DF4D;

	public static final byte[] ENTRY_FORMAT_B_DEFAULT = TlvTag.TAG_B_9F4F;
	public static final byte[] ENTRY_FORMAT_B_LOAD_DEFAULT = TlvTag.TAG_B_DF4F;

	public static final byte[] ENTRY_B_DEFAULT = { (byte) 0x0B, (byte) 0x0A };
	public static final byte[] ENTRY_B_LOAD_DEFAULT = { (byte) 0x0C, (byte) 0x0A };

	public final static String TAG_PRI_01 = "01";
	public final static String TAG_PRI_02 = "02";
	public final static String TAG_PRI_03 = "03";
	public final static String TAG_PRI_04 = "04";

	public static final String READ_EN_TAG_UNKNOWN = "UNKNOWN";
	public static final String READ_EN_TAG_9A = "DATE";
	public static final String READ_EN_TAG_9C = "TRANSTYPE";
	public static final String READ_EN_TAG_5F2A = "CURRENCY";
	public static final String READ_EN_TAG_9F02 = "AMOUNT";
	public static final String READ_EN_TAG_9F03 = "OTHERAMOUNT";
	public static final String READ_EN_TAG_9F1A = "COUNTRY";
	public static final String READ_EN_TAG_9F21 = "TIME";
	public static final String READ_EN_TAG_9F36 = "COUNTER";
	public static final String READ_EN_TAG_9F4E = "TRADER";
	public static final String READ_EN_TAG_PRI_01 = "P1";
	public static final String READ_EN_TAG_PRI_02 = "P2";
	public static final String READ_EN_TAG_PRI_03 = "AMOUNTBEFORELOAD";
	public static final String READ_EN_TAG_PRI_04 = "AMOUNTAFTERLOAD";

	public static final String READ_CN_TAG_UNKNOWN = "未知";
	public static final String READ_CN_TAG_9A = "交易日期";
	public static final String READ_CN_TAG_9C = "交易类型";
	public static final String READ_CN_TAG_5F2A = "交易货币代码";
	public static final String READ_CN_TAG_9F02 = "授权金额";
	public static final String READ_CN_TAG_9F03 = "其他金额";
	public static final String READ_CN_TAG_9F1A = "终端国家代码";
	public static final String READ_CN_TAG_9F21 = "交易时间";
	public static final String READ_CN_TAG_9F36 = "交易计数器";
	public static final String READ_CN_TAG_9F4E = "商户名称";
	public static final String READ_CN_TAG_PRI_01 = "P1";
	public static final String READ_CN_TAG_PRI_02 = "P2";
	public static final String READ_CN_TAG_PRI_03 = "圈存前余额";
	public static final String READ_CN_TAG_PRI_04 = "圈存后余额";

	public static final String TEMP_READABLE_AMOUNT = "%1.2f";
	public static final String[] TEMP_READABLE_DATE = { "-", "-", "" };
	
	public static final String[] TEMP_READABLE_TIME = { ":", ":", "" };
	
	public static final String[] TEMPLATE_DEFAULT_TRANSACTION_DETAILS = {
			TlvTag.TAG_9A, TlvTag.TAG_9C, TlvTag.TAG_5F2A, TlvTag.TAG_9F02,
			TlvTag.TAG_9F21, TlvTag.TAG_9F4E };

	public static final String[] TEMPLATE_LOAD_DEFAULT_TRANSACTION_DETAILS = {
			TAG_PRI_01, TAG_PRI_02, TAG_PRI_03, TAG_PRI_04, TlvTag.TAG_9A,
			TlvTag.TAG_9F21, TlvTag.TAG_9F1A, TlvTag.TAG_9F4E };

	public static final String[] TEMPLATE_DEFAULT_DELETED_TYPE = { TransType.TYPE_NAME_34 };

	public static final String[] TEMPLATE_LOAD_DEFAULT_DELETED_TYPE = {};

	public final static byte[] TEMPLATE_DEFAULT_LOAD_DETAILS_PREFIX = {
			(byte) 0x01, (byte) 0x01, (byte) 0x02, (byte) 0x01, (byte) 0x03,
			(byte) 0x06, (byte) 0x04, (byte) 0x06 };

	public static final String[][] TAG_READABLE = {
			{ TAG_PRI_01, TAG_PRI_02, TAG_PRI_03, TAG_PRI_04, TlvTag.TAG_9A,
					TlvTag.TAG_9C, TlvTag.TAG_5F2A, TlvTag.TAG_9F02,
					TlvTag.TAG_9F03, TlvTag.TAG_9F1A, TlvTag.TAG_9F21,
					TlvTag.TAG_9F36, TlvTag.TAG_9F4E },
			{ TAG_PRI_01, TAG_PRI_02, TAG_PRI_03, TAG_PRI_04, TlvTag.TAG_9A,
					TlvTag.TAG_9C, TlvTag.TAG_5F2A, TlvTag.TAG_9F02,
					TlvTag.TAG_9F03, TlvTag.TAG_9F1A, TlvTag.TAG_9F21,
					TlvTag.TAG_9F36, TlvTag.TAG_9F4E },
			{ READ_EN_TAG_PRI_01, READ_EN_TAG_PRI_02, READ_EN_TAG_PRI_03,
					READ_EN_TAG_PRI_04, READ_EN_TAG_9A, READ_EN_TAG_9C,
					READ_EN_TAG_5F2A, READ_EN_TAG_9F02, READ_EN_TAG_9F03,
					READ_EN_TAG_9F1A, READ_EN_TAG_9F21, READ_EN_TAG_9F36,
					READ_EN_TAG_9F4E },
			{ READ_CN_TAG_PRI_01, READ_CN_TAG_PRI_02, READ_CN_TAG_PRI_03,
					READ_CN_TAG_PRI_04, READ_CN_TAG_9A, READ_CN_TAG_9C,
					READ_CN_TAG_5F2A, READ_CN_TAG_9F02, READ_CN_TAG_9F03,
					READ_CN_TAG_9F1A, READ_CN_TAG_9F21, READ_CN_TAG_9F36,
					READ_CN_TAG_9F4E } };
}
