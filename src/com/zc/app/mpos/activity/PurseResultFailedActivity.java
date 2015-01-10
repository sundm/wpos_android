package com.zc.app.mpos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;

public class PurseResultFailedActivity extends Activity {

	TextView resTextView;

	ImageView backImageView;
	// Button sendCheckCodeButton;purse_result_pos_resone_txt
	Button tryAgainButton;

	private final static String TAG = "purse_result_failed_page";

	// private int second = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_result_failed_page);

		resTextView = (TextView) findViewById(R.id.purse_result_pos_resone_txt);
		String resoneString = getIntent().getStringExtra("res");
		resTextView.setText(resoneString);

		tryAgainButton = (Button) findViewById(R.id.purse_again_button);
		tryAgainButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		backImageView = (ImageView) findViewById(R.id.purse_result_failed_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
		NfcEnv.initNfcEnvironment(intent);
	}

	@Override
	public void onResume() {
		super.onResume();

		NfcEnv.enableNfcForegroundDispatch(this);

		Intent intent_finish = new Intent(LoadingActivity.action);
		intent_finish.putExtra("data", 1);
		sendBroadcast(intent_finish);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");

		NfcEnv.disableNfcForegroundDispatch(this);

	}
}
