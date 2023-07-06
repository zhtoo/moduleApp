package com.zht.modulelibrary.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.zht.measureheartrate.interfaces.HeartRateCallBack;
import com.zht.measureheartrate.util.CameraUtil;
import com.zht.measureheartrate.CameraXHelper;
import com.zht.measureheartrate.util.HeartRateProcessing;
import com.zht.modulelibrary.R;

/**
 * @Date 2023/2/21 15:49
 * @Author zhanghaitao
 * @Description
 */
public class HeartRateFragment extends Fragment {

    private TextView mRateView;
    private PreviewView mPreview;

    private HeartRateProcessing heartRateProcessing;

    private Size minSize;

    String cameraPermission = Manifest.permission.CAMERA;

    private HeartRateCallBack beatCallBack = new HeartRateCallBack() {
        @Override
        public void onCurrentHeartRate(int rate) {
            mRateView.setText(String.format("当前心率:%d次/分钟", rate));
//            Log.e(TAG, "当前心率:" + rate);
        }

        @Override
        public void onResultHeartRate(int rate) {
            CameraXHelper.getInstance().openFlashlight(false);
            CameraXHelper.getInstance().stopCamera();
            mRateView.setText(String.format("最终心率:%d次/分钟", rate));
//            Log.e(HeartRateProcessing.TAG, "最终心率:" + rate);
        }

        @Override
        public void onStartHeartRate() {
            mRateView.setText("开始测量");
//            Log.e(HeartRateProcessing.TAG, "开始测量:");
        }

        @Override
        public void onWaitHeartRate() {
            // Log.e(HeartRateProcessing.TAG, "等待心跳");
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        registerRequestPermission();
        super.onCreate(savedInstanceState);
        heartRateProcessing = new HeartRateProcessing();
        heartRateProcessing.setHeartRateCallBack(beatCallBack);
        minSize = CameraUtil.getCameraMinSize(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_heart_rate, container, false);
        mPreview = view.findViewById(R.id.camera_preview);
        mRateView = view.findViewById(R.id.rate_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermissionAndStartCamera();
    }

    private void registerRequestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), cameraPermission) == PackageManager.PERMISSION_DENIED) {
//            onPermissionGranted();
//        } else {
            ActivityResultContracts.RequestMultiplePermissions requestPermissions = new ActivityResultContracts.RequestMultiplePermissions();
            registerForActivityResult(requestPermissions, result -> {
                if (result.get(cameraPermission) == Boolean.TRUE) {
                    checkPermissionAndStartCamera();
                }
            }).launch(new String[]{cameraPermission});
        }
    }

    private boolean hasCheckPermission = false;

    private synchronized void checkPermissionAndStartCamera() {
        if (hasCheckPermission) {
            return;
        }
        if (ContextCompat.checkSelfPermission(getContext(), cameraPermission) == PackageManager.PERMISSION_DENIED) {
            return;
        }
        hasCheckPermission = true;
        CameraXHelper cameraHelper = CameraXHelper.getInstance();
        cameraHelper.setPreviewSize(minSize);
        cameraHelper.setProcessing(heartRateProcessing);
        cameraHelper.startCamera(this, getActivity().getApplication(), mPreview);
    }

    @Override
    public void onDestroy() {
        CameraXHelper.getInstance().release();
        super.onDestroy();
    }
}
