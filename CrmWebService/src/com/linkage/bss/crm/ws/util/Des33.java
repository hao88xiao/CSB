package com.linkage.bss.crm.ws.util;


import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 3DES���ܹ�����
 *
 * @author liufeng
 * @date 2012-10-11
 */
public class Des33 {
    // ��Կ
    private final static String secretKey = "9628BBC4EC74B2091073A1B825C1D8869628BBC4EC74B209";
    // ����
    private final static byte[] keyiv = {0, 1, 2, 3, 4, 5, 6, 7};
    // �ӽ���ͳһʹ�õı��뷽ʽ
    private final static String encoding = "UTF-8";
    // ��Կ
    private static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        System.out.println(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
        }
        return ret;
    }
  /**
     * 3DES����
     *
     * @param encryptText �����ı�
     * @return
     * @throws Exception
     */
    public static String decode1(String encryptText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(hexStr2Bytes(secretKey));
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/NoPadding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.decodeBase64(encryptText.getBytes()));
        return new String(FormateByte(decryptData), encoding);
    }

    /**
     * �������ʱ���������ֽ���ȥ����<p>
     * (�˷���ֻ��ģʽ��CBCģʽ����䷽ʽΪNoPadding��ʽ�����ֽ������ �������ʹ�á�)
     *
     * @param arr �����ֽ���
     * @return �����ֽ���
     */
    public static byte[] FormateByte(byte[] arr) {

        int i = 0;
        for (; i < arr.length; i++) {
            if (arr[i] == new Byte("0")) {
                break;
            }
        }
        byte[] result = new byte[i];
        for (int j = 0; j < i; j++) {
            result[j] = arr[j];
        }
        return result;
    }

    public static String byte2Ucs2(byte[] data, int iPos, int iLen) {
        try {
            return (new String(data, iPos, iLen, "UTF-16LE")).trim();
        } catch (UnsupportedEncodingException e) {
            System.out.println("ת��UTF8ʧ��");
        }
        return "";
    }

}