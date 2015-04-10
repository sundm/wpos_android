package com.zc.app.mpos.activity;

import java.io.IOException;
import java.util.Calendar;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.sebc.lx.purchaseLogTotal;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class pickDateActivity extends Activity {

	EditText startDateEditText;
	EditText endDateEditText;
	ImageView backImageView;

	Button queryButton;

	private final static String TAG = "pick_date_pag";
	private int query_index = 3;
	private String start;
	private String end;
	private purchaseLogTotal total = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_date_page);

		startDateEditText = (EditText) findViewById(R.id.start_date_edit);
		startDateEditText.setInputType(InputType.TYPE_NULL);
		startDateEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				new DatePickerDialog(pickDateActivity.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// TODO Auto-generated method stub
								startDateEditText.setText(year + "-"
										+ (monthOfYear + 1) + "-" + dayOfMonth);
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH)).show();

			}
		});

		endDateEditText = (EditText) findViewById(R.id.end_date_edit);
		endDateEditText.setInputType(InputType.TYPE_NULL);
		endDateEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();
				new DatePickerDialog(pickDateActivity.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// TODO Auto-generated method stub
								endDateEditText.setText(year + "-"
										+ (monthOfYear + 1) + "-" + dayOfMonth);
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH)).show();

			}
		});

		queryButton = (Button) findViewById(R.id.query_button);
		queryButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				start = startDateEditText.getText().toString();
				end = endDateEditText.getText().toString();
				queryTotal(start, end);
			}
		});

		backImageView = (ImageView) findViewById(R.id.change_password_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.isNeedResume = false;
				finish();
			}
		});

	}

	private void hiddenKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.hideSoftInputFromWindow(pickDateActivity.this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

	private void queryTotal(final String start, final String end) {
		Intent loadingIntent = new Intent();
		loadingIntent.setClass(pickDateActivity.this, LoadingActivity.class);
		startActivity(loadingIntent);

		ZCWebService.getInstance().queryLogTotal(start, end, new MyHandler());
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
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

				ObjectMapper mapper = new ObjectMapper();
				try {
					requestUtil requestObj = mapper.readValue(
							msg.obj.toString(), requestUtil.class);

					ZCLog.i(TAG, requestObj.getDetail().toString());

					String detailString = mapper.writeValueAsString(requestObj
							.getDetail());

					total = mapper.readValue(detailString,
							purchaseLogTotal.class);

					ZCLog.i(TAG, total.toString());

					Intent resultIntent = new Intent(pickDateActivity.this,
							QueryLogResultActivity.class);

					Bundle bundle = new Bundle();
					bundle.putString(QueryLogResultActivity.COUNT,
							total.getCount());

					bundle.putString(QueryLogResultActivity.SUM,
							total.getSumOfAmount());

					bundle.putString(QueryLogResultActivity.START, start);

					bundle.putString(QueryLogResultActivity.END, end);

					bundle.putInt(QueryLogResultActivity.INDEX, query_index);

					resultIntent.putExtras(bundle);
					if (total.getCount().equals("0")) {
						Toast.makeText(getApplicationContext(), "交易日志为空",
								Toast.LENGTH_SHORT).show();
					} else {
						startActivity(resultIntent);
						finish();
					}

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
