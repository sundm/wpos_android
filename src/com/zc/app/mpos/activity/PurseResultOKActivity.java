package com.zc.app.mpos.activity;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;

public class PurseResultOKActivity extends Activity {

	TextView userTextView;
	TextView amountTextView;
	TextView balanceTextView;

	ImageView backImageView;
	private final static String TAG = "purse_result_ok_page";

	// private int second = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_result_ok_page);

		userTextView = (TextView) findViewById(R.id.purse_result_pos_user_txt);
		amountTextView = (TextView) findViewById(R.id.purse_result_ok_amout_txt);
		balanceTextView = (TextView) findViewById(R.id.purse_result_ok_balance_txt);

		String usernameString = getIntent().getStringExtra("username");
		String amountString = getIntent().getStringExtra("amount");
		String balanceString = getIntent().getStringExtra("balance");

		setUser("收款人: " + usernameString);
		setAmount("成功消费: " + amountString + "元");

		BigDecimal v1 = new BigDecimal(String.valueOf(balanceString));
		BigDecimal v2 = new BigDecimal("100");
		String mString = v1.divide(v2, 2, BigDecimal.ROUND_DOWN).toString();

		setBalance("卡内余额: " + mString + "元");

		backImageView = (ImageView) findViewById(R.id.purse_result_ok_back);
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

	private void setAmount(String w) {
		int start_amount = w.indexOf(":") + 1;

		int start = w.indexOf('.');

		int end = w.length() - 1;

		Spannable word = new SpannableString(w);

		word.setSpan(new AbsoluteSizeSpan(80), start, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		word.setSpan(new AbsoluteSizeSpan(120), start_amount, start,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		word.setSpan(new ForegroundColorSpan(0xffcf0005), start_amount, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		amountTextView.setText(word);
	}

	private void setUser(String w) {
		int start_user = w.indexOf(":") + 1;

		int end = w.length();

		Spannable word = new SpannableString(w);

		word.setSpan(new AbsoluteSizeSpan(100), start_user, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		userTextView.setText(word);
	}

	private void setBalance(String w) {
		int start_amount = w.indexOf(":") + 1;

		int start = w.indexOf('.');

		int end = w.length() - 1;

		Spannable word = new SpannableString(w);

		word.setSpan(new AbsoluteSizeSpan(80), start, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		word.setSpan(new AbsoluteSizeSpan(120), start_amount, start,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		word.setSpan(new ForegroundColorSpan(0xff029a38), start_amount, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		balanceTextView.setText(word);
	}
}
