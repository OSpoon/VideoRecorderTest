package com.n22.bean.net;

/**
 * 请求包格式
 * Copyright: 版权所有 
 * Company: 北京耀诚立信科技有限公司
 *
 * @author 陈振国
 * @version 1.0.0
 * @create 2015年10月30日
 */
public class PackageResp extends BaseBean {

	private static final long serialVersionUID = 1L;
	//请求头信息
	private Header header;
	//请求参数
	private String response;
	//响应
//	private String response;
	
//	public Package() {
//	}
	
	
	public PackageResp(Header header, String request){
		super();
		this.header = header;
		this.response = request;
	}


	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public String getRequest() {
		return response;
	}
	public void setRequest(String request) {
		this.response = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
