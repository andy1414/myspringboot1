/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.my.springboot.common.FieldAndMethod;

public class ReflectionCache {
	private Class clz;
	private Map<String, FieldAndMethod> map = new HashMap();
	private Map<String, FieldAndMethod> tempMap = new HashMap();

	public Class getClz() {
		return this.clz;
	}

	public void setClz(Class clz) {
		this.clz = clz;
	}

	public Map<String, FieldAndMethod> getMap() {
		return this.map;
	}

	public void setMap(Map<String, FieldAndMethod> map) {
		this.map = map;
	}

	public FieldAndMethod get(String property) {
		return (FieldAndMethod) this.map.get(property);
	}

	public FieldAndMethod getTemp(String property) {
		return (FieldAndMethod) this.tempMap.get(property);
	}

	public Map<String, FieldAndMethod> getTempMap() {
		return this.tempMap;
	}

	public void setTempMap(Map<String, FieldAndMethod> tempMap) {
		this.tempMap = tempMap;
	}

	public void cache() {
		if (this.clz != null) {
			if (this.map.isEmpty()) {
				Field[] fArr = this.clz.getDeclaredFields();
				Field[] arg4 = fArr;
				int arg3 = fArr.length;

				for (int arg2 = 0; arg2 < arg3; ++arg2) {
					Field f = arg4[arg2];
					if (f.getModifiers() <= 2) {
						String property = f.getName();
						String getterName = BeanUtil.getGetter(property);
						String setterName = BeanUtil.getSetter(property);

						try {
							Method getter = this.clz.getDeclaredMethod(getterName, new Class[0]);
							Class rt = f.getType();
							Method setter = this.clz.getDeclaredMethod(setterName, new Class[] { rt });
							if (getter != null && setter != null) {
								f.setAccessible(true);
								FieldAndMethod fnm = new FieldAndMethod();
								fnm.setProperty(property);
								fnm.setSetterName(setterName);
								fnm.setGetterName(getterName);
								fnm.setField(f);
								fnm.setGetter(getter);
								fnm.setSetter(setter);
								this.map.put(property, fnm);
							}
						} catch (Exception arg12) {
							;
						}
					}
				}
			}

		}
	}

	public String toString() {
		return "ReflectionCache [clz=" + this.clz + ", map=" + this.map + "]";
	}
}