package com.n22.bean.net;

import java.io.Serializable;

import com.n22.util.JsonUtil;
import com.n22.util.encoder.Config;
import com.n22.util.encoder.ThreeDES;


public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 将当前对象转换成json字符串
	 * @return
	 */
	public String toJsonString(){
		return JsonUtil.objectToJson(this);
	}
	
	/**
	 * 将当前对象转换成json字符串，并加密
	 * @return
	 * @throws Exception 
	 */
	public String toEncodeString() throws Exception{
		return ThreeDES.encode(toJsonString(), Config.ORG_VALIDATE_CODE).replace("\n", "");
	}
}
