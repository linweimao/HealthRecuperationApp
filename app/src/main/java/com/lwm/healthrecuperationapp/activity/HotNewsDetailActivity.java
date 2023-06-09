package com.lwm.healthrecuperationapp.activity;

import android.webkit.WebView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.util.Constant;

/**
 * 显示新闻详情
 */
public class HotNewsDetailActivity extends BaseActivity {

    private WebView mHotNewsDetailWebview;

    @Override
    protected int initLayout() {
        return R.layout.activity_hot_news_detail;
    }

    @Override
    protected void initView() {
        mHotNewsDetailWebview = (WebView) findViewById(R.id.hot_news_detail_webview);
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra(Constant.URL);
        mHotNewsDetailWebview.loadUrl(url);
    }
}