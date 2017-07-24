package com.n22.bean.net;

import java.io.Serializable;
import java.util.Date;

public class AppSysFileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9L;
	/** fileID */
	public String fileid;// no
	/** 系统标示 */
	public String systemid;// no
	/** 源ID */
	public String sourceid;// policycode
	/** 源类型 */
	public String sourcetype;// 1234
	/** 文件名称 */
	public String filename;
	/** 文件路径 */
	public String filepath;// no
	/** 文件类型 */
	public String filetype;// 1yin2shi3ying
	/** 文件扩展名 */
	public String fileSuffix;// no
	/** 文件备注 */
	public String filenote;// no
	/** 文件排序 */
	public int filesort;// shuliang
	/** 状态 */
	public String status;// no
	/** 创建时间 */
	public Date createtime;// no
	/** 更新时间 */
	public Date updatetime;// no
	/** 创建人 */
	public String createdby;// no
	/** 最后修改人 */
	public String lastupdatedby;// no
	/** 描述 */
	public String description;
	/** 上传文件总数 */
	public int fileamount;
	/** 受益人顺序1,2,3 */
	public String benefitsort;
	/** 投保人,被保人受益人名称 */
	public String personname;// name
	/** 受益人证件类型 */
	public String certtype;
	/** 上传数据 */
	public String fileData;
	/** 上传文件的MD5值 */
	public String MD5;
	/** 获取token */
	public String tokenFlag;
	/** 0 签字影像 1 双录影像 2投保影像 3音频 4 视频 */
	public String upflag;
}
