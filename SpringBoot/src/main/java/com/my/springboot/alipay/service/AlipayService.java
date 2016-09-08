package com.my.springboot.alipay.service;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.my.springboot.alipay.factory.AlipayAPIClientFactory;
import com.my.springboot.alipay.util.AlipayProperties;
import com.my.springboot.bean.json.BizContent;
import com.my.springboot.bean.json.QrCodeResponse;

@Service
public class AlipayService implements IAlipayService {

	@Override
	public QrCodeResponse getQrCode(BizContent bizContent) {

		String time_expire = DateFormatUtils.format(System.currentTimeMillis() + 24 * 60 * 60 * 1000,
				"yyyy-MM-dd HH:mm:ss");
		bizContent.setTime_expire(time_expire);
		String bizString = JSON.toJSONString(bizContent);
		QrCodeResponse resp = new QrCodeResponse();
		String notifyUrl = AlipayProperties.getNotifyUrl();
		resp = createQrCode(bizString, notifyUrl);

		return resp;
	}

	private QrCodeResponse createQrCode(String bizString, String notifyUrl) {

		QrCodeResponse qcr = new QrCodeResponse();

		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();

		// 使用SDK，构建群发请求模型
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
		request.setBizContent(bizString);
		request.setNotifyUrl(notifyUrl);
		AlipayTradePrecreateResponse response = null;
		try {

			// 使用SDK，调用交易下单接口
			response = alipayClient.execute(request);
			if (null != response && response.isSuccess()) {
				if ("10000".equals(response.getCode())) {
					qcr.setResult("OK");
					// 商户将此二维码值生成二维码，然后展示给用户，用户用支付宝手机钱包扫码完成支付
					qcr.setQrCode(response.getQrCode());
				} else {
					qcr.setResult("NG");
					qcr.setMsg(response.getSubMsg());
				}
			} else {
				qcr.setResult("NG");
				qcr.setMsg(response.getMsg());
			}
		} catch (AlipayApiException e) {
			qcr.setResult("NG");
			qcr.setMsg(e.getMessage());
		}

		return qcr;
	}
}
