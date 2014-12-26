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
import android.view.View;
import android.view.View.OnClickListener;
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

	ImageView passwordImageView;

	ImageView backImageView;
	// Button sendCheckCodeButton;
	Button changePwdButton;

	private final static String TAG = "change_pwd_page";

	// private int second = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd_page);

		// final Handler handler = new Handler();
		// final Runnable sendCheckCodeRunnable = new Runnable() {
		// @Override
		// public void run() {
		// second++;
		//
		// sendCheckCodeButton.setEnabled(false);
		// sendCheckCodeButton.setText(String.valueOf(60 - second)
		// + "秒后\n重新发送");
		//
		// if (second == 60) {
		// second = 0;
		// sendCheckCodeButton.setEnabled(true);
		// sendCheckCodeButton.setText(R.string.getCheckCode);
		// } else {
		// handler.postDelayed(this, 1000);
		// }
		//
		// }
		// };

		userOldPasswordBootstrapEditText = (EditText) findViewById(R.id.changePwd_old_password_edit);

		userNewPasswordBootstrapEditText = (EditText) findViewById(R.id.changePwd_password_edit);

		userPasswordAgainBootstrapEditText = (EditText) findViewById(R.id.changePwd_password_agine_edit);

		passwordImageView = (ImageView) findViewById(R.id.changePwd_password_again_img);
		passwordImageView.setImageDrawable(null);

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
							passwordImageView.setImageDrawable(null);
						} else if (userNewPasswordBootstrapEditText
								.getText()
								.toString()
								.equals(userPasswordAgainBootstrapEditText
										.getText().toString())) {
							if (5 < userNewPasswordBootstrapEditText.getText()
									.toString().length()
									&& userNewPasswordBootstrapEditText
											.getText().toString().length() < 15) {
								passwordImageView
										.setImageResource(R.drawable.ok);
							} else {
								passwordImageView
										.setImageResource(R.drawable.fail);
							}
						} else {
							passwordImageView.setImageResource(R.drawable.fail);
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
							LoginPage.class);

					startActivity(intent);
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
