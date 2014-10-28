package com.zc.app.mpos.fragment;

import java.util.LinkedList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zc.app.mpos.R;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.Mode;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.OnRefreshListener;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshListView;

public class OfflineLogFragment extends Fragment implements OnClickListener {

	public static final String TAG = OfflineLogFragment.class.getSimpleName();

	private OnOfflineLogPageListener mCallback;

	private Bundle bundle;

	private LinkedList<String> mListItems;
	/**
	 * 上拉刷新的控件
	 */
	private PullToRefreshListView mPullRefreshListView;

	private ArrayAdapter<String> mAdapter;

	private int mItemCount = 9;

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnOfflineLogPageListener) activity;
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

		View view = inflater.inflate(R.layout.activity_offline_log_page,
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
		titleView.setText(R.string.activePOSTitle);

		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);

		// 设置你需要的模式可选值为：disabled,pullFromStart,PULL_FROM_END,both,manualOnly
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		// 初始化数据
		initDatas();
		// 设置适配器
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mListItems);
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

	private void initDatas() {
		// 初始化数据和数据源
		mListItems = new LinkedList<String>();

		for (int i = 0; i < mItemCount; i++) {
			mListItems.add("" + i);
		}
	}

	private class GetDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return "" + (mItemCount++);
		}

		@Override
		protected void onPostExecute(String result) {
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

	public interface OnOfflineLogPageListener {
		public void setTag(String tag);
	}
}
