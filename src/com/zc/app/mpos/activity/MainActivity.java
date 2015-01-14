package com.zc.app.mpos.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.nineoldandroids.view.ViewHelper;
import com.zc.app.boardcast.updateBoardCast;
import com.zc.app.mpos.R;
import com.zc.app.mpos.adapter.MenuArrayAdapter;
import com.zc.app.mpos.fragment.OnlineLogFragment.OnOnlineLogPageListener;
import com.zc.app.mpos.fragment.PursePosFragment;
import com.zc.app.mpos.fragment.PursePosFragment.OnPursePosPageListener;
import com.zc.app.mpos.util.ApkUpdateUtil;
import com.zc.app.mpos.util.CurrentVersion;
import com.zc.app.mpos.util.imageUtil;
import com.zc.app.mpos.util.userRole;
import com.zc.app.mpos.view.DragLayout;
import com.zc.app.mpos.view.DragLayout.DragListener;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.utils.MircoPOState;
import com.zc.app.utils.PosInfo;
import com.zc.app.utils.ZCDataBase;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestLoginUtil;
import com.zc.app.utils.requestUtil;

public class MainActivity extends FragmentActivity implements
		OnPursePosPageListener, OnOnlineLogPageListener {

	private DragLayout dl;
	private ListView lv;
	private ImageView user_icon;
	private RelativeLayout more_icon;
	private LinearLayout ll;
	private TextView userNameView;
	private TextView termailView;
	private TextView shopCodeView;

	private android.support.v4.app.Fragment mContent;
	private PursePosFragment pursePosPageFragment = null;

	private String fragmentTag = "";

	private userRole role;
	private boolean isAuth = false;
	private boolean dragIsOpened = false;

	private final static String TAG = "main";
	public final static String USER_INFO_STATE = "user_state";

	private requestLoginUtil requestLoginUtilObj = null;
	private MircoPOState state;
	private PosInfo posInfo;
	private String keyIDString;
	private String psamIDString;

	private final static int CHANGEPWD = 10;
	private final static int CHANGEPOS = 11;
	private final static int ACTIVEPOS = 12;
	private final static int PURSECARD = 13;
	private final static int QUERYLOG = 14;

	private String userNameString;
	private String posStateString;
	private String storeNumberString;
	private String termailNumberString;
	private String phoneString;
	private boolean isPOSActive;

	// location
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private MyLocationListener mMyLocationListener;
	private String tempcoor = "gcj02";

	private double lng = 000.000;
	private double lat = 000.000;

	private int span = 10000;
	private boolean isNeedAddress = true;

	//
	private AlarmManager am;

	// apk 更新
	// private PopDialog popDialog;

	private ProgressDialog pBar;
	private String appCheckUrl = "http://192.168.2.68/ftp/";

	private String appUrl = "WPOS/wpos_version.json";

	private String appName = "sebankdemo.apk";
	private String appDownPathString;

	private String currentVerNameString = "";
	private String newVerName = "";
	private String description = "";
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// getServerVersion();
		isPOSActive = false;

		// 获取系统LocationManager服务
		mLocationClient = new LocationClient(this.getApplicationContext());

		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		initLocation();

		ZCDataBase.getInstance().init(MainActivity.this);

		initUpdateAlarm();

		Intent loadingIntent = new Intent();
		loadingIntent.setClass(MainActivity.this, LoadingActivity.class);
		startActivity(loadingIntent);

		getPOSInfo();

		onNewIntent(getIntent());
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			ZCLog.i(TAG, intent.toString());
			int result = intent.getExtras().getInt("data", 0);
			if (1 == result) {
				SystemClock.sleep(1000);
				dl.close();
			}

		}
	};

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
		NfcEnv.initNfcEnvironment(intent);
	}

	@Override
	public void onStart() {
		super.onStart();
		ZCLog.i(TAG, "onStart!");

	}

	@Override
	public void onResume() {
		super.onResume();
		ZCLog.i(TAG, "onResume!");

		NfcEnv.enableNfcForegroundDispatch(this);

		IntentFilter filter = new IntentFilter("closeDL.broadcast.action");
		registerReceiver(broadcastReceiver, filter);

		if (!NfcEnv.isNfcEnabled(getApplicationContext())) {
			notEnable();
			return;
		}

		mLocationClient.start();
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		NfcEnv.disableNfcForegroundDispatch(this);

		mLocationClient.stop();
	}

	@Override
	protected void onDestroy() {
		ZCLog.i(TAG, "onDestory");
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	};

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);
		option.setCoorType(tempcoor);

		option.setScanSpan(span);
		option.setIsNeedAddress(isNeedAddress);
		mLocationClient.setLocOption(option);
	}

	private void initUpdateAlarm() {
		ZCLog.i(TAG, "initUpdateAlarm");
		am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent("wpos.update.alarm");
		intent.setClass(this, updateBoardCast.class);

		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent,
				Intent.FLAG_ACTIVITY_NEW_TASK);

		long now = System.currentTimeMillis();
		am.setInexactRepeating(AlarmManager.RTC, now, 30000, pi);
	}

	private void getPOSInfo() {
		ZCWebService.getInstance().queryPOS(new Handler() {
			@Override
			public void dispatchMessage(Message msg) {

				switch (msg.what) {
				case ZCWebServiceParams.HTTP_START: {

					break;
				}
				case ZCWebServiceParams.HTTP_FAILED: {
					ZCLog.i(TAG, msg.obj.toString());
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
					isPOSActive = false;
					break;
				}
				case ZCWebServiceParams.HTTP_SUCCESS: {
					ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

					ObjectMapper mapper = new ObjectMapper();
					try {
						requestUtil requestObj = mapper.readValue(
								msg.obj.toString(), requestUtil.class);

						if (requestObj.getDetail() == null) {
							// todo
							return;
						}

						String detailString = mapper
								.writeValueAsString(requestObj.getDetail());
						posInfo = mapper.readValue(detailString, PosInfo.class);
						ZCLog.i(TAG, posInfo.toString());
						storeNumberString = posInfo.getMerchantId();
						termailNumberString = posInfo.getTerminalSeq();
						posStateString = posInfo.getWposDisplayStatus();
						isPOSActive = true;
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
					isAuth = false;

					Intent intent = new Intent(MainActivity.this,
							LoginPage.class);

					startActivity(intent);
					MainActivity.this.finish();

					break;
				}

				case ZCWebServiceParams.HTTP_FINISH: {
					Intent intent_finish = new Intent(LoadingActivity.action);
					intent_finish.putExtra("data", 1);
					sendBroadcast(intent_finish);

					initDragLayout();
					initView();
					break;
				}

				default: {
					ZCLog.i(TAG, "http nothing to do");
					break;
				}

				}
			}
		});
	}

	/**
	 * apk update
	 */
	private void getServerVersion() {
		currentVerNameString = CurrentVersion.getVerName(this);

		String urlString = appCheckUrl + appUrl;
		ZCLog.i(TAG, urlString);

		ZCWebService.getInstance().doBasicPost(urlString, null,
				new updateHandler());

	}

	private void showUpdateDialog() {

		StringBuffer sb = new StringBuffer();
		sb.append("当前版本：");
		sb.append(currentVerNameString);
		sb.append("\n");
		sb.append("发现新版本：");
		sb.append(newVerName);
		sb.append("\n");
		sb.append("描述：\n");
		sb.append(description);
		sb.append("\n");
		sb.append("是否更新？");

		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("软件更新")
				.setMessage(sb.toString())
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						showProgressBar();// 更新当前版本
					}
				})
				.setNegativeButton("暂不更新",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						}).create();
		dialog.show();
	}

	protected void showProgressBar() {
		// TODO Auto-generated method stub
		pBar = new ProgressDialog(this);
		pBar.setTitle("正在下载");
		pBar.setMessage("请稍后...");
		pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		downAppFile(appDownPathString);
	}

	protected void downAppFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					Log.isLoggable("DownTag", (int) length);
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is == null) {
						throw new RuntimeException("isStream is null");
					}
					File file = new File(
							Environment.getExternalStorageDirectory(), appName);
					fileOutputStream = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int ch = -1;
					do {
						ch = is.read(buf);
						if (ch <= 0)
							break;
						fileOutputStream.write(buf, 0, ch);
					} while (true);
					is.close();
					fileOutputStream.close();
					haveDownLoad();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	// cancel progressBar and start new App
	protected void haveDownLoad() {
		// TODO Auto-generated method stub
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				// 弹出警告框 提示是否安装新的版本
				Dialog installDialog = new AlertDialog.Builder(
						MainActivity.this)
						.setTitle("下载完成")
						.setMessage("是否安装新的应用")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										installNewApk();
										finish();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										finish();
									}
								}).create();
				installDialog.show();
			}
		});
	}

	// 安装新的应用
	protected void installNewApk() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), appName)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	private void initDragLayout() {
		dl = (DragLayout) findViewById(R.id.dl);
		dl.setDragListener(new DragListener() {

			@Override
			public void onOpen() {
				hiddenKeyboard();
				lv.smoothScrollToPosition(new Random().nextInt(30));
				dragIsOpened = true;
			}

			@Override
			public void onClose() {
				dragIsOpened = false;
			}

			@Override
			public void onDrag(float percent) {
				animate(percent);
			}
		});
	}

	private void animate(float percent) {
		ViewGroup vg_left = dl.getVg_left();
		ViewGroup vg_main = dl.getVg_main();

		float f1 = 1 - percent * 0.3f;
		ViewHelper.setScaleX(vg_main, f1);
		ViewHelper.setScaleY(vg_main, f1);
		ViewHelper.setTranslationX(vg_left, -vg_left.getWidth() / 2.2f
				+ vg_left.getWidth() / 2.2f * percent);
		ViewHelper.setScaleX(vg_left, 0.5f + 0.5f * percent);
		ViewHelper.setScaleY(vg_left, 0.5f + 0.5f * percent);
		ViewHelper.setAlpha(vg_left, percent);
		ViewHelper.setAlpha(user_icon, 1 - percent);

		int color = (Integer) imageUtil.evaluate(percent,
				Color.parseColor("#ff000000"), Color.parseColor("#00000000"));
		dl.getBackground().setColorFilter(color, Mode.SRC_OVER);
	}

	private void initView() {
		requestLoginUtilObj = null;

		userNameView = (TextView) findViewById(R.id.iv_text);
		shopCodeView = (TextView) findViewById(R.id.tv_shop_text);
		shopCodeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String linkString = shopCodeView.getText().toString();
				ZCLog.i(TAG, "shopcode is clicked: " + linkString);
				if (linkString.contains("点击绑定")) {
					ZCLog.i(TAG, "open active pos");
					dl.close();
					Intent it = new Intent(MainActivity.this,
							ActivePOSActivity.class);

					it.putExtra("uniqueID", state.getUniqueIDString());

					startActivityForResult(it, ACTIVEPOS);
				} else {
					ZCLog.i(TAG, "read shopCode & termail");
					Intent loadingIntent = new Intent();
					loadingIntent.setClass(MainActivity.this,
							LoadingActivity.class);
					startActivity(loadingIntent);

					ZCWebService.getInstance().queryPOS(
							new readUserStatusHandler());
				}
			}
		});

		termailView = (TextView) findViewById(R.id.tv_ter_text);

		user_icon = (ImageView) findViewById(R.id.user_icon);
		more_icon = (RelativeLayout) findViewById(R.id.iv_funcation);
		ll = (LinearLayout) findViewById(R.id.ll1);
		lv = (ListView) findViewById(R.id.lv);

		role = userRole.UNAUTH;
		state = new MircoPOState(this);

		pursePosPageFragment = new PursePosFragment();

		Intent intent = getIntent();

		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				String userString = bundle.getString(USER_INFO_STATE, "");

				ObjectMapper mapper = new ObjectMapper();
				try {
					requestLoginUtilObj = mapper.readValue(userString,
							requestLoginUtil.class);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		if (requestLoginUtilObj != null) {
			isAuth = true;

			userNameString = requestLoginUtilObj.getUsername();
			phoneString = requestLoginUtilObj.getPhoneNumber();
			userNameView.setText(userNameString);

			if (requestLoginUtilObj.getRole().equals("Normal") && !isPOSActive) {
				role = userRole.NORMAL;
				Toast.makeText(this, "非绑定终端，请先绑定", Toast.LENGTH_SHORT).show();
				shopCodeView.setText(Html
						.fromHtml("<a href=\"activePOS\">非绑定终端，点击绑定</a>"));
			} else if (requestLoginUtilObj.getRole().equals("Normal")
					&& isPOSActive) {
				role = userRole.NORMAL;

				if (storeNumberString == null || termailNumberString == null
						|| storeNumberString.isEmpty()
						|| termailNumberString.isEmpty()) {
					shopCodeView
							.setText(Html
									.fromHtml("<a href=\"userStatus\">获取信息失败，点击重新获取</a>"));

				} else {
					shopCodeView.setText("商户号: " + storeNumberString);
					termailView.setText("终端号: " + termailNumberString);
				}
			} else {// if (requestLoginUtilObj.getRole().equals("Active")) {
				role = userRole.ACTIVE;

				if (storeNumberString == null || termailNumberString == null
						|| storeNumberString.isEmpty()
						|| termailNumberString.isEmpty()) {
					shopCodeView
							.setText(Html
									.fromHtml("<a href=\"userStatus\">获取信息失败，点击重新获取</a>"));

				} else {
					shopCodeView.setText("商户号: " + storeNumberString);
					termailView.setText("终端号: " + termailNumberString);
				}
			}

		} else {
			isAuth = false;
			role = userRole.UNAUTH;
		}

		ZCLog.i(TAG, "role:" + String.valueOf(role));

		lv.setAdapter(new MenuArrayAdapter(this, new String[] { "修改密码" }));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.i("click item", arg0.toString());
				Log.i("click item", arg1.toString());
				Log.i("click item", String.valueOf(arg3));

				switch (position) {
				case 0: {
					Intent it = new Intent(MainActivity.this,
							ChangePwdActivity.class);
					startActivityForResult(it, CHANGEPWD);
					break;
				}
				default: {
					dl.close();
					break;
				}
				}

			}
		});

		switch (role) {
		case ACTIVE: {
			break;
		}
		case NORMAL: {

			if (isPOSActive) {
				Intent it = new Intent(MainActivity.this,
						ChangePOSActivity.class);

				it.putExtra("uniqueID", state.getUniqueIDString());
				it.putExtra("phone", phoneString);

				startActivityForResult(it, CHANGEPOS);
			} else {
				Intent it = new Intent(MainActivity.this,
						ActivePOSActivity.class);

				it.putExtra("uniqueID", state.getUniqueIDString());

				startActivityForResult(it, ACTIVEPOS);
			}

			break;
		}
		case UNAUTH: {
			ZCLog.i(TAG, "user is unauth!");
			Intent it = new Intent(MainActivity.this, LoginPage.class);
			startActivity(it);
			MainActivity.this.finish();
			break;
		}

		default: {

			break;
		}
		}

		user_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});

		more_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("onLinster", "onQueryLogOnline");
				Intent it = new Intent(MainActivity.this,
						QueryLogOnlineActivity.class);

				startActivityForResult(it, QUERYLOG);
				// switchContent(onlineLogFragment, true,
				// OnlineLogFragment.TAG);
			}
		});

		ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dl.close();
			}
		});

		switchContent((android.support.v4.app.Fragment) pursePosPageFragment,
				false, PursePosFragment.TAG);
	}

	class readUserStatusHandler extends Handler {

		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case ZCWebServiceParams.HTTP_START: {
				ZCLog.i(TAG, msg.obj.toString());

				break;
			}
			case ZCWebServiceParams.HTTP_FINISH: {
				ZCLog.i(TAG, msg.obj.toString());
				Intent intent_finish = new Intent(LoadingActivity.action);
				intent_finish.putExtra("data", 1);
				sendBroadcast(intent_finish);
				break;
			}
			case ZCWebServiceParams.HTTP_FAILED: {
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();

				shopCodeView.setText(Html
						.fromHtml("<a href=\"userStatus\">获取信息失败，点击重新获取</a>"));

				isPOSActive = false;
				break;
			}
			case ZCWebServiceParams.HTTP_SUCCESS: {
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

				ObjectMapper mapper = new ObjectMapper();
				try {
					requestUtil requestObj = mapper.readValue(
							msg.obj.toString(), requestUtil.class);

					if (requestObj.getDetail() == null) {
						// todo
						return;
					}

					String detailString = mapper.writeValueAsString(requestObj
							.getDetail());
					posInfo = mapper.readValue(detailString, PosInfo.class);
					ZCLog.i(TAG, posInfo.toString());
					storeNumberString = posInfo.getMerchantId();
					termailNumberString = posInfo.getTerminalSeq();
					posStateString = posInfo.getWposDisplayStatus();
					isPOSActive = true;

					if (storeNumberString == null
							|| termailNumberString == null
							|| storeNumberString.isEmpty()
							|| termailNumberString.isEmpty()) {
						shopCodeView
								.setText(Html
										.fromHtml("<a href=\"userStatus\">获取信息失败，点击重新获取</a>"));

					} else {
						shopCodeView.setText("商户号: " + storeNumberString);
						termailView.setText("终端号: " + termailNumberString);
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
			}
			case ZCWebServiceParams.HTTP_UNAUTH: {
				ZCLog.i(TAG, msg.obj.toString());
				Toast.makeText(getApplicationContext(), msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				isAuth = false;

				Intent intent = new Intent(MainActivity.this, LoginPage.class);

				startActivity(intent);
				MainActivity.this.finish();
				break;
			}
			case ZCWebServiceParams.HTTP_THROWABLE: {
				Throwable e = (Throwable) msg.obj;
				ZCLog.e(TAG, "catch thowable:", e);

				break;
			}
			default: {
				ZCLog.i(TAG, "http nothing to do");
				break;
			}
			}
		}
	}

	public void switchContent(android.support.v4.app.Fragment to,
			final boolean isStack, final String fragmentTag) {
		if (mContent != to && to != null) {
			mContent = to;
			android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
			android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			fragmentTransaction.setCustomAnimations(R.anim.push_left_in,
					R.anim.push_left_out, R.anim.back_left_in,
					R.anim.back_right_out);

			fragmentTransaction.replace(R.id.fragment_container,
					(android.support.v4.app.Fragment) to, fragmentTag);

			Log.i("fragment", "replace fragment name is " + fragmentTag);

			if (isStack) {
				fragmentTransaction.addToBackStack(fragmentTag);
			}

			this.fragmentTag = fragmentTag;

			fragmentTransaction.commitAllowingStateLoss();
		}
	}

	private Fragment getCurrentFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragmentManager != null) {

			Log.i("fragment", "current fragment's tag name is " + fragmentTag);

			Fragment currentFragment = getSupportFragmentManager()
					.findFragmentByTag(fragmentTag);

			return currentFragment;
		} else {
			return null;
		}

	}

	private void clearBackStack() {
		FragmentManager manager = getSupportFragmentManager();
		if (manager.getBackStackEntryCount() > 0) {
			FragmentManager.BackStackEntry first = manager
					.getBackStackEntryAt(0);
			manager.popBackStack(first.getId(),
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		FragmentManager fm = getSupportFragmentManager();

		int count = fm.getBackStackEntryCount();

		Log.i("onKeyDown", "count is  " + count);

		if (keyCode == KeyEvent.KEYCODE_BACK && dragIsOpened) {
			dl.close();
			return true;
		}

		if (keyCode == KeyEvent.KEYCODE_BACK && count > 0) {

			Log.i("onKeyDown", "back to list " + count);

			fm.popBackStack(fragmentTag,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);

			return true;
		}

		if (keyCode == KeyEvent.KEYCODE_BACK && count == 0) {
			exit();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK: {
			ZCLog.i(TAG, "result_ok");
			break;
		}
		case RESULT_CANCELED: {
			ZCLog.i(TAG, "result_c");

			break;
		}
		case CHANGEPWD: {
			ZCLog.i(TAG, "result_change_pwd");
			role = userRole.UNAUTH;
			Intent it = new Intent(MainActivity.this, LoginPage.class);
			startActivity(it);
			MainActivity.this.finish();
			break;
		}
		case CHANGEPOS: {
			ZCLog.i(TAG, "result_change_pos");
			Bundle activePOSBuddle = data.getExtras();
			isAuth = activePOSBuddle.getBoolean("unAuth", true);
			if (isAuth) {
				ZCLog.i(TAG, "user is unauth!");
				role = userRole.UNAUTH;
				Intent it = new Intent(MainActivity.this, LoginPage.class);
				startActivity(it);
				MainActivity.this.finish();
			} else {
				role = userRole.ACTIVE;
			}

			break;
		}
		case ACTIVEPOS: {
			ZCLog.i(TAG, "result_active_pos");
			// active pos 返回数据

			Bundle activePOSBuddle = data.getExtras();

			isAuth = activePOSBuddle.getBoolean("unAuth", true);
			if (isAuth) {
				ZCLog.i(TAG, "user is unauth!");
				role = userRole.UNAUTH;
				Intent it = new Intent(MainActivity.this, LoginPage.class);
				startActivity(it);
				MainActivity.this.finish();
				break;
			}

			role = userRole.ACTIVE;

			Intent loadingIntent = new Intent();
			loadingIntent.setClass(MainActivity.this, LoadingActivity.class);
			startActivity(loadingIntent);

			ZCWebService.getInstance().queryPOS(new readUserStatusHandler());

			// role = userRole.ACTIVE;
			//
			// if (storeNumberString == null || termailNumberString == null
			// || storeNumberString.isEmpty()
			// || termailNumberString.isEmpty()) {
			// shopCodeView.setText(Html
			// .fromHtml("<a href=\"userStatus\">获取信息失败，点击重新获取</a>"));
			//
			// } else {
			// shopCodeView.setText("商户号: " + storeNumberString);
			// termailView.setText("终端号: " + termailNumberString);
			// }

			break;
		}
		case QUERYLOG: {
			ZCLog.i(TAG, "query log");

			isAuth = data.getExtras().getBoolean("unAuth", true);
			if (isAuth) {
				ZCLog.i(TAG, "user is unauth!");
				role = userRole.UNAUTH;
				Intent it = new Intent(MainActivity.this, LoginPage.class);
				startActivity(it);
				MainActivity.this.finish();
			} else {
				role = userRole.ACTIVE;
			}

			break;
		}
		default: {
			ZCLog.i(TAG, "result:null");
			break;
		}
		}
	}

	public void exit() {
		try {
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("是否退出程序");
			builder.setTitle("确认");

			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
							java.lang.System.exit(0);
						}
					});
			builder.setNegativeButton("取消", null);
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void notSupport() {
		try {
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("该设备不支持NFC，请退出程序");
			builder.setTitle("确认");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							finish();
							java.lang.System.exit(0);
						}
					});
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void notEnable() {
		try {
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("NFC未打开，进入设置界面");
			builder.setTitle("确认");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							NfcEnv.showNfcSetting(getApplicationContext());
						}
					});
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
			imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	public void setTag(String tag) {
		// TODO Auto-generated method stub
		Log.i("setTag", tag);
		this.fragmentTag = tag;

		mContent = getCurrentFragment();
	}

	@Override
	public void onDoPurse(final String amountString) {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onDoPurse");

		if (!NfcEnv.isNfcEnabled(getApplicationContext())) {
			notEnable();
			return;
		}

		switch (role) {
		case ACTIVE: {
			// purchase(amountString);
			if (Float.valueOf(amountString) < 0.00001f
					&& Float.valueOf(amountString) > -0.00001f) {
				Toast.makeText(getApplicationContext(), "请输入金额",
						Toast.LENGTH_SHORT).show();
				break;
			}

			Intent loadingIntent = new Intent();
			loadingIntent.setClass(MainActivity.this, LoadingActivity.class);
			startActivity(loadingIntent);

			state.getPOSInfoFromServer(new Handler() {
				@Override
				public void dispatchMessage(Message msg) {

					switch (msg.what) {

					case ZCWebServiceParams.HTTP_FAILED: {
						Toast.makeText(getApplicationContext(),
								msg.obj.toString(), Toast.LENGTH_SHORT).show();
						Intent intent_finish = new Intent(
								LoadingActivity.action);
						intent_finish.putExtra("data", 1);
						sendBroadcast(intent_finish);
						break;
					}
					case ZCWebServiceParams.HTTP_SUCCESS: {
						keyIDString = state.getKeyID();
						psamIDString = state.getPsamID();
						ZCLog.i(TAG, "keyID:" + keyIDString + " psamID:"
								+ psamIDString);
						Intent it = new Intent(MainActivity.this,
								PurseCardActivity.class);
						it.putExtra("keyID", keyIDString);
						it.putExtra("psamID", psamIDString);
						it.putExtra("username", userNameString);
						it.putExtra("amount", amountString);
						ZCLog.i(TAG, "lng:" + lng);
						ZCLog.i(TAG, "lat:" + lat);
						it.putExtra("lng", lng);
						it.putExtra("lat", lat);
						startActivityForResult(it, PURSECARD);
						break;
					}

					case ZCWebServiceParams.HTTP_FINISH: {
						ZCLog.i(TAG, msg.obj.toString());
						Intent intent_finish = new Intent(
								LoadingActivity.action);
						intent_finish.putExtra("data", 1);
						sendBroadcast(intent_finish);
						break;
					}

					case ZCWebServiceParams.HTTP_UNAUTH: {
						isAuth = false;

						break;
					}

					default: {
						break;
					}
					}
				}
			});

			break;
		}
		case NORMAL: {
			if (isPOSActive) {
				Toast.makeText(getApplicationContext(), "请重新绑定",
						Toast.LENGTH_SHORT).show();
				Intent it = new Intent(MainActivity.this,
						ChangePOSActivity.class);
				it.putExtra("uniqueID", state.getUniqueIDString());
				startActivityForResult(it, CHANGEPOS);
			} else {
				Toast.makeText(getApplicationContext(), "请先开通",
						Toast.LENGTH_SHORT).show();
				Intent it = new Intent(MainActivity.this,
						ActivePOSActivity.class);

				it.putExtra("uniqueID", state.getUniqueIDString());

				startActivityForResult(it, ACTIVEPOS);
			}
			break;
		}
		default: {
			break;
		}
		}

	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location

			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
				lat = location.getLatitude();
				lng = location.getLongitude();
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				lat = location.getLatitude();
				lng = location.getLongitude();
			} else {
				lat = 000.000;
				lng = 000.000;
			}

			ZCLog.i("BaiduLocationApiDem", sb.toString());
		}
	}

	// apk update
	class updateHandler extends Handler {

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

				break;

			case ZCWebServiceParams.HTTP_SUCCESS:
				String resString = msg.obj.toString();
				ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + resString);
				ObjectMapper mapper = new ObjectMapper();
				try {
					requestUtil res = mapper.readValue(resString,
							requestUtil.class);

					ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + res.toString());

					String detail = mapper.writeValueAsString(res.getDetail());

					ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + detail);

					ApkUpdateUtil apkUpdateUtil = mapper.readValue(detail,
							ApkUpdateUtil.class);

					ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + apkUpdateUtil.toString());

					newVerName = apkUpdateUtil.getVersion();
					appDownPathString = appCheckUrl + apkUpdateUtil.getPath();
					description = apkUpdateUtil.getDescription();

					boolean needupdate = currentVerNameString
							.equals(newVerName);

					ZCLog.i(TAG, String.valueOf(needupdate));
					ZCLog.i(TAG, CurrentVersion.getVerName(MainActivity.this));

					if (!needupdate) {
						showUpdateDialog();
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
