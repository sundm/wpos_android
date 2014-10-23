package com.zc.app.sebc.bridge;

import com.zc.app.sebc.pboc2.Sw1Sw2;

public class MaliciousException extends Exception {
	
	 private static final long serialVersionUID = 1L;
	 
	 private String code;
	 
	 public MaliciousException(String code) {
		 
		 if(code == null) {
			 this.code = Sw1Sw2.SW1SW2_F9FF;
		 } else if(code.length() != 4) {
			 this.code = Sw1Sw2.SW1SW2_F9FF;
		 } else {
			 this.code = code;
		 }
	 }
	 
	 public String getMessageCode() {
		 return this.code;
	 }
	 
	 public String getMessage() {
		 String statusCode = StatusCode.toOuterStatusCode(this.code);
		 return StatusCode.toReadableSw1Sw2(statusCode);
	 }
}
