/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.redis.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.springboot.base.page.Pagination;
import com.my.springboot.common.interfaces.ICacheResolver;
import com.my.springboot.util.Configs;
import com.my.springboot.util.ObjectUtil;
import com.my.springboot.util.VerifyUtil;

@Component
public class CacheResolver implements ICacheResolver {
	public static final String NANO_SECOND = ".N_S";
	private static CacheResolver instance = null;
	@Autowired
	private JedisConnector_Cache jedisConnector_Cache;

	public static CacheResolver getInstance() {
		if (instance == null) {
			instance = new CacheResolver();
		}
		return instance;
	}

	public String markForRefresh(Class clz) {
		String key = getNSKey(clz);
		String time = String.valueOf(System.nanoTime());
		jedisConnector_Cache.set(key.getBytes(), time.getBytes());
		return time;
	}

	public void remove(Class clz, String key) {
		key = getSimpleKey(clz, key);
		jedisConnector_Cache.delete(key.getBytes());
	}

	private String getNSKey(Class clz) {
		return clz.getName() + ".N_S";
	}

	private String getNS(String nsKey) {
		return jedisConnector_Cache.get(nsKey);
	}

	private byte[][] getKeyList(Class clz, List<String> conditionList) {
		if ((conditionList == null) || (conditionList.isEmpty()))
			return null;
		List<byte[] > keyList = new ArrayList<byte[]>();
		for (String condition : conditionList) {
			String key = getSimpleKey(clz, condition);
			keyList.add(key.getBytes());
		}
		if (keyList.isEmpty())
			return null;
		byte[][] arrList = new byte[keyList.size()][];
		int i = 0;
		for (byte[] keyB : keyList) {
			arrList[(i++)] = keyB;
		}
		return arrList;
	}

	public void setResult(Class clz, String condition, Object obj) {
		String key = getKey(clz, condition);
		System.out.println("save key: " + key);
		int validSecond = Configs.getIntValue("CACHE_VALID_SECOND") / 2;
		jedisConnector_Cache.set(key.getBytes(), ObjectUtil.toBytes(obj), validSecond);
	}

	public Object getResult(Class clz, String condition) {
		String key = getKey(clz, condition);
		System.out.println("get key: " + key);
		byte[] bytes = jedisConnector_Cache.get(key.getBytes());

		if (bytes == null) {
			return null;
		}
		return ObjectUtil.toObject(bytes);
	}

	private String getSimpleKey(Class clz, String condition) {
		return "{" + clz.getName() + "}." + condition;
	}

	private String getKey(Class clz, String condition) {
		long startTime = System.currentTimeMillis();
		String key = VerifyUtil.toMD5(getPrefix(clz) + condition);
		long endTime = System.currentTimeMillis();
		System.out.println("time_getKey = " + (endTime - startTime));
		return key;
	}

	private String getPrefix(Class clz) {
		String key = getNSKey(clz);
		byte[] nsArr = jedisConnector_Cache.get(key.getBytes());
		if (nsArr == null) {
			String str = markForRefresh(clz);
			return clz.getName() + str;
		}
		return clz.getName() + new String(nsArr);
	}

	public String markForRefresh(Class clz, long idOne) {
		markForRefresh(clz);

		String key = getNSKey(clz, idOne);
		String time = String.valueOf(System.nanoTime());
		jedisConnector_Cache.set(key.getBytes(), time.getBytes());
		return time;
	}

	public void setResult(Class clz, long idOne, String condition, Object obj) {
		String key = getKey(clz, idOne, condition);
		System.out.println("save key: " + key);
		int validSecond = Configs.getIntValue("CACHE_VALID_SECOND") / 2;
		jedisConnector_Cache.set(key.getBytes(), ObjectUtil.toBytes(obj), validSecond);
	}

	public Object getResult(Class clz, long idOne, String condition) {
		String key = getKey(clz, idOne, condition);
		System.out.println("get key: " + key);
		byte[] bytes = jedisConnector_Cache.get(key.getBytes());

		if (bytes == null) {
			return null;
		}
		return ObjectUtil.toObject(bytes);
	}

	private String getNSKey(Class clz, long idOne) {
		return clz.getName() + "_" + idOne + "_" + ".N_S";
	}

	private String getKey(Class clz, long idOne, String condition) {
		return VerifyUtil.toMD5(getPrefix(clz, idOne) + condition);
	}

	private String getPrefix(Class clz, long idOne) {
		String key = getNSKey(clz, idOne);
		byte[] nsArr = jedisConnector_Cache.get(key.getBytes());
		if (nsArr == null) {
			String str = markForRefresh(clz, idOne);
			return clz.getName() + idOne + str;
		}
		return clz.getName() + idOne + new String(nsArr);
	}

	public void set(Class clz, String key, Object obj) {
		key = getSimpleKey(clz, key);
		int validSecond = getValidSecondAdjusted();
		jedisConnector_Cache.set(key.getBytes(), PersistenceUtil.toBytes(obj), validSecond);
	}

	private int getValidSecondAdjusted() {
		return (Configs.getIntValue("CACHE_VALID_SECOND") * 700);
	}

	public void setResultKeyList(Class clz, String condition, List<String> keyList) {
		String key = getKey(clz, condition);
		int validSecond = Configs.getIntValue("CACHE_VALID_SECOND");
		jedisConnector_Cache.set(key.getBytes(), ObjectUtil.toBytes(keyList), validSecond);
	}

	public void setResultKeyListPaginated(Class clz, String condition, Pagination pagination) {
		String key = getKey(clz, condition);
		int validSecond = Configs.getIntValue("CACHE_VALID_SECOND");
		jedisConnector_Cache.set(key.getBytes(), ObjectUtil.toBytes(pagination), validSecond);
	}

	public List<String> getResultKeyList(Class clz, String condition) {
		String key = getKey(clz, condition);
		System.out.println("get key: " + key);
		long startTime = System.currentTimeMillis();
		byte[] bytes = jedisConnector_Cache.get(key.getBytes());
		long endTime = System.currentTimeMillis();
		System.out.println("time_getResultKeyList = " + (endTime - startTime));
		if (bytes == null) {
			return new ArrayList();
		}
		return ((List) ObjectUtil.toObject(bytes));
	}

	public Pagination<String> getResultKeyListPaginated(Class clz, String condition) {
		String key = getKey(clz, condition);
		System.out.println("get key: " + key);
		byte[] bytes = jedisConnector_Cache.get(key.getBytes());

		if (bytes == null) {
			return null;
		}
		return ((Pagination) ObjectUtil.toObject(bytes));
	}

	public <T> List<T> list(Class<T> clz, List<String> keyList) {
		byte[][] bytesArr = getKeyList(clz, keyList);

		List<byte[]> bytesList = jedisConnector_Cache.mget(bytesArr);

		if (bytesList == null) {
			return new ArrayList();
		}
		List objList = new ArrayList();
		for (byte[] bytes : bytesList) {
			if (bytes == null)
				continue;
			objList.add(PersistenceUtil.toObject(clz, bytes));
		}

		return objList;
	}

	public <T> T get(Class<T> clz, String key) {
		key = getSimpleKey(clz, key);
		byte[] bytes = jedisConnector_Cache.get(key.getBytes());
		if (bytes == null)
			return null;
		Object obj = PersistenceUtil.toObject(clz, bytes);
		return (T) obj;
	}
}