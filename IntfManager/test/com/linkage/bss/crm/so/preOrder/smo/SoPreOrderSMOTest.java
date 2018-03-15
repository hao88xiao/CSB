package com.linkage.bss.crm.so.preOrder.smo;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.linkage.bss.BaseTest;
import com.linkage.bss.commons.util.JsonUtil;
import com.linkage.bss.crm.so.preOrder.smo.ISoPreOrderSMO;
import com.linkage.bss.crm.so.prepare.smo.ISoPrepareSMO;

public class SoPreOrderSMOTest extends BaseTest{
//	private  ISoPrepareSMO soPrepareSMO;
//	private  ISoPreOrderSMO SoPreOrderSMO;
//	@Before
//	public void init() {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/intfManager-spring-all.xml");
//		soPrepareSMO = (ISoPrepareSMO) ctx.getBean("soManager.soPrepareSMO");
//		SoPreOrderSMO = (ISoPreOrderSMO) ctx.getBean("soManager.SoPreOrderSMO");
//	}
//	
//	//³·µ¥
//	@Test
//	public void testReleaseCartByOlIdForPrepare(){
//		Map<String , String> map = new HashMap<String , String>();
//		map.put("olId", "510000235957");
//		map.put("areaId", "45101");
//		map.put("staffId", "1001");
//		map.put("channelId", "51000000");
//		String result = soPrepareSMO.releaseCartByOlIdForPrepare(map);
//		JSONObject oj = new JSONObject();
//		oj.element("result", result);
//		String resString=JsonUtil.getJsonString(oj.toString());
//		System.out.printf(resString);
//		
//	}
//	
//	//×ªÕý
//	@Test
//	public void testCommitPreOrderInfo(){
//		JSONObject jsonParams = new JSONObject();
//		jsonParams.put("olId", "510000235957");
//		JSONObject returnObj = SoPreOrderSMO.commitPreOrderInfo(jsonParams);
//		System.out.printf(returnObj.toString());
//	}
//	

}
