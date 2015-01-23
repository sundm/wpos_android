package com.zc.app.mpos.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.mpos.adapter.LogDateItem;
import com.zc.app.mpos.adapter.LogItem;
import com.zc.app.mpos.adapter.TimeAxisAdapter;
import com.zc.app.mpos.view.EmptyLayout;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.Mode;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.OnRefreshListener2;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshListView;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.sebc.lx.PurchaseLog;
import com.zc.app.sebc.lx.PurchaseLogPage;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class QueryLogOnlineActivity extends Activity {

	List<HashMap<String, Object>> logList = new ArrayList<HashMap<String, Object>>();

	/**
	 * 上拉刷新的控件
	 */
	private PullToRefreshListView mPullRefreshListView;

	private TimeAxisAdapter mAdapter;

	private List<PurchaseLogPage> listPages = new ArrayList<PurchaseLogPage>();
	private PurchaseLogPage logPage;

	private EmptyLayout mEmptyLayout;

	private String dateString;

	private final static String TAG = "log_page";

	private ImageView backImageView;

	private final static int QUERYLOG = 14;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online_log_page);

		backImageView = (ImageView) findViewById(R.id.log_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.isNeedResume = false;
				finish();
			}
		});

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.online_log_list);

		mEmptyLayout = new EmptyLayout(this, mPullRefreshListView);

		// 设置你需要的模式可选值为：disabled,pullFromStart,PULL_FROM_END,both,manualOnly
		mPullRefreshListView.setMode(Mode.BOTH);

		// 设置适配器
		mAdapter = new TimeAxisAdapter(this, logList);

		mPullRefreshListView.setAdapter(mAdapter);
		// 设置监听事件
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// 显示最后更新的时间
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// 加载任务
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						String date = sdf.format(new java.util.Date());
						getInitLog(date);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// 显示最后更新的时间
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// 加载任务
						if (dateString != null) {
							String date = addDate(dateString);
							getMoreLog(date);
						} else {
							mPullRefreshListView.onRefreshComplete();
						}

						// new GetDataTask().execute();
					}

				});
	}

	@Override
	public void onResume() {
		super.onResume();
		NfcEnv.enableNfcForegroundDispatch(this);
		// 加载任务
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new java.util.Date());
		getInitLog(date);
	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
		NfcEnv.initNfcEnvironment(intent);
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

	private void getInitLog(final String date) {

		ZCLog.i(TAG, date);

		ZCWebService.getInstance().queryPurchaseLog(date, new Handler() {
			@Override
			public void dispatchMessage(Message msg) {

				switch (msg.what) {
				case ZCWebServiceParams.HTTP_START: {
					ZCLog.i(TAG, msg.obj.toString());
					listPages.clear();
					logPage = null;
					mEmptyLayout.showLoading();
					break;
				}

				case ZCWebServiceParams.HTTP_FAILED: {
					ZCLog.i(TAG, msg.obj.toString());
					// Toast.makeText(getActivity(), msg.obj.toString(),
					// Toast.LENGTH_SHORT).show();
					mEmptyLayout.setErrorMessage(msg.obj.toString());
					mEmptyLayout.showError();
					break;
				}
				case ZCWebServiceParams.HTTP_FINISH: {
					ZCLog.i(TAG, msg.obj.toString());
					mPullRefreshListView.onRefreshComplete();
					break;
				}
				case ZCWebServiceParams.HTTP_SUCCESS: {
					ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

					ObjectMapper mapper = new ObjectMapper();
					try {
						requestUtil requestObj = mapper.readValue(
								msg.obj.toString(), requestUtil.class);

						String detailString = mapper
								.writeValueAsString(requestObj.getDetail());

						ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + detailString);

						logPage = mapper.readValue(detailString,
								PurchaseLogPage.class);

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

					if (logPage != null) {
						listPages.add(logPage);
					}

					initDatas();

					break;
				}

				case ZCWebServiceParams.HTTP_UNAUTH: {
					ZCLog.i(TAG, msg.obj.toString());
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(QueryLogOnlineActivity.this,
							MainActivity.class);
					Bundle bundle = new Bundle();
					bundle.putBoolean("unAuth", true);
					intent.putExtras(bundle);

					QueryLogOnlineActivity.this.setResult(QUERYLOG, intent);
					QueryLogOnlineActivity.this.finish();
					break;
				}

				default: {
					ZCLog.i(TAG, "http nothing to do");
					break;
				}

				}
			}
		});

		return;
	}

	private void getMoreLog(final String date) {

		ZCLog.i(TAG, date);

		ZCWebService.getInstance().queryPurchaseLog(date, new Handler() {
			@Override
			public void dispatchMessage(Message msg) {

				switch (msg.what) {
				case ZCWebServiceParams.HTTP_START: {
					ZCLog.i(TAG, msg.obj.toString());
					listPages.clear();
					logPage = null;
					mEmptyLayout.showLoading();
					break;
				}

				case ZCWebServiceParams.HTTP_FAILED: {
					ZCLog.i(TAG, msg.obj.toString());
					// Toast.makeText(getActivity(), msg.obj.toString(),
					// Toast.LENGTH_SHORT).show();
					mPullRefreshListView.onRefreshComplete();
					mEmptyLayout.setErrorMessage(msg.obj.toString());
					mEmptyLayout.showError();
					break;
				}
				case ZCWebServiceParams.HTTP_FINISH: {
					ZCLog.i(TAG, msg.obj.toString());
					mPullRefreshListView.onRefreshComplete();
					break;
				}
				case ZCWebServiceParams.HTTP_SUCCESS: {
					ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
					mPullRefreshListView.onRefreshComplete();

					ObjectMapper mapper = new ObjectMapper();
					try {
						requestUtil requestObj = mapper.readValue(
								msg.obj.toString(), requestUtil.class);

						String detailString = mapper
								.writeValueAsString(requestObj.getDetail());

						ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + detailString);

						logPage = mapper.readValue(detailString,
								PurchaseLogPage.class);

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

					if (logPage != null) {
						listPages.add(logPage);
					}

					putMoreDatas();

					break;
				}

				case ZCWebServiceParams.HTTP_UNAUTH: {
					ZCLog.i(TAG, msg.obj.toString());
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(QueryLogOnlineActivity.this,
							MainActivity.class);
					Bundle bundle = new Bundle();
					bundle.putBoolean("unAuth", true);
					intent.putExtras(bundle);

					QueryLogOnlineActivity.this.setResult(QUERYLOG, intent);
					QueryLogOnlineActivity.this.finish();
					break;
				}

				default: {
					ZCLog.i(TAG, "http nothing to do");
					break;
				}

				}
			}
		});

		return;
	}

	private void initDatas() {
		// 初始化数据和数据源
		logList.clear();

		if (listPages.isEmpty()) {
			mEmptyLayout.showEmpty();
			return;
		}

		for (int i = 0; i < listPages.size(); i++) {

			List<PurchaseLog> list = listPages.get(i).getPurchaseLogQueryList();
			LogDateItem dateItem = new LogDateItem();
			HashMap<String, Object> date_map = new HashMap<String, Object>();
			dateItem.setDate(listPages.get(i).getDate());
			dateItem.setCounter(listPages.get(i).getCount());

			date_map.put("date", dateItem);
			dateString = dateItem.getDate();

			logList.add(date_map);

			for (int j = 0; j < list.size(); j++) {
				LogItem logItem = new LogItem();
				HashMap<String, Object> time_map = new HashMap<String, Object>();
				logItem.setTradeTime(list.get(j).getTime());
				logItem.setTradeAmount(list.get(j).getAmount());
				ZCLog.i(TAG, logItem.toString());
				time_map.put("content", logItem);
				logList.add(time_map);
			}

			ZCLog.i(TAG, logList.toString());
			mAdapter.notifyDataSetChanged();
		}

	}

	private void putMoreDatas() {
		// 添加数据和数据源
		ZCLog.i(TAG, "old list is :" + logList.toString());

		if (listPages.isEmpty()) {
			mEmptyLayout.showEmpty();
			return;
		}

		for (int i = 0; i < listPages.size(); i++) {

			List<PurchaseLog> list = listPages.get(i).getPurchaseLogQueryList();
			LogDateItem dateItem = new LogDateItem();
			HashMap<String, Object> date_map = new HashMap<String, Object>();
			dateItem.setDate(listPages.get(i).getDate());
			dateItem.setCounter(listPages.get(i).getCount());

			date_map.put("date", dateItem);
			dateString = dateItem.getDate();

			logList.add(logList.size(), date_map);

			for (int j = 0; j < list.size(); j++) {
				LogItem logItem = new LogItem();
				HashMap<String, Object> time_map = new HashMap<String, Object>();
				logItem.setTradeTime(list.get(j).getTime());
				logItem.setTradeAmount(list.get(j).getAmount());
				ZCLog.i(TAG, logItem.toString());
				time_map.put("content", logItem);
				logList.add(logList.size(), time_map);
			}

			ZCLog.i(TAG, logList.toString());
			mAdapter.notifyDataSetChanged();
		}

	}

	private String addDate(final String dateString) {
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date queryDate;
		try {
			queryDate = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

		calendar.setTime(queryDate);
		calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		queryDate = calendar.getTime(); // 这个时间就是日期往后推一天的结果

		return formatter.format(queryDate);
	}

}
