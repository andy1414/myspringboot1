package com.my.springboot.rabbitmq;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ExchangesFanoutRecvConsole {
	private final static String EXCHANGE_NAME = "ex_log";

	public static void main(String[] argv)
			throws java.io.IOException, java.lang.InterruptedException, TimeoutException {
		// 创建连接和频道
		Channel channel = new MyConnectionFactory().getChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		// 创建一个非持久的、唯一的且自动删除的队列
		String queueName = channel.queueDeclare().getQueue();
		// 为转发器指定队列，设置binding
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 指定接收者，第二个参数为自动应答，无需手动应答
		channel.basicConsume(queueName, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(" [x] Received '" + message + "'");

		}
	}
}
