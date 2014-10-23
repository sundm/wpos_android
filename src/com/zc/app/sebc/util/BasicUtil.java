package com.zc.app.sebc.util;

public final class BasicUtil {

	public final static char[] HEX = {
			'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private BasicUtil() {
		
	}
	
	//reverse a boolean value
	public static boolean boolReverse(boolean bool) {
		
		if(bool == true) {
			return false;
		} else {
			return true;
		}
	}
	
	public static byte[] intToBytes(int a) {
		return new byte[] { 
				(byte) (0x000000ff & (a >>> 24)),
				(byte) (0x000000ff & (a >>> 16)),
				(byte) (0x000000ff & (a >>> 8)), 
				(byte) (0x000000ff & (a))
				};
	}
	
	public static byte[] longToBytes(long a) {
		return new byte[] {
				(byte) (0x000000ff & (a >>> 24)),
				(byte) (0x000000ff & (a >>> 16)),
				(byte) (0x000000ff & (a >>> 8)),
				(byte) (0x000000ff & (a))
				};
	}
	
	public static int bytesToInt(byte[] bytes, int start, int len) {
		int ret = 0;
		final int end = start + len;
		
		for (int i = start; i < end; ++i) {
			ret <<= 8;
			ret |= bytes[i] & 0xFF;
		}
		return ret;
	}
	
	public static long bytesToLong(byte[] bytes, int start, int len) {
		long ret = 0;
		final int end = start + len;
		
		for (int i = start; i < end; ++i) {
			ret <<= 8;
			ret |= bytes[i] & 0xFF;
		}
		return ret;
	}
	
	public static String bytesToHexString(byte[] bytes, int start, int len) {
		
		if(bytes == null) {
			return null;
		}
		
		if(start < 0) {
			return null;
		}
		
		if(len < 1) {
			return null;
		}
		
		int length = bytes.length;
		if(length < (start+len)) {
			return null;
		}
		
		final char[] ret = new char[len*2];
		final int end = start + len;
		int j = 0;
		
		for (int i = start; i < end; ++i) {
			
			final byte v = bytes[i];
			ret[j++] = HEX[0x0F & (v >> 4)];
			ret[j++] = HEX[0x0F & v];
		}
		
		return new String(ret);
	}
	
	public static byte[] hexStringToBytes(String str) {
		
		if(str == null) {
			return null;
		}
		
		int len = str.length();
		if(len < 1) {
			return null;
		}
		
		if(len%2 != 0) {
			return null;
		}
		
		int n = len/2;
		byte byt;
		byte byt1;
		int temp;
		byte[] result = new byte[n];
		byte[] source = str.getBytes();
		
		for (int i = 0; i < len; i+=2) {
			
			byt = source[i];			
			if((byt >= 48) && (byt <= 57)) {
				byt -= 48;
			} else if((byt >= 65) && (byt <= 70)) {
				byt -= 55;
			} else if((byt >= 97) && (byt <= 102)) {
				byt -= 87;
			} else {
				return null;
			}

			byt1 = source[i+1];
			if((byt1 >= 48) && (byt1 <= 57)) {
				byt1 -= 48;
			} else if((byt1 >= 65) && (byt1 <= 70)) {
				byt1 -= 55;
			} else if((byt1 >= 97) && (byt1 <= 102)) {
				byt1 -= 87;
			} else {
				return null;
			}
			
			temp = ((byt<<4)+byt1)&0xFF;
			result[i/2] = (byte)temp;
		}
		
		return result;
	}
	
