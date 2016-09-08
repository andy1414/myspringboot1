/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.my.springboot.common.BeanElement;
import com.my.springboot.common.KV;
import com.my.springboot.common.interfaces.IIndexTyped;
import com.my.springboot.common.interfaces.Persistence;
import com.my.springboot.common.interfaces.Search;
import com.my.springboot.common.interfaces.Persistence.ignore;
import com.my.springboot.common.interfaces.Persistence.isEmail;
import com.my.springboot.common.interfaces.Persistence.isMobile;
import com.my.springboot.common.interfaces.Persistence.noCache;
import com.my.springboot.common.interfaces.Persistence.notNull;
import com.my.springboot.common.interfaces.Search.keywords;
import com.my.springboot.common.interfaces.Search.tag;


public class BeanUtilX extends BeanUtil {
	public static List<BeanElement> getElementList(Class clz) {
		ArrayList fl = new ArrayList();
		if (clz.getSuperclass() != Object.class) {
			fl.addAll(Arrays.asList(clz.getSuperclass().getDeclaredFields()));
		}

		fl.addAll(Arrays.asList(clz.getDeclaredFields()));
		HashMap filterMap = new HashMap();
		HashMap allMap = new HashMap();
		Iterator ml = fl.iterator();

		while (ml.hasNext()) {
			Field mns = (Field) ml.next();
			allMap.put(mns.getName(), mns);
			if (mns.getModifiers() >= 128) {
				filterMap.put(mns.getName(), mns);
			}

			ignore filterList = (ignore) mns.getAnnotation(ignore.class);
			if (filterList != null) {
				filterMap.put(mns.getName(), mns);
			}
		}

		HashSet mns1 = new HashSet();
		ArrayList ml1 = new ArrayList();
		if (clz.getSuperclass() != Object.class) {
			ml1.addAll(Arrays.asList(clz.getSuperclass().getDeclaredMethods()));
		}

		ml1.addAll(Arrays.asList(clz.getDeclaredMethods()));
		Iterator ite = ml1.iterator();

		while (ite.hasNext()) {
			Method filterList1 = (Method) ite.next();
			mns1.add(filterList1.getName());
		}

		ArrayList filterList2 = new ArrayList();
		Iterator list = ml1.iterator();

		while (true) {
			String e;
			BeanElement clzName;
			Method ite1;
			do {
				if (!list.hasNext()) {
					ite = filterList2.iterator();

					while (ite.hasNext()) {
						BeanElement list1 = (BeanElement) ite.next();
						if (!list1.isPair()) {
							ite.remove();
						}
					}

					Iterator e1 = filterMap.keySet().iterator();

					while (true) {
						Iterator beIte1;
						while (e1.hasNext()) {
							String list2 = (String) e1.next();
							beIte1 = filterList2.iterator();

							while (beIte1.hasNext()) {
								clzName = (BeanElement) beIte1.next();
								if (clzName.property.equals(list2)) {
									beIte1.remove();
									break;
								}
							}
						}

						ArrayList list3 = new ArrayList();

						BeanElement e2;
						for (beIte1 = filterList2.iterator(); beIte1.hasNext(); list3.add(e2)) {
							e2 = (BeanElement) beIte1.next();
							parseAnno(clz, e2, (Field) allMap.get(e2.property));
							String clzName1 = e2.clz.getName();
							if (e2.sqlField == null) {
								if (!clzName1.contains("int") && !clzName1.contains("Integer")) {
									if (!clzName1.contains("long") && !clzName1.contains("Long")) {
										if (!clzName1.contains("double") && !clzName1.contains("Double")) {
											if (!clzName1.contains("float") && !clzName1.contains("Float")) {
												if (!clzName1.contains("boolean") && !clzName1.contains("Boolean")) {
													if (clzName1.contains("Date")) {
														e2.sqlField = "timestamp";
													} else if (clzName1.contains("String")) {
														e2.sqlField = "varchar";
														if (e2.length == 0) {
															e2.length = 60;
														}
													}
												} else {
													e2.sqlField = "tinyint";
													e2.length = 1;
												}
											} else {
												e2.sqlField = "float";
												e2.length = 13;
											}
										} else {
											e2.sqlField = "float";
											e2.length = 13;
										}
									} else {
										e2.sqlField = "bigint";
										e2.length = 13;
									}
								} else {
									e2.sqlField = "int";
									e2.length = 11;
								}
							} else if (e2.sqlField.contains("text")) {
								e2.length = 0;
							} else {
								e2.sqlField = "varchar";
							}
						}

						try {
							for (beIte1 = list3.iterator(); beIte1
									.hasNext(); e2.getMethod = clz.getDeclaredMethod(e2.getter, new Class[0])) {
								e2 = (BeanElement) beIte1.next();
								e2.setMethod = clz.getDeclaredMethod(e2.setter, new Class[] { e2.clz });
							}
						} catch (Exception arg13) {
							arg13.printStackTrace();
						}

						return list3;
					}
				}

				ite1 = (Method) list.next();
				e = ite1.getName();
			} while (!e.startsWith("set") && !e.startsWith("get") && !e.startsWith("is"));

			String beIte = getProperty(e);
			clzName = null;
			Iterator arg12 = filterList2.iterator();

			while (arg12.hasNext()) {
				BeanElement setter = (BeanElement) arg12.next();
				if (setter.property.equals(beIte)) {
					clzName = setter;
					break;
				}
			}

			if (clzName == null) {
				clzName = new BeanElement();
				clzName.property = beIte;
				filterList2.add(clzName);
			}

			if (e.startsWith("set")) {
				clzName.setter = e;
			} else if (e.startsWith("get")) {
				clzName.getter = e;
				clzName.clz = ite1.getReturnType();
			} else if (e.startsWith("is")) {
				clzName.getter = e;
				clzName.clz = ite1.getReturnType();
				clzName.property = e;
				String setter1 = getSetter(e);
				if (mns1.contains(setter1)) {
					clzName.setter = setter1;
				}
			}
		}
	}

