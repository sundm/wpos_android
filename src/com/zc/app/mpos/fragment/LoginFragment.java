package com.zc.app.mpos.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

	private final int minLength = 3;

	private OnLoginPageListener mCallback;

	private BootstrapEditText userNameBootstrapEditText;
	private BootstrapEditText pwdBootstrapEditText;
	private BootstrapButton loginBtnBootstrapButton;

	private Bundle bundle;

	private String userNameString;
	private String passWordString;

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
		titleView.setText(R.string.loginTitle);

		SharedPreferences sharedPreferences = getActivity()
				.getApplicationContext().getSharedPreferences("configer",
						Context.MODE_PRIVATE);
		String name = sharedPreferences.getString("_user_", "");

		userNameBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.login_username_edit);
		userNameBootstrapEditText.setText(name);

		userNameBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				userNameString = s.toString();
				passWordString = pwdBootstrapEditText.getText().toString();

				if (userNameString.isEmpty()
						|| userNameString.length() < minLength) {
					userNameBootstrapEditText.setDanger();
					loginBtnBootstrapButton.setEnabled(false);
					return;
				} else {
					userNameBootstrapEditText.setSuccess();
				}

				if (passWordString.isEmpty()
						|| passWordString.length() < minLength) {
					pwdBootstrapEditText.setDanger();
					loginBtnBootstrapButton.setEnabled(false);
					return;
				} else {
					pwdBootstrapEditText.setSuccess();
					loginBtnBootstrapButton.setEnabled(true);
				}
			}
		});

		pwdBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.login_password_edit);
		pwdBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				passWordString = s.toString();
				userNameString = userNameBootstrapEditText.getText().toString();

				if (passWordString.isEmpty()
						|| passWordString.length() < minLength) {
					pwdBootstrapEditText.setDanger();
					loginBtnBootstrapButton.setEnabled(false);
					return;
				} else {
					pwdBootstrapEditText.setSuccess();
				}

				if (userNameString.isEmpty()
						|| userNameString.length() < minLength) {
					userNameBootstrapEditText.setDanger();
					loginBtnBootstrapButton.setEnabled(false);
					return;
				} else {
					userNameBootstrapEditText.setSuccess();
					loginBtnBootstrapButton.setEnabled(true);
				}
			}
		});

		loginBtnBootstrapButton = (BootstrapButton) view
				.findViewById(R.id.signin_button);
		loginBtnBootstrapButton.setOnClickListener(this);
		loginBtnBootstrapButton.setEnabled(false);

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
			mCallback.onSignin(userNameString, passWordString);

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

		public void onSignin(final String userNameString,
				final String passWordString);

		public void onOpenRegisterPage();
	}
}
