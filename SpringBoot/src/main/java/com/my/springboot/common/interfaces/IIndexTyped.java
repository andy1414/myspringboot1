/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.common.interfaces;

import java.util.Map;

public interface IIndexTyped {
	Map<String, String> getTypeMap();

	String getKeyOne();

	String getType();

	long getId();

	void setKeyOne(String arg0);

	void setType(String arg0);

	void setId(long arg0);

	static String getKeyOne(String type, Object key) {
		return type + "_" + key.toString();
	}

	static String getKeyOne(String type, long key) {
		return type + "_" + key;
	}
}