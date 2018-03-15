package com.linkage.bss.crm.ws.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PhotoServiceByImageIO {

    private static final Logger logger = Logger.getLogger(PhotoServiceByImageIO.class);

    public static void main(String[] args) {

        // base64תΪjpg
        String base64String_5 = "BESE64��";
        PhotoServiceByImageIO.savePhotoByImageIO("E://temp//test.jpg", base64String_5);
    }

    /**
     * base64����
     * 
     * @param s
     * @return String �������ֽ�����
     */
    public static byte[] getFromBASE64(String s) {

        BASE64Decoder decoder;
        if (s == null)
            return null;
        decoder = new BASE64Decoder();
        try {
            byte b[] = decoder.decodeBuffer(s);
            return b;
        } catch (java.io.IOException ie) {
            logger.error("****** ��Ƭ�������************", ie);
            return null;
        }
    }

    /**
     * ������Ƭ
     * 
     * @param filename ��Ƭ�ļ�����
     * @param base64String �ӽӿڻ����Ƭ��base64����
     * @return
     */
    public static boolean savePhotoByImageIO(String filename,
                                             String base64String) {

        boolean f = false;
        try {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();
            // �豸���Դ
            File file = new File(filename);
            ImageOutputStream ios = ImageIO.createImageOutputStream(file);
            writer.setOutput(ios);

            // ����������
            InputStream is = new java.io.ByteArrayInputStream(getFromBASE64(base64String));
            BufferedImage src = ImageIO.read(is);
            if (src != null) {
                // ���
                writer.write(src);
            }
            return f;
        } catch (java.io.IOException ie) {
            logger.error("****** ������Ƭ����************", ie);
            return f;
        } catch (Exception ex) {
            logger.error("****** ������Ƭ����************", ex);
            return f;
        } finally {
        }
    }

    public static String getBase64(String filename) {

        String base64 = null;
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            base64 = encoder.encodeBuffer(FileUtils.readFileToByteArray(new File(filename)));
        } catch (IOException e) {
            logger.error("****** ����************", e);
        }
        return base64;
    }
}
