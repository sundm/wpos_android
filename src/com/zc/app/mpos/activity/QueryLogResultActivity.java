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
import org.codehaus.jackson.map.type.TypeFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.mpos.adapter.QueryLogAdapter;
import com.zc.app.mpos.view.EmptyLayout;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.Mode;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshBase.OnRefreshListener2;
import com.zc.app.mpos.view.PullToRefresh.PullToRefreshListView;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.sebc.lx.purchaseLogResult;
import com.zc.app.sebc.lx.purchaselLogItem;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class QueryLogResultActivity extends Activity {

	List<HashMap<String, Object>> logList = new ArrayList<HashMap<String, Object>>();

	/**
	 * 上拉刷新的控件
	 */
	private PullToRefreshListView mPullRefreshListView;

	private QueryLogAdapter mAdapter;

	private List<purchaselLogItem> listPages = new ArrayList<purchaselLogItem>();
	private purchaseLogResult logResult;

	private EmptyLayout mEmptyLayout;

	private String dateString;

	private final static String TAG = "log_result_page";
	public final static String INDEX = "index";
	public final static String COUNT = "count";
	public final static String SUM = "sum";
	public final static String START = "start";
	public final static String END = "end";

	private String count;
	private String sum;
	private String start;
	private String end;
	private int index;
	private int page;
	private boolean last = false;

	private ImageView backImageView;

	private final static int QUERYLOG = 14;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_log_result_page);

		backImageView = (ImageView) findViewById(R.id.log_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.isNeedResume = false;
				finish();
			}
		});

		count = getIntent().getStringExtra(COUNT);
		sum = getIntent().getStringExtra(SUM);
		start = getIntent().getStringExtra(START);
		end = getIntent().getStringExtra(END);
		index = getIntent().getIntExtra(INDEX, 0);

		TextView dateView = (TextView) findViewById(R.id.date_text);
		TextView countView = (TextView) findViewById(R.id.number_counter_text);
		TextView sumView = (TextView) findViewById(R.id.amount_counter_text);

		countView.setText(getSpan("交易笔数:" + count + "笔"));
		sumView.setText(getSpan("总金额:" + sum + "元"));

		if (start != null && end != null) {
			switch (index) {
			case 0: {
				String[] Date = start.split("-");
				dateView.setText(Date[0] + "年" + Date[1] + "月" + Date[2] + "日");
				break;
			}
			case 1: {
				String[] Date = start.split("-");
				dateView.setText(Date[0] + "年" + Date[1] + "月");
				break;
			}
			case 2: {
				String[] Date = start.split("-");
				dateView.setText(Date[0] + "年" + Date[1] + "月");
				break;
			}
			case 3: {
				String[] startDate = start.split("-");
				String[] endDate = end.split("-");
				dateView.setText(startDate[0] + "年" + startDate[1] + "月"
						+ startDate[2] + "日" + "——" + endDate[0] + "年"
						+ endDate[1] + "月" + endDate[2] + "日");
				break;
			}
			default: {
				break;
			}
			}
		}

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.query_log_list);

		mEmptyLayout = new EmptyLayout(this, mPullRefreshListView);

		// 设置你需要的模式可选值为：disabled,pullFromStart,PULL_FROM_END,both,manualOnly
		mPullRefreshListView.setMode(Mode.BOTH);

		// 设置适配器
		mAdapter = new QueryLogAdapter(this, listPages);
		mPullRefreshListView.setAdapter(mAdapter); // 设置监听事件

		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated

						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL); // 显示最后更新的时间
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						page = 0;
						getInitLog(start, end);
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
						ZCLog.i(TAG, "last page:" + last);

						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						getMoreLog();

					}

				});

	}

	@Override
	public void onResume() {
		super.onResume();
		NfcEnv.enableNfcForegroundDispatch(this);
		// 加载任务
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String date = sdf.format(new java.util.Date());
		getInitLog(start, end);
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

	private Spannable getSpan(String w) {
		int start_user = w.indexOf(":") + 1;

		int end = w.length();

		Resources r = getResources();
		int user_size = r.getInteger(R.integer.count_size);

		Spannable word = new SpannableString(w);

		word.setSpan(new AbsoluteSizeSpan(user_size), start_user, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		word.setSpan(new ForegroundColorSpan(0xffcf0005), start_user, end - 1,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		return word;
	}

	private void getInitLog(final String start, final String end) {

		ZCLog.i(TAG, "start:" + start + "-" + "end:" + end);

		ZCWebService.getInstance().queryLogWithDate(page, start, end,
				new Handler() {
					@Override
					public void dispatchMessage(Message msg) {

						switch (msg.what) {
						case ZCWebServiceParams.HTTP_START: {
							ZCLog.i(TAG, msg.obj.toString());
							listPages.clear();
							logResult = null;
							last = false;
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
							ZCLog.i(TAG,
									">>>>>>>>>>>>>>>>" + msg.obj.toString());

							ObjectMapper mapper = new ObjectMapper();
							try {
								requestUtil requestObj = mapper.readValue(
										msg.obj.toString(), requestUtil.class);

								String detailString = mapper
										.writeValueAsString(requestObj
												.getDetail());

								logResult = mapper.readValue(detailString,
										purchaseLogResult.class);

								last = logResult.isLastPage();
								if (!last) {
									Toast.makeText(getApplicationContext(),
											"上拉加载更多", Toast.LENGTH_SHORT)
											.show();
								}

								ZCLog.i(TAG, "last page:" + last);

								String contentString = mapper
										.writeValueAsString(logResult
												.getContent());

								List<purchaselLogItem> list = mapper
										.readValue(
												contentString,
												TypeFactory
														.defaultInstance()
														.constructCollectionType(
																List.class,
																purchaselLogItem.class));

								ZCLog.i(TAG,
										">>>>>>>>>>>>>>>>" + list.toString());

								initDatas(list);

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
							Toast.makeText(getApplicationContext(),
									msg.obj.toString(), Toast.LENGTH_LONG)
									.show();
							Intent intent = new Intent(
									QueryLogResultActivity.this,
									MainActivity.class);
							Bundle bundle = new Bundle();
							bundle.putBoolean("unAuth", true);
							intent.putExtras(bundle);

							QueryLogResultActivity.this.setResult(QUERYLOG,
									intent);
							QueryLogResultActivity.this.finish();
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

	private void getMoreLog() {
		page++;
		ZCLog.i(TAG, "page:" + page + "start:" + start + "end:" + end);

		ZCWebService.getInstance().queryLogWithDate(page, start, end,
				new Handler() {
					@Override
					public void dispatchMessage(Message msg) {

						switch (msg.what) {
						case ZCWebServiceParams.HTTP_START: {
							ZCLog.i(TAG, msg.obj.toString());
							logResult = null;
							mEmptyLayout.showLoading();
							break;
						}

						case ZCWebServiceParams.HTTP_FAILED: {
							ZCLog.i(TAG, msg.obj.toString());
							// Toast.makeText(getActivity(),
							// msg.obj.toString(),
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
							ZCLog.i(TAG,
									">>>>>>>>>>>>>>>>" + msg.obj.toString());
							mPullRefreshListView.onRefreshComplete();

							ObjectMapper mapper = new ObjectMapper();
							try {
								requestUtil requestObj = mapper.readValue(
										msg.obj.toString(), requestUtil.class);

								String detailString = mapper
										.writeValueAsString(requestObj
												.getDetail());

								ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + detailString);

								logResult = mapper.readValue(detailString,
										purchaseLogResult.class);
								last = logResult.isLastPage();
								String contentString = mapper
										.writeValueAsString(logResult
												.getContent());
								if (last || contentString.isEmpty())
									Toast.makeText(getApplicationContext(),
											"最后一页啦", Toast.LENGTH_SHORT).show();
								else {
									List<purchaselLogItem> list = mapper
											.readValue(
													contentString,
													TypeFactory
															.defaultInstance()
															.constructCollectionType(
																	List.class,
																	purchaselLogItem.class));

									ZCLog.i(TAG,
											">>>>>>>>>>>>>>>>"
													+ list.toString());

									addDatas(list);

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

							// if (logPage != null) {
							// listPages.add(logPage);
							// }
							//
							// putMoreDatas();

							break;
						}

						case ZCWebServiceParams.HTTP_UNAUTH: {
							ZCLog.i(TAG, msg.obj.toString());
							Toast.makeText(getApplicationContext(),
									msg.obj.toString(), Toast.LENGTH_LONG)
									.show();
							Intent intent = new Intent(
									QueryLogResultActivity.this,
									MainActivity.class);
							Bundle bundle = new Bundle();
							bundle.putBoolean("unAuth", true);
							intent.putExtras(bundle);

							QueryLogResultActivity.this.setResult(QUERYLOG,
									intent);
							QueryLogResultActivity.this.finish();
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

	private void initDatas(List<purchaselLogItem> list) { // 初始化数据和数据源

		if (list == null || list.isEmpty()) {
			mEmptyLayout.showEmpty();
			return;
		}

		listPages.clear();
		listPages.addAll(list);
		mAdapter.notifyDataSetChanged();

	}

	private void addDatas(List<purchaselLogItem> list) { // 初始化数据和数据源

		if (list == null || list.isEmpty()) {
			mEmptyLayout.showEmpty();
			return;
		}

		listPages.addAll(list);
		mAdapter.notifyDataSetChanged();

	}

	/*
	 * private void putMoreDatas() { // 添加数据和数据源 ZCLog.i(TAG, "old list is :" +
	 * logList.toString());
	 * 
	 * if (listPages.isEmpty()) { mEmptyLayout.showEmpty(); return; }
	 * 
	 * for (int i = 0; i < listPages.size(); i++) {
	 * 
	 * List<PurchaseLog> list = listPages.get(i).getPurchaseLogQueryList();
	 * LogDateItem dateItem = new LogDateItem(); HashMap<String, Object>
	 * date_map = new HashMap<String, Object>();
	 * dateItem.setDate(listPages.get(i).getDate());
	 * dateItem.setCounter(listPages.get(i).getCount());
	 * 
	 * date_map.put("date", dateItem); dateString = dateItem.getDate();
	 * 
	 * logList.add(logList.size(), date_map);
	 * 
	 * for (int j = 0; j < list.size(); j++) { LogItem logItem = new LogItem();
	 * HashMap<String, Object> time_map = new HashMap<String, Object>();
	 * logItem.setTradeTime(list.get(j).getTime());
	 * logItem.setTradeAmount(list.get(j).getAmount()); ZCLog.i(TAG,
	 * logItem.toString()); time_map.put("content", logItem);
	 * logList.add(logList.size(), time_map); }
	 * 
	 * ZCLog.i(TAG, logList.toString()); mAdapter.notifyDataSetChanged(); }
	 * 
	 * }
	 */

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
