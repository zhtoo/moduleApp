package com.zht.modulehome.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.view_binding.BaseViewBindingActivity;
import com.zht.modulehome.databinding.ActivityCamera2Binding;

/**
 * @Date 2023/1/11 14:23
 * @Author zhanghaitao
 * @Description https://github.com/android/camera-samples
 */
@Route(path = ARoutePathConstants.Home.CAMERA2_ACTIVITY)
public class Camera2Activity extends BaseViewBindingActivity<ActivityCamera2Binding> {

    public void clickPreview(View view) {
        ARouter.getInstance()
                .build(ARoutePathConstants.Home.CAMERA2_PREVIEW_ACTIVITY)
                .navigation();
    }

    public void clickTakePhoto(View view) {
        ARouter.getInstance()
                .build(ARoutePathConstants.Home.CAMERA2_PICTURE_ACTIVITY)
                .navigation();
    }

    public void clickTakeVideo(View view) {
        ARouter.getInstance()
                .build(ARoutePathConstants.Home.CAMERA2_VIDEO_ACTIVITY)
                .navigation();
    }

}
