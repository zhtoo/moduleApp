package com.zht.modulehome.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/2/17 11:34
 * @Author zhanghaitao
 * @Description 当被系统回收之后，所有界面都会onCreate。如果没有在宿主的onCreate中保存tab选中状态，默认选中第一个
 * FragmentPagerAdapter仅仅会在 destroyItem() 中detach这个Fragment，
 * 在 instantiateItem() 时会使用旧的Fragment，并触发attach,
 * 没有触发资源及重建的过程。
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
//        super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT); //会回调fragment的setUserVisibleHint(boolean)来告知fragment的显示/隐藏状态
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);//不会回调fragment的setUserVisibleHint(boolean)，但是 只调用当前fragment的onResume
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
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