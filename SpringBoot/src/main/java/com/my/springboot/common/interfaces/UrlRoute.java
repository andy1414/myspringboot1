package com.my.springboot.common.interfaces;

import com.my.springboot.common.Key;

public interface UrlRoute {
	public static final String CREAT_ALIPAY_QR_CODE = "createAlipayQrCode";
	//用户信息
	//用户查询
	String WEIXINPAY = "/wxPay";
	@Key(desc = "获得openId")
	String WEIXINPAY_GET_OPENID = "getOpenId";
	@Key(desc = "微信支付")
	String WEIXINPAY_PAY = "WXPay";
	@Key(desc = "微信支付结果通知")
	String WEIXINPAY_PAY_RESULT_NOTIFY = "resultNotify";
	@Key(desc = "微信二维码支付")
	String WEIXINPAY_CODE_PAY = "wxCodePay";
	
	String ALIPAY = "/alipay";
	@Key(desc = "支付宝支付H5订单成功")
	String ALIPAY_H5_SUBMIT = "alipayH5Submit";
	@Key(desc = "支付宝支付订单成功")
	String ALIPAY_SUCCESS = "alipaySuccess";
	
	public static String getDesc(String constantName) {
		String name = "";
		try {
			name = UrlRoute.class.getDeclaredField(constantName).getAnnotation(Key.class)
					.desc();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return name;
	}
}
