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

public class LoginFragment extends Fragment implements OnClickListener {

	public static final String TAG = LoginFragment.class.getSimpleName();

	public static final String USERNAME = "username";

	private OnLoginPageListener mCallback;

	private BootstrapEditText userNameBootstrapEditText;
	private BootstrapEditText pwdBootstrapEditText;

	private Bundle bundle;

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnLoginPageListener) activity;
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

		View view = inflater.inflate(R.layout.activity_login_page, container,
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

		// Bundle bundle = getArguments();
		// if (bundle != null) {
		// updateView(bundle);
		// }
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
		
		TextView titleView = (TextView) getActivity().findViewById(R.id.iv_title_text);
		titleView.setText(R.string.loginTitle);

		userNameBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.login_username_edit);

		pwdBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.login_password_edit);

		BootstrapButton bt_signin = (BootstrapButton) view
				.findViewById(R.id.signin_button);
		bt_signin.setOnClickListener(this);

		TextView registerLink = (TextView) view
				.findViewById(R.id.register_link);
		registerLink.setOnClickListener(this);

	}

	private void updateView(Bundle bundle) {
		String userName = bundle.getString(LoginFragment.USERNAME);

		if (userName != null) {
			userNameBootstrapEditText.setText(userName);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signin_button: {
			Log.i(TAG, "signin enter");
			mCallback.onSignin();

			break;
		}

		case R.id.register_link: {
			Log.i(TAG, "register link click");
			mCallback.onOpenRegisterPage();
			break;
		}

		default:
			break;
		}
	}

	public interface OnLoginPageListener {
		public void setTag(String tag);

		public void onSignin();

		public void onOpenRegisterPage();
	}
}
