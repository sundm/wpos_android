package com.zc.app.mpos.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class ChangePOSActivity extends Activity {

	EditText storeNumberEditText;
	EditText activeCodeEditText;

	ImageView backImageView;
	Button sendCheckCodeButton;
	Button changePOSButton;

	private final static String TAG = "change_pos_page";
	private String phoneString;
	private int second = 0;
	private String uniqueID;

	private final static int CHANGEPOS = 11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pos_page);

		final Handler handler = new Handler();
		final Runnable sendCheckCodeRunnable = new Runnable() {
			@Override
			public void run() {
				second++;

				sendCheckCodeButton.setEnabled(false);
				sendCheckCodeButton.setText(String.valueOf(60 - second)
						+ "秒后\n重新发送");

				if (second == 60) {
					second = 0;
					sendCheckCodeButton.setEnabled(true);
					sendCheckCodeButton.setText(R.string.getCheckCode);
				} else {
					handler.postDelayed(this, 1000);
				}

			}
		};

		storeNumberEditText = (EditText) findViewById(R.id.change_pos_number_edit);
		activeCodeEditText = (EditText) findViewById(R.id.change_pos_code_edit);

		uniqueID = getIntent().getStringExtra("uniqueID");
		phoneString = getIntent().getStringExtra("phone");

		sendCheckCodeButton = (Button) findViewById(R.id.change_pos_get_code_btn);
		sendCheckCodeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (phoneString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "该用户注册手机号为空",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					handler.postDelayed(sendCheckCodeRunnable, 100);
					ZCWebService.getInstance().getRegisterCode(phoneString,
							new Handler() {
								@Override
								public void dispatchMessage(Message msg) {

									switch (msg.what) {
									case ZCWebServiceParams.HTTP_START:
										ZCLog.i(TAG, msg.obj.toString());
										break;

									case ZCWebServiceParams.HTTP_FINISH:
										ZCLog.i(TAG, msg.obj.toString());
										break;

									case ZCWebServiceParams.HTTP_FAILED:
										ZCLog.i(TAG, msg.obj.toString());
										break;

									case ZCWebServiceParams.HTTP_SUCCESS:
										ZCLog.i(TAG, ">>>>>>>>>>>>>>>>"
												+ msg.obj.toString());
										Toast.makeText(getApplicationContext(),
												"短信已发送，请耐心等待",
												Toast.LENGTH_SHORT).show();

										break;

									case ZCWebServiceParams.HTTP_UNAUTH:
										ZCLog.i(TAG, msg.obj.toString());
										Toast.makeText(getApplicationContext(),
												msg.obj.toString(),
												Toast.LENGTH_LONG).show();
										break;

									case ZCWebServiceParams.HTTP_THROWABLE:
										Throwable e = (Throwable) msg.obj;
										ZCLog.e(TAG, "catch thowable:", e);
										break;

									default:
										ZCLog.i(TAG, "http nothing to do");
										break;
									}
								}
							});
				}
			}
		});

		changePOSButton = (Button) findViewById(R.id.change_submit_button);
		changePOSButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hiddenKeyboard();

				String storeNumber = storeNumberEditText.getText().toString();

				String checkCode = activeCodeEditText.getText().toString();

				if (storeNumber.isEmpty()) {
					Toast.makeText(getApplicationContext(), "商户号不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (checkCode.isEmpty()) {
					Toast.makeText(getApplicationContext(), "激活码不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (storeNumber.length() != 15) {
					Toast.makeText(getApplicationContext(), "商户号长度不为15位",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					WPosInfo info = new WPosInfo();

					info.setTerminalId(storeNumber);
					info.setValidateCode(checkCode);
					info.setFingerprint(uniqueID);

					ZCWebService.getInstance().changePOS(info, new MyHandler());
				}

			}
		});

		backImageView = (ImageView) findViewById(R.id.active_change_pos_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	private void hiddenKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.hideSoftInputFromWindow(ChangePOSActivity.this
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
				break;

			case ZCWebServiceParams.HTTP_FAILED:
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				break;

			case ZCWebServiceParams.HTTP_SUCCESS:
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
				Toast.makeText(getApplicationContext(), "更换成功",
						Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(ChangePOSActivity.this,
						MainActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("storeCode", storeNumberString);
				// bundle.putString("posNumber", posNumberString);
				// intent.putExtras(bundle);

				ChangePOSActivity.this.setResult(CHANGEPOS, intent);
				ChangePOSActivity.this.finish();

				ChangePOSActivity.this.finish();
				break;

			case ZCWebServiceParams.HTTP_UNAUTH:
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				break;

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
}
