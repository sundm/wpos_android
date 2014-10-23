package com.zc.app.mpos.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.zc.app.mpos.R;

/** 开场欢迎动画 */
public class WelcomeA extends Activity {

	private SharedPreferences sharepreferences;

	private SharedPreferences.Editor editor;

	@SuppressLint("CommitPrefEdits")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.strat);

		// getActionBar().hide();

		sharepreferences = this.getSharedPreferences("check", MODE_PRIVATE);

		editor = sharepreferences.edit();

		final boolean fristload = sharepreferences
				.getBoolean("fristload", true);

		// 延迟两秒后执行run方法中的页面跳转ת
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				if (!fristload) {

					Intent intent = new Intent(WelcomeA.this,
							LoginPage.class);

					startActivity(intent);
					WelcomeA.this.finish();

				} else {
					editor.putBoolean("fristload", false);
					
					editor.commit();
					
					
					
					Intent intent = new Intent(WelcomeA.this,
							WhatsnewPagesA.class);
					startActivity(intent);
					WelcomeA.this.finish();
				}

			}
		}, 2000);
	}
}
