package com.zc.app.utils;

import net.sqlcipher.database.SQLiteDatabase;
import android.content.Context;

import com.zc.app.dataBase.updateDataBaseHelper;

public class ZCDataBase {
	private final static String TAG = "zcdatabase";
	private Context context;
	// dataBase
	private SQLiteDatabase db;

	private ZCDataBase() {
	}

	public void init(Context context) {
		if (this.context == null) {
			this.context = context;
		}

		if (this.context == null) {
			ZCLog.i(TAG, "context is null");
			return;
		}
		ZCLog.i(TAG, "initDateBase");
		SQLiteDatabase.loadLibs(context.getApplicationContext());
		updateDataBaseHelper dbHelper = new updateDataBaseHelper(
				context.getApplicationContext(), "wpos.db", null, 1);
		db = dbHelper.getWritableDatabase("secret_key");
	}

	private static ZCDataBase instance;

	public static ZCDataBase getInstance() {
		return instance == null ? (instance = new ZCDataBase()) : instance;
	}

	public SQLiteDatabase getDatabase() {
		return db;
	}
}
