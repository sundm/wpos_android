package com.zc.app.mpos.activity;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.nfc.NfcAdapter;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.zc.app.bootstrap.BootstrapCircleThumbnail;
import com.zc.app.mpos.R;
import com.zc.app.mpos.adapter.MenuArrayAdapter;
import com.zc.app.mpos.fragment.ActivePosFragment;
import com.zc.app.mpos.fragment.ActivePosFragment.OnActivePosPageListener;
import com.zc.app.mpos.fragment.ApplyChangePosFragment;
import com.zc.app.mpos.fragment.ApplyChangePosFragment.OnApplyChangePosPageListener;
import com.zc.app.mpos.fragment.ChangePosFragment;
import com.zc.app.mpos.fragment.ChangePosFragment.OnChangePosPageListener;
import com.zc.app.mpos.fragment.ChangePwdFragment;
import com.zc.app.mpos.fragment.ChangePwdFragment.OnChangePwdPageListener;
import com.zc.app.mpos.fragment.LoginFragment;
import com.zc.app.mpos.fragment.LoginFragment.OnLoginPageListener;
import com.zc.app.mpos.fragment.PursePosFragment;
import com.zc.app.mpos.fragment.PursePosFragment.OnPursePosPageListener;
import com.zc.app.mpos.fragment.PurseResultPosFragment;
import com.zc.app.mpos.fragment.PurseResultPosFragment.OnPurseResultPosPageListener;
import com.zc.app.mpos.fragment.RegisterFragment;
import com.zc.app.mpos.fragment.RegisterFragment.OnRegisterPageListener;
import com.zc.app.mpos.fragment.SettingFragment;
import com.zc.app.mpos.fragment.SettingFragment.OnSettingPageListener;
import com.zc.app.mpos.util.imageUtil;
import com.zc.app.mpos.view.DragLayout;
import com.zc.app.mpos.view.DragLayout.DragListener;
import com.zc.app.sebc.lx.LongxingcardPurchase;
import com.zc.app.sebc.lx.LongxingcardRequest;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.sebc.pboc2.TransUtil;
import com.zc.app.utils.MircoPOState;
import com.zc.app.utils.PurchaseInitInfo;
import com.zc.app.utils.PurchaseUpdateInfo;
import com.zc.app.utils.WPosInfo;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestLoginUtil;
import com.zc.app.utils.requestUtil;

