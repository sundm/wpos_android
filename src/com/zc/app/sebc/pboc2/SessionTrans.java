package com.zc.app.sebc.pboc2;

import android.util.Log;

import com.zc.app.sebc.bridge.TransDetailSetting;
import com.zc.app.sebc.iso7816.BerTlv;
import com.zc.app.sebc.iso7816.RApdu;
import com.zc.app.sebc.nfcmedia.Media;
import com.zc.app.sebc.pboc2.CApduPboc.PbocExternalAuth;
import com.zc.app.sebc.pboc2.CApduPboc.PbocGac;
import com.zc.app.sebc.pboc2.CApduPboc.PbocGetBalance;
import com.zc.app.sebc.pboc2.CApduPboc.PbocGetData;
import com.zc.app.sebc.pboc2.CApduPboc.PbocGpo;
import com.zc.app.sebc.pboc2.CApduPboc.PbocInternalAuth;
import com.zc.app.sebc.pboc2.CApduPboc.PbocPutData;
import com.zc.app.sebc.pboc2.CApduPboc.PbocReadRecord;
import com.zc.app.sebc.pboc2.CApduPboc.PbocSelect;
import com.zc.app.sebc.util.AndroidUtil;
import com.zc.app.sebc.util.BasicUtil;

public final class SessionTrans {
	
	public final static byte[] APP_NAME_PBOC20 = {												//card app name of PBOC2.0
			(byte)0xA0, (byte)0x00, (byte)0x00,
			(byte)0x03, (byte)0x33, (byte)0x01, (byte)0x01};	
	
