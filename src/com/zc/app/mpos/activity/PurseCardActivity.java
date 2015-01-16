package com.zc.app.mpos.activity;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zc.app.mpos.R;
import com.zc.app.sebc.lx.LongxingcardPurchase;
import com.zc.app.sebc.lx.LongxingcardRequest;
import com.zc.app.sebc.lx.NfcEnv;
import com.zc.app.sebc.pboc2.TransUtil;
import com.zc.app.utils.MD5Util;
import com.zc.app.utils.PurchaseInitInfo;
import com.zc.app.utils.PurchaseUpdateInfo;
import com.zc.app.utils.ZCDataBase;
import com.zc.app.utils.ZCLog;
import com.zc.app.utils.ZCWebService;
import com.zc.app.utils.ZCWebServiceParams;
import com.zc.app.utils.requestUtil;

public class PurseCardActivity extends Activity {

	TextView usernameTextView;
	TextView amountTextView;

	ImageView backImageView;

	private String keyIDString;
	private String psamIDString;
	private String username;
	private String amount;
	private double lngDouble;
	private double latDouble;

	private final static String TAG = "purse_card_page";

	// private int second = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_card_page);

		keyIDString = getIntent().getStringExtra("keyID");
		psamIDString = getIntent().getStringExtra("psamID");

		username = getIntent().getStringExtra("username");
		amount = getIntent().getStringExtra("amount");

		lngDouble = getIntent().getDoubleExtra("lng", 000.000);
		latDouble = getIntent().getDoubleExtra("lat", 000.000);

