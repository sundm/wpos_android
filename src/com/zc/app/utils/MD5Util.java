package com.zc.app.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	public static String md5String(String paramString) {

		return DigestUtils.md5Hex(paramString);
	}
}