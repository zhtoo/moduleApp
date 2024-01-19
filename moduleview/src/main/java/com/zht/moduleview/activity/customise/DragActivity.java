package com.zht.moduleview.activity.customise;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.zht.common.mvc.BaseActivity;
import com.zht.common.base.BaseFragmentStatePagerAdapter;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;

import java.util.ArrayList;

/**
 * Created by ZhangHaitao on 2019/5/27
 * TouchDrag
 */
@Route(path = ARoutePathConstants.View.DRAG_ACTIVITY)
public class DragActivity extends BaseActivity {

    private static final String TAG = "DragActivity";

    private ImageView mDragBt;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private BaseFragmentStatePagerAdapter mAdapter;

    private String[] mQuestionNames;


    @Override
    protected int getLayoutId() {
        return R.layout.drag_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mDragBt = findViewById(R.id.drag_bt);

        mTabLayout = (SlidingTabLayout) findViewById(R.id.answer_sheet_tab);
        mViewPager = (ViewPager) findViewById(R.id.answer_sheet_viewPager);

        mAdapter = new BaseFragmentStatePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPager);

    }

    @Override
    protected void initData() {
        mQuestionNames = new String[12];
        ArrayList<Fragment> fragments = new ArrayList<>();
        AnswerSheetFragment fragment;
        for (int i = 0; i < mQuestionNames.length; i++) {
            fragment = AnswerSheetFragment.newInstance("第" + String.valueOf(i + 1) + "题");
            mQuestionNames[i] = String.valueOf(i + 1);
            fragments.add(fragment);
        }
        mAdapter.setNewData(mQuestionNames, fragments);
        mTabLayout.notifyDataSetChanged();

        for (int i = 0; i <mTabLayout.getTabCount(); i++) {
            TextView titleView = mTabLayout.getTitleView(i);

        }
    }
}
