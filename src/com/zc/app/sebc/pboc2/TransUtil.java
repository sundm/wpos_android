package com.zc.app.sebc.pboc2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.zc.app.sebc.bridge.TransDetailSetting;
import com.zc.app.sebc.util.BasicUtil;

public final class TransUtil {
	
	private final static String HEX = "0123456789ABCDEF";
	
	public final static byte[] BIT_SHIFT_MASK_IN_BYTE = {										//mask for bit shift
		(byte)0x80, (byte)0x40, (byte)0x20, (byte)0x10,
		(byte)0x08, (byte)0x04, (byte)0x02, (byte)0x01};
	
	public final static byte[] BIT_GET_MASK_IN_BYTE = {											//mask for get bits in a byte
		(byte)0x80, (byte)0xC0, (byte)0xE0, (byte)0xF0,
		(byte)0xF8, (byte)0xFC, (byte)0xFE, (byte)0xFF};
	
	public static String toReadableTime(String[] template, String time) {
		
		if(BasicUtil.isEmptyStringArray(template)) {
			return null;
		}
		
		if(BasicUtil.isEmptyString(time)) {
			return null;
		}
		
		if(time.length() != 6) {
			return null;
		}
		
		if(template.length != 3) {
			return null;
		}
		
		String newTime = time.substring(0, 2).concat(template[0])
				.concat(time.substring(2, 4)).concat(template[1])
				.concat(time.substring(4, time.length())).concat(template[2]);
		return newTime;
	}
	
	public static String toReadableDate(String[] template, String date) {
		
		if(BasicUtil.isEmptyStringArray(template)) {
			return null;
		}
		
		if(BasicUtil.isEmptyString(date)) {
			return null;
		}
		
		if(date.length() != 6) {
			return null;
		}
		
		if(template.length != 3) {
			return null;
		}
		
		String newDate = date.substring(0, 2).concat(template[0])
				.concat(date.substring(2, 4)).concat(template[1])
				.concat(date.substring(4, date.length())).concat(template[2]);
		return newDate;
	}
	
	public static String toReadableAmount(String amount) {
		
		if(BasicUtil.isEmptyString(amount)) {
			return null;
		}
		
		Double temp = Double.parseDouble(amount)/100;
		String newAmount = String.format(TransDetailSetting.TEMP_READABLE_AMOUNT, temp);
		
		return newAmount;
	}
	
	public static String replaceString(String string, String[][] lookup, int row) {
		
		if(BasicUtil.isEmptyString(string) == true) {
			return null;
		}
		
		if(BasicUtil.isEmptyStringMatrix(lookup) == true) {
			return null;
		}
		
		if(lookup.length < 2) {
			return null;
		}
		
		if(row < 0) {
			return null;
		}
		
		if(row >= lookup.length) {
			return null;
		}
		
		for(int i = 0; i < lookup[0].length; i++) {
			if(lookup[0][i] != null) {
				if(string.equals(lookup[0][i])) {
					return lookup[row][i];
				}
			}
		}
		return null;
	}
	
	private static String[] replaceStringArray(String[] array, String[][] lookup, int row, boolean flag, boolean chinese) {
		
		if(BasicUtil.isEmptyStringArray(array) == true) {
			return null;
		}
		
		if(BasicUtil.isEmptyStringMatrix(lookup) == true) {
			return null;
		}
		
		if(lookup.length < 2) {
			return null;
		}
		
		if(row < 0) {
			return null;
		}
		
		if(row >= lookup.length) {
			return null;
		}
		
		String newString;
		String[] result = new String[array.length];
		
		for(int i = 0; i < array.length; i++) {
			newString = replaceString(array[i], lookup, row);
			if(newString == null) {
				if(flag) {
					if(chinese) {
						newString = TransDetailSetting.READ_CN_TAG_UNKNOWN;
						result[i] = newString;
					} else {
						newString = TransDetailSetting.READ_EN_TAG_UNKNOWN;
						result[i] = newString;
					}
				}
			} else {
				result[i] = newString;
			}
		}
		
		return result;
	}
	
