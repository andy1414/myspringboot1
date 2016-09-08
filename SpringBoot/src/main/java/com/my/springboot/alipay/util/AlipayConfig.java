package com.my.springboot.alipay.util;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088021283133119";
	
	//测试用
	//public static String partner = "2088221738356005";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = "zfb@igoxin.com";
	
	//测试用
	//public static String seller_id = "tona.lin@igoxin.com";

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANW4cpQ9zrjutlDVpUBjkXcjBNye7qprNiPQTuVxyH+TCZlAzTWDw4oy1fLLLyfFAAPK+CMM+iiB5pHKbxZwmckHJnHNXXEntJRd6YT+veLAtuLFP2PARaxySPnVMdLHIVxKXz36WpM9m4x0FYXh4tXlhcJ1QR3lCfQbPeOroMwlAgMBAAECgYBAWOyGnaVkINtbszeyD0h46l5IXsHVJbqDRfC7PGr1njvBnLUsa6OwNGYeV/a2rqW2fqH/S6T2FUZqltyjkPt78mAY5dk5xoUm6Z9ckVEj0mimv5ZwCogdHnarqQfJbfEV6juAm8TkS+4vHEvj3a8aOJCiktBK5xOzQ4IP66+N8QJBAPS5xOhxDeRsKu7wwy6uHiEaTHaMP8rQzAa6v/yOVEHT4vVtx+3a7Wn7rdeX1y68H8g4yT35tQjcgwJ1qSiKo78CQQDfkQL2pOF5yXUFpX0K44nU9Dk0N7zvEkeolliSe7Xe5LLxzfrOhBwjMylBr9z4V1au4dIYDXjaHqxR2ZGsdDkbAkBRDb4j5l3di93kaaWbrluYRyeQE/+E4wbQK8Yulc1eSBlhhEy1gM8lTRBU2ZuRhQlZvUUZ27yUBMT3aZTvMovtAkA/cYFryOKLkxX4YjwX6PZmTmAlZ2PgXEmll/TOrPixpij54cA49tidTJl6oJOOjgp1WnQqizgufeHc4Gcsj1bdAkAQJ9sd7br1I9PewSYynPpkewHxKytYgtwZ+g8eI9Tf9j7GFWAtEb6synR4vDAUA29gn7Hy2t6e/rPqZG6Bng33";
	
	//测试用
	//public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMU3cCfnRp+feqT6xn8MwiKtzJdSMM5UyOr7ILpEFbpfjKqUSdSybMqUiVBD3mCJsAl+lFLb1/lsiX9Dvq28+6okanpyHqOscsw8O8ZLT/2KDxNKoCwgb7Zn5l+bMpDDbiadBaBNxmDV5QcPXaJWH42vrAUba+AWYLZpBoLg4t5HAgMBAAECgYBeWH8XF9T5V9ncQvvim7/jxzEw0uj2TCkyAKFPenLKjhtFH1uSnSjeYOCvvUgM9tF4OU3LtdBESmAcudPmWZoqm5K+QtMy0Y1gnDetDrVL/bjY4HbTflO8yDfYFG4HP8WQnBzvkX9ubYFmk0H/QeNgn31f6xPqT2bmH9js+jL7gQJBAO6Ku0UNatKx1sbH3FTHwufgw1Z6hRYQA83MoA37cuzcqSFD2yJqyytFDW/N8LgQj8qTB6PSdyquIsatDIoeIzkCQQDTpnGXqYI8EBzFJO2iYI5aSU1UgChWanNWUrUQqQJrqTMAcpGkeXAQyohADgHuxqiwylincxYx6OFQLmAJ0Y1/AkAJtgQhr4Ylxa+ksAY8PVWl8vSA1MQOPeCpxCbP6uim0+TewdBt+kMDI/CEGLIwDByOdqXc+Xh4eFZaGDlmdguhAkEAlWsNCI/v1G1fkkJmzVqzRMQ5Jq2vTIqsUEXldBSNE+qIiglYDrB40DHbjLUW4jY3g0/m1JhoE+YfotaKkIPzCwJADei+MnaPwlsIGh75oxcyqakjvEYXGvKzAsr4lOJiAzR7sw9i92qorxcoA8RSsGQMcu8UMWNsa9IuxWuYIKGNDA==";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	
	//测试用
	//public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFN3An50afn3qk+sZ/DMIircyXUjDOVMjq+yC6RBW6X4yqlEnUsmzKlIlQQ95gibAJfpRS29f5bIl/Q76tvPuqJGp6ch6jrHLMPDvGS0/9ig8TSqAsIG+2Z+ZfmzKQw24mnQWgTcZg1eUHD12iVh+Nr6wFG2vgFmC2aQaC4OLeRwIDAQAB";
	
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "http://商户网址/alipay.wap.create.direct.pay.by.user-JAVA-UTF-8/notify_url.jsp";
	public static String notify_url = "http://www.igoxin.com/";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String return_url = "http://商户网址/alipay.wap.create.direct.pay.by.user-JAVA-UTF-8/return_url.jsp";
	public static String return_url = "http://www.igoxin.com/";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "alipay.wap.create.direct.pay.by.user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}

