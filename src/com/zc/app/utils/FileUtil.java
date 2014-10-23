package com.zc.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil
{
  public static void copyStream(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException
  {
    byte[] arrayOfByte = new byte[1024];
    int i = 0;
    while (true)
    {
      i = paramInputStream.read(arrayOfByte, 0, arrayOfByte.length);
      if (i < 0)
        break;
      paramOutputStream.write(arrayOfByte, 0, i);
      paramOutputStream.flush();
    }
  }

  public static void copyFile(File paramFile1, File paramFile2) throws IOException
  {
    if (!paramFile1.exists())
      return;
    FileInputStream localFileInputStream = null;
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileInputStream = new FileInputStream(paramFile1);
      localFileOutputStream = new FileOutputStream(paramFile2);
      copyStream(localFileInputStream, localFileOutputStream);
    }
    finally
    {
      try
      {
        localFileInputStream.close();
      }
      catch (Exception localException1)
      {
      }
      try
      {
        localFileOutputStream.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }

  public static void deleteDir(File paramFile)
  {
    if (!paramFile.exists())
      return;
    File[] arrayOfFile = paramFile.listFiles();
    if ((arrayOfFile != null) && (arrayOfFile.length > 0))
    {
      File localFile = null;
      for (int i = 0; i < arrayOfFile.length; i++)
      {
        localFile = arrayOfFile[i];
        if (localFile.isDirectory())
          deleteDir(localFile);
        else
          localFile.delete();
      }
    }
    paramFile.delete();
  }

  public static void copyItemsInDir(File paramFile1, File paramFile2) throws IOException
  {
    if (!paramFile2.exists())
      paramFile2.mkdirs();
    File[] arrayOfFile = paramFile1.listFiles();
    if ((arrayOfFile != null) && (arrayOfFile.length > 0))
    {
      File localFile1 = null;
      File localFile2 = null;
      for (int i = 0; i < arrayOfFile.length; i++)
      {
        localFile1 = arrayOfFile[i];
        localFile2 = new File(paramFile2, localFile1.getName());
        if (localFile1.isDirectory())
          copyItemsInDir(localFile1, localFile2);
        else
          copyFile(localFile1, localFile2);
      }
    }
  }
}
