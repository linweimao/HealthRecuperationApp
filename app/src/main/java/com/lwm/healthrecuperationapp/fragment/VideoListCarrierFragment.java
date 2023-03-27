package com.lwm.healthrecuperationapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.adapter.HomeAdapter;
import com.lwm.healthrecuperationapp.api.Api;
import com.lwm.healthrecuperationapp.api.ApiConfig;
import com.lwm.healthrecuperationapp.api.RequestCallback;
import com.lwm.healthrecuperationapp.entity.CategoryEntity;
import com.lwm.healthrecuperationapp.entity.VideoCategoryResponse;
import com.lwm.healthrecuperationapp.view.FixedViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * VideoFragment的载体 Fragment
 */
public class VideoListCarrierFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private EditText mEtSearch;
    private SlidingTabLayout mSlidingtablayout;
    private ViewPager mViewPager;

    public VideoListCarrierFragment() {
    }

    public static VideoListCarrierFragment newInstance() {
        VideoListCarrierFragment fragment = new VideoListCarrierFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_video_list_carrier;
    }

    @Override
    protected void initView() {
        mEtSearch = (EditText) mRootView.findViewById(R.id.et_search);
        mSlidingtablayout = (SlidingTabLayout) mRootView.findViewById(R.id.slidingtablayout);
        mViewPager = (FixedViewPager) mRootView.findViewById(R.id.fixedviewpager);
    }

    @Override
    protected void initData() {
        // 从接口中获取视频类型
        getVideoCategoryList();
    }

    private void getVideoCategoryList() {
        HashMap<String, Object> params = new HashMap<>();
        Api.config(ApiConfig.VIDEO_CATEGORY_LIST, params).getRequest(getActivity(), new RequestCallback() {
            @Override
            public void onSuccess(String res) {
                Log.d("onSuccess：", res);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        VideoCategoryResponse response = new Gson().fromJson(res, VideoCategoryResponse.class);
                        if (response != null && response.getCode() == 0) {
                            List<CategoryEntity> list = response.getPage().getList();
                            // 判断接口返回的数据是否为空
                            if (list != null && list.size() > 0) {
                                mTitles = new String[list.size()];
                                for (int i = 0; i < list.size(); i++) {
                                    mTitles[i] = list.get(i).getCategoryName();
                                    mFragments.add(VideoFragment.newInstance(list.get(i).getCategoryId()));
                                }

                                // 当 ViewPager下 Fragment 很多时切换会出现异常(下标越界、页面空白)
                                // 解决方案：
                                //    设置预加载(启动 VideoListCarrierFragment 时预加载全部 Fragment)
                                mViewPager.setOffscreenPageLimit(mFragments.size());
                                mViewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
                                mSlidingtablayout.setViewPager(mViewPager); // SlidingTabLayout 绑定 ViewPager

                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}