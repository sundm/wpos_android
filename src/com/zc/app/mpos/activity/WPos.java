package com.zc.app.mpos.activity;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.apache.http.conn.ssl.SSLSocketFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.zc.app.mpos.R;
import com.zc.app.utils.ZCWebService;

/** 开场欢迎动画 */
public class WPos extends Activity {

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

				InputStream ins;
				try {
					ins = getApplicationContext().getAssets().open("wpos.key");
					CertificateFactory cerFactory = CertificateFactory
							.getInstance("X.509");
					Certificate cer = cerFactory.generateCertificate(ins);
					KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
					keyStore.load(null, null);
					keyStore.setCertificateEntry("trust", cer);
					SSLSocketFactory socketFactory = new SSLSocketFactory(
							keyStore);
					socketFactory
							.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
					ZCWebService.getInstance().setSSLSocketFactory(
							socketFactory);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CertificateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (KeyStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnrecoverableKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Intent intent = new Intent(WPos.this, LoginPage.class);
				startActivity(intent);
				WPos.this.finish();

				// if (!fristload) {
				//
				// Intent intent = new Intent(WelcomeA.this,
				// LoginPage.class);
				//
				// startActivity(intent);
				// WelcomeA.this.finish();
				//
				// } else {
				// editor.putBoolean("fristload", false);
				//
				// editor.commit();
				//
				//
				//
				// Intent intent = new Intent(WelcomeA.this,
				// WhatsnewPagesA.class);
				// startActivity(intent);
				// WelcomeA.this.finish();
				// }

			}
		}, 2000);
	}
}
