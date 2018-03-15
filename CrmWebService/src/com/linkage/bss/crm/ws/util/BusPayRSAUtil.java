package com.linkage.bss.crm.ws.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;

/**
 * RSA算法
 * 
 * @author xiongdazhuang
 */
public class BusPayRSAUtil {

	private static final Logger LOG = Logger.getLogger(BusPayRSAUtil.class);

	public static void main(String[] args) {
		KeyPair keyPair = CipherUtil.rsa_keyPair_generated(1024);
		RSAPrivateCrtKey privateCrtKey = (RSAPrivateCrtKey) keyPair.getPrivate();
		//将此生成的密钥交予 翼支付+  
		System.out.println("modulus:" + privateCrtKey.getModulus().toString(16));
		
		//以下为第三方自己保留
		System.out.println("primeP:" + privateCrtKey.getPrimeP().toString(16));
		System.out.println("primeQ:" + privateCrtKey.getPrimeQ().toString(16));
		System.out.println("primeExponentP:" + privateCrtKey.getPrimeExponentP().toString(16));
		System.out.println("primeExponentQ:" + privateCrtKey.getPrimeExponentQ().toString(16));
		System.out.println("crtCoefficient:" + privateCrtKey.getCrtCoefficient().toString(16));
		System.out.println("privateExponent:" + privateCrtKey.getPrivateExponent().toString(16));

	}

	/**
	 * 总长度+第一个数据长度+第一个数据+。。。。+第n个数据的长度+第n个数据 ，长度总是占4个长度
	 * 
	 * @param queryData
	 * @return
	 */
	public static List<String> getValByLen(String queryData) {
		Assert.hasLength(queryData);
		int numlen = 4;
		int lastLen = 0;
		int queryDataLen = Integer.parseInt(queryData.substring(0, numlen), 16);
		if (LOG.isDebugEnabled()) {
			LOG.debug("queryData total len:" + queryDataLen);
		}
		lastLen += numlen;

		List<String> res = new ArrayList<String>();
		while (lastLen < queryDataLen) {
			int valueLen = Integer.parseInt(queryData.substring(lastLen, lastLen + numlen), 16);
			if (LOG.isDebugEnabled()) {
				LOG.debug("value len:" + valueLen);
			}
			lastLen += numlen;
			String val = queryData.substring(lastLen, lastLen + valueLen);
			res.add(val);
			if (LOG.isInfoEnabled()) {
				LOG.info(val);
			}

			lastLen += valueLen;
		}
		return res;
	}

	// ===构造两字节，十六进制的数据
	public static String buildLen(int len) {
		String hex = Integer.toHexString(len);
		if (hex.length() == 1) {
			hex = "000" + hex;
		} else if (hex.length() == 2) {
			hex = "00" + hex;
		} else if (hex.length() == 3) {
			hex = "0" + hex;
		}
		return hex;
	}

	/**
	 * get rsa public key and private key from map return hex data std mode
	 * 
	 * @return map:
	 */
	public static void generate_rsa_std(KeyPair keyPair, Map<String, String> map) {

		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// public key
		BigInteger public_key_exponent = publicKey.getPublicExponent();
		BigInteger public_key_modules = publicKey.getModulus();
		// output
		map.put(CipherContants.RSA_Name_Public_Modulus, public_key_modules.toString(16));
		map.put(CipherContants.RSA_Name_Public_Exponent, public_key_exponent.toString(16));
		// private key
		BigInteger private_key_modules = privateKey.getModulus();
		BigInteger private_key_exponent = privateKey.getPrivateExponent();
		// output
		map.put(CipherContants.RSA_Name_Private_Modulus, private_key_modules.toString(16));
		map.put(CipherContants.RSA_Name_Private_Exponent, private_key_exponent.toString(16));
	}