	//byte string, ASCII to BCD
	public static byte[] byteAsciiToByteBcd(byte[] bytAscii1, boolean adjust, boolean leftOrRight) {
		
		if(bytAscii1 == null) {
			return null;
		}
		
		int i;
		int j;
		int lenAscii = bytAscii1.length;
		byte [] bytAscii;
		
		if(lenAscii%2 == 1) {
			if(adjust == true) {
				bytAscii = new byte[lenAscii+1];
				if(leftOrRight == false) {
					for(i = 0; i < lenAscii; i++) {
						bytAscii[1+i] = bytAscii1[i];
					}
					bytAscii[0] = (byte)0x30;
				} else {
					for(i = 0; i < lenAscii; i++) {
						bytAscii[i] = bytAscii1[i];
					}
					bytAscii[i] = (byte)0x30;
				}
			} else {
				return null;
			}
		} else {
			bytAscii = bytAscii1;
		}
		lenAscii = bytAscii.length;
		
		byte bytTemp1;
		byte bytTemp2;
		byte[] bytBcd = new byte[lenAscii/2];
		
		//convert ASCII to BCD
		for(i = 0, j = 0; i < lenAscii; i += 2, j++) {
			
			if((bytAscii[i] > 47) && (bytAscii[i] < 58)) {
				bytTemp1 = (byte)(bytAscii[i]-(byte)0x30);
			} else if((bytAscii[i] > 64) && (bytAscii[i] < 71)) {
				bytTemp1 = (byte)(bytAscii[i]-(byte)65+(byte)10);
			} else if((bytAscii[i] > 96) && (bytAscii[i] < 103)) {
				bytTemp1 = (byte)(bytAscii[i]-(byte)97+(byte)10);
			} else {
				return null;
			}
			
			if((bytAscii[i+1] > 47) && (bytAscii[i+1] < 58)) {
				bytTemp2 = (byte)(bytAscii[i+1]-(byte)0x30);
			} else if((bytAscii[i+1] > 64) && (bytAscii[i+1] < 71)) {
				bytTemp2 = (byte)(bytAscii[i+1]-(byte)65+(byte)10);
			} else if((bytAscii[i+1] > 96) && (bytAscii[i+1]< 103 )) {
				bytTemp2 = (byte)(bytAscii[i+1]-(byte)97+(byte)10);
			} else {
				return null;
			}
			
			bytBcd[j] = (byte)((((bytTemp1&0xFF)<<4)&0xF0)+(bytTemp2&0x0F));
		}
		
		return bytBcd;
	}
	
