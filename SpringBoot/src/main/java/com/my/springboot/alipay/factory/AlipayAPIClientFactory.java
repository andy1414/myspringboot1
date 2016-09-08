package com.my.springboot.alipay.factory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
//import com.alipay.constants.AlipayServiceEnvConstants;
import com.my.springboot.alipay.util.AlipayProperties;

/**
 * API调用客户端工厂
 * 
 * @author taixu.zqq
 * @version $Id: AlipayAPIClientFactory.java, v 0.1 2014年7月23日 下午5:07:45
 *          taixu.zqq Exp $
 */
public class AlipayAPIClientFactory {

	/** API调用客户端 */
	private static AlipayClient alipayClient;

	/**
	 * 获得API调用客户端
	 * 
	 * @return
	 */
	public static AlipayClient getAlipayClient() {

		if (null == alipayClient) {
			 alipayClient =  new DefaultAlipayClient(AlipayProperties.getAlipayGateway(),
					 AlipayProperties.getAppId(),
					 AlipayProperties.getPrivateKey(), "json",
					 AlipayProperties.getCharset(),AlipayProperties.getAlipayPublicKey());
		}
		return alipayClient;
	}
}
