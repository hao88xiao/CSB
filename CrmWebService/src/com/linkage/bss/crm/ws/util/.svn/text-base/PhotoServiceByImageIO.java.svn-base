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

        // base64转为jpg
        String base64String_5 = "BESE64码";
        PhotoServiceByImageIO.savePhotoByImageIO("E://temp//test.jpg", base64String_5);
    }

    /**
     * base64解码
     * 
     * @param s
     * @return String 解码后的字节数组
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
            logger.error("****** 照片解码错误************", ie);
            return null;
        }
    }

    /**
     * 保存照片
     * 
     * @param filename 照片文件名称
     * @param base64String 从接口获得照片的base64编码
     * @return
     */
    public static boolean savePhotoByImageIO(String filename,
                                             String base64String) {

        boolean f = false;
        try {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            ImageWriter writer = writers.next();
            // 设备输出源
            File file = new File(filename);
            ImageOutputStream ios = ImageIO.createImageOutputStream(file);
            writer.setOutput(ios);

            // 读入输入流
            InputStream is = new java.io.ByteArrayInputStream(getFromBASE64(base64String));
            BufferedImage src = ImageIO.read(is);
            if (src != null) {
                // 输出
                writer.write(src);
            }
            return f;
        } catch (java.io.IOException ie) {
            logger.error("****** 保存照片错误************", ie);
            return f;
        } catch (Exception ex) {
            logger.error("****** 保存照片错误************", ex);
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
            logger.error("****** 错误************", e);
        }
        return base64;
    }
}
