package com.my.springboot.rabbitmq;

import java.util.UUID;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TopicSend {
	  private static final String EXCHANGE_NAME = "topic_logs";  
	    
	      public static void main(String[] argv) throws Exception  
	      {  
	          // 创建连接和频道  
	          Connection connection = new MyConnectionFactory().getConnection();
	          Channel channel = connection.createChannel();  
	    
	          channel.exchangeDeclare(EXCHANGE_NAME, "topic");  
	    
	          String[] routing_keys = new String[] { "kernal.info", "cron.warning",  
	                  "auth.info", "kernel.critical" };  
	          for (String routing_key : routing_keys)  
	          {  
	              String msg = UUID.randomUUID().toString();  
	              channel.basicPublish(EXCHANGE_NAME, routing_key, null, msg  
	                      .getBytes());  
	              System.out.println(" [x] Sent routingKey = "+routing_key+" ,msg = " + msg + ".");  
	          }  
	    
	          channel.close();  
	          connection.close();  
}
}