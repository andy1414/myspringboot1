/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.common.interfaces;


import java.util.List;

import com.my.springboot.base.page.Pagination;

public interface ICacheResolver {
	String markForRefresh(Class arg0);

	void remove(Class arg0, String arg1);

	void set(Class arg0, String arg1, Object arg2);

	<T> T get(Class<T> arg0, String arg1);

	void setResultKeyList(Class arg0, String arg1, List<String> arg2);

	<T> void setResultKeyListPaginated(Class<T> arg0, String arg1, Pagination<T> arg2);

	List<String> getResultKeyList(Class arg0, String arg1);

	<T> Pagination<T> getResultKeyListPaginated(Class<T> arg0, String arg1);

	<T> List<T> list(Class<T> arg0, List<String> arg1);
}