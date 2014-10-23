package com.zc.app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResourceFileManager
{
  private String a;

  public String getFileStorageDirPath()
  {
    return this.a;
  }

  public ResourceFileManager(String paramString)
  {
    File localFile = new File(paramString);
    a(localFile);
  }

  public ResourceFileManager(File paramFile)
  {
    a(paramFile);
  }

  private void a(File paramFile)
  {
    String str = paramFile.getAbsolutePath();
    if (str.endsWith(File.separator))
      this.a = str.substring(0, str.length() - 1);
    else
      this.a = str;
    if (paramFile.exists())
    {
      if (!paramFile.isDirectory())
      {
        paramFile.delete();
        paramFile.mkdirs();
      }
    }
    else
      paramFile.mkdirs();
  }

  public String getResourceFilePath(String paramString)
  {
    return this.a + File.separator + paramString;
  }

  public boolean isResourceFileExists(String paramString)
  {
    File localFile = new File(this.a, paramString);
    return localFile.exists();
  }

  public void changeResId(String paramString1, String paramString2)
  {
    File localFile = new File(this.a, paramString1);
    localFile.renameTo(new File(this.a, paramString2));
  }

  public void saveResourceFileWithData(byte[] paramArrayOfByte, String paramString) throws IOException
  {
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileOutputStream = new FileOutputStream(new File(this.a, paramString));
      localFileOutputStream.write(paramArrayOfByte);
      localFileOutputStream.flush();
    }
    finally
    {
      try
      {
        localFileOutputStream.close();
      }
      catch (Exception localException1)
      {
      }
    }
  }
}