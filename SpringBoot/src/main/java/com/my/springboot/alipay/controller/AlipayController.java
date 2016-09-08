package com.my.springboot.alipay.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.igoxin.bean.constant.IncomeType;
//import com.igoxin.bean.constant.OrderPayStatus;
//import com.igoxin.bean.constant.OrderState;
//import com.igoxin.bean.dto.order.OrderDto;
//import com.igoxin.bean.entity.order.OrderBean;
//import com.igoxin.bean.entity.order.OrderPayment;
//import com.igoxin.bean.entity.user.Supplier;
//import com.igoxin.bean.entity.user.User;
//import com.igoxin.sdk.sms.SmsSender;
//import com.igoxin.service.constant.PersistenceKeys;
//import com.igoxin.service.finance.BizIncomeService_User;
//import com.igoxin.service.intf.order.IOrderService;
//import com.igoxin.service.intf.user.ISupplierService;
//import com.igoxin.service.intf.user.IUserService;
import com.my.springboot.alipay.util.AlipayConfig;
import com.my.springboot.alipay.util.AlipayNotify;
import com.my.springboot.alipay.util.AlipaySubmit;
import com.my.springboot.common.interfaces.UrlRoute;
import com.my.springboot.core.ModelView;
import com.my.springboot.redis.persistence.JedisConnector_Persistence;