public class MainActivity extends FragmentActivity implements
		OnLoginPageListener, OnPursePosPageListener,
		OnPurseResultPosPageListener, OnRegisterPageListener,
		OnSettingPageListener, OnActivePosPageListener,
		OnChangePosPageListener, OnChangePwdPageListener,
		OnApplyChangePosPageListener {

	private DragLayout dl;
	private ListView lv;
	private BootstrapCircleThumbnail iv_icon;

	private LinearLayout ll;

	private android.support.v4.app.Fragment mContent;

	private LoginFragment loginPageFragment = null;
	private PursePosFragment pursePosPageFragment = null;
	private PurseResultPosFragment purseResultPosFragment = null;
	private RegisterFragment registerFragment = null;

	private SettingFragment settingPageFragment = null;
	private ActivePosFragment activePosPageFragment = null;
	private ApplyChangePosFragment applyChangePosFragment = null;
	private ChangePosFragment changePosFragment = null;
	private ChangePwdFragment changePwdFragment = null;

	private String fragmentTag = "";

	private final int ACTIVE = 0;
	private final int NORMAL = 1;
	private final int UNAUTH = -1;

	private int role = UNAUTH;
	private boolean isAuth = false;
	private boolean dragIsOpened = false;

	private final static String TAG = "main";
	public final static String USER_INFO_STATE = "user_state";

	private requestLoginUtil requestLoginUtilObj = null;
	private MircoPOState state;
	private String keyIDString;
	private String psamIDString;
	private String nickNameString;

	private NfcAdapter nfcAdapter;
	private PendingIntent pendingIntent;

	// private final static String loginTag = LoginFragment.TAG;
	// private final static String registerTag = RegisterFragment.TAG;
	// private final static String settingTag = SettingFragment.TAG;
	//
	// private final static List<String> tagList = Arrays.asList(loginTag,
	// settingTag);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initDragLayout();
		initView();
		
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
				this, getClass())
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
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
		role = UNAUTH;
		state = new MircoPOState(this);

		loginPageFragment = new LoginFragment();

		pursePosPageFragment = new PursePosFragment();
		purseResultPosFragment = new PurseResultPosFragment();

		registerFragment = new RegisterFragment();

		settingPageFragment = new SettingFragment();
		activePosPageFragment = new ActivePosFragment();
		applyChangePosFragment = new ApplyChangePosFragment();
		changePosFragment = new ChangePosFragment();
		changePwdFragment = new ChangePwdFragment();

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
			final Bundle args = new Bundle();

			if (requestLoginUtilObj.getRole().equals("Normal")) {
				role = NORMAL;
				Toast.makeText(this, "请先开通终端", Toast.LENGTH_SHORT).show();
				args.putBoolean(SettingFragment.POS_ACTIVED, false);
			}

			if (requestLoginUtilObj.getRole().equals("Active")) {
				role = ACTIVE;
				args.putBoolean(SettingFragment.POS_ACTIVED, true);
			}

			nickNameString = requestLoginUtilObj.getNickname();
			args.putString(SettingFragment.NICK_NAME, this.nickNameString);
			settingPageFragment.setBundle(args);

		} else {
			isAuth = false;
			role = UNAUTH;
		}

		ZCLog.i(TAG, "role:" + String.valueOf(role));

		iv_icon = (BootstrapCircleThumbnail) findViewById(R.id.iv_icon);
		ll = (LinearLayout) findViewById(R.id.ll1);

		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new MenuArrayAdapter(this, new String[] { "收款交易", "交易查询",
				"龙行卡查询", "设置", "关于" }));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.i("click item", arg0.toString());
				Log.i("click item", arg1.toString());
				Log.i("click item", String.valueOf(arg3));

				Object fragementFragment = null;
				String fragmentTag = "";

				switch (position) {
				case 0: {
					fragmentTag = PursePosFragment.TAG;
					fragementFragment = pursePosPageFragment;
					break;
				}

				case 1: {
					break;
				}

				case 2: {
					break;
				}

				case 3: {
					fragmentTag = SettingFragment.TAG;
					fragementFragment = settingPageFragment;
					break;
				}

				case 4: {
					break;
				}

				default:
					break;
				}

				clearBackStack();

				switchContent(
						(android.support.v4.app.Fragment) fragementFragment,
						false, fragmentTag);

				dl.close();
			}
		});

		switch (role) {
		case ACTIVE: {

			state.getPOSInfoFromServer(new Handler() {
				@Override
				public void dispatchMessage(Message msg) {

					switch (msg.what) {

					case ZCWebServiceParams.HTTP_FAILED: {
						isAuth = false;
						clearBackStack();
						switchContent(
								(android.support.v4.app.Fragment) loginPageFragment,
								false, LoginFragment.TAG);
						break;
					}
					case ZCWebServiceParams.HTTP_SUCCESS: {
						keyIDString = state.getKeyID();
						psamIDString = state.getPsamID();
						ZCLog.i(TAG, "keyID:" + keyIDString + " psamID:"
								+ psamIDString);
						switchContent(
								(android.support.v4.app.Fragment) pursePosPageFragment,
								false, PursePosFragment.TAG);
						break;
					}

					case ZCWebServiceParams.HTTP_UNAUTH: {
						isAuth = false;
						clearBackStack();
						switchContent(
								(android.support.v4.app.Fragment) loginPageFragment,
								false, LoginFragment.TAG);
						break;
					}

					default:{
						break;
					}
					}
				}
			});

			break;
		}
		case NORMAL: {
			switchContent(
					(android.support.v4.app.Fragment) settingPageFragment,
					false, SettingFragment.TAG);

			break;
		}

		default: {
			switchContent((android.support.v4.app.Fragment) loginPageFragment,
					false, LoginFragment.TAG);
			break;
		}
		}

		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
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
							System.exit(0);
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
	@Override
	public void onSignin() {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onSignin");
	}

	@Override
	public void onOpenRegisterPage() {
		// TODO Auto-generated method stub

		Log.i("onLinster", "onOpenRegisterPage");

		Bundle args = new Bundle();
		args.putString(RegisterFragment.USERNAME, "sundm");
		registerFragment.setArguments(args);

		switchContent(registerFragment, true, RegisterFragment.TAG);

	}

	// register page
	@Override
	public void onRegister(String userNameString, String pwdStrings) {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onRegister, user:" + userNameString + " pwd:"
				+ pwdStrings);

		Bundle args = new Bundle();
		args.putString(LoginFragment.USERNAME, "sundm");
		loginPageFragment.setBundle(args);

		clearBackStack();
		switchContent(loginPageFragment, false, LoginFragment.TAG);

	}

	@Override
	public void setTag(String tag) {
		// TODO Auto-generated method stub
		Log.i("setTag", tag);
		this.fragmentTag = tag;

		mContent = getCurrentFragment();
	}

	@Override
	public void onChangePwd() {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onChangePwd");

		Bundle args = new Bundle();
		// args.putString(RegisterFragment.USERNAME, "sundm");
		changePwdFragment.setArguments(args);

		switchContent(changePwdFragment, true, ChangePwdFragment.TAG);
	}

	@Override
	public void onActivePOS() {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onActivePOS");

		Bundle args = new Bundle();
		// args.putString(RegisterFragment.USERNAME, "sundm");
		activePosPageFragment.setArguments(args);

		switchContent(activePosPageFragment, true, ActivePosFragment.TAG);
	}

	@Override
	public void onChangePOS() {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onChangePOS");

		Bundle args = new Bundle();
		// args.putString(RegisterFragment.USERNAME, "sundm");
		changePosFragment.setArguments(args);

		switchContent(changePosFragment, true, ChangePosFragment.TAG);

	}

	@Override
	public void onApplyChangePos() {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onApplyChangePos");

		Bundle args = new Bundle();
		args.putString(ApplyChangePosFragment.POS_NUMBER, "1234ABCD");
		args.putString(ApplyChangePosFragment.POS_CODE, "");

		applyChangePosFragment.setArguments(args);

		switchContent(applyChangePosFragment, true, ApplyChangePosFragment.TAG);

	}

	@Override
	public void onApplyChangeSubmit() {
		// TODO Auto-generated method stub
		Bundle args = new Bundle();
		args.putString(ApplyChangePosFragment.POS_CODE, "abcd1234");
		applyChangePosFragment.updateView(args);
	}

	@Override
	public void onDoPurse(final String amountString) {
		// TODO Auto-generated method stub
		Log.i("onLinster", "onDoPurse");
		
		purchase(amountString);
		

		// Bundle args = new Bundle();
		// args.putString(ApplyChangePosFragment.POS_NUMBER, "1234ABCD");
		// args.putString(ApplyChangePosFragment.POS_CODE, "");
		//
		// applyChangePosFragment.setArguments(args);

		//switchContent(purseResultPosFragment, true, PurseResultPosFragment.TAG);
	}

	@Override
	public void activePos(String storeNumber, String posNumber) {
		// TODO Auto-generated method stub
		hiddenKeyboard();

		WPosInfo info = new WPosInfo();
		String uniqueID = state.getUniqueIDString();

		info.setTerminalId(posNumber);
		info.setMerchantId(storeNumber);
		info.setFingerprint(uniqueID);

		ZCWebService.getInstance().activePOS(info, new Handler() {
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
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
					break;

				case ZCWebServiceParams.HTTP_SUCCESS:
					ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());
					clearBackStack();
					switchContent(settingPageFragment, false,
							SettingFragment.TAG);
					break;

				case ZCWebServiceParams.HTTP_UNAUTH:
					ZCLog.i(TAG, msg.obj.toString());
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							Toast.LENGTH_LONG).show();
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
		});
	}

	@Override
	public void onUnAuth() {
		// TODO Auto-generated method stub
		isAuth = false;
		clearBackStack();
		switchContent(loginPageFragment, false, LoginFragment.TAG);
	}

	
	private void purchase(final String amount) {
		ZCLog.i("consume", "doPurchase, amount: " + amount);
		ZCLog.i("consume", "keyId: " + keyIDString);
		ZCLog.i("consume", "psamId: " + psamIDString);

		LongxingcardRequest request = LongxingcardPurchase
				.requestInitCreditForPurchase_Longxing(amount, keyIDString,
						psamIDString);

		ZCLog.i("consume", request.toString());

		if (request.isOK()) {

			String checkResult = TransUtil.checkInputAmount(amount, 8, 2, true);

			String strAmount = checkResult.substring(4, checkResult.length());

			int bl = Integer.parseInt(request.getBalanceString())
					- Integer.parseInt(strAmount);

			final String lastBalance = Integer.toString(bl);

			ZCLog.i("consume", "lastbalace:" + lastBalance);

			ZCLog.i("consume", "init purchase");

			PurchaseInitInfo infoObj = new PurchaseInitInfo();
			infoObj.setInitResponse(request.getResponseString());
			infoObj.setAmount(request.getAmountString());
			infoObj.setPan(request.getPanString());
			infoObj.setIssuerId(request.getIssuerIdString());
			infoObj.setLng("000.000");
			infoObj.setLat("000.000");

			ZCWebService.getInstance().initForPurchase(infoObj, new Handler() {
				@Override
				public void dispatchMessage(Message msg) {

					switch (msg.what) {
					case ZCWebServiceParams.HTTP_SUCCESS:

						ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

						ObjectMapper mapper = new ObjectMapper();
						try {
							requestUtil requestObj = mapper.readValue(
									msg.obj.toString(), requestUtil.class);

							ZCLog.i(TAG, requestObj.getDetail().toString());

							@SuppressWarnings("unchecked")
							Map<String, Object> _mapperMap = (Map<String, Object>) requestObj
									.getDetail();

							String dataString = _mapperMap.get("tradingOrder")
									.toString();
							String purchaseLogId = _mapperMap.get(
									"purchaseLogId").toString();

							ZCLog.i(TAG, "tradingOrder:" + dataString);
							ZCLog.i(TAG, "purchaseLogId:" + purchaseLogId);

							LongxingcardRequest _request = LongxingcardPurchase
									.requestCreditForPurche_Longxing(dataString);

							ZCLog.i("consume", _request.toString());

							if (_request.isOK()) {

								PurchaseUpdateInfo updateInfo = new PurchaseUpdateInfo();
								updateInfo.setSw(_request.getSwString());
								updateInfo.setLogId(purchaseLogId);
								updateInfo.setMac2(_request.getMac2String());
								updateInfo.setTac(_request.getTacString());

								updateInfo.setBalance(lastBalance);

								ZCLog.i(TAG, updateInfo.toString());

								ZCWebService.getInstance().updateForPurchase(
										updateInfo, new Handler() {
											@Override
											public void dispatchMessage(
													Message msg) {

												switch (msg.what) {
												case ZCWebServiceParams.HTTP_SUCCESS: {
													
													break;
												}
												default:
													break;
												}
											}
										});

							} else {

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
					default:
						break;
					}
				}
			});
		}

	}
}
