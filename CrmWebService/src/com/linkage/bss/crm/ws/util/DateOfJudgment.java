package com.linkage.bss.crm.ws.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 日期转换
 * @author Jon
 *
 */
public class DateOfJudgment {
	public static Map<String, Object> DateOfJudgment(String statisticalTime){
		String startTime;
		String endTime;
		Map<String,Object> map = new HashMap<String, Object>();
		String date = statisticalTime;
		int year = Integer.parseInt(date.substring(0,4));//年份
		String time = date.substring(4,6);//月份
		List<String> list = new ArrayList<String>();
		list.add("04");
		list.add("06");
		list.add("09");
		list.add("11");
		if(list.contains(time)){
			 startTime = statisticalTime +"01";
			 endTime = statisticalTime + "30";
		}else if(time.equals("02")){
			if((year%4==0 && year%100!=0) || year%400==0){
				 startTime = statisticalTime +"01";
				 endTime = statisticalTime + "29";
			}else{
				 startTime = statisticalTime +"01";
				 endTime = statisticalTime + "28";
			}
		}else{
			 startTime = statisticalTime +"01";
			 endTime = statisticalTime + "31";
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
		
	}

}
