package com.n22.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.n22.bean.RecordInfo;
import com.n22.bean.net.AppSysFileInfo;
import com.n22.bean.net.JsonBeanResp;
import com.n22.bean.net.MessageServerReq;
import com.n22.bean.net.PackageResp;
import com.n22.uploading.OSSUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanxiaolin-n22 on 2017/7/24.
 */

public class OssHelper {
    private OSSUtils ossUtil;
    private ProgressDialog mProgressDialog;
    private Activity activity;
    private static final int ERR = 101;// 回传服务端失败
    private List<AppSysFileInfo> lists;
    private RecordInfo recordInfo;

    //处理响应结果
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    dismissProgressDialog();
                    upload(recordInfo);
                    break;
                case 0x4332:
                    DialogUtils.getDialog(activity, (String) msg.obj).create().show();
                    break;
                case ERR:
                    if (msg.obj != null)
                        DialogUtils.getDialog(activity, (String) msg.obj).create().show();
                    break;
                case 0x2345:
                    FileUtils.deleteFile(recordInfo.getVideotapePath());
                    DataSupport.delete(RecordInfo.class, Long.parseLong(recordInfo.getId()));
                    if (onDataComplete!=null)
                        onDataComplete.complete();
                    break;
            }
        }
    };


    public void initHelper(final Activity activity, RecordInfo recordInfo) {
        this.activity = activity;
        this.recordInfo = recordInfo;
        ossUtil = new OSSUtils(activity, new BeanUtilImpl());
        ossUtil.setOssCompletedCallback(ossCompletedCallback);
        initProgress(activity);
        showPressDialog();
        new Thread() {
            @Override
            public void run() {
                super.run();
                boolean is = ossUtil.initOSS();
                if (is) {
                    handler.sendEmptyMessage(0x123);
                }
            }
        }.start();
    }

    public void upload(@NonNull RecordInfo recordInfo) {
        showPressDialog();
        if (FileUtils.isFile(recordInfo.getVideotapePath())) {
            lists = initData(recordInfo);
            ossUtil.size = lists.size();
            MessageServerReq messageServerReq = new MessageServerReq(lists, recordInfo.getPolicy().getPolicyCode(), recordInfo.getPolicy().getSourceid(), "0", 1);
            AppSysFileInfo info = lists.get(0);
            ossUtil.putObjectOSSDouble(FileUtils.getFileName(recordInfo.getVideotapePath()), recordInfo.getVideotapePath(), info, messageServerReq);
        }
    }

    private List<AppSysFileInfo> initData(RecordInfo recordInfo) {
        List<AppSysFileInfo> list = new ArrayList<AppSysFileInfo>();
        AppSysFileInfo appSysFileInfo = new AppSysFileInfo();
        /** 源ID */
        appSysFileInfo.sourceid = recordInfo.getPolicy().getSourceid();// policycode
        /** 源类型 */
        appSysFileInfo.sourcetype = "1";// 1234
        /** 文件名称 */
        appSysFileInfo.filename = FileUtils.getFileName(recordInfo.getVideotapePath());
        /** 文件类型 */
        appSysFileInfo.filetype = "2";// 1yin2shi3ying
        /** 文件排序 */
        appSysFileInfo.filesort = 1;// shuliang
        /** 描述 */
        appSysFileInfo.description = "双录影像";
        /** 上传文件总数 */
        appSysFileInfo.fileamount = 1;
        /** 受益人顺序1,2,3 */
        appSysFileInfo.benefitsort = "";
        /** 投保人,被保人受益人名称 */
        appSysFileInfo.personname = "";// name
        /** 受益人证件类型 */
        appSysFileInfo.certtype = "";
        /** 上传数据 */
        appSysFileInfo.fileData = recordInfo.getVideotapePath();
        /** 上传文件的MD5值 */
        appSysFileInfo.MD5 = FileUtils.getFileMD5ToString(recordInfo.getVideotapePath());
        /** 获取token */
        appSysFileInfo.tokenFlag = "0";
        /** 0 签字影像 1 双录影像 2投保影像 3音频 4 视频 */
        appSysFileInfo.upflag = "4";
        list.add(appSysFileInfo);
        return list;
    }

    /***
     * 该回调方法用于两处: 1.上传文件oss成功回调 2.全部上传oss完成后回传服务端后回调该方法
     */
    private OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback = new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

        @Override
        public void onSuccess(PutObjectRequest arg0, PutObjectResult arg1) {
            dismissProgressDialog();
            // 上传成功回调
            Message message = Message.obtain();
            /** 1.oss上传成功回传服务端由于 服务端封装报文格式必须解析 */
            if (arg1.getServerCallbackReturnBody() != null) {
                String result = arg1.getServerCallbackReturnBody();
                JsonBeanResp jsonBean = null;
                jsonBean = (JsonBeanResp) JsonUtil.jsonToObject(result, JsonBeanResp.class);
                PackageResp packages = jsonBean.packageList.getPackages();
                if (packages.getHeader().isSuccess()) {
                    // 成功处理
                    message.what = 0x2345;
                } else {
                    // 失败处理
                    String errMessage = packages.getHeader().getErrorMessage();
                    message.what = ERR;
                    message.obj = errMessage;
                }
            }
            handler.sendMessage(message);
        }

        @Override
        public void onFailure(PutObjectRequest arg0, ClientException arg1, ServiceException arg2) {
            dismissProgressDialog();
            // 失败时回调
            Message msg = Message.obtain();
            msg.what = 0x4332;
            if (null != arg1 && arg1.getMessage() != null) {
                msg.obj = arg1.getMessage();
            }
            if (null != arg2 && arg2.getMessage() != null) {
                msg.obj = arg2.getMessage();
            }
            handler.sendMessage(msg);
        }
    };

    /**
     * 初始化进度条
     */
    public void initProgress(Context aContext) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(aContext);
        }
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("温馨提示");
        mProgressDialog.setMessage("请等待...");
    }

    public void showPressDialog() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.show();
    }


    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void updateProgressDialog(int progress) {
        if (mProgressDialog != null) {
            if (!mProgressDialog.isShowing()) {
                showPressDialog();
            }
            mProgressDialog.setProgress(progress);
        }
    }

    private OnDataComplete onDataComplete;

    public void setOnDataComplete(OnDataComplete dataComplete) {
        this.onDataComplete = dataComplete;
    }

    public interface OnDataComplete {
        void complete();
    }
}
