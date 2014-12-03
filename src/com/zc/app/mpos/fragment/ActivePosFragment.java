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

public class ActivePosFragment extends Fragment implements OnClickListener {

	public static final String TAG = ActivePosFragment.class.getSimpleName();

	private OnActivePosPageListener mCallback;

	private Bundle bundle;

	private BootstrapEditText storeNumberBootstrapEditText;
	private BootstrapEditText posNumberBootstrapEditText;
	private BootstrapButton activeBootstrapButton;

	@Override
	public void onAttach(Activity activity) {
		Log.e(TAG, "onAttach");
		try {
			mCallback = (OnActivePosPageListener) activity;
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

		View view = inflater.inflate(R.layout.activity_active_pos_page,
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
		titleView.setText(R.string.activePOSTitle);

		storeNumberBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.activePOS_StoreNumber_edit);
		storeNumberBootstrapEditText.setInputType(InputType.TYPE_CLASS_TEXT);
		storeNumberBootstrapEditText.addTextChangedListener(new TextWatcher() {

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
				if ((!s.toString().isEmpty())
						&& (!posNumberBootstrapEditText.getText().toString()
								.isEmpty())) {
					activeBootstrapButton.setEnabled(true);
				} else {
					activeBootstrapButton.setEnabled(false);
				}
			}
		});

		posNumberBootstrapEditText = (BootstrapEditText) view
				.findViewById(R.id.activePOS_POSNumber_edit);
		posNumberBootstrapEditText.setInputType(InputType.TYPE_CLASS_TEXT);
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
				if ((!s.toString().isEmpty())
						&& (!storeNumberBootstrapEditText.getText().toString()
								.isEmpty())) {
					activeBootstrapButton.setEnabled(true);
				} else {
					activeBootstrapButton.setEnabled(false);
				}
			}
		});

		activeBootstrapButton = (BootstrapButton) view
				.findViewById(R.id.activePOS_active_button);
		activeBootstrapButton.setEnabled(false);
		activeBootstrapButton.setOnClickListener(this);

	}

	private void updateView(Bundle bundle) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activePOS_active_button: {
			Log.i(TAG, "active");
			String storeNumber = storeNumberBootstrapEditText.getText()
					.toString();
			String posNumber = posNumberBootstrapEditText.getText().toString();
			mCallback.activePos(storeNumber, posNumber);
			break;
		}

		default:
			break;
		}
	}

	public interface OnActivePosPageListener {
		public void setTag(String tag);

		public void activePos(final String storeNumber, final String posNumber);
	}
}
