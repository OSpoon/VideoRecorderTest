package com.n22.bean.net;

import com.n22.util.encoder.Config;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求头信息
 * Copyright: 版权所有 
 * Company: 北京耀诚立信科技有限公司
 *
 * @author 陈振国
 * @version 1.0.0
 * @create 2015年10月30日
 */
public class Header implements Serializable {

	private static final long serialVersionUID = 1L;

	// 以下是请求时需要设置的属性
	private String requestType; // 功能标识
	private String comId; // 公司编码,个险和银保是不一样的
	private String from = "PAD"; // 发送方PAD
	private String orderSerial; // 交易流水号,与报文体中的OrderId一致
	private String comSerial; // 保险公司流水号,用于传输保险公司的流水号，比如二次核保时的投保单号。第一次时可以为空
	private String productCode; // 产品ID,保险公司定义的商品ID，如果没有填写可输入为空
	private Date sendTime; // 发送时间 yyyy-MM-dd HH:mm:ss，注意：json会自动转换

	// 以下是响应获得的属性
	private String responseCode; // 交易结果：0为成功，非0为失败
	private String errorMessage; // 系统错误信息，必传

	// 以下是请求与响应共用的属性
	private String uuid; // 唯一编码,能够唯一标识一次交互，返回报文中需要携带。 同请求报文相应字段

	public Header() {
	}

	/**
	 * 获取Header对象
	 * @param requestType 功能标识
	 * @param comSerial 保险公司流水号,用于传输保险公司的流水号，比如二次核保时的投保单号。第一次时可以为空
	 * @param orderId 交易流水号,与报文体中的OrderId一致
	 * @return Header对象
	 */
	public static Header obtain(String requestType,String comSerial,String orderId) {
		Header header = new Header();
		header.requestType = requestType;
		header.comId = Config.COM_ID;
		header.comSerial = comSerial;
		header.orderSerial = orderId;
		header.sendTime = new Date();
		header.uuid = java.util.UUID.randomUUID().toString();
		
		return header;
	}

	/**
	 * 返回后台操作是否成功
	 * @return
	 */
	public boolean isSuccess(){
		return "0".equals(responseCode);
	}
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getOrderSerial() {
		return orderSerial;
	}

	public void setOrderSerial(String orderSerial) {
		this.orderSerial = orderSerial;
	}

	public String getComSerial() {
		return comSerial;
	}

	public void setComSerial(String comSerial) {
		this.comSerial = comSerial;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getUUID() {
		return uuid;
	}

	public void setUUID(String uUID) {
		uuid = uUID;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Override
	public String toString() {
		return "Header [requestType=" + requestType + ", comId=" + comId + ", from=" + from + ", orderSerial=" + orderSerial + ", comSerial="
				+ comSerial + ", productCode=" + productCode + ", sendTime=" + sendTime + ", responseCode=" + responseCode + ", errorMessage="
				+ errorMessage + ", UUID=" + uuid + "]";
	}

}
