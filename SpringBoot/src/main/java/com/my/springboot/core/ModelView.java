package com.my.springboot.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ModelView {
	String OK = "OK";
	String FAIL = "FAIL";
	String STATUS = "status";
	String MESSAGE = "message";
	String MODEL = "model";
	String RESULT = "result";

	static Map<String, Object> toast(String message) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", "FAIL");
		map.put("message", message);
		return map;
	}

	static  Map<String, Object> model(Object obj) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", "OK");
		map.put("model", obj);
		return map;
	}

	static <T> T object(Map<String, T> map) {
		return map.remove("model");
	}

	static  Map<String, Object> view(Object obj) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", "OK");
		if (obj instanceof List) {
			map.put("result", obj);
		} else {
			ArrayList list = new ArrayList();
			list.add(obj);
			map.put("result", list);
		}

		return map;
	}

	static  Map<String, Object> view(List<Object> list) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", "OK");
		map.put("result", list);
		return map;
	}

	static Map<String, Object> view() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", "OK");
		return map;
	}

	static boolean isFail(Map<String, Object> map) {
		Object status = map.get("status");
		return status != null && status.equals("FAIL");
	}
}
