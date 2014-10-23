package com.zc.app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.util.Log;

public class URLStringEncoder
{
  public static final String DefaultEncoding = "utf-8";

  public static String decodeURLString(String paramString)
  {
    try
    {
      return URLDecoder.decode(paramString.replaceAll("%20", "\\+"), "utf-8");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Log.e("URLStringEncoder", "decodeURLString()", localUnsupportedEncodingException);
    }
    return "";
  }

  public static String encodeURLString(String paramString)
  {
    try
    {
      return URLEncoder.encode(paramString, "utf-8").replaceAll("\\+", "%20");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Log.e("URLStringEncoder", "encodeURLString()", localUnsupportedEncodingException);
    }
    return "";
  }
}