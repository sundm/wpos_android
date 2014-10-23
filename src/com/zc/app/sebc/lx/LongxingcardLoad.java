package com.zc.app.sebc.lx;

import com.zc.app.sebc.bridge.StatusCode;
import com.zc.app.sebc.bridge.Transaction_Longxing;

public class LongxingcardLoad {

	public static String requestInitCreditForLoad_Longxing(String strAmount, String pin) {
		
		return Transaction_Longxing.requestInitCreditForLoad_Longxing(NfcEnv.media, strAmount, pin, true);
	}
	
	public static RcflResult requestCreditForLoad_Longxing(String appName, String keyId, String terminalNo, 
			String date, String time) {
		
		String[] result;
		result = Transaction_Longxing.requestCreditForLoad_Longxing(appName, keyId, terminalNo, 
			date, time);
		
		RcflResult rcflResult = new RcflResult();
		rcflResult.setStatus(result[0]);
		
		if(result[0].equals(StatusCode.SW1SW2_OK)) {
			
			rcflResult.setCardIssuerId(result[1]);
			rcflResult.setPan(result[2]);
			rcflResult.setKeyIndex(result[3]);
			rcflResult.setTradeAmount(result[4]);
			rcflResult.setTerminalCode(result[5]);
			rcflResult.setBalance(result[6]);
			rcflResult.setOnlineTradeSeq(result[7]);
			rcflResult.setKeyVersion(result[8]);
			rcflResult.setAlgoId(result[9]);
			rcflResult.setRandom(result[10]);
			rcflResult.setStrMAC1(result[11]);
		}
		
		return rcflResult;
	}
	
	public static String performLongxingCreditForLoad(String creditForLoad) {
		
		String loadResult;
		
		loadResult = Transaction_Longxing.performLongxingCreditForLoad(creditForLoad);
		
		return loadResult;
	}
}
