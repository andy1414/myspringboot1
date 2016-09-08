package com.my.springboot.bean.json;

import java.util.List;

public class BizContent {

	private String out_trade_no;
	private String total_amount;
	private String discountable_amount;
	private String subject;
	private String body;
	private String operator_id;
	private String store_id;
	private String terminal_id;
	private String time_expire;
	private List<GoodsDetail> goods_detail;

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getDiscountable_amount() {
		return discountable_amount;
	}

	public void setDiscountable_amount(String discountable_amount) {
		this.discountable_amount = discountable_amount;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public List<GoodsDetail> getGoods_detail() {
		return goods_detail;
	}

	public void setGoods_detail(List<GoodsDetail> goods_detail) {
		this.goods_detail = goods_detail;
	}

}
