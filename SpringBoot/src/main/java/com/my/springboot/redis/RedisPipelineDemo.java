package com.my.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class RedisPipelineDemo {
	@Autowired
	private JedisConnector_Cache jedisConnector_Cache;
	      
	    public void hasPipeline(){  
	    	Jedis jedis = jedisConnector_Cache.get();  
	        System.out.println("开始："+System.currentTimeMillis());  
	        Pipeline pl = jedis.pipelined();  
	        for(int i = 0 ; i < 1000 ; i++){  
	            pl.incr("testPL");  
	        }  
	        pl.sync();  
	        System.out.println("开始："+System.currentTimeMillis());  
	    }  
	      
	    public void noPipeline(){  
	    	Jedis jedis = jedisConnector_Cache.get();  
	        System.out.println("开始："+System.currentTimeMillis());  
	        for(int i = 0 ; i < 1000 ; i++){  
	            jedis.incr("testPL");  
	        }  
	        System.out.println("开始："+System.currentTimeMillis());  
	    }  
}
