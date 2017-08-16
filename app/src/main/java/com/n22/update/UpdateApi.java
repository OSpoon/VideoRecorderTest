package com.n22.update;

import com.google.gson.Gson;
import com.n22.bean.AppBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * XRClient 网络交互类
 *
 * @author spoon
 * @version 1.0.0
 * @created at 2016/9/4
 */
public class UpdateApi {

    /**
     * 检验App版本
     *
     * @author spoon
     * @created at 2016/9/4
     * @version 1.0.0
     */
    public static void checkUpdate(final OnCheckUpdateListener listener) {
        OkHttpUtils.get().url("http://api.fir.im/apps/latest/59849d81ca87a803df000388?api_token=0ac334a22e69c2345a5cec8e1674fff9&type=android").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                listener.onNoUpdateAvailable();
            }

            @Override
            public void onResponse(String response, int id) {
                listener.onUpdateAvailable(new Gson().fromJson(response, AppBean.class));
            }
        });
    }

    public interface OnCheckUpdateListener {
        void onNoUpdateAvailable();

        void onUpdateAvailable(AppBean appBean);
    }
}