	public static boolean isEmptyString(String str) {
		if(str == null) {
			return true;
		} else if(str.length() < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isEmptyStringArray(String[] array) {
		if(array == null) {
			return true;
		} else if(array.length < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isEmptyStringMatrix(String[][] matrix) {
		if(matrix == null) {
			return true;
		} else if(matrix.length < 1) {
			return true;
		} else if(matrix[0].length < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean findStringInArray(String string, String[] array) {
		
		if(isEmptyString(string) == true) {
			return false;
		}
		
		if(isEmptyStringArray(array) == true) {
			return false;
		}
		
		for(int i = 0; i < array.length; i ++) {
			if(string.equals(array[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public static int findStringLocInArray(String string, String[] array) {
		
		if(isEmptyString(string) == true) {
			return -1;
		}
		
		if(isEmptyStringArray(array) == true) {
			return -1;
		}
		
		for(int i = 0; i < array.length; i ++) {
			if(string.equals(array[i])) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static boolean isDecimalString(String str) {
		
		if(str == null) {
			return false;
		}
		
		for(int i = 0; i < str.length(); i++) {
			
			char chrTemp = str.charAt(i);	
			if((chrTemp < 48) || (chrTemp > 57)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isHexString(String str) {
		
		if(str == null) {
			return false;
		}
		
		for(int i = 0; i < str.length(); i++) {
			
			char chrTemp = str.charAt(i);
			if((chrTemp > 47) && (chrTemp < 58)) {
				
			} else if((chrTemp > 64) && (chrTemp < 71)) {
				
			} else if((chrTemp > 96) && (chrTemp < 103)) {
				
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean initByteArray(byte[] bytArray, byte initValue) {
		
		if(bytArray == null) {
			return false;
		}
		
		for(int i = 0; i < bytArray.length; i++) {
			bytArray[i] = initValue;
		}
		
		return true;
	}
	
	public static boolean isByteArraySameValue(byte[] bytArray, byte value) {
		
		if(bytArray == null) {
			return false;
		}
		
		for(int i = 0; i < bytArray.length; i++) {
			if(bytArray[i] != value) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean initShortArray(short[] shrArray, short initValue) {
		
		if(shrArray == null) {
			return false;
		}
		
		for(int i = 0; i < shrArray.length; i++) {
			shrArray[i] = initValue;
		}
		
		return true;
	}
	
	public static short[] bcdByteStringToShortString(byte[] bcd) {
		
		if(bcd == null) {
			return null;
		}
		
		int i;
		int len = bcd.length;
		
		if(len < 1) {
			return null;
		}
		short[] shr = new short[len*2];
		
		for(i = 0; i < len; i++) {
			shr[2*i] = (short)((bcd[i]&0xF0)>>4);
			shr[2*i+1] = (short)(bcd[i]&0x0F);
		}
		
		return shr;
	}
	
	public static byte[] shortStringToBcdByteString(short[] shr) {
		
		if(shr == null) {
			return null;
		}
		
		int len = shr.length;
		if(len < 1) {
			return null;
		}
		
		int i;
		int temp1;
		int temp2;
		short[] shr1;
		
		if(len%2 != 0) {
			shr1 = new short[len+1];
			for(i = 0; i < len; i++) {
				shr1[i+1] = shr[i];
			}
			shr1[0] = 0;
			len ++;
		} else {
			shr1 = shr;
		}
		
		byte[] bcd = new byte[len/2];
		
		for(i = 0; i < len/2; i++) {
			
			temp1 = shr1[2*i];
			temp2 = shr1[2*i+1];
			
			if((temp1 < 0x00) || (temp1 > 0x0F)) {
				return null;
			}
			
			if((temp2 < 0x00) || (temp2 > 0x0F)) {
				return null;
			}
			
			bcd[i] = (byte)(((temp1<<4)&0xF0) + temp2);
		}
		
		return bcd;
	}
	
	public static byte[] shortToByteDec(short[] data, short start, short length) {
		
		if(data == null) {
			return null;
		}
		
		if(start < 0) {
			return null;
		}
		
		if(length < 1) {
			return null;
		}
		
		int len = data.length;
		if(len < start+length) {
			return null;
		}
		
		int i;
		short temp;
		byte[] tempdata = new byte[length];
		
		for(i = 0; i < length; i++) {
			
			temp = data[start+i];
			if((temp < 0) || (temp > 9)) {
				return null;
			}
			
			tempdata[i] = (byte)((temp+48)&0xFF);
		}
		
		return tempdata;
	}
	
	public static short calculateXor(short[] shrData) {
		short shrResult;
		int i;
		
		if(shrData == null) {
			return -1;
		}
		
		if(shrData.length > 0) {
			
			shrResult = shrData[0];
			for(i = 1; i < shrData.length; i++) {
				shrResult ^= shrData[i];
			}
		} else {
			shrResult = -1;
		}
		
		return shrResult;
	}
	
	public static boolean isDigitOrAlphabet(String data) {
		
		if(data == null) {
			return false;
		}
		
		int len = data.length();
		if(len < 1) {
			return false;
		}
		
		byte[] bytData = data.getBytes();
		
		for(int i = 0; i < len; i++) {
			if((bytData[i] >= 48) && (bytData[i] <= 57)) {
				
			} else if((bytData[i] >= 65) && (bytData[i] <= 90)) {
				
			} else if((bytData[i] >= 97) && (bytData[i] <= 122)) {
				
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isByteInByteString(byte[] byteString, byte bytToBeFind) {
		
		if(byteString == null) {
			return false;
		}
		
		int len = byteString.length;
		if(len < 1) {
			return false;
		}
		
		for(int i = 0; i < len; i++) {
			if(bytToBeFind == byteString[i]) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean byteStringCopy(byte[] byteString1, int start1, byte[] byteString2, int start2, int length) {
		
		if(byteString1 == null) {
			return false;
		}
		
		if(byteString2 == null) {
			return false;
		}
		
		if(start1 < 0) {
			return false;
		}
		
		if(start2 < 0) {
			return false;
		}
		
		if(length < 0) {
			return false;
		}
		
		if((start1+length) > byteString1.length) {
			return false;
		}
		
		if((start2+length) > byteString2.length) {
			return false;
		}
		
		for(int i = 0; i < length; i++) {
			byteString2[start2+i] = byteString1[start1+i];
		}
		
		return true;
	}
	
	public static byte[] byteSubString(byte[] bytData, int start, int length) {
		
		if(bytData == null) {
			return null;
		}
		
		if(start < 0) {
			return null;
		}
		
		if(length < 1) {
			return null;
		}
		
		int len = bytData.length;
		if(len < (start+length)) {
			return null;
		}
		
		int i;
		byte[] tempData = new byte[length];
		
		for(i = 0; i < length; i++) {
			tempData[i] = bytData[start+i];
		}
		
		return tempData;
	}
	
	public static short[] shortSubString(short[] shrData, int start, int length) {
		
		if(shrData == null) {
			return null;
		}
		
		if(start < 0) {
			return null;
		}
		
		if(length < 1) {
			return null;
		}
		
		int len = shrData.length;
		if(len < (start+length)) {
			return null;
		}
		
		int i;
		short[] tempData = new short[length];
		
		for(i = 0; i < length; i++) {
			tempData[i] = shrData[start+i];
		}
		
		return tempData;
	}
	
	public static String trimStringLeftZero(String str) {
		
		if(str == null) {
			return null;
		} else if(str.length() < 1) {
			return null;
		}
		
		int i;
		int len = str.length();
		char temp;
		
		for(i = 0; i < len; i++) {
			temp = str.charAt(i);
			if(temp != '0') {
				break;
			}
		}
		
		if(i < len) {
			return str.substring(i, len);
		} else {
			return null;
		}
	}
	
	public static String trimStringRightZero(String str) {
		
		if(str == null) {
			return null;
		} else if(str.length() < 1) {
			return null;
		}
		
		int i;
		int len = str.length();
		char temp;
		
		for(i = len; i > 0; i--) {
			temp = str.charAt(i-1);
			if(temp != '0') {
				break;
			}
		}
		
		if(i > 0) {
			return str.substring(0, i);
		} else {
			return null;
		}
	}
	
	public static String trimStringRight(String str, char chr) {
		
		if(str == null) {
			return null;
		} else if(str.length() < 1) {
			return null;
		}
		
		int i;
		int len = str.length();
		char temp;
		
		for(i = len; i > 0; i--) {
			temp = str.charAt(i-1);
			if(temp != chr) {
				break;
			}
		}
		
		if(i > 0) {
			return str.substring(0, i);
		} else {
			return null;
		}
	}
	
	public static int getStringRightZeroNumber(String str) {
		
		if(str == null) {
			return -1;
		} else if(str.length() < 1) {
			return -1;
		}
		
		int i;
		int len = str.length();
		char temp;
		
		for(i = len; i > 0; i--) {
			temp = str.charAt(i-1);
			if(temp != '0') {
				break;
			}
		}
		
		return len-i;
	}
	
	public static short[] shortArrayLeftAppend(short[] array, int length, short value) {
		
		if(array == null) {
			return null;
		}
		
		int len = array.length;
		if(length < len) {
			return null;
		} else if(length == len) {
			return array;
		}
		
		int i;
		int start = length-len;
		short[] tempArray = new short[length];
		
		for(i = 0; i < length; i++) {
			
			if(i < start) {
				tempArray[i] = value;
			} else {
				tempArray[i] = array[i-start];
			}
		}
		
		return tempArray;
	}
	
	public static byte[] byteStringRightMove(byte[] data, int bits) {
		
		if(data == null) {
			return null;
		}
		
		int len = data.length;
		if(len < 1) {
			return null;
		}
		
		if((bits < 0) || (bits > 7)) {
			return null;
		} else if(bits == 0) {
			return data;
		}
		
		short shrTemp1;
		short shrTemp2;
		
		for(int i = 0; i < len; i++) {
			shrTemp1 = (short)(((data[len-1-i]&0xFF)>>bits)&0xFF);
			if(i < (len-1)) {
				shrTemp2 = (short)(((data[len-2-i]&0xFF)<<(8-bits))&0xFF);
			} else {
				shrTemp2 = 0;
			}
			shrTemp1 |= shrTemp2;
			shrTemp1 &= 0xFF;
			data[len-1-i] = (byte)shrTemp1;
		}
		
		return data;
	}
	
	public static int byteStringCompare(byte[] bytes1, int start1, int length1,
			byte[] bytes2, int start2, int length2) {
		
		if(bytes1 == null) {
			return -2;
		}
		
		if(bytes2 == null) {
			return -2;
		}
		
		if(start1 < 0) {
			return -2;
		}
		
		if(start2 < 0) {
			return -2;
		}
		
		if(length1 < 1) {
			return -2;
		}
		
		if(length2 != length1) {
			return -2;
		}
		
		int len1 = bytes1.length;
		int len2 = bytes2.length;
		
		if((start1+length1) > len1) {
			return -2;
		}
		
		if((start2+length2) > len2) {
			return -2;
		}
		
		int i;
		for(i = 0; i < length1; i++) {
			if((bytes1[start1+i]&0xFF) > (bytes2[start2+i]&0xFF)) {
				return 1;
			}
			if((bytes1[start1+i]&0xFF) < (bytes2[start2+i]&0xFF)) {
				return -1;
			}
		}
		
		return 0;
	}
	
	public static int isBcdString1GreaterThanBcdString2(byte[] bcd1, byte[] bcd2) {
		
		int result = -2;
		
		if(bcd1 == null) {
			return result;
		}
		
		if(bcd2 == null) {
			return result;
		}
		
		int i;
		int len;
		int gap;
		int tag;
		int len1 = bcd1.length;
		int len2 = bcd2.length;
		byte[] longer;
		byte[] shorter;
		
		if(len1 < len2) {
			tag = 2;
			len = len2;
			gap = len2-len1;
			longer = bcd2;
			shorter = bcd1;
		} else {
			tag = 1;
			len = len1;
			gap = len1-len2;
			longer = bcd1;
			shorter = bcd2;
		}
		
		result = 0;
		for(i = 0; i < gap; i++) {
			if((longer[i]&0xFF) > 0) {
				result = 1;
				break;
			}
		}
		
		if(i == gap) {
			for(i = gap; i < len; i++) {
				if((longer[i]&0xFF) > (shorter[i-gap]&0xFF)) {
					result = 1;
					break;
				}
				if((longer[i]&0xFF) < (shorter[i-gap]&0xFF)) {
					result = -1;
					break;
				}
			}
		}
		
		if(result == 0) {
			return result;
		} else {
			if(tag == 1) {
				return result;
			} else {
				return result*(-1);
			}
		}
	}
	
	//must assure decimal1 is longer or at least with equal length to decimal2
	public static short[] decAddDec1Big2Small(short[] decimal1, short[] decimal2) {
		
		if(decimal1 == null) {
			return decimal2;
		}
		
		if(decimal2 == null) {
			return decimal1;
		}
		
		int Length1 = decimal1.length;
		int Length2 = decimal2.length;
		
		int i;
		int Carry;
		int temp;
		int temp1;
		int temp2;
		
		short[] result = null;
		short[] result1 = new short[Length1];
		
		Carry = 0;
		for(i = 0; i < Length1; i++) {
			
			temp1 = decimal1[Length1-1-i];
			if(i < Length2) {
				temp2 = decimal2[Length2-1-i];
			} else {
				temp2 = 0;
			}
			
			if((temp1 > 9) || (temp2 > 9)) {
				return null;
			}
			
			if((temp1 < 0) || (temp2 < 0)) {
				return null;
			}
			
			temp = temp1+temp2+Carry;
			if(temp > 9) {
				temp -= 10;
				Carry = 1;
			} else {
				Carry = 0;
			}
			
			result1[Length1-1-i] = (short)temp;
			if(i == Length1-1) {			
				if(Carry != 0) {
					
					result = new short[Length1+1];
					for(int j = 0; j < Length1; j++) {
						result[j+1] = result1[j];
					}
					result[0] = (short)Carry;
					
					return result;
				} else {
					return result1;
				}
			}
		}
		
		return null;
	}
	
	public static short[] decAddDec(short[] decimal1, short[] decimal2) {
		
		if(decimal1 == null) {
			return decimal2;
		}
		
		if(decimal2 == null) {
			return decimal1;
		}
		
		if(decimal1.length >= decimal2.length) {
			return decAddDec1Big2Small(decimal1, decimal2);
		} else {
			return decAddDec1Big2Small(decimal2, decimal1);
		}
	}
	
	public static String getDecimalRandom(int length) {
		
		if(length < 1) {
			return null;
		}
		
		int i;
		String temp;
		String random = "";
		
		for(i = 0; i < length; i++) {
			temp = String.valueOf((int)(Math.random()*10));
			random += temp;
		}
		
		return random;
	}
	
	public static int timesCharInString(String strData, char cha) {
		
		if(strData == null) {
			return -1;
		} else if(strData.length() < 1) {
			return -1;
		}
		
		int length = strData.length();
		int j = 0;
		
		for(int i = 0; i < length; i++) {
			if(strData.charAt(i) == cha) {
				j++;
			}
		}
		
		return j;
	}
	
	public static int locByteInByteString(byte[] byteString, byte bytToBeFind) {
		
		if(byteString == null) {
			return -1;
		}
		
		int len = byteString.length;
		if(len < 1) {
			return -1;
		}
		
		for(int i = 0; i < len; i++) {
			if(bytToBeFind == byteString[i]) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static String getSubStringBySeparator(String str, byte sep, int times) {
		
		if(str == null) {
			return null;
		} else if(str.length() < 1) {
			return null;
		}
		
		if(sep == (byte)0) {
			return null;
		}
		
		if(times < 1) {
			return null;
		}
		
		byte[] byt = str.getBytes();
		if(byt == null) {
			return null;
		}
		
		int len = byt.length;
		if(len < 1) {
			return null;
		}
		
		int i;
		int j;
		int k;
		byte[] bytResult = new byte[len];
		
		for(i = 0, j = 0, k = 0; i < len; i++) {
			
			if(byt[i] == sep) {
				j++;
				continue;
			}
			
			if(j == times) {
				bytResult[k++] = byt[i];
			}
		}
		
		if(k > 0) {
			String result = new String(bytResult, 0, k);
			return result;
		} else {
			return null;
		}
	}
	
	//convert a binary value to decimal value
	public static short[] binBitToDecArray(short bitLoc) {
		
		if(bitLoc < 0) {
			return null;
		}
		
		if(bitLoc > 63) {
			return null;
		}
		
		//binary value lists
		short[] decimal00 = {1};
		short[] decimal01 = {2};
		short[] decimal02 = {4};
		short[] decimal03 = {8};
		short[] decimal04 = {1, 6};
		short[] decimal05 = {3, 2};
		short[] decimal06 = {6, 4};
		short[] decimal07 = {1, 2, 8};
		short[] decimal08 = {2, 5, 6};
		short[] decimal09 = {5, 1, 2};
		short[] decimal10 = {1, 0, 2, 4};
		short[] decimal11 = {2, 0, 4, 8};
		short[] decimal12 = {4, 0, 9, 6};
		short[] decimal13 = {8, 1, 9, 2};
		short[] decimal14 = {1, 6, 3, 8, 4};
		short[] decimal15 = {3, 2, 7, 6, 8};
		short[] decimal16 = {6, 5, 5, 3, 6};
		short[] decimal17 = {1, 3, 1, 0, 7, 2};
		short[] decimal18 = {2, 6, 2, 1, 4, 4};
		short[] decimal19 = {5, 2, 4, 2, 8, 8};
		short[] decimal20 = {1, 0, 4, 8, 5, 7, 6};
		short[] decimal21 = {2, 0, 9, 7, 1, 5, 2};
		short[] decimal22 = {4, 1, 9, 4, 3, 0, 4};
		short[] decimal23 = {8, 3, 8, 8, 6, 0, 8};
		short[] decimal24 = {1, 6, 7, 7, 7, 2, 1, 6};
		short[] decimal25 = {3, 3, 5, 5, 4, 4, 3, 2};
		short[] decimal26 = {6, 7, 1, 0, 8, 8, 6, 4};
		short[] decimal27 = {1, 3, 4, 2, 1, 7, 7, 2, 8};
		short[] decimal28 = {2, 6, 8, 4, 3, 5, 4, 5, 6};
		short[] decimal29 = {5, 3, 6, 8, 7, 0, 9, 1, 2};
		short[] decimal30 = {1, 0, 7, 3, 7, 4, 1, 8, 2, 4};
		short[] decimal31 = {2, 1, 4, 7, 4, 8, 3, 6, 4, 8};
		short[] decimal32 = {4, 2, 9, 4, 9, 6, 7, 2, 9, 6};
		short[] decimal33 = {8, 5, 8, 9, 9, 3, 4, 5, 9, 2};
		short[] decimal34 = {1, 7, 1, 7, 9, 8, 6, 9, 1, 8, 4};
		short[] decimal35 = {3, 4, 3, 5, 9, 7, 3, 8, 3, 6, 8};
		short[] decimal36 = {6, 8, 7, 1, 9, 4, 7, 6, 7, 3, 6};
		short[] decimal37 = {1, 3, 7, 4, 3, 8, 9, 5, 3, 4, 7, 2};
		short[] decimal38 = {2, 7, 4, 8, 7, 7, 9, 0, 6, 9, 4, 4};
		short[] decimal39 = {5, 4, 9, 7, 5, 5, 8, 1, 3, 8, 8, 8};
		short[] decimal40 = {1, 0, 9, 9, 5, 1, 1, 6, 2, 7, 7, 7, 6};
		short[] decimal41 = {2, 1, 9, 9, 0, 2, 3, 2, 5, 5, 5, 5, 2};
		short[] decimal42 = {4, 3, 9, 8, 0, 4, 6, 5, 1, 1, 1, 0, 4};
		short[] decimal43 = {8, 7, 9, 6, 0, 9, 3, 0, 2, 2, 2, 0, 8};
		short[] decimal44 = {1, 7, 5, 9, 2, 1, 8, 6, 0, 4, 4, 4, 1, 6};
		short[] decimal45 = {3, 5, 1, 8, 4, 3, 7, 2, 0, 8, 8, 8, 3, 2};
		short[] decimal46 = {7, 0, 3, 6, 8, 7, 4, 4, 1, 7, 7, 6, 6, 4};
		short[] decimal47 = {1, 4, 0, 7, 3, 7, 4, 8, 8, 3, 5, 5, 3, 2, 8};
		short[] decimal48 = {2, 8, 1, 4, 7, 4, 9, 7, 6, 7, 1, 0, 6, 5, 6};
		short[] decimal49 = {5, 6, 2, 9, 4, 9, 9, 5, 3, 4, 2, 1, 3, 1, 2};
		short[] decimal50 = {1, 1, 2, 5, 8, 9, 9, 9, 0, 6, 8, 4, 2, 6, 2, 4};
		short[] decimal51 = {2, 2, 5, 1, 7, 9, 9, 8, 1, 3, 6, 8, 5, 2, 4, 8};
		short[] decimal52 = {4, 5, 0, 3, 5, 9, 9, 6, 2, 7, 3, 7, 0, 4, 9, 6};
		short[] decimal53 = {9, 0, 0, 7, 1, 9, 9, 2, 5, 4, 7, 4, 0, 9, 9, 2};
		short[] decimal54 = {1, 8, 0, 1, 4, 3, 9, 8, 5, 0, 9, 4, 8, 1, 9, 8, 4};
		short[] decimal55 = {3, 6, 0, 2, 8, 7, 9, 7, 0, 1, 8, 9, 6, 3, 9, 6, 8};
		short[] decimal56 = {7, 2, 0, 5, 7, 5, 9, 4, 0, 3, 7, 9, 2, 7, 9, 3, 6};
		short[] decimal57 = {1, 4, 4, 1, 1, 5, 1, 8, 8, 0, 7, 5, 8, 5, 5, 8, 7, 2};
		short[] decimal58 = {2, 8, 8, 2, 3, 0, 3, 7, 6, 1, 5, 1, 7, 1, 1, 7, 4, 4};
		short[] decimal59 = {5, 7, 6, 4, 6, 0, 7, 5, 2, 3, 0, 3, 4, 2, 3, 4, 8, 8};
		short[] decimal60 = {1, 1, 5, 2, 9, 2, 1, 5, 0, 4, 6, 0, 6, 8, 4, 6, 9, 7, 6};
		short[] decimal61 = {2, 3, 0, 5, 8, 4, 3, 0, 0, 9, 2, 1, 3, 6, 9, 3, 9, 5, 2};
		short[] decimal62 = {4, 6, 1, 1, 6, 8, 6, 0, 1, 8, 4, 2, 7, 3, 8, 7, 9, 0, 4};
		short[] decimal63 = {9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 8};
		
		//get decimal value according to binary value
		switch(bitLoc) {
			case 0:
				return decimal00;
			case 1:
				return decimal01;
			case 2:
				return decimal02;
			case 3:
				return decimal03;
			case 4:
				return decimal04;
			case 5:
				return decimal05;
			case 6:
				return decimal06;
			case 7:
				return decimal07;
			case 8:
				return decimal08;
			case 9:
				return decimal09;
			case 10:
				return decimal10;
			case 11:
				return decimal11;
			case 12:
				return decimal12;
			case 13:
				return decimal13;
			case 14:
				return decimal14;
			case 15:
				return decimal15;
			case 16:
				return decimal16;
			case 17:
				return decimal17;
			case 18:
				return decimal18;
			case 19:
				return decimal19;
			case 20:
				return decimal20;
			case 21:
				return decimal21;
			case 22:
				return decimal22;
			case 23:
				return decimal23;
			case 24:
				return decimal24;
			case 25:
				return decimal25;
			case 26:
				return decimal26;
			case 27:
				return decimal27;
			case 28:
				return decimal28;
			case 29:
				return decimal29;
			case 30:
				return decimal30;
			case 31:
				return decimal31;
			case 32:
				return decimal32;
			case 33:
				return decimal33;
			case 34:
				return decimal34;
			case 35:
				return decimal35;
			case 36:
				return decimal36;
			case 37:
				return decimal37;
			case 38:
				return decimal38;
			case 39:
				return decimal39;
			case 40:
				return decimal40;
			case 41:
				return decimal41;
			case 42:
				return decimal42;
			case 43:
				return decimal43;
			case 44:
				return decimal44;
			case 45:
				return decimal45;
			case 46:
				return decimal46;
			case 47:
				return decimal47;
			case 48:
				return decimal48;
			case 49:
				return decimal49;
			case 50:
				return decimal50;
			case 51:
				return decimal51;
			case 52:
				return decimal52;
			case 53:
				return decimal53;
			case 54:
				return decimal54;
			case 55:
				return decimal55;
			case 56:
				return decimal56;
			case 57:
				return decimal57;
			case 58:
				return decimal58;
			case 59:
				return decimal59;
			case 60:
				return decimal60;	
			case 61:
				return decimal61;
			case 62:
				return decimal62;
			case 63:
				return decimal63;
			default:
				return null;
		}
	}
}