	/**
	 * RSAKeySpec init rsa private key crt and rsa private key std and rsa
	 * public key
	 * 
	 * @param type
	 *            【RSA_KeySpec_PubKey =
	 *            1;RSA_KeySpec_PriKey_Std=2;RSA_KeySpec_PriKey_Crt=3;】
	 * @param pri_modulus
	 * @param pri_exponent
	 * @param p
	 * @param q
	 * @param dp
	 * @param dq
	 * @param qinv
	 * @param pub_modulus
	 * @param pub_exponent
	 * @return KeySpec[RSAPublicKeySpec、RSAPrivateCrtKeySpec、RSAPrivateKeySpec]
	 */
	private static KeySpec rsa_init_pk(int type, String pri_modulus, String pri_exponent, String p, String q, String dp,
			String dq, String qinv, String pub_modulus, String pub_exponent) {

		BigInteger pri_p = null;
		BigInteger pri_q = null;
		BigInteger pri_dp = null;
		BigInteger pri_dq = null;
		BigInteger pri_qinv = null;
		BigInteger pri_exponent_bigInteger = null;
		BigInteger pri_modulus_bigInteger = null;
		BigInteger pub_exponent_bigInteger = null;
		BigInteger pub_modulus_bigInteger = null;
		if (type == CipherContants.RSA_KeyType_PriKey_Crt) {
			// private key crt
			if (dp == null || dq == null || pri_exponent == null || pri_modulus == null || p == null || q == null
					|| qinv == null || pub_exponent == null) {
				return null;
			}
			pri_p = new BigInteger(p, 16);
			pri_q = new BigInteger(q, 16);
			pri_dp = new BigInteger(dp, 16);
			pri_dq = new BigInteger(dq, 16);
			pri_qinv = new BigInteger(qinv, 16);
			pri_exponent_bigInteger = new BigInteger(pri_exponent, 16);
			pri_modulus_bigInteger = new BigInteger(pri_modulus, 16);
			pub_exponent_bigInteger = new BigInteger(pub_exponent, 16);
			RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = new RSAPrivateCrtKeySpec(pri_modulus_bigInteger,
					pub_exponent_bigInteger, pri_exponent_bigInteger, pri_p, pri_q, pri_dp, pri_dq, pri_qinv);
			return rsaPrivateCrtKeySpec;
		} else if (type == CipherContants.RSA_KeyType_PubKey) {
			// public key
			if (pub_exponent == null || pub_modulus == null) {
				return null;
			}
			pub_exponent_bigInteger = new BigInteger(pub_exponent, 16);
			pub_modulus_bigInteger = new BigInteger(pub_modulus, 16);
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(pub_modulus_bigInteger, pub_exponent_bigInteger);
			return rsaPublicKeySpec;
		} else if (type == CipherContants.RSA_KeyType_PriKey_Std) {
			// private key std
			if (pri_modulus == null || pri_exponent == null) {
				return null;
			}
			pri_exponent_bigInteger = new BigInteger(pri_exponent, 16);
			pri_modulus_bigInteger = new BigInteger(pri_modulus, 16);
			RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(pri_modulus_bigInteger,
					pri_exponent_bigInteger);
			return rsaPrivateKeySpec;
		} else {
			return null;
		}
	}

	public static String byteArrayToString(byte[] indata) {

		int g_RespLen = indata.length;
		byte[] g_Response = indata;

		int m = 0;
		String g_InfoString = "";

		while (m < g_RespLen) {
			if ((g_Response[m] & 0xF0) == 0x00) {
				g_InfoString += '0' + Integer.toHexString((short) (0x00FF & g_Response[m]));
			} else {
				g_InfoString += Integer.toHexString((short) (0x00FF & g_Response[m]));
			}
			m++;
		}

		return g_InfoString.toUpperCase();
	}

