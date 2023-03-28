package com.lwm.healthrecuperationapp.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.adapter.HealthAdapter;
import com.lwm.healthrecuperationapp.entity.News;
import com.lwm.healthrecuperationapp.entity.NewsInfo;
import com.lwm.healthrecuperationapp.util.Constant;

import java.util.Arrays;
import java.util.List;

public class HealthArticleListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = HealthArticleListActivity.class.getSimpleName();
    private ImageView mImgHealthArticleListReturn;
    private RecyclerView mRvHealthArticleList;
    private LinearLayoutManager mLinearLayoutManager;
    private HealthAdapter mHealthAdapter;
    private News mNews;
    private List<NewsInfo> mHealthArticleList;

    @Override
    protected int initLayout() {
        return R.layout.activity_health_article_list;
    }

    @Override
    protected void initView() {
        mImgHealthArticleListReturn = (ImageView) findViewById(R.id.img_health_article_list_return);
        mRvHealthArticleList = (RecyclerView) findViewById(R.id.rv_health_article_list);
        mImgHealthArticleListReturn.setOnClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(HealthArticleListActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvHealthArticleList.setLayoutManager(mLinearLayoutManager);
        mHealthAdapter = new HealthAdapter(HealthArticleListActivity.this);
        mRvHealthArticleList.setAdapter(mHealthAdapter);
        mHealthAdapter.setOnItemClick(new HealthAdapter.OnItemClick() {
            @Override
            public void onclick(View view) {
                int position = mRvHealthArticleList.getChildAdapterPosition(view);
                Log.i(TAG, "onclick：position=" + position);
                String newsUrl = mHealthArticleList.get(position).getUrl();
                Intent intent = new Intent(HealthArticleListActivity.this, HotNewsDetailActivity.class);
                intent.putExtra(Constant.URL, newsUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mNews = (News) getIntent().getSerializableExtra("news_data");
        NewsInfo[] data = mNews.getResult().getData();
        mHealthArticleList = Arrays.asList(data);
        mHealthAdapter.setDatas(mHealthArticleList);
        mHealthAdapter.notifyDataSetChanged(); // 通知 RecyclerView 刷新页面(刷新数据)
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_health_article_list_return: // 返回按钮
                finish();
                break;
        }
    }
}