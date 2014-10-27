package com.zc.app.mpos.activity;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.zc.app.bootstrap.BootstrapButton;
import com.zc.app.bootstrap.BootstrapEditText;
import com.zc.app.mpos.R;
import com.zc.app.utils.UserInfo;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class RegisterPage extends Activity {

	BootstrapEditText userNameBootstrapEditText;
	BootstrapEditText userPasswordBootstrapEditText;
	BootstrapEditText userPasswordAgainBootstrapEditText;
	BootstrapEditText userNickNameBootstrapEditText;
	BootstrapEditText userPhoneBootstrapEditText;

	BootstrapButton registerBootstrapButton;

	private String username;

	private final static String TAG = "registerPage";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_page);

		userNameBootstrapEditText = (BootstrapEditText) findViewById(R.id.regsiter_username_edit);

		userPasswordBootstrapEditText = (BootstrapEditText) findViewById(R.id.regsiter_password_edit);

		userPasswordAgainBootstrapEditText = (BootstrapEditText) findViewById(R.id.regsiter_password_agine_edit);
		userPasswordAgainBootstrapEditText.setDanger();
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
						if (userPasswordBootstrapEditText.getText().toString()
								.equals(s.toString())) {
							userPasswordAgainBootstrapEditText.setSuccess();
						} else {
							userPasswordAgainBootstrapEditText.setDanger();
						}
					}
				});

		userNickNameBootstrapEditText = (BootstrapEditText) findViewById(R.id.regsiter_nickname_edit);

		userPhoneBootstrapEditText = (BootstrapEditText) findViewById(R.id.regsiter_phone_edit);
		userPhoneBootstrapEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

		registerBootstrapButton = (BootstrapButton) findViewById(R.id.regsiter_button);
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
				String nickNamesString = userNickNameBootstrapEditText
						.getText().toString();
				String phoneString = userPhoneBootstrapEditText.getText()
						.toString();

				if (username.isEmpty()) {
					Toast.makeText(getApplicationContext(), "用户名不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (passwordString.isEmpty()
						|| passwordAgainString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "密码不能为空",
							Toast.LENGTH_LONG).show();
					return;
				} else if (!passwordString.equals(passwordAgainString)) {
					Toast.makeText(getApplicationContext(), "密码不一致",
							Toast.LENGTH_LONG).show();
					return;
				} else if (nickNamesString.isEmpty()) {
					Toast.makeText(getApplicationContext(), "昵称不能为空",
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
					info.setNickname(nickNamesString);
					info.setPhonenumber(phoneString);
					ZCWebService.getInstance().register(info, new MyHandler());
				}

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

					ZCLog.i(TAG, requestObj.getDetail().toString());

					Intent intent = new Intent();
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
