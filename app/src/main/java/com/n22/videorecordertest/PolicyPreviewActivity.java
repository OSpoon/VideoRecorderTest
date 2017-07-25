package com.n22.videorecordertest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.n22.bean.RecordInfo;
import com.n22.util.DialogUtils;
import com.n22.util.NetWorkUtils;
import com.n22.util.OssHelper;
import com.n22.widget.ProgressWebView;
import com.n22.zxing.activity.CaptureActivity;

import org.litepal.crud.DataSupport;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by zhanxiaolin-n22 on 2017/7/22.
 */

public class PolicyPreviewActivity extends AppCompatActivity {

    private static final String TAG = "PolicyPreviewActivity";
    public static final String POLICY_CODE = "policy_code";
    private ProgressWebView web;

    public static void start(Context context, String num) {
        Intent intent = new Intent(context, PolicyPreviewActivity.class);
        intent.putExtra(POLICY_CODE, num);
        context.startActivity(intent);
    }

    public static void start(Fragment fragment, int code, String num) {
        Intent intent = new Intent(fragment.getActivity(), PolicyPreviewActivity.class);
        intent.putExtra(POLICY_CODE, num);
        fragment.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policypreview);
        initView();
    }

    private void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        findViewById(R.id.title_center).setVisibility(View.GONE);
        String policy_code = getIntent().getStringExtra(POLICY_CODE);
        mToolbar.setTitle("保单查看,编号:" + policy_code);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PolicyPreviewActivity.super.onBackPressed();
            }
        });
        //此处只是数据库ID
        final RecordInfo recordInfo = DataSupport.find(RecordInfo.class, Long.parseLong(policy_code));
        JCVideoPlayerStandard player = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        String code = null;
        if (recordInfo.getPolicy() != null) {
            code = recordInfo.getPolicy().getPolicyCode();
        } else {
            code = policy_code;
        }
        player.setUp(recordInfo.getVideotapePath(), JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "投保单号:" + code);
        Button upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkUtils.isConnected()) {
                    if (NetWorkUtils.getNetworkType() == NetWorkUtils.NetworkType.NETWORK_WIFI) {
                        uploadData(recordInfo);
                    } else {
                        DialogUtils.getDialog(PolicyPreviewActivity.this, "系统检测到当前网络非Wifi状态,\n可能消耗过多流量,\n确认上传此影像件吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uploadData(recordInfo);
                            }
                        }).create().show();
                    }
                } else {
                    DialogUtils.getDialog(PolicyPreviewActivity.this, "系统检测到当前无网络,请稍后再试。").create().show();
                }
            }
        });
        web = (ProgressWebView) findViewById(R.id.web);
        web.setOnHtmlEventListener(new ProgressWebView.OnHtmlEventListener() {
            @Override
            public void onFinished(String html) {
            }

            @Override
            public void onUriLoading(Uri uri) {
            }
        });
        web.loadUrl("file:///android_asset/policy_yinbao.html");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    public void uploadData(final RecordInfo recordInfo) {
        DialogUtils.getDialog(PolicyPreviewActivity.this, "确认上传此影像件吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OssHelper ossHelper = new OssHelper();
                ossHelper.initHelper(PolicyPreviewActivity.this, recordInfo);
                ossHelper.setOnDataComplete(new OssHelper.OnDataComplete() {
                    @Override
                    public void complete() {
                        DialogUtils.getDialog(PolicyPreviewActivity.this, "上传完成,点击取消即可。").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PolicyPreviewActivity.this.setResult(RESULT_OK);
                                PolicyPreviewActivity.this.finish();
                            }
                        }).create().show();
                    }
                });

            }
        }).create().show();
    }
}

