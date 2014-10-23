package com.zc.app.sebc.iso7816;

public class BerTlv {

	public class BerT {
		
	}
	
	public class BerL {
		
	}
	
	public class BerV {
		
	}
	
	//get the length of a tag in a string, typically a DOL string
	//-1:data error; -2:tag error
	public static int getTagLen(byte[] data, int offset, boolean isIso) {
		
		if(data == null) {
			return -1;
		}
		
		if(offset < 0) {
			return -1;
		}
		
		int len = data.length;
		int lenTag = 1;
		
		if(offset >= len) {
			return -1;
		}
		
		//tag can not be 0
		if(data[offset] == 0) {
			return -2;
		}
		
		if((data[offset]&0x1F) != 0x1F) {
			return lenTag;
		}
		
		if((++offset) >= len) {
			return -1;
		}
		
		if((data[offset]&0x80) == 0x00) {
			
			if(isIso == true) {
				if(data[offset] > (byte)0x1E) {
					return lenTag+1;
				} else {
					return -2;
				}
			} else {
				return lenTag+1;
			}
		}
		
		if((data[offset++]&0x7F) == 0x00) {
			return -2;
		}
		
		if((offset) >= len) {
			return -1;
		}
		
		if((data[offset]&0x80) == 0x00) {
			return lenTag+2;
		} else {
			return -2;
		}
	}
	
	//get the length of L in a LV (TLV), maximum 4
	public static int getLenLen(byte[] data, int offset) {
		
		if(data == null) {
			return -1;
		}
		
		if(offset < 0) {
			return -1;
		}
		
		int len = data.length;
		if(offset >= len) {
			return -1;
		}
		
		int lenL;
		int lenV;
		int offsetBk;
		
		offsetBk = offset;
		lenV = data[offset++]&0xFF;
		
		//check length of L and V
		if(lenV < 0x80) {
			lenL = 1;
		} else if(lenV == 0x81) {
			lenL = 2;
			lenV = data[offset++]&0xFF;
		} else if(lenV == 0x82) {
			lenL = 3;
			lenV = (data[offset++]&0xFF)*0x100;
			lenV += data[offset++]&0xFF;
		} else if(lenV == 0x83) {
			lenL = 4;
			lenV = (data[offset++]&0xFF)*0x10000;
			lenV += (data[offset++]&0xFF)*0x100;
			lenV += data[offset++]&0xFF;
		} else {
			return -2;
		}
		
		//length of data is not enough
		if(len < offsetBk+lenL+lenV) {
			return -1;
		}
		
		return lenL;
	}
	
	//get the length of L in a LV (TLV), maximum 4, length of V is returned in lenValue
	public static int getLenLen(byte[] data, int offset, int[] lenValue) {
		
		if(data == null) {
			return -1;
		}
		
		if(offset < 0) {
			return -1;
		}
		
		if(lenValue == null) {
			return -1;
		} else if(lenValue.length < 1) {
			return -1;
		}
		
		int len = data.length;
		if(offset >= len) {
			return -1;
		}
		
		int lenL;
		int lenV;
		int offsetBk;
		
		offsetBk = offset;
		lenV = data[offset++]&0xFF;
		
		//check length of L and V
		if(lenV < 0x80) {
			lenL = 1;
		} else if(lenV == 0x81) {
			lenL = 2;
			lenV = data[offset++]&0xFF;
		} else if(lenV == 0x82) {
			lenL = 3;
			lenV = (data[offset++]&0xFF)*0x100;
			lenV += data[offset++]&0xFF;
		} else if(lenV == 0x83) {
			lenL = 4;
			lenV = (data[offset++]&0xFF)*0x10000;
			lenV += (data[offset++]&0xFF)*0x100;
			lenV += data[offset++]&0xFF;
		} else {
			return -2;
		}
		
		//length of data is not enough
		if(len < offsetBk+lenL+lenV) {
			return -1;
		}
		
		lenValue[0] = lenV;
		return lenL;
	}
	
	public static int isConstructed(byte[] data, int offset) {
		
		if(data == null) {
			return -1;
		}
		
		if(offset < 0) {
			return -1;
		}
		
		int len = data.length;
		if(offset >= len) {
			return -1;
		}
		
		if(data[0] == (byte)0x00) {
			return -2;
		}
		
		if((data[0]&0x20) == (byte)0x00) {
			return 0;
		} else {
			return 1;
		}
	}
	
