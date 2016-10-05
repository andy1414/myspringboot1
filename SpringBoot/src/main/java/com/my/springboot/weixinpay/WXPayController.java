package com.my.springboot.weixinpay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.springboot.common.interfaces.UrlRoute;
import com.my.springboot.core.ModelView;
import com.my.springboot.redis.JedisConnector_Persistence;
import com.my.springboot.util.CommonUtil;
import com.my.springboot.util.GetWxOrderno;
import com.my.springboot.util.RequestHandler;
import com.my.springboot.util.Sha1Util;
import com.my.springboot.util.TenpayUtil;
import com.my.springboot.util.UrlConnectionUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(UrlRoute.WEIXINPAY)
public class WXPayController {
	private static String APPID = "";
	private static String APPSECRET = "";
	private static String BACKURI = "";
	private static String RESULTNOTIFYURL = "";
	private static String REFRESHORDERSTATUS = "";
	private static String H5BACKURL = "";
	private static final String OPEN_ID_KEY = "wxPay_openId";
	private static final String TOEKN_KEY = "wxPay_token";
	private static final String TICKECT_KEY = "wxPay_ticket";
	private static final String CODE_KEY = "wxPay_code";
	private static final int EXPIRE_MILLISECONDS = 5400;
	private static final String STR_TEMPLATE_SIGNATURE = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";

	static {
		getConfigResource();
	}

	@RequestMapping(UrlRoute.WEIXINPAY_GET_OPENID)
	@ResponseBody
	public Map<String, Object> getOpenId(HttpServletRequest request, HttpServletResponse response) {
		// getConfigResource();
		String url = request.getParameter("url");
		String request_url = url;
		//amended by wugang 2016-4-16
//		String code = GetCode(request);
//		String openId = GetOpenId(code);
//		if(StringUtil.isNullOrEmpty(openId)){
//			return ModelView.toast("");
//		}
		return ModelView.toast(url);
	}

	public static void getConfigResource() {
		ResourceBundle rb = ResourceBundle.getBundle("weixinpay");
		APPID = rb.getString("WEIXIN_APPID");
		APPSECRET = rb.getString("WEIXIN_APPSECRET");
		BACKURI = rb.getString("BACKURI");
		RESULTNOTIFYURL = rb.getString("RESULTNOTIFYURL");
		REFRESHORDERSTATUS = rb.getString("REFRESHORDERSTATUS");
		H5BACKURL = rb.getString("H5BACKURL");
	}

	private String GetTicket(String token) {
		String ticket = JedisConnector_Persistence.getInstance().get(TICKECT_KEY);
		System.out.println("ticket======" + ticket);
		if (ticket == null || "".equals(ticket)) {
			GenerateTicket(token);
		}
		ticket = JedisConnector_Persistence.getInstance().get(TICKECT_KEY);
		return ticket;
	}

	private void GenerateTicket(String token) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";

		JSONObject jsonObject = CommonUtil.httpsRequest(url, "GET", null);

