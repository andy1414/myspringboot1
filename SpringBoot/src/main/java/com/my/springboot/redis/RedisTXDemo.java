package com.my.springboot.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisTXDemo {
	@Autowired
	private JedisConnector_Cache jedisConnector_Cache;
	/**  
     *   
     * @title txDemo  
     * @description 没有异常的事务  
     * @author hadoop  
     */  
    public void txDemo(){  
    	Jedis jedis = jedisConnector_Cache.get();  
        //开启事务  
        Transaction tx = jedis.multi();  
          
        tx.set("txcity", "beijin");  
        tx.sadd("hi", "abc");  
        tx.get("txcity");  
        List<Object> objs = tx.exec();  
          
        for (Object object : objs) {  
            System.out.println(object.toString());  
        }  
    }  
      
    /**  
     *   
     * @title txHasExDemo  
     * @description 含有异常的事务  
     * @author hadoop  
     */  
    public void txHasExDemo(){  
    	Jedis jedis = jedisConnector_Cache.get(); 
        //开启事务  
        Transaction tx = jedis.multi();  
          
        tx.set("txcity1", "beijin");  
        tx.sadd("hi1", "abc");  
        int i = 1/0;  
        tx.get("txcity1");  
        List<Object> objs = tx.exec();  
          
        for (Object object : objs) {  
            System.out.println(object.toString());  
        }  
    }  
      
    public static void main(String[] args) {  
        RedisTXDemo redisTXDemo = new RedisTXDemo();  
        redisTXDemo.txDemo();  
        redisTXDemo.txHasExDemo();  
    }  
}