	//get the location of a tag in a string of TLVs, tag maximum length 3
	//assume tag is a valid tag, i.e., comfirm to iso7816-4
	//returns the offset of L of TLV
	public static int getTagLoc(byte[] tlv, byte[] tag) {
		
		if(tlv == null) {
			return -1;
		}
		
		if(tag == null) {
			return -1;
		}
		
		int lenT = tag.length;
		int lenD = tlv.length;
		
		//check tag length
		if((lenT < 1) || (lenT > 3)) {
			return -1;
		}
		
		//check length of TLVs
		if(lenD <= lenT) {
			return -1;
		}
		
		int i = 0;
		int lenT1;
		int lenL;
		int lenV;
		int offset = 0;
		byte bytTemp1;
		byte bytTemp2;
		byte bytTemp3;
		boolean isConstruct;
		
		while(lenD > 0) {
			
			if(i++>256) {																		//avoid deadlock
				return -1;
			}
			
			isConstruct = false;
			bytTemp1 = tlv[offset++];
			
			//check if it is construct type
			if((bytTemp1&0x20) == 0x20) {
				isConstruct = true;
			}
			
			//tag length is 1 byte
			if((bytTemp1&0x1F) != 0x1F) {
				
				lenT1 = 1;
				if(lenT1 == lenT) {
					
					if(bytTemp1 == tag[0]) {
						return offset;
					}
				}
			}
			else {
				
				//tag length is 2 bytes
				bytTemp2 = tlv[offset++];
				if((bytTemp2&0x80) == 0x00) {
					
					lenT1 = 2;
					if(lenT1 == lenT) {
						
						if((bytTemp1 == tag[0]) && (bytTemp2 == tag[1])) {
							return offset;
						}
					}
				}
				else {
					
					//tag length is 3 bytes
					bytTemp3 = tlv[offset++];
					if((bytTemp3&0x80) == 0x00) {
						
						lenT1 = 3;
						if(lenT1 == lenT) {
							
							if((bytTemp1 == tag[0]) && (bytTemp2 == tag[1]) && (bytTemp3 == tag[2])) {
								return offset;
							}
						}
					}
					else {
						return -2;
					}
				}
			}
			
			//check length of L and V
			lenV = tlv[offset++]&0xFF;
			if(lenV < 0x80) {
				lenL = 1;
			} else if(lenV == 0x81) {
				lenV = tlv[offset++]&0xFF;
				lenL = 2;
			} else if(lenV == 0x82) {
				lenV = (tlv[offset++]&0xFF)*0x100;
				lenV += tlv[offset++]&0xFF;
				lenL = 3;
			} else if(lenV == 0x83) {
				lenV = (tlv[offset++]&0xFF)*0x10000;
				lenV += (tlv[offset++]&0xFF)*0x100;
				lenV += tlv[offset++]&0xFF;
				lenL = 4;
			} else {
				return -2;
			}
			
			//check if length of TLV is enough
			if(lenD < (lenV+lenT1+lenL)) {
				return -1;
			}
			
			if(isConstruct == false) {
				lenD -= (lenV+lenT1+lenL);
				offset += lenV;
			}
			else {
				lenD -= (lenT1+lenL);
			}
		}
		
		return 0;
	}
	
	//get TLV value in a string of TLVs
	public static byte[] getTlvValue(byte[] tlv, byte[] tag) {
		
		if(tlv == null) {
			return null;
		}
		
		if(tag == null) {
			return null;
		}
		
		int lenT = tag.length;
		int lenD = tlv.length;
		
		//check tag length
		if((lenT < 1) || (lenT > 3)) {
			return null;
		}
		
		//check length of TLVs
		if(lenD <= lenT) {
			return null;
		}
		
		int i = 0;
		int lenT1;
		int lenL;
		int lenV = 0;
		int offset = 0;
		byte bytTemp1;
		byte bytTemp2;
		byte bytTemp3;
		boolean isConstruct;
		boolean isFound = false;
		
		while(lenD > 0) {
			
			if(i++>256) {																		//avoid deadlock
				return null;
			}
			
			isConstruct = false;
			bytTemp1 = tlv[offset++];
			
			//check if it is construct type
			if((bytTemp1&0x20) == 0x20) {
				isConstruct = true;
			}
			
			//tag length is 1 byte
			if((bytTemp1&0x1F) != 0x1F) {
				
				lenT1 = 1;
				if(lenT1 == lenT) {
					
					if(bytTemp1 == tag[0]) {
						isFound = true;
					}
				}
			}
			else {
				
				//tag length is 2 bytes
				bytTemp2 = tlv[offset++];
				if((bytTemp2&0x80) == 0x00) {
					
					lenT1 = 2;
					if(lenT1 == lenT) {
						
						if((bytTemp1 == tag[0]) && (bytTemp2 == tag[1])) {
							isFound = true;
						}
					}
				}
				else {
					
					//tag length is 3 bytes
					bytTemp3 = tlv[offset++];
					if((bytTemp3&0x80) == 0x00) {
						
						lenT1 = 3;
						if(lenT1 == lenT) {
							
							if((bytTemp1 == tag[0]) && (bytTemp2 == tag[1]) && (bytTemp3 == tag[2])) {
								isFound = true;
							}
						}
					}
					else {
						return null;
					}
				}
			}
			
			//check length of L and V
			lenV = tlv[offset++]&0xFF;
			if(lenV < 0x80) {
				lenL = 1;
			} else if(lenV == 0x81) {
				lenV = tlv[offset++]&0xFF;
				lenL = 2;
			} else if(lenV == 0x82) {
				lenV = (tlv[offset++]&0xFF)*0x100;
				lenV += tlv[offset++]&0xFF;
				lenL = 3;
			} else if(lenV == 0x83) {
				lenV = (tlv[offset++]&0xFF)*0x10000;
				lenV += (tlv[offset++]&0xFF)*0x100;
				lenV += tlv[offset++]&0xFF;
				lenL = 4;
			} else {
				return null;
			}
			
			//check if length of TLV is enough
			if(lenD < (lenT1+lenL+lenV)) {
				return null;
			}
			
			if(isFound == true) {
				break;
			}
			
			if(isConstruct == false) {
				lenD -= (lenT1+lenL+lenV);
				offset += lenV;
			}
			else {
				lenD -= (lenT1+lenL);
			}
		}
		
		//tag found
		if(isFound == true) {			
			
			if(lenV == 0) {
				return null;
			}
			
			byte value[] = new byte[lenV];				
			
			//get value of the tag
			for(i = 0; i < lenV; i++) {
				value[i] = tlv[offset+i];
			}
			
			return value;
		}
		else {
			return null;
		}
	}
}
