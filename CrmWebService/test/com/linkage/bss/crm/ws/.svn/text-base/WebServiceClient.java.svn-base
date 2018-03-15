package com.linkage.bss.crm.ws;



import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public abstract class WebServiceClient {

	protected Object methodInvoke(Object[] params,String methodName) throws Exception {
		Service service = new Service();
		Call  call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL("http://172.19.17.152:7101/BJCSB/services/CrmService?p=6090010028"));
		call.setOperationName(methodName);
		return call.invoke(params);
	}
}
