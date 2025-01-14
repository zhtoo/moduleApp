package com.zht.moduleview.activity.system.coordinator;


import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.view_binding.BaseViewBindingActivity;
import com.zht.moduleview.databinding.ActivityCoordinatorBehaviorBinding;
import com.zht.moduleview.databinding.ActivityCoordinatorTabBinding;

/**
 * @Date 2024/7/9 17:47
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.View.COORDINATOR_ACTIVITY_TAB)
public class CoordinatorTabActivity extends BaseViewBindingActivity<ActivityCoordinatorTabBinding> {

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }
}
