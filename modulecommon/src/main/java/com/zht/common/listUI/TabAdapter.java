package com.zht.common.listUI;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/11.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    public List<Fragment> mFragments = new ArrayList<>();
    public List<String> mTitles = new ArrayList<>();

    public TabAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }
    @Override
    public Fragment getItem(int arg0) {
        Fragment fragment = mFragments.get(arg0);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void updata(List<Fragment> mFragments, List<String> mTitles) {
        this.mFragments.clear();
        this.mTitles.clear();
        this.mFragments.addAll(mFragments);
        this.mTitles.addAll(mTitles);
        notifyDataSetChanged();
    }
}