	public static void parseCacheableAnno(Class clz, Parsed parsed) {
		noCache p = (noCache) clz.getAnnotation(noCache.class);
		if (p != null) {
			parsed.setNoCache(true);
		}

	}

	public static String parseAnno(Class clz, BeanElement ele, Field f) {
		Object type = null;
		Method m = null;

		try {
			m = clz.getDeclaredMethod(ele.getter, new Class[0]);
		} catch (NoSuchMethodException arg8) {
			;
		}

		Persistence p;
		if (m != null) {
			p = (Persistence) m.getAnnotation(Persistence.class);
			if (p != null) {
				ele.sqlField = p.type();
				ele.length = p.length();
				if (!p.mapper().equals("")) {
					ele.mapper = p.mapper();
				}
			}
		}

		if (f != null) {
			p = (Persistence) f.getAnnotation(Persistence.class);
			if (p != null) {
				ele.sqlField = p.type();
				ele.length = p.length();
				ele.mapper = p.mapper();
			}

			isMobile isMobile = (isMobile) f.getAnnotation(isMobile.class);
			if (isMobile != null) {
				ele.isMobile = true;
			}

			isEmail isEmail = (isEmail) f.getAnnotation(isEmail.class);
			if (isEmail != null) {
				ele.isEmail = true;
			}

			notNull notNull = (notNull) f.getAnnotation(notNull.class);
			if (notNull != null) {
				ele.notNull = true;
			}
		}

		return (String) type;
	}

	public static void parseKey(Parsed parsed, Class clz) {
		Map map = parsed.getKeyMap();
		Map keyFieldMap = parsed.getKeyFieldMap();
		ArrayList list = new ArrayList();

		try {
			list.addAll(Arrays.asList(clz.getDeclaredFields()));
			Class f = clz.getSuperclass();
			if (f != Object.class) {
				list.addAll(Arrays.asList(f.getDeclaredFields()));
			}
		} catch (Exception arg7) {
			;
		}

		Iterator arg5 = list.iterator();

		while (true) {
			Persistence a;
			Field f1;
			do {
				if (!arg5.hasNext()) {
					return;
				}

				f1 = (Field) arg5.next();
				a = (Persistence) f1.getAnnotation(Persistence.class);
			} while (a == null);

			if (a.key() != 1 && a.key() != 2 && a.key() != 7) {
				if (a.key() == 17) {
					map.put(Integer.valueOf(1), f1.getName());
					map.put(Integer.valueOf(7), f1.getName());
					f1.setAccessible(true);
					keyFieldMap.put(Integer.valueOf(1), f1);
					keyFieldMap.put(Integer.valueOf(7), f1);
				}
			} else {
				map.put(Integer.valueOf(a.key()), f1.getName());
				f1.setAccessible(true);
				keyFieldMap.put(Integer.valueOf(a.key()), f1);
			}

			if (a.key() == 1) {
				parsed.setNotAutoIncreament(a.isNotAutoIncrement());
			}
		}
	}

