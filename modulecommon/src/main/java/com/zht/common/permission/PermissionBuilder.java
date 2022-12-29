package com.zht.common.permission;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 *
 * 在 Android 6.0 - Android 8.0 （api 23-26），
 * 如果应用在运行时请求权限并且被授予该权限，
 * 系统会错误地将属于同一权限组并且在清单中注册的其他权限也一起授予应用。
 *
 * 对于针对 Android 8.0 的应用，此行为已被纠正。
 * 系统只会授予应用明确请求的权限。然而，一旦用户为应用授予某个权限，则所有后续对该权限组中权限的请求都将被自动批准。
 *
 * 在 Android 11 中，用户能够针对位置信息、麦克风和摄像头指定更精细的权限。
 * 此外，如果以Android 11或更高版本为目标平台的应用在一段时间内未使用，系统就会重置这些应用的权限。
 * 如果应用使用系统提醒窗口或读取与电话号码相关的信息，可能需要更新它们声明的权限。
 *
 * 在 Android 12 中，应用使用麦克风、摄像头等权限时，将会在状态栏显示图标以提示用户。
 *
 * https://developer.android.google.cn/reference/android/Manifest.permission?hl=zh-cn
 *
 * https://developer.android.google.cn/about/versions
 */
public class PermissionBuilder {

    private WeakReference<FragmentActivity> mFragmentActivityWeak;
    private WeakReference<Fragment> mFragmentWeak;
    private List<String> permissionList = new ArrayList<>();

    public static PermissionBuilder with(Fragment fragment) {
        return new PermissionBuilder(fragment);
    }

    public static PermissionBuilder with(FragmentActivity activity) {
        return new PermissionBuilder(activity);
    }

    public PermissionBuilder(Fragment fragment) {
        mFragmentWeak = new WeakReference<>(fragment);
    }

    public PermissionBuilder(FragmentActivity activity) {
        mFragmentActivityWeak = new WeakReference<>(activity);
    }

    public Observable<Boolean> request() {
        if (mFragmentWeak == null && mFragmentActivityWeak == null) {
            throw new IllegalStateException("Please call with");
        }
        CustomRxPermissions rxPermissions = null;
        if (mFragmentWeak != null && mFragmentWeak.get() != null) {
            rxPermissions = new CustomRxPermissions(mFragmentWeak.get());
        } else if (mFragmentActivityWeak != null && mFragmentActivityWeak.get() != null) {
            rxPermissions = new CustomRxPermissions(mFragmentActivityWeak.get());
        }
        if (rxPermissions == null) {
            throw new IllegalStateException("Your Fragment or FragmentActivity is null");
        }
        String[] permissionArray = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            permissionArray[i] = permissionList.get(i);
        }
        return rxPermissions.request(permissionArray);
    }

    public PermissionBuilder addPermission(String permission) {
        this.permissionList.add(permission);
        return this;
    }

    public PermissionBuilder addPermissions(String... permissions) {
        this.permissionList.addAll(Arrays.asList(permissions));
        return this;
    }

}
