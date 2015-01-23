package com.zc.app.mpos.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.utils.WPosInfo;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;

public class ActivePOSActivity extends Activity {

	private EditText storeNumberEditText;
	private EditText posNumberEditText;

	private ImageView backImageView;
	private Button activePOSButton;

	private final static String TAG = "active_pos";

	private final static int ACTIVEPOS = 12;

	private String storeNumberString;
	private String posNumberString;
	private String uniqueID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_pos_page);

		storeNumberEditText = (EditText) findViewById(R.id.activePOS_StoreNumber_edit);

		posNumberEditText = (EditText) findViewById(R.id.activePOS_POSNumber_edit);

		uniqueID = getIntent().getStringExtra("uniqueID");

		activePOSButton = (Button) findViewById(R.id.activePOS_active_button);
		activePOSButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hiddenKeyboard();

				storeNumberString = storeNumberEditText.getText().toString();

				posNumberString = posNumberEditText.getText().toString();

				if (storeNumberString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "商户号不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (posNumberString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "终端号不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (storeNumberString.length() < 15) {
					Toast.makeText(getApplicationContext(), "商户号小于15位",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (posNumberString.length() < 8) {
					Toast.makeText(getApplicationContext(), "终端号小于8位",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (uniqueID == null || uniqueID.isEmpty()) {
					Toast.makeText(getApplicationContext(), "手机指印获取失败",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					WPosInfo info = new WPosInfo();

					info.setTerminalId(posNumberString);
					info.setMerchantId(storeNumberString);
					info.setFingerprint(uniqueID);

					Intent loadingIntent = new Intent();
					loadingIntent.setClass(ActivePOSActivity.this,
							LoadingActivity.class);
					startActivity(loadingIntent);

					ZCWebService.getInstance().activePOS(info, new MyHandler());
				}

			}
		});

		backImageView = (ImageView) findViewById(R.id.activePOS_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.isNeedResume = false;
				finish();
			}
		});

		setupUI(findViewById(R.id.root_layout));
	}

	private void hiddenKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.hideSoftInputFromWindow(ActivePOSActivity.this
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	class MyHandler extends Handler {
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case ZCWebServiceParams.HTTP_START:
				ZCLog.i(TAG, msg.obj.toString());
				break;

			case ZCWebServiceParams.HTTP_FINISH:
				ZCLog.i(TAG, msg.obj.toString());
				Intent intent_finish = new Intent(LoadingActivity.action);
				intent_finish.putExtra("data", 1);
				sendBroadcast(intent_finish);
				break;

			case ZCWebServiceParams.HTTP_FAILED:
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				break;

			case ZCWebServiceParams.HTTP_SUCCESS: {
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
				Toast.makeText(getApplicationContext(), "开通成功",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ActivePOSActivity.this,
						MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("storeCode", storeNumberString);
				bundle.putString("posNumber", posNumberString);
				bundle.putBoolean("unAuth", false);
				intent.putExtras(bundle);

				ActivePOSActivity.this.setResult(ACTIVEPOS, intent);
				ActivePOSActivity.this.finish();
				break;
			}
			case ZCWebServiceParams.HTTP_UNAUTH: {
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ActivePOSActivity.this,
						MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("unAuth", true);
				intent.putExtras(bundle);

				ActivePOSActivity.this.setResult(ACTIVEPOS, intent);
				ActivePOSActivity.this.finish();
				break;
			}
			case ZCWebServiceParams.HTTP_THROWABLE:
				Throwable e = (Throwable) msg.obj;
				ZCLog.e(TAG, "catch thowable:", e);
				break;

			default:
				ZCLog.i(TAG, "http nothing to do");
				break;
			}
		}
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
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		NfcEnv.disableNfcForegroundDispatch(this);
	}

	private void setupUI(View view) {
		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {
			view.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					hiddenKeyboard();
					return false;
				}
			});
		}

		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);
				setupUI(innerView);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MainActivity.isNeedResume = false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