	/**
	 * use [rsa private key crt ] or [ras private key std] signature
	 * 
	 * @param data
	 * @param alg
	 *            "SHA1WithRSA"
	 *            SHA1WithRSA、MD2withRSA、MD5withRSA、SHA1withRSA、SHA256withRSA
	 *            、SHA384withRSA、SHA512withRSA
	 * @param prikey_type
	 *            :[RSA_KeyType_PriKey_Std=2] [RSA_KeyType_PriKey_Crt=3]
	 * @param pri_modulus
	 * @param pri_exponent
	 * @param dp
	 * @param dq
	 * @param p
	 * @param q
	 * @param qinv
	 * @param pub_exponents
	 * @return
	 */
	public static String rsaPrikeySignature(byte[] data) {
		LoadConfigUtil rsaConfig = LoadConfigUtil.getInstance();
		String RSA_MODULU = rsaConfig.getConfigItem("RSA_MODULU");
		String pri_p = rsaConfig.getConfigItem("PRI_P");
		String pri_q = rsaConfig.getConfigItem("PRI_Q");
		String pri_dp = rsaConfig.getConfigItem("PRI_DP");
		String pri_dq = rsaConfig.getConfigItem("PRI_DQ");
		String pri_qinv = rsaConfig.getConfigItem("PRI_QINV");
		String pri_exponet = rsaConfig.getConfigItem("PRI_EXPONET");
		return rsaPrikeySignature(data, RSA_MODULU, pri_p, pri_q, pri_dp, pri_dq, pri_qinv, pri_exponet);
	}

	public static String rsaPrikeySignatureByDb(byte[] data, CumRSA rsa) {
		String RSA_MODULU = rsa.getRsaModulu();
		String pri_p = rsa.getPriP();
		String pri_q = rsa.getPriQ();
		String pri_dp = rsa.getPriDp();
		String pri_dq = rsa.getPriDq();
		String pri_qinv = rsa.getPriQinv();
		String pri_exponet = rsa.getPriExponet();
		return rsaPrikeySignature(data, RSA_MODULU, pri_p, pri_q, pri_dp, pri_dq, pri_qinv, pri_exponet, "MD5withRSA");
	}

	public static String rsaPrikeySignature(byte[] data, String RSA_MODULU, String pri_p, String pri_q, String pri_dp,
			String pri_dq, String pri_qinv, String pri_exponet) {
		return rsaPrikeySignature(data, RSA_MODULU, pri_p, pri_q, pri_dp, pri_dq, pri_qinv, pri_exponet, "SHA1WITHRSA");

	}

