package com.my.springboot.rabbitmq;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class ExchangesFanoutSend {
	 private final static String EXCHANGE_NAME = "ex_log";  
	   
	     public static void main(String[] args) throws IOException, TimeoutException  
	     {  
	         // 创建连接和频道  
	    	//创建一个连接  
	         Connection connection = new MyConnectionFactory().getConnection();
	         //创建一个频道  
	         Channel channel = connection.createChannel();  
	         // 声明转发器和类型  
	         channel.exchangeDeclare(EXCHANGE_NAME, "fanout" );  
	           
	         String message = new Date().toString()+" : log something";  
	         // 往转发器上发送消息  
	         channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());  
	   
	         System.out.println(" [x] Sent '" + message + "'");  
	   
	         channel.close();  
	         connection.close();  
	   
	     }  
}
