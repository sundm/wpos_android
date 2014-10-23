package com.zc.app.sebc.lx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zc.app.sebc.bridge.StatusCode;
import com.zc.app.sebc.nfcmedia.MediaSetting;
import com.zc.app.sebc.nfcmedia.nfc.NfcUtil;

public class NfcEnv extends MediaSetting {
	
	public static boolean isNfcSupported(Context context) {
		
		String statusCode = NfcUtil.checkNfcStatus(context);
		statusCode = StatusCode.toOuterStatusCode(statusCode);
		
		if(statusCode.equals(StatusCode.SW1SW2_FF02)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isNfcEnabled(Context context) {
		
		String statusCode = NfcUtil.checkNfcStatus(context);
		statusCode = StatusCode.toOuterStatusCode(statusCode);
		
		if(statusCode.equals(StatusCode.SW1SW2_FF02)) {
			return false;
		} else {
			if(statusCode.equals(StatusCode.SW1SW2_FF03)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public static String initNfcEnvironment(Intent intent) {
		
		String statusCode = setNfc(intent);
		statusCode = StatusCode.toOuterStatusCode(statusCode);
		return statusCode;
	}
	
	public static void disableNfcForegroundDispatch(Activity activity) {
		NfcUtil.disableForegroundDispatch(activity);
	}
	
	public static void enableNfcForegroundDispatch(Activity activity) {
		NfcUtil.enableForegroundDispatch(activity);
	}
	
	public static void showNfcSetting(Context context) {
		NfcUtil.openNfcSetting(context);
	}
	
	public static void showNfcSetting(Fragment fragment) {
		NfcUtil.openNfcSetting(fragment);
	}
}
