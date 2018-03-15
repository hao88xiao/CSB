package com.linkage.bss.crm.ws.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import net.netca.util.encoders.BadEncodeException;
import net.netca.util.encoders.Base64;
import net.netca.util.encoders.Hex;

import com.sun.crypto.provider.SunJCE;

public class DES3N {

	private String desAlgorithm = "DESede/CBC/PKCS5Padding";
	private static String desKeyAlgorithm = "DESede";

	private Key key;
	private IvParameterSpec ivSpec;
	private byte defaultIV[] = new byte[8];

	private String KEY_STR;

	private static String keyValue = "FCB5023DA5545ED8680A2D07806DBDCA6ACDCF47E08F6A2F";
	private static String vectorValue = "1,2,3,4,5,6,7,8";

	public static String encryptBy3DesAndBase64(String content) {
		try {
			if (content == null || content.trim().length() < 1) {
				return null;
			}
			DES3N desUtil = getInstance(keyValue, vectorValue);
			byte[] input;
			input = content.getBytes("UTF-8");
			byte[] output = desUtil.encryptBy3Des(input);
			output = Base64.encode(output);
			return new String(output, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptBy3DesAndBase64(String encryptContent) {
		try {
			if (encryptContent == null || encryptContent.trim().length() < 1) {
				return null;
			}
			DES3N desUtil = getInstance(keyValue, vectorValue);
			byte[] input;
			input = encryptContent.getBytes("UTF-8");
			input = Base64.decode(input);
			byte[] output = desUtil.decryptBy3Des(input);
			return new String(output, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	public static DES3N getInstance(String keyValue, String vectorValue)
			throws Exception {
		DES3N desUtil = new DES3N();
		// 3DES加解密实例初始化
		desUtil.initWithKeyAndVector(keyValue, vectorValue);
		return desUtil;
	}

	private byte[] encryptBy3Des(byte input[]) throws Exception {
		return cryptBy3Des(input, Cipher.ENCRYPT_MODE, null);
	}

	private byte[] decryptBy3Des(byte input[]) throws Exception {
		return cryptBy3Des(input, Cipher.DECRYPT_MODE, null);
	}

	private byte[] cryptBy3Des(byte input[], int cryptModel, byte iv[])
			throws Exception {
		try {
			Cipher c = Cipher.getInstance(desAlgorithm);
			c.init(cryptModel, key,
					((java.security.spec.AlgorithmParameterSpec) (ivSpec)));
			return c.doFinal(input);
		} catch (NoSuchPaddingException e) {
			throw new Exception("无法解密, e");
		} catch (InvalidAlgorithmParameterException e) {
			throw new Exception("无法解密" + desAlgorithm, e);
		} catch (IllegalBlockSizeException e) {
			throw new Exception("无法解密", e);
		} catch (BadPaddingException e) {
			throw new Exception("无法解密", e);
		}
	}

	public void initWithKeyAndVector(String keyValue, String vectorValue)
			throws Exception {
		try {
			if (keyValue != null && keyValue.length() == 48) {
				this.KEY_STR = keyValue;
			}
			if (vectorValue != null) {
				String[] defaultIVStr = vectorValue.split(",");
				if (defaultIVStr.length == 8) {
					for (int i = 0; i < 8; i++) {
						this.defaultIV[i] = (byte) Integer
								.parseInt(defaultIVStr[i].trim());
					}
				}
			}
			java.security.Security.addProvider(new SunJCE());
			java.security.Security.insertProviderAt(new SunJCE(), 1);

			this.key = KeyGenerator(KEY_STR);
			this.ivSpec = IvGenerator(defaultIV);

		} catch (InvalidKeyException e) {
			throw new Exception("无效的key");
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无效" + desAlgorithm, e);
		} catch (InvalidKeySpecException e) {
			throw new Exception("无效的key");
		} catch (Exception e) {
			throw new RuntimeException("加载SunJCE出错");
		}
	}

	private Key KeyGenerator(String KeyStr) throws InvalidKeyException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			BadEncodeException {
		if (KeyStr == null) {
			KeyStr = KEY_STR;
		}
		byte input[] = Hex.decode(KeyStr);
		DESedeKeySpec KeySpec = new DESedeKeySpec(input);
		SecretKeyFactory KeyFactory = SecretKeyFactory
				.getInstance(desKeyAlgorithm);
		return ((Key) (KeyFactory
				.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
	}

	/**
	 * 生成加密向量 对象
	 * 
	 * @param b
	 * @return synchronized
	 */
	private IvParameterSpec IvGenerator(byte[] b) {
		IvParameterSpec IV = new IvParameterSpec(b);
		return IV;
	}

	public static void main(String[] args) throws Exception {
		DES3N t = new DES3N();
		String passwdStr = t.encryptBy3DesAndBase64("123456");
		System.out.println("加密后：" + passwdStr);
		String ps = "OQaKWpFucrr08uCUzWWcaHBGxWGf7NbjPtkAhySOfPQI0xvbXpvjke5wyQZY6y2u7Sy1x436DFsoWboOtFQXRyazBAfWERSPzqAsu0obFB4VMyh3ku0+WEd7EP6E9xEg+Fa/aVv7ey5Vv/UHEWu6s92a9kfnHW9xTpxoCcQa8bWVV70WnJfiTWtEBwsN1OlUv2uzhloQZRc6eQC/QbjxsDxQ8qw34dvFcsPz/szCQ0kCMhhjCIpYTDDKTR9dxFZlGlh01dzfVFyvwodrFUid6teoFeXADHt/8NZVgFmnOwwtZZMdMaGXVGuApaXG5toFHn91sK900rBMjjscBujAYVl1V5uXa50Djpg4LjmIqWOR+lzRS/9ydCpsoIg1atQPuqwybdGR4IX13ZzDrPa2tQ55G8467JyLQkhj+9lH3ac=";
		// String
		// test1="wx5NIjW8fWi3BZAz/DO8XS3K9PuLPx95x+ZuETRaRxMjaqclt0seBOlMm5lYzPRWPaSEQqy5aiG0vYRM/dIwF7C3RGy30ICESyYeLUH05uitGyenhCYC0vg1DzD9Cwid2JAOLVbY91PUbicDfQyolOd9domNI2x1nH5Pet0hc3DXfvMRHYEv007JQNKu2AGW";
		// String test1 = "sxIgK4bAy4k=";
		// String authInfoStr1 = URLDecoder.decode(test1, "UTF-8");
		// System.out.println(authInfoStr1);
		String passwdStr1 = t.decryptBy3DesAndBase64(ps);
		System.out.println("解密后：" + passwdStr1);
	}
}

