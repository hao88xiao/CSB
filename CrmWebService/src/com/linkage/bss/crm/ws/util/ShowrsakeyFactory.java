/**
 * 
 */
package com.linkage.bss.crm.ws.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author  
 * �Ƿ���ʾ��ʱ��ӡ��乤����
 * ��ȡconfigs/csLog.properties��showLogֵ
 * showLog : 1 ��ʾ
 */
public class ShowrsakeyFactory {

	public static String isShowrsakey(String name) {
		Properties props = new Properties();
		InputStream ips = null;
		String value = null;
		try {
			ips = new BufferedInputStream(new FileInputStream("configs//rsakey.properties"));
			props.load(ips);
			value = props.getProperty(name);
			ips.close();
			return value;
		} catch (Exception e) {
		} finally {
			try {
				if (ips != null)
					ips.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

		public static void main(String[] args) {
			System.out.println(isShowrsakey("url"));
		}

}
