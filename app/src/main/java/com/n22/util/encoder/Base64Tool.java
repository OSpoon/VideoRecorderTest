package com.n22.util.encoder;

public class Base64Tool {
	public static String encode(String strSrc,String encode){
		String strOut="";
		try {
			strOut=new String(Base64Encoder.encode(strSrc.getBytes(encode)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strOut;
	}
	public static String encode(byte[] bytes){
		String strOut="";
		try {
			strOut=new String(Base64Encoder.encode(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strOut;
	}
	public static String decode(String strSrc,String encode){
		String strOut="";
		try {
			 strOut = new String(Base64Decoder.decodeToBytes(strSrc),encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strOut;
	}
	public static byte[] decode(String strSrc){
		byte[] bytes;
		bytes = Base64Decoder.decodeToBytes(strSrc);
		return bytes;
	}

}
