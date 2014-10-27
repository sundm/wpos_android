package com.zc.app.mpos.fragment;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zc.app.mpos.R;
import com.zc.app.utils.PosInfo;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class SettingFragment extends Fragment implements OnClickListener {
	public static final String TAG = SettingFragment.class.getSimpleName();

	public static final String NICK_NAME = "nickName";
	public static final String POS_NUMBER = "posNumber";
	public static final String POS_STATE = "posState";
	public static final String POS_ACTIVED = "actived";
	
	private OnSettingPageListener mCallback;

	private Bundle bundle;

	private TextView nickNameTextView;
	private TextView posNumberTextView;
	private TextView activeTextView;
	private LinearLayout activePOSLayout;
	
	private String terID;

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnSettingPageListener) activity;
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

		View view = inflater.inflate(R.layout.settings, container, false);
		findView(view);

		if (this.bundle != null) {
			updateView(this.bundle);
		} else {
			this.bundle = new Bundle();
		}

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
		if (this.bundle != null) {
			updateView(this.bundle);
		}

		ZCWebService.getInstance().queryPOS(new Handler() {
			@Override
			public void dispatchMessage(Message msg) {

				switch (msg.what) {

				case ZCWebServiceParams.HTTP_FAILED: {
					ZCLog.i(TAG, msg.obj.toString());
					Toast.makeText(getActivity(), msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
					bundle.putString(SettingFragment.POS_NUMBER,
							msg.obj.toString());
					updateView(bundle);
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

						PosInfo posInfo = mapper.readValue(detailString,
								PosInfo.class);
						bundle.putString(SettingFragment.POS_NUMBER,
								posInfo.getTerminalSeq());
						updateView(bundle);
						
						terID = posInfo.getTerminalId();

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
					mCallback.onUnAuth();
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
		titleView.setText(R.string.settingTitle);

		LinearLayout changePwdLayout = (LinearLayout) view
				.findViewById(R.id.change_pwd);

		changePwdLayout.setOnClickListener(this);

		activePOSLayout = (LinearLayout) view
				.findViewById(R.id.active_pos);

		activePOSLayout.setOnClickListener(this);
		
		activeTextView = (TextView) view.findViewById(R.id.active_pos_active_text);

		LinearLayout changePOSLayout = (LinearLayout) view
				.findViewById(R.id.change_pos);

		changePOSLayout.setOnClickListener(this);

		LinearLayout applyChangePOSLayout = (LinearLayout) view
				.findViewById(R.id.apply_change_pos);

		applyChangePOSLayout.setOnClickListener(this);

		nickNameTextView = (TextView) view.findViewById(R.id.user_nick_name);

		posNumberTextView = (TextView) view.findViewById(R.id.pos_number);

	}

	public void updateView(Bundle bundle) {
		// update ui

		if (bundle == null)
			return;

		String nickNameString = bundle.getString(NICK_NAME, "");
		if (!nickNameString.isEmpty()) {
			nickNameTextView.setText(nickNameString);
		} else {
			nickNameTextView.setText("获取昵称失败");
		}

		String posNumberString = bundle.getString(POS_NUMBER, "");

		if (!posNumberString.isEmpty()) {
			posNumberTextView.setText("终端序列号:" + posNumberString);
		} else {
			posNumberTextView.setText("获取POS信息失败");
		}
		
		if (bundle.getBoolean(POS_ACTIVED, false)) {
			activePOSLayout.setOnClickListener(null);
			activeTextView.setText("开通终端(已开通)");
		}
		else {
			activePOSLayout.setOnClickListener(this);
			activeTextView.setText("开通终端");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_pwd: {
			Log.i(TAG, "change password");
			mCallback.onChangePwd();
			break;
		}

		case R.id.active_pos: {
			Log.i(TAG, "active pos");
			mCallback.onActivePOS();
			break;
		}

		case R.id.change_pos: {
			Log.i(TAG, "change pos");
			mCallback.onChangePOS();
			break;
		}

		case R.id.apply_change_pos: {
			Log.i(TAG, "apply change pos");
			mCallback.onApplyChangePos(terID);
			break;
		}

		default:
			break;
		}
	}

	public interface OnSettingPageListener {
		public void setTag(String tag);

		public void onUnAuth();

		public void onChangePwd();

		public void onActivePOS();

		public void onChangePOS();

		public void onApplyChangePos(final String terID);

	}
}
