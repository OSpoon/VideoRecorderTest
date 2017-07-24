package com.n22.bean.net;

/**
 * @Describe: Json的根对象封装类
 * @Copyright: 版权所有
 * @Company: 北京耀诚立信科技有限公司
 *
 * @author HanXiaoqiang-N22
 * @version 1.0.0
 * @create 2015年11月9日
 */
public class JsonBeanResp extends BaseBean {

	private static final long serialVersionUID = 1L;

	public PackageListResp packageList;

	public JsonBeanResp(PackageListResp packageList) {
		super();
		this.packageList = packageList;
	}

}
