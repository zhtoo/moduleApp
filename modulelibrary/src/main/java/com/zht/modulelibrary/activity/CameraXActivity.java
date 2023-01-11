package com.zht.modulelibrary.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulelibrary.databinding.ActivityCameraXBinding;

/**
 * @Date 2023/1/11 14:23
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.CAMERAX_ACTIVITY)
public class CameraXActivity extends BaseViewBindingActivity<ActivityCameraXBinding> {

    public void clickPreview(View view) {
        ARouter.getInstance()
                .build(ARoutePathConstants.Library.CAMERAX_PREVIEW_ACTIVITY)
                .navigation();
    }

    public void clickTakePhoto(View view) {
        ARouter.getInstance()
                .build(ARoutePathConstants.Library.CAMERAX_PICTURE_ACTIVITY)
                .navigation();
    }

    public void clickTakeVideo(View view) {
        ARouter.getInstance()
                .build(ARoutePathConstants.Library.CAMERAX_VIDEO_ACTIVITY)
                .navigation();
    }

}
