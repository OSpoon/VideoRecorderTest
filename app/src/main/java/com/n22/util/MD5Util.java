package com.n22.util;


import com.n22.bean.net.Config;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密工具类
 * 
 * @author yang
 */
public class MD5Util {

	public static String encode(String str) {
		return encode(str, "MD5");
	}

	public static String encode(String str, String type) {
		return encode(str, "UTF-8", type);
	}

	/*
	 * MD5加密
	 */
	private static String encode(String str, String encoding, String type) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(type);
			messageDigest.reset();
			messageDigest.update(str.getBytes(encoding));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				builder.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				builder.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return builder.toString();
	}

	private static final char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	private static MD5Tool md5;

	public static String md5Encode(String data, String encoding) {
//		try {
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(data.getBytes(encoding));
//			return hexEncode(md.digest());
//		} catch (Exception e) {
//			throw new IllegalArgumentException("Encoding failed", e);
//		}

		String resultStr = null;
		try {
			init();
			byte[] result = md5.ComputeHash(data.getBytes(encoding));
			resultStr = MD5Tool.byte2hex(result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}

	public static String hexEncode(byte[] bytes) {
		int nBytes = bytes.length;
		char[] result = new char[2 * nBytes];

		int j = 0;
		for (int i = 0; i < nBytes; i++) {
			result[(j++)] = HEX[((0xF0 & bytes[i]) >>> 4)];

			result[(j++)] = HEX[(0xF & bytes[i])];
		}

		return new String(result);
	}

	public static byte[] getMD5Mac(byte[] bySourceByte) {
		byte[] byDisByte;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(bySourceByte);
			byDisByte = md.digest();
		} catch (NoSuchAlgorithmException n) {

			return (null);
		}
		return (byDisByte);
	}

	public static String bintoascii(byte[] bySourceByte)

	{
		int len, i;
		byte tb;
		char high, tmp, low;
		String result = new String();
		len = bySourceByte.length;
		for (i = 0; i < len; i++) {
			tb = bySourceByte[i];
			tmp = (char) ((tb >>> 4) & 0x000f);
			if (tmp >= 10)
				high = (char) ('a' + tmp - 10);
			else
				high = (char) ('0' + tmp);
			result += high;
			tmp = (char) (tb & 0x000f);
			if (tmp >= 10)
				low = (char) ('a' + tmp - 10);
			else
				low = (char) ('0' + tmp);
			result += low;
		}
		return result;
	}

	public static String getMD5ofStr(String inbuf, String encoding) throws Exception {
		if (inbuf == null || "".equals(inbuf))
			return "";
		return bintoascii(getMD5Mac(inbuf.getBytes(encoding)));
	}

	public static String createSignUsingMD5(String inbuf, String encoding) throws Exception {
//		return getMD5ofStr(inbuf, encoding).toLowerCase();
//		return getMD5ofStr(inbuf, encoding);
		//edit by han 2016-01-06
		init();
		byte[] result = md5.ComputeHash(inbuf.getBytes("UTF-8"));
		String resultStr=MD5Tool.byte2hex(result);
		return resultStr;
	}

	private static synchronized void init() throws GeneralSecurityException {
		if(md5 == null)
		md5 = new MD5Tool(Config.ORG_VALIDATE_CODE.getBytes());
	}

}
