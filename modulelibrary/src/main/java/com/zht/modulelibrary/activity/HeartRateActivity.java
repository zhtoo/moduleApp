package com.zht.modulelibrary.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulelibrary.R;
import com.zht.modulelibrary.databinding.ActivityHeartRateBinding;
import com.zht.modulelibrary.fragment.HeartRateFragment;

/**
 * @Date 2023/2/20 17:13
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.HEART_RATE_ACTIVITY)
public class HeartRateActivity extends BaseViewBindingActivity<ActivityHeartRateBinding> {

    @Override
    protected void initView(Bundle savedInstanceState) {
        HeartRateFragment fragment = new HeartRateFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment, "HeartRateFragment");
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
