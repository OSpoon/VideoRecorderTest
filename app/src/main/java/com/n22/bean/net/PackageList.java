package com.n22.bean.net;

public class PackageList extends BaseBean {

	private static final long serialVersionUID = 1L;
	private Package packages;

	public PackageList() {
		super();
	}

	public PackageList(Package packages) {
		super();
		this.packages = packages;
	}

	public Package getPackages() {
		return packages;
	}

	public void setPackages(Package packages) {
		this.packages = packages;
	}

}
