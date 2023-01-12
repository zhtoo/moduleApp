package com.zht.modulehome.activity.camerax;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.kotlin.cameraX.PreviewCameraActivity;
import com.zht.modulehome.databinding.ActivityCameraXPreviewBinding;

/**
 * @Date 2023/1/11 14:23
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Home.CAMERAX_PREVIEW_ACTIVITY)
public class PreviewActivity extends PreviewCameraActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCameraXPreviewBinding binding = ActivityCameraXPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkPermissionsAndStartCamera();
    }

    @Override
    public void onCatchPreviewImage(@NonNull Bitmap bitmap) {

    }
}