	public static Map<String, Object> getQueryMap(Parsed parsed, Object obj) {
		HashMap map = new HashMap();
		Class clz = obj.getClass();

		try {
			Iterator arg4 = parsed.getBeanElementList().iterator();

			while (arg4.hasNext()) {
				BeanElement e = (BeanElement) arg4.next();
				Method method = e.getMethod;
				Object value = method.invoke(obj, new Object[0]);
				Class type = method.getReturnType();
				if (type == Integer.TYPE) {
					if (((Integer) value).intValue() != 0) {
						map.put(e.property, value);
					}
				} else if (type == Integer.class) {
					if (value != null) {
						map.put(e.property, value);
					}
				} else if (type == Long.TYPE) {
					if (((Long) value).longValue() != 0L) {
						map.put(e.property, value);
					}
				} else if (type == Long.class) {
					if (value != null) {
						map.put(e.property, value);
					}
				} else if (type == Double.TYPE) {
					if (((Double) value).doubleValue() != 0.0D) {
						map.put(e.property, value);
					}
				} else if (type == Double.class) {
					if (value != null) {
						map.put(e.property, value);
					}
				} else if (type == Float.TYPE) {
					if (((Float) value).floatValue() != 0.0F) {
						map.put(e.property, value);
					}
				} else if (type == Float.class) {
					if (value != null) {
						map.put(e.property, value);
					}
				} else if (type == Boolean.TYPE) {
					if (((Boolean) value).booleanValue()) {
						map.put(e.property, value);
					}
				} else if (type == Boolean.class) {
					if (value != null) {
						map.put(e.property, value);
					}
				} else if (type == String.class) {
					if (value != null && !((String) value).equals("")) {
						map.put(e.property, value);
					}
				} else if (type == Date.class && value != null) {
					map.put(e.property, value);
				}
			}
		} catch (Exception arg8) {
			arg8.printStackTrace();
		}

		return map;
	}

	public static List<KV> getQueryList(Parsed parsed, Object obj) {
		LinkedList list = new LinkedList();
		Class clz = obj.getClass();

		try {
			Iterator oneKV = parsed.getBeanElementList().iterator();

			while (oneKV.hasNext()) {
				BeanElement keyOne = (BeanElement) oneKV.next();
				Method kv = clz.getDeclaredMethod(keyOne.getter, new Class[0]);
				Object value = kv.invoke(obj, new Object[0]);
				Class type = kv.getReturnType();
				if (type == Integer.TYPE) {
					if (((Integer) value).intValue() != 0) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == Integer.class) {
					if (value != null) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == Long.TYPE) {
					if (((Long) value).longValue() != 0L) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == Long.class) {
					if (value != null) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == Double.TYPE) {
					if (((Double) value).doubleValue() != 0.0D) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == Double.class) {
					if (value != null) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == Boolean.TYPE) {
					if (((Boolean) value).booleanValue()) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == Boolean.class) {
					if (value != null) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == String.class) {
					if (value != null && !((String) value).equals("")) {
						list.add(new KV(keyOne.property, value));
					}
				} else if (type == Date.class && value != null) {
					list.add(new KV(keyOne.property, value));
				}
			}
		} catch (Exception arg8) {
			arg8.printStackTrace();
		}

		String keyOne1 = parsed.getKey(1);
		KV oneKV1 = null;
		Iterator value1 = list.iterator();

		while (value1.hasNext()) {
			KV kv1 = (KV) value1.next();
			if (kv1.k.equals(keyOne1)) {
				list.remove(kv1);
				oneKV1 = kv1;
				break;
			}
		}

		if (oneKV1 != null) {
			list.add(0, oneKV1);
		}

		return list;
	}

