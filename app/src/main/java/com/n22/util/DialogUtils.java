package com.n22.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.n22.videorecordertest.R;

/**
 * Created by zhanxiaolin-n22 on 2017/7/22.
 */

public class DialogUtils {
    public static AlertDialog.Builder getDialog(Activity activity, @NonNull String mag) {
        return new AlertDialog.Builder(activity).setIcon(R.mipmap.icon_info).setTitle("温馨提示").setMessage(mag).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false);
    }
}
