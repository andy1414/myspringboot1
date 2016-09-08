/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.redis.persistence;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.my.springboot.util.Configs;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConnector_Persistence {
	private static JedisPool pool;
	private static JedisConnector_Persistence instance;
	@Autowired
	public static RedisProperties redisProperties;


	public static JedisConnector_Persistence getInstance() {
		if (instance == null) {
			instance = new JedisConnector_Persistence();
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(redisProperties.getPersistencemax());
			config.setMaxIdle(redisProperties.getPersistenceidle());
			config.setJmxEnabled(true);
			config.setJmxNamePrefix("redis-persistence");
			config.setTestOnBorrow(true);

			System.out.println("REDIS_IP_PERSISTENCE = " + Configs.getString("REDIS_IP_PERSISTENCE"));
			System.out.println("REDIS_PORT_PERSISTENCE = " + Configs.getString("REDIS_PORT_PERSISTENCE"));

			pool = new JedisPool(config,redisProperties.getPersistenceip(),redisProperties.getPersistenceport());
		}
		return instance;
	}

	public Jedis get() {
		return pool.getResource();
	}

	public void close(Jedis jedis) {
		pool.returnResource(jedis);
	}

	public void closeBroken(Jedis jedis) {
		pool.returnBrokenResource(jedis);
	}

	public void set(String key, String value) {
		if ((key == null) || (key.equals(""))) {
			return;
		}

		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return;
			jedis.set(key, value);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}
	}

	public void set(String key, String value, int seconds) {
		if ((key == null) || (key.equals(""))) {
			return;
		}

		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return;
			jedis.set(key, value);
			jedis.expire(key, seconds);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}
	}

	public void set(byte[] key, byte[] value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return;
			jedis.set(key, value);
			jedis.expire(key, seconds);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}
	}

	public void set(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return;
			jedis.set(key, value);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}
	}

	public String get(String key) {
		String str = null;
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return null;
			str = jedis.get(key);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}

		return str;
	}

	public List<byte[]> mget(byte[][] keyArr) {
		if ((keyArr == null) || (keyArr.length == 0)) {
			return null;
		}
		List byteList = null;
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return null;
			byteList = jedis.mget(keyArr);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}

		return byteList;
	}

	public byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return null;
			value = jedis.get(key);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}

		return value;
	}

	public void delete(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return;
			jedis.del(key);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}
	}

	public void hset(String mapName, String key, String value) {
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return;
			jedis.hset(mapName, key, value);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}
	}

	public String hget(String mapName, String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return "";
			value = jedis.hget(mapName, key);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}

		return value;
	}

	public long hincrBy(String mapName, String key, long increment) {
		long value = 0L;
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return 0L;
			value = jedis.hincrBy(mapName, key, increment).longValue();
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}

		return value;
	}

	public boolean lock(String key) {
		boolean isLock = false;

		String value = "LOCK";
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return isLock;
			isLock = jedis.setnx(key, value).longValue() == 1L;
			jedis.expire(key, 3);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}

		return isLock;
	}

	public void unLock(String key) {
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return;
			jedis.del(key);
			pool.returnResource(jedis);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
		}
	}
}