/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldAndMethod {
	private Field field;
	private Method setter;
	private Method getter;
	private String property;
	private String setterName;
	private String getterName;

	public Field getField() {
		return this.field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Method getSetter() {
		return this.setter;
	}

	public void setSetter(Method setter) {
		this.setter = setter;
	}

	public Method getGetter() {
		return this.getter;
	}

	public void setGetter(Method getter) {
		this.getter = getter;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getSetterName() {
		return this.setterName;
	}

	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}

	public String getGetterName() {
		return this.getterName;
	}

	public void setGetterName(String getterName) {
		this.getterName = getterName;
	}

	public String toString() {
		return "FieldAndMethod [field=" + this.field == null ? "null"
				: this.field.getName() + ", setter=" + this.setter.getName() + ", getter=" + this.getter.getName()
						+ "]";
	}
}