		if (null != jsonObject) {
			try {
				System.out.println("getTicketResult=" + jsonObject.toString());
				String ticket = jsonObject.getString("ticket");
				System.out.println("getTicket=======" + ticket);
				JedisConnector_Persistence.getInstance().set(TICKECT_KEY, ticket, EXPIRE_MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String SHA1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换 十六进制
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String GenerateSignature(String ticket, String nonceStr, String timeStamp, String url) {
		String toCryphStr = String.format(STR_TEMPLATE_SIGNATURE, ticket, nonceStr, timeStamp, url);

		String returnValue = SHA1(toCryphStr);

		return returnValue;
	}

	private String GetOpenId(String code) {
		// String openId =
		// JedisConnector_Persistence.getInstance().get(OPEN_ID_KEY);
		String openId = "";
		System.out.println("openId======" + openId);
		// if (openId == null || "".equals(openId)) {
		openId = GenerateOpenId(code);
		// }
		// openId = JedisConnector_Persistence.getInstance().get(OPEN_ID_KEY);
		return openId;
	}

	private String GetToken(String code) {
		String token = JedisConnector_Persistence.getInstance().get(TOEKN_KEY);
		System.out.println("token======" + token);
		if (token == null || "".equals(token)) {
			GenerateToken();
		}
		token = JedisConnector_Persistence.getInstance().get(TOEKN_KEY);
		return token;
	}

	private String GetCode(HttpServletRequest request) {
		// String code = JedisConnector_Persistence.getInstance().get(CODE_KEY);
		String code = "";

		// if (code == null || "".equals(code)) {
		code = request.getParameter("code");
		System.out.println("code=======" + code);
		// JedisConnector_Persistence.getInstance().set(CODE_KEY, code,
		// EXPIRE_MILLISECONDS);
		// }
		return code;
	}

	private void GenerateToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret="
				+ APPSECRET;
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "GET", null);

		if (null != jsonObject) {
			try {
				System.out.println("getTokenJson=" + jsonObject.toString());
				String token = jsonObject.getString("access_token");
				System.out.println("access_token=======" + token);
				JedisConnector_Persistence.getInstance().set(TOEKN_KEY, token, EXPIRE_MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String GenerateOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APPSECRET
				+ "&code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "GET", null);
		String openId = "";
		System.out.println("GenerateOpenId=======" + jsonObject.toString());
		if (null != jsonObject && jsonObject.toString().contains("openid")) {
			try {
				openId = jsonObject.getString("openid");
				System.out.println("getOpenId=======" + openId);
				JedisConnector_Persistence.getInstance().set(OPEN_ID_KEY, openId, EXPIRE_MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
				return "weixinpay/fail";
			}
			return openId;
		}
		return "weixinpay/fail";
	}

	@RequestMapping(UrlRoute.WEIXINPAY_PAY)
	public String wxPay(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		// getConfigResource();
		System.out.println("wxPay==========" + request.toString());
		request.setAttribute("url", H5BACKURL);
		String orderNo = request.getParameter("orderNo");
		String orderId = orderNo + "_" + System.currentTimeMillis();
		String money = request.getParameter("money");
		String userId = request.getParameter("userId");
		String code = GetCode(request);
		String openId = GetOpenId(code);
		String token = GetToken(code);
		String ticket = GetTicket(token);

		String signature = GenerateSignature(ticket, UUID.randomUUID().toString(),
				String.valueOf(System.currentTimeMillis()), BACKURI);

		System.out.println("orderNo==========" + orderNo + "money==========" + money + "code==========" + code
				+ "signature=============" + signature);
		float sessionmoney = Float.parseFloat(money);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = TenpayUtil.buildRandom(4) + "";
		String strReq = strTime + strRandom;
		String mch_id = "1260214801";
		String device_info = "";
		String nonce_str = strReq;
		String body = "网络科技有限公司";
		String attach = userId;
		if (attach == null || "".equals(attach)) {
			attach = String.valueOf(System.currentTimeMillis() / 1000);
		}
		String out_trade_no = orderNo;
		int intMoney = Integer.parseInt(finalmoney);

		int total_fee = intMoney;
		String spbill_create_ip = request.getRemoteAddr();
		String notify_url = RESULTNOTIFYURL + "?orderNo=" + orderNo;
		String trade_type = "JSAPI";
		String openid = openId;
		String partnerkey = "gouxinpaysecret20150612gouxinpay";
		SortedMap<String, String> packgeParams = new TreeMap<>();
		packgeParams.put("appid", APPID);
		packgeParams.put("mch_id", mch_id);
		packgeParams.put("nonce_str", nonce_str);
		packgeParams.put("body", body);
		packgeParams.put("attach", attach);
		packgeParams.put("out_trade_no", out_trade_no);

		packgeParams.put("total_fee", total_fee + "");
		packgeParams.put("spbill_create_ip", spbill_create_ip);
		packgeParams.put("notify_url", notify_url);

		packgeParams.put("trade_type", trade_type);
		packgeParams.put("openid", openid);

		RequestHandler requestHandler = new RequestHandler(request, response);
		requestHandler.init(APPID, APPSECRET, partnerkey);

		String sign = requestHandler.createSign(packgeParams);
		String xml = "<xml>" + "<appid>" + APPID + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" + "<body><![CDATA[" + body + "]]></body>"
				+ "<attach>" + attach + "</attach>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
				+ "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
				+ "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
				+ "</trade_type>" + "<openid>" + openid + "</openid>" + "</xml>";
		String allParameters = "";
		try {
			allParameters = requestHandler.genPackage(packgeParams);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		try {
			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (prepay_id.equals("")) {
				request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
				return "weixinpay/fail";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		SortedMap<String, String> finalpackage = new TreeMap<>();
		String appid = APPID;
		String timestamp = Sha1Util.getTimeStamp();
		String nonceStr = nonce_str;
		String prepay_id2 = "prepay_id=" + prepay_id;
		String packages = prepay_id2;
		finalpackage.put("appId", appid);
		finalpackage.put("timeStamp", timestamp);
		finalpackage.put("nonceStr", nonceStr);
		finalpackage.put("package", packages);
		finalpackage.put("signType", "MD5");
		String finalsign = requestHandler.createSign(finalpackage);
		request.setAttribute("appid", appid);
		request.setAttribute("timeStamp", timestamp);
		request.setAttribute("nonceStr", nonceStr);
		request.setAttribute("package", packages);
		request.setAttribute("sign", finalsign);
		request.setAttribute("productName", body);
		request.setAttribute("money", money);
		request.setAttribute("orderId", orderNo);
		request.setAttribute("signature", signature);
		return "weixinpay/pay";
	}

	@RequestMapping(UrlRoute.WEIXINPAY_PAY_RESULT_NOTIFY)
	public String notifyUrl(HttpServletRequest request) {
		String responseResult = "";
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
			Map<String, Object> map = null;
			try {
				map = GetWxOrderno.doXMLParse(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			String result_code = (String) map.get("result_code");
			if ("SUCCESS".equals(result_code)) {
				String transaction_id = (String) map.get("transaction_id");
				String out_trade_no = (String) map.get("out_trade_no");
				String time_end = (String) map.get("time");
				String attach = (String) map.get("attach");
				String sign = (String) map.get("sign");
				String orderId = out_trade_no.split("_")[0];
				String notifyUrl = REFRESHORDERSTATUS + "?userId=" + attach + "&orderId=" + orderId + "&sign=" + sign;
				String str = UrlConnectionUtil.getUrl(notifyUrl);
				com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(str);
				String result = jsonObject.getString("status");
				if ("OK".equals(result)) {
					responseResult = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
							+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
					return responseResult;
				} else {
					responseResult = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
							+ "<return_msg><![CDATA[修改订单状�?�失败]]></return_msg>" + "</xml>";
					return responseResult;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseResult = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[系统异常]]></return_msg>" + "</xml>";
			return responseResult;
		}
		return responseResult;
	}
}
