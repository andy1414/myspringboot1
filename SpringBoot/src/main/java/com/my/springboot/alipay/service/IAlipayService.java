package com.my.springboot.alipay.service;

import com.my.springboot.bean.json.BizContent;
import com.my.springboot.bean.json.QrCodeResponse;

public interface IAlipayService {

	public QrCodeResponse getQrCode(BizContent bizContent);
}