	public static String[][] adjustTableMatrix(String[] template, String[][] matrix, int start, String[] deletedType, boolean order) {
		
		String[][] error = new String[1][1];
		error[0][0] = Sw1Sw2.SW1SW2_FC60;
		
		if(BasicUtil.isEmptyStringArray(template)) {
			return error;
		}
		
		if(BasicUtil.isEmptyStringMatrix(matrix)) {
			return error;
		}
		
		if(matrix.length < 2) {
			return error;
		}
		
		if(start != 1) {
			return error;
		}
		
		int i;
		int j;
		int row;
		int col;
		int loc;
		
		row = matrix.length;
		int leftRowNumber = 0;
		int[] leftRow = new int[row];
		
		if(BasicUtil.isEmptyStringArray(deletedType) == false) {
			loc = BasicUtil.findStringLocInArray(TlvTag.TAG_9C, matrix[start]);
			if(loc > -1) {
				
				boolean typeFound;
				for(i = 1+start; i < row; i++) {
					typeFound = BasicUtil.findStringInArray(matrix[i][loc], deletedType);
					if(typeFound == true) {
						leftRow[i] = -1;
					} else {
						leftRowNumber ++;
						leftRow[i] = i;
					}
				}
				
				String[][] matrix1 = new String[2+leftRowNumber][matrix[0].length];
				matrix1[0] = matrix[0];
				matrix1[start] = matrix[start];
				for(i = 1+start, j = 0; i < row; i++) {
					if(leftRow[i] > 0) {
						matrix1[2+j++] = matrix[i];
					}
				}
				
				matrix = matrix1;
			}
		}
		
		row = matrix.length;
		col = matrix[0].length;
		
		if(order == false) {
			String[][] matrix1 = new String[row][col];
			matrix1[0] = matrix[0];
			matrix1[start] = matrix[start];
			for(i = 1+start; i < row; i++) {
				matrix1[i] = matrix[row-1+1+start-i];
			}
			matrix = matrix1;
		}
		
		row = matrix.length;
		col = template.length;
		String[][] adjusted = new String[row][col];
		
		for(i = 0; i < template.length; i++) {
			loc = BasicUtil.findStringLocInArray(template[i], matrix[start]);
			if(loc > -1) {
				for(j = 0; j < row; j++) {
					adjusted[j][i] = matrix[j][loc];
				}
			}
		}
		
		adjusted[0][0] = matrix[0][0];
		return adjusted;
	}
	
	public static String[][] toReadableTableMatrix(String[][] matrix, int start, int length, boolean flag, int headReadId) {
		
		String[][] error = new String[1][1];
		error[0][0] = Sw1Sw2.SW1SW2_FC60;
		
		if(BasicUtil.isEmptyStringMatrix(matrix)) {
			return error;
		}
		
		if(matrix.length < 2) {
			return error;
		}
		
		if(start != 1) {
			return error;
		}
		
		if(length < 0) {
			return error;
		}
		
		if((start+length) >= matrix.length) {
			return error;
		}
		
		if(headReadId < 0) {
			return error;
		}
		
		String[] readable = replaceStringArray(matrix[start], TransDetailSetting.TAG_READABLE, headReadId, flag, true);
		if(readable == null) {
			return error;
		}
		
		int i;
		int j;
		String temp1;
		
		for(i = 0; i < matrix[0].length; i++) {
			
			if(matrix[start][i].equals(TlvTag.TAG_9A)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = toReadableDate(TransDetailSetting.TEMP_READABLE_DATE, matrix[j][i]);
						if(temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if(matrix[start][i].equals(TlvTag.TAG_9C)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = TransType.toReadableTransactionType(matrix[j][i]);
						if(temp1 != null) {
							matrix[j][i] = temp1;
						}
					}
				}
			} else if(matrix[start][i].equals(TlvTag.TAG_5F2A)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = CurrencyCode.toReadableCurrency(matrix[j][i]);
						if(temp1 != null) {
							matrix[j][i] = temp1;
						}
					}
				}
			} else if(matrix[start][i].equals(TlvTag.TAG_9F02)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = toReadableAmount(matrix[j][i]);
						if(temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if(matrix[start][i].equals(TlvTag.TAG_9F03)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = toReadableAmount(matrix[j][i]);
						if(temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if(matrix[start][i].equals(TlvTag.TAG_9F1A)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = CountryCode.toReadableCountry(matrix[j][i]);
						if(temp1 != null) {
							matrix[j][i] = temp1;
						}
					}
				}
			} else if(matrix[start][i].equals(TlvTag.TAG_9F21)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = toReadableTime(TransDetailSetting.TEMP_READABLE_TIME, matrix[j][i]);
						if(temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if(matrix[start][i].equals(TlvTag.TAG_9F36)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						if((matrix[j][i].length() > 0) && (matrix[j][i].length() < 5)) {
							int temp = Integer.valueOf(matrix[j][i], 16);
							temp1 = Integer.toString(temp);
							if(temp1 == null) {
								return error;
							}
							matrix[j][i] = temp1;
						}
					}
				}
			} else if(matrix[start][i].equals(TlvTag.TAG_9F4E)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						byte[] name = BasicUtil.hexStringToBytes(matrix[j][i]);
						if(name == null) {
							return error;
						}
						try {
							int k = 0;
							int n = 0;
							for(k = name.length ; k > 0; k--) {
								if(name[k-1] == 0x00) {
									n++;
								} else {
									break;
								}
							}
							temp1 = new String(name, "GBK");
							matrix[j][i] = temp1.substring(0, temp1.length()-n);				
						} catch(Exception e) {
							return error;
						}
					}
				}
			} else if(matrix[start][i].equals(TransDetailSetting.TAG_PRI_01)) {
				
			} else if(matrix[start][i].equals(TransDetailSetting.TAG_PRI_02)) {
				
			} else if(matrix[start][i].equals(TransDetailSetting.TAG_PRI_03)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = toReadableAmount(matrix[j][i]);
						if(temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			} else if(matrix[start][i].equals(TransDetailSetting.TAG_PRI_04)) {
				for(j = start+1 ; j < start+1+length; j++) {
					if(matrix[j][i] != null) {
						temp1 = toReadableAmount(matrix[j][i]);
						if(temp1 == null) {
							return error;
						}
						matrix[j][i] = temp1;
					}
				}
			}
		}
		
