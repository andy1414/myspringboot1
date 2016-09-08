/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.util.HashMap;
import java.util.Map;

import com.my.springboot.common.interfaces.ISerialWR;

public class BeanSerial {
	private static Map<String, ISerialWR> map = new HashMap();

	public static Map<String, ISerialWR> getMap() {
		return map;
	}

	public static void setMap(Map<String, ISerialWR> mapx) {
		map = mapx;
	}

	public static ISerialWR get(String clzName) {
		ISerialWR wr = (ISerialWR) map.get(clzName);
		if (wr == null) {
			try {
				wr = (ISerialWR) Class.forName(clzName + "WR").newInstance();
				System.out.println("wr = " + wr);
				map.put(clzName, wr);
			} catch (Exception arg2) {
				map.put(clzName,null);
			}
		}

		return wr;
	}
}