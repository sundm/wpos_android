package com.zc.app.sebc.nfcmedia.nfc;

import java.io.IOException;
import java.nio.ByteBuffer;

import android.nfc.tech.IsoDep;

import com.zc.app.sebc.iso7816.CApdu;
import com.zc.app.sebc.iso7816.RApdu;
import com.zc.app.sebc.util.BasicUtil;
import com.zc.app.utils.ZCLog;

public final class IsoDepTag {

	public static final byte[] ERR_NFC_ISODEP_TRANSCEIVE = {(byte)0xFF, (byte)0x07};
	public static final int TIMEOUT_TRANSCEIVE_MINIMUM = 10000;
	public static final int TIMEOUT_TRANSCEIVE_MAXIMUM = 2147483647;
	
	public static int timeout_transceive = 100000;
	public IsoDep isoDep;
	
	public IsoDepTag (IsoDep isoDep) {
		this.isoDep = isoDep;
	}
	
	public IsoDepTag (IsoDep isoDep, int timeout) {
		this.isoDep = isoDep;
		if(timeout > TIMEOUT_TRANSCEIVE_MINIMUM) {
			timeout_transceive = timeout;
		}
	}
	
	public void setTimeout(int timeout) {
		if(timeout > TIMEOUT_TRANSCEIVE_MINIMUM) {
			timeout_transceive = timeout;
		}
	}
	
	public boolean isConnected() {
		
		return isoDep.isConnected();
	}
	
	public int connect() {
		try {
			isoDep.setTimeout(timeout_transceive);
			isoDep.connect();
			return 0;
		} catch(IOException e) {
			return -1;
		} catch(Exception e) {
			return -2;
		}
	}
	
	public int connect(int timeout) {
		try {
			if(timeout > TIMEOUT_TRANSCEIVE_MINIMUM) {
				isoDep.setTimeout(timeout);
			} else {
				isoDep.setTimeout(timeout_transceive);
			}
			isoDep.connect();
			return 0;
		} catch(IOException e) {
			return -1;
		} catch(Exception e) {
			return -2;
		}
	}
	
	public int close() {
		try {
			isoDep.close();
			return 0;
		} catch(IOException e) {
			return -1;
		} catch(Exception e) {
			return -2;
		}
	}
	
	public byte[] transceive(byte[] cmd) {
		
		ZCLog.i("card", "---->" + BasicUtil.bytesToHexString(cmd, 0, cmd.length));
		
		byte[] resp;
		try {
			resp = isoDep.transceive(cmd);
		} catch (Exception e) {
			return ERR_NFC_ISODEP_TRANSCEIVE;
		}
		
		ZCLog.i("card", "<----" + BasicUtil.bytesToHexString(resp, 0, resp.length));
		
		return resp;
	}
	
	public RApdu sendApdu(byte[] apdu) {
		
		byte[] response;
		
		
		
		if(apdu == null) {
			response = CApdu.ERR_APDU_NULL;
		} else if(apdu.length < 4) {
			response = CApdu.ERR_APDU_HEAD_LENGTH;
		} else {
			ByteBuffer byteBuffer = ByteBuffer.allocate(apdu.length);
			byteBuffer.put(apdu);
			response = transceive(byteBuffer.array());
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
			ByteBuffer byteBuffer = ByteBuffer.allocate(apdu.length+1);
			byteBuffer.put(apdu)
					.put(le);
			response = transceive(byteBuffer.array());
		}
		
		RApdu rApdu = new RApdu(response);
		return rApdu;
	}
}
