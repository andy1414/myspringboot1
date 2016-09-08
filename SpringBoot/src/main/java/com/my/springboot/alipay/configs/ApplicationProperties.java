package com.my.springboot.alipay.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

	private static String port;
	private static String domain;

	static {

		try {
			Properties prop = new Properties();
			InputStream in = ApplicationProperties.class.getResourceAsStream("application.properties");
			prop.load(in);
			port = prop.getProperty("APP_PORT");
			domain = prop.getProperty("APP_DOMAIN");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getPort() {
		return port;
	}

	public static String getDomain() {
		return domain;
	}

}
