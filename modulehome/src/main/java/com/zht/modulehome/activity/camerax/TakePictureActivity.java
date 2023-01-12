package com.zht.modulehome.activity.camerax;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.kotlin.cameraX.PictureCameraActivity;
import com.zht.modulehome.databinding.ActivityCameraXPictureBinding;

/**
 * @Date 2023/1/11 14:23
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.CAMERAX_PICTURE_ACTIVITY)
public class TakePictureActivity extends PictureCameraActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCameraXPictureBinding binding = ActivityCameraXPictureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkPermissionsAndStartCamera();
    }

    public void clickTakePhoto(View view) {
        takePhoto();
    }

    public void changeCamera(View view) {
        setUseFrontCamera(!getUseFrontCamera());
        checkPermissionsAndStartCamera();
    }


}
