package com.lwm.healthrecuperationapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.jsbridge.BridgeHandler;
import com.lwm.healthrecuperationapp.jsbridge.BridgeWebView;
import com.lwm.healthrecuperationapp.jsbridge.CallBackFunction;

/**
 * 加载网页的Activity
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends BaseActivity {

    private BridgeWebView mBridgeWebView;
    private String url;

    @Override
    protected int initLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        mBridgeWebView = findViewById(R.id.bridgewebview);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
        }
        registJavaHandler();
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = mBridgeWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mBridgeWebView.loadUrl(url);
    }

    private void registJavaHandler() {
        mBridgeWebView.registerHandler("goback", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
    }
}