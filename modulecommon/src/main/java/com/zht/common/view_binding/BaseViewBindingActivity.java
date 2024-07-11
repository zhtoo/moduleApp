package com.zht.common.view_binding;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.PermissionActivity;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseViewBindingActivity<T extends ViewBinding> extends PermissionActivity {

    public T binding;

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

        // 加载布局
        try {
            // 获取到第一个范型类的Class
            Type actualTypeArgument = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Class<T> clazz = (Class<T>) actualTypeArgument;
            Method method = clazz.getMethod("inflate", LayoutInflater.class);//获取inflate方法
            method.setAccessible(true);//强制反射
            binding = (T) method.invoke(null, getLayoutInflater());//调用inflate方法获取binding对象
        } catch (Exception e) {
            e.printStackTrace();
        }

        //ViewBinding
        setContentView(binding.getRoot());
        ARouter.getInstance().inject(this);
        initView(savedInstanceState);
        initData();
    }

    public void beforeSetContentView(Bundle savedInstanceState) {
    }

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
        binding = null;
        super.onDestroy();
    }


}
