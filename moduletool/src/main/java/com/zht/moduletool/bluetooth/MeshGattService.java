package com.zht.moduletool.bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.Arrays;
import java.util.UUID;

/**
 * @Date 2025/5/31 22:21
 * @Author zhanghaitao
 * @Description
 */
public class MeshGattService {

    private final static String TAG = "GattService";

    private static BluetoothGattServer gattServer;
    private static BluetoothDevice currentDevice;
    public static UUID SERVICE_UUID = UUID.fromString("86DE14AC-8322-4BB2-B667-66037C55A950");
    public static UUID WRITE_CHARACTERISTIC_UUID = UUID.fromString("86DE14AC-8322-4BB2-B667-66037C55A951");
    public static UUID UPDATE_CHARACTERISTIC_UUID = UUID.fromString("86DE14AC-8322-4BB2-B667-66037C55A952");
    public static UUID CHARACTERISTIC_DESCRIPTOR_UUID = UUID.fromString("86DE14AC-8322-4BB2-B667-66037C55A95A");

    private static BluetoothGattCharacteristic notifyCharacteristic;



    @SuppressLint("MissingPermission")
    public static void startGattService(Context context) {
        Log.d(TAG, "call startGattService");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "app permission denied");
            return;
        }
        if (gattServer != null && gattServer.getService(SERVICE_UUID) != null) {
            Log.d(TAG, "gattServer is add");
            return;
        }

        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        // 开启Gatt服务
        gattServer = bluetoothManager.openGattServer(context, mGattServerCallback);

        if (gattServer != null) {
            gattServer.addService(createCustomService());
        }
    }

    @SuppressLint("MissingPermission")
    public static void stopGattService(Context context) {
        Log.d(TAG, "call stopGattService");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "app permission denied");
            return;
        }
        if (gattServer == null || gattServer.getService(SERVICE_UUID) == null) {
            Log.d(TAG, "gattServer is null or service is null");
            return;
        }
        gattServer.removeService(gattServer.getService(SERVICE_UUID));
    }

    public static BluetoothGattService createCustomService() {
        // 创建特征（支持读、写 暂时不知道这个特征值具体权限，临时赋予读写）
        BluetoothGattCharacteristic writeCharacteristic = new BluetoothGattCharacteristic(
                WRITE_CHARACTERISTIC_UUID,
                BluetoothGattCharacteristic.PROPERTY_READ |
                        BluetoothGattCharacteristic.PROPERTY_NOTIFY |
                        BluetoothGattCharacteristic.PROPERTY_WRITE,
                BluetoothGattCharacteristic.PERMISSION_READ |
                        BluetoothGattCharacteristic.PERMISSION_WRITE
        );

        // 创建特征（支持读、写）
        BluetoothGattCharacteristic updateCharacteristic = new BluetoothGattCharacteristic(
                UPDATE_CHARACTERISTIC_UUID,
                BluetoothGattCharacteristic.PROPERTY_READ |
                        BluetoothGattCharacteristic.PROPERTY_NOTIFY |
                        BluetoothGattCharacteristic.PROPERTY_WRITE,
                BluetoothGattCharacteristic.PERMISSION_READ |
                        BluetoothGattCharacteristic.PERMISSION_WRITE
        );

        // 添加 CCCD 描述符
        BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(
                CHARACTERISTIC_DESCRIPTOR_UUID,
                BluetoothGattDescriptor.PERMISSION_WRITE
        );

        writeCharacteristic.addDescriptor(descriptor);
        updateCharacteristic.addDescriptor(descriptor);

        // 创建服务并添加特征
        BluetoothGattService service = new BluetoothGattService(
                SERVICE_UUID,
                BluetoothGattService.SERVICE_TYPE_PRIMARY
        );
        notifyCharacteristic = writeCharacteristic;
        service.addCharacteristic(writeCharacteristic);
        service.addCharacteristic(updateCharacteristic);
        return service;
    }


    private static BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            Log.d(TAG, "连接状态发生改变:" + status);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.i(TAG, "设备已连接: " + device.getAddress());
                currentDevice = device;
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i(TAG, "设备断开: " + device.getAddress());
                // 可选：尝试重新连接或清理资源
                currentDevice = null;
            }
        }

        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            Log.d(TAG, "onServiceAdded:" + status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Gatt服务已成功添加：" + service.getUuid());
            } else {
                Log.e(TAG, "服务添加失败");
            }
        }


        @SuppressLint("MissingPermission")
        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            Log.d(TAG, "远程客户端请求读取本地特征");
            Log.d(TAG, "device:" + device.getAddress());
            Log.d(TAG, "requestId:" + requestId);
            Log.d(TAG, "characteristic:" + characteristic.getUuid().toString());
            Log.d(TAG, "offset:" + offset);
            // 返回特征值（需自行实现数据逻辑）
            byte[] value = characteristic.getValue();
            if (offset <= value.length) {
                gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset,
                        Arrays.copyOfRange(value, offset, value.length));
            } else {
                gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET, 0, null);
            }
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            Log.d(TAG, "远程客户端请求写入本地特性");
            Log.d(TAG, "device:" + device.getAddress());
            Log.d(TAG, "requestId:" + requestId);
            Log.d(TAG, "characteristic:" + characteristic.getUuid().toString());
            Log.d(TAG, "preparedWrite:" + preparedWrite);
            Log.d(TAG, "responseNeeded:" + responseNeeded);
            Log.d(TAG, "offset:" + offset);
            int[] data = new int[value.length];
            int index = 0;
            for (byte b : value) {
                data[index] = (b & 0xFF);
                index++;
            }
            Log.d(TAG, "value:" + data);

            // 接受写入并更新特征值
            if (offset == 0) {
                characteristic.setValue(value);
                if (responseNeeded) {
                    gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, null);
                }
                // 通知其他客户端特征已更新
//                gattServer.notifyCharacteristicChanged(device, characteristic, false);
            } else {
                if (responseNeeded) {
                    gattServer.sendResponse(device, requestId, BluetoothGatt.GATT_INVALID_OFFSET, offset, null);
                }
            }

        }

        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            Log.d(TAG, "onDescriptorReadRequest");
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            Log.d(TAG, "onDescriptorWriteRequest");
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            Log.d(TAG, "onExecuteWrite");
        }

        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            Log.d(TAG, "onNotificationSent");
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            Log.d(TAG, "onMtuChanged");
        }

        @Override
        public void onPhyUpdate(BluetoothDevice device, int txPhy, int rxPhy, int status) {
            Log.d(TAG, "onPhyUpdate");
        }

        @Override
        public void onPhyRead(BluetoothDevice device, int txPhy, int rxPhy, int status) {
            Log.d(TAG, "onPhyRead");
        }
    };

}
