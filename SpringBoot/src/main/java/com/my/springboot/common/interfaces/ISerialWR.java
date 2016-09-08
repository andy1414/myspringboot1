/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.common.interfaces;

import java.nio.ByteBuffer;

public interface ISerialWR {
	<T> T read(ByteBuffer arg0) throws Exception;

	<T> ByteBuffer write(Object arg0) throws Exception;
}