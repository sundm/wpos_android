package com.zc.app.boardcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zc.app.utils.ZCLog;

public class updateBoardCast extends BroadcastReceiver {
	private static String tag = "updateBoardCast";
	private static int num = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ZCLog.i(tag, "updateBoardCast onReceive");
	}
}