@Controller
@RequestMapping(value = UrlRoute.ALIPAY, produces = "application/json")
public class AlipayController {

//	@Autowired
//	private IOrderService iOrderService;
//
//	@Autowired
//	private ISupplierService iSupplierService;
//
//	@Autowired
//	private IUserService iUserService;
//
//	// @Autowired
//	// private AlipayService alipayService;
//	//
//	// @RequestMapping(value = UrlRoute.CREAT_ALIPAY_QR_CODE, method =
//	// RequestMethod.POST)
//	// @ResponseBody
//	// public QrCodeResponse getAlipayQrCode(@RequestBody BizContent bizContent)
//	// {
//	// QrCodeResponse qrCode = alipayService.getQrCode(bizContent);
//	// return qrCode;
//	// }
//
//	@RequestMapping(UrlRoute.ALIPAY_H5_SUBMIT)
//	public Map<String, Object> alipayH5Success(HttpServletRequest req) throws UnsupportedEncodingException {
//
//		//////////////////////////////////// 请求参数//////////////////////////////////////
//
//		// 商户订单号，商户网站订单系统中唯一订单号，必填
//		String out_trade_no = req.getParameter("out_trade_no");
//
//		// 订单名称，必填
//		String subject = req.getParameter("subject");
//
//		// 付款金额，必填
//		String total_fee = req.getParameter("total_fee");
//
//		// 收银台页面上，商品展示的超链接，必填
//		String show_url = req.getParameter("show_url");
//
//		// 商品描述，可空
//		String body = req.getParameter("body");
//
//		//////////////////////////////////////////////////////////////////////////////////
//
//		System.out.println("========alipayH5Submit==========" + out_trade_no);
//
//		// 把请求参数打包成数组
//		Map<String, String> sParaTemp = new HashMap<String, String>();
//		sParaTemp.put("service", AlipayConfig.service);
//		sParaTemp.put("partner", AlipayConfig.partner);
//		sParaTemp.put("seller_id", AlipayConfig.seller_id);
//		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
//		sParaTemp.put("payment_type", AlipayConfig.payment_type);
//		sParaTemp.put("notify_url", req.getParameter("notify_url"));
//		sParaTemp.put("return_url", req.getParameter("return_url"));
//		sParaTemp.put("out_trade_no", out_trade_no);
//		sParaTemp.put("subject", subject);
//		sParaTemp.put("total_fee", total_fee);
//		sParaTemp.put("show_url", show_url);
//		sParaTemp.put("body", body);
//		// 其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
//		// 如sParaTemp.put("参数名","参数值");
//
//		// 建立请求
//		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
//		return ModelView.view(sHtmlText);
//	}
//
//	@RequestMapping(UrlRoute.ALIPAY_SUCCESS)
//	@ResponseBody
//	public void alipaySuccess(HttpServletRequest req, HttpServletResponse response)
//			throws UnsupportedEncodingException {
//		System.out.println("========enter  alipaySuccess==========");
//		// 获取支付宝POST过来反馈信息
//		Map<String, String> params = new HashMap<String, String>();
//		Map requestParams = req.getParameterMap();
//		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = (String) iter.next();
//			String[] values = (String[]) requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			}
//			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
//			params.put(name, valueStr);
//		}
//		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//		// 商户订单号
//		String out_trade_no = new String(req.getParameter("out_trade_no").getBytes("ISO-8859-1"), "GBK");
//
//		/*
//		 * 加分布式锁
//		 */
//		final String lockKey = "alipaySuccess_"+out_trade_no;
//		boolean isLocked = JedisConnector_Persistence.getInstance().lock(lockKey);
//		System.out.println("========isLocked==========" + isLocked + "==========" + lockKey);
//		if (! isLocked){
//			return;
//		}
//
//		final String CALLED = "CALLED";
//		byte[] callBytes = JedisConnector_Persistence.getInstance().get(PersistenceKeys.getKey("ALI_CALLBACK", out_trade_no).getBytes());
//
//		String callStr = null;
//		if (callBytes != null){
//			callStr = new String(callBytes);
//		}
//
//		if (callStr !=null && callStr.equals(CALLED) ){
//			try {
//				response.getWriter().write("success");
//				return;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return;
//			}
//		}
//
//		// 金额
//		String total_fee = new String(req.getParameter("total_fee").getBytes("ISO-8859-1"), "GBK");
//
//		// 支付宝交易号 String trade_no = new
//		// String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"GBK");
//
//		// 交易状态
//		String trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"), "GBK");
//		System.out.println("========params==========" + params.toString());
//		System.out.println("========verify==========" + AlipayNotify.verify(params));
//		if (AlipayNotify.verify(params)) {// 验证成功
//			System.out.println("========trade_status==========" + trade_status);
//			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
//				System.out.println("========order==========1");
//				// 查询订单
//				OrderBean order = new OrderBean();
//				if(out_trade_no.contains("F")){
//					order.setParentId(out_trade_no);
//				}else{
//					order.setId(Integer.valueOf(out_trade_no));
//				}
//				List<OrderDto> orderList = iOrderService.listOrder(order);
//				System.out.println("========order==========2");
//				long userId = orderList.get(0).getOrderBean().getUserid();
//				System.out.println("========userId==========" + userId);
//				// // 判断订单是否为空
//				// if(orderList == null || orderList.size() == 0){
//				// return ModelView.view("支付失败");
//				// }
//
//				// // 判断总金额
//				// double totalPay = 0;
//				// for(OrderDto bean:orderList){
//				// totalPay = totalPay + bean.getOrderBean().getTotalPayPrice();
//				// }
//				// if(totalPay != Double.valueOf(trade_status)){
//				// return ModelView.view("支付失败");
//				// }
//
//				/*
//				 * FIXME 移动到支付中心
//				 */
//				BizIncomeService_User.getInstance().create(userId, NumberUtil.getValue(Double.valueOf(total_fee)),
//						IncomeType.RECHARGE, "订单充值", 0, 0);
//				System.out.println("========BizIncomeService_User==========");
//				// 循环订单
//				OrderPayment payment = new OrderPayment();
//				payment.setPayState(OrderPayStatus.ORDERPAY_PAYFINISH);
//				System.out.println("========OrderPayment==========" + payment.toString());
//				String message = "";
//				for (OrderDto orderDto : orderList) {
//					//判断是否订单已经支付
//					if(orderDto.getOrderBean().getOrderState() >= OrderState.ORDER_HAVEPAY){
//						return;
//					}
//					iOrderService.payOrder(payment, orderDto.getOrderBean().getId(), userId);
//					Supplier supplier = iSupplierService.getSupplier(orderDto.getOrderBean().getSupplierId());
//					if (supplier != null) {
//						if (supplier.getMobile() != null && supplier.getMobile().length() == 11) {
//							SmsSender.get().send(supplier.getMobile(),
//									"买家已付款，请尽快安排发货，订单编号为：" + String.valueOf(orderDto.getOrderBean().getId()));
//						}
//					}
//					if ("".equals(message)) {
//						message = String.valueOf(orderDto.getOrderBean().getId());
//					} else {
//						message = message + "、" + String.valueOf(orderDto.getOrderBean().getId());
//					}
//				}
//				Map<String, Object> map = null;
//				map = iUserService.getUserInfo(userId);
//				if (ModelView.isFail(map)) {
//					try {
//						response.getWriter().write("fail");
//						return;
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						return;
//					}
//				}
//				User user = ModelView.object(map);
//				SmsSender.get().send(user.getMobile(), "您的订单已支付成功，我们会尽快安排发货,订单编号为：" + message);
//				JedisConnector_Persistence.getInstance().set(PersistenceKeys.getKey("ALI_CALLBACK", out_trade_no).getBytes(), CALLED.getBytes(), 24 * 3 * 60 * 60);
//				JedisConnector_Persistence.getInstance().unLock(lockKey);
//				ModelView.view("success");
//			}
//		} else {// 验证失败
//			ModelView.view("fail");
//		}
//	}

}