	public static String rsaPrikeySignature(byte[] data, String RSA_MODULU, String pri_p, String pri_q, String pri_dp,
			String pri_dq, String pri_qinv, String pri_exponet, String alg) {

		int prikey_type = 3;
		String pub_exponet = "010001";

		PrivateKey privateKey = null;
		RSAPrivateCrtKeySpec rsaPrivateCrtKeySpec = null;// crt
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		rsaPrivateCrtKeySpec = (RSAPrivateCrtKeySpec) rsa_init_pk(prikey_type, RSA_MODULU, pri_exponet, pri_p, pri_q,
				pri_dp, pri_dq, pri_qinv, null, pub_exponet);
		try {
			privateKey = keyFactory.generatePrivate(rsaPrivateCrtKeySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		//
		Signature signature = null;
		try {
			signature = Signature.getInstance(alg);
			signature.initSign(privateKey);
			signature.update(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

		byte[] resp = null;
		try {
			resp = signature.sign();

			// byteArrayToString.
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return BusPayRSAUtil.byteArrayToString(resp);
	}

	public static boolean verifySign(final String data, final String signature, String pubModulus) {
		String alg = "MD5withRSA";
		String pubExponent = "010001";
		try {
			return rsa_pubkey_verify(data.getBytes("UTF-8"), CipherUtil.decode(signature), alg, pubModulus,
					pubExponent);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verifySign(final Object obj, final String signature, String pubModulus) {
		String alg = "SHA1WITHRSA";
		String pubExponent = "010001";
		return rsa_pubkey_verify(getSignStrByObj(obj).getBytes(), CipherUtil.decode(signature), alg, pubModulus,
				pubExponent);
	}

	public static boolean verifyOfMD5withRSA(final Map obj, final String signature, String pubModulus) {
		String alg = "MD5withRSA";
		String pubExponent = "010001";
		try {
			return rsa_pubkey_verify(getSignStrByMap(obj).getBytes("UTF-8"), CipherUtil.decode(signature), alg,
					pubModulus, pubExponent);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean verifyOfMD5withRSA1(final Map obj, final String signature, String pubModulus) {
		String alg = "MD5withRSA";
		String pubExponent = "010001";
		try {
			return rsa_pubkey_verify(getSignStrByMap1(obj).getBytes("UTF-8"), CipherUtil.decode(signature), alg,
					pubModulus, pubExponent);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean verifyOfMD5withRSA(final Object obj, final String signature, String pubModulus) {
		String alg = "MD5withRSA";
		String pubExponent = "010001";
		try {
			return rsa_pubkey_verify(getSignStrByObj(obj).getBytes("UTF-8"), CipherUtil.decode(signature), alg,
					pubModulus, pubExponent);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 参数之间不用&连接
	 * @param reqObj
	 * @return
	 */
	public static String getSign(final Object reqObj, CumRSA cumRsa) {
		try {
			return rsaPrikeySignatureByDb(getSignStrByObjForRSA(reqObj).getBytes("UTF-8"), cumRsa).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 参数之间用&连接
	 * @param reqObj
	 * @return
	 */
	public static String getSign1(final Object reqObj, CumRSA cumRsa) {
		try {
			return rsaPrikeySignatureByDb(getSignStrByObj(reqObj).getBytes("UTF-8"), cumRsa).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getSign(final String signStr, CumRSA cumRsa) {
		return rsaPrikeySignatureByDb(signStr.getBytes(), cumRsa).toUpperCase();
	}

	/**
	 * @param reqObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getSignStrByObj(final Object reqObj) {
		if (reqObj == null) {
			LOG.error("参数不能为空");
			return null;
		}
		String signStr = "";
		try {
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> map = om.convertValue(reqObj, Map.class);
			List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
			// 排序
			Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
				public int compare(final Map.Entry<String, Object> o1, final Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			StringBuffer buffer = new StringBuffer();
			for (Map.Entry<String, Object> maEntry : list) {
				Object val = maEntry.getValue();
				String key = maEntry.getKey();
				if ("sign".equals(key) || "code".equals(key) || "content".equals(key) || val == null) {
					continue;
				}
				buffer.append(key).append("=").append(val).append("&");
			}
			String str = buffer.toString();
			if (buffer.length() > 1) {
				signStr = str.substring(0, str.length() - 1);
			}
			LOG.info("签名明文：" + signStr);

			String md5Str = MD5.getMD5(signStr.getBytes("UTF-8"));
			LOG.info("对需要签名明文进行MD5摘要:" + md5Str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return signStr;
	}

	@SuppressWarnings("unchecked")
	public static String getSignStrByMap(final Map map) {
		if (map == null) {
			LOG.error("参数不能为空");
			return null;
		}
		String signStr = "";
		try {
			List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
			// 排序
			Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
				public int compare(final Map.Entry<String, Object> o1, final Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			StringBuffer buffer = new StringBuffer();
			for (Map.Entry<String, Object> maEntry : list) {
				Object val = maEntry.getValue();
				String key = maEntry.getKey();
				if ("sign".equals(key) || "code".equals(key) || "content".equals(key) || val == null) {
					continue;
				}
				buffer.append(key).append("=").append(val).append("&");
			}
			String str = buffer.toString();
			if (buffer.length() > 1) {
				signStr = str.substring(0, str.length() - 1);
			}
			LOG.info("签名明文：" + signStr);

			String md5Str = MD5.getMD5(signStr.getBytes("UTF-8"));
			LOG.info("对需要签名明文进行MD5摘要:" + md5Str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return signStr;
	}

	@SuppressWarnings("unchecked")
	public static String getSignStrByMap1(final Map map) {
		if (map == null) {
			LOG.error("参数不能为空");
			return null;
		}
		String signStr = "";
		try {
			List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
			// 排序
			Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
				public int compare(final Map.Entry<String, Object> o1, final Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			StringBuffer buffer = new StringBuffer();
			for (Map.Entry<String, Object> maEntry : list) {
				Object val = maEntry.getValue();
				String key = maEntry.getKey();
				if ("sign".equals(key) || "code".equals(key) || "content".equals(key) || val == null) {
					continue;
				}
				buffer.append(key).append("=").append(val);
			}
			String str = buffer.toString();
			if (buffer.length() > 1) {
				signStr = str.substring(0, str.length() - 1);
			}
			LOG.info("签名明文：" + signStr);

			String md5Str = MD5.getMD5(signStr.getBytes("UTF-8"));
			LOG.info("对需要签名明文进行MD5摘要:" + md5Str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return signStr;
	}
	
	/**
	 * @param reqObj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getSignStrByObjForRSA(final Object reqObj) {
		if (reqObj == null) {
			LOG.error("参数不能为空");
			return null;
		}
		String signStr = "";
		try {
			ObjectMapper om = new ObjectMapper();
			Map<String, Object> map = om.convertValue(reqObj, Map.class);
			List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
			// 排序
			Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
				public int compare(final Map.Entry<String, Object> o1, final Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			StringBuffer buffer = new StringBuffer();
			for (Map.Entry<String, Object> maEntry : list) {
				Object val = maEntry.getValue();
				String key = maEntry.getKey();
				if ("sign".equals(key) || "code".equals(key) || "content".equals(key) || val == null) {
					continue;
				}
				buffer.append(key).append("=").append(val);
			}

			signStr = buffer.toString();
			LOG.info("签名明文：" + signStr);

			String md5Str = MD5.getMD5(signStr.getBytes("UTF-8"));
			LOG.info("对需要签名明文进行MD5摘要:" + md5Str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return signStr;
	}

	/**
	 * rsa public key validate sign
	 * 
	 * @param data
	 * @param bsignature
	 * @param alg
	 *            "SHA1WithRSA"
	 * @param pub_modulus
	 * @param pub_exponent
	 * @return
	 */
	public static boolean rsa_pubkey_verify(byte[] data, byte[] bsignature, String alg, String pub_modulus,
			String pub_exponent) {
		PublicKey publicKey = null;
		RSAPublicKeySpec rsaPublicKeySpec = (RSAPublicKeySpec) rsa_init_pk(CipherContants.RSA_KeyType_PubKey, null,
				null, null, null, null, null, null, pub_modulus, pub_exponent);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		try {
			publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		Signature signature = null;
		boolean b = false;
		try {
			signature = Signature.getInstance(alg);
			signature.initVerify(publicKey);
			signature.update(data);
			b = signature.verify(bsignature);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

	/**
	 * 数据转换 eg 0.01 -> 000000000001
	 * 
	 * @param transAmount
	 * @return
	 */
	public static String amount2Format(String transAmount) {
		int i = 0;

		while (i < transAmount.length()) {
			if (transAmount.charAt(i) == '.') {
				if ((transAmount.length() - i - 1) == 2) {
					break;
				} else if ((transAmount.length() - i - 1) == 1) {
					transAmount += '0';
					break;
				} else if ((transAmount.length() - i - 1) == 0) {
					transAmount += "00";
					break;
				}
			}
			i++;
		}
		if (i == transAmount.length()) {
			// amount = transAmount + '.' + "00";
			// no decimal point
			transAmount += "00";
			while (transAmount.length() < 12) {
				transAmount = '0' + transAmount;
			}
		} else {
			// amount = transAmount;
			// get rid of decimal point
			transAmount = transAmount.substring(0, i) + transAmount.substring(i + 1, transAmount.length());
			while (transAmount.length() < 12) {
				transAmount = '0' + transAmount;
			}
		}

		return transAmount;
	}

	public static String[] getDataByLen(String queryData) {

		return null;
	}

}
