package com.my.springboot.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
//公平转发（Fair dispatch）
//告诉RabbitMQ不要在同一时间给一个消费者超过一条消息。换句话说，只有在消费者空闲的时候会发送下一条信息。
public class WorkQueuesSendQos {
	  //队列名称  
	     private final static String QUEUE_NAME = "workqueue_persistence";  
	   
	     public static void main(String[] args) throws IOException, TimeoutException  
	     {  
	         //创建连接和频道  
	    	//创建一个连接  
	         Connection connection = new MyConnectionFactory().getConnection();
	         //创建一个频道  
	         Channel channel = connection.createChannel();  
	         boolean durable = true;// 1、设置队列持久化  
	         channel.queueDeclare(QUEUE_NAME, durable, false, false, null);  
	         
	         //发送10条消息，依次在消息后面附加1-10个点  
	         for (int i = 5; i > 0; i--)    
	         {  
	             String dots = "";  
	             for (int j = 0; j <= i; j++)  
	             {  
	                 dots += ".";  
	             }  
	             String message = "helloworld" + dots+dots.length();  
	             // MessageProperties 2、设置消息持久化
	             channel.basicPublish("", QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes()); 
	             System.out.println(" [x] Sent '" + message + "'");  
	         }  
	         //关闭频道和资源  
	         channel.close();  
	         connection.close();  
	   
	     }  
	   
}