		matrix[start] = readable;
		return matrix;
	}
	
	//convert from a 5 bits value to a radix 32 value,
	//according to Unionpay Integrated Circuit Card Specifications,
	//Part 7 Dynamic Password Specifications
	public static byte toRadix32(short shrInput) {
		
		byte bytOutput = 0;
		shrInput &= 0x001F;
		
		switch(shrInput) {
			case 0:
				bytOutput = 48;
				break;
			case 1:
				bytOutput = 49;
				break;
			case 2:
				bytOutput = 50;
				break;
			case 3:
				bytOutput = 51;
				break;
			case 4:
				bytOutput = 52;
				break;
			case 5:
				bytOutput = 53;
				break;
			case 6:
				bytOutput = 54;
				break;
			case 7:
				bytOutput = 55;
				break;
			case 8:
				bytOutput = 56;
				break;
			case 9:
				bytOutput = 57;
				break;
			case 10:
				bytOutput = 65;
				break;
			case 11:
				bytOutput = 67;
				break;
			case 12:
				bytOutput = 68;
				break;
			case 13:
				bytOutput = 69;
				break;
			case 14:
				bytOutput = 70;
				break;
			case 15:
				bytOutput = 71;
				break;
			case 16:
				bytOutput = 72;
				break;
			case 17:
				bytOutput = 74;
				break;
			case 18:
				bytOutput = 75;
				break;
			case 19:
				bytOutput = 76;
				break;
			case 20:
				bytOutput = 77;
				break;
			case 21:
				bytOutput = 78;
				break;
			case 22:
				bytOutput = 80;
				break;
			case 23:
				bytOutput = 82;
				break;
			case 24:
				bytOutput = 83;
				break;
			case 25:
				bytOutput = 84;
				break;
			case 26:
				bytOutput = 85;
				break;
			case 27:
				bytOutput = 86;
				break;
			case 28:
				bytOutput = 87;
				break;
			case 29:
				bytOutput = 88;
				break;
			case 30:
				bytOutput = 89;
				break;
			case 31:
				bytOutput = 90;
				break;
			default:
				bytOutput = 0;
				break;
		}
		
		return bytOutput;
	}
	
	//obtain the bits from data, typically APDU response, according to a template;
	//if the length of the bits got is not the multiple of 8,
	//then bitLen stores the remainder of this length divided by 8
	public static byte[] getBitsByTemplate(byte[] data, byte[] template, short[] bitLen) {
		
		if(data == null) {
			return null;
		}
		
		if(template == null) {
			return null;
		}
		
		if(bitLen == null) {
			return null;
		}
		
		int lenData = data.length;
		int lenTemplate = template.length;
		
		if(lenTemplate < 1) {
			return null;
		}
		if(lenData < lenTemplate) {
			return null;
		}
		
		int i;
		int j;
		int lenBit = 0;
		int lenByte = 0;
		short temp;
		byte[] tempData = new byte[lenTemplate];
		BasicUtil.initByteArray(tempData, (byte)0x00);
		
		for(i = 0; i < lenTemplate; i++) {
			
			if((short)(template[i]&0xFF) > 0) {													//template[i] is not zero
				
				//check every bit according to template[i]
				for(j = 0; j < 8; j++) {
					
					if((short)((template[i]&BIT_SHIFT_MASK_IN_BYTE[j])&0xFF) > 0x00) {			//this bit of template[i] is 1
						
						//get this bit of data[i], and stores it in tempData[lenByte]
						lenBit++;
						temp = (short)((data[i]&0xFF)&BIT_SHIFT_MASK_IN_BYTE[j]);
						temp = (short)(temp>>(8-1-j));
						temp = (short)(temp<<(8-lenBit));
						temp = (short)(temp&0xFF);
						tempData[lenByte] = (byte)((tempData[lenByte]&0xFF)|temp);
						
						//check another byte
						if(lenBit >= 8) {
							
							lenBit = 0;
							lenByte++;
						}
					}
				}
			}
		}
		
		bitLen[0] = (short)lenBit;																//the remainder of data length divided by 8
		if(lenBit > 0) {
			lenByte++;
		}
		
		return BasicUtil.byteSubString(tempData, 0, lenByte);
	}
	
	//exchange odd bits and even bits of data;
	//bitLen indicates the bit length of last byte, which participate the exchange
	public static byte[] bitOddEvenExchange(byte[] data, short bitLen) {
		
		if(data == null) {
			return null;
		}
		
		int lenData = data.length;
		if(lenData < 1) {
			return null;
		}
		
		//bitLen must be even
		if(bitLen%2 == 1) {
			return null;
		}
		
		if((bitLen < 0) || (bitLen > 8)) {
			return null;
		}
		
		int i;
		int lenDataBk = lenData;
		short odd;
		short even;
		byte[] tempData = new byte[lenData];
		BasicUtil.initByteArray(tempData, (byte)0x00);
		
		//if bitLen is zero, the last byte need not participate the exchange
		if(bitLen == 0) {
			lenDataBk--;
		}
		
		//exchange odd bits and even bits
		for(i = 0; i < lenDataBk; i++) {
			
			odd = (short)(data[i]&0x55);
			even = (short)(data[i]&0xAA);
			
			odd <<= 1;
			odd &= 0xFF;
			
			even >>= 1;
			even &= 0xFF;
			
			tempData[i] = (byte)(odd|even);
		}
		
		//if bitLen is not zero, set other bits in last byte to zero
		if(bitLen > 0) {
			tempData[lenData-1] = (byte)((tempData[lenData-1]&0xFF)&(BIT_GET_MASK_IN_BYTE[bitLen-1]&0xFF));
		}
		
		return tempData;
	}
	
	//convert binary number to decimal number:
	//data is binary number, lenBits is total length of bits in data which needs to convert
	public static short[] arrayBinaryToDecimal(byte[] data, short lenBits) {
		
		if(data == null) {
			return null;
		}
		
		if(lenBits < 1) {
			return null;
		}
		
		int lenData = data.length;
		if(lenData*8 < lenBits) {
			return null;
		}
		
		int i;
		int j;
		int bits;
		int bitV;
		int bitLoc;
		int lenBytes = lenBits/8;
		int lenBitsRemain = lenBits%8;
		short[] shrResult1 = null;
		short[] shrResult2 = null;
		boolean toBeAdd = false;
		
		if(lenBitsRemain != 0) {
			lenBytes++;
		} else {
			lenBitsRemain = 8;
		}
		
		for(i = 0; i < lenBytes; i++) {
			
			//the last byte
			if(i >= (lenBytes-1)) {
				bits = lenBitsRemain;
			}
			else {
				bits = 8;
			}
			
			for(j = 0; j < bits; j++) {
				
				bitV = (data[i]&BIT_SHIFT_MASK_IN_BYTE[j])&0xFF;								//get one bit
				if(bitV > 0) {																	//it greater than zero, need to be added
					
					bitLoc = lenBits-1-i*8-j;													//get bit location
					if(toBeAdd == true) {
						
						shrResult2 = BasicUtil.binBitToDecArray((short)bitLoc);
						if(shrResult2 == null) {
							return null;
						}
						
						shrResult1 = BasicUtil.decAddDec(shrResult1, shrResult2);				//add
						if(shrResult1 == null) {
							return null;
						}
					}
					else {
						toBeAdd = true;
						shrResult1 = BasicUtil.binBitToDecArray((short)bitLoc);
						
						if(shrResult1 == null) {
							return null;
						}
					}
				}
			}
		}
		
		return shrResult1;
	}
	
	//convert from binary data to radix32 data,
	//according to Unionpay Integrated Circuit Card Specifications,
	//Part 7 Dynamic Password Specifications
	public static byte[] arrayBinaryToRadix32(byte[] data, short lenBits) {
		
		if(data == null) {
			return null;
		}
		
		if(lenBits < 1) {
			return null;
		}
		
		int lenData = data.length;
		if(lenBits > lenData*8) {
			return null;
		}
		
		int i;
		int j;
		int k;
		int l1;
		int l2;
		int r;
		short temp;
		short temp1;
		int lenRadix32;
		
		//reconstruct data, in case the first byte needs to be padded with zero
		short[] data1 = new short[lenData+1];
		data1[0] = 0x00;
		for(i = 0; i < lenData; i++) {
			data1[1+i] = data[i];
		}
		
		//initialize the container for radix32 data
		lenRadix32 = lenBits/5;
		i = lenBits%5;
		if(i > 0) {
			lenRadix32++;
		}
		byte[] radix32 = new byte[lenRadix32];
		
		//get the outer loop index
		l1 = lenBits/(5*8);
		l2 = lenBits%(5*8);
		if(l2 > 0) {
			l1++;
		}
		
		//get the inner loop index
		r = l2/5;
		i = l2%5;
		if(i > 0) {
			r++;
		}
		
		for(i = 0, k = lenData; i < l1; i++, k-=5) {
			
			//the last loop
			if(i >= (l1-1)) {
				l2 = r;
			}
			else {
				l2 = 8;
			}
			
			for(j = 0; j < l2; j++) {
				
				//convert to radix32 data
				switch (j) {
					case 0:
						temp = (short)(data1[k]&0x1F);
						radix32[lenRadix32-(8*i+j)-1] = TransUtil.toRadix32(temp);
						break;
					case 1:
						temp = (short)((data1[k]&0xE0)>>5);
						temp1 = (short)((data1[k-1]&0x03)<<3);
						temp |= temp1;
						radix32[lenRadix32-(8*i+j)-1] = TransUtil.toRadix32(temp);
						break;
					case 2:
						temp = (short)((data1[k-1]&0x7C)>>2);
						radix32[lenRadix32-(8*i+j)-1] = TransUtil.toRadix32(temp);
						break;
					case 3:
						temp = (short)((data1[k-1]&0x80)>>7);
						temp1 = (short)((data1[k-2]&0x0F)<<1);
						temp |= temp1;
						radix32[lenRadix32-(8*i+j)-1] = TransUtil.toRadix32(temp);
						break;
					case 4:
						temp = (short)((data1[k-2]&0xF0)>>4);
						temp1 = (short)((data1[k-3]&0x01)<<4);
						temp |= temp1;
						radix32[lenRadix32-(8*i+j)-1] = TransUtil.toRadix32(temp);
						break;
					case 5:
						temp = (short)((data1[k-3]&0x3E)>>1);
						radix32[lenRadix32-(8*i+j)-1] = TransUtil.toRadix32(temp);
						break;
					case 6:
						temp = (short)((data1[k-3]&0xC0)>>6);
						temp1 = (short)((data1[k-4]&0x07)<<2);
						temp |= temp1;
						radix32[lenRadix32-(8*i+j)-1] = TransUtil.toRadix32(temp);
						break;
					case 7:
						temp = (short)((data1[k-4]&0xF8)>>3);
						radix32[lenRadix32-(8*i+j)-1] = TransUtil.toRadix32(temp);
						break;
				}
			}
		}
		
		return radix32;
	}
	
	public static byte[] encryptSha(String info) throws NoSuchAlgorithmException {
		
		if(info == null) {
			return null;
		} else if(info.length() < 1) {
			return null;
		}
		
		MessageDigest md5 = MessageDigest.getInstance("SHA");
		byte[] srcBytes = info.getBytes();
		
		md5.update(srcBytes);
		
		byte[] resultBytes = md5.digest();
		return resultBytes;
	}
	
	public static byte[] encryptSha(byte[] info) throws NoSuchAlgorithmException {
		
		if(info == null) {
			return null;
		} else if(info.length < 1) {
			return null;
		}
		
		MessageDigest md5 = MessageDigest.getInstance("SHA");//MessageDigest.getInstance("SHA-1");
		md5.update(info);
		
		byte[] resultBytes = md5.digest();
		return resultBytes;
	}
	
	public static String tefToChallenge(String tef) {
		
		if(tef == null) {
			return Sw1Sw2.SW1SW2_FD10;
		} else if(tef.length() < 1) {
			return Sw1Sw2.SW1SW2_FD10;
		}
		
		byte[] bytShaResult = null;
		try {
			bytShaResult = encryptSha(tef);
		} catch(Exception e) {
			return Sw1Sw2.SW1SW2_FD91;
		}
		
		if(bytShaResult == null) {
			return Sw1Sw2.SW1SW2_FD91;
		} else if(bytShaResult.length < 1) {
			return Sw1Sw2.SW1SW2_FD91;
		}
		
		final StringBuilder hex = new StringBuilder(2 * bytShaResult.length);
		for (final byte b : bytShaResult) {
			hex.append(HEX.charAt((b & 0xF0) >> 4)).append(
					HEX.charAt((b & 0x0F)));
		}
		
		String temp = hex.toString();
		if(temp.length() < 10) {
			return Sw1Sw2.SW1SW2_FC61;
		}
		
		String challange = temp.substring(2, 10);
		return Sw1Sw2.SW1SW2_OK + challange;
	}
	
	//mode: true, show original string except the digits indicated by start and length which are marked by 'mark';
	//		false, show original string marked by start and length, other digits are marked by 'mark'
	//order: true, count from left; false, count from right
	//seperator: true, there is seperator mark1, every length1
	//			 false, no seperator
	//order1: true, seperate from left; false, seperate from right
	public static String toReadableString(String data,
			boolean mode, boolean order, int start, int length, byte mark,
			boolean seperator, boolean order1, int length1, byte mark1) {
		
		if(data == null) {
			return Sw1Sw2.SW1SW2_FC60;
		}
		
		int lenData = data.length();
		byte[] bytData = null;
		byte[] bytData1 = null;
		
		if(lenData > 0) {
			bytData = data.getBytes();
			bytData1 = new byte[bytData.length];
			BasicUtil.initByteArray(bytData1, mark);
		}
		
		if((start > -1) && (length > 0) && (start < lenData)) {
			if(mode == true) {
				if(order == true) {
					for(int i = start; i < start + length; i++) {
						if(i >= lenData) {
							break;
						}
						bytData[i] = mark;
					}
					data = new String(bytData);
				} else {
					for(int i = start; i < start + length; i++) {
						if(i >= lenData) {
							break;
						}
						bytData[lenData-1-i] = mark;
					}
					data = new String(bytData);
				}
			} else {
				if(order == true) {
					for(int i = start; i < start + length; i++) {
						if(i >= lenData) {
							break;
						}
						bytData1[i] = bytData[i];
					}
					data = new String(bytData1);
				} else {
					for(int i = start; i < start + length; i++) {
						if(i >= lenData) {
							break;
						}
						bytData1[lenData-1-i] = bytData[lenData-1-i];
					}
					data = new String(bytData1);
				}
			}
		}
		
		if(seperator == true) {
			if(length1 > 0) {
				int number = lenData/length1;
				number += (lenData%length1>0)?1:0;
				if(number > 1) {
					bytData = data.getBytes();
					bytData1 = new byte[bytData.length+number-1];
					if(order1 == true) {
						for(int i = 0, j = 0; i < lenData; i++) {
							bytData1[i+j] = bytData[i];
							if((i < lenData-1) && (i%length1 == (length1-1))) {
								j++;
								bytData1[i+j] = mark1;
							}
						}
					} else {
						for(int i = 0, j = 0; i < lenData; i++) {
							bytData1[number-1+lenData-i-1-j] = bytData[lenData-i-1];
							if((i < lenData-1) && (i%length1 == (length1-1))) {
								j++;
								bytData1[number-1+lenData-i-1-j] = mark1;
							}
						}
					}
					data = new String(bytData1);
				}
			}
		}
		
		return Sw1Sw2.SW1SW2_OK + data;
	}
	
	//order: true, ascending; false, descending
	public static byte[] sortByteString(byte[] bytes, int offset, int group, int item, boolean order) {
		
		if(offset < 0) {
			return bytes;
		}
		
		if(group < 1) {
			return bytes;
		}
		
		if((item < 0) || (item > (group-1))) {
			return bytes;
		}
		
		if(bytes == null) {
			return null;
		} else if(bytes.length <= (offset+group)) {
			return bytes;
		}
		
		int i;
		int j;
		int len = bytes.length-offset;
		
		i = len%group;
		if(i != 0) {
			return bytes;
		}
		
		int byte1;
		int byte2;
		byte tempBuff[] = new byte[group];
		
		for(i = group; i < len; i+=group) {
			for(j = group; j < len+group-i; j+=group) {
				
				byte1 = bytes[offset+j-group+item]&0x00FF;
				byte2 = bytes[offset+j+item]&0x00FF;			
				
				if(order == true) {
					if(byte1 > byte2) {
						BasicUtil.byteStringCopy(bytes, offset+j, tempBuff, 0, group);
						BasicUtil.byteStringCopy(bytes, offset+j-group, bytes, offset+j, group);
						BasicUtil.byteStringCopy(tempBuff, 0, bytes, offset+j-group, group);
					}
				} else {
					if(byte1 < byte2) {
						BasicUtil.byteStringCopy(bytes, offset+j, tempBuff, 0, group);
						BasicUtil.byteStringCopy(bytes, offset+j-group, bytes, offset+j, group);
						BasicUtil.byteStringCopy(tempBuff, 0, bytes, offset+j-group, group);
					}
				}
			}
		}
		
		return bytes;
	}
	
	//total: max length
	//decimal: max length of after decimal point
	public static String checkInputAmount(String amount, int total, int decimal, boolean noneZero) {
		
		if(total < 1) {
	    	return Sw1Sw2.SW1SW2_FC60;
		}
		
		if(decimal < 0) {
	    	return Sw1Sw2.SW1SW2_FC60;
		}
		
		if(BasicUtil.isEmptyString(amount) == true ) {
	    	return Sw1Sw2.SW1SW2_FD01;
		}
		
		boolean isDigit = true;
	    String amountOk = null;
	    
	    byte[] tempAmount = amount.getBytes();
	    int dotNumber = 0;
	    int dotLoc = -1;
	    
	    for (int i = 0; i < tempAmount.length; i++) {
	    	if ((tempAmount[i] <= 47) || (tempAmount[i] >= 58)) {
	    		if (tempAmount[i] == 46) {
	    			dotLoc = i;
	    			dotNumber++;
	    		} else {
	    			isDigit = false;
	    			break;
	    		}
	    	}
	    }
	    
	    if (!isDigit) {
	    	return Sw1Sw2.SW1SW2_FD02;
	    }
	    
	    if (dotNumber > 1) {
	    	return Sw1Sw2.SW1SW2_FD03;
	    }
	    
	    if (dotLoc > -1)
	    {
	    	int right = BasicUtil.getStringRightZeroNumber(amount);
	    	
	    	if ((dotLoc + 1 + decimal) < (amount.length()-right)) {
	    		return Sw1Sw2.SW1SW2_FD04;
	    	}
	    	
	    	amount = amount.substring(0, amount.length()-right);
	    	
	    	String tempString1 = amount.substring(0, dotLoc);
	    	String tempString2 = amount.substring(dotLoc + 1, amount.length());
	    	amountOk = tempString1.concat(tempString2);
	    	
	    	if ((dotLoc + 1 + decimal) > amount.length())
	    	{
	    		int lenPadding = dotLoc + 1 + decimal - amount.length();
	    		tempString1 = "000000000000000000000000".substring(0, lenPadding);
	    		amountOk = amountOk.concat(tempString1);
	    	}
	    } else {
	    	amountOk = amount+"00";
	    }
	    
    	amountOk = BasicUtil.trimStringLeftZero(amountOk);
    	if(amountOk == null) {
    		if(noneZero) {
	    		return Sw1Sw2.SW1SW2_FD06;
    		} else {
    			amountOk = "";
    		}
    	}
	    
    	if (amountOk.length() > (total)) {
    		return Sw1Sw2.SW1SW2_FD05;
    	}
	    
	    int lenAmount = amountOk.length();
	    if (lenAmount < (total))
	    {
	    	String tempString1 = "000000000000000000000000";
	    	String tempString2 = tempString1.substring(0, total - lenAmount);
	    	amountOk = tempString2.concat(amountOk);
	    }
	    
	    return Sw1Sw2.SW1SW2_OK + amountOk;
	}
	
	//decimal:  max length of after decimal point
	public static String checkInputAmount(String amount, int decimal, boolean noneZero) {
		
		if(decimal < 0) {
	    	return Sw1Sw2.SW1SW2_FC60;
		}
		
		if(BasicUtil.isEmptyString(amount) == true ) {
	    	return Sw1Sw2.SW1SW2_FD01;
		}
		
		boolean isDigit = true;
	    String amountOk = null;
	    
	    byte[] tempAmount = amount.getBytes();
	    int dotNumber = 0;
	    int dotLoc = -1;

	    for (int i = 0; i < tempAmount.length; i++) {
	    	if ((tempAmount[i] <= 47) || (tempAmount[i] >= 58)) {
	    		if (tempAmount[i] == 46) {
	    			dotLoc = i;
	    			dotNumber++;
	    		} else {
	    			isDigit = false;
	    			break;
	    		}
	    	}
	    }
	    
	    if (!isDigit) {
	    	return Sw1Sw2.SW1SW2_FD02;
	    }
	    
	    if (dotNumber > 1) {
	    	return Sw1Sw2.SW1SW2_FD03;
	    }
	    
	    if (dotLoc > -1)
	    {
	    	int right = BasicUtil.getStringRightZeroNumber(amount);
	    	
	    	if ((dotLoc + 1 + decimal) < (amount.length()-right)) {
	    		return Sw1Sw2.SW1SW2_FD04;
	    	}
	    	
	    	amount = amount.substring(0, amount.length()-right);
	    	
	    	String tempString1 = amount.substring(0, dotLoc);
	    	String tempString2 = amount.substring(dotLoc + 1, amount.length());
	    	amountOk = tempString1.concat(tempString2);
	    	
	    	if ((dotLoc + 1 + decimal) > amount.length())
	    	{
	    		int lenPadding = dotLoc + 1 + decimal - amount.length();
	    		tempString1 = "000000000000000000000000".substring(0, lenPadding);
	    		amountOk = amountOk.concat(tempString1);
	    	}
	    } else {
	    	amountOk = amount+"00";
	    }
	    
    	amountOk = BasicUtil.trimStringLeftZero(amountOk);
    	if(amountOk == null) {
    		if(noneZero) {
	    		return Sw1Sw2.SW1SW2_FD01;
    		} else {
    			amountOk = "";
    		}
    	}
	    
	    return Sw1Sw2.SW1SW2_OK + amountOk;
	}
	
	public static String checkInputChallenge(String challenge, int lenExpected) {
		
		if(challenge == null) {
			return Sw1Sw2.SW1SW2_FD07;
		} else if(challenge.length() != lenExpected) {
			return Sw1Sw2.SW1SW2_FD08;
		} else {
			if(BasicUtil.isHexString(challenge) == false) {
				return Sw1Sw2.SW1SW2_FD09;
			} else {
				return Sw1Sw2.SW1SW2_OK;
			}
		}
	}
	
	//flag: true, should be all digits
	public static String checkInputAccount(String account, int minLen, int maxLen, boolean flag) {
		
		if(minLen < 1) {
	    	return Sw1Sw2.SW1SW2_FC60;
		}
		
		if(maxLen < minLen) {
	    	return Sw1Sw2.SW1SW2_FC60;
		}
		
		if(BasicUtil.isEmptyString(account) == true ) {
	    	return Sw1Sw2.SW1SW2_FD12;
		}
		
	    int len = account.length();
	    if((len < minLen) || (len > maxLen)) {
	    	return Sw1Sw2.SW1SW2_FD14;
	    }
	    
		if(flag == true) {
			
			boolean isDigit = true;
		    byte[] tempAccount = account.getBytes();
		    
		    for (int i = 0; i < tempAccount.length; i++) {
		    	if ((tempAccount[i] <= 47) || (tempAccount[i] >= 58)) {
		    		isDigit = false;
		    		break;
		    	}
		    }
		    
		    if (!isDigit) {
		    	return Sw1Sw2.SW1SW2_FD13;
		    }
		}
	    
	    return Sw1Sw2.SW1SW2_OK;
	}
}
