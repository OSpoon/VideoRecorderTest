package com.n22.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by zhanxiaolin-n22 on 2017/7/21.
 */

public class Policy extends DataSupport {

    private String insured;//被保人暂时只模拟保存姓名
    private String applicant;//投保人暂时只模拟保存姓名
    private String policyCode;//保单号
    private String product;//产品名称
    private String insureTime;//投保时间

    @Override
    public long getBaseObjId() {
        return super.getBaseObjId();
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getInsureTime() {
        return insureTime;
    }

    public void setInsureTime(String insureTime) {
        this.insureTime = insureTime;
    }
}
