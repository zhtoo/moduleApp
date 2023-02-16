package com.zht.modulehome.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulehome.R;
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

    private List<String> fragmentNameList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);
        fragmentNameList.add("tab1");
        fragmentNameList.add("tab2");
        fragmentNameList.add("tab3");
        fragmentNameList.add("tab4");
    }

    public void onTabClick(View view) {
        String tabName = ((TextView) view).getText().toString();
        switchFragment(tabName);
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

}
