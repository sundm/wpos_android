package com.zc.app.boardcast;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.zc.app.utils.PurchaseUpdateInfo;
import com.zc.app.utils.ZCDataBase;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;

public class updateBoardCast extends BroadcastReceiver {
	private static String tag = "updateBoardCast";
	private static int num = 0;
	private Cursor cursor;
	private MyHandler _handler = new MyHandler();

	class MyHandler extends Handler {

		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case 0: {
				ZCLog.i(tag, "循环接受");
				if (cursor.moveToNext()) {
					doUpdate();
				} else {
					ZCLog.i(tag, "循环结束");
					cursor.close();
				}
				break;
			}
			case 1: {
				ZCLog.i(tag, "停止接受");
				cursor.close();
				break;
			}

			default:
				ZCLog.i(tag, "nothing to do");
				break;
			}
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ZCLog.e(tag, "updateBoardCast onReceive" + num++);
		SQLiteDatabase dbDatabase = ZCDataBase.getInstance().getDatabase();
		if (dbDatabase == null) {
			ZCLog.e(tag, "SQLiteDatabase is null");
			return;
		} else if (dbDatabase.isDbLockedByOtherThreads()) {
			ZCLog.e(tag, "SQLiteDatabase is locked by other threads");
			return;
		}
		cursor = ZCDataBase.getInstance().getDatabase()
				.query("wpos_update", null, null, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			ZCLog.i(tag, "contain update log");
			doUpdate();
		} else {
			cursor.close();
		}

	}

	private void doUpdate() {
		if (cursor != null) {
			ZCLog.i(tag, "contain update log");
			ZCLog.i(tag, cursor.toString());
			String sw = cursor.getString(cursor.getColumnIndex("sw"));
			final String logId = cursor.getString(cursor
					.getColumnIndex("logId"));
			String mac2 = cursor.getString(cursor.getColumnIndex("mac2"));
			String tac = cursor.getString(cursor.getColumnIndex("tac"));
			String balance = cursor.getString(cursor.getColumnIndex("balance"));

			PurchaseUpdateInfo updateInfo = new PurchaseUpdateInfo();
			updateInfo.setSw(sw);
			updateInfo.setLogId(logId);
			updateInfo.setMac2(mac2);
			updateInfo.setTac(tac);

			updateInfo.setBalance(balance);

			ZCLog.i(tag, updateInfo.toString());

			ZCWebService.getInstance().updateForPurchase(updateInfo,
					new Handler() {
						@Override
						public void dispatchMessage(Message msg) {

							switch (msg.what) {
							case ZCWebServiceParams.HTTP_THROWABLE: {
								ZCLog.i(tag, "HTTP_THROWABLE");
								ZCLog.i(tag, "消费结果上传失败");
								cursor.close();
								break;
							}
							case ZCWebServiceParams.HTTP_FAILED: {
								ZCLog.i(tag, "HTTP_FAILED");
								ZCLog.i(tag, "消费结果上传失败");
								ZCLog.i(tag, msg.obj.toString());

								if (!msg.obj.toString().equals("网络不给力")) {
									ZCDataBase
											.getInstance()
											.getDatabase()
											.delete("wpos_update",
													"logId=" + logId, null);

									doNext();
								}
								break;
							}
							case ZCWebServiceParams.HTTP_PURCHASE_SUCCESS: {
								ZCLog.i(tag, "消费结果上传成功");
								ZCDataBase
										.getInstance()
										.getDatabase()
										.delete("wpos_update",
												"logId=" + logId, null);

								doNext();
								break;
							}
							default: {
								break;
							}
							}
						}
					});

		}

	}

	private void doNext() {
		Message _msg = _handler.obtainMessage();
		_msg.what = 0;
		_handler.sendMessage(_msg);
		ZCLog.i(tag, "循环发送");
	}
}
