package com.n22;

import android.app.Application;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by zhanxiaolin-n22 on 2017/7/21.
 */

public class AppContext extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        LitePal.initialize(this);
    }
}
