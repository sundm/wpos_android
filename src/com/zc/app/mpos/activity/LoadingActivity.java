package com.zc.app.mpos.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.zc.app.mpos.R;

public class LoadingActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);

		IntentFilter filter = new IntentFilter(LoginPage.action);
		registerReceiver(broadcastReceiver, filter);
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int result = intent.getExtras().getInt("data", 0);
			if (1 == result) {
				LoadingActivity.this.finish();
			}

		}
	};

	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	};
}