package core.util.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.springframework.util.Assert;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author cuipeng cuipeng.star@gmail.com
 * @date 2014年4月23日
 * @description DES加密解密辅助类
 */
public class DESUtil {
	
	private static final String DES = "DES";
	private static final String KEY = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * @title encrypt
	 * @description 根据键值进行加密
	 * @param @param data
	 * @param @return
	 * @return String
	 * @date 2014年4月23日
	 */
	public static String encrypt(String data) {
		Assert.notNull(data, "Input data must not be null");
		byte[] bt = null;
		try {
			bt = encrypt(data.getBytes(), KEY.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		String retStr = new BASE64Encoder().encode(bt);
		return retStr;
	}
	
	/**
	 * @title decrypt
	 * @description 根据键值进行解密
	 * @param @param data
	 * @param @return
	 * @return String
	 * @throws IOException 
	 * @date 2014年4月23日
	 */
	public static String decrypt(String data) throws IOException {
		Assert.notNull(data, "Input data must not be null");
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = null;
		try {
			bt = decrypt(buf, KEY.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(bt);
	}
	
	/**
	 * @title encrypt
	 * @description 根据键值进行加密
	 * @param @param data
	 * @param @param key
	 * @param @return
	 * @param @throws InvalidKeyException
	 * @param @throws NoSuchAlgorithmException
	 * @param @throws InvalidKeySpecException
	 * @param @throws NoSuchPaddingException
	 * @param @throws IllegalBlockSizeException
	 * @param @throws BadPaddingException
	 * @return byte[]
	 * @date 2014年4月23日
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		//生成一个可信任的随机数据源
		SecureRandom secRandom = new SecureRandom();
		//从原始密码数据创建DESKeySpec对象
		DESKeySpec desKeySpec = new DESKeySpec(key);
		//创建一个密钥工厂，然后用它将DESKeySpec转换成SecureKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		//Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		//用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, secRandom);
		return cipher.doFinal(data);
	}
	
	/**
	 * @title decrypt
	 * @description 根据键值进行解密
	 * @param @param data
	 * @param @param key
	 * @param @return
	 * @param @throws Exception
	 * @return byte[]
	 * @date 2014年4月23日
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		//生成一个可信任的随机数据源
		SecureRandom secRandom = new SecureRandom();
		//从原始密码数据创建DESKeySpec对象
		DESKeySpec desKeySpec = new DESKeySpec(key);
		//创建一个密钥工厂，然后用它将DESKeySpec转换成SecureKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		//Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		//用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, secretKey, secRandom);
		return cipher.doFinal(data);
	}

}
