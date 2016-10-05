package com.my.springboot.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

class MyConnectionFactory{
	private String host = "192.168.31.194";
	private int port = 5672;
	private String username = "testlink";
	private String password = "testlink";
	
	public ConnectionFactory buildConnectionFactory() {
		ConnectionFactory factory  = new ConnectionFactory(); 
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory;
	}
	
	public Connection getConnection() throws IOException, TimeoutException{
		return buildConnectionFactory().newConnection();
	}
	
	public Channel getChannel(Connection connection) throws IOException, TimeoutException{
		return connection.createChannel();
	}
	
	public Channel getChannel() throws IOException, TimeoutException{
		return getConnection().createChannel();
	}

}