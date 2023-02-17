package com.zht.modulehome.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/2/17 11:34
 * @Author zhanghaitao
 * @Description
 */
public class ViewPager2Adapter extends FragmentStateAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ViewPager2Adapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        mFragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        if (!mFragmentList.isEmpty() && mFragmentManager != null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            for (Fragment fragment : mFragmentList) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commitNowAllowingStateLoss();
        }
        mFragmentList.clear();
        mFragmentList.addAll(fragmentList);
        notifyDataSetChanged();
    }

}