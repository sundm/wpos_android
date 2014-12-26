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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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

import com.nineoldandroids.view.ViewHelper;
import com.zc.app.mpos.R;
import com.zc.app.mpos.adapter.MenuArrayAdapter;
import com.zc.app.mpos.fragment.OnlineLogFragment;
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
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestLoginUtil;
import com.zc.app.utils.requestUtil;

public class MainActivity extends FragmentActivity implements
		OnPursePosPageListener, OnOnlineLogPageListener {

	private DragLayout dl;
	private ListView lv;
	private ImageView iv_icon;
	private RelativeLayout more_icon;
	private LinearLayout ll;
	private TextView userNameView;
	private TextView termailView;
	private TextView shopCodeView;

	private android.support.v4.app.Fragment mContent;

	// private LoginFragment loginPageFragment = null;
	private PursePosFragment pursePosPageFragment = null;
	// private PurseResultPosFragment purseResultPosFragment = null;
	private OnlineLogFragment onlineLogFragment = null;
	// private OfflineLogFragment offlineLogFragment = null;
	// private RegisterFragment registerFragment = null;
	//
	// private SettingFragment settingPageFragment = null;
	// private ActivePosFragment activePosPageFragment = null;
	// private ApplyChangePosFragment applyChangePosFragment = null;
	// private ChangePosFragment changePosFragment = null;
	// private ChangePwdFragment changePwdFragment = null;

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

	private final static int CHANGEPOS = 11;
	private final static int ACTIVEPOS = 12;
	private final static int PURSECARD = 13;

	private String userNameString;
	private String posStateString;
	private String storeNumberString;
	private String termailNumberString;
	private String phoneString;
	private boolean isPOSActive;

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
		getPOSInfo();
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

	private void getPOSInfo() {
		ZCWebService.getInstance().queryPOS(new Handler() {
			@Override
			public void dispatchMessage(Message msg) {

				switch (msg.what) {

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
					break;
				}

				case ZCWebServiceParams.HTTP_FINISH: {
					initDragLayout();
					initView();

					onNewIntent(getIntent());
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
		ViewHelper.setAlpha(iv_icon, 1 - percent);

		int color = (Integer) imageUtil.evaluate(percent,
				Color.parseColor("#ff000000"), Color.parseColor("#00000000"));
		dl.getBackground().setColorFilter(color, Mode.SRC_OVER);
	}

	private void initView() {
		requestLoginUtilObj = null;

		userNameView = (TextView) findViewById(R.id.iv_text);
		shopCodeView = (TextView) findViewById(R.id.tv_shop_text);
		termailView = (TextView) findViewById(R.id.tv_ter_text);

		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		more_icon = (RelativeLayout) findViewById(R.id.iv_funcation);
		ll = (LinearLayout) findViewById(R.id.ll1);
		lv = (ListView) findViewById(R.id.lv);

		role = userRole.UNAUTH;
		state = new MircoPOState(this);

		pursePosPageFragment = new PursePosFragment();
		onlineLogFragment = new OnlineLogFragment();

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
			// final Bundle args = new Bundle();

			if (requestLoginUtilObj.getRole().equals("Normal")) {
				role = userRole.NORMAL;
				Toast.makeText(this, "非绑定终端，请先绑定", Toast.LENGTH_SHORT).show();
				// args.putBoolean(SettingFragment.POS_ACTIVED, false);
			}

			if (requestLoginUtilObj.getRole().equals("Active")) {
				role = userRole.ACTIVE;
				// args.putBoolean(SettingFragment.POS_ACTIVED, true);
			}

			userNameString = requestLoginUtilObj.getUsername();
			phoneString = requestLoginUtilObj.getPhoneNumber();

			userNameView.setText(userNameString);
			if (storeNumberString == null || termailNumberString == null
					|| storeNumberString.isEmpty()
					|| termailNumberString.isEmpty()) {
				shopCodeView.setText("获取终端信息失败");
			} else {
				shopCodeView.setText("商户号: " + storeNumberString);
				termailView.setText("终端号: " + termailNumberString);
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
				//
				// Object fragementFragment = null;
				// String fragmentTag = "";

				switch (position) {
				case 0: {
					Intent it = new Intent(MainActivity.this,
							ChangePwdActivity.class);
					startActivity(it);
					break;
				}
				// case 0: {
				// if (role == userRole.ACTIVE) {
				// fragmentTag = PursePosFragment.TAG;
				// fragementFragment = pursePosPageFragment;
				// } else {
				// Toast.makeText(MainActivity.this, "请先开通终端",
				// Toast.LENGTH_SHORT).show();
				// fragmentTag = SettingFragment.TAG;
				// fragementFragment = settingPageFragment;
				// }
				// break;
				// }
				//
				// case 1: {
				// fragmentTag = OnlineLogFragment.TAG;
				// fragementFragment = onlineLogFragment;
				// break;
				// }
				//
				// case 2: {
				// fragmentTag = OfflineLogFragment.TAG;
				// fragementFragment = offlineLogFragment;
				// break;
				// }
				//
				// case 3: {
				// fragmentTag = SettingFragment.TAG;
				// fragementFragment = settingPageFragment;
				// break;
				// }
				//
				// case 4: {
				// break;
				// }

				default:
					break;
				}

				// clearBackStack();
				//
				// switchContent(
				// (android.support.v4.app.Fragment) fragementFragment,
				// false, fragmentTag);

				dl.close();
			}
		});

		switch (role) {
		case ACTIVE: {

			// state.getPOSInfoFromServer(new Handler() {
			// @Override
			// public void dispatchMessage(Message msg) {
			//
			// switch (msg.what) {
			//
			// case ZCWebServiceParams.HTTP_FAILED: {
			// isAuth = false;
			// clearBackStack();
			// switchContent(
			// (android.support.v4.app.Fragment) loginPageFragment,
			// false, LoginFragment.TAG);
			// break;
			// }
			// case ZCWebServiceParams.HTTP_SUCCESS: {
			// keyIDString = state.getKeyID();
			// psamIDString = state.getPsamID();
			// ZCLog.i(TAG, "keyID:" + keyIDString + " psamID:"
			// + psamIDString);
			// switchContent(
			// (android.support.v4.app.Fragment) pursePosPageFragment,
			// false, PursePosFragment.TAG);
			// break;
			// }
			//
			// case ZCWebServiceParams.HTTP_UNAUTH: {
			// isAuth = false;
			// clearBackStack();
			// switchContent(
			// (android.support.v4.app.Fragment) loginPageFragment,
			// false, LoginFragment.TAG);
			// break;
			// }
			//
			// default: {
			// break;
			// }
			// }
			// }
			// });

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

		default: {

			break;
		}
		}

		switchContent((android.support.v4.app.Fragment) pursePosPageFragment,
				false, PursePosFragment.TAG);

		iv_icon.setOnClickListener(new OnClickListener() {
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

				switchContent(onlineLogFragment, true, OnlineLogFragment.TAG);
			}
		});

		ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dl.close();
			}
		});
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

			fragmentTransaction.commit();
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
		case CHANGEPOS: {
			ZCLog.i(TAG, "result_change_pos");
			role = userRole.ACTIVE;
			break;
		}
		case ACTIVEPOS: {
			ZCLog.i(TAG, "result_active_pos");
			// active pos 返回数据
			role = userRole.ACTIVE;
			Bundle activePOSBuddle = data.getExtras();
			storeNumberString = activePOSBuddle.getString("storeCode");
			termailNumberString = activePOSBuddle.getString("posNumber");

			if (storeNumberString == null || termailNumberString == null
					|| storeNumberString.isEmpty()
					|| termailNumberString.isEmpty()) {
				shopCodeView.setText("获取终端信息失败");
			} else {
				shopCodeView.setText("商户号: " + storeNumberString);
				termailView.setText("终端号: " + termailNumberString);
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

	private void hiddenKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	// Login Page
	// @Override
	// public void onSignin(final String userNameString,
	// final String passWordString) {
	// // TODO Auto-generated method stub
	// Log.i("onLinster", "onSignin");
	//
	// UserInfo info = new UserInfo();
	// info.setUsername(userNameString);
	// info.setPassword(passWordString);
	//
	// String fingerprint = state.getUniqueIDString();
	//
	// ZCWebService.getInstance().userLogin(info, fingerprint, new Handler() {
	// @Override
	// public void dispatchMessage(Message msg) {
	//
	// switch (msg.what) {
	// case ZCWebServiceParams.HTTP_START:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FINISH:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FAILED:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_SUCCESS:
	// Toast.makeText(getApplicationContext(), "登录成功",
	// Toast.LENGTH_SHORT).show();
	// ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
	//
	// SharedPreferences sharedPreferences = getApplicationContext()
	// .getSharedPreferences("configer",
	// Context.MODE_PRIVATE);
	// // 编辑配置
	// Editor editor = sharedPreferences.edit();// 获取编辑器
	// editor.putString("_user_", userNameString);
	// editor.commit();// 提交修改
	//
	// ObjectMapper mapper = new ObjectMapper();
	// try {
	// requestUtil requestObj = mapper.readValue(
	// msg.obj.toString(), requestUtil.class);
	//
	// String detailString = mapper
	// .writeValueAsString(requestObj.getDetail());
	//
	// requestLoginUtilObj = mapper.readValue(detailString,
	// requestLoginUtil.class);
	//
	// hiddenKeyboard();
	// initView();
	//
	// } catch (JsonParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (JsonMappingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// break;
	//
	// case ZCWebServiceParams.HTTP_UNAUTH:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_THROWABLE:
	// Throwable e = (Throwable) msg.obj;
	// ZCLog.e(TAG, "catch thowable:", e);
	// break;
	//
	// default:
	// ZCLog.i(TAG, "http nothing to do");
	// break;
	// }
	// }
	// });
	// }

	// @Override
	// public void onOpenRegisterPage() {
	// // TODO Auto-generated method stub
	//
	// Log.i("onLinster", "onOpenRegisterPage");
	//
	// Bundle args = new Bundle();
	// args.putString(RegisterFragment.USERNAME, "sundm");
	// registerFragment.setArguments(args);
	//
	// switchContent(registerFragment, true, RegisterFragment.TAG);
	//
	// }

	// register page
	// @Override
	// public void onRegister(final UserInfo info) {
	// // TODO Auto-generated method stub
	// Log.i("onLinster", "onRegister, user:" + info.getUsername());
	//
	// ZCWebService.getInstance().register(info, new Handler() {
	// @Override
	// public void dispatchMessage(Message msg) {
	//
	// switch (msg.what) {
	// case ZCWebServiceParams.HTTP_START:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FINISH:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FAILED:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_SUCCESS:
	// Toast.makeText(getApplicationContext(), "注册成功",
	// Toast.LENGTH_LONG).show();
	// ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
	//
	// SharedPreferences sharedPreferences = getApplicationContext()
	// .getSharedPreferences("configer",
	// Context.MODE_PRIVATE);
	// // 编辑配置
	// Editor editor = sharedPreferences.edit();// 获取编辑器
	// editor.putString("_user_", info.getUsername());
	// editor.commit();// 提交修改
	//
	// ObjectMapper mapper = new ObjectMapper();
	// try {
	// requestUtil requestObj = mapper.readValue(
	// msg.obj.toString(), requestUtil.class);
	//
	// ZCLog.i(TAG, requestObj.getDetail().toString());
	//
	// hiddenKeyboard();
	// clearBackStack();
	// switchContent(loginPageFragment, false,
	// LoginFragment.TAG);
	//
	// } catch (JsonParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (JsonMappingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// break;
	//
	// case ZCWebServiceParams.HTTP_UNAUTH:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_THROWABLE:
	// Throwable e = (Throwable) msg.obj;
	// ZCLog.e(TAG, "catch thowable:", e);
	// break;
	//
	// default:
	// ZCLog.i(TAG, "http nothing to do");
	// break;
	// }
	// }
	// });
	//
	// }

	@Override
	public void setTag(String tag) {
		// TODO Auto-generated method stub
		Log.i("setTag", tag);
		this.fragmentTag = tag;

		mContent = getCurrentFragment();
	}

	// @Override
	// public void onChangePwd() {
	// // TODO Auto-generated method stub
	// Log.i("onLinster", "onChangePwd");
	//
	// Bundle args = new Bundle();
	// args.putString(ChangePwdFragment.USERNAME, userNameString);
	// changePwdFragment.setBundle(args);
	//
	// switchContent(changePwdFragment, true, ChangePwdFragment.TAG);
	// }
	//
	// @Override
	// public void onActivePOS() {
	// // TODO Auto-generated method stub
	// Log.i("onLinster", "onActivePOS");
	//
	// // Bundle args = new Bundle();
	// // args.putString(RegisterFragment.USERNAME, "sundm");
	// // activePosPageFragment.setArguments(args);
	//
	// switchContent(activePosPageFragment, true, ActivePosFragment.TAG);
	// }
	//
	// @Override
	// public void onChangePOS() {
	// // TODO Auto-generated method stub
	// Log.i("onLinster", "onChangePOS");
	//
	// Bundle args = new Bundle();
	// // args.putString(RegisterFragment.USERNAME, "sundm");
	// changePosFragment.setArguments(args);
	//
	// switchContent(changePosFragment, true, ChangePosFragment.TAG);
	//
	// }
	//
	// @Override
	// public void onApplyChangePos(final String terID) {
	// // TODO Auto-generated method stub
	// Log.i("onLinster", "onApplyChangePos");
	//
	// Bundle args = new Bundle();
	// args.putString(ApplyChangePosFragment.POS_NUMBER, terID);
	// args.putString(ApplyChangePosFragment.POS_CODE, "");
	//
	// applyChangePosFragment.setBundle(args);
	//
	// switchContent(applyChangePosFragment, true, ApplyChangePosFragment.TAG);
	//
	// }
	//
	// @Override
	// public void onApplyChangeSubmit() {
	// // TODO Auto-generated method stub
	// ZCWebService.getInstance().changePOSCode(new Handler() {
	// @Override
	// public void dispatchMessage(Message msg) {
	//
	// switch (msg.what) {
	// case ZCWebServiceParams.HTTP_START:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FINISH:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FAILED:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_SUCCESS:
	// ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
	// Toast.makeText(getApplicationContext(), "申请成功",
	// Toast.LENGTH_SHORT).show();
	//
	// ObjectMapper mapper = new ObjectMapper();
	// try {
	// requestUtil requestObj = mapper.readValue(
	// msg.obj.toString(), requestUtil.class);
	//
	// Bundle args = new Bundle();
	// args.putString(ApplyChangePosFragment.POS_CODE,
	// requestObj.getDetail().toString());
	// applyChangePosFragment.updateView(args);
	//
	// } catch (JsonParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (JsonMappingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// break;
	//
	// case ZCWebServiceParams.HTTP_UNAUTH:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_SHORT).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_THROWABLE:
	// Throwable e = (Throwable) msg.obj;
	// ZCLog.e(TAG, "catch thowable:", e);
	// break;
	//
	// default:
	// ZCLog.i(TAG, "http nothing to do");
	// break;
	// }
	// }
	// });
	//
	// }

	@Override
	public void onDoPurse(final String amountString) {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onDoPurse");

		switch (role) {
		case ACTIVE: {
			// purchase(amountString);
			if (!(Float.valueOf(amountString) > 0.00f))
				break;

			state.getPOSInfoFromServer(new Handler() {
				@Override
				public void dispatchMessage(Message msg) {

					switch (msg.what) {

					case ZCWebServiceParams.HTTP_FAILED: {
						Toast.makeText(getApplicationContext(),
								msg.obj.toString(), Toast.LENGTH_SHORT).show();
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
						startActivityForResult(it, PURSECARD);
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

		// Bundle args = new Bundle();
		// args.putString(ApplyChangePosFragment.POS_NUMBER, "1234ABCD");
		// args.putString(ApplyChangePosFragment.POS_CODE, "");
		//
		// applyChangePosFragment.setArguments(args);

		// switchContent(purseResultPosFragment, true,
		// PurseResultPosFragment.TAG);
	}

	// @Override
	// public void activePos(String storeNumber, String posNumber) {
	// // TODO Auto-generated method stub
	// hiddenKeyboard();
	//
	// WPosInfo info = new WPosInfo();
	// String uniqueID = state.getUniqueIDString();
	//
	// info.setTerminalId(posNumber);
	// info.setMerchantId(storeNumber);
	// info.setFingerprint(uniqueID);
	//
	// ZCWebService.getInstance().activePOS(info, new Handler() {
	// @Override
	// public void dispatchMessage(Message msg) {
	//
	// switch (msg.what) {
	// case ZCWebServiceParams.HTTP_START:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FINISH:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FAILED:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_SUCCESS:
	// ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
	// Toast.makeText(getApplicationContext(), "开通成功",
	// Toast.LENGTH_SHORT).show();
	//
	// hiddenKeyboard();
	// clearBackStack();
	//
	// role = userRole.ACTIVE;
	// final Bundle args = new Bundle();
	// args.putBoolean(SettingFragment.POS_ACTIVED, true);
	// settingPageFragment.setBundle(args);
	//
	// switchContent(settingPageFragment, false,
	// SettingFragment.TAG);
	// break;
	//
	// case ZCWebServiceParams.HTTP_UNAUTH:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_THROWABLE:
	// Throwable e = (Throwable) msg.obj;
	// ZCLog.e(TAG, "catch thowable:", e);
	// break;
	//
	// default:
	// ZCLog.i(TAG, "http nothing to do");
	// break;
	// }
	// }
	// });
	// }
	//
	// @Override
	// public void onUnAuth() {
	// // TODO Auto-generated method stub
	// isAuth = false;
	// clearBackStack();
	// switchContent(loginPageFragment, false, LoginFragment.TAG);
	// }

	// private void purchase(final String amount) {
	// ZCLog.i("consume", "doPurchase, amount: " + amount);
	// ZCLog.i("consume", "keyId: " + keyIDString);
	// ZCLog.i("consume", "psamId: " + psamIDString);
	//
	// LongxingcardRequest request = LongxingcardPurchase
	// .requestInitCreditForPurchase_Longxing(amount, keyIDString,
	// psamIDString);
	//
	// ZCLog.i("consume", request.toString());
	//
	// if (request.isOK()) {
	//
	// String checkResult = TransUtil.checkInputAmount(amount, 8, 2, true);
	//
	// String strAmount = checkResult.substring(4, checkResult.length());
	//
	// int bl = Integer.parseInt(request.getBalanceString())
	// - Integer.parseInt(strAmount);
	//
	// final String lastBalance = Integer.toString(bl);
	//
	// ZCLog.i("consume", "lastbalace:" + lastBalance);
	//
	// ZCLog.i("consume", "init purchase");
	//
	// PurchaseInitInfo infoObj = new PurchaseInitInfo();
	// infoObj.setInitResponse(request.getResponseString());
	// infoObj.setAmount(request.getAmountString());
	// infoObj.setPan(request.getPanString());
	// infoObj.setIssuerId(request.getIssuerIdString());
	// infoObj.setLng("000.000");
	// infoObj.setLat("000.000");
	//
	// ZCWebService.getInstance().initForPurchase(infoObj, new Handler() {
	// @Override
	// public void dispatchMessage(Message msg) {
	//
	// switch (msg.what) {
	// case ZCWebServiceParams.HTTP_SUCCESS:
	//
	// ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
	//
	// ObjectMapper mapper = new ObjectMapper();
	// try {
	// requestUtil requestObj = mapper.readValue(
	// msg.obj.toString(), requestUtil.class);
	//
	// ZCLog.i(TAG, requestObj.getDetail().toString());
	//
	// @SuppressWarnings("unchecked")
	// Map<String, Object> _mapperMap = (Map<String, Object>) requestObj
	// .getDetail();
	//
	// String dataString = _mapperMap.get("tradingOrder")
	// .toString();
	// String purchaseLogId = _mapperMap.get(
	// "purchaseLogId").toString();
	//
	// ZCLog.i(TAG, "tradingOrder:" + dataString);
	// ZCLog.i(TAG, "purchaseLogId:" + purchaseLogId);
	//
	// LongxingcardRequest _request = LongxingcardPurchase
	// .requestCreditForPurche_Longxing(dataString);
	//
	// ZCLog.i("consume", _request.toString());
	//
	// if (_request.isOK()) {
	//
	// PurchaseUpdateInfo updateInfo = new PurchaseUpdateInfo();
	// updateInfo.setSw(_request.getSwString());
	// updateInfo.setLogId(purchaseLogId);
	// updateInfo.setMac2(_request.getMac2String());
	// updateInfo.setTac(_request.getTacString());
	//
	// updateInfo.setBalance(lastBalance);
	//
	// ZCLog.i(TAG, updateInfo.toString());
	//
	// ZCWebService.getInstance().updateForPurchase(
	// updateInfo, new Handler() {
	// @Override
	// public void dispatchMessage(
	// Message msg) {
	//
	// switch (msg.what) {
	// case ZCWebServiceParams.HTTP_PURCHASE_SUCCESS: {
	// ZCLog.i(TAG, "消费结果上传成功");
	// Bundle args = new Bundle();
	// args.putString(
	// PurseResultPosFragment.AMOUNT,
	// amount);
	//
	// args.putString(
	// PurseResultPosFragment.BALANCE,
	// lastBalance);
	//
	// args.putString(
	// PurseResultPosFragment.HINT,
	// "完成交易");
	//
	// purseResultPosFragment
	// .setBundle(args);
	//
	// switchContent(
	// purseResultPosFragment,
	// true,
	// PurseResultPosFragment.TAG);
	// break;
	// }
	// default:
	// break;
	// }
	// }
	// });
	//
	// } else {
	//
	// }
	//
	// } catch (JsonParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (JsonMappingException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// break;
	// default:
	// break;
	// }
	// }
	// });
	// }
	//
	// }
	//
	// @Override
	// public void onFinish() {
	// // TODO Auto-generated method stub
	// clearBackStack();
	// switchContent(pursePosPageFragment, false, PursePosFragment.TAG);
	// }
	//
	// @Override
	// public void onChangePos(String posNumber, String posCode) {
	// // TODO Auto-generated method stub
	// WPosInfo info = new WPosInfo();
	// String uniqueID = state.getUniqueIDString();
	//
	// info.setTerminalId(posNumber);
	// info.setValidateCode(posCode);
	// info.setFingerprint(uniqueID);
	//
	// ZCWebService.getInstance().changePOS(info, new Handler() {
	// @Override
	// public void dispatchMessage(Message msg) {
	//
	// switch (msg.what) {
	// case ZCWebServiceParams.HTTP_START:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FINISH:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FAILED:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_SUCCESS:
	// ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
	// Toast.makeText(getApplicationContext(), "更换成功",
	// Toast.LENGTH_SHORT).show();
	//
	// hiddenKeyboard();
	// clearBackStack();
	// switchContent(settingPageFragment, false,
	// SettingFragment.TAG);
	// break;
	//
	// case ZCWebServiceParams.HTTP_UNAUTH:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_THROWABLE:
	// Throwable e = (Throwable) msg.obj;
	// ZCLog.e(TAG, "catch thowable:", e);
	// break;
	//
	// default:
	// ZCLog.i(TAG, "http nothing to do");
	// break;
	// }
	// }
	// });
	// }
	//
	// @Override
	// public void onChangePwd(UserInfo info) {
	// // TODO Auto-generated method stub
	// ZCWebService.getInstance().changePassword(info, new Handler() {
	// @Override
	// public void dispatchMessage(Message msg) {
	//
	// switch (msg.what) {
	// case ZCWebServiceParams.HTTP_START:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FINISH:
	// ZCLog.i(TAG, msg.obj.toString());
	// break;
	//
	// case ZCWebServiceParams.HTTP_FAILED:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_SUCCESS:
	// ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
	// Toast.makeText(getApplicationContext(), "修改密码成功",
	// Toast.LENGTH_SHORT).show();
	//
	// hiddenKeyboard();
	// clearBackStack();
	// switchContent(settingPageFragment, false,
	// SettingFragment.TAG);
	// break;
	//
	// case ZCWebServiceParams.HTTP_UNAUTH:
	// ZCLog.i(TAG, msg.obj.toString());
	// Toast.makeText(getApplicationContext(), msg.obj.toString(),
	// Toast.LENGTH_LONG).show();
	// break;
	//
	// case ZCWebServiceParams.HTTP_THROWABLE:
	// Throwable e = (Throwable) msg.obj;
	// ZCLog.e(TAG, "catch thowable:", e);
	// break;
	//
	// default:
	// ZCLog.i(TAG, "http nothing to do");
	// break;
	// }
	// }
	// });
	// }

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
