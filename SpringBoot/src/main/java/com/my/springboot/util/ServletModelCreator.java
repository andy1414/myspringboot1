package com.my.springboot.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class ServletModelCreator {
	private static Logger logger = Logger.getLogger(ServletModelCreator.class.getName());

	public static <T> T createModel(HttpServletRequest request, Class<T> clz) {

		Object obj = null;
		try {
			obj = clz.newInstance();
		} catch (InstantiationException e1) {

			e1.printStackTrace();
		} catch (IllegalAccessException e1) {

			e1.printStackTrace();
		}


		Map inputs = request.getParameterMap();
		
		List<Field> list = new ArrayList<Field>();

		Field[] fs = clz.getSuperclass().getDeclaredFields();
		for (Field f :fs){
			list.add(f);
		}
		
		fs = clz.getDeclaredFields();
		for (Field f :fs){
			list.add(f);
		}

		for (Field field : list) {
			String key = field.getName();
			String v = "";
			Object value = null;

			if (inputs.containsKey(key)) {

				value = inputs.get(key);
				v = ((String[]) value)[0].trim();
				
				/*if (StringUtil.isNullOrEmpty(v))
					continue;*/
				// Object v = null;

				Class<?> c = field.getType();
				// if ( c.getSimpleName().equals("String"))
				// v = ((String[])value)[0];
				String name = c.getSimpleName();

				try {
					if (name.equals("String")) {
						System.err.println(name+"==========createModel==========="+v);
						v = v.replace("<", "&lt").replace(">", "&gt");
						obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj, v);

					} else {
						if (!v.equals("")) {
							if (name.equals("int") || name.equals("Integer")) {
								obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
										Integer.parseInt(v));

							} else if (name.equals("long") || name.equals("Long")) {
								obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
										Long.parseLong(v));
							} else if (name.equals("double") || name.equals("Double")) {
								obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
										Double.parseDouble(v));
							}else if (name.equals("float") || name.equals("Float")) {
								obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
										Float.parseFloat(v));
							} 
							else if (name.equals("boolean") || name.equals("Boolean")) {
								System.err.println(v +" true or false = " + Boolean.parseBoolean(v));
								obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
										Boolean.parseBoolean(v));
							} 
							else if (name.contains("Date")) {
								obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
										TimeUtil.getDate(v));
							}
						}
					}
				} catch (Exception e) {
					logger.log(Level.INFO,
							" *******  ServletModelCreator.createModel: getDeclaredMethod.invoke" + e.getMessage());
					throw new RuntimeException("ServletModelCreator.createModel: getDeclaredMethod().invoke() wrong"
							+ c.getName() + "xxx: " + key + "****** " + e.getMessage());
				}
			}

		}
		
		return (T) obj;
	}

	public static List createModelList(HttpServletRequest request, Class clz) {
		List list = new ArrayList();
		Map inputs = request.getParameterMap();
		Field[] fields = clz.getDeclaredFields();

		int row = 0;
		Iterator ite = inputs.values().iterator();
		while (ite.hasNext()) {
			String[] ss = (String[]) ite.next();
			row = ss.length;
			break;
		}
		for (int i = 0; i < row; i++) {
			int length = fields.length;

			Object obj = null;
			try {
				obj = clz.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}

			for (int l = 0; l < length; l++) {
				String key = "";
				String v = "";
				Object value = null;
				key = fields[l].getName();
				if (inputs.containsKey(key)) {

					value = inputs.get(key);

					v = ((String[]) value)[i].trim();
					logger.log(Level.INFO, "key: " + key + "     :      value: " + v);
					// Object v = null;

					Class<?> c = fields[l].getType();
					// if ( c.getSimpleName().equals("String"))
					// v = ((String[])value)[0];
					String name = c.getSimpleName();
					try {
						if (name.equals("String")) {
							obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj, v);
						} else {
							if (!v.equals("")) {
								if (name.equals("int") || name.equals("Integer")) {
									obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
											Integer.parseInt(v));

								} else if (name.equals("long") || name.equals("Long")) {
									obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
											Long.parseLong(v));
								} else if (name.equals("double") || name.equals("Double")) {
									obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
											Double.parseDouble(v));
								} else if (name.equals("float") || name.equals("Float")) {
									obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
											Float.parseFloat(v));
								} 
								else if (name.equals("boolean") || name.equals("Boolean")) {
									obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
											Boolean.parseBoolean(v));
								} 
								else if (name.contains("Date")) {
									obj.getClass().getDeclaredMethod(BeanUtil.getSetter(key), c).invoke(obj,
											TimeUtil.getDate(v));
								}
							}
						}
					} catch (Exception e) {
						logger.log(Level.INFO, " *******  ServletModelCreator.createModelList: getDeclaredMethod.invoke"
								+ e.getMessage());
						throw new RuntimeException(
								"ServletModelCreator.createModel: getDeclaredMethodList().invoke() wrong");
					}
				}
			}
			list.add(obj);
		}
		if (list.size() == 0)
			list = null;
		return list;
	}

}