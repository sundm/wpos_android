package com.zc.app.mpos.activity;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.TextView;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.utils.MircoPOState;
import com.zc.app.utils.UserInfo;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class LoginPage extends Activity {

	EditText userNameBootstrapEditText;
	EditText userPasswordBootstrapEditText;

	Button signInBootstrapButton;

	TextView registerTextView;

	ImageView userImageView;
	ImageView passwordImageView;

	private String username;

	private final static String TAG = "loginPage";

	private String userNameRegex = "[a-zA-Z0-9_]{4,16}";
	private String passwordRegex = "[a-zA-Z0-9_]{6,14}";

	private MircoPOState state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);

		state = new MircoPOState(this.getApplicationContext());

		userNameBootstrapEditText = (EditText) findViewById(R.id.login_username_edit);

		passwordImageView = (ImageView) findViewById(R.id.login_password_txt);
		userImageView = (ImageView) findViewById(R.id.login_username_txt);

		SharedPreferences sharedPreferences = getApplicationContext()
				.getSharedPreferences("configer", Context.MODE_PRIVATE);
		String name = sharedPreferences.getString("_user_", "");

		userNameBootstrapEditText.setText(name);

		userPasswordBootstrapEditText = (EditText) findViewById(R.id.login_password_edit);

		userNameBootstrapEditText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				passwordImageView.setImageResource(R.drawable.login_password);

				userImageView
						.setImageResource(R.drawable.login_username_highlight);
				return false;
			}
		});

		userPasswordBootstrapEditText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				userImageView.setImageResource(R.drawable.login_username);

				passwordImageView
						.setImageResource(R.drawable.login_password_highlight);
				return false;
			}
		});

		signInBootstrapButton = (Button) findViewById(R.id.signin_button);

		signInBootstrapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hiddenKeyboard();

				username = userNameBootstrapEditText.getText().toString();
				String password = userPasswordBootstrapEditText.getText()
						.toString();

				if (username.isEmpty() || password.isEmpty()) {
					Toast.makeText(getApplicationContext(), "用户名或密码为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (!checkUserName(username)) {
					Toast.makeText(getApplicationContext(), "用户名格式错误",
							Toast.LENGTH_LONG).show();
					return;
				} else if (!checkPassword(password)) {
					Toast.makeText(getApplicationContext(), "密码格式错误",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					ZCLog.i(TAG, "longin and get info");
					UserInfo info = new UserInfo();
					info.setUsername(username);
					info.setPassword(password);

					String fingerprint = state.getUniqueIDString();

					Intent loadingIntent = new Intent();
					loadingIntent.setClass(LoginPage.this,
							LoadingActivity.class);
					startActivity(loadingIntent);

					ZCWebService.getInstance().userLogin(info, fingerprint,
							new MyHandler());
				}

			}
		});

		registerTextView = (TextView) findViewById(R.id.register_link);

		registerTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginPage.this, RegisterPage.class);

				username = userNameBootstrapEditText.getText().toString();
				intent.putExtra("user", username);

				startActivityForResult(intent, RESULT_OK);
			}
		});

		setupUI(findViewById(R.id.root_layout));

		if (!NfcEnv.isNfcEnabled(getApplicationContext())) {
			notEnable();
		}

		if (!NfcEnv.isNfcSupported(getApplicationContext())) {
			// Toast.makeText(getApplicationContext(), "该设备不支持NFC硬件",
			// Toast.LENGTH_SHORT).show();
			notSupport();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK: {
			// register的返回数据
			Bundle MarsBuddle = data.getExtras();
			String username = MarsBuddle.getString("user");
			userNameBootstrapEditText.setText(username);
			ZCLog.i(TAG, "result: " + username);
			break;
		}
		default: {
			ZCLog.i(TAG, "result:null");
			break;
		}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		try {
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("是否退出程序");
			builder.setTitle("确认");

			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
							System.exit(0);
						}
					});
			builder.setNegativeButton("取消", null);
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void hiddenKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.hideSoftInputFromWindow(LoginPage.this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	class MyHandler extends Handler {

		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case ZCWebServiceParams.HTTP_START: {
				ZCLog.i(TAG, msg.obj.toString());

				break;
			}
			case ZCWebServiceParams.HTTP_FINISH: {
				ZCLog.i(TAG, msg.obj.toString());
				Intent intent_finish = new Intent(LoadingActivity.action);
				intent_finish.putExtra("data", 1);
				sendBroadcast(intent_finish);
				break;
			}
			case ZCWebServiceParams.HTTP_FAILED: {
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_LONG).show();

				break;
			}
			case ZCWebServiceParams.HTTP_SUCCESS: {
				Toast.makeText(getApplicationContext(), "登录成功",
						Toast.LENGTH_SHORT).show();
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

				SharedPreferences sharedPreferences = getApplicationContext()
						.getSharedPreferences("configer", Context.MODE_PRIVATE);
				// 编辑配置
				Editor editor = sharedPreferences.edit();// 获取编辑器
				editor.putString("_user_", username);
				editor.commit();// 提交修改

				ObjectMapper mapper = new ObjectMapper();
				try {
					requestUtil requestObj = mapper.readValue(
							msg.obj.toString(), requestUtil.class);

					Intent mainIntent = new Intent(LoginPage.this,
							MainActivity.class);

					Bundle bundle = new Bundle();
					bundle.putString(MainActivity.USER_INFO_STATE,
							mapper.writeValueAsString(requestObj.getDetail()));

					MainActivity.isFromLogin = true;
					mainIntent.putExtras(bundle);

					startActivity(mainIntent);
					LoginPage.this.finish();

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
			}
			case ZCWebServiceParams.HTTP_UNAUTH: {
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				break;
			}
			case ZCWebServiceParams.HTTP_THROWABLE: {
				Throwable e = (Throwable) msg.obj;
				ZCLog.e(TAG, "catch thowable:", e);

				break;
			}
			default: {
				ZCLog.i(TAG, "http nothing to do");
				break;
			}
			}
		}
	}

	private boolean checkUserName(String s) {
		return s.matches(userNameRegex);
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

	public void notSupport() {
		try {
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("该设备不支持NFC，请退出程序");
			builder.setTitle("确认");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
							java.lang.System.exit(0);
						}
					});
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void notEnable() {
		try {
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("NFC未打开，进入设置界面");
			builder.setTitle("确认");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							NfcEnv.showNfcSetting(getApplicationContext());
						}
					});
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
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
