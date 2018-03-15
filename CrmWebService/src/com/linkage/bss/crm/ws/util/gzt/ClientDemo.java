package com.linkage.bss.crm.ws.util.gzt;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.id5.model.qzj.Request;
import cn.id5.model.qzj.Result;

/**
 * @author pony
 * @date 2017年11月9日
 */
public class ClientDemo {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


	@Before
	public void init() {
	}

	@Test
	public void testCompare() {

		//初始化一次
		ClientOnJdkHttpConnection client = new ClientOnJdkHttpConnection();
		client.init();
		
		try {
			Request request = new Request.Builder()
					.gmsfhm("要比对的身份证号")
					.xm("要比对的姓名")
				
					.build();

			Result result = client.invoke(request);
			System.err.println(gson.toJson(result));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
