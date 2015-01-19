package com.zc.app.mpos.activity;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.utils.UserInfo;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class ChangePwdActivity extends Activity {

	EditText userOldPasswordBootstrapEditText;
	EditText userNewPasswordBootstrapEditText;
	EditText userPasswordAgainBootstrapEditText;

	ImageView passwordNewImageView;
	ImageView passwordAgainImageView;

	ImageView backImageView;
	// Button sendCheckCodeButton;
	Button changePwdButton;

	private final static String TAG = "change_pwd_page";

	private final static int CHANGEPWD = 10;

	private String passwordRegex = "[a-zA-Z0-9_]{6,14}";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd_page);

		userOldPasswordBootstrapEditText = (EditText) findViewById(R.id.changePwd_old_password_edit);
		userOldPasswordBootstrapEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (!hasFocus) {

							String passwordString = userOldPasswordBootstrapEditText
									.getText().toString();
							if (!checkPassword(passwordString)) {

								Toast.makeText(getApplicationContext(),
										"密码格式错误", Toast.LENGTH_SHORT).show();
							}
						}
					}
				});
		userNewPasswordBootstrapEditText = (EditText) findViewById(R.id.changePwd_password_edit);
		passwordNewImageView = (ImageView) findViewById(R.id.changePwd_password_img);
		passwordNewImageView.setImageDrawable(null);
		userNewPasswordBootstrapEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus) {
							passwordNewImageView.setImageDrawable(null);
						} else {
							String passwordString = userNewPasswordBootstrapEditText
									.getText().toString();
							if (checkPassword(passwordString)) {
								String passwordAgainString = userPasswordAgainBootstrapEditText
										.getText().toString();

								passwordNewImageView
										.setImageResource(R.drawable.ok);

								if (passwordString.equals(passwordAgainString)) {
									passwordAgainImageView
											.setImageResource(R.drawable.ok);
								} else {
									passwordAgainImageView
											.setImageResource(R.drawable.fail);
								}
							} else {
								passwordNewImageView
										.setImageResource(R.drawable.fail);
								passwordAgainImageView.setImageDrawable(null);
								Toast.makeText(getApplicationContext(),
										"密码格式错误", Toast.LENGTH_SHORT).show();
							}
						}
					}
				});

		userPasswordAgainBootstrapEditText = (EditText) findViewById(R.id.changePwd_password_agine_edit);
		passwordAgainImageView = (ImageView) findViewById(R.id.changePwd_password_again_img);
		passwordAgainImageView.setImageDrawable(null);

		userPasswordAgainBootstrapEditText
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						if (userNewPasswordBootstrapEditText.getText()
								.toString().isEmpty()) {
							passwordAgainImageView.setImageDrawable(null);
						} else if (userNewPasswordBootstrapEditText
								.getText()
								.toString()
								.equals(userPasswordAgainBootstrapEditText
										.getText().toString())) {
							passwordAgainImageView
									.setImageResource(R.drawable.ok);
						} else {
							passwordAgainImageView
									.setImageResource(R.drawable.fail);
						}

					}
				});

		// sendCheckCodeButton = (Button) findViewById(R.id.register_phone_btn);
		// sendCheckCodeButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// handler.postDelayed(sendCheckCodeRunnable, 100);
		// }
		// });

		changePwdButton = (Button) findViewById(R.id.changePwd_button);
		changePwdButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hiddenKeyboard();

				String oldPassword = userOldPasswordBootstrapEditText.getText()
						.toString();

				String passwordString = userNewPasswordBootstrapEditText
						.getText().toString();
				String passwordAgainString = userPasswordAgainBootstrapEditText
						.getText().toString();

				if (oldPassword.isEmpty()) {
					Toast.makeText(getApplicationContext(), "旧密码不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (passwordString.isEmpty()
						|| passwordAgainString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "新密码不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (passwordString.length() < 6
						|| passwordString.length() > 14
						|| passwordAgainString.length() < 6
						|| passwordAgainString.length() > 14) {
					Toast.makeText(getApplicationContext(), "密码6-14位之间",
							Toast.LENGTH_LONG).show();
					return;
				} else if (!passwordString.equals(passwordAgainString)) {
					Toast.makeText(getApplicationContext(), "密码不一致",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					Intent loadingIntent = new Intent();
					loadingIntent.setClass(ChangePwdActivity.this,
							LoadingActivity.class);
					startActivity(loadingIntent);

					UserInfo info = new UserInfo();
					info.setOldpassword(oldPassword);
					info.setNewpassword(passwordString);
					ZCWebService.getInstance().changePassword(info,
							new MyHandler());
				}

			}
		});

		backImageView = (ImageView) findViewById(R.id.change_password_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
			imm.hideSoftInputFromWindow(ChangePwdActivity.this
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

			case ZCWebServiceParams.HTTP_SUCCESS:
				Toast.makeText(getApplicationContext(), "密码修改成功,请重新登录",
						Toast.LENGTH_LONG).show();
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

				ObjectMapper mapper = new ObjectMapper();
				try {
					requestUtil requestObj = mapper.readValue(
							msg.obj.toString(), requestUtil.class);

					ZCLog.i(TAG, requestObj.getDetail().toString());
					Intent intent = new Intent(ChangePwdActivity.this,
							MainActivity.class);

					ChangePwdActivity.this.setResult(CHANGEPWD, intent);
					ChangePwdActivity.this.finish();

				} catch (JsonParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				break;

			case ZCWebServiceParams.HTTP_UNAUTH:
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ChangePwdActivity.this,
						MainActivity.class);

				ChangePwdActivity.this.setResult(CHANGEPWD, intent);
				ChangePwdActivity.this.finish();
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

		// Intent intent_finish = new Intent("closeDL.broadcast.action");
		// intent_finish.putExtra("data", 1);
		// sendBroadcast(intent_finish);

	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");

		NfcEnv.disableNfcForegroundDispatch(this);

	}

	private boolean checkPassword(String s) {
		return s.matches(passwordRegex);
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
}
