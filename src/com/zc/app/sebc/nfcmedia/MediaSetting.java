package com.zc.app.sebc.nfcmedia;

import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Parcelable;
import android.util.Log;

import com.zc.app.sebc.pboc2.Sw1Sw2;

public class MediaSetting {

	public static String[][] TECHLISTS;															//tech list
	public static IntentFilter[] FILTERS;														//filter
	
	//tech list and filters
	static {
		try {
			TECHLISTS = new String[][] {
					{ IsoDep.class.getName() },
					{ NfcV.class.getName() },
					{ NfcF.class.getName() }};
			
			FILTERS = new IntentFilter[] {
					new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED, "*/*"),
					new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED, "application/vnd.com.example.android.beam")};
			
		} catch (Exception e) {
			Log.i("TECHLISTS|FILTERS", "tech list or filter error");
		}
	}
	
	public static Media media = null;
	
	public static String setNfc(Intent intent) {
		
		if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			
			Parcelable parcelNfc;
			parcelNfc = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			
	    	if(parcelNfc != null) {
	    		
				final Tag tag = (Tag) parcelNfc;
				final IsoDep isoDep = IsoDep.get(tag);
				
				if(isoDep != null) {
					media = new Media((byte)1, parcelNfc);
					return Sw1Sw2.SW1SW2_OK;
				}
	    	}
			return Sw1Sw2.SW1SW2_FF05;
		}
		return Sw1Sw2.SW1SW2_FF04;
	}
}
