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
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.utils.UserInfo;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class RegisterPage extends Activity {

	EditText userNameBootstrapEditText;
	EditText userPasswordBootstrapEditText;
	EditText userPasswordAgainBootstrapEditText;
	EditText validateCodeEditText;
	EditText userPhoneBootstrapEditText;

	ImageView usernameImageView;
	ImageView passwordImageView;

	ImageView backImageView;
	Button sendCheckCodeButton;
	Button registerBootstrapButton;

	private String username;

	private final static String TAG = "registerPage";

	private int second = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_page);

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

		userNameBootstrapEditText = (EditText) findViewById(R.id.regsiter_username_edit);
		usernameImageView = (ImageView) findViewById(R.id.register_username_img);
		usernameImageView.setImageDrawable(null);
		userNameBootstrapEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus) {
							usernameImageView.setImageDrawable(null);
						} else {
							String usernameString = userNameBootstrapEditText
									.getText().toString();
							if (usernameString.isEmpty()
									|| usernameString.length() < 4
									|| usernameString.length() > 16) {
								usernameImageView
										.setImageResource(R.drawable.fail);
							} else {

								ZCWebService.getInstance().isExistUserName(
										usernameString, new Handler() {
											@Override
											public void dispatchMessage(
													Message msg) {

												switch (msg.what) {
												case ZCWebServiceParams.HTTP_START:
													ZCLog.i(TAG,
															msg.obj.toString());
													break;

												case ZCWebServiceParams.HTTP_FINISH:
													ZCLog.i(TAG,
															msg.obj.toString());
													break;

												case ZCWebServiceParams.HTTP_FAILED:
													ZCLog.i(TAG,
															msg.obj.toString());
													usernameImageView
															.setImageResource(R.drawable.ok);
													break;

												case ZCWebServiceParams.HTTP_SUCCESS:
													ZCLog.i(TAG,
															">>>>>>>>>>>>>>>>"
																	+ msg.obj
																			.toString());
													Toast.makeText(
															getApplicationContext(),
															"用户名已存在",
															Toast.LENGTH_SHORT)
															.show();

													usernameImageView
															.setImageResource(R.drawable.fail);
													break;

												case ZCWebServiceParams.HTTP_UNAUTH:
													ZCLog.i(TAG,
															msg.obj.toString());
													Toast.makeText(
															getApplicationContext(),
															msg.obj.toString(),
															Toast.LENGTH_LONG)
															.show();
													break;

												case ZCWebServiceParams.HTTP_THROWABLE:
													Throwable e = (Throwable) msg.obj;
													ZCLog.e(TAG,
															"catch thowable:",
															e);
													break;

												default:
													ZCLog.i(TAG,
															"http nothing to do");
													break;
												}
											}
										});

							}

						}
					}
				});

		userPasswordBootstrapEditText = (EditText) findViewById(R.id.regsiter_password_edit);

		userPasswordAgainBootstrapEditText = (EditText) findViewById(R.id.regsiter_password_agine_edit);

		passwordImageView = (ImageView) findViewById(R.id.register_password_again_img);
		passwordImageView.setImageDrawable(null);

		userPasswordAgainBootstrapEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus) {
							passwordImageView.setImageDrawable(null);
						} else {
							String oldPasswordString = userPasswordBootstrapEditText
									.getText().toString();
							String newPasswordString = userPasswordAgainBootstrapEditText
									.getText().toString();

							if (!oldPasswordString.isEmpty()
									&& oldPasswordString.length() > 5
									&& oldPasswordString.length() < 15
									&& oldPasswordString
											.equals(newPasswordString)) {
								passwordImageView
										.setImageResource(R.drawable.ok);
							} else {
								passwordImageView
										.setImageResource(R.drawable.fail);
							}

						}
					}
				});

		userPhoneBootstrapEditText = (EditText) findViewById(R.id.regsiter_phone_edit);
		userPhoneBootstrapEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

		validateCodeEditText = (EditText) findViewById(R.id.regsiter_check_edit);
		validateCodeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

		sendCheckCodeButton = (Button) findViewById(R.id.register_phone_btn);
		sendCheckCodeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String phoneString = userPhoneBootstrapEditText.getText()
						.toString();
				if (phoneString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "手机号不能为空",
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
										Toast.makeText(getApplicationContext(),
												msg.obj.toString(),
												Toast.LENGTH_SHORT).show();
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

		registerBootstrapButton = (Button) findViewById(R.id.regsiter_button);
		registerBootstrapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hiddenKeyboard();
				username = userNameBootstrapEditText.getText().toString();
				String passwordString = userPasswordBootstrapEditText.getText()
						.toString();
				String passwordAgainString = userPasswordAgainBootstrapEditText
						.getText().toString();
				String validateCode = validateCodeEditText.getText().toString();
				String phoneString = userPhoneBootstrapEditText.getText()
						.toString();

				if (username.isEmpty()) {
					Toast.makeText(getApplicationContext(), "用户名不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (username.length() < 4 || username.length() > 16) {
					Toast.makeText(getApplicationContext(), "用户名4-16位之间",
							Toast.LENGTH_LONG).show();
					return;
				} else if (passwordString.isEmpty()
						|| passwordAgainString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
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
				} else if (validateCode.isEmpty()) {
					Toast.makeText(getApplicationContext(), "验证码不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (phoneString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "手机号不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					UserInfo info = new UserInfo();
					info.setUsername(username);
					info.setPassword(passwordString);
					info.setValidateCode(validateCode);
					info.setPhonenumber(phoneString);
					ZCWebService.getInstance().register(info, new MyHandler());
				}

			}
		});

		backImageView = (ImageView) findViewById(R.id.register_back);
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
			imm.hideSoftInputFromWindow(RegisterPage.this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
				Toast.makeText(getApplicationContext(), "注册成功",
						Toast.LENGTH_LONG).show();
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

				// SharedPreferences sharedPreferences = getApplicationContext()
				// .getSharedPreferences("configer", Context.MODE_PRIVATE);
				// // 编辑配置
				// Editor editor = sharedPreferences.edit();// 获取编辑器
				// editor.putString("_user_", username);
				// editor.commit();// 提交修改

				ObjectMapper mapper = new ObjectMapper();
				try {
					requestUtil requestObj = mapper.readValue(
							msg.obj.toString(), requestUtil.class);

					ZCLog.i(TAG, requestObj.getDetail().toString());

					Intent intent = new Intent(RegisterPage.this,
							LoginPage.class);
					Bundle bundle = new Bundle();
					bundle.putString("user", username);
					intent.putExtras(bundle);

					RegisterPage.this.setResult(RESULT_OK, intent);
					RegisterPage.this.finish();

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
}
