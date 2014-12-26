package com.zc.app.mpos.fragment;

import android.app.Activity;
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
import com.zc.app.utils.UserInfo;

public class ChangePwdFragment extends Fragment implements OnClickListener {

	public static final String TAG = ChangePwdFragment.class.getSimpleName();
	public static final String USERNAME = "userName";
	
	private final int minLength = 4;

	private OnChangePwdPageListener mCallback;

	private Bundle bundle;

	private BootstrapEditText userBootstrapEditText;
	private BootstrapEditText oldPassWordBootstrapEditText;
	private BootstrapEditText newPassWordBootstrapEditText;
	private BootstrapEditText againPassWordBootstrapEditText;

	private BootstrapButton changePwdbBootstrapButton;

	private String oldPassWordString;
	private String newPassWordString;
	private String againPassWordString;

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnChangePwdPageListener) activity;
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

		View view = inflater.inflate(R.layout.activity_change_pwd_page,
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
		titleView.setText(R.string.changePwdTitle);

//		userBootstrapEditText = (BootstrapEditText) view
//				.findViewById(R.id.changePwd_username_edit);
		userBootstrapEditText.setKeyListener(null);

		oldPassWordBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.changePwd_old_password_edit);
		oldPassWordBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				if (s.toString().length() < minLength) {
					oldPassWordBootstrapEditText.setDanger();
					changePwdbBootstrapButton.setEnabled(false);
					return;
				} else {
					oldPassWordBootstrapEditText.setSuccess();
					oldPassWordString = s.toString();
				}

				newPassWordString = newPassWordBootstrapEditText.getText()
						.toString();
				againPassWordString = againPassWordBootstrapEditText.getText()
						.toString();

				if (newPassWordString.isEmpty()
						|| newPassWordString.length() < minLength) {
					newPassWordBootstrapEditText.setDanger();
					changePwdbBootstrapButton.setEnabled(false);
					return;
				}
				if (againPassWordString.isEmpty()
						|| againPassWordString.length() < minLength) {
					againPassWordBootstrapEditText.setDanger();
					changePwdbBootstrapButton.setEnabled(false);
					return;
				}

				if (newPassWordString.equals(againPassWordString)) {
					againPassWordBootstrapEditText.setSuccess();
					changePwdbBootstrapButton.setEnabled(true);
				} else {
					againPassWordBootstrapEditText.setDanger();
					changePwdbBootstrapButton.setEnabled(false);
				}

			}
		});

		newPassWordBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.changePwd_password_edit);
		newPassWordBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				newPassWordString = s.toString();

				if (newPassWordString.isEmpty()
						|| newPassWordString.length() < minLength) {
					newPassWordBootstrapEditText.setDanger();
					changePwdbBootstrapButton.setEnabled(false);
					return;
				} else {
					newPassWordBootstrapEditText.setSuccess();
				}

				oldPassWordString = oldPassWordBootstrapEditText.getText()
						.toString();
				againPassWordString = againPassWordBootstrapEditText.getText()
						.toString();

				if (oldPassWordString.isEmpty()
						|| oldPassWordString.length() < minLength) {
					oldPassWordBootstrapEditText.setDanger();
					changePwdbBootstrapButton.setEnabled(false);
					return;
				}
				if (againPassWordString.isEmpty()
						|| againPassWordString.length() < minLength) {
					againPassWordBootstrapEditText.setDanger();
					changePwdbBootstrapButton.setEnabled(false);
					return;
				}

				if (newPassWordString.equals(againPassWordString)) {
					againPassWordBootstrapEditText.setSuccess();
					changePwdbBootstrapButton.setEnabled(true);
				} else {
					againPassWordBootstrapEditText.setDanger();
					changePwdbBootstrapButton.setEnabled(false);
				}

			}
		});

		againPassWordBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.changePwd_password_agine_edit);
		againPassWordBootstrapEditText
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						againPassWordString = s.toString();

						if (againPassWordString.isEmpty()
								|| againPassWordString.length() < minLength) {
							againPassWordBootstrapEditText.setDanger();
							changePwdbBootstrapButton.setEnabled(false);
							return;
						} else {
							againPassWordBootstrapEditText.setSuccess();
						}

						oldPassWordString = oldPassWordBootstrapEditText
								.getText().toString();
						newPassWordString = newPassWordBootstrapEditText
								.getText().toString();

						if (oldPassWordString.isEmpty()
								|| oldPassWordString.length() < minLength) {
							oldPassWordBootstrapEditText.setDanger();
							changePwdbBootstrapButton.setEnabled(false);
							return;
						}
						if (newPassWordString.isEmpty()
								|| newPassWordString.length() < minLength) {
							newPassWordBootstrapEditText.setDanger();
							changePwdbBootstrapButton.setEnabled(false);
							return;
						}

						if (newPassWordString.equals(againPassWordString)) {
							againPassWordBootstrapEditText.setSuccess();
							changePwdbBootstrapButton.setEnabled(true);
						} else {
							againPassWordBootstrapEditText.setDanger();
							changePwdbBootstrapButton.setEnabled(false);
						}
					}
				});

		changePwdbBootstrapButton = (BootstrapButton) view
				.findViewById(R.id.changePwd_button);
		changePwdbBootstrapButton.setEnabled(false);
		changePwdbBootstrapButton.setOnClickListener(this);
	}

	private void updateView(Bundle bundle) {
		Log.e(TAG, "update view");

		String userNameString = bundle.getString(USERNAME);
		if (userNameString != null) {
			userBootstrapEditText.setText(userNameString);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changePwd_button: {
			Log.i(TAG, "change password");
			UserInfo info = new UserInfo();
			info.setOldpassword(oldPassWordString);
			info.setNewpassword(newPassWordString);
			mCallback.onChangePwd(info);
			break;
		}

		default:
			break;
		}
	}

	public interface OnChangePwdPageListener {
		public void setTag(String tag);

		public void onChangePwd(UserInfo info);
	}

}
