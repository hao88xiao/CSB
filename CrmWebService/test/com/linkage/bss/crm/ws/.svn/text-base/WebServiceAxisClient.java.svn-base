package com.linkage.bss.crm.ws;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebServiceAxisClient {

	private static Log logger = LogFactory.getLog(WebServiceAxisClient.class);

	public static String webServicePost(String obj, String function, String url) {
		try {
			logger.debug(String.format("方法名:%s,入参：%s", function, obj));
			Service service = new Service();
			Call call = (Call) service.createCall();
			// 设置调用服务地址
			call.setTargetEndpointAddress(new java.net.URL(url));

			// 此处一定要配置wsdl的namespace参数http://www.hua-xia.com.cn/ZySearch
			call.setOperationName(new QName("http://service.ws.crm.bss.linkage.com/", function));
			// 此处需要配置传入参数类型与参数名称,如果未设置jax-ws则无法接受参数,会认为传入的参数为null
			call.addParameter("request", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			// 如果设置类传入参数类型,此处也需要设置返回参数类型
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			call.setUseSOAPAction(true);
			call.setSOAPActionURI(url);
			String result = (String) call.invoke(new Object[] { obj });
			logger.debug(String.format("结果：%s", result));
			return result;
		} catch (Exception e) {
			logger.debug(String.format("异常信息：%s", e));
			return e.getMessage();
		}

	}

	public static String CSBTestMethod(String request , String method ,String nicode ){
		Service service = new Service();
		Call call = null;
		try {
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			return e.getMessage();
		}
		try {
//			http://172.19.100.180:9300/BJCSB/services/CrmService
			call.setTargetEndpointAddress(new java.net.URL("http://172.19.100.180:9300/BJCSB/services/CrmService?p="+nicode));
		} catch (MalformedURLException e) {
			return e.getMessage();
		}
		call.setOperationName(method);
		String response = StringUtils.center("调用失败！",60, "*");
		try {
			response = (String) call.invoke(new Object[] { request });
		} catch (RemoteException e) {
			return e.getMessage();
		}
		return response;
	}
	
	
	
	public static String callRemote(String xml, String Url) {
		// 设置url地址
		PostMethod post = new PostMethod(Url);
		String retxml = null;
		HttpClient httpclient = new HttpClient();
		try {
			post.setRequestEntity(new StringRequestEntity(xml, "text/xml; charset=UTF-8", "UTF-8"));
			// 设置一些访问http服务的属性,如超时时间,重试次数等
			httpclient.getParams().setSoTimeout(60 * 1000); // 访问后台Http服务2秒超时
			int retryCount = 0; // 失败的时候，重试次数, 一般不重试,填0即可
			httpclient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(retryCount, false));
			// 调用后台的http服务
			int result = httpclient.executeMethod(post);
			logger.debug("返回码:" + result);
			retxml = post.getResponseBodyAsString();
			return retxml;
		} catch (Exception e) {
			logger.debug(String.format("异常信息：%s", e.getMessage()));
			return "";
		} finally {
			// 关闭连接
			post.releaseConnection();
		}
	}

}
