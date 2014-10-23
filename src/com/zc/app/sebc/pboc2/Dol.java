package com.zc.app.sebc.pboc2;

import com.zc.app.sebc.iso7816.BerTlv;
import com.zc.app.sebc.util.BasicUtil;

public final class Dol {

	//get the value location of a tag in a DOL
	public static int getValLocInDol(byte[] dolData, int start, int end, byte[] bytTag) {
		
		if(dolData == null) {
			return -1;
		}
		
		if(start < 0) {
			return -1;
		}
		
		if(start >= end) {
			return -1;
		}
		
		int len = dolData.length;
		if(len < end) {
			return -1;
		}
		
		if(bytTag == null) {
			return -1;
		}
		
		int offset = 0;
		int locTag = 0;
		int lenTag;
		int lenLen;
		int i;
		
		len = end-start;
		while(len > 0) {
			
			//get tag length
			lenTag = BerTlv.getTagLen(dolData, start+offset, false);
			if(lenTag < 0) {
				return lenTag;
			}
			
			if(bytTag.length == lenTag) {
				
				//check if the tag contained in bytTag is equal to a tag in DOL
				for(i = 0; i < lenTag; i++) {
					if(bytTag[i] != dolData[start+offset+i]) {
						break;
					}
				}
				
				//tag found
				if(i == lenTag) {
					return locTag;
				}
			}
			
			//check the length of L, and calculate the location
			offset += lenTag;
			if((dolData[start+offset]&0xFF) < 0x80) {
				
				lenLen = 1;
				locTag += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x81) {
				
				offset++;
				lenLen = 2;
				locTag += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x82) {
				
				offset++;
				lenLen = 3;
				locTag += (dolData[start+offset++]&0xFF)*0x100;
				locTag += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x83) {
				
				offset++;
				lenLen = 4;
				locTag += (dolData[start+offset++]&0xFF)*0x10000;
				locTag += (dolData[start+offset++]&0xFF)*0x100;
				locTag += dolData[start+offset++]&0xFF;
				
			} else {
				return -2;
			}
			
			len -= (lenTag+lenLen);
		}
		
		return -1;
	}
	
	//get the total length of the values indicated in a DOL
	public static int getDolDataLen(byte[] dolData, int start, int end, boolean flag) {
		
		if(dolData == null) {
			return -1;
		}
		
		if(start < 0) {
			return -1;
		}
		
		if(start >= end) {
			return -1;
		}
		
		int len = dolData.length;
		if(len < end) {
			return -1;
		}
		
		int offset = 0;
		int lenDol = 0;
		int lenTag;
		int lenLen;
		
		len = end-start;
		while(len > 0) {
			
			//get tag length
			lenTag = BerTlv.getTagLen(dolData, start+offset, false);
			if(lenTag < 0) {
				return lenTag;
			}
			
			//check length of L and V, and get lengths of the values indicated in the DOL
			offset += lenTag;
			if((dolData[start+offset]&0xFF) < 0x80) {
				
				lenLen = 1;
				lenDol += (dolData[start+offset++]&0xFF);
				
			} else if((dolData[start+offset]&0xFF) == 0x81) {
				
				offset++;
				lenLen = 2;
				lenDol += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x82) {
				
				offset++;
				lenLen = 3;
				lenDol += (dolData[start+offset++]&0xFF)*0x100;
				lenDol += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x83) {
				
				offset++;
				lenLen = 4;
				lenDol += (dolData[start+offset++]&0xFF)*0x10000;
				lenDol += (dolData[start+offset++]&0xFF)*0x100;
				lenDol += dolData[start+offset++]&0xFF;
				
			} else {
				return -2;
			}
			
			if(flag == true) {
				lenDol = 0;
				flag = false;
			}
			
			len -= (lenTag+lenLen);
		}
		
		return lenDol;
	}
	
	public static int[] getDolTagNumberAndDataLen(byte[] dolData, int start, int end, boolean flag) {
		
		int[] result1 = {-1, 0, 0};
		int[] result2 = {-2, 0, 0};
		
		if(dolData == null) {
			return result1;
		}
		
		if(start < 0) {
			return result1;
		}
		
		if(start >= end) {
			return result1;
		}
		
		int len = dolData.length;
		if(len < end) {
			return result1;
		}
		
		int offset = 0;
		int lenDol = 0;
		int numTag = 0;
		int lenTag;
		int lenLen;
		
		len = end-start;
		while(len > 0) {
			
			//get tag length
			lenTag = BerTlv.getTagLen(dolData, start+offset, false);
			if(lenTag < 0) {
				result1[0] = lenTag;
				return result1;
			}
			
			//check length of L and V, and get lengths of the values indicated in the DOL
			offset += lenTag;
			if((dolData[start+offset]&0xFF) < 0x80) {
				
				lenLen = 1;
				lenDol += (dolData[start+offset++]&0xFF);
				
			} else if((dolData[start+offset]&0xFF) == 0x81) {
				
				offset++;
				lenLen = 2;
				lenDol += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x82) {
				
				offset++;
				lenLen = 3;
				lenDol += (dolData[start+offset++]&0xFF)*0x100;
				lenDol += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x83) {
				
				offset++;
				lenLen = 4;
				lenDol += (dolData[start+offset++]&0xFF)*0x10000;
				lenDol += (dolData[start+offset++]&0xFF)*0x100;
				lenDol += dolData[start+offset++]&0xFF;
				
			} else {
				return result2;
			}
			
			numTag ++;
			if(flag == true) {
				numTag = 0;
				lenDol = 0;
				flag = false;
			}
			
			len -= (lenTag+lenLen);
		}
		
		result1[0] = 0;
		result1[1] = numTag;
		result1[2] = lenDol;
		return result1;
	}
	
