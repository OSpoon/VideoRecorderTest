package com.n22.videorecordertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.n22.widget.ProgressWebView;

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
}

