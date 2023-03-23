package com.zht.common.base;

import android.content.pm.PackageManager;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.zht.common.listener.PermissionCallBack;

/**
 * @Date 2022/12/15 17:46
 * @Author zhanghaitao
 * @Description
 */
public class PermissionActivity  extends AppCompatActivity {

    private PermissionCallBack callBack;
    private int REQUEST_PERMISSION_CODE = 0x0001;

    public void requestPermissions(@NonNull final String[] permissions,
                                   @NonNull PermissionCallBack permissionCallBack) {
        callBack = permissionCallBack;

        boolean permissionGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    == PackageManager.PERMISSION_DENIED) {
                permissionGranted = false;
                break;
            }
        }
        if (permissionGranted) {
            callBack.granted();
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_PERMISSION_CODE) {
            return;
        }
        if (grantResults == null || grantResults.length == 0) {
            callBack.denied();
            return;
        }
        boolean permissionGranted = true;
        for (int grantResult : grantResults) {
            if (PackageManager.PERMISSION_DENIED == grantResult) {
                permissionGranted = false;
                break;
            }
        }
        if (permissionGranted) {
            callBack.granted();
        } else {
            callBack.denied();
        }
    }

}
