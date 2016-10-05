package com.my.springboot.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

public class RedisDataTypeDemo {
	@Autowired
	private JedisConnector_Cache jedisConnector_Cache;

    /**  
     *   
     * @title demoKey  
     * @description 测试Key  
     * @author hadoop  
     */  
    public void demoKey(){  
        Jedis jedis = jedisConnector_Cache.get(); 
        System.out.println("查看键为aa的是否存在："+jedis.exists("aa"));  
        System.out.println("查看键为aa的类型："+jedis.type("aa"));  
        System.out.println("随机获得一个key："+jedis.randomKey());  
        System.out.println("将aa重命名为bb："+jedis.rename("aa", "bb"));  
        System.out.println("删除bb："+jedis.del("bb"));  
        //从上面的方法可以看出，redis提供的接口方法名和redis-cli客户端提供的方法名是一样的，所以，只要对redis客户端的命令熟悉，那么Java操作redis就不是问题  
    }  
    /**  
     *   
     * @title demoString  
     * @description 测试String  
     * @author hadoop  
     */  
    public void demoString(){  
    	Jedis jedis = jedisConnector_Cache.get(); 
        System.out.println("设置name："+jedis.set("name", "小花"));  
        System.out.println("设置name1："+jedis.set("name1", "小花1"));  
        System.out.println("设置那么2："+jedis.set("name2", "小花2"));  
        System.out.println("设置name，如果存在返回0："+jedis.setnx("name", "小花哈哈"));  
        System.out.println("获取key为name和name1的value值："+jedis.mget("name","name1"));  
        System.out.println("自增1："+jedis.incr("index"));  
        System.out.println("自增1："+jedis.incr("index"));  
        System.out.println("自增2："+jedis.incrBy("count", 2));  
        System.out.println("自增2："+jedis.incrBy("count", 2));  
        System.out.println("递减1："+jedis.decr("count"));  
        System.out.println("递减2："+jedis.decrBy("index",2));  
        System.out.println("在name后面添加String："+jedis.append("name", ",我爱你"));  
        System.out.println("获取key为name的值："+jedis.get("name"));  
          
    }  
    /**  
     *   
     * @title demoList  
     * @description 测试list数据类型  
     * @author hadoop  
     */  
    public void demoList(){  
    	Jedis jedis = jedisConnector_Cache.get(); 
        //在头添加  
        jedis.lpush("list", "hadoop","hbase","hive","pig","zookeeper");  
        //在末尾添加  
        jedis.rpush("list", "java","struts");  
        //返回长度  
        jedis.llen("list");  
        //取值  
        List<String> list = jedis.lrange("list", 0, -1);  
        for (String string : list) {  
            System.out.println(string);  
        }  
    }  
    /**  
     *   
     * @title demoSet  
     * @description 测试set数据类型  
     * @author hadoop  
     */  
    public void demoSet(){  
    	Jedis jedis = jedisConnector_Cache.get(); 
        jedis.sadd("city", "北京","上海","重启","武汉");  
        System.out.println("取出最上面的："+jedis.spop("city"));  
        System.out.println("随机取出一个值"+jedis.srandmember("city"));  
        jedis.sadd("city2", "北京","武汉","河北","张家界","辽林");  
        System.out.println("交集："+jedis.sinter("city","city1"));  
        System.out.println("并集："+jedis.sunion("city","city1"));  
        System.out.println("差集："+jedis.sdiff("city","city1"));  
          
    }  
    /**  
     *   
     * @title demoMap  
     * @description 测试map数据类型  
     * @author hadoop  
     */  
    public void demoMap(){  
    	Jedis jedis = jedisConnector_Cache.get();  
        jedis.hset("Mcity", "c1", "北京");  
        System.out.println("取值："+jedis.hget("city", "c1"));  
        Map<String,String> mcity = new HashMap<String, String>();  
        mcity.put("c1", "桂林");  
        mcity.put("c2", "天津");  
        mcity.put("c3", "合肥");  
        jedis.hmset("Mcity2", mcity);  
        List<String> list =  jedis.hmget("Mcity2", "c1","c2","c3");  
        for (String string : list) {  
            System.out.println(string);  
        }  
        System.out.println(jedis.hlen("Mcity2"));  
    }  
    //测试  
    public static void main(String[] args) {  
        RedisDataTypeDemo rdr = new RedisDataTypeDemo();  
        rdr.demoKey();  
        rdr.demoList();  
        rdr.demoMap();  
        rdr.demoSet();  
        rdr.demoString();  
    }  
}
