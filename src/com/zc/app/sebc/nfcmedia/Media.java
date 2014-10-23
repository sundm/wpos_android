package com.zc.app.sebc.nfcmedia;

import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Parcelable;

import com.zc.app.sebc.iso7816.CApdu;
import com.zc.app.sebc.iso7816.RApdu;
import com.zc.app.sebc.nfcmedia.nfc.IsoDepTag;

public final class Media {

	public static final byte[] ERR_MEDIA_UNKNOWN = {(byte)0xFF, (byte)0x00};
	
	public final static String SW1SW2_OK = "9000";		//OK_CARD = "";
	public final static String SW1SW2_FF00 = "FF00";	//ERR_MEDIA_UNKNOWN = "所选媒介未知";
	public final static String SW1SW2_FF01 = "FF01";	//ERR_NO_MEDIA = "媒介未初始化";
	public final static String SW1SW2_FF05 = "FF05";	//ERR_NFC_GET_TAG = "未能获得NFC标签";
	
	public int timeout_transceive = 10000;
	public int mediaId = 0;
	
	public IsoDepTag isoDepTag;
	public IsoDep isoDep;
	
	public Media(byte mediaId, Parcelable parcelNfc) {
		
		if(mediaId == 1) {
			if(parcelNfc != null) {
				final Tag tag = (Tag) parcelNfc;
				final IsoDep isoDep = IsoDep.get(tag);				
				if(isoDep != null) {
					this.isoDep = isoDep;
					this.isoDepTag = new IsoDepTag(isoDep);
					this.mediaId = 1;
				}
			}
		}
	}
	
	public Media(IsoDepTag isoDepTag) {
		this.isoDepTag = isoDepTag;
		mediaId = 1;
	}
	
	public Media(IsoDep isoDep) {
		this.isoDep = isoDep;
		this.isoDepTag = new IsoDepTag(isoDep);
		mediaId = 1;
	}
	
	public Media(IsoDepTag isoDepTag, int time_transceive) {
		this.isoDepTag = isoDepTag;
		if(time_transceive > 0) {
			timeout_transceive = time_transceive;
		}
		mediaId = 1;
	}
	
	public Media(IsoDep isoDep, int time_transceive) {
		this.isoDep = isoDep;
		this.isoDepTag = new IsoDepTag(isoDep);
		if(time_transceive > 0) {
			timeout_transceive = time_transceive;
		}
		mediaId = 1;
	}
	
	public boolean isMediaOk(byte mediaId) {
		switch(mediaId) {
		case 1:
			if(isoDepTag != null) {
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}
	}
	
	public String isMediaOk() {
		switch(mediaId) {
		case 1:
			if(isoDepTag != null) {
				return SW1SW2_OK;
			} else {
				return SW1SW2_FF05;
			}
		default:
			return SW1SW2_FF00;
		}
	}
	
	public void setTimeout(int time_transceive) {
		if(time_transceive > 0) {
			timeout_transceive = time_transceive;
		}
	}
	
	public boolean isConnected() {
		switch(mediaId) {
		case 1:
			return isoDepTag.isConnected();
		default:
			return false;
		}
	}
	
	public int connect() {
		switch(mediaId) {
		case 1:
			return isoDepTag.connect();
		default:
			return -3;
		}
	}
	
	public int connect(int timeout) {
		switch(mediaId) {
		case 1:
			return isoDepTag.connect(timeout);
		default:
			return -3;
		}
	}
	
	public int close() {
		switch(mediaId) {
		case 1:
			return isoDepTag.close();
		default:
			return -3;
		}
	}
	
	public RApdu sendApdu(byte[] apdu) {
		
		byte[] response;
		
		if(apdu == null) {
			response = CApdu.ERR_APDU_NULL;
		} else if(apdu.length < 4) {
			response = CApdu.ERR_APDU_HEAD_LENGTH;
		} else {
			switch(mediaId) {
			case 1:
				return isoDepTag.sendApdu(apdu);
			default:
				response = ERR_MEDIA_UNKNOWN;
			}
		}
		
		RApdu rApdu = new RApdu(response);
		return rApdu;
	}
	
	public RApdu sendApdu(byte[] apdu, byte le) {
		
		byte[] response;
		
		if(apdu == null) {
			response = CApdu.ERR_APDU_NULL;
		} else if(apdu.length < 4) {
			response = CApdu.ERR_APDU_HEAD_LENGTH;
		} else {
			switch(mediaId) {
			case 1:
				return isoDepTag.sendApdu(apdu, le);
			default:
				response = ERR_MEDIA_UNKNOWN;
			}
		}
		
		RApdu rApdu = new RApdu(response);
		return rApdu;
	}
	
	public RApdu sendApdu(CApdu apdu) {
		
		byte[] response;
		
		if(apdu == null) {
			response = CApdu.ERR_APDU_NULL;
		} else {
			switch(mediaId) {
			case 1:
				return isoDepTag.sendApdu(apdu.apdu);
			default:
				response = ERR_MEDIA_UNKNOWN;
			}
		}
		
		RApdu rApdu = new RApdu(response);
		
		return rApdu;
	}
	
	public static String checkMedia(Media media) {
		
		String sw1sw2;
		if(media == null) {
			return SW1SW2_FF01;
		} else {
			if((sw1sw2 = media.isMediaOk()) != SW1SW2_OK) {
				return sw1sw2;
			} else {
				return SW1SW2_OK;
			}
		}
	}
	
	public static boolean closeSessionMedia(Media media){
		
		if(media != null) {
			media.close();
		}
		return true;
	}
}
