package com.zht.modulemain;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.zht.common.mvc.BaseActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulemain.view.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager mViewpager;
    private MenuItem menuItem;

    private List<Fragment> listFragment = new ArrayList<>();

    private BottomNavigationView bottomNavigationView;
    private Fragment toolsFragment;
    private Fragment homeFragment;
    private Fragment libFragment;
    private Fragment viewFragment;
    private Fragment personalFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        initViewPager();
        initBottomNavigation();
    }

    private void initViewPager() {
        mViewpager = findViewById(R.id.main_viewpager);

        homeFragment = (Fragment) ARouter.getInstance()
//                .build(ARoutePathConstants.Home.COMPOSE_FRAGMENT)
                .build(ARoutePathConstants.Home.HOME_FRAGMENT)
                .navigation();


        toolsFragment = (Fragment) ARouter.getInstance()
                .build(ARoutePathConstants.Tool.TOOLS_FRAGMENT)
                .navigation();

        libFragment = (Fragment) ARouter.getInstance()
                .build(ARoutePathConstants.Library.LIBRARY_FRAGMENT)
                .navigation();
        viewFragment = (Fragment) ARouter.getInstance()
                .build(ARoutePathConstants.View.VIEW_FRAGMENT)
                .navigation();
        personalFragment = (Fragment) ARouter.getInstance()
                .build(ARoutePathConstants.Personal.PERSONAL_FRAGMENT)
                .navigation();


        logFragment(homeFragment, "home");
        logFragment(toolsFragment, "tools");
        logFragment(libFragment, "lib");
        logFragment(viewFragment, "view");
        logFragment(personalFragment, "personal");


        listFragment.add(homeFragment);
        listFragment.add(toolsFragment);
        listFragment.add(libFragment);
        listFragment.add(viewFragment);
        listFragment.add(personalFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), listFragment);
        mViewpager.setAdapter(adapter);

    }

    private void logFragment(Fragment fragment, String name) {
        if (fragment == null) {
            Log.e("main", name + " is null");
        } else {
            Log.e("main", name + " is " + fragment.getClass().getName());
        }
    }

    /**
     * 初始化BottomNavigation
     */
    private void initBottomNavigation() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
        //禁止滑动效果
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        //添加监听
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.main_bottom_tab_home) {
                    mViewpager.setCurrentItem(0, false);
                    return true;
                } else if (itemId == R.id.main_bottom_tab_tools) {
                    mViewpager.setCurrentItem(1, false);
                    return true;
                } else if (itemId == R.id.main_bottom_tab_lib) {
                    mViewpager.setCurrentItem(2, false);
                    return true;
                } else if (itemId == R.id.main_bottom_tab_view) {
                    mViewpager.setCurrentItem(3, false);
                    return true;
                } else if (itemId == R.id.main_bottom_tab_personal) {
                    mViewpager.setCurrentItem(4, false);
                    return true;
                } else {
                    return false;
                }
            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

}
