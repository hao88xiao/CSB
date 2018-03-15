/**
 * 
 */
package com.linkage.bss.crm.ws.others;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author hell
 * 是否显示超时打印语句工具类
 * 读取configs/csLog.properties中showLog值
 * showLog : 1 显示
 */
public class ShowCsLogFactory {

	public static boolean isShowCsLog() {
		Properties props = new Properties();
		InputStream ips = null;
		try {
			ips = new BufferedInputStream(new FileInputStream("configs//csLog.properties"));
			props.load(ips);
			String value = props.getProperty("showLog");
			ips.close();
			return "1".equals(value);
		} catch (Exception e) {
		} finally {
			try {
				if (ips != null)
					ips.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	//	public static void main(String[] args) {
	//		System.out.println(isShowCsLog());
	//	}

}
