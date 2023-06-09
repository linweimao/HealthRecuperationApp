package com.lwm.healthrecuperationapp.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.adapter.MyPagerAdapter;
import com.lwm.healthrecuperationapp.entity.TabEntity;
import com.lwm.healthrecuperationapp.fragment.HomeFragment;
import com.lwm.healthrecuperationapp.fragment.VideoListCarrierFragment;
import com.lwm.healthrecuperationapp.fragment.HotNewsCarrierFragment;
import com.lwm.healthrecuperationapp.fragment.MyFragment;
import com.lwm.healthrecuperationapp.view.FixedViewPager;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private String[] mTitles = {"首页", "视频", "资讯", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.home_unselect, R.mipmap.videolist_unselect,
            R.mipmap.collect_unselect, R.mipmap.my_unselect}; // 未选中时的图标
    private int[] mIconSelectIds = {
            R.mipmap.home_selected, R.mipmap.videolist_selected,
            R.mipmap.collect_selected, R.mipmap.my_selected}; // 选中时的图标
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager mViewpager;
    private CommonTabLayout mCommontablayout;

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    protected void initView() {
        mViewpager = (FixedViewPager) findViewById(R.id.viewpager);
        mCommontablayout = (CommonTabLayout) findViewById(R.id.commontablayout);
    }

    @Override
    protected void initData() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(VideoListCarrierFragment.newInstance());
        mFragments.add(HotNewsCarrierFragment.newInstance());
        mFragments.add(MyFragment.newInstance());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mCommontablayout.setTabData(mTabEntities);
        // 设置底部导航栏点击监听
        mCommontablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));

        // 当 ViewPager下 Fragment 很多时切换会出现异常(下标越界、页面空白)
        // 解决方案：
        //    设置预加载(启动 HomeActivity 时预加载全部 Fragment)
        mViewpager.setOffscreenPageLimit(mFragments.size());
        // ViewPager 滑动监听(页面和底部导航栏按钮同时变化)
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommontablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewpager.setCurrentItem(0); // 设置选中第一个
    }
}