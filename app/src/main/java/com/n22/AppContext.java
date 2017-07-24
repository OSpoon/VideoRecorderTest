package com.n22;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.simple.CrashCanary;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by zhanxiaolin-n22 on 2017/7/21.
 */

public class AppContext extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        //初始化数据库
        LitePal.initialize(this);
        final CrashCanary crashCanary = new CrashCanary.Builder(this)
                .setExceptionHandler(new Thread.UncaughtExceptionHandler() {  // 设置自己的异常处理 Handler
                    @Override
                    public void uncaughtException(Thread thread, Throwable ex) {

                    }
                })
                .setKillProcessWhenCrash(true)      // 发生crash之后关闭应用,再次进入应用时会有crash通知
                .build();
    }
}
