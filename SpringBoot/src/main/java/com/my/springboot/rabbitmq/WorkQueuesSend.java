package com.my.springboot.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class WorkQueuesSend {
	  //队列名称  
	     private final static String QUEUE_NAME = "workqueue";  
	   
	     public static void main(String[] args) throws IOException, TimeoutException  
	     {  
	         //创建连接和频道  
	    	//创建一个连接  
	         Connection connection = new MyConnectionFactory().getConnection();
	         //创建一个频道  
	         Channel channel = connection.createChannel();  
	         //指定一个队列  
	         channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	         //持久化队列
	         //boolean durable = true;
	         //channel.queueDeclare("task_queue", durable, false, false, null);
	         
	         //发送10条消息，依次在消息后面附加1-10个点  
	         for (int i = 0; i < 10; i++)  
	         {  
	             String dots = "";  
	             for (int j = 0; j <= i; j++)  
	             {  
	                 dots += ".";  
	             }  
	             String message = "helloworld" + dots+dots.length();  
	             channel.basicPublish("", QUEUE_NAME, null, message.getBytes()); 
	             //标识信息为持久化
	             //channel.basicPublish("", "task_queue",MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
	             
	             System.out.println(" [x] Sent '" + message + "'");  
	         }  
	         //关闭频道和资源  
	         channel.close();  
	         connection.close();  
	   
	     }  
	   
}
