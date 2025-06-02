package com.zht.moduletool.bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.AdvertisingSet;
import android.bluetooth.le.AdvertisingSetCallback;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Log;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter;

import androidx.core.app.ActivityCompat;

import java.util.UUID;


/**
 * @Date 2025/5/31 22:33
 * @Author zhanghaitao
 * @Description
 */
public class MeshAdvertising {

    private final static String TAG = "Advertising";

    private static Handler handler;
    private static boolean isAdvertising;

    /**
     * 开启广播
     *
     * @param context 上下文
     * @param timeOut 蓝牙广播时间
     */
    @SuppressLint("MissingPermission")
    public static void startAdvertising(Context context, String localName, String serviceUUID, int timeOut) {
        Log.d(TAG, "call startAdvertising");
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "app permission denied");
//            return;
//        }
        if (!verifyUUID(serviceUUID)) {
            Log.d(TAG, "UUID format error");
            return;
        }
        try {
            BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
            if (bluetoothAdapter == null) {
                // 设备不支持蓝牙
                Log.d(TAG, "bluetoothAdapter is null");
                return;
            }
            if (!bluetoothAdapter.isEnabled()) {
                // 未开启蓝牙
                Log.d(TAG, "bluetoothAdapter is disabled");
                return;
            }
            String name = bluetoothAdapter.getName();
            Log.d(TAG, "bluetoothAdapter name:" + name);
            if (!TextUtils.equals(name, localName)) {
                bluetoothAdapter.setName(localName); //设置设备名称
                postDelayed(() -> {
                    Log.d(TAG, "bluetoothAdapter after setName:" + bluetoothAdapter.getName());
                    startAdvertisingAfterSetName(bluetoothAdapter, serviceUUID, timeOut);
                }, timeOut);
            } else {
                startAdvertisingAfterSetName(bluetoothAdapter, serviceUUID, timeOut);
            }
        } catch (Exception e) {
            Log.d(TAG, "error:" + e.getMessage());
        }
    }

    @SuppressLint("MissingPermission")
    private static void startAdvertisingAfterSetName(BluetoothAdapter bluetoothAdapter,
                                                     String serviceUUID,
                                                     int timeOut) {
        try {

            //初始化广播设置
            AdvertiseSettings mAdvertiseSettings = new AdvertiseSettings.Builder()
                    //设置广播模式，以控制广播的功率和延迟。
                    .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER)
                    //发射功率级别
                    .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                    //不得超过180000毫秒。值为0将禁用时间限制。
                    .setTimeout(timeOut)
                    //设置是否可以连接
                    .setConnectable(true)
                    .build();
            //初始化广播包
            AdvertiseData mAdvertiseData = new AdvertiseData.Builder()
                    .addServiceUuid(new ParcelUuid(UUID.fromString(serviceUUID)))// 设置服务的UUID
                    //设置广播设备名称
                    .setIncludeDeviceName(true)
                    //设置发射功率级别
                    .setIncludeTxPowerLevel(false)
                    .build();

            //初始化扫描响应包
            AdvertiseData mScanResponseData = new AdvertiseData.Builder()
                    //隐藏广播设备名称
                    .setIncludeDeviceName(false)
                    //隐藏发射功率级别
                    .setIncludeDeviceName(false)
                    .build();

            BluetoothLeAdvertiser advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();

            if (advertiser == null) {
                Log.d(TAG, "advertiser is null");
                return;
            }
            //开启广播
            advertiser.startAdvertising(mAdvertiseSettings,
                    mAdvertiseData, mScanResponseData, mAdvertisingCallback);
        } catch (Exception e) {

        }
    }

    @SuppressLint("MissingPermission")
    public static void stopAdvertising(Context context) {
        Log.d(TAG, "call stopAdvertising");
        try {
//            if (isAdvertising) {
                BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
                BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                BluetoothLeAdvertiser advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
                advertiser.stopAdvertising(new AdvertiseCallback() {
                    @Override
                    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                        Log.d(TAG, "onStartSuccess");
                    }

                    @Override
                    public void onStartFailure(int errorCode) {
                        Log.d(TAG, "onStartFailure");
                    }
                });
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static AdvertiseCallback mAdvertisingCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Log.d(TAG, "onAdvertisingStarted Success");
            isAdvertising = true;
        }

        @Override
        public void onStartFailure(int errorCode) {
            Log.d(TAG, "onAdvertisingStarted Fail:" + errorCode);

        }
    };


    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler.postDelayed(runnable, delayMillis);
    }

    private static boolean verifyUUID(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            return false;
        }
        String uuidPattern = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        return uuid.matches(uuidPattern);
    }
}
