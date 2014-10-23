package com.zc.app.sebc.bridge;

public final class LongXingTransDetailSetting {

	public static final String TEMPLATE_TRANSACTION_DETAILS = "1F01021F02031F03041F04011F05061F06041F0703";

	public static final String TAG_1F01 = "1F01";
	public static final String TAG_1F02 = "1F02";
	public static final String TAG_1F03 = "1F03";
	public static final String TAG_1F04 = "1F04";
	public static final String TAG_1F05 = "1F05";
	public static final String TAG_1F06 = "1F06";
	public static final String TAG_1F07 = "1F07";

	public static final String READ_EN_TAG_UNKNOWN = "UNKNOWN";
	public static final String READ_EN_TAG_1F01 = "SERIALNO";
	public static final String READ_EN_TAG_1F02 = "OVERDRAWLIMIT";
	public static final String READ_EN_TAG_1F03 = "AMOUNT";
	public static final String READ_EN_TAG_1F04 = "TYPE";
	public static final String READ_EN_TAG_1F05 = "TERMINALNUMBER";
	public static final String READ_EN_TAG_1F06 = "DATE";
	public static final String READ_EN_TAG_1F07 = "TIME";

	public static final String READ_CN_TAG_UNKNOWN = "未知";
	public static final String READ_CN_TAG_1F01 = "交易序号";
	public static final String READ_CN_TAG_1F02 = "透支限额";
	public static final String READ_CN_TAG_1F03 = "交易金额";
	public static final String READ_CN_TAG_1F04 = "交易类型";
	public static final String READ_CN_TAG_1F05 = "终端编号";
	public static final String READ_CN_TAG_1F06 = "交易日期";
	public static final String READ_CN_TAG_1F07 = "交易时间";

	public static final String TEMP_READABLE_AMOUNT = "%1.2f";
	public static final String[] TEMP_READABLE_DATE = { "-", "-", "" };
	public static final String[] TEMP_READABLE_TIME = { ":", ":", "" };

	public static final String[] TEMPLATE_DEFAULT_TRANSACTION_DETAILS = {
			TAG_1F01, TAG_1F02, TAG_1F03, TAG_1F04, TAG_1F05, TAG_1F06,
			TAG_1F07 };

	public static final String[] TEMPLATE_DEFAULT_DELETED_TYPE = {};

	public static final String[][] TAG_READABLE = {
			{ TAG_1F01, TAG_1F02, TAG_1F03, TAG_1F04, TAG_1F05, TAG_1F06,
					TAG_1F07 },
			{ TAG_1F01, TAG_1F02, TAG_1F03, TAG_1F04, TAG_1F05, TAG_1F06,
					TAG_1F07 },
			{ READ_EN_TAG_1F01, READ_EN_TAG_1F02, READ_EN_TAG_1F03,
					READ_EN_TAG_1F04, READ_EN_TAG_1F05, READ_EN_TAG_1F06,
					READ_EN_TAG_1F07 },
			{ READ_CN_TAG_1F01, READ_CN_TAG_1F02, READ_CN_TAG_1F03,
					READ_CN_TAG_1F04, READ_CN_TAG_1F05, READ_CN_TAG_1F06,
					READ_CN_TAG_1F07 } };
}
