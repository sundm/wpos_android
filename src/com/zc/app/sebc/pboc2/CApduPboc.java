package com.zc.app.sebc.pboc2;

import com.zc.app.sebc.iso7816.CApdu;
import com.zc.app.sebc.lx.Longxingcard;
import com.zc.app.sebc.util.BasicUtil;
import com.zc.app.utils.ZCLog;

public class CApduPboc extends CApdu {

	public static class PbocSelect extends CApduPboc {

		public PbocSelect(byte[] aid) {
			if (aid != null) {
				if (aid.length > 4) {
					apdu = new byte[5 + aid.length + 1];
					apdu[0] = (byte) 0x00;
					apdu[1] = (byte) 0xA4;
					apdu[2] = (byte) 0x04;
					apdu[3] = (byte) 0x00;
					apdu[4] = (byte) ((aid.length) & 0xFF);
					BasicUtil.byteStringCopy(aid, 0, apdu, 5, aid.length);
					apdu[5 + aid.length] = (byte) 0x00;
				}
			}
		}

		public PbocSelect(String aid) {
			if (aid != null) {
				if (aid.length() > 9) {
					byte[] bytAid = BasicUtil.byteAsciiToByteBcd(
							aid.getBytes(), false, false);
					if (bytAid != null) {
						apdu = new byte[5 + bytAid.length + 1];
						apdu[0] = (byte) 0x00;
						apdu[1] = (byte) 0xA4;
						apdu[2] = (byte) 0x04;
						apdu[3] = (byte) 0x00;
						apdu[4] = (byte) ((bytAid.length) & 0xFF);
						BasicUtil.byteStringCopy(bytAid, 0, apdu, 5,
								bytAid.length);
						apdu[5 + bytAid.length] = (byte) 0x00;
					}
				}
			}
		}
	}

	public static class PbocGpo extends CApduPboc {

		public PbocGpo(byte[] pdol) {
			if (pdol == null) {
				apdu = new byte[5 + 2 + 1];
				apdu[0] = (byte) 0x80;
				apdu[1] = (byte) 0xA8;
				apdu[2] = (byte) 0x00;
				apdu[3] = (byte) 0x00;
				apdu[4] = (byte) 0x02;
				apdu[5] = (byte) 0x83;
				apdu[6] = (byte) 0x00;
				apdu[7] = (byte) 0x00;
			} else {
				apdu = new byte[5 + 2 + pdol.length + 1];
				apdu[0] = (byte) 0x80;
				apdu[1] = (byte) 0xA8;
				apdu[2] = (byte) 0x00;
				apdu[3] = (byte) 0x00;
				apdu[4] = (byte) ((pdol.length + 2) & 0x00FF);
				apdu[5] = (byte) 0x83;
				apdu[6] = (byte) (pdol.length & 0x00FF);
				BasicUtil.byteStringCopy(pdol, 0, apdu, 5 + 2, pdol.length);
				apdu[5 + 2 + pdol.length] = (byte) 0x00;
			}
		}
	}

	public static class PbocReadRecord extends CApduPboc {

		public PbocReadRecord(int sfi, int record) {
			if ((sfi > 0) && (sfi < 32)) {
				if ((record > 0) && (record < 256)) {
					apdu = new byte[5];
					apdu[0] = (byte) 0x00;
					apdu[1] = (byte) 0xB2;
					apdu[2] = (byte) record;
					apdu[3] = (byte) ((sfi << 3) | 0x04);
					apdu[4] = (byte) 0x00;
				}
			}
		}

		public PbocReadRecord(byte sfiMoved, byte record) {
			if ((sfiMoved & 0x00F8) > 0) {
				if ((record & 0x00FF) > 0) {
					apdu = new byte[5];
					apdu[0] = (byte) 0x00;
					apdu[1] = (byte) 0xB2;
					apdu[2] = (byte) record;
					apdu[3] = (byte) ((sfiMoved & 0x00F8) | 0x04);
					apdu[4] = (byte) 0x00;
				}
			}
		}
	}

	public static class PbocGac extends CApduPboc {

