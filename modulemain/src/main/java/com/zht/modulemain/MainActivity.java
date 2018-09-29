package com.zht.modulemain;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseActivity;
import com.zht.modulemain.view.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    ViewPager mViewpager;
    private MenuItem menuItem;

    List<Fragment> listFragment = new ArrayList<>();

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

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

    }

    private void initViewPager() {
        mViewpager = findViewById(R.id.main_viewpager);

        homeFragment = (Fragment) ARouter.getInstance()
                .build("/modulehome/homefragment")
                .navigation();
        toolsFragment = (Fragment) ARouter.getInstance()
                .build("/moduletool/toolsfragment")
                .navigation();
        libFragment = (Fragment) ARouter.getInstance()
                .build("/moduleLibrary/LibraryFragment")
                .navigation();
        viewFragment = (Fragment) ARouter.getInstance()
                .build("/moduleview/ViewFragment")
                .navigation();
        personalFragment = (Fragment) ARouter.getInstance()
                .build("/modulePersonal/PersonalFragment")
                .navigation();
        listFragment.add(homeFragment);
        listFragment.add(toolsFragment);
        listFragment.add(libFragment);
        listFragment.add(viewFragment);
        listFragment.add(personalFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), listFragment);
        mViewpager.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        super.initData();

    }


    /**
     * 初始化BottomNavigation
     * init BottomNavigation
     */
    private void initBottomNavigation() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
        //禁止滑动效果  disable BottomNavigationView shift mode
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        //添加监听
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.main_bottom_tab_home) {
                mViewpager.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.main_bottom_tab_tools) {
                mViewpager.setCurrentItem(1);
                return true;
            } else if (itemId == R.id.main_bottom_tab_lib) {
                mViewpager.setCurrentItem(2);
                return true;
            } else if (itemId == R.id.main_bottom_tab_view) {
                mViewpager.setCurrentItem(3);
                return true;
            } else if (itemId == R.id.main_bottom_tab_personal) {
                mViewpager.setCurrentItem(4);
                return true;
            } else {
                return false;
            }
        }
    };

}
