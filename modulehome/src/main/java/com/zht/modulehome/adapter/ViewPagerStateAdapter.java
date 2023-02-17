package com.zht.modulehome.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/2/17 11:34
 * @Author zhanghaitao
 * @Description 当被系统回收之后，所有界面都会onCreate。如果没有在宿主的onCreate中保存tab选中状态，默认选中第一个
 * FragmentStatePagerAdapter会在getItemPosition()返回POSITION_NONE 触发调用 destroyItem() 释放资源
 */
public class ViewPagerStateAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerStateAdapter(@NonNull FragmentManager fm) {
//        super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT); //会回调fragment的setUserVisibleHint(boolean)来告知fragment的显示/隐藏状态
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);//不会回调fragment的setUserVisibleHint(boolean)，但是 只调用当前fragment的onResume
        mFragmentManager = fm;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * 当宿主视图试图确定项的位置是否已更改时调用。
     * 如果给定项的位置未更改，则返回POSITION_UNCHANGE；
     * 如果适配器中不再存在该项，则返回POSITION_NONE。
     */
    @Override
    public int getItemPosition(@NonNull Object item) {
        if(mFragmentList.contains(item)){
            return super.getItemPosition(item);
        }
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
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
