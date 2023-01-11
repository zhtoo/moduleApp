package com.zht.modulelibrary.activity.camerax;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.kotlin.cameraX.VideoCameraActivity;
import com.zht.modulelibrary.databinding.ActivityCameraXVideoBinding;

/**
 * @Date 2023/1/11 14:23
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.CAMERAX_VIDEO_ACTIVITY)
public class TakeVideoActivity extends VideoCameraActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCameraXVideoBinding binding = ActivityCameraXVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkPermissionsAndStartCamera();
    }

    public void clickStartVideo(View view) {
        startVideo();
    }

    public void clickPauseVideo(View view) {
        pauseVideo();
    }

    public void clickStopVideo(View view) {
        stopVideo();
    }

}
