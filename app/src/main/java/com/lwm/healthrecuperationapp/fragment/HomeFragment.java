package com.lwm.healthrecuperationapp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lwm.healthrecuperationapp.R;

/**
 * 实用工具 Fragment
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTvHiName; // 名字
    private LinearLayout mLineNursingList; // 护工列表
    private LinearLayout mLineMedicationReminder; // 服药提醒
    private LinearLayout mLineVideoCall; // 视频通话
    private LinearLayout mLineWeatherInfo; // 天气信息
    private LinearLayout mLineFlashlight; // 手电筒
    private LinearLayout mLineViewAll; // 查看全部
    private RecyclerView mRvHealthArticle; // 健康文章列表
    private String mName; // 用户名

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
        mLineViewAll.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getName();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.line_nursing_list:

                break;
            case R.id.line_medication_reminder:

                break;
            case R.id.line_video_call:

                break;
            case R.id.line_weather_info:

                break;
            case R.id.line_flashlight:

                break;
            case R.id.line_view_all:

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