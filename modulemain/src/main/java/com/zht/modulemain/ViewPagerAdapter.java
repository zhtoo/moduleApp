package com.zht.modulemain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mFragmentList;

    public ViewPagerAdapter(FragmentManager manager, List<Fragment> mFragmentList) {
        super(manager);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
