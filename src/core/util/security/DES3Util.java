package core.util.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Encoder;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月23日
 * @description 3DES加密解密算法工具类
 */
public class DES3Util {
	
	private static final String DES = "3DES";
	private static final String KEY = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * @title getEncStr
	 * @description 获得一次3DES加密后的密文
	 * @param @param data
	 * @param @param key
	 * @param @return
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String getEncStr(String data) {
		byte[] encodeByte = null;
		String encodeStr = "";
		Key key = getKey();
		BASE64Encoder encoder = new BASE64Encoder();
		encodeByte = getEncCode(data.getBytes(), key);
		encodeStr = encoder.encode(encodeByte);
		return encodeStr;
	}
	
	/**
	 * 获得一次3DES解密后的明文
	 * @title getDecStr
	 * @description TODO
	 * @param @param data
	 * @param @return
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String getDecStr(String data) {
		byte[] decodeByte = null;
		String decodeStr = "";
		Key key = getKey();
		BASE64Encoder encoder = new BASE64Encoder();
		decodeByte = getDecCode(data.getBytes(), key);
		decodeStr = encoder.encode(decodeByte);
		return decodeStr;
	}
	
	/**
	 * @title getTwiceEncStr
	 * @description 获得两次3DES加密后的密文
	 * @param @param data
	 * @param @return
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String getTwiceEncStr(String data) {
		return getEncStr(getEncStr(data));
	}
	
	/**
	 * @title getTwiceDecStr
	 * @description 获得两次3DES解密后的明文
	 * @param @param data
	 * @param @return
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String getTwiceDecStr(String data) {
		return getDecStr(getDecStr(data));
	}
	
	private static byte[] getDecCode(byte[] decodeByte, Key key) {
		byte[] finalByte = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.DECRYPT_MODE, key);
			finalByte = cipher.doFinal(decodeByte);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return finalByte;
	}

	private static byte[] getEncCode(byte[] encodeByte, Key key) {
		byte[] finalByte = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			finalByte = cipher.doFinal(encodeByte);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return finalByte;
	}
	
	/**
	 * @title getKey
	 * @description 生成key
	 * @param @param param
	 * @param @return
	 * @return Key
	 * @date 2014年4月23日
	 */
	private static Key getKey() {
		Key key = null;
		try {
			KeyGenerator generator = KeyGenerator.getInstance(DES);
			generator.init(new SecureRandom(KEY.getBytes()));
			key = generator.generateKey();
			generator = null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	}

}
