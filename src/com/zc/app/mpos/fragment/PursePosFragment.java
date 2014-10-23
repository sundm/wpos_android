package com.zc.app.mpos.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zc.app.bootstrap.BootstrapButton;
import com.zc.app.bootstrap.BootstrapEditText;
import com.zc.app.mpos.R;
import com.zc.app.mpos.util.keyboardUtil;
import com.zc.app.sebc.lx.Longxingcard;
import com.zc.app.sebc.lx.LongxingcardInfo;
import com.zc.app.sebc.pboc2.TransUtil;
import com.zc.app.sebc.util.StatusCheck;
import com.zc.app.utils.ZCLog;

public class PursePosFragment extends Fragment implements OnClickListener,
		OnTouchListener {

	public static final String TAG = PursePosFragment.class.getSimpleName();

	private OnPursePosPageListener mCallback;

	private Bundle bundle;

	private BootstrapEditText amountBootstrapEditText;
	private BootstrapEditText balancEditText;
	private BootstrapButton doPurseBootstrapButton;

	private keyboardUtil mKeyboardUtil;

	private int recLen = 0;
	private String amountString;

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnPursePosPageListener) activity;
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

		View view = inflater.inflate(R.layout.activity_purse_page, container,
				false);
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

		getBalance();
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

	private void getBalance() {
		LongxingcardInfo requestInfo = Longxingcard.getLongxingcardInfo();
		ZCLog.i("getBalance", requestInfo.toString());

		if (requestInfo.getStatus().equals(StatusCheck.SW1SW2_OK)) {

			String balanceString = requestInfo.getFloatBalance();

			balancEditText.setText(balanceString);
		}
	}

	public void setBundle(Bundle bundle) {
		Log.e(TAG, "setBundle");

		this.bundle = bundle;
	}

	private void findView(View view) {
		Log.e(TAG, "findView");

		TextView titleView = (TextView) getActivity().findViewById(
				R.id.iv_title_text);
		titleView.setText(R.string.purseTitle);

		amountBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.purse_pos_amount_edit);
		amountBootstrapEditText.setOnTouchListener(this);

		balancEditText = (BootstrapEditText) view
				.findViewById(R.id.purse_pos_balance_edit);
		balancEditText.setOnTouchListener(this);
		balancEditText.setKeyListener(null);

		final Handler handler = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				recLen++;
				doPurseBootstrapButton.setText("将在"
						+ String.valueOf(3 - recLen) + "秒后开始交易");
				if (recLen == 3) {
					mCallback.onDoPurse(amountString);
				} else {
					handler.postDelayed(this, 1000);
				}

			}
		};

		balancEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				recLen = 0;

				amountString = amountBootstrapEditText.getText().toString();
				if (amountString.isEmpty()) {
					doPurseBootstrapButton.setText("请输入金额");
					return;
				} else {
					String checkResult = TransUtil.checkInputAmount(
							amountString, 8, 2, true);

					String strAmount = checkResult.substring(4,
							checkResult.length());

					if (Integer.parseInt(strAmount) == 0) {
						doPurseBootstrapButton.setText("交易金额不能为零");
						return;
					}

					int bl = Integer.parseInt(s.toString())
							- Integer.parseInt(strAmount);

					if (bl < 0) {
						doPurseBootstrapButton.setText("余额不足");
						return;
					} else {
						handler.postDelayed(runnable, 1000);

					}
				}
			}
		});

		doPurseBootstrapButton = (BootstrapButton) view
				.findViewById(R.id.purse_pos_button);

		doPurseBootstrapButton.setBootstrapButtonEnabled(false);

	}

	private void updateView(Bundle bundle) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.purse_pos_button: {
			Log.i(TAG, "do purse");
			amountString = amountBootstrapEditText.getText().toString();
			mCallback.onDoPurse(amountString);
			break;
		}

		default:
			break;
		}
	}

	public interface OnPursePosPageListener {
		public void setTag(String tag);

		public void onDoPurse(final String amountString);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		// // 这样是在触摸到控件时，软键盘才会显示出来
		if (mKeyboardUtil != null) {
			mKeyboardUtil.hideKeyboard();
		}

		switch (v.getId()) {
		case R.id.purse_pos_amount_edit: {

			InputMethodManager imm = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			// 得到InputMethodManager的实例
			if (imm.isActive()) {
				// 如果开启
				imm.hideSoftInputFromWindow(
						amountBootstrapEditText.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}

			int inputback = amountBootstrapEditText.getInputType();
			amountBootstrapEditText.setInputType(InputType.TYPE_NULL);
			mKeyboardUtil = new keyboardUtil(getActivity(), getActivity(),
					amountBootstrapEditText);
			mKeyboardUtil.showKeyboard();
			amountBootstrapEditText.setInputType(inputback);

			break;
		}

		default: {
			break;
		}

		}

		return false;
	}
}
