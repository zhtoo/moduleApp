package com.zht.common.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.zht.common.listener.PermissionCallBack;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 上下文对象
     */
    protected Context mContext;
    /**
     * ButterKnife
     */
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         init(savedInstanceState);
    }

    /**
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState) {
        beforeSetContentView(savedInstanceState);
        //加载布局
        setContentView(getLayoutId());
        //注册butterknife
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        initView(savedInstanceState);
        initData();
    }

    public void beforeSetContentView(Bundle savedInstanceState) {
    }

    /**
     * 获取当前layouty的布局ID,用于设置当前布局
     * 交由子类实现
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState
     */
    protected void initView(Bundle savedInstanceState) {
    }

    /**
     * 初始化数据
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    private PermissionCallBack callBack;
    private int REQUEST_CDOE;

    public void requestPermissions(@NonNull final String[] permissions,
                                   @IntRange(from = 0L) final int requestCode,
                                   @NonNull PermissionCallBack permissionCallBack) {
        callBack = permissionCallBack;
        REQUEST_CDOE = requestCode;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            callBack.granted();
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_CDOE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode != REQUEST_CDOE) {
            return;
        }
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the
            // contacts-related task you need to do.
            callBack.granted();
        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            callBack.denied();
        }
        return;
    }


}
