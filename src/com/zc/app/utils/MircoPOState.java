package com.zc.app.utils;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MircoPOState {
	private String imeiString;
	private String uniqueIDString;

	private String keyID;
	private String psamID;

	private final static String TAG = "pos state";

	private Context _context;

	public MircoPOState(Context mContext) {
		this._context = mContext;
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);

		imeiString = tm.getDeviceId();
		String simSerialNumber = tm.getSimSerialNumber();
		String androidId = android.provider.Settings.Secure.getString(
				mContext.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);

		ZCLog.i(TAG, "IMEI: " + imeiString);
		ZCLog.i(TAG, "SN: " + simSerialNumber);
		ZCLog.i(TAG, "Android ID: " + androidId);

		if (simSerialNumber == null && androidId != null)
			simSerialNumber = androidId;

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) imeiString.hashCode() << 32)
						| simSerialNumber.hashCode());

		uniqueIDString = deviceUuid.toString();
		ZCLog.i(TAG, "unique ID: " + uniqueIDString);
	}

	public void getPOSInfoFromServer(final Handler _handler) {
		ZCLog.i(TAG, "get pos info from server");

		ZCWebService.getInstance().getPosInfo(new Handler() {
			@Override
			public void dispatchMessage(Message msg) {

				switch (msg.what) {
				case ZCWebServiceParams.HTTP_START: {
					ZCLog.i(TAG, msg.obj.toString());
					break;
				}
				case ZCWebServiceParams.HTTP_FINISH: {
					ZCLog.i(TAG, msg.obj.toString());
					break;
				}
				case ZCWebServiceParams.HTTP_FAILED: {
					ZCLog.i(TAG, msg.obj.toString());
					Toast.makeText(_context, msg.obj.toString(),
							Toast.LENGTH_LONG).show();

					Message msg2 = _handler.obtainMessage();
					msg2.what = ZCWebServiceParams.HTTP_FAILED;
					msg2.obj = msg.obj;
					_handler.sendMessage(msg2);

					break;
				}
				case ZCWebServiceParams.HTTP_SUCCESS: {
					ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

					ObjectMapper mapper = new ObjectMapper();

					try {
						requestUtil requestObj = mapper.readValue(
								msg.obj.toString(), requestUtil.class);

						ZCLog.i(TAG, requestObj.getDetail().getClass()
								.toString());

						@SuppressWarnings("unchecked")
						Map<String, Object> _mapperMap = (Map<String, Object>) requestObj
								.getDetail();

						keyID = _mapperMap.get("keyIndex").toString();
						psamID = _mapperMap.get("psamId").toString();

						ZCLog.i(TAG, "keyId: " + keyID);
						ZCLog.i(TAG, "psamId: " + psamID);

						Message msg2 = _handler.obtainMessage();
						msg2.what = ZCWebServiceParams.HTTP_SUCCESS;
						_handler.sendMessage(msg2);

					} catch (JsonParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (JsonMappingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					break;
				}
				case ZCWebServiceParams.HTTP_UNAUTH: {
					ZCLog.i(TAG, msg.obj.toString());
					Toast.makeText(_context, msg.obj.toString(),
							Toast.LENGTH_LONG).show();
					Message msg2 = _handler.obtainMessage();
					msg2.what = ZCWebServiceParams.HTTP_UNAUTH;
					_handler.sendMessage(msg2);
					break;
				}
				case ZCWebServiceParams.HTTP_THROWABLE: {
					Throwable e = (Throwable) msg.obj;
					ZCLog.e(TAG, "catch thowable:", e);
					break;
				}
				default: {
					ZCLog.i(TAG, "http nothing to do");
					break;
				}
				}
			}
		});
	}

	public String getIMEI() {
		return imeiString;
	}

	public String getUniqueIDString() {
		return uniqueIDString;
	}

	public String getKeyID() {
		return keyID;
	}

	public String getPsamID() {
		return psamID;
	}

}
