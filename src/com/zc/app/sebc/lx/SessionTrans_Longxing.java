package com.zc.app.sebc.lx;

import com.zc.app.sebc.bridge.LongXingTransDetailSetting;
import com.zc.app.sebc.iso7816.CApdu;
import com.zc.app.sebc.iso7816.RApdu;
import com.zc.app.sebc.nfcmedia.Media;
import com.zc.app.sebc.pboc2.CApduPboc.PbocCreditForLoad;
import com.zc.app.sebc.pboc2.CApduPboc.PbocGetBalance;
import com.zc.app.sebc.pboc2.CApduPboc.PbocInitForLoad;
import com.zc.app.sebc.pboc2.CApduPboc.PbocInitForPurchase;
import com.zc.app.sebc.pboc2.CApduPboc.PbocReadBinary;
import com.zc.app.sebc.pboc2.CApduPboc.PbocSelect;
import com.zc.app.sebc.pboc2.CApduPboc.PbocVerify;
import com.zc.app.sebc.pboc2.SessionTrans;
import com.zc.app.sebc.pboc2.Sw1Sw2;
import com.zc.app.sebc.pboc2.TransUtil;
import com.zc.app.sebc.util.BasicUtil;
import com.zc.app.utils.ZCLog;

public final class SessionTrans_Longxing {

	public final static String APP_NAME_STRING_EP_LONGXING = "D15601010100626F6C7A000045500101";

	public final static byte[] APP_NAME_EP_LONGXING = { // card app name of
			// Longxing ep
			(byte) 0xD1, (byte) 0x56, (byte) 0x01, (byte) 0x01, (byte) 0x01,
			(byte) 0x00, (byte) 0x62, (byte) 0x6F, (byte) 0x6C, (byte) 0x7A,
			(byte) 0x00, (byte) 0x00, (byte) 0x45, (byte) 0x50, (byte) 0x01,
			(byte) 0x01 };

	public final static byte SFI_TRANSACTION_DETAIL = (byte) 0x18;

	private static Media sessionMedia = null; // media during a session
	private static String sessionAmount = null; // amount during a session
	private static String sessionPin = null; // pin during a session
	// private static String sessionDate = null; //date during a session
	// private static String sessionTime = null; //time during a session

	private static long sessionStartTime = 0;
	private static long sessionTimeout = 0;

	private static String[] replaceStringArray(String[] array,
			String[][] lookup, int row, boolean flag, boolean chinese) {

		if (BasicUtil.isEmptyStringArray(array) == true) {
			return null;
		}

		if (BasicUtil.isEmptyStringMatrix(lookup) == true) {
			return null;
		}

		if (lookup.length < 2) {
			return null;
		}

		if (row < 0) {
			return null;
		}

		if (row >= lookup.length) {
			return null;
		}

		String newString;
		String[] result = new String[array.length];

		for (int i = 0; i < array.length; i++) {
			newString = TransUtil.replaceString(array[i], lookup, row);
			if (newString == null) {
				if (flag) {
					if (chinese) {
						newString = LongXingTransDetailSetting.READ_CN_TAG_UNKNOWN;
						result[i] = newString;
					} else {
						newString = LongXingTransDetailSetting.READ_EN_TAG_UNKNOWN;
						result[i] = newString;
					}
				}
			} else {
				result[i] = newString;
			}
		}

		return result;
	}

	public static String toReadableAmount(String amount) {

		if (BasicUtil.isEmptyString(amount)) {
			return null;
		}

		int temp1 = Integer.valueOf(amount, 16);
		String temp2 = Integer.toString(temp1);
		Double temp = Double.parseDouble(temp2) / 100;

		String newAmount = String.format(
				LongXingTransDetailSetting.TEMP_READABLE_AMOUNT, temp);

		return newAmount;
	}

	public static String toReadableOverdraw(String overdraw) {

		if (BasicUtil.isEmptyString(overdraw)) {
			return null;
		}

		Double temp = Double.parseDouble(overdraw) / 100;
		String newAmount = String.format(
				LongXingTransDetailSetting.TEMP_READABLE_AMOUNT, temp);

		return newAmount;
	}

	public static String toReadableTransNumber(String number) {

		if (BasicUtil.isEmptyString(number)) {
			return null;
		}

		int temp1 = Integer.valueOf(number, 16);
		String temp2 = Integer.toString(temp1);
		Double temp = Double.parseDouble(temp2) / 100;

		String newAmount = String.format(
				LongXingTransDetailSetting.TEMP_READABLE_AMOUNT, temp);

		return newAmount;
	}

