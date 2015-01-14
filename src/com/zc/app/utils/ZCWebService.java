package com.zc.app.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.http.Header;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Handler;
import android.os.Message;

import com.zc.app.utils.crypto.RSA;
import com.zc.app.utils.http.AsyncHttpClient;
import com.zc.app.utils.http.AsyncHttpResponseHandler;
import com.zc.app.utils.http.RequestParams;

public class ZCWebService {
	private int timeOut;
	private ResourceFileManager resFileManager;
	private AsyncHttpClient _asyncHttpClient = new AsyncHttpClient();
	private static ZCWebService INSTANCE = new ZCWebService();
	private String _responseString;

	private static String tagString = "zcWebService";

	private boolean isLogin = false;

	private ZCWebService() {
	}

	public static ZCWebService getInstance() {
		return INSTANCE;
	}

	public int getRequestTimeoutSeconds() {
		return this.timeOut;
	}

	public boolean isLogin() {
		return this.isLogin;
	}

	public void setRequestTimeoutSeconds(int time) {
		this.timeOut = time;
		_asyncHttpClient.setTimeout(this.timeOut * 1000);
		ZCLog.i(tagString,
				"set request time out seconds:" + Integer.toString(time));
	}

	public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
		_asyncHttpClient.setSSLSocketFactory(sslSocketFactory);
		ZCLog.i(tagString, "set sslSocketFactory" + sslSocketFactory.toString());
	}

	public ResourceFileManager getResourceFileManager() {
		return this.resFileManager;
	}

	public void setResourceFileManager(
			ResourceFileManager paramResourceFileManager) {
		this.resFileManager = paramResourceFileManager;
	}

	public boolean doBasicGet(String url,
			ConcurrentHashMap<String, String> _paramMaps, final Handler _handler) {
		if (url == null || _handler == null) {
			return false;
		}

		if (url.isEmpty()) {
			return false;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("?");
		if (_paramMaps != null && _paramMaps.size() > 0) {
			Iterator<String> it = _paramMaps.keySet().iterator();

			while (it.hasNext()) {
				String key = it.next();
				builder.append(key);
				builder.append("=");
				builder.append(_paramMaps.get(key));
				if (it.hasNext()) {
					builder.append("&");
				}
			}

			String getUrlString = url + builder.toString();

			doGet(getUrlString, _handler);

		} else {
			doGet(url, _handler);
		}

		return true;
	}

	public boolean doBasicPost(String url,
			ConcurrentHashMap<String, String> _paramMaps, final Handler _handler) {
		if (url == null || _handler == null) {
			return false;
		}

		if (url.isEmpty()) {
			return false;
		}

		if (_paramMaps != null && _paramMaps.size() > 0) {
			String cryptoString = _paramMaps.get("_crypto_");
			ArrayList<String> cryptoList = new ArrayList<String>();
			JSONArray cryptoArray = null;
			RequestParams params = new RequestParams();

			if (cryptoString == null || cryptoString.isEmpty()) {
				Iterator<String> it = _paramMaps.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					params.put(key, _paramMaps.get(key));
				}

				doPost(params, url, _handler);
			} else {
				try {
					cryptoArray = new JSONArray(cryptoString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (cryptoArray == null) {
					return false;
				}

				for (int i = 0; i < cryptoArray.length(); i++) {
					try {
						cryptoList.add(cryptoArray.getString(i));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						cryptoList.clear();
					}
				}

				if (cryptoList.isEmpty()) {
					return false;
				} else {
					params.put("_crypto_", cryptoList);
				}

				Iterator<String> it = _paramMaps.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();

					if (key.equals("_crypto_")) {
						continue;
					}

					if (cryptoList.contains(key)) {
						// RSA crypto
						String valuesString = _paramMaps.get(key);
						RSA rsa = new RSA();
						String code = null;// =
											// HybridActivity._sharedPreferences
						// .getString("_code_", "");

						try {
							rsa.setPublicKey(code, "65537");
							String encString = rsa.Encrypt(valuesString);
							params.put(key, encString);
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvalidKeySpecException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvalidKeyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchPaddingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalBlockSizeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BadPaddingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						params.put(key, _paramMaps.get(key));
					}

				}

				doPost(params, url, _handler);

			}

		} else {
			doPost(null, url, _handler);
		}

		return true;
	}

	/**
	 * 用户登录
	 * 
	 * @param _loginParams
	 *            登录信息
	 * @param _handler
	 *            UI回调句柄
	 * @return
	 */
	public boolean userLogin(final UserInfo info, final String fingerprint,
			final Handler _handler) {

		if (_handler == null || info == null || fingerprint == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.LOGIN_URL;
		RequestParams params = new RequestParams();

		params.put("username", info.getUsername());
		params.put("password", info.getPassword());
		params.put("fingerprint", fingerprint);

		isLogin = false;

		doPost(params, postURL, _handler);
		return true;
	}

	public boolean userLogout(final Handler _handler) {
		if (_handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.LOGOUT_URL;

		doBasicPost(postURL, null, _handler);

		return true;
	}

	// public boolean userLogout(final Handler _handler) {
	// if (_handler == null) {
	// return false;
	// }
	//
	// CookieStore _cookie = _asyncHttpClient.getCookieStore();
	// if (_cookie == null) {
	// return false;
	// }
	//
	// _cookie.clear();
	// ZCLog.i(tagString, "user cookie cleared!");
	//
	// doGet(ZCWebServiceParams.LOGOUT_URL, _handler);
	// ZCLog.i(tagString, "server logout!");
	// return true;
	// }

	public boolean checkZipFile(final String md5String, final Handler _handler) {
		if (_handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.ZIP_URL;

		RequestParams params = new RequestParams();

		params.put("md5", md5String);

		doPost(params, postURL, _handler);

		return true;
	}

	public boolean getPosInfo(final Handler _handler) {
		if (_handler == null) {
			return false;
		}

		String posURL = ZCWebServiceParams.GETPOSINFO_URL;

		doPost(null, posURL, _handler);

		return true;
	}

	public boolean initForPurchase(PurchaseInitInfo info, final Handler _handler) {
		if (info == null || _handler == null) {
			return false;
		}

		String postUrl = ZCWebServiceParams.INITPURCHASE_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();

		paramsMap.put("initResponse", info.getInitResponse());
		paramsMap.put("amount", info.getAmount());
		paramsMap.put("pan", info.getPan());
		paramsMap.put("issuerId", info.getIssuerId());
		paramsMap.put("lng", info.getLng());
		paramsMap.put("lat", info.getLat());

		doBasicPost(postUrl, paramsMap, _handler);

		return true;
	}

	public boolean updateForPurchase(PurchaseUpdateInfo info,
			final Handler _handler) {
		if (info == null || _handler == null) {
			return false;
		}

		String postUrl = ZCWebServiceParams.UPDATEMAC2_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();

		paramsMap.put("sw", info.getSw());
		paramsMap.put("purchaseLogId", info.getLogId());
		paramsMap.put("tac", info.getTac());
		paramsMap.put("mac2", info.getMac2());
		paramsMap.put("walletBalanceLater", info.getBalance());

		doBasicPost(postUrl, paramsMap, _handler);

		return true;
	}

	public boolean getUserInfo(String userNameString, final Handler _handler) {
		if (userNameString == null || _handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.USERINFO_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("username", userNameString);
		// doPost(null, postURL);
		doBasicPost(postURL, paramsMap, _handler);
		return true;
	}

	public boolean setUserInfo(UserInfo info, final Handler _handler) {
		if (info == null || _handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.CHANGEUSER_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("validateCode", info.getValidateCode());
		paramsMap.put("phoneNumber", info.getPhonenumber());
		// doPost(null, postURL);
		doBasicPost(postURL, paramsMap, _handler);
		return true;
	}

	public boolean register(final UserInfo info, final Handler _handler) {
		if (info == null || _handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.REGISTER_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("username", info.getUsername());
		paramsMap.put("password", info.getPassword());
		paramsMap.put("validateCode", info.getValidateCode());
		paramsMap.put("phoneNumber", info.getPhonenumber());
		// doPost(null, postURL);
		doBasicPost(postURL, paramsMap, _handler);
		return true;

	}

	public boolean getRegisterCode(final String phone, final Handler _handler) {
		if (phone == null || _handler == null) {
			return false;
		}

		if (phone.isEmpty()) {
			return false;
		}

		String postURL = ZCWebServiceParams.REGISTER_CODE;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();

		paramsMap.put("phoneNumber", phone);
		// doPost(null, postURL);
		doBasicPost(postURL, paramsMap, _handler);
		return true;

	}

	public boolean getChangeCode(final Handler _handler) {
		if (_handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.CHANGEBYPOS_URL;

		doBasicPost(postURL, null, _handler);
		return true;

	}

	public boolean isExistUserName(final String username, final Handler _handler) {
		if (username == null || _handler == null) {
			return false;
		}

		if (username.isEmpty()) {
			return false;
		}

		String postURL = ZCWebServiceParams.REGISTER_USER_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();

		paramsMap.put("username", username);
		// doPost(null, postURL);
		doBasicPost(postURL, paramsMap, _handler);
		return true;

	}

	public boolean changePassword(final UserInfo info, final Handler _handler) {
		if (info == null || _handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.MODIFYPASSWORD_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("oldPassword", info.getOldpassword());
		paramsMap.put("newPassword", info.getNewpassword());
		// doPost(null, postURL);
		doBasicPost(postURL, paramsMap, _handler);
		return true;
	}

	// 激活POS
	public boolean activePOS(final WPosInfo info, final Handler _handler) {
		if (info == null || _handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.ACTIVE_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("terminalId", info.getTerminalId());
		paramsMap.put("merchantId", info.getMerchantId());
		paramsMap.put("fingerprint", info.getFingerprint());

		// doPost(null, postURL);
		doBasicPost(postURL, paramsMap, _handler);
		return true;

	}

	// 查询POS
	public boolean queryPOS(final Handler _handler) {
		if (_handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.QUERY_URL;

		doBasicPost(postURL, null, _handler);
		return true;

	}

	// 查询交易日志
	public boolean queryPurchaseLog(final String date, final Handler _handler) {
		if (date == null || _handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.QUERY_LOG_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("date", date);

		doBasicPost(postURL, paramsMap, _handler);
		return true;
	}

	// 申请POS验证码
	public boolean changePOSCode(final Handler _handler) {
		if (_handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.CHANGEBYPOS_URL;

		doBasicPost(postURL, null, _handler);
		return true;

	}

	// 更换POS
	public boolean changePOS(final WPosInfo info, final Handler _handler) {
		if (info == null || _handler == null) {
			return false;
		}

		String postURL = ZCWebServiceParams.VALIDATEPOS_URL;

		ConcurrentHashMap<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("merchantId", info.getMerchantId());
		paramsMap.put("validateCode", info.getValidateCode());
		paramsMap.put("fingerprint", info.getFingerprint());

		// doPost(null, postURL);
		doBasicPost(postURL, paramsMap, _handler);
		return true;

	}

	/**
	 * 查询交易记录
	 * 
	 * @param _detail
	 *            查询信息
	 * @param _handler
	 *            UI回调句柄
	 * @return
	 * 
	 *         public boolean doTradeDetailQuery(TradeDetailQuery _detail, final
	 *         Handler _handler) {
	 * 
	 *         if (_detail == null || _handler == null) { return false; }
	 * 
	 *         if (_detail.getCardNumber() == null || _detail.getDateStart() ==
	 *         null || _detail.getDateEnd() == null) { return false; }
	 * 
	 *         String postURL = ZCWebServiceParams.TRADEQUERY_URL; RequestParams
	 *         params = new RequestParams();
	 * 
	 *         params.put("cardNumber", _detail.getCardNumber());
	 *         params.put("dateStart", _detail.getDateStart());
	 *         params.put("dateEnd", _detail.getDateEnd());
	 * 
	 *         doPost(params, postURL, _handler);
	 * 
	 *         return true; }
	 */

	@SuppressWarnings("unused")
	private void doGetWithHeader(String url, Header _header,
			final Handler _handler) {
		setRequestTimeoutSeconds(5);

		_asyncHttpClient.get(url, _header, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				_responseString = response;
				ZCLog.i(tagString, _responseString);
				Message msg = _handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_SUCCESS;
				msg.obj = _responseString;

				_handler.sendMessage(msg);

				ZCLog.i(tagString, "uer login success");
				ZCLog.i(tagString, _responseString);
			}

			@Override
			public void onStart() {
				// Initiated the request
				ZCLog.i(tagString, "http get method start");
				Message msg = _handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_START;
				msg.obj = "http get method start";

				_handler.sendMessage(msg);
			}

			@Override
			public void onFailure(Throwable e, String response) {
				// ZCLog.e(tagString, response);//, e);
				ZCLog.e(tagString, response, e);
				Message msg = _handler.obtainMessage();

				HttpResponseException _e = null;
				try {
					_e = (HttpResponseException) e;
				} catch (Exception excp) {
					// TODO: handle exception
				}

				if (_e == null) {
					msg.what = ZCWebServiceParams.HTTP_FAILED;
					msg.obj = e.toString();
					_handler.sendMessage(msg);
				} else if (_e.getStatusCode() == 401) {
					msg.what = ZCWebServiceParams.HTTP_UNAUTH;
					msg.obj = "Unauthorized";
					_handler.sendMessage(msg);
				} else {
					msg.what = ZCWebServiceParams.HTTP_FAILED;
					msg.obj = _e.getStatusCode();
					_handler.sendMessage(msg);
				}
			}

			@Override
			public void onFinish() {
				ZCLog.i(tagString, "http get finish");
				Message msg = _handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_START;
				msg.obj = "http get method finish";
				_handler.sendMessage(msg);
			}
		});

	}

	private void doGet(String url, final Handler handler) {
		setRequestTimeoutSeconds(5);

		_asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				_responseString = response;
				ZCLog.i(tagString, _responseString);
				Message msg = handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_SUCCESS;
				msg.obj = _responseString;

				handler.sendMessage(msg);

				ZCLog.i(tagString, "uer login success");
				ZCLog.i(tagString, _responseString);
			}

			@Override
			public void onStart() {
				// Initiated the request
				ZCLog.i(tagString, "http get method start");
				Message msg = handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_START;
				msg.obj = "http get method start";

				handler.sendMessage(msg);
			}

			@Override
			public void onFailure(Throwable e, String response) {
				ZCLog.e(tagString, response, e);
				Message msg = handler.obtainMessage();

				HttpResponseException _e = null;

				try {
					_e = (HttpResponseException) e;
				} catch (Exception e2) {
					// TODO: handle exception
				}

				if (_e == null) {
					msg.what = ZCWebServiceParams.HTTP_FAILED;
					msg.obj = e.toString();
					handler.sendMessage(msg);
					return;
				}

				if (_e.getStatusCode() == 401) {
					msg.what = ZCWebServiceParams.HTTP_UNAUTH;
					msg.obj = "Unauthorized";
					handler.sendMessage(msg);
				} else {
					msg.what = ZCWebServiceParams.HTTP_FAILED;
					msg.obj = _e.getStatusCode();
					handler.sendMessage(msg);
				}

			}

			@Override
			public void onFinish() {
				ZCLog.i(tagString, "http get finish");
				Message msg = handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_START;
				msg.obj = "http get method finish";
				handler.sendMessage(msg);
			}
		});

	}

	private void doPost(RequestParams params, String url, final Handler handler) {
		setRequestTimeoutSeconds(30);

		ZCLog.i(tagString, url);
		if (params != null) {
			ZCLog.i(tagString, params.toString());
		}

		_asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				_responseString = response;
				ZCLog.i(tagString, "post success");
				ZCLog.i(tagString, _responseString);

				ObjectMapper mapper = new ObjectMapper();
				Message msg = handler.obtainMessage();

				try {
					requestUtil res = mapper.readValue(_responseString,
							requestUtil.class);

					if (res.getCode().equals("SUCCESS")) {
						msg.what = ZCWebServiceParams.HTTP_SUCCESS;
						msg.obj = _responseString;

						ZCLog.i(tagString, "success!");

						isLogin = true;
					} else if (res.getCode().equals("UN_AUTH")) {
						msg.what = ZCWebServiceParams.HTTP_UNAUTH;
						msg.obj = res.getDetail();

						ZCLog.i(tagString, msg.obj.toString());

						isLogin = true;
					} else if (res.getCode().equals(
							"WPOS_PURCHASE_UPDATE_SUCCESS")) {
						msg.what = ZCWebServiceParams.HTTP_PURCHASE_SUCCESS;
						msg.obj = _responseString;

						ZCLog.i(tagString, "Purchase success!");
					} else {
						msg.what = ZCWebServiceParams.HTTP_FAILED;
						if (res.getDetail() != null) {
							msg.obj = res.getDetail();
							ZCLog.i(tagString, msg.obj.toString());
						} else {
							msg.obj = res.getCode();
							ZCLog.i(tagString, msg.obj.toString());
						}

					}

				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					msg.what = ZCWebServiceParams.HTTP_FAILED;
					msg.obj = "返回数据处理失败!";
					ZCLog.i(tagString, "request failed!", e);

					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					msg.what = ZCWebServiceParams.HTTP_FAILED;
					msg.obj = "返回数据处理失败!";
					ZCLog.i(tagString, "request failed!", e);

					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					msg.what = ZCWebServiceParams.HTTP_FAILED;
					msg.obj = "返回数据处理失败!";
					ZCLog.i(tagString, "request failed!", e);

					e.printStackTrace();
				}

				handler.sendMessage(msg);

			}

			@Override
			public void onStart() {
				// Initiated the request
				ZCLog.i(tagString, "http post method start");
				Message msg = handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_START;
				msg.obj = "http post method start";

				handler.sendMessage(msg);
			}

			@Override
			public void onFailure(Throwable e, String response) {
				ZCLog.e(tagString, response, e);

				Message msg = handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_FAILED;
				if (e.getClass() == ConnectException.class
						|| e.getClass() == HttpHostConnectException.class
						|| e.getClass() == SocketTimeoutException.class
						|| e.getClass() == SocketException.class) {
					msg.obj = "网络不给力";
				} else {
					msg.obj = "服务器连接失败";
				}

				handler.sendMessage(msg);
			}

			@Override
			public void onFinish() {
				ZCLog.i(tagString, "http post finish");
				Message msg = handler.obtainMessage();
				msg.what = ZCWebServiceParams.HTTP_FINISH;
				msg.obj = "http post method finish";

				handler.sendMessage(msg);

			}
		});
	}

	@SuppressWarnings("unused")
	private String dateToString(Date _date) {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String reportDate = df.format(_date);
		ZCLog.i(tagString, reportDate);
		return reportDate;
	}

	public byte[] doDownload(String paramString, List<String> paramList1,
			List<String> paramList2) {
		return null;
	}

	public boolean doDownloadToSave(String paramString1,
			List<String> paramList1, List<String> paramList2,
			String paramString2) {
		return true;
	}

	public String doUpload(String paramString, List<String> paramList1,
			List<String> paramList2, List<String> paramList3,
			List<String> paramList4) {
		return null;
	}

	public byte[] doUploadAndDownload(String paramString,
			List<String> paramList1, List<String> paramList2,
			List<String> paramList3, List<String> paramList4) {
		return null;
	}

	public boolean doDownloadResource(String paramString1,
			List<String> paramList1, List<String> paramList2,
			String paramString2) {
		return true;
	}

	public String doUploadResource(String paramString, List<String> paramList1,
			List<String> paramList2, List<String> paramList3,
			List<String> paramList4) {
		return null;
	}
}