		public PbocGac(byte p1, byte[] cdol) {
			if (cdol == null) {
				apdu = new byte[5 + 1];
				apdu[0] = (byte) 0x80;
				apdu[1] = (byte) 0xAE;
				apdu[2] = (byte) p1;
				apdu[3] = (byte) 0x00;
				apdu[4] = (byte) 0x00;
				apdu[5] = (byte) 0x00;
			} else {
				apdu = new byte[5 + cdol.length + 1];
				apdu[0] = (byte) 0x80;
				apdu[1] = (byte) 0xAE;
				apdu[2] = (byte) p1;
				apdu[3] = (byte) 0x00;
				apdu[4] = (byte) ((cdol.length) & 0xFF);
				BasicUtil.byteStringCopy(cdol, 0, apdu, 5, cdol.length);
				apdu[5 + cdol.length] = (byte) 0x00;
			}
		}
	}

	public static class PbocInternalAuth extends CApduPboc {

		public PbocInternalAuth(byte[] ddol) {
			if (ddol == null) {
				apdu = new byte[5 + 1];
				apdu[0] = (byte) 0x00;
				apdu[1] = (byte) 0x88;
				apdu[2] = (byte) 0x00;
				apdu[3] = (byte) 0x00;
				apdu[4] = (byte) 0x00;
				apdu[5] = (byte) 0x00;
			} else {
				apdu = new byte[5 + ddol.length + 1];
				apdu[0] = (byte) 0x00;
				apdu[1] = (byte) 0x88;
				apdu[2] = (byte) 0x00;
				apdu[3] = (byte) 0x00;
				apdu[4] = (byte) ((ddol.length) & 0xFF);
				BasicUtil.byteStringCopy(ddol, 0, apdu, 5, ddol.length);
				apdu[5 + ddol.length] = (byte) 0x00;
			}
		}
	}

	public static class PbocExternalAuth extends CApduPboc {

		public PbocExternalAuth(byte[] auth) {
			if (auth != null) {
				if (auth.length == 10) {
					apdu = new byte[5 + auth.length];
					apdu[0] = (byte) 0x00;
					apdu[1] = (byte) 0x82;
					apdu[2] = (byte) 0x00;
					apdu[3] = (byte) 0x00;
					apdu[4] = (byte) ((auth.length) & 0xFF);
					BasicUtil.byteStringCopy(auth, 0, apdu, 5, auth.length);
				}
			}
		}

		public PbocExternalAuth(byte[] arpc, byte[] answer) {
			if ((arpc != null) && (answer != null)) {
				if ((arpc.length == 8) && (answer.length == 2)) {
					apdu = new byte[5 + arpc.length + answer.length];
					apdu[0] = (byte) 0x00;
					apdu[1] = (byte) 0x82;
					apdu[2] = (byte) 0x00;
					apdu[3] = (byte) 0x00;
					apdu[4] = (byte) ((arpc.length + answer.length) & 0xFF);
					BasicUtil.byteStringCopy(arpc, 0, apdu, 5, arpc.length);
					BasicUtil.byteStringCopy(answer, 0, apdu, 5 + arpc.length,
							answer.length);
				}
			}
		}
	}

	public static class PbocGetData extends CApduPboc {

		public PbocGetData(byte p1, byte p2) {
			if ((p1 & 0x00FF) > 0) {
				apdu = new byte[5];
				apdu[0] = (byte) 0x80;
				apdu[1] = (byte) 0xCA;
				apdu[2] = p1;
				apdu[3] = p2;
				apdu[4] = (byte) 0x00;
			}
		}

		public PbocGetData(byte[] p1p2) {
			if (p1p2 != null) {
				if (p1p2.length > 1) {
					if ((p1p2[0] & 0x00FF) > 0) {
						apdu = new byte[5];
						apdu[0] = (byte) 0x80;
						apdu[1] = (byte) 0xCA;
						apdu[2] = p1p2[0];
						apdu[3] = p1p2[1];
						apdu[4] = (byte) 0x00;
					}
				}
			}
		}
	}

	public static class PbocPutData extends CApduPboc {

		public PbocPutData(byte[] apdu) {
			if (apdu != null) {
				if (apdu.length > 9) {
					if ((apdu[0] == (byte) 0x04) && (apdu[1] == (byte) 0xDA)) {
						if ((apdu[2] & 0x00FF) > 0) {
							if ((apdu[4] & 0x00FF) > 4) {
								this.apdu = apdu;
							}
						}
					}
				}
			}
		}

		public PbocPutData(byte p1, byte p2, byte[] data) {
			if ((p1 & 0x00FF) > 0) {
				if (data != null) {
					if (data.length > 0) {
						apdu = new byte[5 + data.length];
						apdu[0] = (byte) 0x04;
						apdu[1] = (byte) 0xDA;
						apdu[2] = p1;
						apdu[3] = p2;
						apdu[4] = (byte) ((data.length) & 0xFF);
						BasicUtil.byteStringCopy(data, 0, apdu, 5, data.length);
					}
				}
			}
		}
	}

