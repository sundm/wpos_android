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

public class ChangePosFragment extends Fragment implements OnClickListener {

	public static final String TAG = ChangePosFragment.class.getSimpleName();
	
	private final int minLength = 6;

	private OnChangePosPageListener mCallback;

	private Bundle bundle;

	private BootstrapEditText posNumberBootstrapEditText;
	private BootstrapEditText posCodeBootstrapEditText;
	private BootstrapButton changePosBootstrapButton;

	private String posNumberString;
	private String posCodeString;

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnChangePosPageListener) activity;
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

		View view = inflater.inflate(R.layout.activity_change_pos_page,
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
		titleView.setText(R.string.changePOSTitle);

		posNumberBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.change_pos_number_edit);

		posNumberBootstrapEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		posNumberBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				posNumberString = s.toString();
				posCodeString = posCodeBootstrapEditText.getText().toString();
				
				if (posNumberString.length() < minLength) {
					posNumberBootstrapEditText.setDanger();
					changePosBootstrapButton.setEnabled(false);
					return;
				} 
				else{
					posNumberBootstrapEditText.setSuccess();
				}
				
				if (posCodeString.isEmpty() || posCodeString.length() < minLength) {
					posCodeBootstrapEditText.setDanger();
					changePosBootstrapButton.setEnabled(false);
				}
				else{
					posCodeBootstrapEditText.setSuccess();
					changePosBootstrapButton.setEnabled(true);
				}
			}
		});

		posCodeBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.change_pos_code_edit);
		
		posCodeBootstrapEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

		posCodeBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				posCodeString = s.toString();
				posNumberString = posNumberBootstrapEditText.getText().toString();
				
				if (posCodeString.length() < minLength) {
					posCodeBootstrapEditText.setDanger();
					changePosBootstrapButton.setEnabled(false);
					return;
				} else {
					posCodeBootstrapEditText.setSuccess();
				}
				
				if (posNumberString.isEmpty() || posNumberString.length() < minLength) {
					posNumberBootstrapEditText.setDanger();
					changePosBootstrapButton.setEnabled(false);
				}
				else{
					posNumberBootstrapEditText.setSuccess();
					changePosBootstrapButton.setEnabled(true);
				}
			}
		});

		changePosBootstrapButton = (BootstrapButton) view
				.findViewById(R.id.change_submit_button);
		changePosBootstrapButton.setOnClickListener(this);
		changePosBootstrapButton.setEnabled(false);
	}

	private void updateView(Bundle bundle) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_submit_button: {
			Log.i(TAG, "change submit");
			mCallback.onChangePos(posNumberString, posCodeString);
			break;
		}

		default:
			break;
		}
	}

	public interface OnChangePosPageListener {
		public void setTag(String tag);

		public void onChangePos(final String posNumber, final String posCode);
	}
}