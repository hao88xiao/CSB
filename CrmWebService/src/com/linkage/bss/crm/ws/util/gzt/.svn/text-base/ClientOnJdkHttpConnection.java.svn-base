package com.linkage.bss.crm.ws.util.gzt;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.id5.identityverify.datahandler.ClientConfig;
import cn.id5.identityverify.datahandler.DataHandler;
import com.linkage.bss.crm.ws.util.gzt.HttpResponseData;
import com.linkage.bss.crm.ws.util.gzt.HttpUtils;
import cn.id5.model.qzj.Request;
import cn.id5.model.qzj.Result;

/**
 * @author pony
 * @date 2017年11月9日
 */
public class ClientOnJdkHttpConnection {
	
	
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static final Logger logger = LoggerFactory.getLogger(ClientOnJdkHttpConnection.class);
	
	
	private ClientConfig config;
	
	public ClientOnJdkHttpConnection() {
		
	}
	
	public void init() {

		//没有配置情况下，读取配置文件
		if(config == null) {
			//identitycompare_config.json
			try {
				String jsonConfig = loadString("identitycompare_config.json");
				
				config = gson.fromJson(jsonConfig, ClientConfig.class);
				
				logger.info("init client config = {} " , jsonConfig);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	
	}
	
	public Result invoke(Request request) throws Exception {
		if(null == request) {
			throw new IllegalArgumentException("输入参数为空!");
		}
	
		DataHandler handler = new DataHandler(request,config);
		handler.processRequeset();
		Map<String,String> headers = handler.getHeaders();
		String data = handler.getPostData();
		
		HttpResponseData responseData = HttpUtils.sendHttpPost(config.getEndpoint(), config.getTimeOut(), data, headers);
		
		Map<String,String> responseHeaders = new HashMap<String,String>();
		
		responseHeaders.put("G-aesKey", responseData.getAesKey());
		handler.responseProcess(responseData.getData(), responseData.getStatus(), responseHeaders);
	
		return handler.getResult();
	}
 


	public ClientConfig getConfig() {
		return config;
	}
	public void setConfig(ClientConfig config) {
		this.config = config;
	}
	
	
	
	public static String loadString(String fileName) throws Exception  {
		InputStream inputStream = ClientOnJdkHttpConnection.class.getResourceAsStream("/" + fileName);

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");

	}
	
}