		usernameTextView = (TextView) findViewById(R.id.purse_card_pos_user_txt);
		setUser("收款人: " + username);
		amountTextView = (TextView) findViewById(R.id.purse_card_pos_amout_txt);
		setAmount("收款金额: " + amount + "元");
		backImageView = (ImageView) findViewById(R.id.purse_card_back);
		backImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		onNewIntent(getIntent());
	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
		NfcEnv.initNfcEnvironment(intent);
	}

	@Override
	public void onResume() {
		super.onResume();

		Intent intent_finish = new Intent(LoadingActivity.action);
		intent_finish.putExtra("data", 1);
		sendBroadcast(intent_finish);

		NfcEnv.enableNfcForegroundDispatch(this);

		if (NfcEnv.media == null || NfcEnv.media.connect() != 0) {
			return;
		}
		purchase(amount);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");

		NfcEnv.disableNfcForegroundDispatch(this);

	}

	private void setAmount(String w) {
		int start_amount = w.indexOf(":") + 1;

		int start = w.indexOf('.');

		int end = w.length() - 1;

		Spannable word = new SpannableString(w);

		word.setSpan(new AbsoluteSizeSpan(80), start, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		word.setSpan(new AbsoluteSizeSpan(110), start_amount, start,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		word.setSpan(new ForegroundColorSpan(0xffcf0005), start_amount, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		amountTextView.setText(word);
	}

	private void setUser(String w) {
		int start_user = w.indexOf(":") + 1;

		int end = w.length();

		Spannable word = new SpannableString(w);

		word.setSpan(new AbsoluteSizeSpan(90), start_user, end,

		Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		usernameTextView.setText(word);
	}

	private void purchase(final String amount) {
		ZCLog.i("consume", "doPurchase, amount: " + amount);
		ZCLog.i("consume", "keyId: " + keyIDString);
		ZCLog.i("consume", "psamId: " + psamIDString);

		LongxingcardRequest request = LongxingcardPurchase
				.requestInitCreditForPurchase_Longxing(amount, keyIDString,
						psamIDString);

		ZCLog.i("consume", request.toString());

		if (request.isOK()) {

			Intent loadingIntent = new Intent();
			loadingIntent.setClass(PurseCardActivity.this,
					LoadingActivity.class);
			startActivity(loadingIntent);

			String checkResult = TransUtil.checkInputAmount(amount, 8, 2, true);

			String strAmount = checkResult.substring(4, checkResult.length());

			int bl = Integer.parseInt(request.getBalanceString())
					- Integer.parseInt(strAmount);

			final String lastBalance = Integer.toString(bl);
			String temp = Integer.toString(bl, 16);
			while (temp.length() < 8) {
				temp = "0" + temp;
			}

			final String walletBalanceString = temp;

			ZCLog.i("consume", "lastBalance:" + lastBalance);
			ZCLog.i("consume", "walletBalanceString:" + walletBalanceString);

			ZCLog.i("consume", "init purchase");

			PurchaseInitInfo infoObj = new PurchaseInitInfo();
			infoObj.setInitResponse(request.getResponseString());
			infoObj.setAmount(request.getAmountString());
			infoObj.setPan(request.getPanString());
			infoObj.setIssuerId(request.getIssuerIdString());
			infoObj.setLng(String.valueOf(lngDouble));
			infoObj.setLat(String.valueOf(latDouble));

			ZCWebService.getInstance().initForPurchase(infoObj, new Handler() {
				@Override
				public void dispatchMessage(Message msg) {

					switch (msg.what) {
					case ZCWebServiceParams.HTTP_FAILED: {
						ZCLog.i(TAG, "init通讯失败");

						Intent it = new Intent(PurseCardActivity.this,
								PurseResultFailedActivity.class);

						it.putExtra("res", msg.obj.toString());

						startActivity(it);

						break;
					}
					case ZCWebServiceParams.HTTP_SUCCESS: {

						ZCLog.i(TAG, ">>>>>>>>>>>>>>>>" + msg.obj.toString());

						ObjectMapper mapper = new ObjectMapper();
						try {
							requestUtil requestObj = mapper.readValue(
									msg.obj.toString(), requestUtil.class);

							ZCLog.i(TAG, requestObj.getDetail().toString());

							@SuppressWarnings("unchecked")
							Map<String, Object> _mapperMap = (Map<String, Object>) requestObj
									.getDetail();

							String dataString = _mapperMap.get("tradingOrder")
									.toString();
							String purchaseLogId = _mapperMap.get(
									"purchaseLogId").toString();
							String mac2 = _mapperMap.get("mac2").toString();

							ZCLog.i(TAG, "tradingOrder:" + dataString);
							ZCLog.i(TAG, "purchaseLogId:" + purchaseLogId);
							ZCLog.i(TAG, "mac2:" + mac2);

							LongxingcardRequest _request = LongxingcardPurchase
									.requestCreditForPurche_Longxing(dataString);

							ZCLog.i("consume", _request.toString());

							if (_request.isOK()) {

								final PurchaseUpdateInfo updateInfo = new PurchaseUpdateInfo();
								updateInfo.setSw(_request.getSwString());
								updateInfo.setLogId(purchaseLogId);
								updateInfo.setMac2(_request.getMac2String());
								updateInfo.setTac(_request.getTacString());

								updateInfo.setBalance(walletBalanceString);

								ZCLog.i(TAG, updateInfo.toString());

								ZCWebService.getInstance().updateForPurchase(
										updateInfo, new Handler() {
											@Override
											public void dispatchMessage(
													Message msg) {

												switch (msg.what) {
												case ZCWebServiceParams.HTTP_THROWABLE: {
													ZCLog.i(TAG, "消费结果上传失败");

													addUpdateInfo(updateInfo);

													break;
												}
												case ZCWebServiceParams.HTTP_FAILED: {
													ZCLog.i(TAG, "消费结果上传失败");

													if (msg.obj.toString()
															.equals("网络不给力")) {
														addUpdateInfo(updateInfo);
													}

													break;
												}
												case ZCWebServiceParams.HTTP_PURCHASE_SUCCESS: {
													ZCLog.i(TAG, "消费结果上传成功");

													// addUpdateInfo(updateInfo);

													break;
												}
												case ZCWebServiceParams.HTTP_FINISH: {

													break;
												}
												default: {
													break;
												}
												}
											}
										});

								if (mac2.equals(MD5Util.md5String(_request
										.getMac2String()))) {
									Intent it = new Intent(
											PurseCardActivity.this,
											PurseResultOKActivity.class);
									it.putExtra("username", username);
									it.putExtra("amount", amount);
									it.putExtra("balance", lastBalance);
									startActivity(it);
									PurseCardActivity.this.finish();
								} else {
									ZCLog.i(TAG,
											"mac2 check failed:"
													+ MD5Util.md5String(_request
															.getMac2String()));

									Intent it = new Intent(
											PurseCardActivity.this,
											PurseResultFailedActivity.class);

									it.putExtra("res", "卡片异常");

									startActivity(it);
								}

							} else {

								final PurchaseUpdateInfo updateInfo = new PurchaseUpdateInfo();
								updateInfo.setSw(_request.getSwString());
								updateInfo.setLogId(purchaseLogId);
								updateInfo.setMac2("");
								updateInfo.setTac("");
								updateInfo.setBalance("");

								ZCLog.i(TAG, updateInfo.toString());

								Intent it = new Intent(PurseCardActivity.this,
										PurseResultFailedActivity.class);

								it.putExtra("res", "卡片操作失败");

								startActivity(it);

								ZCWebService.getInstance().updateForPurchase(
										updateInfo, new Handler() {
											@Override
											public void dispatchMessage(
													Message msg) {

												switch (msg.what) {
												case ZCWebServiceParams.HTTP_THROWABLE: {
													ZCLog.i(TAG,
															"HTTP_THROWABLE");
													ZCLog.i(TAG, "消费结果上传失败");
													addUpdateInfo(updateInfo);

													break;
												}
												case ZCWebServiceParams.HTTP_FAILED: {
													ZCLog.i(TAG, "HTTP_FAILED");
													ZCLog.i(TAG,
															msg.obj.toString());

													if (msg.obj.toString()
															.equals("网络不给力")) {
														addUpdateInfo(updateInfo);
													}

													break;
												}
												case ZCWebServiceParams.HTTP_PURCHASE_SUCCESS: {
													ZCLog.i(TAG, "消费结果上传成功");
													break;
												}
												case ZCWebServiceParams.HTTP_FINISH: {
													break;
												}
												default: {
													break;
												}
												}
											}
										});

							}

						} catch (JsonParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							Intent it = new Intent(PurseCardActivity.this,
									PurseResultFailedActivity.class);

							it.putExtra("res", "数据处理失败");

							startActivity(it);
						} catch (JsonMappingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							Intent it = new Intent(PurseCardActivity.this,
									PurseResultFailedActivity.class);

							it.putExtra("res", "数据处理失败");

							startActivity(it);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							Intent it = new Intent(PurseCardActivity.this,
									PurseResultFailedActivity.class);

							it.putExtra("res", "数据处理失败");

							startActivity(it);
						}

						break;
					}
					default:
						break;
					}
				}
			});
		} else {
			String reString = "卡片异常";
			if (request.getSwString().equals("9401")) {
				reString = "卡内余额不足";
			} else if (request.getSwString().equals("FF07")) {
				reString = "卡片连接异常";
			}

			Intent it = new Intent(PurseCardActivity.this,
					PurseResultFailedActivity.class);

			it.putExtra("res", reString);

			startActivity(it);

		}

	}

	private void addUpdateInfo(PurchaseUpdateInfo updateInfo) {
		ZCLog.i(TAG, "addUpdateInfo");
		ContentValues values = new ContentValues();
		values.put("sw", updateInfo.getSw());
		values.put("logId", updateInfo.getLogId());
		values.put("mac2", updateInfo.getMac2());
		values.put("tac", updateInfo.getTac());
		values.put("balance", updateInfo.getBalance());

		ZCDataBase.getInstance().getDatabase()
				.insert("wpos_update", null, values);
	}

}
