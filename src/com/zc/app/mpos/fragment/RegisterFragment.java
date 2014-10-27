package com.zc.app.mpos.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
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

public class RegisterFragment extends Fragment implements OnClickListener {
	public static final String TAG = RegisterFragment.class.getSimpleName();

	public static final String USERNAME = "username";

	private final int minLength = 4;

	private OnRegisterPageListener mCallback;

	private BootstrapEditText userNameBootstrapEditText;
	private BootstrapEditText userPasswordBootstrapEditText;
	private BootstrapEditText userPasswordAgainBootstrapEditText;
	private BootstrapEditText userNickNameBootstrapEditText;
	private BootstrapEditText userPhoneBootstrapEditText;

	private BootstrapButton registerBootstrapButton;

	private String userNameString;
	private String passWordString;
	private String nickNameString;
	private String phoneString;

	@Override
	public void onAttach(Activity activity) {
		try {
			mCallback = (OnRegisterPageListener) activity;
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

		View view = inflater.inflate(R.layout.activity_register_page,
				container, false);
		findView(view);

		mCallback.setTag(TAG);

		return view;

	}

	private void findView(View view) {

		Log.i(TAG, "findview");

		TextView titleView = (TextView) getActivity().findViewById(
				R.id.iv_title_text);
		titleView.setText(R.string.registerTitle);

		userNameBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.regsiter_username_edit);
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
				passWordString = userPasswordBootstrapEditText.getText()
						.toString();
				nickNameString = userNickNameBootstrapEditText.getText()
						.toString();
				phoneString = userPhoneBootstrapEditText.getText().toString();

				String againPassWord = userPasswordAgainBootstrapEditText
						.getText().toString();

				if (userNameString.isEmpty()
						|| userNameString.length() < minLength) {
					userNameBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userNameBootstrapEditText.setSuccess();
				}

				if (passWordString.isEmpty()
						|| passWordString.length() < minLength) {
					userPasswordBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPasswordBootstrapEditText.setSuccess();
				}

				if (nickNameString.isEmpty()
						|| nickNameString.length() < minLength) {
					userNickNameBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userNickNameBootstrapEditText.setSuccess();
				}

				if (phoneString.isEmpty() || phoneString.length() < minLength) {
					userPhoneBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPhoneBootstrapEditText.setSuccess();
				}

				if (againPassWord.isEmpty()
						|| againPassWord.length() < minLength
						|| !againPassWord.equals(passWordString)) {
					userPasswordAgainBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPasswordAgainBootstrapEditText.setSuccess();
					registerBootstrapButton.setEnabled(true);
				}

			}
		});

		userPasswordBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.regsiter_password_edit);
		userPasswordBootstrapEditText.setDanger();
		userPasswordBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				nickNameString = userNickNameBootstrapEditText.getText()
						.toString();
				phoneString = userPhoneBootstrapEditText.getText().toString();

				String againPassWord = userPasswordAgainBootstrapEditText
						.getText().toString();

				if (userNameString.isEmpty()
						|| userNameString.length() < minLength) {
					userNameBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userNameBootstrapEditText.setSuccess();
				}

				if (passWordString.isEmpty()
						|| passWordString.length() < minLength) {
					userPasswordBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPasswordBootstrapEditText.setSuccess();
				}

				if (nickNameString.isEmpty()
						|| nickNameString.length() < minLength) {
					userNickNameBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userNickNameBootstrapEditText.setSuccess();
				}

				if (phoneString.isEmpty() || phoneString.length() < minLength) {
					userPhoneBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPhoneBootstrapEditText.setSuccess();
				}

				if (againPassWord.isEmpty()
						|| againPassWord.length() < minLength
						|| !againPassWord.equals(passWordString)) {
					userPasswordAgainBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPasswordAgainBootstrapEditText.setSuccess();
					registerBootstrapButton.setEnabled(true);
				}
			}
		});

		userPasswordAgainBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.regsiter_password_agine_edit);
		userPasswordAgainBootstrapEditText
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
						String againPassWord = s.toString();
						userNameString = userNameBootstrapEditText.getText()
								.toString();
						nickNameString = userNickNameBootstrapEditText
								.getText().toString();
						phoneString = userPhoneBootstrapEditText.getText()
								.toString();

						passWordString = userPasswordBootstrapEditText
								.getText().toString();

						if (userNameString.isEmpty()
								|| userNameString.length() < minLength) {
							userNameBootstrapEditText.setDanger();
							registerBootstrapButton.setEnabled(false);
							return;
						} else {
							userNameBootstrapEditText.setSuccess();
						}

						if (passWordString.isEmpty()
								|| passWordString.length() < minLength) {
							userPasswordBootstrapEditText.setDanger();
							registerBootstrapButton.setEnabled(false);
							return;
						} else {
							userPasswordBootstrapEditText.setSuccess();
						}

						if (nickNameString.isEmpty()
								|| nickNameString.length() < minLength) {
							userNickNameBootstrapEditText.setDanger();
							registerBootstrapButton.setEnabled(false);
							return;
						} else {
							userNickNameBootstrapEditText.setSuccess();
						}

						if (phoneString.isEmpty()
								|| phoneString.length() < minLength) {
							userPhoneBootstrapEditText.setDanger();
							registerBootstrapButton.setEnabled(false);
							return;
						} else {
							userPhoneBootstrapEditText.setSuccess();
						}

						if (againPassWord.isEmpty()
								|| againPassWord.length() < minLength
								|| !againPassWord.equals(passWordString)) {
							userPasswordAgainBootstrapEditText.setDanger();
							registerBootstrapButton.setEnabled(false);
							return;
						} else {
							userPasswordAgainBootstrapEditText.setSuccess();
							registerBootstrapButton.setEnabled(true);
						}
					}
				});

		userNickNameBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.regsiter_nickname_edit);
		userNickNameBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				nickNameString = s.toString();
				userNameString = userNameBootstrapEditText.getText().toString();
				passWordString = userPasswordBootstrapEditText.getText()
						.toString();
				phoneString = userPhoneBootstrapEditText.getText().toString();

				String againPassWord = userPasswordAgainBootstrapEditText
						.getText().toString();

				if (userNameString.isEmpty()
						|| userNameString.length() < minLength) {
					userNameBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userNameBootstrapEditText.setSuccess();
				}

				if (passWordString.isEmpty()
						|| passWordString.length() < minLength) {
					userPasswordBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPasswordBootstrapEditText.setSuccess();
				}

				if (nickNameString.isEmpty()
						|| nickNameString.length() < minLength) {
					userNickNameBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userNickNameBootstrapEditText.setSuccess();
				}

				if (phoneString.isEmpty() || phoneString.length() < minLength) {
					userPhoneBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPhoneBootstrapEditText.setSuccess();
				}

				if (againPassWord.isEmpty()
						|| againPassWord.length() < minLength
						|| !againPassWord.equals(passWordString)) {
					userPasswordAgainBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPasswordAgainBootstrapEditText.setSuccess();
					registerBootstrapButton.setEnabled(true);
				}
			}
		});

		userPhoneBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.regsiter_phone_edit);
		userPhoneBootstrapEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		userPhoneBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				phoneString = s.toString();
				userNameString = userNameBootstrapEditText.getText().toString();
				passWordString = userPasswordBootstrapEditText.getText()
						.toString();
				nickNameString = userNickNameBootstrapEditText.getText()
						.toString();

				String againPassWord = userPasswordAgainBootstrapEditText
						.getText().toString();

				if (userNameString.isEmpty()
						|| userNameString.length() < minLength) {
					userNameBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userNameBootstrapEditText.setSuccess();
				}

				if (passWordString.isEmpty()
						|| passWordString.length() < minLength) {
					userPasswordBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPasswordBootstrapEditText.setSuccess();
				}

				if (nickNameString.isEmpty()
						|| nickNameString.length() < minLength) {
					userNickNameBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userNickNameBootstrapEditText.setSuccess();
				}

				if (phoneString.isEmpty() || phoneString.length() < minLength) {
					userPhoneBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPhoneBootstrapEditText.setSuccess();
				}

				if (againPassWord.isEmpty()
						|| againPassWord.length() < minLength
						|| !againPassWord.equals(passWordString)) {
					userPasswordAgainBootstrapEditText.setDanger();
					registerBootstrapButton.setEnabled(false);
					return;
				} else {
					userPasswordAgainBootstrapEditText.setSuccess();
					registerBootstrapButton.setEnabled(true);
				}
			}
		});

		registerBootstrapButton = (BootstrapButton) view
				.findViewById(R.id.regsiter_button);
		registerBootstrapButton.setOnClickListener(this);
		registerBootstrapButton.setEnabled(false);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regsiter_button: {
			Log.i(TAG, "register submit");
			UserInfo info = new UserInfo();
			info.setUsername(userNameString);
			info.setPassword(passWordString);
			info.setNickname(nickNameString);
			info.setPhonenumber(phoneString);

			mCallback.onRegister(info);

			break;
		}
		default:
			break;
		}
	}

	public interface OnRegisterPageListener {
		public void setTag(String tag);

		public void onRegister(final UserInfo info);
	}

}
