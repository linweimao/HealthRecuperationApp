package com.lwm.healthrecuperationapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.lwm.healthrecuperationapp.MainActivity;
import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.adapter.GuidePageAdapter;
import com.lwm.healthrecuperationapp.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现首次启动的引导页面
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mGuideVp;
    private int[] mImageIdArray; // 图片资源的数组
    private String[] mTitleIdArray; // 标题资源的数组
    private String[] mContentIdArray; // 内容资源的数组
    private List<View> mViewList; // 图片资源的集合
    private ViewGroup mGuideLlPoint; // 放置圆点
    //实例化原点View
    private ImageView mImgvPoint;
    private ImageView[] mIvPointArray;
    //最后一页的按钮
    private TextView mGuideTvExperienceNow, mGuideTvSkip;
    private TextView mGuidePageTitle, mGuidePageContent;

    @Override
    protected int initLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        // 判断是否为第一次打开软件
        if (!getBooleanFromSp("firststartup")) {
            if (StringUtils.isEmpty(getStringFromSp("token"))) {
                // token为空进入登录页面
                navigateTo(MainActivity.class);
                finish();
            } else {
                startActivity(new Intent(GuideActivity.this, HomeActivity.class));
                finish();
            }
        }
        mGuideTvSkip = (TextView) findViewById(R.id.guide_tv_skip);
        mGuideTvExperienceNow = (TextView) findViewById(R.id.guide_tv_experience_now);
        mGuidePageTitle = (TextView) findViewById(R.id.guide_page_title);
        mGuidePageContent = (TextView) findViewById(R.id.guide_page_content);
        mGuideTvSkip.setOnClickListener(this);
        mGuideTvExperienceNow.setOnClickListener(this);
        // 加载ViewPager
        initViewPager();
        // 加载底部圆点
        initPoint();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_tv_skip: // 跳过文字按钮
                saveBooleanToSp("firststartup", false);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.guide_tv_experience_now:  // 立即体验按钮
                saveBooleanToSp("firststartup", false);
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        // 这里实例化 LinearLayout
        mGuideLlPoint = (ViewGroup) findViewById(R.id.guide_ll_point);
        // 根据ViewPager的item数量实例化数组
        mIvPointArray = new ImageView[mViewList.size()];
        // 循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = mViewList.size();
        for (int i = 0; i < size; i++) {
            mImgvPoint = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
            layoutParams.leftMargin = 30;
            mImgvPoint.setLayoutParams(layoutParams);
            mIvPointArray[i] = mImgvPoint;
            // 第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                mImgvPoint.setBackgroundResource(R.mipmap.full_holo);
            } else {
                mImgvPoint.setBackgroundResource(R.mipmap.empty_holo);
            }
            // 将数组中的ImageView加入到ViewGroup
            mGuideLlPoint.addView(mIvPointArray[i]);
        }
    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        mGuideVp = (ViewPager) findViewById(R.id.guide_vp);
        // 实例化图片资源
        mImageIdArray = new int[]{R.mipmap.guide_img_one, R.mipmap.guide_img_two,
                R.mipmap.guide_img_three, R.mipmap.guide_img_four};
        // 实例化标题资源
        mTitleIdArray = new String[]{getResources().getString(R.string.guide_page_title_one), getResources().getString(R.string.guide_page_title_two),
                getResources().getString(R.string.guide_page_title_three), getResources().getString(R.string.guide_page_title_four)};
        // 实例化内容资源
        mContentIdArray = new String[]{getResources().getString(R.string.guide_page_content_one), getResources().getString(R.string.guide_page_content_two),
                getResources().getString(R.string.guide_page_content_three), getResources().getString(R.string.guide_page_content_four)};
        mViewList = new ArrayList<>();
        // 获取一个 Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // 循环创建View并加入到集合中
        int len = mImageIdArray.length;
        for (int i = 0; i < len; i++) {
            // new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(mImageIdArray[i]);
            // 将ImageView加入到集合中
            mViewList.add(imageView);
        }
        // View集合初始化好后，设置Adapter
        mGuideVp.setAdapter(new GuidePageAdapter(mViewList));
        // 设置滑动监听
        mGuideVp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * 滑动后的监听
     */
    @Override
    public void onPageSelected(int position) {
        mGuidePageTitle.setText(mTitleIdArray[position]); // 标题
        mGuidePageContent.setText(mContentIdArray[position]); // 内容
        // 循环设置当前页的标记图
        int length = mImageIdArray.length;
        for (int i = 0; i < length; i++) {
            mIvPointArray[position].setBackgroundResource(R.mipmap.full_holo);
            if (position != i) {
                mIvPointArray[i].setBackgroundResource(R.mipmap.empty_holo);
            }
        }
        // 判断是否是最后一页，若是则隐藏跳过文字按钮显示立即体验按钮
        if (position == mImageIdArray.length - 1) {
            mGuideTvSkip.setVisibility(View.GONE);
            mGuideTvExperienceNow.setVisibility(View.VISIBLE);
        } else {
            mGuideTvSkip.setVisibility(View.VISIBLE);
            mGuideTvExperienceNow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}