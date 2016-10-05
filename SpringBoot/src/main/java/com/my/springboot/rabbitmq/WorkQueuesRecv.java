package com.my.springboot.rabbitmq;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class WorkQueuesRecv {
	 //队列名称  
	     private final static String QUEUE_NAME = "workqueue";  
	   
	     public static void main(String[] argv) throws java.io.IOException,  
	             java.lang.InterruptedException, TimeoutException  
	     {  
	         //区分不同工作进程的输出  
	         int hashCode = WorkQueuesRecv.class.hashCode();  
	         //创建连接和频道  
	       //打开连接和创建频道，与发送端一样  
	         Channel channel = new MyConnectionFactory().getChannel();
	         //声明队列  
	         channel.queueDeclare(QUEUE_NAME, false, false, false, null); 
	         System.out.println(hashCode  
	                 + " [*] Waiting for messages. To exit press CTRL+C");  
	       
	         QueueingConsumer consumer = new QueueingConsumer(channel);
	         //boolean ack = false ; //打开应答机制  
	         //channel.basicConsume(QUEUE_NAME, ack, consumer);
	         
	         
	         // 指定消费队列  
	         channel.basicConsume(QUEUE_NAME, true, consumer);  
	         while (true)  
	         {  
	             QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
	             String message = new String(delivery.getBody());  
	   
	             System.out.println(hashCode + " [x] Received '" + message + "'");  
	             doWork(message);  
	             System.out.println(hashCode + " [x] Done");  
	   
	         }  
	   
	     }  
	   
	     /** 
	      * 每个点耗时1s 
	      * @param task 
	      * @throws InterruptedException 
	      */  
	     private static void doWork(String task) throws InterruptedException  
	     {  
	         for (char ch : task.toCharArray())  
	         {  
	             if (ch == '.')  
	                 Thread.sleep(1000);  
	         }  
	     }  
}
