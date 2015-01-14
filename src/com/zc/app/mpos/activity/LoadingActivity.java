package com.zc.app.mpos.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.utils.ZCLog;

public class LoadingActivity extends Activity {

	public static final String action = "loading.broadcast.action";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		// String loadingString = getIntent().getStringExtra("loading_text");

	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			ZCLog.i("loading", intent.toString());
			int result = intent.getExtras().getInt("data", 0);
			if (1 == result) {
				LoadingActivity.this.finish();
			}

		}
	};

	@Override
	protected void onResume() {
		ZCLog.i("loading", "onResume");
		NfcEnv.enableNfcForegroundDispatch(this);

		IntentFilter filter = new IntentFilter(action);
		registerReceiver(broadcastReceiver, filter);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		ZCLog.i("loading", "onDestory");
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	};

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
		NfcEnv.initNfcEnvironment(intent);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("loading", "onPause");
		NfcEnv.disableNfcForegroundDispatch(this);
	}

}