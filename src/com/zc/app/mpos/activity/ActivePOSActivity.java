package com.zc.app.mpos.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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
					ZCWebService.getInstance().activePOS(info, new MyHandler());
				}

			}
		});

		backImageView = (ImageView) findViewById(R.id.activePOS_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

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

							Intent intent = new Intent(ActivePOSActivity.this,
									MainActivity.class);

							ActivePOSActivity.this.setResult(RESULT_CANCELED,
									intent);
							ActivePOSActivity.this.finish();
							android.os.Process.killProcess(android.os.Process
									.myPid());
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

				break;

			case ZCWebServiceParams.HTTP_FAILED:
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_LONG).show();
				break;

			case ZCWebServiceParams.HTTP_SUCCESS:
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
				Toast.makeText(getApplicationContext(), "开通成功",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ActivePOSActivity.this,
						MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("storeCode", storeNumberString);
				bundle.putString("posNumber", posNumberString);
				intent.putExtras(bundle);

				ActivePOSActivity.this.setResult(ACTIVEPOS, intent);
				ActivePOSActivity.this.finish();
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
