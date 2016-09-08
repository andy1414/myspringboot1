/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.common;

import java.lang.reflect.Method;

import com.my.springboot.util.BeanUtil;

public class BeanElement {
	public String property;
	public String setter;
	public String getter;
	public Class clz;
	public int length;
	public String sqlField;
	public String mapper = "";
	public boolean isMobile;
	public boolean isEmail;
	public boolean notNull;
	public Method getMethod;
	public Method setMethod;

	public String property() {
		return this.mapper.equals("") ? this.property : this.mapper;
	}

	public String getSqlType() {
		String name = this.clz.getName();
		return name
				.contains("Date")
						? "timestamp"
						: (name.contains("String") ? "varchar"
								: (!name.contains("int") && !name.contains("Integer")
										? (!name.contains("long") && !name.contains("Long")
												? (!name.contains("double") && !name.contains("Double")
														? (!name.contains("float") && !name.contains("Float")
																? (!name.contains("boolean")
																		&& !name.contains("Boolean") ? "" : "tinyint")
																: "float")
														: "float")
												: "bigint")
										: "int"));
	}

	public String toString() {
		return "BeanElement [property=" + this.property + ", setter=" + this.setter + ", getter=" + this.getter
				+ ", sqlField=" + this.sqlField + ", clz=" + this.clz + "]";
	}

	public boolean isPair() {
		return this.setter == null ? false
				: (this.getter == null ? false
						: (this.getter.startsWith("is") ? this.setter.substring(3).equals(this.getter.substring(2))
								: BeanUtil.getProperty(this.setter).equals(BeanUtil.getProperty(this.getter))));
	}
}