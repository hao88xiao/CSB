package com.linkage.bss.crm.ws.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetRebateAddress {

	public String getAddress(String name) {
		Properties props = new Properties();
		InputStream ips = null;
		final String PFILE = "rsakey.properties";

		try {
//			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//			String mm = this.getClass().getClassLoader().getResource(PFILE).getPath();
//			System.out.println(mm);
//			ips = classloader.getResourceAsStream(mm);
			ips = this.getClass().getClassLoader().getResourceAsStream("rsakey.properties");  
//			ips = new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream(
//					"configs//rsakey.properties"));
			props.load(ips);
			String address = props.getProperty(name);
			ips.close();
			return address;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ips != null)
					ips.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String address = new GetRebateAddress().getAddress("url");
		
		System.out.println(address);
	}


}
