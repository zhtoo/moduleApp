package com.zht.moduleview.activity.system.coordinator;


import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.view_binding.BaseViewBindingActivity;
import com.zht.moduleview.databinding.ActivityCoordinatorBehaviorBinding;

/**
 * @Date 2024/7/9 17:47
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.View.COORDINATOR_BEHAVIOR)
public class CoordinatorBehaviorActivity extends BaseViewBindingActivity<ActivityCoordinatorBehaviorBinding> {

    @Override
    protected void initView(Bundle savedInstanceState) {
        binding.dependency.setOnTouchListener(new View.OnTouchListener() {

            int mOldX;
            int mOldY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int currX = (int) event.getRawX();
                int currY = (int) event.getRawY();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mOldX = currX;
                        mOldY = currY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int offsetX = currX - mOldX;
                        int offsetY = currY - mOldY;
                        binding.dependency.layout(
                                binding.dependency.getLeft() + offsetX,
                                binding.dependency.getTop() + offsetY,
                                binding.dependency.getRight() + offsetX,
                                binding.dependency.getBottom() + offsetY);
                        mOldX = currX;
                        mOldY = currY;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void initData() {

    }
}
