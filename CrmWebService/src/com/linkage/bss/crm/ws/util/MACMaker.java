package com.linkage.bss.crm.ws.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.ibm.ws.webservices.engine.encoding.Base64;
import com.linkage.bss.crm.ws.common.ResultCode;

/**
 * 
 * 字符串 DESede(3DES) 加密
 * 
 */

public class MACMaker {

	private static String Algorithm = "DESede"; // 定义 加密算法,可用

	// DES,DESede,Blowfish

	/**
	 * 生成密钥
	 * 
	 * @return
	 */
	public static SecretKey getKey(String key) {
		SecretKey deskey = null;
		try {
			KeyGenerator gen = KeyGenerator.getInstance(Algorithm);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			gen.init(112, secureRandom);
			deskey = gen.generateKey();
			gen = null;
			return deskey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return deskey;

	}

	/**
	 * 解密
	 * 
	 * @param key
	 * @param src
	 * @return
	 */
	public static String decrypt(String key, String src) {

		SecretKey deskey = getKey(key);
		String sourceXml = null;
		try {
			Cipher c2 = Cipher.getInstance(Algorithm + "/ECB/PKCS5Padding");
			c2.init(Cipher.DECRYPT_MODE, deskey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceXml;

	}

	/**
	 * 加密
	 * 
	 * @param key
	 * @param src
	 * @return
	 */
	public static String encode(String key, String src) {
		SecretKey deskey = getKey(key);
		String destCode = null;
		try {
			Cipher c1 = Cipher.getInstance(Algorithm + "/ECB/PKCS5Padding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] encoded = c1.doFinal(src.getBytes("UTF-8"));
			destCode = new String(Base64.encode(encoded));

		} catch (Exception e) {
			return WSUtil.buildResponse(ResultCode.PARAMETER_ERROR, "加密异常！");
		}
		return destCode;
	}

	public static void main(String[] args) {

		String key = "test";

		StringBuffer s = new StringBuffer();
		s.append("<Plain id=\"TSReq\">");
		s.append("<TransId>TSReq</TransId>");
		s.append("<RelationAcct>B23764861");
		s.append("</RelationAcct>");
		s.append("<MerchantId>100600040001</MerchantId>");
		s.append("<CardNo>098929</CardNo>");
		s.append("<PhoneNo>13404171778</PhoneNo>");
		s.append("<MerchantTransTime></MerchantTransTime>");
		s.append("<MerchantSeqNo></MerchantSeqNo>");
		s.append("<CardType>D</CardType>");
		s.append("<SignType>0</SignType>");
		s.append("<Stages>13</Stages>");
		s.append("<MinAmt>1312</MinAmt>");
		s.append("<CheckFlag>0</CheckFlag>");
		s.append("<CustName>test</CustName>");
		s.append("<CertType>1</CertType>");
		s.append("<CertNo>110102198501011234</CertNo>");
		s.append("<ChannelId>10</ChannelId>");
		s.append("</Plain>");

		System.out.println("加密前的字符串:\n" + s.toString());
		String code = encode(key, s.toString());
		System.out.println("加密后的字符串:\n" + code);

		System.out.println("加密后的字符串:\n" + decrypt(key, code));

	}

}
