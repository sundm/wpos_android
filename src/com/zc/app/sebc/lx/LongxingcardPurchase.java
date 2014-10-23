package com.zc.app.sebc.lx;

import com.zc.app.sebc.bridge.Transaction_Longxing;

public class LongxingcardPurchase {
	public static LongxingcardRequest requestInitCreditForPurchase_Longxing(
			String strAmount, String strKeyID, String strPSAMID) {

		return Transaction_Longxing.requestInitForPurchase_Longxing(
				NfcEnv.media, strAmount, strPSAMID, strKeyID, true);
	}
	
	public static LongxingcardRequest requestCreditForPurche_Longxing(String dataString){
		return Transaction_Longxing.requestCreditForPurchase_Longxing(dataString);
	}
}