	public static String getIndexClzName(Class clz) {
		String name = clz.getName();
		name = name + "Index";
		return name;
	}

	public static String getClzName(IIndexTyped obj) {
		String name = obj.getClass().getName();
		name = name.replace("Index", "");
		return name;
	}

	public static <T> Class<T> getBeanClass(IIndexTyped obj) {
		String clzName = getClzName(obj);
		Class clz = null;

		try {
			clz = Class.forName(clzName);
		} catch (ClassNotFoundException arg3) {
			arg3.printStackTrace();
		}

		return clz;
	}

	public static Class getIndexClass(Class clz) {
		String name = getIndexClzName(clz);
		Class indexClz = null;

		try {
			indexClz = Class.forName(name);
		} catch (Exception arg3) {
			;
		}

		return indexClz;
	}

	public static void filter(IIndexTyped index) {
		Class clz = getBeanClass(index);
		if (clz == null) {
			throw new RuntimeException("BIG_INDEX EXCEPTION: BEAN CLASS NOT EXISTED, class = " + getClzName(index));
		}
	}

	public static void parseSearch(Parsed parsed, Class clz) {
		Search pClz = (Search) clz.getAnnotation(Search.class);
		if (pClz != null) {
			parsed.setSearchable(true);
			Field[] arg5;
			int arg4 = (arg5 = clz.getDeclaredFields()).length;

			for (int arg3 = 0; arg3 < arg4; ++arg3) {
				Field f = arg5[arg3];
				keywords pp = (keywords) f.getAnnotation(keywords.class);
				if (pp != null) {
					parsed.getKeywordsList().add(f.getName());
				} else {
					Search pc = (Search) f.getAnnotation(Search.class);
					String name;
					if (pc != null) {
						Class pt = f.getType();
						String cl = f.getName();
						name = cl + ".";
						parseSearch(name, parsed, pt);
					} else {
						tag arg15 = (tag) f.getAnnotation(tag.class);
						if (arg15 != null) {
							Class arg14 = f.getType();
							name = f.getName();
							String prefix = name + ".";
							parseSearch(prefix, parsed, arg14);
							TagParsed tag = new TagParsed();
							tag.setType(arg15.type());
							tag.setField(f);
							String tagKey = arg15.type().getSimpleName() + "Tag";
							tagKey = getByFirstLower(tagKey);
							tag.setTagKey(tagKey);
							f.setAccessible(true);
							parsed.getTagMap().put(name, tag);
						}
					}
				}
			}

		}
	}

	private static void parseSearch(String prefix, Parsed parsed, Class clz) {
		Search pClz = (Search) clz.getAnnotation(Search.class);
		if (pClz != null) {
			Field[] arg6;
			int arg5 = (arg6 = clz.getDeclaredFields()).length;

			for (int arg4 = 0; arg4 < arg5; ++arg4) {
				Field f = arg6[arg4];
				keywords pp = (keywords) f.getAnnotation(keywords.class);
				if (pp != null) {
					parsed.getKeywordsList().add(prefix + f.getName());
				} else {
					Search pc = (Search) f.getAnnotation(Search.class);
					if (pc != null) {
						Class pt = f.getType();
						String cl = f.getName();
						prefix = prefix + cl + ".";
						parseSearch(prefix, parsed, pt);
					} else {
						tag arg14 = (tag) f.getAnnotation(tag.class);
						if (arg14 != null) {
							Class arg15 = f.getType();
							String name = f.getName();
							prefix = prefix + name + ".";
							parseSearch(prefix, parsed, arg15);
							TagParsed tag = new TagParsed();
							tag.setType(arg14.type());
							tag.setField(f);
							String tagKey = arg14.type().getSimpleName() + "Tag";
							tagKey = getByFirstLower(tagKey);
							tag.setTagKey(tagKey);
							f.setAccessible(true);
							parsed.getTagMap().put(name, tag);
						}
					}
				}
			}

		}
	}
}