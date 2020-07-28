package com.zht.common.listUI;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zht.common.R;

import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/11.
 * 共用的ListUI界面
 */

public abstract class TabListActivity extends AppCompatActivity {

//    private PagerIndicator mPagerIndicator;
    private ViewPager mViewPager;
    private TabAdapter mAdapter;
//    private NativeHeaderView mHeaderView;
    private View mRootView;
    private TextView mTitle;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = LayoutInflater.from(this).inflate(getLayoutId(), null, false);
        setContentView(mRootView);
        initView();
        initData();
    }

    protected int getLayoutId() {
        return R.layout.activity_common_tab_list;
    }


    protected void initView() {
        mTitle = (TextView) findViewById(R.id.common_title);
        mTabLayout = (TabLayout) findViewById(R.id.common_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.common_viewPager);
        findViewById(R.id.common_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    protected void initData() {
        mTitle.setText(setTitle());
        List<String> list = setTabTitles();
        mAdapter = new TabAdapter(getSupportFragmentManager(), setFragment(), list);
        mViewPager.setAdapter(mAdapter);
        for (int i = 0; i < list.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(list.get(i)));
        }
        mTabLayout.setupWithViewPager(mViewPager);
    }

    protected abstract String setTitle();

    protected abstract List<Fragment> setFragment();

    protected abstract List<String> setTabTitles();

//    public void updata(List<Fragment> mFragments, List<String> mTitles) {
//        mAdapter.updata(mFragments,mTitles);
//        mPagerIndicator.updateIndicator();
//    }
//
//    public NativeHeaderView getHeaderView() {
//        return mHeaderView;
//    }
}
