package com.n22.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanxiaolin-n22 on 2017/7/26.
 */

public class NetPolicyVideoList implements Serializable{

    /**
     * insureImageUploadDetail : {"policyNo":"20170704548302036","contractNo":"00279109305208088","productName":"XXX人寿福满堂（尊享）年金保险","applicationName":"哈哈哈","insureName":"好汉歌","benefitName":null,"appDate":"2017-07-04 14:27:34.0","insureDate":"2017-07-04 00:00:00.0","policyStatus":"生效","upFileInfos":[{"fileid":"c1dedac337f143a986232a7ddea43272","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_132357.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_132357.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=RjyOIlLckeZ1CGYwxVPWLYPNJkY%3D","filetype":"3","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"ad0577dc98c84d20933e97b09a08a87e","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_132623.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_132623.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=WN/BMEftv8aGTUeoIGRO74sHIMQ%3D","filetype":"3","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"da1a5702868a45b4a2b767e49fd099ff","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_133643.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_133643.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=xMo10l69xFU7qTEn4ZVBBK12QR0%3D","filetype":"3","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"25cfe1b10bc44fdba0ef1de1a43b5e6a","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_132041.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_132041.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=XD6Q7eCqlGJeEc8zo6fsuiNENKw%3D","filetype":"3","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"4db7830a7d754f249b72ed86eb129453","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_134557.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_134557.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=%2BSx9n9Xtk4eFlkMUtp2xNNIkvNU%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"f7ebd12774b44e6496e25f394abceaf3","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_134844.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_134844.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=U1eY/d5097plioqns5hdvqPsri0%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"a7d9699a921240eb9d79d48efa052a91","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_135229.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_135229.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=DuYHsMLdzDwc3a2BWyQ12NCrF8A%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"22e75777d9474cf69379dbba83f864d6","systemid":null,"sourceid":null,"sourcetype":"1","filename":"video_2017_07_25_18_04_06.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/video_2017_07_25_18_04_06.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=HPi8GsfbpmtWSn8gRCuZY/0zH38%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"9a95145822ea473f8a906b11b2505d3c","systemid":null,"sourceid":null,"sourcetype":"1","filename":"video_2017_07_25_18_51_59.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/video_2017_07_25_18_51_59.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=FfrXBBUY6WTTRgtp7qsGZHW8NfI%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null}]}
     */

    private InsureImageUploadDetailBean insureImageUploadDetail;

    public InsureImageUploadDetailBean getInsureImageUploadDetail() {
        return insureImageUploadDetail;
    }

    public void setInsureImageUploadDetail(InsureImageUploadDetailBean insureImageUploadDetail) {
        this.insureImageUploadDetail = insureImageUploadDetail;
    }

    public static class InsureImageUploadDetailBean {
        /**
         * policyNo : 20170704548302036
         * contractNo : 00279109305208088
         * productName : XXX人寿福满堂（尊享）年金保险
         * applicationName : 哈哈哈
         * insureName : 好汉歌
         * benefitName : null
         * appDate : 2017-07-04 14:27:34.0
         * insureDate : 2017-07-04 00:00:00.0
         * policyStatus : 生效
         * upFileInfos : [{"fileid":"c1dedac337f143a986232a7ddea43272","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_132357.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_132357.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=RjyOIlLckeZ1CGYwxVPWLYPNJkY%3D","filetype":"3","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"ad0577dc98c84d20933e97b09a08a87e","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_132623.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_132623.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=WN/BMEftv8aGTUeoIGRO74sHIMQ%3D","filetype":"3","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"da1a5702868a45b4a2b767e49fd099ff","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_133643.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_133643.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=xMo10l69xFU7qTEn4ZVBBK12QR0%3D","filetype":"3","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"25cfe1b10bc44fdba0ef1de1a43b5e6a","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_132041.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_132041.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=XD6Q7eCqlGJeEc8zo6fsuiNENKw%3D","filetype":"3","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"4db7830a7d754f249b72ed86eb129453","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_134557.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_134557.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=%2BSx9n9Xtk4eFlkMUtp2xNNIkvNU%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"f7ebd12774b44e6496e25f394abceaf3","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_134844.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_134844.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=U1eY/d5097plioqns5hdvqPsri0%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"a7d9699a921240eb9d79d48efa052a91","systemid":null,"sourceid":null,"sourcetype":"1","filename":"VID_20170725_135229.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_135229.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=DuYHsMLdzDwc3a2BWyQ12NCrF8A%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"22e75777d9474cf69379dbba83f864d6","systemid":null,"sourceid":null,"sourcetype":"1","filename":"video_2017_07_25_18_04_06.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/video_2017_07_25_18_04_06.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=HPi8GsfbpmtWSn8gRCuZY/0zH38%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null},{"fileid":"9a95145822ea473f8a906b11b2505d3c","systemid":null,"sourceid":null,"sourcetype":"1","filename":"video_2017_07_25_18_51_59.mp4","filepath":"http://tpad-sl.oss-cn-szfinance.aliyuncs.com/video_2017_07_25_18_51_59.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=FfrXBBUY6WTTRgtp7qsGZHW8NfI%3D","filetype":"2","fileSuffix":null,"filenote":"双录影像","filesort":null,"status":null,"reactivate":null,"createtime":null,"updatetime":null,"createdby":null,"lastupdatedby":null,"description":"双录影像","fileamount":"1","benefitsort":null,"personname":null,"certtype":null,"fic":null,"checkNotes":null,"checkdesc":null,"fileData":null,"MD5":null,"applyCode":null,"tokenFlag":null,"upflag":null,"md5":null}]
         */

