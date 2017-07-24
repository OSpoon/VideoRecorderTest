package com.n22.util.encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * java版3DES加解密 加密选项：DESede/ECB/PKCS5Padding
 * 
 * @author mac
 *
 */
public class ThreeDES {

	/**
	 * 将数据使用3EDS算法加密，加密后使用Base64编码
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encode(String message, String key) throws Exception {
		if(key.length() != 24){
			throw new Exception("密钥位数不是24位");
		}
		String algorithm = "DESede/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(algorithm);
		byte[] keybyte = key.getBytes("UTF-8");
		DESedeKeySpec desKeySpec = new DESedeKeySpec(keybyte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		String result = Base64Tool.encode(cipher.doFinal(message.getBytes("UTF-8")));
		return result;
	}

	/**
	 * 将数据使用3EDS算法加密，加密后使用Base64编码
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encode(byte[] bytes, String key) throws Exception {
		if(key.length() != 24){
			throw new Exception("密钥位数不是24位");
		}
		String algorithm = "DESede/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(algorithm);
		byte[] keybyte = key.getBytes("UTF-8");
		DESedeKeySpec desKeySpec = new DESedeKeySpec(keybyte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		String result = Base64Tool.encode(cipher.doFinal(bytes));
		return result;
	}

	/**
	 * 将数据使用3EDS算法解密，解密前的数据为Base64编码
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decode(String desStr, String key) throws Exception {
		if(key.length() != 24){
			throw new Exception("密钥位数不是24位");
		}
		String algorithm = "DESede/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(algorithm);
		byte[] keybyte = key.getBytes("UTF-8");
		DESedeKeySpec desKeySpec = new DESedeKeySpec(keybyte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] bytes = Base64Tool.decode(desStr);
		byte[] decodeBytes = cipher.doFinal(bytes);

		String result = new String(decodeBytes, "UTF-8");
		return result;
	}

	/**
	 * 将数据使用3EDS算法解密，解密前的数据为Base64编码
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decode(byte[] bytes, String key) throws Exception {
		if(key.length() != 24){
			throw new Exception("密钥位数不是24位");
		}
		String algorithm = "DESede/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(algorithm);
		byte[] keybyte = key.getBytes("UTF-8");
		DESedeKeySpec desKeySpec = new DESedeKeySpec(keybyte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decodeBytes = cipher.doFinal(bytes);

		String result = new String(decodeBytes, "UTF-8");
		return result;
	}

	/**
	 * 将数据使用3EDS算法解密，解密前的数据为Base64编码
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decodeToBytes(byte[] bytes, String key) throws Exception {
		if(key.length() != 24){
			throw new Exception("密钥位数不是24位");
		}
		String algorithm = "DESede/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(algorithm);
		byte[] keybyte = key.getBytes("UTF-8");
		DESedeKeySpec desKeySpec = new DESedeKeySpec(keybyte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decodeBytes = cipher.doFinal(bytes);
		return decodeBytes;
	}

	public static void main(String[] args) throws Exception {
		String message = "汉字_110_ABC";
		String key = "ABCD12345678901234567890";
		String desStr = ThreeDES.encode(message, key);
		String message2 = ThreeDES.decode(desStr, key);
		System.out.println("源码：" + message);
		System.out.println("密钥：" + key);
		System.out.println("密码:" + desStr);
		System.out.println("解码:" + message2);
	}
}