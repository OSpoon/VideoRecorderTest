package com.n22.videorecordertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.title_center).setVisibility(View.VISIBLE);
        findViewById(R.id.list_layout).setOnClickListener(this);
        findViewById(R.id.new_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.list_layout:
                startActivity(new Intent(this, TabPageActivity.class));
                break;
            case R.id.new_layout:
                startActivity(new Intent(this, RecordActivity.class));
                break;
        }
    }
}