	public static class PbocGetBalance extends CApduPboc {

		public PbocGetBalance(byte p2) {
			if (((p2 & 0x00FF) > 0) || ((p2 & 0x00FF) < 3)) {
				apdu = new byte[5];
				apdu[0] = (byte) 0x80;
				apdu[1] = (byte) 0x5C;
				apdu[2] = (byte) 0x00;
				apdu[3] = p2;
				apdu[4] = (byte) 0x04;
			}
		}
	}

	public static class PbocVerify extends CApduPboc {

		public PbocVerify(byte p2, byte[] pin) {
			if (pin != null) {
				if (pin.length > 0) {
					apdu = new byte[5 + pin.length];
					apdu[0] = (byte) 0x00;
					apdu[1] = (byte) 0x20;
					apdu[2] = (byte) 0x00;
					apdu[3] = (byte) p2;
					apdu[4] = (byte) ((pin.length) & 0xFF);
					BasicUtil.byteStringCopy(pin, 0, apdu, 5, pin.length);
				}
			}
		}

		public PbocVerify(byte p2, String pin) {
			if (pin != null) {
				if (pin.length() > 0) {
					byte[] bytPin = BasicUtil.byteAsciiToByteBcd(
							pin.getBytes(), true, true);
					if (bytPin != null) {
						apdu = new byte[5 + bytPin.length];
						apdu[0] = (byte) 0x00;
						apdu[1] = (byte) 0x20;
						apdu[2] = (byte) 0x00;
						apdu[3] = (byte) p2;
						apdu[4] = (byte) ((bytPin.length) & 0xFF);
						BasicUtil.byteStringCopy(bytPin, 0, apdu, 5,
								bytPin.length);
					}
				}
			}
		}
	}

	public static class PbocInitForLoad extends CApduPboc {

		public PbocInitForLoad(byte p2, byte key, byte[] amount, byte[] pos) {
			if (((p2 & 0x00FF) > 0) || ((p2 & 0x00FF) < 3)) {
				if ((amount != null) && (pos != null)) {
					if ((amount.length == 4) && (pos.length == 6)) {
						apdu = new byte[5 + 1 + amount.length + pos.length + 1];
						apdu[0] = (byte) 0x80;
						apdu[1] = (byte) 0x50;
						apdu[2] = (byte) 0x00;
						apdu[3] = p2;
						apdu[4] = (byte) (1 + amount.length + pos.length);
						apdu[5] = key;
						BasicUtil.byteStringCopy(amount, 0, apdu, 5 + 1,
								amount.length);
						BasicUtil.byteStringCopy(pos, 0, apdu,
								5 + 1 + amount.length, pos.length);
						apdu[5 + 1 + amount.length + pos.length] = (byte) 0x10;
					}
				}
			}
		}

		public PbocInitForLoad(byte p2, byte key, String amount, String pos) {
			if (((p2 & 0x00FF) > 0) || ((p2 & 0x00FF) < 3)) {
				if ((amount != null) && (pos != null)) {

					amount = BasicUtil.trimStringLeftZero(amount);
					if (amount == null) {
						amount = "";
					}

					if ((amount.length() < 9) && (pos.length() == 12)) {

						byte[] bytAmount = null;
						long longAmount = Long.parseLong(amount);
						bytAmount = BasicUtil.longToBytes(longAmount);

						byte[] bytPos = null;
						bytPos = BasicUtil.byteAsciiToByteBcd(pos.getBytes(),
								false, false);

						if (bytPos != null) {
							apdu = new byte[5 + 1 + bytAmount.length / 2
									+ pos.length() / 2 + 1];
							apdu[0] = (byte) 0x80;
							apdu[1] = (byte) 0x50;
							apdu[2] = (byte) 0x00;
							apdu[3] = p2;
							apdu[4] = (byte) (1 + bytAmount.length / 2 + pos
									.length() / 2);
							apdu[5] = key;
							BasicUtil.byteStringCopy(bytAmount,
									bytAmount.length / 2, apdu, 5 + 1,
									bytAmount.length / 2);
							BasicUtil
									.byteStringCopy(bytPos, 0, apdu,
											5 + 1 + bytAmount.length / 2,
											bytPos.length);
							apdu[5 + 1 + bytAmount.length / 2 + pos.length()
									/ 2] = (byte) 0x10;
						}
					}
				}
			}
		}
	}

