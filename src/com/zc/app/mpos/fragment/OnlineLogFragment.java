package com.zc.app.mpos.fragment;

import java.util.ArrayList;
import java.util.List;

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

import com.zc.app.mpos.R;
import com.zc.app.mpos.adapter.DetailItem;
import com.zc.app.mpos.adapter.DetailItemAdpater;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.Mode;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.OnRefreshListener;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshListView;
import com.zc.app.sebc.lx.CardLogEntry;
import com.zc.app.sebc.lx.CardLogObject;
import com.zc.app.sebc.lx.Longxingcard;
import com.zc.app.sebc.util.StatusCheck;
import com.zc.app.utils.ZCLog;

public class OnlineLogFragment extends Fragment implements OnClickListener {

	public static final String TAG = OnlineLogFragment.class.getSimpleName();

	private OnOnlineLogPageListener mCallback;

	private Bundle bundle;

	private List<DetailItem> mListItems = new ArrayList<DetailItem>();

	/**
	 * 上拉刷新的控件
	 */
	private PullToRefreshListView mPullRefreshListView;

	private DetailItemAdpater mAdapter;

	private CardLogObject cardLogObject;
	private List<CardLogEntry> transactionDetails;
	private CardThread cardThread;

	private int mItemCount = 9;
	private int maxCount = 5;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String message = (String) msg.obj;

			if (message.equals("1")) {
				if (cardThread != null) {
					cardThread.interrupt();
					cardThread = null;
				}
				initDatas(cardLogObject);
			}
		}
	};

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
		mAdapter = new DetailItemAdpater(getActivity(), mListItems);

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

	class CardThread extends Thread {

		public CardThread() {

		}

		public void run() {
			cardLogObject = Longxingcard.getTransactionDetails();
			Message toMain = handler.obtainMessage();
			toMain.obj = "1";
			handler.sendMessage(toMain);
		}
	}

	public void getTransactionDetails() {

		if (cardThread == null) {
			cardThread = this.new CardThread();
		}
		
		if (!cardThread.isAlive()) {
			cardThread.start();
		}
		
		return;
	}

	private void initDatas(CardLogObject obj) {
		String status = obj.getStatus();
		if ((status.equals(StatusCheck.SW1SW2_OK) == false)
				&& (status.equals(StatusCheck.SW1SW2_OK1) == false)) {
			ZCLog.i(TAG, obj.toString());
			return;
		}

		// 初始化数据和数据源
		mAdapter.clear();
		
		transactionDetails = obj.getDetails();

		mListItems = new ArrayList<DetailItem>();

		CardLogEntry cardLogEntry = null;

		for (int i = 0; i < transactionDetails.size(); i++) {
			cardLogEntry = transactionDetails.get(i);

			ZCLog.i(TAG, cardLogEntry.toString());
			DetailItem itemDetailFormat = new DetailItem();

			itemDetailFormat.setTradeType(cardLogEntry.getType());
			itemDetailFormat.setTradeAmount(cardLogEntry.getAmount());
			itemDetailFormat.setTradeDate(cardLogEntry.getDate());
			itemDetailFormat.setTradeTime(cardLogEntry.getTime());

			mAdapter.add(itemDetailFormat);
		}
		
	}

	private class GetDataTask extends AsyncTask<Void, Void, DetailItem> {

		@Override
		protected DetailItem doInBackground(Void... params) {
			DetailItem itemDetailFormat = new DetailItem();

			try {
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return itemDetailFormat;
		}

		@Override
		protected void onPostExecute(DetailItem result) {
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
