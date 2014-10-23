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

public class RegisterFragment extends Fragment implements OnClickListener{
	public static final String TAG = RegisterFragment.class.getSimpleName();
	
	public static final String USERNAME = "username";

	private OnRegisterPageListener mCallback;
	
	private BootstrapEditText userNameEdit = null;

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

		View view = inflater.inflate(R.layout.activity_register_page, container,
				false);
		findView(view);
		
		Bundle bundle=getArguments(); 
		userNameEdit.setText(bundle.getString(RegisterFragment.USERNAME, ""));
		
		mCallback.setTag(TAG);

		return view;

	}
	
	public void updateContent(){
		
	}

	private void findView(View view) {
		
		Log.i(TAG, "findview");
		
		TextView titleView = (TextView) getActivity().findViewById(R.id.iv_title_text);
		titleView.setText(R.string.registerTitle);

		//todo get username & password to register
		userNameEdit = (BootstrapEditText) view.findViewById(R.id.regsiter_username_edit);
		
		BootstrapButton bt_register = (BootstrapButton) view
				.findViewById(R.id.regsiter_button);
		bt_register.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regsiter_button:
		{
			Log.i(TAG, "register submit");
			String userNameString = "sundm";
			String pwdString = "asdf";
			mCallback.onRegister(userNameString, pwdString);

			break;
		}
		default:
			break;
		}
	}

	public interface OnRegisterPageListener {
		public void setTag(String tag);
		public void onRegister(String userNameString, String pwdString);
	}
}
