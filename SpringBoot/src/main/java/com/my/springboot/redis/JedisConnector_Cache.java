/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.redis;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisConnector_Cache {
	private static JedisPool pool;
	public JedisConnector_Cache instance;
	@Autowired
	private RedisProperties redisProperties;
	@Bean
	public JedisConnector_Cache getJedisConnectorCache(){
				instance = new JedisConnector_Cache();
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(redisProperties.getCachemax());
				config.setMaxIdle(redisProperties.getCacheidle());
				config.setJmxEnabled(true);
				config.setJmxNamePrefix("redis-cahce");
				config.setTestOnBorrow(true);
				pool = new JedisPool(config, redisProperties.getCacheip(), redisProperties.getCacheport());
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
		if ((key == null) || (key.equals("")))
			return;
		set(key.getBytes(), value.getBytes());
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

	public void set(byte[] key, byte[] value, int validSeconds) {
		Jedis jedis = null;
		try {
			jedis = get();
			if (jedis == null)
				return;
			jedis.set(key, value);
			jedis.expire(key, validSeconds);
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
}