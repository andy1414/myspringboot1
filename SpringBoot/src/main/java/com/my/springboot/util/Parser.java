/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.my.springboot.common.BeanElement;
import com.my.springboot.common.interfaces.Persistence;

public class Parser {
	private static final Map<Class, Parsed> map = new ConcurrentHashMap();
	private static final Map<Class, ReflectionCache> cacheMap = new ConcurrentHashMap();

	public static void put(Class clz, Parsed parsed) {
		map.put(clz, parsed);
	}

	public static Parsed get(Class clz) {
		Parsed parsed = (Parsed) map.get(clz);
		if (parsed == null) {
			parse(clz);
			parsed = (Parsed) map.get(clz);
		}

		return parsed;
	}

	public static void parse(Class clz) {
		List elementList = BeanUtilX.getElementList(clz);
		Parsed parsed = new Parsed(clz);
		parsed.setBeanElementList(elementList);
		BeanUtilX.parseKey(parsed, clz);
		Persistence p = (Persistence) clz.getAnnotation(Persistence.class);
		if (p != null) {
			String one = p.mapper();
			if (!one.equals("")) {
				parsed.setTableName(one);
			} else {
				parsed.setTableName(BeanUtil.getByFirstLower(clz.getSimpleName()));
			}
		} else {
			parsed.setTableName(BeanUtil.getByFirstLower(clz.getSimpleName()));
		}

		BeanElement one1 = null;
		BeanElement two = null;
		Iterator ite = elementList.iterator();

		while (ite.hasNext()) {
			BeanElement beIte = (BeanElement) ite.next();
			if (beIte.property.equals(parsed.getKey(1))) {
				one1 = beIte;
				ite.remove();
			} else if (parsed.isCombinedKey() && beIte.property.equals(parsed.getKey(2))) {
				two = beIte;
				ite.remove();
			}
		}

		elementList.add(0, one1);
		if (parsed.isCombinedKey()) {
			elementList.add(1, two);
		}

		Iterator beIte1 = parsed.getBeanElementList().iterator();

		while (beIte1.hasNext()) {
			if (beIte1.next() == null) {
				beIte1.remove();
			}
		}

		BeanUtilX.parseCacheableAnno(clz, parsed);
		put(clz, parsed);
		System.out.println(elementList.toString());
		BeanUtilX.parseSearch(parsed, clz);
	}

	public static ReflectionCache getReflectionCache(Class clz) {
		ReflectionCache cache = (ReflectionCache) cacheMap.get(clz);
		if (cache == null) {
			cache = new ReflectionCache();
			cache.setClz(clz);
			cache.cache();
			cacheMap.put(clz, cache);
		}

		return cache;
	}
}