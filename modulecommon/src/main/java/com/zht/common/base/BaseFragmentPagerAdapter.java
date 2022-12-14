package com.zht.common.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * @Date 2022/12/6 13:55
 * @Author zhanghaitao
 * @Description
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;
    private String[] mTitles;
    private List<Fragment> mFragments;

    public BaseFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    public BaseFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.fragmentManager = fm;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (mFragments == null) {
            return null;
        }
        if (position >= 0 && position < mFragments.size()) {
            return mFragments.get(position);
        }
        return null;
    }


    public void setNewData(String[] titles, List<Fragment> fragments) {
        if (this.mFragments != null && fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
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
