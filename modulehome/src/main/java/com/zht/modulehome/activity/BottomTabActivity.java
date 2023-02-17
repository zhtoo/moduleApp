package com.zht.modulehome.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ReportFragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulehome.R;
import com.zht.modulehome.adapter.ViewPager2Adapter;
import com.zht.modulehome.adapter.ViewPagerAdapter;
import com.zht.modulehome.adapter.ViewPagerStateAdapter;
import com.zht.modulehome.fragment.BottomTabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/2/16 17:26
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.BOTTOM_TAB_ACTIVITY)
public class BottomTabActivity extends AppCompatActivity {

    private static final String KEY_LAST_FRAGMENT_TAG = "fragment_last_fragment_tag";
    private String mLastFragmentNameTag;
    private List<String> fragmentNameList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);
        fragmentNameList.clear();
        fragmentNameList.add("tab1");
        fragmentNameList.add("tab2");
        fragmentNameList.add("tab3");
        fragmentNameList.add("tab4");
//        if (savedInstanceState != null) {
//            switchFragment(savedInstanceState.getString(KEY_LAST_FRAGMENT_TAG));
//        } else {
//            switchFragment(fragmentNameList.get(0));
//        }

//        initViewPager(savedInstanceState);
        initViewPager2(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onTabClick(View view) {
        String tabName = ((TextView) view).getText().toString();
        switchFragment(tabName);

        mViewpager.setCurrentItem(tabName.indexOf(tabName));
    }

    private void switchFragment(String fragmentName) {
        if (TextUtils.isEmpty(fragmentName)) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentName);
        if (null != fragment && fragment.isVisible()) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (String comparedFragmentName : fragmentNameList) {
            if (TextUtils.equals(fragmentName, comparedFragmentName)) {
                fragment = fragmentManager.findFragmentByTag(comparedFragmentName);
                if (fragment == null) {
                    fragment = createFragmentByName(fragmentName);
                    if (fragment == null) {
                        return;
                    }
                    ft.add(R.id.fragment_container, fragment, fragmentName);
                } else {
                    if (fragment.isHidden()) {
                        ft.show(fragment);
                    }
                }
            } else {
                fragment = fragmentManager.findFragmentByTag(comparedFragmentName);
                if (fragment != null && !fragment.isHidden()) {
                    ft.hide(fragment);
                }
            }
        }
        ft.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        mLastFragmentNameTag = fragmentName;
    }

    private Fragment createFragmentByName(String fragmentName) {
        switch (fragmentName) {
            case "tab1":
                return BottomTabFragment.newInstance(fragmentName);
            case "tab2":
                return BottomTabFragment.newInstance(fragmentName);
            case "tab3":
                return BottomTabFragment.newInstance(fragmentName);
            case "tab4":
                return BottomTabFragment.newInstance(fragmentName);
        }
        return null;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_LAST_FRAGMENT_TAG, mLastFragmentNameTag);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        List<Fragment> fragments = fragmentManager.getFragments();
//        for (Fragment fragment : fragments) {
//            Log.e("LazyFragment",  " Activity.onSaveInstanceState ---> " + fragment.getTag());
//            fragmentTransaction.remove(fragment);
//        }
//        fragmentTransaction.commitAllowingStateLoss();
//        fragmentManager.executePendingTransactions();
    }

    /***
     * ViewPager
     */

    private ViewPager mViewpager;

    private void initViewPager(Bundle savedInstanceState) {
        mViewpager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        ViewPagerStateAdapter adapter = new ViewPagerStateAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(adapter);

        if (savedInstanceState == null) {
            mViewpager.postDelayed(() -> {
                List<Fragment> mFragmentList = new ArrayList<>();
                for (String name : fragmentNameList) {
                    mFragmentList.add(BottomTabFragment.newInstance(name));
                }
                adapter.setFragmentList(mFragmentList);
            }, 1000);
        }

    }

    /***
     * ViewPager2
     */
    private ViewPager2 mViewpager2;

    private void initViewPager2(Bundle savedInstanceState) {
        mViewpager2 = findViewById(R.id.view_pager2);
        ViewPager2Adapter adapter = new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle());
        mViewpager2.setAdapter(adapter);
        mViewpager2.postDelayed(() -> {
            List<Fragment> mFragmentList = new ArrayList<>();
            for (String name : fragmentNameList) {
                mFragmentList.add(BottomTabFragment.newInstance(name));
            }
            adapter.setFragmentList(mFragmentList);
        }, 1000);
    }


}
