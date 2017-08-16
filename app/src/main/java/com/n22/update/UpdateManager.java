package com.n22.update;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;

import com.n22.bean.AppBean;
import com.n22.update.service.DownloadService;
import com.n22.util.DialogHelp;

/**
 * 更新管理类
 *
 * @author spoon
 * @version 1.0.0
 * @created at 2016/9/4
 */
public class UpdateManager {

    private AppBean mAppBean;

    private Context mContext;

    private boolean isShow = false;

    private ProgressDialog _waitDialog;

    //检查更新结果回调接口
    UpdateApi.OnCheckUpdateListener checkUpdateListener = new UpdateApi.OnCheckUpdateListener() {
        @Override
        public void onNoUpdateAvailable() {
            hideCheckDialog();
            if (isShow) {
                showFaileDialog();
            }
        }

        @Override
        public void onUpdateAvailable(AppBean appBean) {
            hideCheckDialog();
            mAppBean = appBean;
            onFinshCheck();
        }
    };

    public UpdateManager(Context context, boolean isShow) {
        this.mContext = context;
        this.isShow = isShow;
    }

    public void checkUpdate() {
        if (isShow) {
            showCheckDialog();
        }
        UpdateApi.checkUpdate(checkUpdateListener);
    }

    /**
     * 检查版本
     *
     * @author spoon
     * @created at 2016/9/4
     * @version 1.0.0
     */
    public boolean haveNew() {
        if (this.mAppBean == null) {
            return false;
        }
        boolean haveNew = false;
        int curVersionCode = getAppVersionCode(mContext.getPackageName());
        if (curVersionCode < Integer.valueOf(mAppBean.getVersion())) {
            haveNew = true;
        }
        return haveNew;
    }

    private void onFinshCheck() {
        if (haveNew()) {
            showUpdateInfo();
        } else {
            if (isShow) {
                showLatestDialog();
            }
        }
    }

    private void showCheckDialog() {
        if (_waitDialog == null) {
            _waitDialog = DialogHelp.getWaitDialog((Activity) mContext, "正在获取新版本信息...");
        }
        _waitDialog.show();
    }

    private void hideCheckDialog() {
        if (_waitDialog != null) {
            _waitDialog.dismiss();
        }
    }

    private void showUpdateInfo() {
        if (mAppBean == null) {
            return;
        }
        AlertDialog.Builder dialog = DialogHelp.getConfirmDialog(mContext, mAppBean.getChangelog(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openDownLoadService(mContext, mAppBean.getInstall_url(), mAppBean.getVersion());
            }
        });
        dialog.setTitle("发现新版本");
        dialog.show();
    }

    private void showLatestDialog() {
//        DialogHelp.getMessageDialog(mContext, "已经是新版本了").show();
    }

    private void showFaileDialog() {
        DialogHelp.getMessageDialog(mContext, "网络异常，无法获取新版本信息").show();
    }

    public static void openDownLoadService(Context context, String downurl,
                                           String tilte) {
        final DownloadService.ICallbackResult callback = new DownloadService.ICallbackResult() {

            @Override
            public void OnBackResult(Object s) {
            }
        };
        ServiceConnection conn = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                binder.addCallback(callback);
                binder.start();
            }
        };
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, downurl);
        intent.putExtra(DownloadService.BUNDLE_KEY_TITLE, tilte);
        context.startService(intent);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 获取App版本码
     *
     * @param packageName 包名
     * @return App版本码
     */
    private int getAppVersionCode(String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