	public static String[][] getDolTagsAndLens(byte[] dolData, int start, int end, int tags, boolean flag) {
		
		if(dolData == null) {
			return null;
		}
		
		if(start < 0) {
			return null;
		}
		
		if(start >= end) {
			return null;
		}
		
		int len = dolData.length;
		if(len < end) {
			return null;
		}
		
		if(tags < 1) {
			return null;
		}
		
		int offset = 0;
		int numTag = 0;
		int lenTag;
		int lenLen;
		int lenVal;
		String[][] dolTagsLens = new String[2][tags];
		
		len = end-start;
		while(len > 0) {			
			
			//get tag length
			lenTag = BerTlv.getTagLen(dolData, start+offset, false);
			if(lenTag < 0) {
				return null;
			}
			
			if(flag == false) {
				if((numTag+1) > tags) {
					return null;
				}
				
				String temp = BasicUtil.bytesToHexString(dolData, start+offset, lenTag);
				dolTagsLens[0][numTag] = temp;
			}
			
			//check length of L and V, and get lengths of the values indicated in the DOL
			offset += lenTag;
			if((dolData[start+offset]&0xFF) < 0x80) {
				
				lenLen = 1;
				lenVal = (dolData[start+offset++]&0xFF);
				
			} else if((dolData[start+offset]&0xFF) == 0x81) {
				
				offset++;
				lenLen = 2;
				lenVal = dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x82) {
				
				offset++;
				lenLen = 3;
				lenVal = (dolData[start+offset++]&0xFF)*0x100;
				lenVal += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x83) {
				
				offset++;
				lenLen = 4;
				lenVal = (dolData[start+offset++]&0xFF)*0x10000;
				lenVal += (dolData[start+offset++]&0xFF)*0x100;
				lenVal += dolData[start+offset++]&0xFF;
				
			} else {
				return null;
			}
			
			if(flag == false) {
				dolTagsLens[1][numTag++] = Integer.toString(lenVal);
			} else {
				flag = false;
			}
			
			len -= (lenTag+lenLen);
		}
		
		if(tags != numTag) {
			return null;
		}
		
		return dolTagsLens;
	}
	
	public static String[] getDolValues(byte[] dolData, int start, int end, int tags, boolean flag, byte[] data) {
		
		if(dolData == null) {
			return null;
		}
		
		if(start < 0) {
			return null;
		}
		
		if(start >= end) {
			return null;
		}
		
		int len = dolData.length;
		if(len < end) {
			return null;
		}
		
		if(tags < 1) {
			return null;
		}
		
		if(data == null) {
			return null;
		}
		
		int offset = 0;
		int numTag = 0;
		int lenTag;
		int lenLen;
		int lenVal;
		int lenAllVals = 0;
		String[] dolVals = new String[tags];
		
		len = end-start;
		while(len > 0) {
			
			//get tag length
			lenTag = BerTlv.getTagLen(dolData, start+offset, false);
			if(lenTag < 0) {
				return null;
			}
			
			if(flag == false) {
				if((numTag+1) > tags) {
					return null;
				}
			}
			
			//check length of L and V, and get lengths of the values indicated in the DOL
			offset += lenTag;
			if((dolData[start+offset]&0xFF) < 0x80) {
				
				lenLen = 1;
				lenVal = (dolData[start+offset++]&0xFF);
				
			} else if((dolData[start+offset]&0xFF) == 0x81) {
				
				offset++;
				lenLen = 2;
				lenVal = dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x82) {
				
				offset++;
				lenLen = 3;
				lenVal = (dolData[start+offset++]&0xFF)*0x100;
				lenVal += dolData[start+offset++]&0xFF;
				
			} else if((dolData[start+offset]&0xFF) == 0x83) {
				
				offset++;
				lenLen = 4;
				lenVal = (dolData[start+offset++]&0xFF)*0x10000;
				lenVal += (dolData[start+offset++]&0xFF)*0x100;
				lenVal += dolData[start+offset++]&0xFF;
				
			} else {
				return null;
			}
			
			if(flag == false) {
				
				if((lenAllVals+lenVal) > data.length) {
					return null;
				}
				
				String temp = BasicUtil.bytesToHexString(data, lenAllVals, lenVal);
				dolVals[numTag++] = temp;
				lenAllVals += lenVal;
				
			} else {
				flag = false;
			}
			
			len -= (lenTag+lenLen);
		}
		
		if(tags != numTag) {
			return null;
		}
		
		return dolVals;
	}
}
