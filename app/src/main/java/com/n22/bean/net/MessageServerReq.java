package com.n22.bean.net;

import java.io.Serializable;
/**
 * 影像件回传服务端请求报文
 * @author huashuke-n22
 *
 */
import java.util.List;


public class MessageServerReq implements Serializable {

	public List<AppSysFileInfo> appSysFileInfo;
	/** 投保单号 */
	public String applyCode;
	/** 获取token */
	public String tokenFlag;
	/** 源ID:保单id */
	public String sourceid;// policyId
	/** 上传文件总数 */
	public int fileamount;

	public MessageServerReq() {
		super();
	}

	public MessageServerReq(List<AppSysFileInfo> list, String applyCode, String sourceid, String tokenFlag, int fileamount) {
		super();
		this.appSysFileInfo = list;
		this.applyCode = applyCode;
		this.tokenFlag = tokenFlag;
		this.sourceid = sourceid;
		this.fileamount = fileamount;
	}

	public int getFileamount() {
		return fileamount;
	}

	public void setFileamount(int fileamount) {
		this.fileamount = fileamount;
	}

	public List<AppSysFileInfo> getList() {
		return appSysFileInfo;
	}

	public void setList(List<AppSysFileInfo> list) {
		this.appSysFileInfo = list;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public String getTokenFlag() {
		return tokenFlag;
	}

	public void setTokenFlag(String tokenFlag) {
		this.tokenFlag = tokenFlag;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

}
