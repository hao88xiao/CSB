package com.linkage.bss.crm.ws.util.gzt;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkage.bss.crm.ws.util.gzt.ClientOnJdkHttpConnection;

public class HttpUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientOnJdkHttpConnection.class);


	public static HttpResponseData sendHttpPost(String endpoint, int timeout, String body, Map<String, String> headers)
			throws Exception {

		HttpResponseData httpResponse = new HttpResponseData();
		long startTime = System.currentTimeMillis();
		InputStream ins = null;
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		logger.debug("request url =  {} ", endpoint );
		logger.debug("request timeout =  {} ", timeout );
		logger.debug("request headers =  {} ", headers );
		logger.debug("request body =  {} ", body );
		try {
			// 创建连接
			URL url = new URL(endpoint);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "	application/json;charset=UTF-8");
			connection.setRequestProperty("Accept-Charset", "utf-8");
			if (null != headers) {
				for (Map.Entry<String, String> header : headers.entrySet()) {
					connection.setRequestProperty(header.getKey(), header.getValue());
				}
			}
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);

			connection.connect();

			// 发送 POST请求
			out = new DataOutputStream(connection.getOutputStream());
			out.write(body.getBytes("UTF-8"));
			out.flush();
			int code = connection.getResponseCode();

			httpResponse.setStatus(code);

			ins = connection.getInputStream();
			String data = readByInputStream(ins);
			httpResponse.setTime(System.currentTimeMillis() - startTime);
			httpResponse.setData(data);

			httpResponse.setAesKey( connection.getHeaderField("G-aesKey"));

			logger.debug("response getResponseCode =  {} ", code );
			logger.debug("response timeout =  {} ", httpResponse.getTime() );
			logger.debug("response data =  {} ", data );
			logger.debug("response Header =  {} ", connection.getHeaderField("G-aesKey") );
			
			return httpResponse;
		} catch (Exception e) {
			throw e;
		} finally {

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				if (ins != null) {
					ins.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static String readByInputStream(InputStream ins) throws IOException {
		byte[] by = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		int len = -1;
		while ((len = ins.read(by)) != -1) {
			bos.write(by, 0, len);
		}
		ins.close();
		return bos.toString("utf-8");

	}

}