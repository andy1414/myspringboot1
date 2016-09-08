/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.io.Serializable;
import java.lang.reflect.Field;

public class TagParsed implements Serializable {
	private static final long serialVersionUID = 7631076184961243538L;
	private Class type;
	private String tagKey;
	private Field field;

	public Class getType() {
		return this.type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public String getTagKey() {
		return this.tagKey;
	}

	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String toString() {
		return "TagParsed [type=" + this.type + ", tagKey=" + this.tagKey + "]";
	}
}