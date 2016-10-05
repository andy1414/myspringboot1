package com.my.springboot.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//@Data
@ConfigurationProperties(prefix = "redis",locations="classpath:redis.properties")
@Component
public class RedisProperties {
	private String persistenceip;
	private int persistenceport;
	private int persistencemax;
	private int persistenceidle;
	private String cacheip;
	private int cacheport;
	private int cachemax;
	private int cacheidle;

	public String getPersistenceip() {
		return persistenceip;
	}
	public void setPersistenceip(String persistenceip) {
		this.persistenceip = persistenceip;
	}
	public int getPersistenceport() {
		return persistenceport;
	}
	public void setPersistenceport(int persistenceport) {
		this.persistenceport = persistenceport;
	}
	public int getPersistencemax() {
		return persistencemax;
	}
	public void setPersistencemax(int persistencemax) {
		this.persistencemax = persistencemax;
	}
	public int getPersistenceidle() {
		return persistenceidle;
	}
	public void setPersistenceidle(int persistenceidle) {
		this.persistenceidle = persistenceidle;
	}
	public String getCacheip() {
		return cacheip;
	}
	public void setCacheip(String cacheip) {
		this.cacheip = cacheip;
	}
	public int getCacheport() {
		return cacheport;
	}
	public void setCacheport(int cacheport) {
		this.cacheport = cacheport;
	}
	public int getCachemax() {
		return cachemax;
	}
	public void setCachemax(int cachemax) {
		this.cachemax = cachemax;
	}
	public int getCacheidle() {
		return cacheidle;
	}
	public void setCacheidle(int cacheidle) {
		this.cacheidle = cacheidle;
	}

}
