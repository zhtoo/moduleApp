package com.zht.common.base;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private FragmentManager mFm;
    private String[] mTitles;
    private List<Fragment> mFragments;

    public BaseFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFm = fm;
    }

    public BaseFragmentStatePagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.mFm = fm;
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setNewData(String[] titles, List<Fragment> fragments) {
        if (this.mFragments != null && mFm != null) {
            FragmentTransaction ft = mFm.beginTransaction();
            for (Fragment fragment : this.mFragments) {
                ft.remove(fragment);
            }
            ft.commitNowAllowingStateLoss();
        }

        this.mTitles = titles;
        this.mFragments = fragments;
        notifyDataSetChanged();
    }
}