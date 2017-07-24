package com.n22.util;

import com.n22.util.encoder.Base64Tool;
import com.n22.util.encoder.ThreeDES;

import org.apache.http.util.EncodingUtils;

import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * hmac-md5 算法
 * 与dot.NET System.Security.Cryptography.HMACMD5 Class兼容
 * @author mac
 *
 */
public class MD5Tool {
	private final String HMAC_MD5_NAME = "HmacMD5";
	private SecretKeySpec sk;
	private Mac mac;

	public MD5Tool(byte[] key) throws GeneralSecurityException {
		init(key);
	}

	public MD5Tool(String key) throws GeneralSecurityException {
		init(EncodingUtils.getAsciiBytes(key));
	}

	private void init(byte[] key) throws GeneralSecurityException {
		sk = new SecretKeySpec(key, HMAC_MD5_NAME);
		mac = Mac.getInstance(HMAC_MD5_NAME);
		mac.init(sk);
	}

	public byte[] ComputeHash(byte[] data) {
		return mac.doFinal(data);
	}

	public byte[] ComputeHash(String data) {
		return ComputeHash(EncodingUtils.getAsciiBytes(data));
	}
	
	/**
	 * 将加密结果采用Base64编码表示
	 * @param key
	 * @param str
	 * @return
	 */
	public static  String md5(String key,String str){
		String resultStr="";
		try{
		MD5Tool md5=new MD5Tool(key.getBytes());
		byte[] result=md5.ComputeHash(str.getBytes("UTF-8"));
		resultStr= Base64Tool.encode(result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return resultStr;
	}
	
	/**
	 * 将加密结果采用16进制数表示
	 * @param key
	 * @param str
	 * @return
	 */
	public static  String md5Hex(String key,String str){
		String resultStr="";
		try{
		MD5Tool md5=new MD5Tool(key.getBytes());
		byte[] result=md5.ComputeHash(str.getBytes("UTF-8"));
		resultStr=MD5Tool.byte2hex(result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return resultStr;
	}
	
	public static String byte2hex(byte[] b) //二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
		stmp = (Integer.toHexString(b[n] & 0XFF));
		if (stmp.length() == 1)
		hs = hs + "0" + stmp;
		else
		hs = hs + stmp;
		}
		return hs;
	}
	public static void main(String[] args) throws Exception {
		String key ="ABCD12345678901234567890";
		String data="汉字";
		data= ThreeDES.encode(data, key);
		MD5Tool md5=new MD5Tool(key.getBytes());
		byte[] result=md5.ComputeHash(data.getBytes("UTF-8"));
		String resultStr=MD5Tool.byte2hex(result);
		//String resultStr=Base64Tool.encode(result);
		System.out.println(resultStr);
	}
	//CwtXP2yBymnbaS4FCuPFMw==
}