	private final static byte[] BIT_TEMPLATE_DIGITAL_STAMP_IN_GAC1 = {							//mask in GAC1 for calculate digital stamp
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x7F,
			(byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0xAC, (byte)0x78};
	
	private final static String AUTH_CODE_ACCEPT_1 = "3030";
	private final static String AUTH_CODE_ACCEPT_2 = "3130";
	private final static String AUTH_CODE_ACCEPT_3 = "3131";
	private final static String AUTH_CODE_REFERENCE_1 = "3031";
	private final static String AUTH_CODE_REFERENCE_2 = "3032";
	
	private final static String[] AUTH_CODE_ACCEPT = {
			AUTH_CODE_ACCEPT_1,
			AUTH_CODE_ACCEPT_2,
			AUTH_CODE_ACCEPT_3,
			AUTH_CODE_REFERENCE_1,
			AUTH_CODE_REFERENCE_2
	};
	
	private static Media sessionMedia = null;													//media during a session
	private static RApdu sessionRApduSelect = null;												//select response during a session
	private static String sessionAmount = null;													//amount during a session
	private static String sessionDate = null;													//date during a session
	private static String sessionTime = null;													//time during a session
	private static String sessionTrader = null;													//trader during a session
	private static String sessionType = null;													//type during a session
	private static String sessionCurrency = null;												//currency during a session
	private static String sessionCountry = null;												//country during a session
	private static long sessionSum = -1;														//sum during a session
	private static byte[] sessionCdol2 = null;													//CDOL2 during a session
	
	private static long sessionStartTime = 0;
	private static long sessionTimeout = 0;
	
	//transaction apdu does not guarantee input parameters, the caller checks this
	private static RApdu transSelect(Media media, byte[] appName) {
		
		PbocSelect pbocSelect = new PbocSelect(appName);
		RApdu rApdu = media.sendApdu(pbocSelect);
		Log.i("shishi: select", BasicUtil.bytesToHexString(pbocSelect.apdu, 0, pbocSelect.apdu.length));
		Log.i("shishi: select", rApdu.getHexString());
		return rApdu;
	}
	
	//transaction apdu does not guarantee input parameters, the caller checks this
	private static RApdu transGpo(Media media, byte[] bytResp,
			byte[] bytTransChallenge, byte[] bytTransAmount,
			byte[] bytTransDate, byte[] bytTransTime, byte[] bytTransTrader,
			byte[] bytTransType, byte[] bytTransCurrency, byte[] bytTransCountry) {
		
		int i;
		int j;
		int lenPdol = -1;
		int loc9A = -1;
		int loc9C = -1;
		int loc5F2A = -1;
		int loc9F1A = -1;
		int loc9F21 = -1;
		int loc9F4E = -1;
		int loc9F66 = -1;
		int locAmount = -1;
		int locChallenge = -1;
		
		//get PDOL
		byte bytVPdol[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_PDOL);
		if(bytVPdol != null) {																	//PDOL exist
			
			lenPdol = Dol.getDolDataLen(bytVPdol, 0, bytVPdol.length, false);
			if(lenPdol < 0) {
				return new RApdu(Sw1Sw2.SW1SW2_F801);
			}
			
			//get tag location in DOL
			loc9A = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_9A);
			loc9C = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_9C);
			loc5F2A = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_5F2A);
			loc9F1A = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_9F1A);
			loc9F21 = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_9F21);
			loc9F4E = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_9F4E);
			loc9F66 = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_9F66);
			locAmount = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_9F02);
			locChallenge = Dol.getValLocInDol(bytVPdol, 0, bytVPdol.length, TlvTag.TAG_B_9F37);
		}
		else {
			lenPdol = 0;
		}
		
		byte pdol[] = null;
		if(lenPdol > 0) {
			//initialize APDU data
			pdol = new byte[lenPdol];
			BasicUtil.initByteArray(pdol, (byte)0x00);
			
			//add tag value
			if(loc9A > -1) {
				if(bytTransDate != null) {
					if(bytTransDate.length == 3) {
						for(i = 0; i < 3; i++) {
							pdol[loc9A+i] = bytTransDate[i];
						}
					}
				}
			}
			
			if(loc9C > -1) {
				if(bytTransType != null) {
					if(bytTransType.length == 1) {
						for(i = 0; i < 1; i++) {
							pdol[loc9C+i] = bytTransType[i];
						}
					}
				}
			}
			
			if(loc5F2A > -1) {
				for(i = 0; i < 2; i++) {
					pdol[loc5F2A+i] = TlvTag.VAL_B_D_5F2A[i];
				}
				if(bytTransCurrency != null) {
					if(bytTransCurrency.length == 2) {
						for(i = 0; i < 2; i++) {
							pdol[loc5F2A+i] = bytTransCurrency[i];
						}
					}
				}
			}
			
			if(loc9F1A > -1) {
				for(i = 0; i < 2; i++) {
					pdol[loc9F1A+i] = TlvTag.VAL_B_D_9F1A[i];
				}
				if(bytTransCountry != null) {
					if(bytTransCountry.length == 2) {
						for(i = 0; i < 2; i++) {
							pdol[loc9F1A+i] = bytTransCountry[i];
						}
					}
				}
			}
			
			if(loc9F21 > -1) {
				if(bytTransTime != null) {
					if(bytTransTime.length == 3) {
						for(i = 0; i < 3; i++) {
							pdol[loc9F21+i] = bytTransTime[i];
						}
					}
				}
			}
			
			if(loc9F4E > -1) {
				if(bytTransTrader != null) {
					if(bytTransTrader.length < 21) {
						for(i = 0; i < bytTransTrader.length; i++) {
							pdol[loc9F4E+i] = bytTransTrader[i];
						}
					} else {
						for(i = 0; i < 20; i++) {
							pdol[loc9F4E+i] = bytTransTrader[i];
						}
					}
				}
			}
			
			if(loc9F66 > -1) {
				for(i = 0; i < 4; i++) {
					pdol[loc9F66+i] = TlvTag.VAL_B_D_9F66[i];
				}
			}
			
			//if amount exist in PDOL
			if(locAmount > -1) {
				if(bytTransAmount != null) {	
					for(j = 0; j < bytTransAmount.length; j++) {
						pdol[locAmount+j] = bytTransAmount[j];
					}
				}
			}
			
			//if challenge exist in PDOL
			if(locChallenge > -1) {
				if(bytTransChallenge != null) {				
					for(j = 0; j < bytTransChallenge.length; j++) {
						pdol[locChallenge+j] = bytTransChallenge[j];
					}
				}
			}
		}
		
		//send APDU GPO
		PbocGpo pbocGpo = new PbocGpo(pdol);
		RApdu rApdu = media.sendApdu(pbocGpo);
		Log.i("shishi: gpo", BasicUtil.bytesToHexString(pbocGpo.apdu, 0, pbocGpo.apdu.length));
		Log.i("shishi: gpo", rApdu.getHexString());
		return rApdu;
	}
	
	public static String selectCardApp(Media media, byte[] appName) {
		
		String sw1sw2;
		if((sw1sw2 = Media.checkMedia(media)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		if(appName == null) {
			return Sw1Sw2.SW1SW2_FD0C;
		} else if(appName.length < 5) {
			return Sw1Sw2.SW1SW2_FD0D;
		}
		
		String strApduResp = null;
		
		media.connect();
		strApduResp = transSelect(media, appName).getHexString();
		media.close();
		
		return strApduResp;
	}
	
	//mode: true, show original pan except the digits indicated by start and length, which are replaced by 'mark';
	//		false, show original pan indicated by start and length, other digits are replaced by 'mark'
	//order: true, count from left; false, count from right
	//seperator: true, there is seperator mark1, every length1
	//			 false, no seperator
	//order1: true, seperate from left; false, seperate from right
	public static String getCardNumber(Media media, byte[] appName,
			String strChallange, String strAmount,
			boolean mode, boolean order, int start, int length, byte mark,
			boolean seperator, boolean order1, int length1, byte mark1) {
		
		String track2Data = getTrack2Data(media, appName, strChallange, strAmount);
		String sw1sw2 = track2Data.substring(0, 4);
		if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		track2Data = track2Data.substring(4, track2Data.length());
		return calculateCardNumber(track2Data,
				mode, order, start, length, mark,
				seperator, order1, length1, mark1);
	}
	
	//mode: true, show original string except the digits indicated by start and length which are marked by 'mark';
	//		false, show original string marked by start and length, other digits are marked by 'mark'
	//order: true, count from left; false, count from right
	//seperator: true, there is seperator mark1, every length1
	//			 false, no seperator
	//order1: true, seperate from left; false, seperate from right
	private static String calculateCardNumber(String track2Data,
			boolean mode, boolean order, int start, int length, byte mark,
			boolean seperator, boolean order1, int length1, byte mark1) {
		
		if(track2Data == null) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		int lenTrack2Data = track2Data.length();
		if(lenTrack2Data < (24+2)) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		track2Data = track2Data.substring(2, lenTrack2Data);
		lenTrack2Data -= 2;
		
		int offset = -1;
		byte[] valTrack2 = track2Data.getBytes();
		offset = BasicUtil.locByteInByteString(valTrack2, (byte)0x44);
		if(offset < 16) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		String pan = new String(valTrack2, 0, offset);
		String temp = pan.substring(pan.length()-1, pan.length());
		if((temp.equals("F")) || (temp.equals("f"))) {
			pan = pan.substring(0, pan.length()-1);
		}
		
		return TransUtil.toReadableString(pan,
				mode, order, start, length, mark,
				seperator, order1, length1, mark1);
	}
	
	//get the card information
	public static String getTrack2Data(Media media, byte[] appName,
			String strChallange, String strAmount) {
		
		String sw1sw2;
		if((sw1sw2 = Media.checkMedia(media)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		if(appName == null) {
			appName = APP_NAME_PBOC20;
		} else {
			if(appName.length < 7) {
				appName = APP_NAME_PBOC20;
			}
		}
		
		if(strChallange == null) {
			strChallange = TlvTag.VAL_D_9F37;
		} else {
			sw1sw2 = TransUtil.checkInputChallenge(strChallange, 8);
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				return sw1sw2;
			}
		}
		
		if(strAmount == null) {
			strAmount = TlvTag.VAL_D_9F02;
		} else {
			String checkResult = TransUtil.checkInputAmount(strAmount, 12, 2, false);
			sw1sw2 = checkResult.substring(0, 4);
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				return sw1sw2;
			}
			strAmount = checkResult.substring(4, checkResult.length());
		}
		
		String strApduResp = null;
		
		media.connect();		
		strApduResp = calculateTrack2Data(media, appName, strChallange, strAmount);
		media.close();
		
		return strApduResp;
	}
	
	//calculate card info
	private static String calculateTrack2Data(Media media, byte[] appName,
			String strChallange, String strAmount) {
		
		int lenL;
		int[] lenV = {0};
		byte[] bytResp = null;
		String strResp = null;
		RApdu rApdu = null;
		String sw1sw2 = null;
		
		//get challenge
		byte[] bytTransChallenge = null;
		if(strChallange != null) {
			bytTransChallenge = BasicUtil.byteAsciiToByteBcd(strChallange.getBytes(), false, false);
			if(bytTransChallenge == null) {
				return Sw1Sw2.SW1SW2_FC60;
			}
		}
		
		//get amount
		byte[] bytTransAmount = null;
		if(strAmount != null) {
			bytTransAmount = BasicUtil.byteAsciiToByteBcd(strAmount.getBytes(), false, false);
			if(bytTransAmount == null) {
				return Sw1Sw2.SW1SW2_FC60;
			}
		}
		
		//select application
		rApdu = transSelect(media, appName);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		bytResp = rApdu.getDataBytes();
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		
		if(lenL < 0) {
			return Sw1Sw2.SW1SW2_F801;
		}
		if(bytResp.length < (1 + lenL + lenV[0])) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		String curTime = null;
		byte[] bytCurTime = null;
		curTime = AndroidUtil.getCurTime();
		bytCurTime = BasicUtil.byteAsciiToByteBcd(curTime.getBytes(), false, false);
		
		if(bytCurTime == null) {
			return Sw1Sw2.SW1SW2_FD81;
		} else if(bytCurTime.length != 3) {
			return Sw1Sw2.SW1SW2_FD81;
		}
		
		String curDate = null;
		byte[] bytCurDate = null;
		curDate = AndroidUtil.getCurDate();
		bytCurDate = BasicUtil.byteAsciiToByteBcd(curDate.getBytes(), false, false);
		
		if(bytCurDate == null) {
			return Sw1Sw2.SW1SW2_FD81;
		} else if(bytCurDate.length != 3) {
			return Sw1Sw2.SW1SW2_FD81;
		}
		
		rApdu = transGpo(media, rApdu.getBytes(), bytTransChallenge, bytTransAmount,
				bytCurDate, bytCurTime, null,
				null, null, null);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		byte[] bytRespGpo = rApdu.getDataBytes();
		
		lenV[0] = 0;
		lenL = BerTlv.getLenLen(bytRespGpo, 1, lenV);
		if(lenL < 0) {
			return Sw1Sw2.SW1SW2_F801;
		}
		if(bytRespGpo.length < (1 + lenL + lenV[0])) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		//check GPO response, AFL must exist
		int lenRespGpo = bytRespGpo.length;
		int lenAfl = bytRespGpo.length-1 -lenL-2;
		if((lenAfl < 4) || ((lenAfl%4) !=0 )) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		byte sfi;
		byte recEnd;
		byte recStart;
		int offsetAfl = 1+lenL+2;
		boolean track2Found = false;
		boolean errOccured = false;
		String strTrack2Lv = null;
		
		int offset = 0;
		int lenTrack2 = -1;
		
		while(lenRespGpo > offsetAfl) {
			
			sfi = (byte)(bytRespGpo[offsetAfl]&0xF8);
			recStart = (byte)(bytRespGpo[offsetAfl+1]&0xFF);
			recEnd = (byte)(bytRespGpo[offsetAfl+2]&0xFF);
			
			while((recStart&0xFF) <= (recEnd&0xFF)) {
				
				PbocReadRecord pbocReadRecord = new PbocReadRecord(sfi, recStart);
				sw1sw2 = Sw1Sw2.SW1SW2_OK;
				rApdu = media.sendApdu(pbocReadRecord);								//Read Record
				
				//check response
				if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
					if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A83) == true) {
						recStart++;
						continue;
					} else {
						errOccured = true;
						break;
					}
				}
				
				if(track2Found == false) {
					
					bytResp = rApdu.getDataBytes();
					
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_TRACK2);
					if(offset > 0) {																		//found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenTrack2 = lenV[0];
						if(lenTrack2 < 12) {
							errOccured = true;
							break;
						}
						
						if(bytResp.length < (offset + lenL + lenTrack2)) {
							errOccured = true;
							break;
						}
						
						strResp = rApdu.getDataHexString();
						strTrack2Lv = strResp.substring(offset+offset, offset+offset + lenL+lenL + lenTrack2+lenTrack2);
						track2Found = true;
						break;
					}
				}
				
				recStart++;
			}
			
			if((track2Found == true) || (errOccured == true)) {
				break;
			}
			
			offsetAfl += 4;
		}
		
		if(errOccured == true) {
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				return sw1sw2;
			}
			else {
				return Sw1Sw2.SW1SW2_F801;
			}
		}
		
		if(track2Found == false) {
			return Sw1Sw2.SW1SW2_OK;
		} else {
			return Sw1Sw2.SW1SW2_OK + strTrack2Lv;
		}
	}
	
	//request EP balance
	public static String getEpBalance(Media media, byte[] appName) {
		
		String sw1sw2;
		if((sw1sw2 = Media.checkMedia(media)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		if(appName == null) {
			return Sw1Sw2.SW1SW2_FD0C;
		} else {
			if(appName.length < 5) {
				return Sw1Sw2.SW1SW2_FD0D;
			}
		}
		
		String strApduResp = null;
		
		media.connect();		
		strApduResp = calculateEpBalance(media, appName);
		media.close();
		
		return strApduResp;
	}
	
	//calculate EP balance
	private static String calculateEpBalance(Media media, byte[] appName) {
		
		RApdu rApdu = null;
		byte[] bytResp = null;
		String sw1sw2 = null;
		
		rApdu = transSelect(media, appName);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		PbocGetBalance pbocGetBalance = new PbocGetBalance((byte)0x02);
		rApdu = media.sendApdu(pbocGetBalance);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		bytResp = rApdu.getDataBytes();
		if(bytResp == null) {
			return Sw1Sw2.SW1SW2_F801;
		} else if(bytResp.length != 4) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		long lngEpBalance = BasicUtil.bytesToLong(bytResp, 0, bytResp.length);
		String strEpBalance = Long.toString(lngEpBalance);
		return Sw1Sw2.SW1SW2_OK+strEpBalance;
	}
	
	public static String getEccBalanceLimit(Media media, byte[] appName) {
		
		String limit = getEccInfo(media, appName, TlvTag.TAG_B_9F77);
		String sw1sw2 = limit.substring(0, 4);
		
		if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == true) {
			limit = limit.substring(4, limit.length());
			limit = TransUtil.toReadableAmount(limit);
			limit = sw1sw2 + limit;
			return limit;
		} else {
			return sw1sw2;
		}
	}
	
	public static String getEccBalance(Media media, byte[] appName) {
		
		String limit = getEccInfo(media, appName, TlvTag.TAG_B_9F79);
		String sw1sw2 = limit.substring(0, 4);
		
		if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == true) {
			limit = limit.substring(4, limit.length());
			limit = TransUtil.toReadableAmount(limit);
			limit = sw1sw2 + limit;
			return limit;
		} else {
			return sw1sw2;
		}
	}
	
	//request ECC info, only applies tag whose value is 6 bytes long
	public static String getEccInfo(Media media, byte[] appName, byte[] infoTag) {
		
		String sw1sw2;
		if((sw1sw2 = Media.checkMedia(media)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		if(appName == null) {
			appName = APP_NAME_PBOC20;
		}
		else {
			if(appName.length < 7) {
				appName = APP_NAME_PBOC20;
			}
		}
		
		if(infoTag == null) {
			return Sw1Sw2.SW1SW2_FD18;
		} else if(infoTag.length != 2) {
			return Sw1Sw2.SW1SW2_FD19;
		}
		
		String strApduResp = null;
		
		media.connect();		
		strApduResp = calculateEccInfo(media, appName, infoTag);
		media.close();
		
		return strApduResp;
	}
	
	//calculate ECC info, only applies tag whose value is 6 bytes long
	private static String calculateEccInfo(Media media, byte[] appName, byte[] infoTag) {
		
		RApdu rApdu = null;
		String strResp = null;
		String sw1sw2 = null;
		
		rApdu = transSelect(media, appName);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		PbocGetData pbocGetData = new PbocGetData(infoTag[0], infoTag[1]);
		rApdu = media.sendApdu(pbocGetData);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		strResp = rApdu.getDataHexString();
		if(strResp == null) {
			return Sw1Sw2.SW1SW2_F801;
		} else if(strResp.length() < (2+1+6)*2) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		String strVal = strResp.substring(6, 18);
		return Sw1Sw2.SW1SW2_OK+strVal;
	}
	
	public static String[][] getReadableTransactionDetails(Media media, byte[] appName,
			byte[] entryTag, byte[] formatTag, byte[] oldEntry,
			byte sfi, String template,
			int headReadId,
			String[] outerTemplate, String[] deletedType,
			boolean order,
			boolean deleteInit, byte init) {
		
		String[][] strApduResp = getTransactionDetails(media, appName,
				entryTag, formatTag, oldEntry,
				sfi, template,
				outerTemplate, deletedType,
				order,
				deleteInit, init);
		
		String sw1sw2 = strApduResp[0][0];
		if((sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) && (sw1sw2.equals(Sw1Sw2.SW1SW2_OK1) == false)) {
			return strApduResp;
		}
		
		if(strApduResp.length < 2) {
			strApduResp[0][0] = Sw1Sw2.SW1SW2_FC60;
			return strApduResp;
		}
		
		strApduResp = TransUtil.toReadableTableMatrix(strApduResp, 1, strApduResp.length-2, false, headReadId);
		return strApduResp;
	}
	
	public static String[][] getReadableTransactionDetails(Media media, byte[] appName,
			byte[] entryTag, byte[] formatTag, byte[] oldEntry,
			byte sfi, String template,
			int headReadId,
			boolean deleteInit, byte init) {
		
		String[][] strApduResp = getTransactionDetails(media, appName,
				entryTag, formatTag, oldEntry,
				sfi, template,
				deleteInit, init);
		
		String sw1sw2 = strApduResp[0][0];
		if((sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) && (sw1sw2.equals(Sw1Sw2.SW1SW2_OK1) == false)) {
			return strApduResp;
		}
		
		if(strApduResp.length < 2) {
			strApduResp[0][0] = Sw1Sw2.SW1SW2_FC60;
			return strApduResp;
		}
		
		strApduResp = TransUtil.toReadableTableMatrix(strApduResp, 1, strApduResp.length-2, false, headReadId);
		return strApduResp;
	}
	
	public static String[][] getTransactionDetails(Media media, byte[] appName,
			byte[] entryTag, byte[] formatTag, byte[] oldEntry,
			byte sfi, String template,
			String[] outerTemplate, String[] deletedType,
			boolean order,
			boolean deleteInit, byte init) {
		
		String[][] strApduResp = getTransactionDetails(media, appName,
				entryTag, formatTag, oldEntry,
				sfi, template,
				deleteInit, init);
		if((strApduResp[0][0].equals(Sw1Sw2.SW1SW2_OK) == false) && (strApduResp[0][0].equals(Sw1Sw2.SW1SW2_OK1) == false)) {
			return strApduResp;
		}
		
		strApduResp = TransUtil.adjustTableMatrix(outerTemplate, strApduResp, 1, deletedType, order);
		return strApduResp;
	}
	
	public static String[][] getTransactionDetails(Media media, byte[] appName,
			byte[] entryTag, byte[] formatTag, byte[] oldEntry,
			byte sfi, String template,
			boolean deleteInit, byte init) {
		
		String[][] strApduResp = new String[1][1];
		String sw1sw2;
		
		if((sw1sw2 = Media.checkMedia(media)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strApduResp[0][0] = sw1sw2;
			return strApduResp;
		}
		
		if(appName == null) {
			appName = APP_NAME_PBOC20;
		} else {
			if(appName.length < 7) {
				appName = APP_NAME_PBOC20;
			}
		}
		
		media.connect();		
		strApduResp = calculateTransactionDetails(media, appName,
				entryTag, formatTag, oldEntry,
				sfi, template,
				deleteInit, init);
		media.close();
		
		return strApduResp;
	}
	
	//sfi: 0, pboc detail; other, ep detail, with template indicating detail template
	private static String[][] calculateTransactionDetails(Media media, byte[] appName,
			byte[] entryTag, byte[] formatTag, byte[] oldEntry,
			byte sfi, String template,
			boolean deleteInit, byte init) {
		
		int lenT;
		int lenL;
		int[] lenV = {0};
		String[][] strErrResp = new String[1][1];
		byte[] bytResp = null;
		byte[] bytRespGetData = null;
		RApdu rApdu = null;
 		String strResp = null;
		String sw1sw2 = null;
		
		//select application
		rApdu = transSelect(media, appName);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0][0] = sw1sw2;
			return strErrResp;
		}
		
		bytResp = rApdu.getDataBytes();
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		
		if(lenL < 0) {
			strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		if(bytResp.length < (1 + lenL + lenV[0])) {
			strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		bytResp = rApdu.getDataBytes();
		int i;
		int j;
		int records = 0;
		boolean flag;
		boolean flagDol = true;
		int[] dolInfo = null;
		String[][] strDetails = null;
		
		if(sfi == (byte)0x00) {
			
			byte bytVEntry[] = null;			
			if(entryTag != null) {
				i = BerTlv.getTagLen(entryTag, 0, false);
				if(i < 0) {
					strErrResp[0][0] = Sw1Sw2.SW1SW2_F804;
					return strErrResp;
				}
				bytVEntry = BerTlv.getTlvValue(bytResp, entryTag);
			}
			
			if(bytVEntry == null) {
				if(oldEntry != null) {
					if(oldEntry.length == 2) {
						bytVEntry = oldEntry;
					} else {
						strErrResp[0][0] = Sw1Sw2.SW1SW2_F804;
						return strErrResp;
					}
				} else {
					strErrResp[0][0] = Sw1Sw2.SW1SW2_F803;
					return strErrResp;
				}
			}
			
			if(bytVEntry.length < 2) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F804;
				return strErrResp;
			} else {
				if(((bytVEntry[0]&0xFF) < 1) || ((bytVEntry[0]&0xFF) > 31)) {
					strErrResp[0][0] = Sw1Sw2.SW1SW2_F804;
					return strErrResp;
				}
				if((bytVEntry[1]&0xFF) < 1) {
					strErrResp[0][0] = Sw1Sw2.SW1SW2_F804;
					return strErrResp;
				}
			}
			
			if(formatTag != null) {
				i = BerTlv.getTagLen(formatTag, 0, false);
				if(i < 0) {
					strErrResp[0][0] = Sw1Sw2.SW1SW2_F804;
					return strErrResp;
				}
			} else {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F803;
				return strErrResp;
			}
			
			PbocGetData pbocGetData = new PbocGetData(formatTag);
			rApdu = media.sendApdu(pbocGetData);
			if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
				strErrResp[0][0] = sw1sw2;
				return strErrResp;
			}
			
			bytRespGetData = rApdu.getDataBytes();
			lenT = BerTlv.getTagLen(bytRespGetData, 0, false);
			if(lenT != i) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
			
			lenV[0] = 0;
			lenL = BerTlv.getLenLen(bytRespGetData, lenT, lenV);		
			if(lenL < 0) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
			if(bytRespGetData.length != (lenT + lenL + lenV[0])) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
			
			if(BasicUtil.isBcdString1GreaterThanBcdString2(entryTag, TlvTag.TAG_B_DF4D) == 0) {
				if(BasicUtil.isBcdString1GreaterThanBcdString2(formatTag, TlvTag.TAG_B_DF4F) == 0) {
					
					i = lenV[0]+TransDetailSetting.TEMPLATE_DEFAULT_LOAD_DETAILS_PREFIX.length;
					byte[] tempResp = new byte[i];
					
					for(j = 0; j < TransDetailSetting.TEMPLATE_DEFAULT_LOAD_DETAILS_PREFIX.length; j++) {
						tempResp[j] = TransDetailSetting.TEMPLATE_DEFAULT_LOAD_DETAILS_PREFIX[j];
					}
					for(j = 0; j < lenV[0]; j++) {
						tempResp[TransDetailSetting.TEMPLATE_DEFAULT_LOAD_DETAILS_PREFIX.length+j] = bytRespGetData[lenT+lenL+j];
					}
					
					flagDol = false;
					bytRespGetData = tempResp;
				}
			}
			
			dolInfo = Dol.getDolTagNumberAndDataLen(bytRespGetData, 0, bytRespGetData.length, flagDol);
			if(dolInfo[0] < 0) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			} else {
				if((dolInfo[1] < 1) || (dolInfo[2] < 1)) {
					strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
					return strErrResp;
				}
			}
			
			sfi = bytVEntry[0];
			records = bytVEntry[1]&0xFF;
			strDetails = new String[2+records][dolInfo[1]];
			String[][] strTagsAndVals = null;
			
			strTagsAndVals = Dol.getDolTagsAndLens(bytRespGetData, 0, bytRespGetData.length, dolInfo[1], flagDol);
			if(strTagsAndVals == null) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
			
			strDetails[1] = strTagsAndVals[0];
			flag = true;
			
		} else {
			
			if((sfi&0xFF) > 31) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
			
			if(BasicUtil.isEmptyString(template)) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
			
			bytRespGetData = BasicUtil.byteAsciiToByteBcd(template.getBytes(), false, false);
			if(bytRespGetData == null) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
			
			dolInfo = Dol.getDolTagNumberAndDataLen(bytRespGetData, 0, bytRespGetData.length, false);
			if(dolInfo[0] < 0) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			} else {
				if((dolInfo[1] < 1) || (dolInfo[2] < 1)) {
					strErrResp[0][0] = Sw1Sw2.SW1SW2_FC60;
					return strErrResp;
				}
			}
			
			strDetails = new String[2+255][dolInfo[1]];
			String[][] strTagsAndVals = null;
			
			strTagsAndVals = Dol.getDolTagsAndLens(bytRespGetData, 0, bytRespGetData.length, dolInfo[1], false);
			if(strTagsAndVals == null) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
			
			strDetails[1] = strTagsAndVals[0];
			records = 255;
			flag = false;
		}
		
		String[] strDolVals;
		boolean finished = true;
		int initRow = 0;
		//read record, cyclic file, the record is logical record, with rec No. 1 the newest
		for(i = 0; i < records; i++) {
			
			PbocReadRecord pbocReadRecord = new PbocReadRecord((byte)(sfi<<3), (byte)(i+1));
			rApdu = media.sendApdu(pbocReadRecord);									//Read Record
			
			if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
				if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A83) == true) {
					flag = false;
					break;
				} else {
					if(sw1sw2.equals(Sw1Sw2.SW1SW2_FF07) == true) {
						if(i > 0) {
							flag = false;
							finished = false;
							break;
						}
					}
					
					strErrResp[0][0] = sw1sw2;
					return strErrResp;
				}
			}
			
			bytResp = rApdu.getDataBytes();
			strResp = rApdu.getDataHexString();
			
			if(strResp == null) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			} else if(strResp.length() != dolInfo[2]+dolInfo[2]) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
			
			boolean flag1 = flag;
			if(flag == true) {
				flag1 = flagDol;
			}
			strDolVals = Dol.getDolValues(bytRespGetData, 0, bytRespGetData.length, dolInfo[1], flag1, bytResp);
			if(strDolVals == null) {
				strErrResp[0][0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
			
			if(deleteInit) {
				if(BasicUtil.isByteArraySameValue(bytResp, init) == true) {
					initRow ++;
				} else {
					initRow = 0;
				}
			}
			
			strDetails[2+i] = strDolVals;
		}
		
		int trimRow = initRow;
		if(flag == false) {
			if(i < records) {
				trimRow = records-i+initRow;
			}
		}
		
		if(trimRow > records) {
			strErrResp[0][0] = Sw1Sw2.SW1SW2_FC61;
			return strErrResp;
		}
		
		if(trimRow > 0) {
			String[][] strDetailsTrim = new String[2+records-trimRow][dolInfo[1]];
			for(j = 0; j < 2+records-trimRow; j++) {
				strDetailsTrim[j] = strDetails[j];
			}
			strDetails = strDetailsTrim;
		}
		
		if(finished == false) {
			strDetails[0][0] = Sw1Sw2.SW1SW2_OK1;
		} else {
			strDetails[0][0] = Sw1Sw2.SW1SW2_OK;
		}
		
		return strDetails;
	}
	
	//request credit for load
	public static String[] requestCreditForLoad(Media media, byte[] appName,
			byte mode, String strChallange, String strAmount, boolean nonZero, 
			String date, String time, String trader,
			String type, String currency, String country) {
		
		String[] strErrResp = new String[1];
		String sw1sw2;
		
		if((sw1sw2 = Media.checkMedia(media)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		if(appName == null) {
			appName = APP_NAME_PBOC20;
		}
		else {
			if(appName.length < 7) {
				appName = APP_NAME_PBOC20;
			}
		}
		
		if(strChallange == null) {
			strChallange = TlvTag.VAL_D_9F37;
		} else {
			sw1sw2 = TransUtil.checkInputChallenge(strChallange, 8);
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				strErrResp[0] = sw1sw2;
				return strErrResp;
			}
		}
		
		String checkResult = TransUtil.checkInputAmount(strAmount, 12, 2, nonZero);
		sw1sw2 = checkResult.substring(0, 4);
		if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		strAmount = checkResult.substring(4, checkResult.length());
		
		if(date == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FE21;
			return strErrResp;
		} else if(date.length() != 8) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FE21;
			return strErrResp;
		} else {
			date = date.substring(2, date.length());
		}
		
		if(time == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FE22;
			return strErrResp;
		} else if(time.length() != 6) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FE22;
			return strErrResp;
		}
		
		if(type == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
			return strErrResp;
		} else if(type.length() != 2) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
			return strErrResp;
		}
		
		if(currency != null) {
			if(currency.length() > 4) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
				return strErrResp;
			} else if(currency.length() < 4) {
				currency = "0000".substring(0, 4-currency.length()) + currency;
			}
		} else {
			currency = TlvTag.VAL_D_5F2A;
		}
		
		if(country != null) {
			if(country.length() > 4) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
				return strErrResp;
			} else if(country.length() < 4) {
				country = "0000".substring(0, 4-country.length()) + country;
			}
		} else {
			country = TlvTag.VAL_D_9F1A;
		}
		
		sessionMedia = media;
		sessionAmount = strAmount;
		sessionDate = date;
		sessionTime = time;
		sessionTrader = trader;
		sessionType = type;
		sessionCurrency = currency;
		sessionCountry = country;
		String strApduResp[] = null;
		
		media.connect();
		checkResult = checkCreditForLoadCondition(media, appName, mode, strChallange, strAmount);
		
		if(checkResult.equals(Sw1Sw2.SW1SW2_OK) == false) {
			media.close();
			clearCreditForLoadSession();
			strErrResp[0] = checkResult;
			return strErrResp;
		}
		
		strApduResp = calculateCreditForLoadRequest(media, appName, mode, strChallange, strAmount,
				date, time, trader,
				type, currency, country);
		if(strApduResp[0].equals(Sw1Sw2.SW1SW2_OK) == false) {
			clearCreditForLoadSession();
			media.close();
			return strApduResp;
		}
		
		sessionStartTime = System.nanoTime();
		sessionTimeout = 30000000000L;
		
		return strApduResp;
	}
	
	private static void clearCreditForLoadSession() {
		sessionMedia = null;
		sessionAmount = null;
		sessionDate = null;
		sessionTime = null;
		sessionTrader = null;
		sessionType = null;
		sessionCurrency = null;
		sessionCountry = null;
		sessionSum = -1;
		sessionRApduSelect = null;
		sessionCdol2 = null;
		
		sessionStartTime = 0;
		sessionTimeout = 0;
	}
	
	//check if the condition for credit for load met
	private static String checkCreditForLoadCondition(Media media, byte[] appName,
			byte mode, String strChallange, String strAmount) {
		
		int lenT;
		int lenL;
		int[] lenV = {0};
		byte[] bytResp = null;
		RApdu rApdu = null;
		String sw1sw2 = null;
		
		//select application
		rApdu = transSelect(media, appName);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		bytResp = rApdu.getDataBytes();
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		
		if(lenL < 0) {
			return Sw1Sw2.SW1SW2_F801;
		}
		if(bytResp.length < (1 + lenL + lenV[0])) {
			return Sw1Sw2.SW1SW2_F801;
		}
		sessionRApduSelect = rApdu;
		
		//Get Data 9F79
		PbocGetData pbocGetData9F79 = new PbocGetData(TlvTag.TAG_B_9F79);
		rApdu = media.sendApdu(pbocGetData9F79);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		bytResp = rApdu.getDataBytes();
		lenT = BerTlv.getTagLen(bytResp, 0, false);
		if(lenT != 2) {
			return Sw1Sw2.SW1SW2_F808;
		}
		
		if(BasicUtil.byteStringCompare(bytResp, 0, lenT, TlvTag.TAG_B_9F79, 0, lenT) != 0) {
			return Sw1Sw2.SW1SW2_F808;
		}
		
		lenV[0] = 0;
		lenL = BerTlv.getLenLen(bytResp, lenT, lenV);		
		if(lenL != 1) {
			return Sw1Sw2.SW1SW2_F808;
		}
		if(bytResp.length != (lenT + lenL + lenV[0])) {
			return Sw1Sw2.SW1SW2_F808;
		}
		if(bytResp.length != (2+1+6)) {
			return Sw1Sw2.SW1SW2_F808;
		}
		
		String val9F79 = rApdu.getDataHexString().substring(2+1 + 2+1, 2+1+6 + 2+1+6);
		if(BasicUtil.isDecimalString(val9F79) == false) {
			return Sw1Sw2.SW1SW2_F808;
		}
		
		//Get Data 9F77
		PbocGetData pbocGetData9F77 = new PbocGetData(TlvTag.TAG_B_9F77);
		rApdu = media.sendApdu(pbocGetData9F77);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		bytResp = rApdu.getDataBytes();
		lenT = BerTlv.getTagLen(bytResp, 0, false);
		if(lenT != 2) {
			return Sw1Sw2.SW1SW2_F809;
		}
		
		if(BasicUtil.byteStringCompare(bytResp, 0, lenT, TlvTag.TAG_B_9F77, 0, lenT) != 0) {
			return Sw1Sw2.SW1SW2_F809;
		}
		
		lenV[0] = 0;
		lenL = BerTlv.getLenLen(bytResp, lenT, lenV);		
		if(lenL != 1) {
			return Sw1Sw2.SW1SW2_F809;
		}
		if(bytResp.length != (lenT + lenL + lenV[0])) {
			return Sw1Sw2.SW1SW2_F809;
		}
		if(bytResp.length != (2+1+6)) {
			return Sw1Sw2.SW1SW2_F809;
		}
		
		String val9F77 = rApdu.getDataHexString().substring(2+1 + 2+1, 2+1+6 + 2+1+6);
		if(BasicUtil.isDecimalString(val9F77) == false) {
			return Sw1Sw2.SW1SW2_F809;
		}
		
		long long9F79 = Long.parseLong(val9F79);
		long long9F77 = Long.parseLong(val9F77);
		long longAmount = Long.parseLong(strAmount);
		long sum = long9F79 + longAmount;
		
		//amount overflow
		if(sum > 999999999999L) {
			return Sw1Sw2.SW1SW2_FA02;
		}
		
		//check if sum is greater than 9F77
		if(sum > long9F77) {
			return Sw1Sw2.SW1SW2_FA04;
		}
		
		sessionSum = sum;
		return Sw1Sw2.SW1SW2_OK;
	}
	
	//calculate credit for load
	private static String[] calculateCreditForLoadRequest(Media media, byte[] appName,
			byte mode, String strChallange, String strAmount,
			String date, String time, String trader,
			String type, String currency, String country) {
		
		String[] strErrResp = new String[1];
		
		int lenResp;
		byte[] bytResp = null;
		RApdu rApdu = null;
		String strResp = null;
		String sw1sw2 = null;
		
		//get challenge
		byte[] bytTransChallenge = null;
		if(strChallange != null) {
			bytTransChallenge = BasicUtil.byteAsciiToByteBcd(strChallange.getBytes(), false, false);
			if(bytTransChallenge == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		//get amount
		byte[] bytTransAmount = null;
		if(strAmount != null) {
			bytTransAmount = BasicUtil.byteAsciiToByteBcd(strAmount.getBytes(), false, false);
			if(bytTransAmount == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		byte[] bytTransDate = null;
		if(date != null) {
			bytTransDate = BasicUtil.byteAsciiToByteBcd(date.getBytes(), false, false);
			if(bytTransDate == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE21;
				return strErrResp;
			}
		}
		
		byte[] bytTransTime = null;
		if(time != null) {
			bytTransTime = BasicUtil.byteAsciiToByteBcd(time.getBytes(), false, false);
			if(bytTransTime == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE22;
				return strErrResp;
			}
		}
		
		byte[] bytTransTrader = null;
		if(trader != null) {
			try {
				bytTransTrader = trader.getBytes("GBK");
			} catch(Exception e) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
				return strErrResp;
			}
			
			if(bytTransTrader != null) {
				while(bytTransTrader.length > 20) {
					try {
						trader = trader.substring(0, trader.length()-1);
						bytTransTrader = trader.getBytes("GBK");
					} catch(Exception e) {
						strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
						return strErrResp;
					}
				}
			}
		}
		
		byte[] bytTransType = null;
		if(type != null) {
			bytTransType = BasicUtil.byteAsciiToByteBcd(type.getBytes(), false, false);
			if(bytTransType == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
				return strErrResp;
			}
		}
		
		byte[] bytTransCurrency = null;
		if(currency != null) {
			bytTransCurrency = BasicUtil.byteAsciiToByteBcd(currency.getBytes(), true, false);
			if(bytTransCurrency == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
				return strErrResp;
			}
		} else {
			bytTransCurrency = TlvTag.VAL_B_D_5F2A;
		}
		
		byte[] bytTransCountry = null;
		if(country != null) {
			bytTransCountry = BasicUtil.byteAsciiToByteBcd(country.getBytes(), true, false);
			if(bytTransCountry == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE01;
				return strErrResp;
			}
		} else {
			bytTransCountry = TlvTag.VAL_B_D_9F1A;
		}
		
		rApdu = sessionRApduSelect;
		rApdu = transGpo(media, rApdu.getBytes(), bytTransChallenge, bytTransAmount,
				bytTransDate, bytTransTime, bytTransTrader,
				bytTransType, bytTransCurrency, bytTransCountry);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		byte[] bytRespGpo = rApdu.getDataBytes();
		strResp = rApdu.getDataHexString();
		
		int lenL;
		int[] lenV = {0};
		lenL = BerTlv.getLenLen(bytRespGpo, 1, lenV);
		if(lenL < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		if(bytRespGpo.length < (1 + lenL + lenV[0])) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		int lenRespGpo = bytRespGpo.length;
		int lenAfl = bytRespGpo.length-1 -lenL-2;
		if((lenAfl < 4) || ((lenAfl%4) !=0 )) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		//sessionGpoResp = strResp;
		
		int sfi;
		int recEnd;
		int recStart;
		int offsetAfl = 1+lenL+2;
		boolean cdol1Found = false;
		boolean cdol2Found = false;
		boolean track2Found = false;
		boolean panSnFound = false;
		boolean startDateFound = false;
		boolean failDateFound = false;
		boolean errOccured = false;
		byte[] bytStartDate = null;
		byte[] bytFailDate = null;
		String tlv82 = TlvTag.TAG_LEN_AIP+strResp.substring(1+1+lenL+lenL, 1+1+lenL+lenL+4);
		String track2Data = null;
		String panSn = null;
		
		int j;
		int offset = 0;
		int lenCdol1 = -1;
		int lenCdol2 = -1;
		int lenTrack2 = -1;
		int lenPanSn = -1;
		int lenStartDate = -1;
		int lenFailDate = -1;
		int lenAllCdol1 = -1;
		int loc9A = -1;
		int loc9C = -1;
		int loc5F2A = -1;
		int loc9F1A = -1;
		int loc9F21 = -1;
		int loc9F4E = -1;
		int locAmount = -1;
		int locChallenge = -1;
		
		while(lenRespGpo > offsetAfl) {
			
			sfi = (bytRespGpo[offsetAfl]&0xF8);
			recStart = bytRespGpo[offsetAfl+1]&0xFF;
			recEnd = bytRespGpo[offsetAfl+2]&0xFF;
			
			while((recStart&0xFF) <= (recEnd&0xFF)) {
				
				PbocReadRecord pbocReadRecord = new PbocReadRecord((byte)sfi, (byte)recStart);
				sw1sw2 = Sw1Sw2.SW1SW2_OK;
				rApdu = media.sendApdu(pbocReadRecord);
				Log.i("shishi: read record", BasicUtil.bytesToHexString(pbocReadRecord.apdu, 0, pbocReadRecord.apdu.length));
				Log.i("shishi: read record", rApdu.getHexString());
				
				//check response
				if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
					if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A83) == true) {
						recStart++;
						continue;
					} else {
						errOccured = true;
						break;
					}
				}
				
				bytResp = rApdu.getDataBytes();
				strResp = rApdu.getDataHexString();
				
				if(cdol1Found == false) {
					
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_CDOL1);
					if(offset > 0) {
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenCdol1 = lenV[0];
						if(bytResp.length < (offset + lenL + lenCdol1)) {
							errOccured = true;
							break;
						}
						
						lenAllCdol1 = Dol.getDolDataLen(bytResp, offset+lenL, offset+lenL+lenCdol1, false);					
						if(lenAllCdol1 < 1) {
							errOccured = true;
							break;
						}
						
						loc9A = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9A);
						loc9C = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9C);
						loc5F2A = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_5F2A);
						loc9F1A = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9F1A);
						loc9F21 = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9F21);
						loc9F4E = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9F4E);
						locChallenge = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9F37);
						
						locAmount = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9F02);
						if(locAmount < 0) {
							errOccured = true;
							break;
						}
						
						cdol1Found = true;
						if((cdol2Found == true) 
								&& (track2Found == true) 
								&& (panSnFound == true)
								&& (startDateFound == true)
								&& (failDateFound == true)) {
							break;
						}
					}
				}
				
				if(cdol2Found == false) {
					
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_CDOL2);
					if(offset > 0) {
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenCdol2 = lenV[0];
						if(bytResp.length < (offset + lenL + lenCdol2)) {
							errOccured = true;
							break;
						}
						sessionCdol2 = BasicUtil.byteSubString(bytResp, offset+lenL, lenCdol2);					
						
						if(sessionCdol2 == null) {
							errOccured = true;
							break;
						}
						
						cdol2Found = true;
						if((cdol1Found == true) 
								&& (track2Found == true) 
								&& (panSnFound == true)
								&& (startDateFound == true)
								&& (failDateFound == true)) {
							break;
						}
					}
				}
				
				if(track2Found == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_TRACK2);
					if(offset > 0) {																		//track2 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenTrack2 = lenV[0];
						if(lenTrack2 < 12) {
							errOccured = true;
							break;
						}
						
						if(bytResp.length < (offset + lenL + lenTrack2)) {
							errOccured = true;
							break;
						}
						track2Data = strResp.substring(offset + offset, offset+lenL+lenTrack2 + offset+lenL+lenTrack2);
						
						track2Found = true;
						if((cdol1Found == true) 
								&& (cdol2Found == true) 
								&& (panSnFound == true)
								&& (startDateFound == true)
								&& (failDateFound == true)) {
							break;
						}
					}
				}
				
				if(panSnFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_5F34);
					if(offset > 0) {																		//
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenPanSn = lenV[0];
						if(bytResp.length < (offset + lenL + lenPanSn)) {
							errOccured = true;
							break;
						}
						panSn = strResp.substring(offset+lenL + offset+lenL, offset+lenL+lenPanSn + offset+lenL+lenPanSn);
						
						panSnFound = true;
						if((cdol1Found == true) 
								&& (cdol2Found == true) 
								&& (track2Found == true)
								&& (startDateFound == true)
								&& (failDateFound == true)) {
							break;
						}
					}
				}
				
				if(startDateFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_5F25);
					if(offset > 0) {																		//
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenStartDate = lenV[0];
						if(lenStartDate != 3) {
							errOccured = true;
							break;
						}
						if(bytResp.length < (offset + lenL + lenStartDate)) {
							errOccured = true;
							break;
						}
						
						int i;
						bytStartDate = new byte[lenStartDate];
						for(i = 0; i < lenStartDate; i++) {
							bytStartDate[i] = bytResp[offset+lenL+i];
						}
						
						startDateFound = true;
						if((cdol1Found == true) 
								&& (cdol2Found == true) 
								&& (track2Found == true)
								&& (panSnFound == true)
								&& (failDateFound == true)) {
							break;
						}
					}
				}
				
				if(failDateFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_5F24);
					if(offset > 0) {																		//
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenFailDate = lenV[0];
						if(lenFailDate != 3) {
							errOccured = true;
							break;
						}
						if(bytResp.length < (offset + lenL + lenFailDate)) {
							errOccured = true;
							break;
						}
						
						int i;
						bytFailDate = new byte[lenFailDate];
						for(i = 0; i < lenFailDate; i++) {
							bytFailDate[i] = bytResp[offset+lenL+i];
						}
						
						failDateFound = true;
						if((cdol1Found == true) 
								&& (cdol2Found == true) 
								&& (track2Found == true)
								&& (panSnFound == true)
								&& (startDateFound == true)) {
							break;
						}
					}
				}
				
				recStart++;
			}
			
			if(((cdol1Found == true) 
					&& (cdol2Found == true) 
					&& (track2Found == true) 
					&& (panSnFound == true)
					&& (startDateFound == true)
					&& (failDateFound == true)) 
					|| (errOccured == true)) {
				break;
			}
			
			offsetAfl += 4;
		}
		
		if(errOccured == true) {
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				strErrResp[0] = sw1sw2;
				return strErrResp;
			} else {
				strErrResp[0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
		}
		
		if(cdol1Found == false) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		if(cdol2Found == false) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		if(track2Found == false) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		String pan = calculateCardNumber(track2Data,
				true, true, 0, 0, (byte)0,
				false, true, 0, (byte)0);
		
		if((sw1sw2 = pan.substring(0, 4)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		pan = pan.substring(4, pan.length());
		
		if(startDateFound == true) {
			if(BasicUtil.byteStringCompare(bytTransDate, 0, 3, bytStartDate, 0, 3) < 0) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE23;
				return strErrResp;
			}
		}
		
		if(failDateFound == true) {
			if(BasicUtil.byteStringCompare(bytFailDate, 0, 3, bytTransDate, 0, 3) < 0) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FE24;
				return strErrResp;
			}
		}
		
		byte cdol1[] = new byte[lenAllCdol1];
		BasicUtil.initByteArray(cdol1, (byte)0x00);	
		
		for(j = 0; j < 2; j++) {
			
			if(loc5F2A > -1) {
				cdol1[loc5F2A+j] = bytTransCurrency[j];
			}
			
			if(loc9F1A > -1) {
				cdol1[loc9F1A+j] = bytTransCountry[j];
			}
		}
		
		if(locChallenge > -1) {
			if(bytTransChallenge != null) {			
				for(j = 0; j < bytTransChallenge.length; j++) {
					cdol1[locChallenge+j] = bytTransChallenge[j];
				}
			}
		}
		
		if(locAmount > -1) {
			if(bytTransAmount != null) {				
				for(j = 0; j < bytTransAmount.length; j++) {
					cdol1[locAmount+j] = bytTransAmount[j];
				}
			}
		}
		
		if(loc9C > -1) {
			if(bytTransType != null) {
				for(j = 0; j < bytTransType.length; j++) {
					cdol1[loc9C+j] = bytTransType[j];
				}
			}
		}
		
		if(loc9A > -1) {
			if(bytTransDate != null) {
				for(j = 0; j < bytTransDate.length; j++) {
					cdol1[loc9A+j] = bytTransDate[j];
				}
			}
		}
		
		if(loc9F21 > -1) {
			if(bytTransTime != null) {
				for(j = 0; j < bytTransTime.length; j++) {
					cdol1[loc9F21+j] = bytTransTime[j];
				}
			}
		}
		
		if(loc9F4E > -1) {
			if(bytTransTrader != null) {
				for(j = 0; j < bytTransTrader.length; j++) {
					cdol1[loc9F4E+j] = bytTransTrader[j];
				}
			}
		}
		
		PbocGac pbocGac = new PbocGac((byte)0x80, cdol1);
		rApdu = media.sendApdu(pbocGac);
		Log.i("shishi: gac-1", BasicUtil.bytesToHexString(pbocGac.apdu, 0, pbocGac.apdu.length));
		Log.i("shishi: gac-1", rApdu.getHexString());
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		strResp = rApdu.getDataHexString();
		if(strResp == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		lenResp = strResp.length();
		if(lenResp < (21)*2) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		bytResp = rApdu.getDataBytes();
		String val9F10 = null;
		String val9F26 = null;
		String val9F27 = null;
		String val9F36 = null;
		
		lenV[0] = 0;
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		if(lenL < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		if(bytResp[0] == (byte)0x80) {
			val9F10 = strResp.substring(4+4+lenL+lenL+8+8, strResp.length());
			val9F26 = strResp.substring(4+4+lenL+lenL, 4+4+lenL+lenL+8+8);
			val9F27 = strResp.substring(1+1+lenL+lenL, 1+1+lenL+lenL+1+1);
			val9F36 = strResp.substring(2+2+lenL+lenL, 2+2+lenL+lenL+2+2);
		} else if(bytResp[0] == (byte)0x77) {		
			byte byt9F10[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F10);
			byte byt9F26[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F26);
			byte byt9F27[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F27);
			byte byt9F36[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F36);
			
			if((byt9F10 == null)
					||(byt9F26 == null)
					||(byt9F27 == null)
					||(byt9F36 == null)) {
				strErrResp[0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
			
			val9F10 = BasicUtil.bytesToHexString(byt9F10, 0, byt9F10.length);
			val9F26 = BasicUtil.bytesToHexString(byt9F26, 0, byt9F26.length);
			val9F27 = BasicUtil.bytesToHexString(byt9F27, 0, byt9F27.length);
			val9F36 = BasicUtil.bytesToHexString(byt9F36, 0, byt9F36.length);
		} else {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		byte[] bytVal9F10 = BasicUtil.hexStringToBytes(val9F10);
		if(bytVal9F10 == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		} else {
			if((bytVal9F10[4]&0x30) != 0x20) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FA05;
				return strErrResp;
			}
		}
		
		String tlv9F26 = TlvTag.TAG_LEN_9F26+val9F26;
		String tlv9F27 = TlvTag.TAG_LEN_9F27+val9F27;
		
		String len9F10 = Integer.toHexString(val9F10.length()/2);
		if(len9F10.length() < 2) {
			len9F10 = "00".substring(0, 2-len9F10.length()) + len9F10;
		}
		String tlv9F10 = TlvTag.TAG_9F10+len9F10 + val9F10;
		
		String tlv9F37 = TlvTag.TAG_LEN_9F37+strChallange;
		String tlv9F36 = TlvTag.TAG_LEN_9F36+val9F36;
		String tlv95 = TlvTag.TAG_LEN_95+TlvTag.VAL_D_95;
		String tlv9A = TlvTag.TAG_LEN_9A+date;
		String tlv9C = TlvTag.TAG_LEN_9C+type;
		String tlv9F02 = TlvTag.TAG_LEN_9F02+strAmount;
		String tlv5F2A = TlvTag.TAG_LEN_5F2A+currency;
		String tlv9F1A = TlvTag.TAG_LEN_9F1A+country;
		String tlv9F03 = TlvTag.TAG_LEN_9F03+TlvTag.VAL_D_9F03;
		
		String val9F33 = TlvTag.VAL_D_9F33;
		String tlv9F33 = TlvTag.TAG_LEN_9F33+val9F33;
		
		String valField55 = tlv9F26 + tlv9F27 + tlv9F10 + tlv9F37 + tlv9F36 + tlv95 + tlv9A
				+ tlv9C + tlv9F02 + tlv5F2A + tlv82 + tlv9F1A + tlv9F03 + tlv9F33;
		String lenField55 = Integer.toString(valField55.length()/2);
		if(lenField55.length() < 3) {
			lenField55 = "000".substring(0, 3-lenField55.length()) + lenField55;
		}
		
		String field55 = lenField55 + valField55;
		String[] strOkResp = new String[4];
		strOkResp[0] = Sw1Sw2.SW1SW2_OK;
		strOkResp[1] = field55;
		strOkResp[2] = pan;
		if(panSn != null) {
			strOkResp[3] = panSn;
		} else {
			strOkResp[3] = TlvTag.VAL_D_5F34;
		}
		
		return strOkResp;
	}
	
	public static String performCreditForLoad(byte[] appName, byte mode, String strChallange,
			String strArpc, String strAuthCode, String strPutData,
			int balanceFlag) {
		
		String sw1sw2;
		
		if(sessionMedia == null) {
			clearCreditForLoadSession();
			return Sw1Sw2.SW1SW2_FC63;
		} else if(sessionMedia.isConnected() == false) {
			clearCreditForLoadSession();
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		long curTime = System.nanoTime();
		if((sessionStartTime+sessionTimeout) < curTime) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC64;
		}
		
		if(sessionCdol2 == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		if(sessionAmount == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		if(sessionSum < 0) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		if(sessionDate == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		if(sessionTime == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		if(sessionType == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		if(sessionCurrency == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		if(sessionCountry == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FC63;
		}
		
		if(appName == null) {
			appName = APP_NAME_PBOC20;
		}
		else {
			if(appName.length < 7) {
				appName = APP_NAME_PBOC20;
			}
		}
		
		if(strChallange == null) {
			strChallange = TlvTag.VAL_D_9F37;
		} else {
			sw1sw2 = TransUtil.checkInputChallenge(strChallange, 8);
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				clearCreditForLoadSession();
				Media.closeSessionMedia(sessionMedia);
				return sw1sw2;
			}
		}
		
		if(strArpc == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FE01;
		}
		
		if(strAuthCode == null) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FE01;
		}
		
		if(strArpc.length() != 16) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FE01;
		}
		
		if(strAuthCode.length() != 4) {
			clearCreditForLoadSession();
			Media.closeSessionMedia(sessionMedia);
			return Sw1Sw2.SW1SW2_FE01;
		}
		
		if(strPutData != null) {
			if(strPutData.length() < 18) {
				clearCreditForLoadSession();
				Media.closeSessionMedia(sessionMedia);
				return Sw1Sw2.SW1SW2_FE01;
			}
			
			if(strPutData.length()%2 != 0) {
				clearCreditForLoadSession();
				Media.closeSessionMedia(sessionMedia);
				return Sw1Sw2.SW1SW2_FE01;
			}
		}
		
		String loadResult = null;
		loadResult = executeCreditForLoad(appName, mode, strChallange, sessionAmount,
				strArpc, strAuthCode, strPutData, balanceFlag,
				sessionDate, sessionTime, sessionTrader,
				sessionType, sessionCurrency, sessionCountry);
		Media.closeSessionMedia(sessionMedia);
		clearCreditForLoadSession();
		
		return loadResult;
	}
	
	private static String executeCreditForLoad(byte[] appName, byte mode, String strChallange, String strAmount,
			String strArpc, String strAuthCode, String strPutData, 
			int balanceFlag,
			String date, String time, String trader,
			String type, String currency, String country) {
		
		Media media = sessionMedia;
		
		byte[] bytArpc;
		byte[] bytAuthCode;
		byte[] bytPutData = null;
		byte[] bytTransChallenge = null;
		byte[] bytTransAmount = null;
		RApdu rApdu = null;
		
		bytArpc = BasicUtil.byteAsciiToByteBcd(strArpc.getBytes(), false, false);
		if(bytArpc == null) {
			return Sw1Sw2.SW1SW2_FE01;
		}
		
		bytAuthCode = BasicUtil.byteAsciiToByteBcd(strAuthCode.getBytes(), false, false);
		if(bytAuthCode == null) {
			return Sw1Sw2.SW1SW2_FE01;
		}
		
		if(strPutData != null) {
			bytPutData = BasicUtil.byteAsciiToByteBcd(strPutData.getBytes(), false, false);
			if(bytPutData == null) {
				return Sw1Sw2.SW1SW2_FE01;
			}
		}
		
		bytTransChallenge = BasicUtil.byteAsciiToByteBcd(strChallange.getBytes(), false, false);
		if(bytTransChallenge == null) {
			return Sw1Sw2.SW1SW2_FC60;
		}
		
		bytTransAmount = BasicUtil.byteAsciiToByteBcd(strAmount.getBytes(), false, false);
		if(bytTransAmount == null) {
			return Sw1Sw2.SW1SW2_FC60;
		}
		
		byte[] bytTransDate = BasicUtil.byteAsciiToByteBcd(date.getBytes(), false, false);
		if(bytTransDate == null) {
			return Sw1Sw2.SW1SW2_FC60;
		}
		
		byte[] bytTransTime = BasicUtil.byteAsciiToByteBcd(time.getBytes(), false, false);
		if(bytTransTime == null) {
			return Sw1Sw2.SW1SW2_FC60;
		}
		
		byte[] bytTransTrader = null;
		if(trader != null) {
			try {
				bytTransTrader = trader.getBytes("GBK");
			} catch(Exception e) {
				return Sw1Sw2.SW1SW2_FC60;
			}
		}
		
		byte[] bytTransType = BasicUtil.byteAsciiToByteBcd(type.getBytes(), false, false);
		if(bytTransType == null) {
			return Sw1Sw2.SW1SW2_FC60;
		}
		
		byte[] bytTransCurrency = BasicUtil.byteAsciiToByteBcd(currency.getBytes(), true, false);
		if(bytTransCurrency == null) {
			return Sw1Sw2.SW1SW2_FC60;
		}
		
		byte[] bytTransCountry = BasicUtil.byteAsciiToByteBcd(country.getBytes(), true, false);
		if(bytTransCountry == null) {
			return Sw1Sw2.SW1SW2_FC60;
		}
		
		int lenL;
		int[] lenV = {0};
		
		byte[] bytResp = null;
		String strResp = null;
		String sw1sw2 = null;
		
		PbocExternalAuth pbocExternalAuth = new PbocExternalAuth(bytArpc, bytAuthCode);
		rApdu = media.sendApdu(pbocExternalAuth);
		Log.i("shishi: external auth", BasicUtil.bytesToHexString(pbocExternalAuth.apdu, 0, pbocExternalAuth.apdu.length));
		Log.i("shishi: external auth", rApdu.getHexString());
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		int j;
		int lenCdol2 = -1;
		int lenAllCdol2 = -1;
		int loc8A = -1;
		int loc91 = -1;
		int loc9A = -1;
		int loc9C = -1;
		int loc5F2A = -1;
		int loc9F1A = -1;
		int loc9F21 = -1;
		int loc9F4E = -1;
		int locAmount = -1;
		int locChallenge = -1;
		
		bytResp = sessionCdol2;
		lenCdol2 = bytResp.length;
		lenAllCdol2 = Dol.getDolDataLen(bytResp, 0, lenCdol2, false);
		
		if(lenAllCdol2 < 1) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		loc9F1A = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_9F1A);
		loc5F2A = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_5F2A);
		locChallenge = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_9F37);
		
		locAmount = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_9F02);
		if(locAmount < 0) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		loc8A = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_8A);
		loc91 = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_91);
		loc9A = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_9A);
		loc9C = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_9C);
		loc9F21 = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_9F21);
		loc9F4E = Dol.getValLocInDol(bytResp, 0, lenCdol2, TlvTag.TAG_B_9F4E);
		
		byte cdol2[] = new byte[lenAllCdol2];
		BasicUtil.initByteArray(cdol2, (byte)0x00);	
		
		for(j = 0; j < 2; j++) {
			
			if(loc9F1A > -1) {
				cdol2[loc9F1A+j] = bytTransCountry[j];
			}
			
			if(loc5F2A > -1) {						
				cdol2[loc5F2A+j] = bytTransCurrency[j];
			}
		}
		
		if(locChallenge > -1) {
			if(bytTransChallenge != null) {			
				for(j = 0; j < bytTransChallenge.length; j++) {
					cdol2[locChallenge+j] = bytTransChallenge[j];
				}
			}
		}
		
		if(locAmount > -1) {
			if(bytTransAmount != null) {				
				for(j = 0; j < bytTransAmount.length; j++) {
					cdol2[locAmount+j] = bytTransAmount[j];
				}
			}
		}
		
		if(loc8A > -1) {
			for(j = 0; j < bytAuthCode.length; j++) {
				cdol2[loc8A+j] = bytAuthCode[j];
			}
		}
		
		if(loc9C > -1) {
			for(j = 0; j < 1; j++) {
				cdol2[loc9C+j] = bytTransType[j];
			}
		}
		
		if(loc91 > -1) {
			for(j = 0; j < 8; j++) {
				cdol2[loc91+j] = bytArpc[j];
			}
			for(j = 0; j < 2; j++) {
				cdol2[loc91+8+j] = bytAuthCode[j];
			}
		}
		
		if(loc9F21 > -1) {		
			for(j = 0; j < 3; j++) {
				cdol2[loc9F21+j] = bytTransTime[j];
			}
		}
		
		if(loc9A > -1) {
			for(j = 0; j < 3; j++) {
				cdol2[loc9A+j] = bytTransDate[j];
			}
		}
		
		if(loc9F4E > -1) {
			if(bytTransTrader != null) {
				int lenTrader = bytTransTrader.length;
				if(lenTrader > 20) {
					lenTrader = 20;
				}
				for(j = 0; j < lenTrader; j++) {
					cdol2[loc9F4E+j] = bytTransTrader[j];
				}
			}
		}
		
		int loc = -1;
		byte p1 = (byte)0x00;
		boolean isAccept = false;
		
		loc = BasicUtil.findStringLocInArray(strAuthCode, AUTH_CODE_ACCEPT);
		if(loc > -1) {
			p1 = (byte)0x40;
			isAccept = true;
		}
		
		PbocGac pbocGac = new PbocGac(p1, cdol2);
		rApdu = media.sendApdu(pbocGac);
		Log.i("shishi: gac-2", BasicUtil.bytesToHexString(pbocGac.apdu, 0, pbocGac.apdu.length));
		Log.i("shishi: gac-2", rApdu.getHexString());
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		bytResp = rApdu.getDataBytes();
		lenV[0] = 0;
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		
		if(lenL < 0) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		if(bytResp.length < (1 + lenL + lenV[0])) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		if(lenV[0] < 19) {
			return Sw1Sw2.SW1SW2_F801;
		}
		
		if(isAccept == true) {
			byte[] bytVal9F10 = null;
			
			if(bytResp[0] != (byte)0x80) {
				if(bytResp[0] == (byte)0x77) {		
					bytVal9F10 = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F10);
					if(bytVal9F10 == null) {
						return Sw1Sw2.SW1SW2_F801;
					} else if(bytVal9F10.length < 8) {
						return Sw1Sw2.SW1SW2_F801;
					}
				} else {
					return Sw1Sw2.SW1SW2_F801;
				}
			} else {
				int len = bytResp.length-(1+lenL+1+2+8);
				if(len < 8) {
					return Sw1Sw2.SW1SW2_F801;
				}
				bytVal9F10 = BasicUtil.byteSubString(bytResp, 1+lenL+1+2+8, len);
				if(bytVal9F10 == null) {
					return Sw1Sw2.SW1SW2_F801;
				}
			}
			
			if((bytVal9F10[4]&0xC0) != 0x40) {
				isAccept = false;
			}
		}
		
		if(isAccept == false) {
			return Sw1Sw2.SW1SW2_FA05;
		}
		
		PbocPutData pbocPutData = new PbocPutData(bytPutData);
		if(pbocPutData.isCApduOk() == false) {
			return Sw1Sw2.SW1SW2_FE01;
		}
		
		if((pbocPutData.apdu[0] == (byte)0x04)
				&&(pbocPutData.apdu[1] == (byte)0xDA)
				&&(pbocPutData.apdu[2] == (byte)0x9F)
				&&(pbocPutData.apdu[3] == (byte)0x79)) {
			if((pbocPutData.apdu.length == (5+6+4))
					||(pbocPutData.apdu.length == (5+6+8))){
				long sum1 = sessionSum;
				String downAmount = strPutData.substring(5 + 5, 5+6 + 5+6);
				if(BasicUtil.isDecimalString(downAmount) == false) {
					return Sw1Sw2.SW1SW2_FE01;
				}
				long sum2 = Long.parseLong(downAmount);
				if(sum1 != sum2) {
					return Sw1Sw2.SW1SW2_FC62;
				}
			} else {
				return Sw1Sw2.SW1SW2_FE01;
			}
		}
		
		rApdu = media.sendApdu(pbocPutData);
		Log.i("shishi: putdata", BasicUtil.bytesToHexString(pbocPutData.apdu, 0, pbocPutData.apdu.length));
		Log.i("shishi: putdata", rApdu.getHexString());
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			return sw1sw2;
		}
		
		if(balanceFlag == 0) {
			return Sw1Sw2.SW1SW2_OK;
		} else if((balanceFlag > 0) && (balanceFlag < 3)){
			
			//Get Data 9F79
			PbocGetData pbocGetData = new PbocGetData(TlvTag.TAG_B_9F79);
			rApdu = media.sendApdu(pbocGetData);
			Log.i("shishi: getdata", BasicUtil.bytesToHexString(pbocGetData.apdu, 0, pbocGetData.apdu.length));
			Log.i("shishi: getdata", rApdu.getHexString());
			if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
				return sw1sw2;
			}
			
			strResp = rApdu.getDataHexString();
			if(strResp == null) {
				return Sw1Sw2.SW1SW2_F801;
			} else if(strResp.length() < (2+1+6 + 2+1+6)) {
				return Sw1Sw2.SW1SW2_F801;
			}
			
			String balance = strResp.substring(2+1+2+1, 2+1+6 + 2+1+6);
			
			if(balanceFlag == 2) {
				balance = TransUtil.toReadableAmount(balance);
				return Sw1Sw2.SW1SW2_OK + balance;
			} else {
				return Sw1Sw2.SW1SW2_OK + balance;
			}
			
		} else {
			return Sw1Sw2.SW1SW2_OK;
		}
	}
	
	//get the Digital Stamp
	public static String[] getDigitalStamp(Media media, byte[] appName,
			byte mode, byte digitalStampLen, 
			String strChallange, String strAmount, boolean nonZero, String strAccount) {
		
		String[] strErrResp = new String[1];
		String sw1sw2;
		
		if((sw1sw2 = Media.checkMedia(media)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		if((mode < 0) || (mode > 2)){
			strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
			return strErrResp;
		}
		
		if((digitalStampLen < 0) || (digitalStampLen > 1)){
			strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
			return strErrResp;
		}
		
		if(appName == null) {
			appName = APP_NAME_PBOC20;
		} else {
			if(appName.length < 7) {
				appName = APP_NAME_PBOC20;
			}
		}
		
		if((mode > 0) && (mode < 3)) {
			
			String checkResult = TransUtil.checkInputAmount(strAmount, 12, 2, nonZero);
			sw1sw2 = checkResult.substring(0, 4);
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				strErrResp[0] = sw1sw2;
				return strErrResp;
			}
			strAmount = checkResult.substring(4, checkResult.length());
			
			checkResult = TransUtil.checkInputAccount(strAccount, 8, 8, true);
			sw1sw2 = checkResult.substring(0, 4);
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				strErrResp[0] = sw1sw2;
				return strErrResp;
			}
			
			if(mode == 2) {
				sw1sw2 = TransUtil.checkInputChallenge(strChallange, 8);
				if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
					strErrResp[0] = sw1sw2;
					return strErrResp;
				}
			} else {
				strChallange = "00000000";
			}
		} else if(mode == 0) {
			strAmount = TlvTag.VAL_D_9F02;
			strChallange = "00000000";
			strAccount = "00000000";
		}
		
		String[] strResp = null;
			
		media.connect();		
		if(mode == 2) {
			strResp = calculateEnhancedDigitalStamp(media, appName, mode, digitalStampLen, strChallange, strAmount, strAccount);
		} else {
			strResp = calculateDigitalStamp(media, appName, mode, digitalStampLen, strChallange, strAmount, strAccount);
		}
		media.close();
		
		return strResp;
	}
	
	//calculate Digital Stamp number
	private static String[] calculateDigitalStamp(Media media, byte[] appName,
			byte mode, byte digitalStampLen,
			String strChallange, String strAmount, String strAccount) {
		
		int lenL;
		int[] lenV = {0};
		byte[] bytResp = null;
		String strResp = null;
		String sw1sw2 = null;
		String[] strErrResp = new String[1];
		RApdu rApdu = null;
		
		//get challenge
		byte[] bytTransChallenge = null;
		if(strChallange != null) {
			bytTransChallenge = BasicUtil.byteAsciiToByteBcd(strChallange.getBytes(), false, false);
			if(bytTransChallenge == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		//get amount
		byte[] bytTransAmount = null;
		if(strAmount != null) {
			bytTransAmount = BasicUtil.byteAsciiToByteBcd(strAmount.getBytes(), false, false);
			if(bytTransAmount == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		//get amount
		byte[] bytTransAccount = null;
		if(strAccount != null) {
			bytTransAccount = BasicUtil.byteAsciiToByteBcd(strAccount.getBytes(), false, false);
			if(bytTransAccount == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		//select application
		rApdu = transSelect(media, appName);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		bytResp = rApdu.getDataBytes();
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		
		if(lenL < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		if(bytResp.length < (1 + lenL + lenV[0])) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		/*String curTime = null;
		byte[] bytCurTime = null;
		curTime = AndroidUtil.getCurTime();
		bytCurTime = BasicUtil.byteAsciiToByteBcd(curTime.getBytes(), false, false);
		
		if(bytCurTime == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD81;
			return strErrResp;
		} else if(bytCurTime.length != 3) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD81;
			return strErrResp;
		}
		
		String curDate = null;
		byte[] bytCurDate = null;
		curDate = AndroidUtil.getCurDate();
		bytCurDate = BasicUtil.byteAsciiToByteBcd(curDate.getBytes(), false, false);
		
		if(bytCurDate == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD81;
			return strErrResp;
		} else if(bytCurDate.length != 3) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD81;
			return strErrResp;
		}*/
		
		rApdu = transGpo(media, rApdu.getDataBytes(), bytTransChallenge, bytTransAmount,
				null, null, null,
				null, null, null);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		byte[] bytRespGpo = rApdu.getDataBytes();
		strResp = rApdu.getDataHexString();
		
		lenL = BerTlv.getLenLen(bytRespGpo, 1, lenV);
		if(lenL < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		if(bytRespGpo.length < (1 + lenL + lenV[0])) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		//check GPO response, AFL must exist
		int lenRespGpo = bytRespGpo.length;
		int lenAfl = bytRespGpo.length-1 -lenL-2;
		if((lenAfl < 4) || ((lenAfl%4) !=0 )) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		String aip = strResp.substring(1+lenL + 1+lenL, 1+lenL + 1+lenL + 4);
		
		int sfi;
		int recEnd;
		int recStart;
		int offsetAfl = 1+lenL+2;
		boolean cdol1Found = false;
		boolean track2Found = false;
		boolean panSnFound = false;
		boolean errOccured = false;
		
		int j;
		int offset = 0;
		int loc5F2A = -1;
		int loc9F1A = -1;
		int locAmount = -1;
		int locChallenge = -1;
		int lenCdol1 = -1;
		int lenTrack2 = -1;
		int lenPanSn = -1;
		int lenAllCdol1 = -1;
		String track2Data = null;
		String panSn = null;
		
		while(lenRespGpo > offsetAfl) {
			
			sfi = (bytRespGpo[offsetAfl]&0xF8);
			recStart = bytRespGpo[offsetAfl+1]&0xFF;
			recEnd = bytRespGpo[offsetAfl+2]&0xFF;
			
			while((recStart&0xFF) <= (recEnd&0xFF)) {
				
				PbocReadRecord pbocReadRecord = new PbocReadRecord((byte)sfi, (byte)recStart);
				sw1sw2 = Sw1Sw2.SW1SW2_OK;
				rApdu = media.sendApdu(pbocReadRecord);								//Read Record
				
				//check response
				if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
					if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A83) == true) {
						recStart++;
						continue;
					} else {
						errOccured = true;
						break;
					}
				}
				
				bytResp = rApdu.getDataBytes();
				strResp = rApdu.getDataHexString();
				
				if(cdol1Found == false) {				
					
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_CDOL1);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenCdol1 = lenV[0];
						if(bytResp.length < (offset + lenL + lenCdol1)) {
							errOccured = true;
							break;
						}
						lenAllCdol1 = Dol.getDolDataLen(bytResp, offset+lenL, offset+lenL+lenCdol1, false);
						
						if(lenAllCdol1 < 1) {
							errOccured = true;
							break;
						}
						
						//check if these tags exist in CDOL1	
						loc9F1A = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9F1A);
						loc5F2A = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_5F2A);
						locChallenge = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9F37);
						locAmount = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenCdol1, TlvTag.TAG_B_9F02);
						
						cdol1Found = true;
						if((track2Found == true) && (panSnFound == true)) {
							break;
						}
					}
				}
				
				if(track2Found == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_TRACK2);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenTrack2 = lenV[0];
						if(lenTrack2 < 12) {
							errOccured = true;
							break;
						}
						
						if(bytResp.length < (offset + lenL + lenTrack2)) {
							errOccured = true;
							break;
						}
						track2Data = strResp.substring(offset + offset, offset+lenL+lenTrack2 + offset+lenL+lenTrack2);
						
						track2Found = true;
						if((cdol1Found == true) && (panSnFound == true)) {
							break;
						}
					}
				}
				
				if(panSnFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_5F34);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenPanSn = lenV[0];
						if(bytResp.length < (offset + lenL + lenPanSn)) {
							errOccured = true;
							break;
						}
						panSn = strResp.substring(offset+lenL + offset+lenL, offset+lenL+lenPanSn + offset+lenL+lenPanSn);
						
						panSnFound = true;
						if((cdol1Found == true) && (track2Found == true)) {
							break;
						}
					}
				}
				
				recStart++;
			}
			
			if(((cdol1Found == true) && (track2Found == true) && (panSnFound == true)) || (errOccured == true)) {
				break;
			}
			
			offsetAfl += 4;
		}
		
		if(errOccured == true) {
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				strErrResp[0] = sw1sw2;
				return strErrResp;
			} else {
				strErrResp[0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
		}
		
		//track2 must exist
		if(track2Found == false) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		String pan = calculateCardNumber(track2Data,
				true, true, 0, 0, (byte)0,
				false, true, 0, (byte)0);
		
		if((sw1sw2 = pan.substring(0, 4)).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		pan = pan.substring(4, pan.length());
		
		//CDOL1 must exist
		if(cdol1Found == false) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		//initialize APDU GAC
		byte cdol1[] = new byte[lenAllCdol1];
		BasicUtil.initByteArray(cdol1, (byte)0x00);	
		
		//add tag values for GAC
		for(j = 0; j < 2; j++) {
			
			if(loc9F1A > -1) {
				cdol1[loc9F1A+j] = TlvTag.VAL_B_D_9F1A[j];
			}
			
			if(loc5F2A > -1) {						
				cdol1[loc5F2A+j] = TlvTag.VAL_B_D_5F2A[j];
			}
		}
		
		/*for(j = 0; j < 3; j++) {
			
			if(loc9A > -1) {
				cdol1[loc9A+j] = bytCurDate[j];
			}
			
			if(loc9F21 > -1) {						
				cdol1[loc9F21+j] = bytCurTime[j];
			}
		}*/
		
		if(mode == 1) {
			if(locChallenge > -1) {
				if(bytTransAccount != null) {			
					for(j = 0; j < bytTransAccount.length; j++) {
						cdol1[locChallenge+j] = bytTransAccount[j];
					}
				}
			}
			
			if(locAmount > -1) {
				if(bytTransAmount != null) {				
					for(j = 0; j < bytTransAmount.length; j++) {
						cdol1[locAmount+j] = bytTransAmount[j];
					}
				}
			}
		}
		
		PbocGac pbocGac = new PbocGac((byte)0x00, cdol1);
		rApdu = media.sendApdu(pbocGac);											//GAC
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		bytResp = rApdu.getDataBytes();
		if(bytResp == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		int len = bytResp.length;
		if(len < 21) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		lenV[0] = 0;
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		if(lenL < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		if(bytResp[0] != (byte)0x80) {
			if(bytResp[0] == (byte)0x77) {		
				byte byt9F10[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F10);
				byte byt9F26[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F26);
				byte byt9F27[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F27);
				byte byt9F36[] = BerTlv.getTlvValue(bytResp, TlvTag.TAG_B_9F36);							
				
				if((byt9F10 == null)
						||(byt9F26 == null)
						||(byt9F27 == null)
						||(byt9F36 == null)) {
					strErrResp[0] = Sw1Sw2.SW1SW2_F801;
					return strErrResp;
				}
				
				len = byt9F10.length+byt9F26.length+byt9F27.length+byt9F36.length;
				if((len < 19) || (len > (255-3))) {
					strErrResp[0] = Sw1Sw2.SW1SW2_F801;
					return strErrResp;
				}
				
				if(len < 0x80) {
					lenL = 1;
				} else {
					lenL = 2;
				}
				
				int i;
				byte[] tempBuff = new byte[1+lenL+len];
				for(i = 0; i < byt9F27.length; i++) {
					tempBuff[1+lenL+i] = byt9F27[i];
				}
				for(i = 0; i < byt9F36.length; i++) {
					tempBuff[1+lenL+byt9F27.length+i] = byt9F36[i];
				}
				for(i = 0; i < byt9F26.length; i++) {
					tempBuff[1+lenL+byt9F27.length+byt9F36.length+i] = byt9F26[i];
				}
				for(i = 0; i < byt9F10.length; i++) {
					tempBuff[1+lenL+byt9F27.length+byt9F36.length+byt9F26.length+i] = byt9F10[i];
				}
				bytResp = tempBuff;
			} else {
				strErrResp[0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
		}
		
		int atc;
		int ac;
		
		//get ATC and AC
		atc = (bytResp[1+lenL+1]&0xFF)*0x100+(bytResp[1+lenL+1+1]&0xFF);
		atc &= 0x7FFF;
		ac = (bytResp[1+lenL+1+2]&0xFF)*0x100+(bytResp[1+lenL+1+3]&0xFF);
		ac &= 0xFFFF;
		
		//AC XOR ATC
		ac = ac>>1;
		ac &= 0x7FFF;
		atc ^= ac;
		atc &= 0x7FFF;
		
		bytResp[1+lenL+1] = (byte)((atc>>8)&0xFF);
		bytResp[1+lenL+1+1] = (byte)(atc&0xFF);
		
		//get the bits which digital stamp needs
		short[] bitLen = {(short)0x00};
		byte[] bitData = TransUtil.getBitsByTemplate(bytResp, BIT_TEMPLATE_DIGITAL_STAMP_IN_GAC1, bitLen);
		if(bitData == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F802;
			return strErrResp;
		}
		
		short lenBits;
		if(bitLen[0] == 0) {
			lenBits = (short)((bitData.length)*8);
		} else {
			lenBits = (short)((bitData.length-1)*8+bitLen[0]);
		}
		
		if(lenBits != 0x27) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F802;
			return strErrResp;
		}
		
		byte[] bitData1 = TransUtil.bitOddEvenExchange(bitData, (short)(bitLen[0]-1));
		if(bitData1 == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F802;
			return strErrResp;
		}
		
		//restore the last bit after exchanging odd and even
		short shrTemp = (short)((bitData[bitData.length-1])&0xFF);
		shrTemp = (short)(shrTemp&(TransUtil.BIT_SHIFT_MASK_IN_BYTE[bitLen[0]-1]&0xFF));
		bitData1[bitData1.length-1] = (byte)((bitData1[bitData1.length-1]&0xFF)|shrTemp);
		
		byte[] digitalStampResult = null;
		if(digitalStampLen == 0) {																	//digital stamp length: 12 decimal digits
			
			short[] shrResult1 = null;
			shrResult1 = TransUtil.arrayBinaryToDecimal(bitData1, lenBits);
			if(shrResult1 == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_F802;
				return strErrResp;
			}
			
			if(shrResult1.length > 12) {
				strErrResp[0] = Sw1Sw2.SW1SW2_F802;
				return strErrResp;
			} else if(shrResult1.length < 12) {
				shrResult1 = BasicUtil.shortArrayLeftAppend(shrResult1, 12, (short)0x00);
				if(shrResult1 == null) {
					strErrResp[0] = Sw1Sw2.SW1SW2_F802;
					return strErrResp;
				}
			}
			
			digitalStampResult = BasicUtil.shortToByteDec(shrResult1, (short)0, (short)(shrResult1.length));
			if(digitalStampResult == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_F802;
				return strErrResp;
			}
		} else if(digitalStampLen == 1) {															//digital stamp length: 8 radix32 digits
			
			int bitsToMove = 8-lenBits%8;
			bitData1 = BasicUtil.byteStringRightMove(bitData1, bitsToMove);		
			if(bitData1 == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_F802;
				return strErrResp;
			}
			
			digitalStampResult = TransUtil.arrayBinaryToRadix32(bitData1, lenBits);
			if(digitalStampResult == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_F802;
				return strErrResp;
			}
		} else {
			strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
			return strErrResp;
		}
		
		String strDigitalStampResult = new String(digitalStampResult, 0, digitalStampResult.length);
		
		String[] strOkResp = new String[5];
		strOkResp[0] = Sw1Sw2.SW1SW2_OK;
		strOkResp[1] = strDigitalStampResult;
		strOkResp[2] = pan;
		if(panSn != null) {
			strOkResp[3] = panSn;
		} else {
			strOkResp[3] = TlvTag.VAL_D_5F34;
		}
		strOkResp[4] = aip;
		
		return strOkResp;
	}
	
	//calculate enhanced digital stamp number
	private static String[] calculateEnhancedDigitalStamp(Media media, byte[] appName,
			byte mode, byte digitalStampLen,
			String strChallange, String strAmount, String strAccount) {
		
		int lenL;
		int[] lenV = {0};
		byte[] bytResp = null;
		String strResp = null;
		String sw1sw2 = null;
		String[] strErrResp = new String[1];
		RApdu rApdu = null;
		
		//get challenge
		byte[] bytTransChallenge = null;
		if(strChallange != null) {
			bytTransChallenge = BasicUtil.byteAsciiToByteBcd(strChallange.getBytes(), false, false);
			if(bytTransChallenge == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		//get amount
		byte[] bytTransAmount = null;
		if(strAmount != null) {
			bytTransAmount = BasicUtil.byteAsciiToByteBcd(strAmount.getBytes(), false, false);
			if(bytTransAmount == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		//get amount
		byte[] bytTransAccount = null;
		if(strAccount != null) {
			bytTransAccount = BasicUtil.byteAsciiToByteBcd(strAccount.getBytes(), false, false);
			if(bytTransAccount == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		//select application
		rApdu = transSelect(media, appName);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		bytResp = rApdu.getDataBytes();
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		
		if(lenL < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		if(bytResp.length < (1 + lenL + lenV[0])) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		/*String curTime = null;
		byte[] bytCurTime = null;
		curTime = AndroidUtil.getCurTime();
		bytCurTime = BasicUtil.byteAsciiToByteBcd(curTime.getBytes(), false, false);
		
		if(bytCurTime == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD81;
			return strErrResp;
		} else if(bytCurTime.length != 3) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD81;
			return strErrResp;
		}
		
		String curDate = null;
		byte[] bytCurDate = null;
		curDate = AndroidUtil.getCurDate();
		bytCurDate = BasicUtil.byteAsciiToByteBcd(curDate.getBytes(), false, false);
		
		if(bytCurDate == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD81;
			return strErrResp;
		} else if(bytCurDate.length != 3) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD81;
			return strErrResp;
		}*/
		
		rApdu = transGpo(media, rApdu.getDataBytes(), bytTransChallenge, bytTransAmount,
				null, null, null,
				null, null, null);
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		byte[] bytRespGpo = rApdu.getDataBytes();
		lenL = BerTlv.getLenLen(bytRespGpo, 1, lenV);
		if(lenL < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		if(bytRespGpo.length < (1 + lenL + lenV[0])) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		//check GPO response, AFL must exist
		int lenRespGpo = bytRespGpo.length;
		int lenAfl = bytRespGpo.length-1 -lenL-2;
		if((lenAfl < 4) || ((lenAfl%4) !=0 )) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		if((bytRespGpo[1+lenL]&(byte)0x60) == 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F807;
			return strErrResp;
		}
		
		bytRespGpo = TransUtil.sortByteString(bytRespGpo, 1+lenL+2, 4, 0, true);
		if(bytRespGpo == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		strResp = rApdu.getDataHexString();
		String aip = strResp.substring(1+lenL + 1+lenL, 1+lenL + 1+lenL + 4);
		
		int sfi;
		int recEnd;
		int recStart;
		int offsetAfl = 1+lenL+2;
		
		int j;
		int offset = 0;
		int mandatoryItemFound = 0;
		int optionalItemFound = 0;
		boolean errOccured = false;
		
		boolean ddolFound = false;
		boolean caIndexFound = false;
		boolean issuerCertificateFound = false;
		boolean issuerRemainderFound = false;
		boolean issuerIndexFound = false;
		boolean icCertificateFound = false;
		boolean icRemainderFound = false;
		boolean icIndexFound = false;
		boolean panSnFound = false;
		boolean tag9F4AFound = false;
		
		int loc5F2A = -1;
		int loc9F1A = -1;
		int locAmount = -1;
		int locChallenge = -1;
		int lenDdol = -1;
		int lenAllDdol = -1;
		int sadNumber = 0;
		
		String caIndex = null;
		String issuerCertificate = null;
		String issuerRemainder = null;
		String issuerIndex = null;
		String icCertificate = null;
		String icRemainder = null;
		String icIndex = null;
		String sad = "";
		String panSn = null;
		String val9F4A = null;
		
		while(lenRespGpo > offsetAfl) {
			
			//prepare APDU Read Record
			sfi = (bytRespGpo[offsetAfl]&0xF8);
			recStart = bytRespGpo[offsetAfl+1]&0xFF;
			recEnd = bytRespGpo[offsetAfl+2]&0xFF;
			sadNumber = bytRespGpo[offsetAfl+3]&0xFF;
			
			while((recStart&0xFF) <= (recEnd&0xFF)) {
				
				PbocReadRecord pbocReadRecord = new PbocReadRecord((byte)sfi, (byte)recStart);			
				sw1sw2 = Sw1Sw2.SW1SW2_OK;
				rApdu = media.sendApdu(pbocReadRecord);								//Read Record
				
				//check response
				if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
					if(sw1sw2.equals(Sw1Sw2.SW1SW2_6A83) == true) {
						recStart++;
						continue;
					} else {
						errOccured = true;
						break;
					}
				}
				
				bytResp = rApdu.getDataBytes();
				strResp = rApdu.getDataHexString();
				
				if(sadNumber-- > 0) {
					lenV[0] = 0;
					int lenLen = BerTlv.getLenLen(bytResp, 1, lenV);
					
					if(lenLen < 1) {
						errOccured = true;
						break;
					}
					
					if(bytResp.length < (1 + lenL + lenV[0])) {
						errOccured = true;
						break;
					}
					
					String tempSad = strResp.substring(1+lenLen + 1+lenLen, 1+lenLen+lenV[0] + 1+lenLen+lenV[0]);
					sad = sad+tempSad;
				}
				
				if(ddolFound == false) {				
					
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_DDOL);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						lenDdol = lenV[0];
						if(bytResp.length < (offset + lenL + lenDdol)) {
							errOccured = true;
							break;
						}
						lenAllDdol = Dol.getDolDataLen(bytResp, offset+lenL, offset+lenL+lenDdol, false);
						
						if(lenAllDdol < 1) {
							errOccured = true;
							break;
						}
						
						//check if these tags exist in CDOL1
						loc5F2A = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenDdol, TlvTag.TAG_B_5F2A);
						loc9F1A = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenDdol, TlvTag.TAG_B_9F1A);
						locChallenge = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenDdol, TlvTag.TAG_B_9F37);
						locAmount = Dol.getValLocInDol(bytResp, offset+lenL, offset+lenL+lenDdol, TlvTag.TAG_B_9F02);
						
						ddolFound = true;
						mandatoryItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(caIndexFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_8F);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						caIndex = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						caIndexFound = true;
						mandatoryItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(issuerCertificateFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_90);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						issuerCertificate = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						issuerCertificateFound = true;
						mandatoryItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(issuerRemainderFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_92);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						issuerRemainder = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						issuerRemainderFound = true;
						mandatoryItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(issuerIndexFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_9F32);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						issuerIndex = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						issuerIndexFound = true;
						mandatoryItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(icCertificateFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_9F46);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						icCertificate = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						icCertificateFound = true;
						mandatoryItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(icRemainderFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_9F48);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						icRemainder = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						icRemainderFound = true;
						optionalItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(icIndexFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_9F47);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						icIndex = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						icIndexFound = true;
						mandatoryItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(panSnFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_5F34);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						panSn = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						panSnFound = true;
						optionalItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				if(tag9F4AFound == false) {
					offset = BerTlv.getTagLoc(bytResp, TlvTag.TAG_B_9F4A);
					if(offset > 0) {																		//CDOL1 found
						
						lenV[0] = 0;
						lenL = BerTlv.getLenLen(bytResp, offset, lenV);
						if(lenL < 0) {
							errOccured = true;
							break;
						}
						
						int len = lenV[0];
						if(bytResp.length < (offset + lenL + len)) {
							errOccured = true;
							break;
						}
						val9F4A = strResp.substring(offset+lenL + offset+lenL, offset+lenL+len + offset+lenL+len);
						
						tag9F4AFound = true;
						optionalItemFound ++;
						if((mandatoryItemFound > 6) && (optionalItemFound > 2)) {
							break;
						}
					}
				}
				
				recStart++;
			}
			
			if(((mandatoryItemFound > 6) && (optionalItemFound > 2)) || (errOccured == true)) {
				break;
			}
			
			offsetAfl += 4;
		}
		
		if(errOccured == true) {
			if(sw1sw2.equals(Sw1Sw2.SW1SW2_OK) == false) {
				strErrResp[0] = sw1sw2;
				return strErrResp;
			} else {
				strErrResp[0] = Sw1Sw2.SW1SW2_F801;
				return strErrResp;
			}
		}
		
		//8 items must exist
		if(mandatoryItemFound < 7) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		//challenge must exist
		if(locChallenge < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		if(sad.length() < 1) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		//initialize APDU GAC
		byte ddol[] = new byte[lenAllDdol];
		BasicUtil.initByteArray(ddol, (byte)0x00);
		
		//add tag values for GAC
		for(j = 0; j < 2; j++) {
			
			if(loc9F1A > -1) {
				ddol[loc9F1A+j] = TlvTag.TAG_B_9F1A[j];
			}
			
			if(loc5F2A > -1) {
				ddol[loc5F2A+j] = TlvTag.TAG_B_5F2A[j];
			}
		}
		
		/*for(j = 0; j < 3; j++) {
			
			if(loc9A > -1) {
				ddol[loc9A+j] = bytCurDate[j];
			}
			
			if(loc9F21 > -1) {
				ddol[loc9F21+j] = bytCurTime[j];
			}
		}*/
		
		if(locAmount > -1) {
			if(bytTransAmount != null) {				
				for(j = 0; j < bytTransAmount.length; j++) {
					ddol[locAmount+j] = bytTransAmount[j];
				}
			}
		}
		
		String enhangcedChallenge = strAmount + strAccount + strChallange;
		byte[] bytEnhangcedChallenge = null;
		if(enhangcedChallenge != null) {
			bytEnhangcedChallenge = BasicUtil.byteAsciiToByteBcd(enhangcedChallenge.getBytes(), false, false);
			if(bytEnhangcedChallenge == null) {
				strErrResp[0] = Sw1Sw2.SW1SW2_FC60;
				return strErrResp;
			}
		}
		
		try {
			bytEnhangcedChallenge = TransUtil.encryptSha(bytEnhangcedChallenge);
		} catch(Exception e) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD91;
			return strErrResp;
		}
		if(bytEnhangcedChallenge == null) {
			strErrResp[0] = Sw1Sw2.SW1SW2_FD91;
			return strErrResp;
		}
		
		for(j = 0; j < 4; j++) {
			ddol[locChallenge+j] = bytEnhangcedChallenge[j];
		}
		
		PbocInternalAuth pbocInternalAuth = new PbocInternalAuth(ddol);
		rApdu = media.sendApdu(pbocInternalAuth);											//GAC
		if((sw1sw2 = rApdu.getSw1Sw2String()).equals(Sw1Sw2.SW1SW2_OK) == false) {
			strErrResp[0] = sw1sw2;
			return strErrResp;
		}
		
		bytResp = rApdu.getDataBytes();
		strResp = rApdu.getDataHexString();
		
		lenV[0] = 0;
		lenL = BerTlv.getLenLen(bytResp, 1, lenV);
		if(lenL < 0) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		if(bytResp.length < (1 + lenL + lenV[0])) {
			strErrResp[0] = Sw1Sw2.SW1SW2_F801;
			return strErrResp;
		}
		
		String strDigitalStamp = strResp.substring(1+lenL + 1+lenL, 1+lenL+lenV[0] + 1+lenL+lenV[0]);
		
		String rid = null;
		rid = BasicUtil.bytesToHexString(appName, 0, appName.length).substring(0, 10);
		
		String[] strOkResp = new String[12];
		strOkResp[0] = Sw1Sw2.SW1SW2_OK;
		strOkResp[1] = strDigitalStamp;
		strOkResp[2] = rid;
		strOkResp[3] = caIndex;
		strOkResp[4] = issuerCertificate;
		strOkResp[5] = issuerRemainder;
		strOkResp[6] = issuerIndex;
		strOkResp[7] = icCertificate;
		strOkResp[8] = icRemainder;
		strOkResp[9] = icIndex;
		if(val9F4A != null) {
			if(val9F4A.equals(TlvTag.TAG_AIP)) {
				sad = sad+aip;
			}
		}
		strOkResp[10] = sad;
		if(panSn != null) {
			strOkResp[11] = panSn;
		} else {
			strOkResp[11] = TlvTag.VAL_D_5F34;
		}
		
		return strOkResp;
	}
}
