package com.n22.util.encoder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class Base64Encoder extends FilterOutputStream
{
  private static final char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
  private int charCount;
  private int carryOver;

  public Base64Encoder(OutputStream out)
  {
    super(out);
  }

  public void write(int b)
    throws IOException
  {
    if (b < 0) {
      b += 256;
    }

    if (this.charCount % 3 == 0) {
      int lookup = b >> 2;
      this.carryOver = (b & 0x3);
      this.out.write(chars[lookup]);
    }
    else if (this.charCount % 3 == 1) {
      int lookup = (this.carryOver << 4) + (b >> 4) & 0x3F;
      this.carryOver = (b & 0xF);
      this.out.write(chars[lookup]);
    }
    else if (this.charCount % 3 == 2) {
      int lookup = (this.carryOver << 2) + (b >> 6) & 0x3F;
      this.out.write(chars[lookup]);
      lookup = b & 0x3F;
      this.out.write(chars[lookup]);
      this.carryOver = 0;
    }
    this.charCount += 1;

    if (this.charCount % 57 == 0)
      this.out.write(10);
  }

  public void write(byte[] buf, int off, int len)
    throws IOException
  {
    for (int i = 0; i < len; i++)
      write(buf[(off + i)]);
  }

  public void close()
    throws IOException
  {
    if (this.charCount % 3 == 1) {
      int lookup = this.carryOver << 4 & 0x3F;
      this.out.write(chars[lookup]);
      this.out.write(61);
      this.out.write(61);
    }
    else if (this.charCount % 3 == 2) {
      int lookup = this.carryOver << 2 & 0x3F;
      this.out.write(chars[lookup]);
      this.out.write(61);
    }
    super.close();
  }

  public static String encode(String unencoded)
  {
    byte[] bytes = null;
    try {
      bytes = unencoded.getBytes("8859_1");
    } catch (UnsupportedEncodingException ignored) {
    }
    return encode(bytes);
  }

  public static String encode(byte[] bytes)
  {
    ByteArrayOutputStream out = new ByteArrayOutputStream((int)(bytes.length * 1.37D));

    Base64Encoder encodedOut = new Base64Encoder(out);
    try
    {
      encodedOut.write(bytes);
      encodedOut.close();

      return out.toString("8859_1"); } catch (IOException ignored) {
    }
    return null;
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: java com.oreilly.servlet.Base64Encoder fileToEncode");

      return;
    }

    Base64Encoder encoder = null;
    BufferedInputStream in = null;
    try {
      encoder = new Base64Encoder(System.out);
      in = new BufferedInputStream(new FileInputStream(args[0]));

      byte[] buf = new byte[4096];
      int bytesRead;
      while ((bytesRead = in.read(buf)) != -1)
        encoder.write(buf, 0, bytesRead);
    }
    finally
    {
      if (in != null) in.close();
      if (encoder != null) encoder.close();
    }
  }
}
