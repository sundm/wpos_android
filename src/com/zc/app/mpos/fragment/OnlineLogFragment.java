package com.zc.app.mpos.fragment;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
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
import com.zc.app.sebc.lx.PurchaseLog;
import com.zc.app.sebc.lx.PurchaseLogPage;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class OnlineLogFragment extends Fragment implements OnClickListener {

	public static final String TAG = OnlineLogFragment.class.getSimpleName();

	private OnOnlineLogPageListener mCallback;

	private Bundle bundle;

	List<HashMap<String, Object>> logList = new ArrayList<HashMap<String, Object>>();

	/**
	 * 上拉刷新的控件
	 */
	private PullToRefreshListView mPullRefreshListView;

	private TimeAxisAdapter mAdapter;

	private List<PurchaseLog> logs;

	private List<PurchaseLogPage> listPages = new ArrayList<PurchaseLogPage>();

	private EmptyLayout mEmptyLayout;

	private String dateString;

	// final Handler handler = new Handler() {
	// public void handleMessage() {
	// HashMap<String, Object> map0 = new HashMap<String, Object>();
	// LogItem logItem = new LogItem();
	// logItem.setTradeTime("12:12:32");
	// logItem.setTradeAmount("120");
	// map0.put("content", logItem);
	// // logList.add(map0);
	//
	// mAdapter.add(map0);
	// }
	// };

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnOnlineLogPageListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnLoginPageListener");
		}
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e(TAG, "onCreateView");

		View view = inflater.inflate(R.layout.activity_online_log_page,
				container, false);
		findView(view);

		mCallback.setTag(TAG);

		mEmptyLayout = new EmptyLayout(this.getActivity(), mPullRefreshListView);

		return view;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "onActivityCreated");

	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String date = sdf.format(new java.util.Date());
		// getTransactionDetails(date);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");

	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(TAG, "onDetach");
	}

	public void setBundle(Bundle bundle) {
		Log.e(TAG, "setBundle");

		this.bundle = bundle;
	}

	private void findView(View view) {
		Log.e(TAG, "findView");

		TextView titleView = (TextView) getActivity().findViewById(
				R.id.iv_title_text);
		titleView.setText(R.string.queryLogOffTitle);

		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.online_log_list);

		// 设置你需要的模式可选值为：disabled,pullFromStart,PULL_FROM_END,both,manualOnly
		mPullRefreshListView.setMode(Mode.BOTH);

		// 设置适配器
		mAdapter = new TimeAxisAdapter(getActivity(), logList);

		mPullRefreshListView.setAdapter(mAdapter);
		// 设置监听事件
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(getActivity()
								.getApplicationContext(), System
								.currentTimeMillis(),
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
						String label = DateUtils.formatDateTime(getActivity()
								.getApplicationContext(), System
								.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// 显示最后更新的时间
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// 加载任务
						String date = addDate(dateString);
						getMoreLog(date);
						// new GetDataTask().execute();
					}

				});

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

						PurchaseLogPage logPage = mapper.readValue(
								detailString, PurchaseLogPage.class);

						listPages.add(logPage);

						initDatas();

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
					Toast.makeText(getActivity(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
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

						PurchaseLogPage logPage = mapper.readValue(
								detailString, PurchaseLogPage.class);

						listPages.add(logPage);

						putMoreDatas();

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
					Toast.makeText(getActivity(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
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

	// private class GetDataTask extends
	// AsyncTask<Void, Void, HashMap<String, Object>> {
	//
	// @Override
	// protected HashMap<String, Object> doInBackground(Void... params) {
	// HashMap<String, Object> itemDetailFormat = new HashMap<String, Object>();
	//
	// try {
	// Thread.sleep(1000);
	//
	// LogItem logItem = new LogItem();
	// logItem.setTradeTime("12:12:32");
	// logItem.setTradeAmount("120");
	// itemDetailFormat.put("content", logItem);
	//
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// return itemDetailFormat;
	// }
	//
	// @Override
	// protected void onPostExecute(HashMap<String, Object> result) {
	// ZCLog.i(TAG, result.toString());
	//
	// int index = logList.size();
	// logList.add(index, result);
	// mAdapter.notifyDataSetChanged();
	// // Call onRefreshComplete when the list has been refreshed.
	// mPullRefreshListView.onRefreshComplete();
	//
	// super.onPostExecute(result);
	// }
	// }

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activePOS_active_button: {
			Log.i(TAG, "active");

			break;
		}

		default:
			break;
		}
	}

	public interface OnOnlineLogPageListener {
		public void setTag(String tag);
	}
}
