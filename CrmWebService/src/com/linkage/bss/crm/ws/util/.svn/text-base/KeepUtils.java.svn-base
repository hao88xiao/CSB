package com.linkage.bss.crm.ws.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * keep工具
 * 
 * @author Extendstep
 *
 */

public class KeepUtils {

	private static int randomSequence = 0;

	/**
	 * @Description 获取本次服务的标识流水
	 * @param tmnNum
	 *            终端号，需要分配
	 * @return 20渠道号 + 四位完全的随机数 +时间（毫秒级）+ 五位完全的随机数 + keep序列号（01到99）
	 */
	public static String getKeep() {
		if (randomSequence > 99) {
			randomSequence = 0;
		}
		String randomSequenceString = ValueUtil.sNull(randomSequence);
		if (ValueUtil.isNotEmpty(randomSequenceString) && randomSequenceString.length() == 1) {
			randomSequenceString = "0" + randomSequenceString;
		}
		String keep = "20" + get4RandomNumber() + getTime_yyyyMMddHHmmssSSS() + get5RandomNumber()
				+ randomSequenceString;
		randomSequence++;
		return keep;
	}

	/**
	 * 获取四位随机数
	 * 
	 * @return
	 */
	public static String get4RandomNumber() {
		String randstr = "";
		Random ran = new Random(System.currentTimeMillis());
		randstr = "" + (ran.nextInt(9000) + 1000);
		return randstr;
	}

	/**
	 * 获取五位随机数
	 * 
	 * @return
	 */
	public static String get5RandomNumber() {
		String randstr = "";
		Random ran = new Random(System.currentTimeMillis());
		randstr = "" + (ran.nextInt(90000) + 10000);
		return randstr;
	}

	/**
	 * @Description 获取yyyyMMddHHmmss格式的时间字符串
	 * @return {@link String}时间
	 */
	public static String getTime_yyyyMMddHHmmssSSS() {
		Date currentDate = new Date();
		DateFormat formater = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINESE);
		return formater.format(currentDate);
	}
}
