package com.zc.app.sebc.bridge;

import com.zc.app.sebc.lx.LongxingcardRequest;
import com.zc.app.sebc.lx.SessionTrans_Longxing;
import com.zc.app.sebc.nfcmedia.Media;

public final class Transaction_Longxing {

	public static String[][] getReadableTransactionDetails_Longxing(
			Media media, byte[] appName, byte sfi, String template,
			int headReadId, String[] outerTemplate, String[] deletedType,
			boolean order, boolean deleteInit, byte init) {

		String sw1sw2 = null;
		String[][] result = null;

		if (sfi == (byte) 0x00) {
			result = new String[1][1];
			result[0][0] = StatusCode.SW1SW2_FC61;
			return result;
		}

		try {
			result = SessionTrans_Longxing
					.getReadableTransactionDetails_Longxing(media, appName,
							null, null, null, sfi, template, headReadId,
							outerTemplate, deletedType, order, deleteInit, init);

			sw1sw2 = StatusCode.toOuterStatusCode(result[0][0]);
			if ((sw1sw2.equals(StatusCode.SW1SW2_OK))
					|| (sw1sw2.equals(StatusCode.SW1SW2_OK1))) {
				result[0][0] = sw1sw2;
			} else {
				result = new String[1][1];
				result[0][0] = sw1sw2;
			}

		} catch (NullPointerException nullPointerException1) {
			result = new String[1][1];
			result[0][0] = StatusCode.SW1SW2_FC61;
			return result;
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			result = new String[1][1];
			result[0][0] = StatusCode.SW1SW2_FC61;
			return result;
		}

		return result;
	}

	public static String[] getCardInfomation(Media media, String appName) {

		String sw1sw2 = null;
		String result[] = null;

		try {
			result = SessionTrans_Longxing.getCardInfomation(media, appName);

			sw1sw2 = StatusCode.toOuterStatusCode(result[0]);
			if (sw1sw2.equals(StatusCode.SW1SW2_OK)) {
				result[0] = sw1sw2;
			} else {
				result = new String[1];
				result[0] = sw1sw2;
			}

		} catch (NullPointerException nullPointerException1) {
			result = new String[1];
			result[0] = StatusCode.SW1SW2_FC61;
			return result;
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			result = new String[1];
			result[0] = StatusCode.SW1SW2_FC61;
			return result;
		}

		return result;
	}

	public static String requestInitCreditForLoad_Longxing(Media media,
			String strAmount, String pin, boolean nonZero) {

		String sw1sw2 = null;
		String result = null;

		try {
			result = SessionTrans_Longxing.requestInitCreditForLoad_Longxing(
					media, strAmount, pin, nonZero);

			sw1sw2 = StatusCode.toOuterStatusCode(result);
			if (sw1sw2.equals(StatusCode.SW1SW2_OK)) {
				result = sw1sw2 + result.substring(4, result.length());
			} else {
				result = sw1sw2;
			}

		} catch (NullPointerException nullPointerException1) {
			result = StatusCode.SW1SW2_FC61;
			return result;
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			result = StatusCode.SW1SW2_FC61;
			return result;
		}

		return result;
	}

	public static String[] requestCreditForLoad_Longxing(String appName,
			String keyId, String terminalNo, String date, String time) {

		String sw1sw2 = null;
		String result[] = null;

		try {
			result = SessionTrans_Longxing.requestCreditForLoad_Longxing(
					appName, keyId, terminalNo, date, time);

			sw1sw2 = StatusCode.toOuterStatusCode(result[0]);
			if (sw1sw2.equals(StatusCode.SW1SW2_OK)) {
				result[0] = sw1sw2;
			} else {
				result = new String[1];
				result[0] = sw1sw2;
			}

		} catch (NullPointerException nullPointerException1) {
			result = new String[1];
			result[0] = StatusCode.SW1SW2_FC61;
			return result;
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			result = new String[1];
			result[0] = StatusCode.SW1SW2_FC61;
			return result;
		}

		return result;
	}

	public static String performLongxingCreditForLoad(String creditForLoad) {

		String sw1sw2 = null;
		String result = null;

		try {
			result = SessionTrans_Longxing
					.performLongxingCreditForLoad(creditForLoad);

			sw1sw2 = StatusCode.toOuterStatusCode(result);
			if (sw1sw2.equals(StatusCode.SW1SW2_OK)) {
				result = sw1sw2 + result.substring(4, result.length());
			} else {
				result = sw1sw2;
			}

		} catch (NullPointerException nullPointerException1) {
			result = StatusCode.SW1SW2_FC61;
			return result;
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			result = StatusCode.SW1SW2_FC61;
			return result;
		}

		return result;
	}

	public static LongxingcardRequest requestInitForPurchase_Longxing(
			Media media, String strAmount, String terminalNo, String keyId,
			boolean nonZero) {

		return SessionTrans_Longxing.preInitCreditForPurchase_Longxing(media,
				strAmount, terminalNo, keyId, nonZero);

	}

	public static LongxingcardRequest requestCreditForPurchase_Longxing(
			String dataString) {
		return SessionTrans_Longxing.creditForPurchase_Longxing(dataString);
	}

	public static LongxingcardRequest test_Longxing(Media media) {

		return SessionTrans_Longxing.test(media);

	}
	
	public static LongxingcardRequest purse_Longxing(Media media) {

		return SessionTrans_Longxing.purse(media);

	}
}
