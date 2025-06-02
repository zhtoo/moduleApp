package com.zht.moduletool.activity;

import android.Manifest;
import android.bluetooth.BluetoothGattServerCallback;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.listener.PermissionCallBack;
import com.zht.common.view_binding.BaseViewBindingActivity;
import com.zht.moduletool.bluetooth.MeshAdvertising;
import com.zht.moduletool.bluetooth.MeshGattService;
import com.zht.moduletool.databinding.ActivityBluetoothBinding;

/**
 * @Date 2025/5/31 22:06
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Tool.BLUETOOTH_ACTIVITY)
public class BluetoothActivity extends BaseViewBindingActivity<ActivityBluetoothBinding> {

    public void clickPermission(View view) {
        requestPermissions(
                new String[]{
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_ADVERTISE
                },

                new PermissionCallBack() {
                    @Override
                    public void granted() {
                        Toast.makeText(BluetoothActivity.this, "授予权限", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void denied() {
                        Toast.makeText(BluetoothActivity.this, "无权限访", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void clickStartGatt(View view) {
        MeshGattService.startGattService(view.getContext());
    }

    public void clickStopGatt(View view) {
        MeshGattService.stopGattService(view.getContext());
    }

    public void clickStartAdvertising(View view) {
        MeshAdvertising.startAdvertising(view.getContext(),
                "12345678","12010000-0000-0000-ffff-ffffff000000",60000 );
    }

    public void clickStopAdvertising(View view) {
        MeshAdvertising.stopAdvertising(view.getContext());
    }


}