        private String policyNo;
        private String contractNo;
        private String productName;
        private String applicationName;
        private String insureName;
        private Object benefitName;
        private String appDate;
        private String insureDate;
        private String policyStatus;
        private List<UpFileInfosBean> upFileInfos;

        public String getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
        }

        public String getContractNo() {
            return contractNo;
        }

        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getApplicationName() {
            return applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public String getInsureName() {
            return insureName;
        }

        public void setInsureName(String insureName) {
            this.insureName = insureName;
        }

        public Object getBenefitName() {
            return benefitName;
        }

        public void setBenefitName(Object benefitName) {
            this.benefitName = benefitName;
        }

        public String getAppDate() {
            return appDate;
        }

        public void setAppDate(String appDate) {
            this.appDate = appDate;
        }

        public String getInsureDate() {
            return insureDate;
        }

        public void setInsureDate(String insureDate) {
            this.insureDate = insureDate;
        }

        public String getPolicyStatus() {
            return policyStatus;
        }

        public void setPolicyStatus(String policyStatus) {
            this.policyStatus = policyStatus;
        }

        public List<UpFileInfosBean> getUpFileInfos() {
            return upFileInfos;
        }

        public void setUpFileInfos(List<UpFileInfosBean> upFileInfos) {
            this.upFileInfos = upFileInfos;
        }

        public static class UpFileInfosBean {
            /**
             * fileid : c1dedac337f143a986232a7ddea43272
             * systemid : null
             * sourceid : null
             * sourcetype : 1
             * filename : VID_20170725_132357.mp4
             * filepath : http://tpad-sl.oss-cn-szfinance.aliyuncs.com/VID_20170725_132357.mp4?Expires=1501042018&OSSAccessKeyId=LTAIOWA60snS86OB&Signature=RjyOIlLckeZ1CGYwxVPWLYPNJkY%3D
             * filetype : 3
             * fileSuffix : null
             * filenote : 双录影像
             * filesort : null
             * status : null
             * reactivate : null
             * createtime : null
             * updatetime : null
             * createdby : null
             * lastupdatedby : null
             * description : 双录影像
             * fileamount : 1
             * benefitsort : null
             * personname : null
             * certtype : null
             * fic : null
             * checkNotes : null
             * checkdesc : null
             * fileData : null
             * MD5 : null
             * applyCode : null
             * tokenFlag : null
             * upflag : null
             * md5 : null
             */

            private String fileid;
            private Object systemid;
            private Object sourceid;
            private String sourcetype;
            private String filename;
            private String filepath;
            private String filetype;
            private Object fileSuffix;
            private String filenote;
            private Object filesort;
            private Object status;
            private Object reactivate;
            private Object createtime;
            private Object updatetime;
            private Object createdby;
            private Object lastupdatedby;
            private String description;
            private String fileamount;
            private Object benefitsort;
            private Object personname;
            private Object certtype;
            private Object fic;
            private Object checkNotes;
            private Object checkdesc;
            private Object fileData;
            private Object MD5;
            private Object applyCode;
            private Object tokenFlag;
            private Object upflag;
            private Object md5;

            public String getFileid() {
                return fileid;
            }

            public void setFileid(String fileid) {
                this.fileid = fileid;
            }

            public Object getSystemid() {
                return systemid;
            }

            public void setSystemid(Object systemid) {
                this.systemid = systemid;
            }

            public Object getSourceid() {
                return sourceid;
            }

            public void setSourceid(Object sourceid) {
                this.sourceid = sourceid;
            }

            public String getSourcetype() {
                return sourcetype;
            }

            public void setSourcetype(String sourcetype) {
                this.sourcetype = sourcetype;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getFilepath() {
                return filepath;
            }

            public void setFilepath(String filepath) {
                this.filepath = filepath;
            }

            public String getFiletype() {
                return filetype;
            }

            public void setFiletype(String filetype) {
                this.filetype = filetype;
            }

            public Object getFileSuffix() {
                return fileSuffix;
            }

            public void setFileSuffix(Object fileSuffix) {
                this.fileSuffix = fileSuffix;
            }

            public String getFilenote() {
                return filenote;
            }

            public void setFilenote(String filenote) {
                this.filenote = filenote;
            }

            public Object getFilesort() {
                return filesort;
            }

            public void setFilesort(Object filesort) {
                this.filesort = filesort;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getReactivate() {
                return reactivate;
            }

            public void setReactivate(Object reactivate) {
                this.reactivate = reactivate;
            }

            public Object getCreatetime() {
                return createtime;
            }

            public void setCreatetime(Object createtime) {
                this.createtime = createtime;
            }

            public Object getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(Object updatetime) {
                this.updatetime = updatetime;
            }

            public Object getCreatedby() {
                return createdby;
            }

            public void setCreatedby(Object createdby) {
                this.createdby = createdby;
            }

            public Object getLastupdatedby() {
                return lastupdatedby;
            }

            public void setLastupdatedby(Object lastupdatedby) {
                this.lastupdatedby = lastupdatedby;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getFileamount() {
                return fileamount;
            }

            public void setFileamount(String fileamount) {
                this.fileamount = fileamount;
            }

            public Object getBenefitsort() {
                return benefitsort;
            }

            public void setBenefitsort(Object benefitsort) {
                this.benefitsort = benefitsort;
            }

            public Object getPersonname() {
                return personname;
            }

            public void setPersonname(Object personname) {
                this.personname = personname;
            }

            public Object getCerttype() {
                return certtype;
            }

            public void setCerttype(Object certtype) {
                this.certtype = certtype;
            }

            public Object getFic() {
                return fic;
            }

            public void setFic(Object fic) {
                this.fic = fic;
            }

            public Object getCheckNotes() {
                return checkNotes;
            }

            public void setCheckNotes(Object checkNotes) {
                this.checkNotes = checkNotes;
            }

            public Object getCheckdesc() {
                return checkdesc;
            }

            public void setCheckdesc(Object checkdesc) {
                this.checkdesc = checkdesc;
            }

            public Object getFileData() {
                return fileData;
            }

            public void setFileData(Object fileData) {
                this.fileData = fileData;
            }

            public Object getMD5() {
                return MD5;
            }

            public void setMD5(Object MD5) {
                this.MD5 = MD5;
            }

            public Object getApplyCode() {
                return applyCode;
            }

            public void setApplyCode(Object applyCode) {
                this.applyCode = applyCode;
            }

            public Object getTokenFlag() {
                return tokenFlag;
            }

            public void setTokenFlag(Object tokenFlag) {
                this.tokenFlag = tokenFlag;
            }

            public Object getUpflag() {
                return upflag;
            }

            public void setUpflag(Object upflag) {
                this.upflag = upflag;
            }

            public Object getMd5() {
                return md5;
            }

            public void setMd5(Object md5) {
                this.md5 = md5;
            }
        }
    }
}
