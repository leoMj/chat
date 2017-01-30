package com.ting.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

public class Utils {
	public static String getPara(String param, String name) {
		String[] strArr = param.split("&");
		for (String str : strArr) {
			if (str.split("=")[0].equals(name)) {
				return str.split("=")[1];
			}
			break;
		}
		return "";
	}

	public static void setCache(String cacheName, String key, Object value) {
		Cache bbsCache = Redis.use("message");
		bbsCache.set(key, value);
	}

	public static Object getCache(String cacheName, String key) {
		Cache bbsCache = Redis.use("message");
		// Object o = bbsCache.key(key);
		// System.out.println(o);
		return bbsCache.get(key);
	}

	public static String formatTime(long timeMillis) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		calendar.setTimeInMillis(timeMillis);
		int year = calendar.get(Calendar.YEAR);

		int month = calendar.get(Calendar.MONTH) + 1;
		String mToMonth = null;
		if (String.valueOf(month).length() == 1) {
			mToMonth = "0" + month;
		} else {
			mToMonth = String.valueOf(month);
		}

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dToDay = null;
		if (String.valueOf(day).length() == 1) {
			dToDay = "0" + day;
		} else {
			dToDay = String.valueOf(day);
		}

		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		String hToHour = null;
		if (String.valueOf(hour).length() == 1) {
			hToHour = "0" + hour;
		} else {
			hToHour = String.valueOf(hour);
		}

		int minute = calendar.get(Calendar.MINUTE);
		String mToMinute = null;
		if (String.valueOf(minute).length() == 1) {
			mToMinute = "0" + minute;
		} else {
			mToMinute = String.valueOf(minute);
		}

		int second = calendar.get(Calendar.SECOND);
		String sToSecond = null;
		if (String.valueOf(second).length() == 1) {
			sToSecond = "0" + second;
		} else {
			sToSecond = String.valueOf(second);
		}
		return year + "-" + mToMonth + "-" + dToDay + " " + hToHour + ":" + mToMinute + ":" + sToSecond;
	}

	public static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	public static String getGravatar(String email) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return "http://www.gravatar.com/avatar/" + hex(md.digest(email.getBytes("CP1252")));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
}
