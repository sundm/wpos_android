package com.zc.app.sebc.nfcmedia.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v4.app.Fragment;

import com.zc.app.sebc.nfcmedia.MediaSetting;
import com.zc.app.sebc.pboc2.Sw1Sw2;

public final class NfcUtil {

	public static String checkNfcStatus(Context context) {

		NfcAdapter nfcAdapter;
		nfcAdapter = NfcAdapter.getDefaultAdapter(context);

		if (nfcAdapter == null) {
			return Sw1Sw2.SW1SW2_FF02;
		} else {
			if (nfcAdapter.isEnabled() == false) {
				return Sw1Sw2.SW1SW2_FF03;
			}
		}
		return Sw1Sw2.SW1SW2_OK;
	}

	public static void disableForegroundDispatch(Activity activity) {

		NfcAdapter nfcAdapter;
		nfcAdapter = NfcAdapter.getDefaultAdapter(activity);

		if (nfcAdapter != null) {
			nfcAdapter.disableForegroundDispatch(activity);
		}
	}

	public static void enableForegroundDispatch(Activity activity) {

		PendingIntent pendingIntent;
		pendingIntent = PendingIntent.getActivity(activity, 0, new Intent(
				activity, activity.getClass())
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		NfcAdapter nfcAdapter;
		nfcAdapter = NfcAdapter.getDefaultAdapter(activity);

		if (nfcAdapter != null) {
			nfcAdapter.enableForegroundDispatch(activity, pendingIntent,
					MediaSetting.FILTERS, MediaSetting.TECHLISTS);
		}
	}

	public static void openNfcSetting(Context context) {
		Intent it = new Intent("android.settings.NFC_SETTINGS");
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);
	}

	public static void openNfcSetting(Fragment fragment) {
		fragment.startActivity(new Intent("android.settings.NFC_SETTINGS"));
	}
}
