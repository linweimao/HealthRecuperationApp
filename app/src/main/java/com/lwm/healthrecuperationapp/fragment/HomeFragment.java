package com.lwm.healthrecuperationapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.activity.EmergencyCallActivity;
import com.lwm.healthrecuperationapp.activity.FlashActivity;
import com.lwm.healthrecuperationapp.activity.HealthArticleListActivity;
import com.lwm.healthrecuperationapp.activity.HotNewsDetailActivity;
import com.lwm.healthrecuperationapp.activity.SelectCityActivity;
import com.lwm.healthrecuperationapp.adapter.HealthAdapter;
import com.lwm.healthrecuperationapp.api.Api;
import com.lwm.healthrecuperationapp.api.RequestCallback;
import com.lwm.healthrecuperationapp.entity.News;
import com.lwm.healthrecuperationapp.entity.NewsInfo;
import com.lwm.healthrecuperationapp.util.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实用工具 Fragment
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private TextView mTvHiName; // 名字
    private LinearLayout mLineNursingList; // 护工列表
    private LinearLayout mLineMedicationReminder; // 服药提醒
    private LinearLayout mLineVideoCall; // 视频通话
    private LinearLayout mLineWeatherInfo; // 天气信息
    private LinearLayout mLineFlashlight; // 手电筒
    private LinearLayout mLineViewAll; // 查看全部
    private RecyclerView mRvHealthArticle; // 健康文章列表
    private String mName; // 用户名
    private LinearLayoutManager mLinearLayoutManager;
    private HealthAdapter mHealthAdapter;
    private List<NewsInfo> threeDatas = new ArrayList<>();
    private News mTransmitNews = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // 将数据设置进 HealthAdapter
                    mHealthAdapter.setDatas(threeDatas);
                    mHealthAdapter.notifyDataSetChanged(); // 通知 RecyclerView 刷新页面(刷新数据)
                    break;
            }
        }
    };

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mTvHiName = (TextView) mRootView.findViewById(R.id.tv_hi_name);
        mLineNursingList = (LinearLayout) mRootView.findViewById(R.id.line_nursing_list);
        mLineMedicationReminder = (LinearLayout) mRootView.findViewById(R.id.line_medication_reminder);
        mLineVideoCall = (LinearLayout) mRootView.findViewById(R.id.line_video_call);
        mLineWeatherInfo = (LinearLayout) mRootView.findViewById(R.id.line_weather_info);
        mLineFlashlight = (LinearLayout) mRootView.findViewById(R.id.line_flashlight);
        mLineViewAll = (LinearLayout) mRootView.findViewById(R.id.line_view_all);
        mRvHealthArticle = (RecyclerView) mRootView.findViewById(R.id.rv_health_article);
        mLineNursingList.setOnClickListener(this);
        mLineMedicationReminder.setOnClickListener(this);
        mLineVideoCall.setOnClickListener(this);
        mLineWeatherInfo.setOnClickListener(this);
        mLineFlashlight.setOnClickListener(this);
        mLineViewAll.setOnClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvHealthArticle.setLayoutManager(mLinearLayoutManager);
        mHealthAdapter = new HealthAdapter(getActivity());
        mRvHealthArticle.setAdapter(mHealthAdapter);
        mHealthAdapter.setOnItemClick(new HealthAdapter.OnItemClick() {
            @Override
            public void onclick(View view) {
                int position = mRvHealthArticle.getChildAdapterPosition(view);
                Log.i(TAG, "onclick：position=" + position);
                String newsUrl = threeDatas.get(position).getUrl();
                Intent intent = new Intent(getActivity(), HotNewsDetailActivity.class);
                intent.putExtra(Constant.URL, newsUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        getName();
        getHealthData();
    }

    /**
     * 获取健康文章列表数据
     */
    private void getHealthData() {
        Api.configJuhe(Constant.JUHE_URL + "?type=" + Constant.HEALTH_TITLES + "&key=" + Constant.JUHE_APP_KEY)
                .getJuheRequest(getActivity(), new RequestCallback() {
                    @Override
                    public void onSuccess(String res) {
                        Gson gson = new Gson();
                        News news = gson.fromJson(res, News.class);
                        mTransmitNews = news;
                        NewsInfo[] data = news.getResult().getData();
                        List<NewsInfo> newsInfos = Arrays.asList(data);
                        for (int i = 0; i < 3; i++) {
                            threeDatas.add(newsInfos.get(i));
                        }
                        mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        showToastSync("网络请求失败，原因：" + e.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.line_nursing_list:

                break;
            case R.id.line_medication_reminder:

                break;
            case R.id.line_video_call: // 紧急呼叫
                navigateTo(EmergencyCallActivity.class);
                break;
            case R.id.line_weather_info: // 天气信息
                navigateTo(SelectCityActivity.class);
                break;
            case R.id.line_flashlight: // 手电筒
                navigateTo(FlashActivity.class);
                break;
            case R.id.line_view_all: // 查看全部
                // 如果 mTransmitNews对象为 null则 Toast提示
                if (null == mTransmitNews) {
                    showToast(getResources().getString(R.string.health_article_list_empty_data));
                } else {
                    navigateToWithSerializableObject(HealthArticleListActivity.class, "news_data", mTransmitNews);
                }
                break;
        }
    }

    // 通过 Sp获取用户名
    private void getName() {
        mName = getStringFromSp("account");
        if (mName.isEmpty()) {
            mTvHiName.setText(getResources().getString(R.string.home_hi_name_null));
        } else {
            mTvHiName.setText(String.format(getResources().getString(R.string.home_hi_name), mName));
        }
    }
}