	public static class PbocCreditForLoad extends CApduPboc {

		public PbocCreditForLoad(String apdu) {
			if (apdu != null) {
				if (apdu.length() == 17 + 17) {
					byte[] loadApdu = BasicUtil.byteAsciiToByteBcd(
							apdu.getBytes(), false, false);
					if (loadApdu != null) {
						if (loadApdu.length == 17) {

							if ((loadApdu[0] == (byte) 0x80)
									&& (loadApdu[1] == (byte) 0x52)
									&& (loadApdu[2] == (byte) 0x00)
									&& (loadApdu[3] == (byte) 0x00)
									&& (loadApdu[4] == (byte) 0x0B)
									&& (loadApdu[loadApdu.length - 1] == (byte) 0x04)) {
								this.apdu = loadApdu;
							}
						}
					}
				}
			}
		}
	}

	public static class PbocReadBinary extends CApduPboc {

		public PbocReadBinary(byte sfi, byte offset) {
			if (((sfi & 0x00FF) > 0) || ((sfi & 0x00FF) < 0x20)) {
				apdu = new byte[5];
				apdu[0] = (byte) 0x00;
				apdu[1] = (byte) 0xB0;
				apdu[2] = (byte) (0x80 | sfi);
				apdu[3] = offset;
				apdu[4] = (byte) 0x00;
			}
		}
	}

	public static class PbocInitForPurchase extends CApduPboc {

		public PbocInitForPurchase(byte keyID, String amount, String posID,
				byte p2) {
			if (((p2 & 0x00FF) > 0) || ((p2 & 0x00FF) < 3)) {
				if ((amount != null) && (posID != null)) {

					amount = BasicUtil.trimStringLeftZero(amount);

					ZCLog.i(Longxingcard.TAG, "amount:" + amount);
					ZCLog.i(Longxingcard.TAG, "posID:" + posID);

					if (amount == null) {
						amount = "";
					}

					if ((amount.length() < 9) && (posID.length() == 12)) {

						byte[] bytAmount = null;
						long longAmount = Long.parseLong(amount);
						bytAmount = BasicUtil.longToBytes(longAmount);

						ZCLog.i(Longxingcard.TAG,
								"amount byte: "
										+ BasicUtil.bytesToHexString(bytAmount,
												0, bytAmount.length));

						byte[] bytPos = null;
						bytPos = BasicUtil.byteAsciiToByteBcd(posID.getBytes(),
								false, false);

						ZCLog.i(Longxingcard.TAG,
								"pos byte: "
										+ BasicUtil.bytesToHexString(bytPos, 0,
												bytPos.length));

						if (bytPos != null) {
							apdu = new byte[5 + 1 + bytAmount.length
									+ posID.length() / 2 + 1];
							apdu[0] = (byte) 0x80;
							apdu[1] = (byte) 0x50;
							apdu[2] = (byte) 0x01;
							apdu[3] = p2;
							apdu[4] = (byte) 0x0B;
							apdu[5] = keyID;

							BasicUtil.byteStringCopy(bytAmount, 0, apdu, 5 + 1,
									bytAmount.length);
							BasicUtil.byteStringCopy(bytPos, 0, apdu,
									5 + 1 + bytAmount.length, bytPos.length);
							apdu[5 + 1 + bytAmount.length + posID.length() / 2] = (byte) 0x0F;

							ZCLog.i(Longxingcard.TAG, BasicUtil
									.bytesToHexString(apdu, 0, apdu.length));
						}
					}
				}
			}
		}
	}

	public static class PbocCreditForPurchase extends CApduPboc {
		public PbocCreditForPurchase(String data) {
			if (data != null) {
				if (data.length() == 30) {
					byte[] bytAid = BasicUtil.byteAsciiToByteBcd(
							data.getBytes(), false, false);
					if (bytAid != null) {
						apdu = new byte[5 + bytAid.length + 1];
						apdu[0] = (byte) 0x80;
						apdu[1] = (byte) 0x54;
						apdu[2] = (byte) 0x01;
						apdu[3] = (byte) 0x00;
						apdu[4] = (byte) 0x0F;
						BasicUtil.byteStringCopy(bytAid, 0, apdu, 5,
								bytAid.length);
						apdu[5 + bytAid.length] = (byte) 0x08;
					}
				}
			}
		}
	}
}
