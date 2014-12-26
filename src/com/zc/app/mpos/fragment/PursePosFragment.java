package com.zc.app.mpos.fragment;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zc.app.mpos.R;
import com.zc.app.mpos.util.keyboardUtil;
import com.zc.app.sebc.lx.Longxingcard;
import com.zc.app.sebc.lx.LongxingcardInfo;
import com.zc.app.utils.ZCLog;

public class PursePosFragment extends Fragment implements OnClickListener,
		OnTouchListener {

	public static final String TAG = PursePosFragment.class.getSimpleName();

	private OnPursePosPageListener mCallback;

	private Bundle bundle;

	private TextView amountTextView;
	private Button doPurseButton;

	private keyboardUtil mKeyboardUtil;

	private int recLen = 0;
	private String amountString;
	private String balanceString;

	private float amount = 0.00f;

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

		View view = inflater.inflate(R.layout.activity_purse, container, false);
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

		// if (bundle != null) {
		// updateView(this.bundle);
		// } else {
		// balancEditText.setText("");
		// }
		//
		// if (mKeyboardUtil != null) {
		// mKeyboardUtil.hideKeyboard();
		// }
		//
		// getBalance();
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

	private boolean getBalance() {
		LongxingcardInfo requestInfo = Longxingcard.getLongxingcardInfo();
		ZCLog.i("getBalance", requestInfo.toString());
		return false;
		// balancEditText.setText("");
		//
		// if (requestInfo.getStatus().equals(StatusCheck.SW1SW2_OK)) {
		//
		// balanceString = requestInfo.getFloatBalance();
		//
		// balancEditText.setText(balanceString);
		// return true;
		// } else {
		// balancEditText.setText("");
		// doPurseBootstrapButton.setText("等待刷卡");
		// return false;
		// }
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

		amountTextView = (TextView) view.findViewById(R.id.purse_amount_txt);

		setAmount("0.00");

		doPurseButton = (Button) view.findViewById(R.id.purse_active_button);
		doPurseButton.setOnClickListener(this);

		TextView padTextView_1 = (TextView) view.findViewById(R.id.pad_1);
		padTextView_1.setOnClickListener(this);

		TextView padTextView_2 = (TextView) view.findViewById(R.id.pad_2);
		padTextView_2.setOnClickListener(this);

		TextView padTextView_3 = (TextView) view.findViewById(R.id.pad_3);
		padTextView_3.setOnClickListener(this);

		TextView padTextView_4 = (TextView) view.findViewById(R.id.pad_4);
		padTextView_4.setOnClickListener(this);

		TextView padTextView_5 = (TextView) view.findViewById(R.id.pad_5);
		padTextView_5.setOnClickListener(this);

		TextView padTextView_6 = (TextView) view.findViewById(R.id.pad_6);
		padTextView_6.setOnClickListener(this);

		TextView padTextView_7 = (TextView) view.findViewById(R.id.pad_7);
		padTextView_7.setOnClickListener(this);

		TextView padTextView_8 = (TextView) view.findViewById(R.id.pad_8);
		padTextView_8.setOnClickListener(this);

		TextView padTextView_9 = (TextView) view.findViewById(R.id.pad_9);
		padTextView_9.setOnClickListener(this);

		TextView padTextView_0 = (TextView) view.findViewById(R.id.pad_0);
		padTextView_0.setOnClickListener(this);

		TextView padTextView_c = (TextView) view.findViewById(R.id.pad_c);
		padTextView_c.setOnClickListener(this);

		ImageView padTextView_cancel = (ImageView) view
				.findViewById(R.id.pad_del);
		padTextView_cancel.setOnClickListener(this);

	}

	private void updateView(Bundle bundle) {

	}

	private float appendAmount(float n) {
		if (amount > 100.00f) {
			return amount;
		}

		// float m = (float) (amount * 10 + n);

		BigDecimal v1 = new BigDecimal(String.valueOf(amount));
		BigDecimal v2 = new BigDecimal("10.0");
		BigDecimal v3 = new BigDecimal(n);
		amount = v1.multiply(v2).add(v3).setScale(2, BigDecimal.ROUND_HALF_UP)
				.floatValue();

		// BigDecimal b = new BigDecimal(m);
		// amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return amount;
	}

	private void setAmount(String w) {
		int start = w.indexOf('.');

		int end = w.length();

		Spannable word = new SpannableString(w);

		word.setSpan(new AbsoluteSizeSpan(80), start, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		word.setSpan(new AbsoluteSizeSpan(120), 0, start,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		amountTextView.setText(word);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.purse_active_button: {
			Log.i(TAG, "do purse");
			amountString = amountTextView.getText().toString();
			mCallback.onDoPurse(amountString);
			break;
		}
		case R.id.pad_1: {

			setAmount(String.valueOf(appendAmount(0.01f)));
			break;
		}
		case R.id.pad_2: {
			setAmount(String.valueOf(appendAmount(0.02f)));
			break;
		}
		case R.id.pad_3: {
			setAmount(String.valueOf(appendAmount(0.03f)));
			break;
		}
		case R.id.pad_4: {
			setAmount(String.valueOf(appendAmount(0.04f)));
			break;
		}
		case R.id.pad_5: {
			setAmount(String.valueOf(appendAmount(0.05f)));
			break;
		}
		case R.id.pad_6: {
			setAmount(String.valueOf(appendAmount(0.06f)));
			break;
		}
		case R.id.pad_7: {
			setAmount(String.valueOf(appendAmount(0.07f)));
			break;
		}
		case R.id.pad_8: {
			setAmount(String.valueOf(appendAmount(0.08f)));
			break;
		}
		case R.id.pad_9: {
			setAmount(String.valueOf(appendAmount(0.09f)));
			break;
		}
		case R.id.pad_0: {
			setAmount(String.valueOf(appendAmount(0.0f)));
			break;
		}
		case R.id.pad_c: {
			amount = 0.00f;
			setAmount("0.00");
			break;
		}
		case R.id.pad_del: {

			BigDecimal v1 = new BigDecimal(String.valueOf(amount));
			BigDecimal v2 = new BigDecimal("10");
			String mString = v1.divide(v2, 2, BigDecimal.ROUND_DOWN).toString();

			float a = Float.valueOf(mString);
			if (a < 0.01) {
				amount = 0.00f;
			} else {
				amount = a;
			}

			setAmount(mString);
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
		// case R.id.purse_pos_amount_edit: {
		//
		// InputMethodManager imm = (InputMethodManager) getActivity()
		// .getSystemService(Context.INPUT_METHOD_SERVICE);
		// // 得到InputMethodManager的实例
		// if (imm.isActive()) {
		// // 如果开启
		// imm.hideSoftInputFromWindow(
		// amountBootstrapEditText.getWindowToken(),
		// InputMethodManager.HIDE_NOT_ALWAYS);
		// }
		//
		// int inputback = amountBootstrapEditText.getInputType();
		// amountBootstrapEditText.setInputType(InputType.TYPE_NULL);
		// mKeyboardUtil = new keyboardUtil(getActivity(), getActivity(),
		// amountBootstrapEditText);
		// mKeyboardUtil.showKeyboard();
		// amountBootstrapEditText.setInputType(inputback);
		//
		// break;
		// }

		default: {
			break;
		}

		}

		return false;
	}
}
