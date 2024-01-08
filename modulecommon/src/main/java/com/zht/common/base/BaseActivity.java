package com.zht.common.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BaseActivity extends PermissionActivity {

    /**
     * 上下文对象
     */
    protected Context mContext;

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
    }

}
