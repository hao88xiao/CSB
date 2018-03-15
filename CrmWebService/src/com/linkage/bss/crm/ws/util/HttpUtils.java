package com.linkage.bss.crm.ws.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class HttpUtils {

	private static final Log LOGGER = LogFactory.getLog(HttpUtils.class);

	/**
	 * 通过URL、方法、参数 、编码 获得服务端反回的字符串
	 * 
	 * @param url
	 * @param method
	 * @param params
	 * @return
	 */
	public static String openUrl(String url, String method, Map<String, String> params, String enc) {

		String response = null;

		if (method.equals("GET")) {
			url = url + "?" + encodeUrl(params);
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setReadTimeout(50000); // 设置超时时间
			if (method.equals("POST")) {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.getOutputStream().write(encodeUrl(params).getBytes("UTF-8"));
			}
			response = read(conn.getInputStream(), enc);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return response;
	}

	public static String queryJsonData(String urlStr, String jsonString) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();
		// POST请求
		OutputStreamWriter outputSW = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
		outputSW.write(jsonString);
		outputSW.flush();
		outputSW.close();

		String str = read(connection.getInputStream(), "utf8");

		// 断开连接
		connection.disconnect();
		return str;
	}

	/**
	 * 
	 * @param req
	 * @param url
	 * @return
	 */
	public static String queryData(String req, String url) {
		LOGGER.info("  request param:" + req);
		try {
			String queryJsonData = HttpUtils.queryJsonData(url, req);
			LOGGER.info("  respone content:" + queryJsonData);
			return queryJsonData;
		} catch (IOException e) {
			LOGGER.error("连接失败:" + url, e);
		}
		return null;
	}

	public static <T, M extends BaseResp> M callAsynPay(T req, Class<M> type, String url) throws IOException {
		return callAsynPay(req, null, type, url);
	}

	public static <T, M extends BaseResp> M callAsynPay(T req, String urlParam, Class<M> type, String url)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(req);
			LOGGER.info("pay asyn request param:" + jsonStr);
		} catch (JsonParseException e) {
			LOGGER.error("json解析错误！", e);
		}

		if (jsonStr != null) {
			String rtnString = null;
			// String url = null;
			StringBuilder sb = new StringBuilder();
			sb.append(url);
			if (urlParam != null) {
				sb.append("/");
				sb.append(urlParam);
			}
			url = sb.toString();

			rtnString = HttpUtils.queryJsonData(url, jsonStr);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(rtnString);
			}

			M resp = mapper.readValue(rtnString, type);
			return resp;
		}

		return null;
	}

	/**
	 * 将InputStream按一定编码读成字符串
	 * 
	 * @param in
	 * @param enc
	 * @return
	 * @throws IOException
	 */
	private static String read(InputStream in, String enc) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = null;
		if (enc != null) {
			// 按指定的编码读入流
			r = new BufferedReader(new InputStreamReader(in, enc), 1000);
		} else {
			// 按默认的编码读入
			r = new BufferedReader(new InputStreamReader(in), 1000);
		}

		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}

	/**
	 * 将HashMap parameters类型的参数封闭到URL中
	 * 
	 * @param hm
	 * @return
	 */
	public static String encodeUrl(Map<String, String> hm) {
		if (hm == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		Iterator<String> it = hm.keySet().iterator();
		while (it.hasNext()) {
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			String key = it.next();
			sb.append(key + "=" + hm.get(key));
		}
		return sb.toString();
	}

	public static String standardOrderJSON(Object req) {
		String jsonStr = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(req);
			// LOGGER.info(jsonStr);
		} catch (JsonParseException e) {
			LOGGER.error("", e);
		} catch (IOException e) {
			LOGGER.error("", e);
		}
		return jsonStr;
	}

	public static String getNoSignJsonStr(Object req) {
		String jsonStr = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(req);
			JsonNode readTree = mapper.readTree(jsonStr);
			ObjectNode onode = (ObjectNode) readTree;
			onode.remove("sign");
			jsonStr = onode.toString();

		} catch (JsonParseException e) {
			LOGGER.error("", e);
		} catch (IOException e) {
			LOGGER.error("", e);
		}
		return jsonStr;
	}

}
