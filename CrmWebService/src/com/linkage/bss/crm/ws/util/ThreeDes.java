package com.linkage.bss.crm.ws.util;


import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ThreeDes {

	private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
    
    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
    	
       try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //加密
            Cipher c1 = Cipher.getInstance(Algorithm+"/ECB/NoPadding");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static String decryptMode(String key_str, String src_hex) {
    	
    	byte[] key_byte = hexStringToByte(key_str);
    	byte[] src = hexStringToByte(src_hex);
    	
    	System.out.println( "decryptMode  src_len=" + src.length + "  src=" + src[0] + " " + src[1] + " " + src[2]);
    	try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(key_byte, Algorithm);

            //解密
            Cipher c1 = Cipher.getInstance(Algorithm+"/ECB/NoPadding");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            byte[] decrypt_byte = c1.doFinal(src);
            if (decrypt_byte != null){
            	return EncodeUtf8ByteToString(decrypt_byte);
            }
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
    	
    	try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //解密
            Cipher c1 = Cipher.getInstance(Algorithm+"/ECB/NoPadding");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    
    private static String EncodeUtf8ByteToString(byte[] buffer)  
    {  
        int count = 0;  
        int index = 0;  
        byte a = 0;  
        int utfLength = buffer.length;  
        char[] result = new char[utfLength];  

        while (count < utfLength)  
        {  
        	if (buffer[count] == 0)			//去掉  加密数据前不足8字节补加的0x00 
        	{	
        		break;
        	}
            if ((result[index] = (char)buffer[count++]) < 0x80)  
            {  
                index ++;  
            }
            else if (((a = (byte)result[index]) & 0xE0) == 0xC0)  
            {  
                if (count >= utfLength)  
                {  
                    break;//throw new IOException("Invalid UTF-8 encoding found, start of two byte char found at end.");  
                }  

                byte b = buffer[count++];  
                if ((b & 0xC0) != 0x80)  
                {  
                	break;
                }  

                result[index ++] = (char)(((a & 0x1F) << 6) | (b & 0x3F));  

            }  
            else if ((a & 0xF0) == 0xE0)  
            {  

                if (count + 1 >= utfLength)  
                {  
                	break;
                }  

                byte b = buffer[count++];  
                byte c = buffer[count++];  
                if (((b & 0xC0) != 0x80) || ((c & 0xC0) != 0x80))  
                {  
                	break;
                }  

                result[index ++] = (char)(((a & 0x0F) << 12) |  
                                          ((b & 0x3F) << 6) | (c & 0x3F));  

            }  
            else  
            {  
                break;  
            }  
        }  
        return String.valueOf(result, 0, index);//new String(result, 0, index); ;
    }
    private static byte HexToByte(char hex1, char hex2)
    {
	    byte result = 0;
	    for(int i = 0;i < 2;i++)
	    {
		    char c;
		    if (i == 0)
		    	c = hex1;
		    else
		    	c = hex2;
		    byte b = 0;
		    switch (c)
		    {
			    case '0':
			    case '1':
			    case '2':
			    case '3':
			    case '4':
			    case '5':
			    case '6':
			    case '7':
			    case '8':
			    case '9':
				    b = (byte)(c - '0');
				    break;
			    case 'A':
			    case 'B':
			    case 'C':
			    case 'D':
			    case 'E':
			    case 'F':
				    b = (byte)(10 + c - 'A');
				    break;
			    case 'a':
			    case 'b':
			    case 'c':
			    case 'd':
			    case 'e':
			    case 'f':
				    b = (byte)(10 + c - 'a');
				    break;
		    }
		    if (i == 0)
		    {
		    	b = (byte)(b * 16);
		    }
		    result += b;
	    }
	    return result;
    }
    
    private static byte[] hexStringToByte(String hex_str){
    
    	byte[] data_byte = new byte[hex_str.length()/2];
    	int byte_len = 0;
    	for (int i = 0;i < hex_str.length();i += 2){
    		data_byte[byte_len ++] = HexToByte(hex_str.charAt(i),hex_str.charAt(i+1));
    	}
    	return data_byte;
    }
    
    
    
     public static void main(String[] args) {
//    	byte[] test_3des_key = {			//mac  dc:2c:26:54:54:54
//    			(byte)0x08 ,0x37 ,0x4e ,(byte)0xff ,(byte)0x1c ,0x12 ,(byte)0x28 ,(byte)0x89,
//    			(byte)0x86 ,(byte)0xa2 ,(byte)0xfb ,(byte)0xcd ,(byte)0xa2 ,0x78 ,(byte)0x89 ,(byte)0xc1,
//    			(byte)0x08 ,0x37 ,0x4e ,(byte)0xff ,(byte)0x1c ,0x12 ,(byte)0x28 ,(byte)0x89};
    	String test_3des_key = "08374eff1c12288986a2fbcda27889c108374eff1c122889";
    	String s = "f2874194ce129fe845fce617e78db6b81881cc1f66712c96e167f1b016481ad365443472a9ca21db237153b3ce40e0c195be3775eac553570d101222a3c68dedd29f68dcc515e9e52fd18ca1090b8c0966bd78b67ef65b0d299f41d6692673b5d9a9a7b966325d847d581f3b496b0e32896e5a9f140eb53972aeddb9b273cd00cdf0d24876c01002ed7a937e9d691b50d7829f1f29612f5e8fa10a176d6523e8a75954e6017477c037d4de608db073a81881cc1f66712c96682e22519b508a2df6d99c32d9b59a03e532a46782eec0d902295a60822f29badc768c7957c1a87c6a3ceb164d3f85a70b285d825990d77d053768269bd3e6bcbd08d25af6f59e3b465fba15992209b4080288f5b3654d3b0a09b2222656cccde1b30f2cbb279684df860d3eeed205827507381578591e16bc7b8da2330fb2a8e7075f94e7795319";
    	String encodeUtf8ByteToString = ThreeDes.decryptMode(test_3des_key,s);
    	
    	
    	
//    	String encodeUtf8ByteToString = ThreeDes.EncodeUtf8ByteToString(decryptMode);
    	System.out.println(encodeUtf8ByteToString);
	}
}