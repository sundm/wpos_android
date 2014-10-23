package com.zc.app.utils;

public class HexUtil
{
  public static String toHexString(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder(Integer.toHexString(paramInt));
    for (int i = localStringBuilder.length(); i < 8; i++)
      localStringBuilder.insert(0, '0');
    return localStringBuilder.toString();
  }

  public static String toHexString(byte[] paramArrayOfByte)
  {
    return toHexString(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static String toHexString(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder(Long.toHexString(paramLong));
    for (int i = localStringBuilder.length(); i < 16; i++)
      localStringBuilder.insert(0, '0');
    return localStringBuilder.toString();
  }

  public static String toHexString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    long l = 0L;
    int i = paramInt2 / 8;
    int j = paramInt1;
    StringBuilder localStringBuilder = new StringBuilder();
    for (int k = 0; k < i; k++)
    {
      l = (paramArrayOfByte[j] << 56 & 0x0) + (paramArrayOfByte[(j + 1)] << 48 & 0x0) + (paramArrayOfByte[(j + 2)] << 40 & 0x0) + (paramArrayOfByte[(j + 3)] << 32 & 0x0) + (paramArrayOfByte[(j + 4)] << 24 & 0xFF000000) + (paramArrayOfByte[(j + 5)] << 16 & 0xFF0000) + (paramArrayOfByte[(j + 6)] << 8 & 0xFF00) + (paramArrayOfByte[(j + 7)] & 0xFF);
      localStringBuilder.append(toHexString(l));
      j += 8;
    }
    while (j < paramInt2)
    {
      localStringBuilder.append(toHexString(paramArrayOfByte[j]));
      j++;
    }
    return localStringBuilder.toString();
  }
}