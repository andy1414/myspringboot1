/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;

public class Configs {
	private static Map<String, Object> map = new ConcurrentHashMap();

	public static Map<String, Object> getMap() {
		return map;
	}

	public static Object get(String keyStr) {
		List keyList = KeyUtil.getKeyList(keyStr);
		Map tempMap = map;

		Object obj;
		for (Iterator arg3 = keyList.iterator(); arg3.hasNext(); tempMap = (Map) obj) {
			String key = (String) arg3.next();
			obj = tempMap.get(key);
			if (obj == null) {
				tempMap = null;
				return null;
			}

			if (!(obj instanceof Map)) {
				return obj;
			}
		}

		return tempMap;
	}

	public static int getIntValue(String key) {
		Integer value = Integer.valueOf(0);

		String err;
		try {
			value = Integer.valueOf("" + get(key));
		} catch (MissingResourceException arg3) {
			err = "config/*.txt, key:" + key;
			System.err.println(err);
			arg3.printStackTrace();
		} catch (Exception arg4) {
			err = "config/*.txt" + key + "=" + map.get(key);
			System.err.println(err);
			arg4.printStackTrace();
		}

		return value.intValue();
	}

	public static Map<String, Object> getMap(String key) {
		Object obj = get(key);
		return obj instanceof Map ? (Map) obj : null;
	}

	public static String getString(String key) {
		String value = "";

		String err;
		try {
			value = "" + get(key);
		} catch (MissingResourceException arg3) {
			err = "���������ļ�config/*.txt, ȱ��key:" + key;
			System.err.println(err);
			arg3.printStackTrace();
		} catch (Exception arg4) {
			err = "���������ļ�config/*.txt, ������:" + key + "=" + map.get(key);
			System.err.println(err);
			arg4.printStackTrace();
		}

		return value;
	}

	public static long getLongValue(String key) {
		Long value = Long.valueOf(0L);

		String err;
		try {
			value = Long.valueOf("" + get(key));
		} catch (MissingResourceException arg3) {
			err = "���������ļ�config/*.txt, ȱ��key:" + key;
			System.err.println(err);
			arg3.printStackTrace();
		} catch (Exception arg4) {
			err = "���������ļ�config/*.txt, ������:" + key + "=" + map.get(key);
			System.err.println(err);
			arg4.printStackTrace();
		}

		return value.longValue();
	}

	public static boolean isTrue(String key) {
		String value = "";

		String err;
		try {
			value = "" + get(key);
			return Boolean.parseBoolean(value);
		} catch (MissingResourceException arg3) {
			err = "���������ļ�config/*.txt, ȱ��key:" + key;
			System.err.println(err);
			arg3.printStackTrace();
		} catch (Exception arg4) {
			err = "���������ļ�config/*.txt, ������:" + key + "=" + map.get(key);
			System.err.println(err);
			arg4.printStackTrace();
		}

		return false;
	}
}