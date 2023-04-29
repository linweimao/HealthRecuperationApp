package com.lwm.healthrecuperationapp;

import android.content.SharedPreferences;

import org.litepal.LitePalApplication;

import cn.bmob.v3.Bmob;
import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();

        // 启动应用时查找加载对应的应用皮肤
        SharedPreferences sp = getSharedPreferences("sp_lwm", MODE_PRIVATE);
        String skin = sp.getString("skin", "");
        if (skin.equals("night")) {
            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
        } else {
            SkinCompatManager.getInstance().restoreDefaultTheme();
        }
        // 初始化 BmobSDK
        Bmob.initialize(this, "196fa596d85f4412335d67d1743071e7");
    }
}