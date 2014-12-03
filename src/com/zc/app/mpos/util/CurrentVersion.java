package com.zc.app.mpos.util;

import android.content.Context;

import com.zc.app.mpos.R;
import com.zc.app.utils.ZCLog;


public class CurrentVersion {
	private static final String TAG = "Config";
	public static final String appPackName = "com.zcr";

	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager()
					.getPackageInfo(appPackName, 0).versionCode;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ZCLog.e(TAG, e.getMessage());
		}
		return verCode;
	}

	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager()
					.getPackageInfo(appPackName, 0).versionName;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ZCLog.e(TAG, e.getMessage());
		}
		return verName;
	}

	public static String getAppName(Context context) {
		String appNameString = context.getResources()
				.getText(R.string.app_name).toString();
		return appNameString;
		
	}
}