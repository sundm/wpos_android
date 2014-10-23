package com.zc.app.mpos.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zc.app.bootstrap.BootstrapButton;
import com.zc.app.bootstrap.BootstrapEditText;
import com.zc.app.mpos.R;

public class ApplyChangePosFragment extends Fragment implements OnClickListener {

	public static final String TAG = ApplyChangePosFragment.class
			.getSimpleName();
	
	public static final String POS_NUMBER = "number";
	public static final String POS_CODE = "code";

	private OnApplyChangePosPageListener mCallback;

	private Bundle bundle;

	private BootstrapEditText posNumberEditText;
	private BootstrapEditText posCodeEditText;

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnApplyChangePosPageListener) activity;
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

		View view = inflater.inflate(R.layout.activity_apply_change_pos_page,
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

		this.bundle = getArguments();
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
		titleView.setText(R.string.applyChangePOSTitle);

		posNumberEditText = (BootstrapEditText) view
				.findViewById(R.id.apply_change_pos_number_edit);
		posNumberEditText.setKeyListener(null);

		posCodeEditText = (BootstrapEditText) view
				.findViewById(R.id.apply_change_pos_code_edit);
		posCodeEditText.setKeyListener(null);

		BootstrapButton bt_apply = (BootstrapButton) view
				.findViewById(R.id.apply_change_submit_button);
		bt_apply.setOnClickListener(this);

	}

	public void updateView(Bundle bundle) {
		Log.e(TAG, "update view");
		
		String numberString = bundle.getString(POS_NUMBER);
		if (numberString != null) {
			posNumberEditText.setText(numberString);
		}
		
		String codeString = bundle.getString(POS_CODE);
		if (codeString != null) {
			posCodeEditText.setText(codeString);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.apply_change_submit_button: {
			Log.i(TAG, "apply change");
			mCallback.onApplyChangeSubmit();
			break;
		}

		default:
			break;
		}
	}

	public interface OnApplyChangePosPageListener {
		public void setTag(String tag);
		
		public void onApplyChangeSubmit();
	}
}
