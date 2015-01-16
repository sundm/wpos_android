package com.zc.app.mpos.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.utils.ZCLog;

public class LoadingActivity extends Activity {

	public static final String action = "loading.broadcast.action";
	private int second = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		// String loadingString = getIntent().getStringExtra("loading_text");
		final Handler handler = new Handler();
		final Runnable longestTime = new Runnable() {
			@Override
			public void run() {
				second++;

				if (second >= 50) {
					second = 0;
					LoadingActivity.this.finish();
				} else {
					handler.postDelayed(this, 100);
				}

			}
		};

		handler.postDelayed(longestTime, 100);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}