package com.zc.app.mpos.util;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.zc.app.mpos.R;
import com.zc.app.mpos.view.MyKeyboardView;

public class keyboardUtil {
	private MyKeyboardView keyboardView;
	private Keyboard k;// 数字键盘
	private EditText ed;

	public keyboardUtil(Activity act, Context ctx, EditText edit) {
		this.ed = edit;
		k = new Keyboard(ctx, R.xml.nativekeyboard);
		keyboardView = (MyKeyboardView) act.findViewById(R.id.keyboard_view);
		keyboardView.setKeyboard(k);
		keyboardView.setEnabled(true);
		keyboardView.setPreviewEnabled(false);
		keyboardView.setVisibility(View.VISIBLE);
		keyboardView.setOnKeyboardActionListener(listener);
	}

	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		@Override
		public void swipeUp() {
		}

		@Override
		public void swipeRight() {
		}

		@Override
		public void swipeLeft() {
		}

		@Override
		public void swipeDown() {
		}

		@Override
		public void onText(CharSequence text) {
		}

		@Override
		public void onRelease(int primaryCode) {
			if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
				hideKeyboard();
			}
		}

		@Override
		public void onPress(int primaryCode) {
		}

		// 一些特殊操作按键的codes是固定的比如完成、回退等
		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			Editable editable = ed.getText();
			int start = ed.getSelectionStart();
			if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
				if (editable != null && editable.length() > 0) {
					if (start > 0) {
						editable.delete(start - 1, start);
					}
				}
			} else if (primaryCode == 4896) {// 清空
				editable.clear();
			} else if (primaryCode == 10) { // OK
				hideKeyboard();
			} else { // 将要输入的数字现在编辑框中
				String currentString = editable.toString();
				if ((45 < primaryCode) && (primaryCode < 60)) {
					//只能有一个"."
					if ((currentString.contains(".")) && primaryCode == 46) {
						return;
					}
					//小数点后只可以有两位。
					if ((currentString.contains("."))
							&& (currentString.length() - currentString
									.indexOf(".")) == 3) {
						return;
					}
					
					if (((currentString.length() - currentString
							.indexOf(".")) > 3) && (primaryCode != 46)){
						return;
					}
					
					if ((currentString.length() == 0) && (primaryCode == 46)){
						editable.insert(0,
								Character.toString((char) 48));
						
						editable.insert(1,
								Character.toString((char) 46));
						
						start += 2;
						
						return;
					}
					
					editable.insert(start,
							Character.toString((char) primaryCode));
				}

			}
		}
	};

	/**
	 * 显示键盘
	 */
	public void showKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			keyboardView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏键盘
	 */
	public void hideKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.VISIBLE) {
			keyboardView.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 判断当前键盘是否可见
	 * 
	 * @return true为键盘可见，false为键盘不可见
	 */
	public boolean isShowKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.VISIBLE) {
			return true;
		}
		return false;
	}

}