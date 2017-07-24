package com.n22.videorecordertest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.n22.bean.Policy;
import com.n22.bean.RecordInfo;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1;
    private static final long ERROR = -1;

    private TextView nameView;
    private TextView pwdView;
    private TextView notice;

    private String name;
    private String pwd;
    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.title_center).setVisibility(View.VISIBLE);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        nameView = (TextView) findViewById(R.id.name);
        pwdView = (TextView) findViewById(R.id.pwd);
        notice = (TextView) findViewById(R.id.notice);
        nameView.setText("abc");
        pwdView.setText("a123456");
        name = getResources().getString(R.string.username);
        pwd = getResources().getString(R.string.pwd);
        notice.setText("© 2017 N22. All rights reserved.\n账号：" + name + " 密码：" + pwd);
//        notice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Connector.getDatabase();
//                LitePal.deleteDatabase("Record_Info.db");
//                for (int i = 1; i < 50; i++) {
//                    RecordInfo recordInfo = new RecordInfo();
//                    recordInfo.setAuthor("abc");
//                    long millis = System.currentTimeMillis();
//                    recordInfo.setUpdateTime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(millis)));
//                    recordInfo.setUpdate(millis);
//                    recordInfo.setVideotapePath(Environment.getExternalStorageDirectory().getPath() + "/VideoRecorderTest/ceshi" + i + ".mp4");
//                    Policy policy = new Policy();
//                    policy.setInsured("被保人" + i);//被保人暂时只模拟保存姓名
//                    policy.setApplicant("投保人" + i);//投保人暂时只模拟保存姓名
//                    policy.setPolicyCode("M1019292039" + i);//保单号
//                    policy.setPolicyId("1019292039" + i);
//                    policy.setProduct("最新型XXX保险");//产品名称
//                    policy.setInsureTime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(millis)));//投保时间
//                    policy.save();
//                    recordInfo.setPolicy(policy);
//                    recordInfo.save();
//                }
//            }
//        });
//        askPermission();
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void login() {
        String msg = "";
        String inputName = nameView.getText().toString();
        String inputPwd = pwdView.getText().toString();
        if (inputName.trim().equals("")) {
            msg = "用户名不能为空";
            // for show
//        } else if(inputPwd.trim().equals("")){
//            msg = "密码不能为空";
//        } else if(!name.equals(inputName) || !pwd.equals(inputPwd)){
//            msg = getResources().getString(R.string.login_wrong);
        } else {
            askPermission();
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void askPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> pers = new ArrayList<>();
            for (String permission : permissions) {
                if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    pers.add(permission);
                }
            }
            if (pers.size() == 0) {
                toMain();
            } else {
                String[] asks = new String[pers.size()];
                pers.toArray(asks);
                Log.d("ask", asks.toString());
                this.requestPermissions(asks, PERMISSION_CODE);
            }
        } else {
            toMain();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_CODE) {
            return;
        }
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                askPermission();
                return;
            }
        }
        toMain();
    }

    private void toMain() {
        long availableSize = getAvailableExternalMemorySize();
        if (availableSize <= 1024L * 1024 * 1024 * 1) {
            showWarn();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void showWarn() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage("您的手机存储空间小于1G，请清理存储后重试");
        dialog.setButton(Dialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }
}
