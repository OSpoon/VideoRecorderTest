package com.n22.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by zhanxiaolin-n22 on 2017/7/21.
 */

public class RecordInfo extends DataSupport {
    private String author;//添加的作者为登录用户
    private String updateTime;//更新时间 yyyy/mm/dd hh/mm/ss
    private long update;//更新时间 yyyy/mm/dd hh/mm/ss
    private String videotapePath;//录像本地路径
    private Policy policy;//扫面二维码绑定保单信息

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return String.valueOf(super.getBaseObjId());
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getVideotapePath() {
        return videotapePath;
    }

    public void setVideotapePath(String videotapePath) {
        this.videotapePath = videotapePath;
    }

    public Policy getPolicy() {
        List<Policy> policies = DataSupport.where("recordinfo_id = ?", String.valueOf(getBaseObjId())).find(Policy.class);
        if (policies!=null&&policies.size()>0)
            return policies.get(0);
        return null;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public long getUpdate() {
        return update;
    }

    public void setUpdate(long update) {
        this.update = update;
    }


}
