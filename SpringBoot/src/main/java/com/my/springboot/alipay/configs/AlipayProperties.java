package com.my.springboot.alipay.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AlipayProperties {

	private static String alipayGateway;
	private static String appId;
	private static String privateKey;
	private static String alipayPublicKey;
	private static String charset;
	private static String notifyUrl;

	static {

		try {
			Properties prop = new Properties();
			InputStream in = AlipayProperties.class.getResourceAsStream("alipay.properties");
			prop.load(in);
			alipayGateway = prop.getProperty("ALIPAY_GATEWAY");
			appId = prop.getProperty("APP_ID");
			privateKey = prop.getProperty("PRIVATE_KEY");
			alipayPublicKey = prop.getProperty("ALIPAY_PUBLIC_KEY");
			charset = prop.getProperty("CHARSET");
			notifyUrl = prop.getProperty("NOTIFY_URL");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getAlipayGateway() {
		return alipayGateway;
	}

	public static String getAppId() {
		return appId;
	}

	public static String getPrivateKey() {
		return privateKey;
	}

	public static String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public static String getCharset() {
		return charset;
	}

	public static String getNotifyUrl() {
		return notifyUrl;
	}

}