	public static String toReadableTime(String[] template, String time) {

		if (BasicUtil.isEmptyStringArray(template)) {
			return null;
		}

		if (BasicUtil.isEmptyString(time)) {
			return null;
		}

		if (time.length() != 6) {
			return null;
		}

		if (template.length != 3) {
			return null;
		}

		String newTime = time.substring(0, 2).concat(template[0])
				.concat(time.substring(2, 4)).concat(template[1])
				.concat(time.substring(4, time.length())).concat(template[2]);
		return newTime;
	}

	public static String toReadableDate(String[] template, String date) {

		if (BasicUtil.isEmptyStringArray(template)) {
			return null;
		}

		if (BasicUtil.isEmptyString(date)) {
			return null;
		}

		if (date.length() != 8) {
			return null;
		}

		if (template.length != 3) {
			return null;
		}

		String newDate = date.substring(0, 4).concat(template[0])
				.concat(date.substring(4, 6)).concat(template[1])
				.concat(date.substring(6, date.length())).concat(template[2]);
		return newDate;
	}

	public static String[][] toReadableTableMatrix(String[][] matrix,
			int start, int length, boolean flag, int headReadId) {

		String[][] error = new String[1][1];
		error[0][0] = Sw1Sw2.SW1SW2_FC60;

		if (BasicUtil.isEmptyStringMatrix(matrix)) {
			return error;
		}

		if (matrix.length < 2) {
			return error;
		}

		if (start != 1) {
			return error;
		}

		if (length < 0) {
			return error;
		}

		if ((start + length) >= matrix.length) {
			return error;
		}

		if (headReadId < 0) {
			return error;
		}

		String[] readable = replaceStringArray(matrix[start],
				LongXingTransDetailSetting.TAG_READABLE, headReadId, flag, true);
		if (readable == null) {
			return error;
		}

		int i;
		int j;
		String temp1;

		for (i = 0; i < matrix[0].length; i++) {
			if (matrix[start][i].equals(LongXingTransDetailSetting.TAG_1F07)) {
				for (j = start + 1; j < start + 1 + length; j++) {
					if (matrix[j][i] != null) {
						temp1 = toReadableTime(
								LongXingTransDetailSetting.TEMP_READABLE_TIME,
								matrix[j][i]);
						if (temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if (matrix[start][i]
					.equals(LongXingTransDetailSetting.TAG_1F06)) {
				for (j = start + 1; j < start + 1 + length; j++) {
					if (matrix[j][i] != null) {
						temp1 = toReadableDate(
								LongXingTransDetailSetting.TEMP_READABLE_DATE,
								matrix[j][i]);
						if (temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if (matrix[start][i]
					.equals(LongXingTransDetailSetting.TAG_1F05)) {

			} else if (matrix[start][i]
					.equals(LongXingTransDetailSetting.TAG_1F04)) {
				for (j = start + 1; j < start + 1 + length; j++) {
					if (matrix[j][i] != null) {
						temp1 = TransType
								.toReadableTransactionType(matrix[j][i]);
						if (temp1 != null) {
							matrix[j][i] = temp1;
						}
					}
				}
			} else if (matrix[start][i]
					.equals(LongXingTransDetailSetting.TAG_1F03)) {
				for (j = start + 1; j < start + 1 + length; j++) {
					if (matrix[j][i] != null) {
						temp1 = toReadableAmount(matrix[j][i]);
						if (temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if (matrix[start][i]
					.equals(LongXingTransDetailSetting.TAG_1F02)) {
				for (j = start + 1; j < start + 1 + length; j++) {
					if (matrix[j][i] != null) {
						temp1 = toReadableOverdraw(matrix[j][i]);
						if (temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if (matrix[start][i]
					.equals(LongXingTransDetailSetting.TAG_1F01)) {
				for (j = start + 1; j < start + 1 + length; j++) {
					if (matrix[j][i] != null) {
						temp1 = toReadableTransNumber(matrix[j][i]);
						if (temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			}
		}

		matrix[start] = readable;
		return matrix;
	}

	public static String[][] getReadableTransactionDetails_Longxing(
			Media media, byte[] appName, byte[] entryTag, byte[] formatTag,
			byte[] oldEntry, byte sfi, String template, int headReadId,
			String[] outerTemplate, String[] deletedType, boolean order,
			boolean deleteInit, byte init) {

		String[][] strApduResp = SessionTrans.getTransactionDetails(media,
				appName, null, null, null, sfi, template, outerTemplate,
				deletedType, order, deleteInit, init);

		String sw1sw2 = strApduResp[0][0];
		if ((sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false)
				&& (sw1sw2.equals(Sw1Sw2.SW1SW2_OK1) == false)) {
			return strApduResp;
		}

		if (strApduResp.length < 2) {
			strApduResp[0][0] = Sw1Sw2.SW1SW2_FC60;
			return strApduResp;
		}

		strApduResp = toReadableTableMatrix(strApduResp, 1,
				strApduResp.length - 2, false, headReadId);
		return strApduResp;
	}

	public static String[] getCardInfomation(Media media, String appName) {

		String strErrResp[] = new String[1];
		String sw1sw2;

		if ((sw1sw2 = Media.checkMedia(media)) != Sw1Sw2.SW1SW2_OK) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}

		if (appName == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD0C;
			return strErrResp;
		} else if (appName.length() < 10) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD0D;
			return strErrResp;
		}

		media.connect();
		String strApduResp[] = null;
		strApduResp = calculateCardInfo(media, appName);
		media.close();

		return strApduResp;
	}

	private static String[] calculateCardInfo(Media media, String appName) {

		String strErrResp[] = new String[1];
		String sw1sw2;
		RApdu rApdu;
		String strResp;

		PbocSelect pbocSelect = new PbocSelect(appName);
		rApdu = media.sendApdu(pbocSelect);
		if ((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}

		PbocGetBalance pbocGetBalance = new PbocGetBalance((byte) 0x02);
		rApdu = media.sendApdu(pbocGetBalance);
		if ((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}

		strResp = rApdu.getDataHexString();
		if (strResp == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		} else if (strResp.length() != 4 + 4) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}

		String balance = strResp.substring(0, 4 + 4);
		int intBalance = Integer.valueOf(balance, 16);
		balance = String.format("%d", intBalance);

		PbocReadBinary pbocReadBinary = new PbocReadBinary((byte) 0x15,
				(byte) 0x00);
		rApdu = media.sendApdu(pbocReadBinary);
		if ((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}

		strResp = rApdu.getDataHexString();
		if (strResp == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		} else if (strResp.length() < (10 + 10 + 10 + 10 + 10 + 10)) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		String appSerialNo = strResp.substring(24, 40);

		String strApduResp[] = new String[3];
		strApduResp[0] = Sw1Sw2.SW1SW2_OK;
		strApduResp[1] = appSerialNo;
		strApduResp[2] = balance;

		return strApduResp;
	}

	public static String requestInitCreditForLoad_Longxing(Media media,
			String strAmount, String pin, boolean nonZero) {

		String sw1sw2;

		if ((sw1sw2 = Media.checkMedia(media)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}

		String checkResult = TransUtil.checkInputAmount(strAmount, 8, 2,
				nonZero);
		sw1sw2 = checkResult.substring(0, 4);
		if (sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		strAmount = checkResult.substring(4, checkResult.length());

		if (pin == null) {
			return Sw1Sw2.SW1SW2_FD0A;
		} else if (pin.length() < 1) {
			return Sw1Sw2.SW1SW2_FD0B;
		}

		sessionMedia = media;
		sessionAmount = strAmount;
		sessionPin = pin;

		sessionStartTime = System.nanoTime();
		sessionTimeout = 30000000000L;

		return Sw1Sw2.SW1SW2_OK;
	}

	private static void clearCreditForLoadSession() {
		sessionMedia = null;
		sessionAmount = null;
		sessionPin = null;
		// sessionDate = null;
		// sessionTime = null;

		sessionStartTime = 0;
		sessionTimeout = 0;
	}

	public static String[] requestCreditForLoad_Longxing(String appName,
			String keyId, String terminalNo, String date, String time) {

		String[] strErrResp = new String[1];

		if (appName == null) {
			clearCreditForLoadSession();
			strErrResp[0] = Sw1Sw2.SW1SW2_FD0C;
			return strErrResp;
		} else if (appName.length() < 10) {
			clearCreditForLoadSession();
			strErrResp[0] = Sw1Sw2.SW1SW2_FD0D;
			return strErrResp;
		}

		if (keyId == null) {
			clearCreditForLoadSession();
			strErrResp[0] = Sw1Sw2.SW1SW2_FD15;
			return strErrResp;
		} else {
			int len = keyId.length();
			if (len > 2) {
				clearCreditForLoadSession();
				strErrResp[0] = Sw1Sw2.SW1SW2_FD16;
				return strErrResp;
			} else if (len < 2) {
				keyId = "00".substring(0, 2 - len) + keyId;
			}
		}

		if (terminalNo == null) {
			clearCreditForLoadSession();
			strErrResp[0] = Sw1Sw2.SW1SW2_FD0E;
			return strErrResp;
		} else {
			int len = terminalNo.length();
			if (len > 12) {
				clearCreditForLoadSession();
				strErrResp[0] = Sw1Sw2.SW1SW2_FD0F;
				return strErrResp;
			} else if (len < 12) {
				terminalNo = "000000000000".substring(0, 12 - len) + terminalNo;
			}
		}

		if (date == null) {
			clearCreditForLoadSession();
			strErrResp[0] = Sw1Sw2.SW1SW2_FE21;
			return strErrResp;
		} else {
			int len = date.length();
			if (len != 8) {
				clearCreditForLoadSession();
				strErrResp[0] = Sw1Sw2.SW1SW2_FE21;
				return strErrResp;
			}
		}

		if (time == null) {
			clearCreditForLoadSession();
			strErrResp[0] = Sw1Sw2.SW1SW2_FE22;
			return strErrResp;
		} else {
			int len = time.length();
			if (len != 6) {
				clearCreditForLoadSession();
				strErrResp[0] = Sw1Sw2.SW1SW2_FE22;
				return strErrResp;
			}
		}

		// sessionDate = date;
		// sessionTime = time;

		return requestCreditForLoad_Longxing(appName, keyId, terminalNo);
	}

	private static String[] requestCreditForLoad_Longxing(String appName,
			String keyId, String terminalNo) {

		String strApduResp[] = null;
		Media media = sessionMedia;
		String strAmount = sessionAmount;
		String pin = sessionPin;
		sessionPin = null;

		media.connect();
		strApduResp = calculateCreditForLoadRequest_Longxing(media, appName,
				strAmount, terminalNo, keyId, pin);

		if (strApduResp[0].equals(Sw1Sw2.SW1SW2_OK) == false) {
			clearCreditForLoadSession();
			media.close();
		}

		return strApduResp;
	}

	// calculate credit for load
	private static String[] calculateCreditForLoadRequest_Longxing(Media media,
			String appName, String strAmount, String terminalNo, String keyId,
			String pin) {

		String strResp = null;
		String sw1sw2 = null;
		RApdu rApdu = null;
		String[] strErrResp = new String[1];

		// select application
		PbocSelect pbocSelect = new PbocSelect(appName);
		rApdu = media.sendApdu(pbocSelect);
		if ((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}

		// verify
		PbocVerify pbocVerify = new PbocVerify((byte) 0x00, pin);
		rApdu = media.sendApdu(pbocVerify);
		pin = null;
		pbocVerify = null;
		if ((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			if (sw1sw2.substring(0, 3).equals(Sw1Sw2.SW1SW2_63C)) {
				sw1sw2 = Sw1Sw2.SW1SW2_63CX;
			}
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}

		PbocReadBinary pbocReadBinary = new PbocReadBinary((byte) 0x15,
				(byte) 0x00);
		rApdu = media.sendApdu(pbocReadBinary);
		if ((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}

		strResp = rApdu.getDataHexString();
		if (strResp == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		} else if (strResp.length() < (10 + 10 + 10 + 10 + 10 + 10)) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}

		String issuerId = strResp.substring(0, 16);
		String appSerialNo = strResp.substring(24, 40);

		byte[] bytKeyId = BasicUtil.hexStringToBytes(keyId);
		if (bytKeyId == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD17;
			return strErrResp;
		} else if (bytKeyId.length != 1) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD16;
			return strErrResp;
		}

		PbocInitForLoad pbocInitForLoad = new PbocInitForLoad((byte) 0x02,
				bytKeyId[0], strAmount, terminalNo);
		rApdu = media.sendApdu(pbocInitForLoad);
		if ((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}

		strResp = rApdu.getDataHexString();
		if (strResp == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		} else if (strResp.length() != 16 + 16) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}

		String balance = strResp.substring(0, 4 + 4);
		int intBalance = Integer.valueOf(balance, 16);
		balance = String.format("%d", intBalance);

		String strApduResp[] = new String[12];
		strApduResp[0] = Sw1Sw2.SW1SW2_OK;
		strApduResp[1] = issuerId;
		strApduResp[2] = appSerialNo;
		strApduResp[3] = keyId;
		strApduResp[4] = strAmount;
		strApduResp[5] = terminalNo;
		strApduResp[6] = balance;
		strApduResp[7] = strResp.substring(4 + 4, 4 + 2 + 4 + 2);
		strApduResp[8] = strResp
				.substring(4 + 2 + 4 + 2, 4 + 2 + 1 + 4 + 2 + 1);
		strApduResp[9] = strResp.substring(4 + 2 + 1 + 4 + 2 + 1, 4 + 2 + 1 + 1
				+ 4 + 2 + 1 + 1);
		strApduResp[10] = strResp.substring(4 + 2 + 1 + 1 + 4 + 2 + 1 + 1, 4
				+ 2 + 1 + 1 + 4 + 4 + 2 + 1 + 1 + 4);
		strApduResp[11] = strResp.substring(4 + 2 + 1 + 1 + 4 + 4 + 2 + 1 + 1
				+ 4, strResp.length());

		return strApduResp;
	}

	public static String performLongxingCreditForLoad(String creditForLoad) {

		long curTime = System.nanoTime();
		if ((sessionStartTime + sessionTimeout) < curTime) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC64;
		}

		if (sessionMedia == null) {
			clearCreditForLoadSession();
			return Sw1Sw2.SW1SW2_FC63;
		} else if (sessionMedia.isConnected() == false) {
			clearCreditForLoadSession();
			return Sw1Sw2.SW1SW2_FC63;
		}

		if (sessionAmount == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}

		String loadResult = null;
		loadResult = executeLongxingCreditForLoad(creditForLoad);

		clearCreditForLoadSession();
		Media.closeSessionMedia(sessionMedia);

		return loadResult;
	}

	private static String executeLongxingCreditForLoad(String creditForLoad) {

		Media media = sessionMedia;
		String strResp = null;
		String sw1sw2 = null;
		RApdu rApdu = null;

		PbocCreditForLoad pbocCreditForLoad = new PbocCreditForLoad(
				creditForLoad);
		if (pbocCreditForLoad.isCApduOk() == false) {
			return Sw1Sw2.SW1SW2_FE01;
		}

		rApdu = media.sendApdu(pbocCreditForLoad);
		if ((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}

		strResp = rApdu.getDataHexString();
		/*
		 * if(strResp == null) { return Sw1Sw2.SW1SW2_F801; } else
		 * if(strResp.length() != 8) { return Sw1Sw2.SW1SW2_F801; }
		 */

		return Sw1Sw2.SW1SW2_OK + strResp;
	}

	// add by sundm. add credit for purchase.
	public static LongxingcardRequest preInitCreditForPurchase_Longxing(
			Media media, String strAmount, String terminalNo, String keyId,
			boolean nonZero) {

		LongxingcardRequest request = new LongxingcardRequest();

		if (Media.checkMedia(media).equals(Sw1Sw2.SW1SW2_OK) == false) {
			request.setSwString(Media.checkMedia(media));
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG, "media check failed!");
			return request;
		}

		String checkResult = TransUtil.checkInputAmount(strAmount, 8, 2,
				nonZero);
		String sw1sw2 = checkResult.substring(0, 4);
		if (sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
			request.setSwString(sw1sw2);
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG, "checkResult:" + checkResult);
			ZCLog.i(Longxingcard.TAG, "sw:" + sw1sw2);
			return request;
		}
		strAmount = checkResult.substring(4, checkResult.length());

		sessionMedia = media;
		sessionAmount = strAmount;

		sessionStartTime = System.nanoTime();
		sessionTimeout = 30000000000L;

		media.close();

		media.connect();
		request = initCreditForPurchaseRequest_Longxing(media, strAmount,
				terminalNo, keyId);

		return request;
	}

	public static LongxingcardRequest creditForPurchase_Longxing(String dataString){
		LongxingcardRequest request = new LongxingcardRequest();

		RApdu rApdu = null;

		// purchase
		//PbocCreditForPurchase pbocPurchase = new PbocCreditForPurchase(dataString);
		CApdu cApdu = new CApdu(dataString);
		rApdu = sessionMedia.sendApdu(cApdu);
		if (rApdu.getSw1Sw2String().equals(Sw1Sw2.SW1SW2_OK) == false) {
			request.setSwString(rApdu.getSw1Sw2String());
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG, "select failed! sw:" + request.getSwString());
			
			return request;
		}
		else {
			request.setOK(true);
			request.setSwString(Sw1Sw2.SW1SW2_OK);
			
			String hexString = rApdu.getHexString();
			
			ZCLog.i(Longxingcard.TAG, hexString);
			
			request.setResponseString(hexString);
			request.setTacString(hexString.substring(0, 8));
			request.setMac2String(hexString.substring(8, 16));
			
			return request;
		}
	}

	private static LongxingcardRequest initCreditForPurchaseRequest_Longxing(
			Media media, String strAmount, String terminalNo, String keyId) {

		LongxingcardRequest request = new LongxingcardRequest();

		RApdu rApdu = null;

		// select application
		PbocSelect pbocSelect = new PbocSelect(Longxingcard.LongxingCardAID);
		rApdu = media.sendApdu(pbocSelect);
		if (rApdu.getSw1Sw2String().equals(Sw1Sw2.SW1SW2_OK) == false) {
			request.setSwString(rApdu.getSw1Sw2String());
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG,
					"select failed! sw:" + request.getSwString());
			return request;
		}

		PbocReadBinary pbocReadBinary = new PbocReadBinary((byte) 0x15,
				(byte) 0x00);
		rApdu = media.sendApdu(pbocReadBinary);
		if (rApdu.getSw1Sw2String().equals(Sw1Sw2.SW1SW2_OK) == false) {
			request.setSwString(rApdu.getSw1Sw2String());
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG,
					"read 15 file failed! sw:" + request.getSwString());
			return request;
		}

		String strResp = rApdu.getDataHexString();
		if (strResp == null) {
			request.setSwString(Sw1Sw2.SW1SW2_F801);
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG,
					"getDataHexString failed! sw:" + request.getSwString());
			return request;
		}

		String issuerId = strResp.substring(0, 16);
		String appSerialNo = strResp.substring(24, 40);

		byte[] bytKeyId = BasicUtil.hexStringToBytes(keyId);
		byte keyID;
		if (bytKeyId == null) {
			request.setSwString(Sw1Sw2.SW1SW2_FD17);
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG,
					"get keyID failed! sw:" + request.getSwString());
			return request;
		} else if (bytKeyId.length != 1) {
			request.setSwString(Sw1Sw2.SW1SW2_FD16);
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG,
					"get keyID failed! sw:" + request.getSwString());
			return request;
		} else {
			keyID = bytKeyId[0];
		}

		PbocInitForPurchase pbocInitForPurchase = new PbocInitForPurchase(
				keyID, strAmount, terminalNo, (byte) 0x02);
		rApdu = media.sendApdu(pbocInitForPurchase);
		if (rApdu.getSw1Sw2String().equals(Sw1Sw2.SW1SW2_OK) == false) {
			request.setSwString(rApdu.getSw1Sw2String());
			request.setOK(false);
			ZCLog.i(Longxingcard.TAG,
					"init purchase failed! sw:" + request.getSwString());
			return request;
		}

		strResp = rApdu.getDataHexString();
		if (strResp == null) {
			request.setSwString(Sw1Sw2.SW1SW2_F801);
			request.setOK(false);

			ZCLog.i(Longxingcard.TAG,
					"getdataHexString failed! sw:" + request.getSwString());
			return request;
		}

		String balance = strResp.substring(0, 4 + 4);
		int intBalance = Integer.valueOf(balance, 16);
		balance = String.format("%d", intBalance);

		byte[] bytAmount = null;
		long longAmount = Long.parseLong(strAmount);
		bytAmount = BasicUtil.longToBytes(longAmount);
		String amountString = BasicUtil.bytesToHexString(bytAmount,
				0, bytAmount.length);

		request.setOK(true);
		request.setSwString(Sw1Sw2.SW1SW2_OK);
		request.setIssuerIdString(issuerId);
		request.setAppSerialNoString(appSerialNo);
		request.setPanString(appSerialNo.replaceAll("f|F", ""));
		request.setAmountString(amountString);
		request.setTerminalNoString(terminalNo);
		request.setBalanceString(balance);
		request.setResponseString(strResp);

		ZCLog.i(Longxingcard.TAG, "longxingcard request:" + request.toString());

		return request;
	}

}
