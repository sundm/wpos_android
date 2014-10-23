package com.zc.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{
  public static String md5String(String paramString)
  {
    MessageDigest localMessageDigest;
    try
    {
      localMessageDigest = MessageDigest.getInstance("MD5");
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new RuntimeException(localNoSuchAlgorithmException);
    }
    byte[] arrayOfByte = localMessageDigest.digest(paramString.getBytes());
    return HexUtil.toHexString(arrayOfByte);
  }
}