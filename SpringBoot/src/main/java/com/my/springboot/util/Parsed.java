/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.my.springboot.common.BeanElement;

public class Parsed {
	private Class clz;
	private String tableName;
	private final Map<Integer, String> keyMap = new HashMap();
	private final Map<Integer, Field> keyFieldMap = new HashMap();
	private List<BeanElement> beanElementList;
	private Map<String, BeanElement> elementMap = new HashMap();
	private boolean isNotAutoIncreament;
	private boolean isNoCache;
	private List<String> keywordsList = new ArrayList();
	private boolean isSearchable;
	private Map<String, TagParsed> tagMap = new HashMap();

	public Class getClz() {
		return this.clz;
	}

	public void setClz(Class clz) {
		this.clz = clz;
	}

	public Parsed(Class clz) {
		this.clz = clz;
	}

	public String getId() {
		return this.isCombinedKey()
				? (String) this.keyMap.get(Integer.valueOf(1)) + "_" + (String) this.keyMap.get(Integer.valueOf(2))
				: String.valueOf(this.keyMap.get(Integer.valueOf(1)));
	}

	public BeanElement getElement(String property) {
		return (BeanElement) this.elementMap.get(property);
	}

	public Map<Integer, String> getKeyMap() {
		return this.keyMap;
	}

	public Map<Integer, Field> getKeyFieldMap() {
		return this.keyFieldMap;
	}

	public Field getKeyField(int index) {
		return (Field) this.keyFieldMap.get(Integer.valueOf(index));
	}

	public String getKey(int index) {
		return this.keyMap.isEmpty() && index == 1 ? "id" : (String) this.keyMap.get(Integer.valueOf(index));
	}

	public boolean isCombinedKey() {
		return this.keyMap.containsKey(Integer.valueOf(1)) && this.keyMap.containsKey(Integer.valueOf(2));
	}

	public List<BeanElement> getBeanElementList() {
		return this.beanElementList;
	}

	public void setBeanElementList(List<BeanElement> beanElementList) {
		this.beanElementList = beanElementList;
		Iterator arg2 = this.beanElementList.iterator();

		while (arg2.hasNext()) {
			BeanElement e = (BeanElement) arg2.next();
			this.elementMap.put(e.property, e);
		}

	}

	public boolean isNotAutoIncreament() {
		return this.isNotAutoIncreament;
	}

	public void setNotAutoIncreament(boolean isAutoIncreament) {
		this.isNotAutoIncreament = isAutoIncreament;
	}

	public boolean isSharding() {
		return this.keyMap.containsKey(Integer.valueOf(7));
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isNoCache() {
		return this.isNoCache;
	}

	public void setNoCache(boolean isNoCache) {
		this.isNoCache = isNoCache;
	}

	public List<String> getKeywordsList() {
		return this.keywordsList;
	}

	public void setKeywordsList(List<String> keywordsList) {
		this.keywordsList = keywordsList;
	}

	public boolean isSearchable() {
		return this.isSearchable;
	}

	public void setSearchable(boolean isSearchable) {
		this.isSearchable = isSearchable;
	}

	public String[] getKeywardsArr() {
		String[] keywordsArr = new String[this.keywordsList.size()];
		this.keywordsList.toArray(keywordsArr);
		return keywordsArr;
	}

	public Map<String, TagParsed> getTagMap() {
		return this.tagMap;
	}

	public void setTagMap(Map<String, TagParsed> tagMap) {
		this.tagMap = tagMap;
	}

	public String toString() {
		return "Parsed [tableName=" + this.tableName + ", keyMap=" + this.keyMap + ", beanElementList="
				+ this.beanElementList + ", elementMap=" + this.elementMap + ", isNotAutoIncreament="
				+ this.isNotAutoIncreament + ", isNoCache=" + this.isNoCache + "]";
	}
}