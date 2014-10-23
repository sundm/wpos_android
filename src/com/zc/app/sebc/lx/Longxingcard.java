package com.zc.app.sebc.lx;

import java.util.ArrayList;
import java.util.List;

import com.zc.app.sebc.bridge.LongXingTransDetailSetting;
import com.zc.app.sebc.bridge.StatusCode;
import com.zc.app.sebc.bridge.Transaction_Longxing;
import com.zc.app.sebc.util.BasicUtil;

public class Longxingcard {

	public final static byte LongxingCardAID[] = new byte[] { (byte) 0xD1, (byte) 0x56,
			(byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x00, (byte) 0x62,
			(byte) 0x6F, (byte) 0x6C, (byte) 0x7A, (byte) 0x00, (byte) 0x00,
			(byte) 0x45, (byte) 0x50, (byte) 0x01, (byte) 0x01 };
	
	public final static String TAG = "longxingCard";

	public static CardLogObject getTransactionDetails() {

		String[][] strDetails = Transaction_Longxing
				.getReadableTransactionDetails_Longxing(
						NfcEnv.media,
						SessionTrans_Longxing.APP_NAME_EP_LONGXING,
						SessionTrans_Longxing.SFI_TRANSACTION_DETAIL,
						LongXingTransDetailSetting.TEMPLATE_TRANSACTION_DETAILS,
						1,
						LongXingTransDetailSetting.TEMPLATE_DEFAULT_TRANSACTION_DETAILS,
						LongXingTransDetailSetting.TEMPLATE_DEFAULT_DELETED_TYPE,
						true, true, (byte) 0);

		CardLogObject cardLogObject = new CardLogObject();

		String status = strDetails[0][0];
		cardLogObject.setStatus(status);

		if ((status.equals(StatusCode.SW1SW2_OK) == true)
				|| (status.equals(StatusCode.SW1SW2_OK1) == true)) {

			int i;
			int loc1F01 = -1;
			int loc1F02 = -1;
			int loc1F03 = -1;
			int loc1F04 = -1;
			int loc1F05 = -1;
			int loc1F06 = -1;
			int loc1F07 = -1;

			loc1F01 = BasicUtil.findStringLocInArray(
					LongXingTransDetailSetting.TAG_1F01, strDetails[1]);
			loc1F02 = BasicUtil.findStringLocInArray(
					LongXingTransDetailSetting.TAG_1F02, strDetails[1]);
			loc1F03 = BasicUtil.findStringLocInArray(
					LongXingTransDetailSetting.TAG_1F03, strDetails[1]);
			loc1F04 = BasicUtil.findStringLocInArray(
					LongXingTransDetailSetting.TAG_1F04, strDetails[1]);
			loc1F05 = BasicUtil.findStringLocInArray(
					LongXingTransDetailSetting.TAG_1F05, strDetails[1]);
			loc1F06 = BasicUtil.findStringLocInArray(
					LongXingTransDetailSetting.TAG_1F06, strDetails[1]);
			loc1F07 = BasicUtil.findStringLocInArray(
					LongXingTransDetailSetting.TAG_1F07, strDetails[1]);

			List<CardLogEntry> details = new ArrayList<CardLogEntry>();

			for (i = 2; i < strDetails.length; i++) {

				String val1F01;
				String val1F02;
				String val1F03;
				String val1F04;
				String val1F05;
				String val1F06;
				String val1F07;
				CardLogEntry cardLogEntry = new CardLogEntry();

				if (loc1F01 > -1) {
					val1F01 = strDetails[i][loc1F01];
					cardLogEntry.setSerialNo(val1F01);
				}

				if (loc1F02 > -1) {
					val1F02 = strDetails[i][loc1F02];
					cardLogEntry.setOverdrawLimit(val1F02);
				}

				if (loc1F03 > -1) {
					val1F03 = strDetails[i][loc1F03];
					cardLogEntry.setAmount(val1F03);
				}

				if (loc1F04 > -1) {
					val1F04 = strDetails[i][loc1F04];
					cardLogEntry.setType(val1F04);
				}

				if (loc1F05 > -1) {
					val1F05 = strDetails[i][loc1F05];
					cardLogEntry.setTerminalNumber(val1F05);
				}

				if (loc1F06 > -1) {
					val1F06 = strDetails[i][loc1F06];
					cardLogEntry.setDate(val1F06);
				}

				if (loc1F07 > -1) {
					val1F07 = strDetails[i][loc1F07];
					cardLogEntry.setTime(val1F07);
				}

				details.add(cardLogEntry);
			}

			cardLogObject.setDetails(details);
		}

		return cardLogObject;
	}

	public static LongxingcardInfo getLongxingcardInfo() {

		String result[];
		String status;
		LongxingcardInfo longxingcardInfo = new LongxingcardInfo();

		result = Transaction_Longxing.getCardInfomation(NfcEnv.media,
				SessionTrans_Longxing.APP_NAME_STRING_EP_LONGXING);
		status = result[0];

		longxingcardInfo.setStatus(status);

		if (status.equals(StatusCode.SW1SW2_OK)) {

			longxingcardInfo.setCardNumber(result[1]);
			longxingcardInfo.setBalance(result[2]);
		}

		return longxingcardInfo;
	}
}
