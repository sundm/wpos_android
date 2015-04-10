package com.zc.app.mpos.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.sebc.lx.purchaseLogTotal;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

@SuppressLint("ClickableViewAccessibility")
public class QueryLogListActivity extends Activity {

	ImageView backImageView;

	private final static String TAG = "query_log_list_page";

	private int query_index = 0;
	private String start;
	private String end;

	private purchaseLogTotal total = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_list_page);

		RelativeLayout today_layout = (RelativeLayout) findViewById(R.id.today_layout);
		today_layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				query_index = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				start = sdf.format(new java.util.Date());
				end = sdf.format(new java.util.Date());
				queryTotal(start, end);
				return false;
			}
		});

		RelativeLayout month_layout = (RelativeLayout) findViewById(R.id.month_layout);
		month_layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				query_index = 1;

				getFirstday_Lastday_Month(new java.util.Date());
				// System.out.println("一个月前第一天：" + map.get("first"));
				// System.out.println("一个月前最后一天：" + map.get("last"));
				// System.out.println("当月第一天：" + getFirstDay());
				// System.out.println("当月最后一天：" + getLastDay());

				start = getFirstDay();
				end = getLastDay();
				queryTotal(start, end);

				return false;
			}
		});

		RelativeLayout next_layout = (RelativeLayout) findViewById(R.id.next_month_layout);
		next_layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				query_index = 2;
				Map<String, String> map = getFirstday_Lastday_Month(new java.util.Date());
				// System.out.println("一个月前第一天：" + map.get("first"));
				// System.out.println("一个月前最后一天：" + map.get("last"));
				start = map.get("first");
				end = map.get("last");
				queryTotal(start, end);
				return false;
			}
		});

		RelativeLayout user_layout = (RelativeLayout) findViewById(R.id.user_layout);
		user_layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				Intent pickIntent = new Intent(QueryLogListActivity.this,
						pickDateActivity.class);

				startActivity(pickIntent);

				return false;
			}
		});

		backImageView = (ImageView) findViewById(R.id.query_list_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.isNeedResume = false;
				finish();
			}
		});
	}

	private void queryTotal(final String start, final String end) {
		Intent loadingIntent = new Intent();
		loadingIntent
				.setClass(QueryLogListActivity.this, LoadingActivity.class);
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

					Intent resultIntent = new Intent(QueryLogListActivity.this,
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MainActivity.isNeedResume = false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 某一个月第一天和最后一天
	 * 
	 * @param date
	 * @return
	 */
	private static Map<String, String> getFirstday_Lastday_Month(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		Date theDate = calendar.getTime();

		// 上个月第一天
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);
		day_first = str.toString();

		// 上个月最后一天
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		String day_last = df.format(calendar.getTime());
		StringBuffer endStr = new StringBuffer().append(day_last);
		day_last = endStr.toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("first", day_first);
		map.put("last", day_last);
		return map;
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	private static String getFirstDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);
		return str.toString();

	}

	/**
	 * 当月最后一天
	 * 
	 * @return
	 */
	private static String getLastDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		String s = df.format(theDate);
		StringBuffer str = new StringBuffer().append(s);
		return str.toString();

	}
}
