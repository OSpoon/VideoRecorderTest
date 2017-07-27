package com.n22;

import android.app.Application;
import android.util.Log;
//import android.support.multidex.MultiDex;

import com.n22.util.Utils;
import com.simple.CrashCanary;
import com.zhy.http.okhttp.OkHttpUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by zhanxiaolin-n22 on 2017/7/21.
 */

public class AppContext extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
        Utils.init(this);
        //初始化数据库
        LitePal.initialize(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        final CrashCanary crashCanary = new CrashCanary.Builder(this)
                .setExceptionHandler(new Thread.UncaughtExceptionHandler() {  // 设置自己的异常处理 Handler
                    @Override
                    public void uncaughtException(Thread thread, Throwable ex) {
                        Log.e("CrashCanary",ex.toString());
                    }
                })
                .setKillProcessWhenCrash(true)      // 发生crash之后关闭应用,再次进入应用时会有crash通知
                .build();
    }
}
