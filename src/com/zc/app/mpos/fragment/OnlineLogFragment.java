package com.zc.app.mpos.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.os.AsyncTask;
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
import com.zc.app.mpos.adapter.LogItem;
import com.zc.app.mpos.adapter.LogItemAdpater;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.Mode;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.OnRefreshListener;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshListView;
import com.zc.app.sebc.lx.PurchaseLog;
import com.zc.app.sebc.lx.PurchaseLogPage;
import com.zc.app.sebc.lx.PurchaseLogQuery;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class OnlineLogFragment extends Fragment implements OnClickListener {

	public static final String TAG = OnlineLogFragment.class.getSimpleName();

	private OnOnlineLogPageListener mCallback;

	private Bundle bundle;

	private List<LogItem> mListItems = new ArrayList<LogItem>();

	/**
	 * 上拉刷新的控件
	 */
	private PullToRefreshListView mPullRefreshListView;

	private LogItemAdpater mAdapter;

	private List<PurchaseLog> logs;

	private int mItemCount = 9;
	private int maxCount = 5;

	

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

		if (bundle != null) {
			updateView(this.bundle);
		}

		getTransactionDetails();
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
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);

		// 设置适配器
		mAdapter = new LogItemAdpater(getActivity(), mListItems);

		mPullRefreshListView.setAdapter(mAdapter);
		// 设置监听事件
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity()
								.getApplicationContext(), System
								.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// 显示最后更新的时间
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// 模拟加载任务
						new GetDataTask().execute();
					}
				});

	}

	public void getTransactionDetails() {

		PurchaseLogQuery logQuery = new PurchaseLogQuery();
		logQuery.setStart("20141001");
		logQuery.setEnd("20141030");

		ZCWebService.getInstance().queryPurchaseLog(logQuery, new Handler() {
			@Override
			public void dispatchMessage(Message msg) {

				switch (msg.what) {

				case ZCWebServiceParams.HTTP_FAILED: {
					ZCLog.i(TAG, msg.obj.toString());
					Toast.makeText(getActivity(), msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
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

						logs = logPage.getContent();

						ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + logs.toString());

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
		mAdapter.clear();

		mListItems = new ArrayList<LogItem>();

		PurchaseLog logEntry = null;

		for (int i = 0; i < logs.size(); i++) {
			logEntry = logs.get(i);

			ZCLog.i(TAG, logEntry.toString());
			LogItem itemDetailFormat = new LogItem();

			itemDetailFormat.setTradePan(logEntry.getPan());
			itemDetailFormat.setTradeAmount(String.valueOf(logEntry.getAmount()));
			itemDetailFormat.setTradeDate(logEntry.getPosDate());
			itemDetailFormat.setTradeTime(logEntry.getPosTime());

			mAdapter.add(itemDetailFormat);
		}

	}

	private class GetDataTask extends AsyncTask<Void, Void, LogItem> {

		@Override
		protected LogItem doInBackground(Void... params) {
			LogItem itemDetailFormat = new LogItem();

			try {
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return itemDetailFormat;
		}

		@Override
		protected void onPostExecute(LogItem result) {
			mListItems.add(result);
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
		}
	}

	private void updateView(Bundle bundle) {
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
