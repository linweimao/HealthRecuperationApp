package com.lwm.healthrecuperationapp.fragment;

import android.os.Bundle;
import com.lwm.healthrecuperationapp.R;

/**
 * 实用工具 Fragment
 */
public class HomeFragment extends BaseFragment {

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

    }

    @Override
    protected void initData() {

    }
}