/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyUtil {
	public static List<String> getKeyList(String str) {
		Object list = null;
		String[] arr;
		if (str.contains(".")) {
			arr = str.split("\\.");
			list = Arrays.asList(arr);
		} else if (str.contains("/")) {
			if (str.startsWith("/")) {
				str = str.substring(1);
			}

			arr = str.split("\\/");
			list = Arrays.asList(arr);
		} else {
			list = new ArrayList();
			((List) list).add(str);
		}

		return (List) list;
	}

	public static String getKey(Class clz) {
		String key = clz.getName();
		if (key.contains(".")) {
			key = key.replace(".", "_");
		}

		return key;
	}
}