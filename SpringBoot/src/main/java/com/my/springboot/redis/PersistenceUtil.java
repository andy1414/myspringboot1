/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.redis.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import com.my.springboot.common.interfaces.ISerialWR;
import com.my.springboot.util.BeanSerial;

public class PersistenceUtil {
	public static byte[] toBytes(Object obj) {
		if (obj == null) {
			return null;
		}
		String clzName = obj.getClass().getName();
		ISerialWR wr = BeanSerial.get(clzName);
		if (wr != null)
			;
		try {
			ByteBuffer buffer = wr.write(obj);
			buffer.flip();
			return buffer.array();
		} catch (Exception e) {
			e.printStackTrace();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(baos);
				oos.writeObject(obj);
				return baos.toByteArray();
			} catch (IOException ie) {
				ie.printStackTrace();
			} finally {
				try {
					oos.close();
					baos.close();
				} catch (IOException ie2) {
					ie2.printStackTrace();
				}
			}
		}
		return null;
	}

	public static <T> T toObject(Class<T> clz, byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		String clzName = clz.getName();
		ISerialWR wr = BeanSerial.get(clzName);
		if (wr != null) {
			try {
				ByteBuffer buffer = ByteBuffer.wrap(bytes);
				return wr.read(buffer);
			} catch (Exception e) {
				System.out.println("toObject(Class<T> clz, byte[] bytes) 1-------------> " + clz.getName());
				e.printStackTrace();
				return null;
			}

		}

		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			Object obj = ois.readObject();
			return (T) obj;
		} catch (Exception e) {
			System.out.println("toObject(Class<T> clz, byte[] bytes) 2-------------> " + clz.getName());
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}