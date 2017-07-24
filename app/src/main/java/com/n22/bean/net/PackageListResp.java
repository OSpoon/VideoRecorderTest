package com.n22.bean.net;

public class PackageListResp extends BaseBean {

	private static final long serialVersionUID = 1L;
	private PackageResp packages;

	public PackageListResp() {
		super();
	}

	public PackageListResp(PackageResp packages) {
		super();
		this.packages = packages;
	}

	public PackageResp getPackages() {
		return packages;
	}

	public void setPackages(PackageResp packages) {
		this.packages = packages;
	}

}
