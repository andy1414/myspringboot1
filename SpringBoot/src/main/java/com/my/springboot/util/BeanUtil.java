/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;

import com.my.springboot.common.FieldAndMethod;

public class BeanUtil {
	public static String getGetter(String property) {
		if (property.startsWith("is")) {
			return property;
		} else {
			String a = property.substring(0, 1);
			String rest = property.substring(1);
			return "get" + a.toUpperCase() + rest;
		}
	}

	public static String getSetter(String property) {
		String a;
		if (property.startsWith("is")) {
			a = property.substring(2);
			return "set" + a;
		} else {
			a = property.substring(0, 1);
			String rest = property.substring(1);
			return "set" + a.toUpperCase() + rest;
		}
	}

	public static String getByFirstLower(String clzName) {
		String a = clzName.substring(0, 1);
		String rest = clzName.substring(1);
		String result = a.toLowerCase() + rest;
		return result;
	}

	public static String getProperty(String methodName) {
		if (methodName.startsWith("is")) {
			return methodName;
		} else {
			methodName = methodName.substring(3);
			return getByFirstLower(methodName);
		}
	}

	public static String getPropertyOfBoolen(String setter) {
		return "is" + setter.substring(3);
	}

	public static <T> T copy(Class<T> clz, Object origin) {
		if (origin == null) {
			return null;
		} else {
			T t = null;
			String p = "";
			Object v = null;

			try {
				t = clz.newInstance();
				Class e = origin.getClass();
				ReflectionCache originCache = Parser.getReflectionCache(e);
				ReflectionCache cache = Parser.getReflectionCache(clz);
				Iterator arg8 = cache.getMap().values().iterator();

				while (arg8.hasNext()) {
					FieldAndMethod fnm = (FieldAndMethod) arg8.next();
					FieldAndMethod originFnm = originCache.get(fnm.getProperty());
					if (originFnm == null) {
						originFnm = originCache.getTemp(fnm.getProperty());
						if (originFnm == null) {
							originFnm = new FieldAndMethod();
							originCache.getTempMap().put(fnm.getProperty(), originFnm);
							String m = fnm.getGetterName();
							Method orginGetter = null;

							try {
								orginGetter = e.getDeclaredMethod(m, new Class[0]);
							} catch (Exception arg17) {
								;
							}

							if (orginGetter != null) {
								originFnm.setGetter(orginGetter);
								originFnm.setGetterName(m);
								String setterName = fnm.getSetterName();
								Method orginSetter = null;

								try {
									orginSetter = e.getDeclaredMethod(setterName,
											new Class[] { fnm.getField().getType() });
								} catch (Exception arg16) {
									;
								}

								if (orginSetter != null) {
									originFnm.setSetter(orginSetter);
									originFnm.setSetterName(setterName);
								}

								originFnm.setProperty(fnm.getProperty());
							}
						}
					}

					try {
						if (originFnm != null && originFnm.getGetterName() != null) {
							v = e.getDeclaredMethod(originFnm.getGetterName(), new Class[0]).invoke(origin,
									new Object[0]);
							Method m1 = fnm.getSetter();
							m1.invoke(t, new Object[] { v });
						}
					} catch (Exception arg15) {
						;
					}
				}
			} catch (Exception arg18) {
				System.out.println("p = " + p + ", v = " + v);
				arg18.printStackTrace();
			}

			return t;
		}
	}

	public static void copy(Object target, Object origin) {
		if (origin != null && target != null) {
			try {
				Class e = target.getClass();
				Class oc = origin.getClass();
				Method[] originMethodArr = oc.getDeclaredMethods();
				HashSet methodSet = new HashSet();
				Method[] arg8 = originMethodArr;
				int arg7 = originMethodArr.length;

				Method m;
				int arg6;
				for (arg6 = 0; arg6 < arg7; ++arg6) {
					m = arg8[arg6];
					methodSet.add(m.getName());
				}

				arg7 = (arg8 = e.getDeclaredMethods()).length;

				for (arg6 = 0; arg6 < arg7; ++arg6) {
					m = arg8[arg6];
					if (m.getName().startsWith("set")) {
						String p = "";
						if (m.getParameterTypes()[0] != Boolean.TYPE && m.getParameterTypes()[0] != Boolean.class) {
							p = getProperty(m.getName());
						} else {
							p = getPropertyOfBoolen(m.getName());
						}

						String getter = getGetter(p);
						if (methodSet.contains(getter)) {
							Object v = null;

							try {
								v = oc.getDeclaredMethod(getter, new Class[0]).invoke(origin, new Object[0]);
							} catch (Exception arg13) {
								;
							}

							if (v != null) {
								m.invoke(target, new Object[] { v });
							}
						}
					}
				}
			} catch (Exception arg14) {
				arg14.printStackTrace();
			}

		}
	}

	public static void copyX(Object target, Object origin) {
		if (origin != null && target != null) {
			Class clz = target.getClass();
			Class oc = origin.getClass();
			ReflectionCache cache = Parser.getReflectionCache(clz);
			HashSet set = new HashSet();
			Method[] e;
			int v = (e = oc.getDeclaredMethods()).length;

			for (int arg6 = 0; arg6 < v; ++arg6) {
				Method fam = e[arg6];
				set.add(fam.getName());
			}

			Iterator arg13 = cache.getMap().values().iterator();

			while (true) {
				FieldAndMethod arg12;
				do {
					if (!arg13.hasNext()) {
						return;
					}

					arg12 = (FieldAndMethod) arg13.next();
				} while (!set.contains(arg12.getGetterName()));

				Object arg14 = null;

				try {
					Method arg15 = oc.getDeclaredMethod(arg12.getGetterName(), new Class[0]);
					arg14 = arg15.invoke(origin, new Object[0]);
					Class rt = arg15.getReturnType();
					if ((rt == Integer.TYPE || rt == Long.TYPE || rt == Double.TYPE || rt == Float.TYPE
							|| rt == Boolean.TYPE) && arg14.toString().equals("0")) {
						arg14 = null;
					}
				} catch (Exception arg11) {
					;
				}

				if (arg14 != null) {
					try {
						arg12.getSetter().invoke(target, new Object[] { arg14 });
					} catch (Exception arg10) {
						arg10.printStackTrace();
					}
				}
			}
		}
	}
}