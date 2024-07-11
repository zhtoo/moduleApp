package com.zht.common.view_binding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class BaseViewBindingFragment<T extends ViewBinding> extends Fragment {

    public T binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化view
     * 子类实现 控件绑定、视图初始化等内容
     *
     * @param savedInstanceState
     */
    protected void initView(View view, Bundle savedInstanceState) {
    }

    /**
     * 初始化数据
     * 子类可以复写此方法初始化子类数据
     */
    protected void initData() {
    }

    @Override
    public void onDestroyView() {
        View root = binding.getRoot();
        if(root instanceof ViewGroup){
            ((ViewGroup) root).removeAllViews();
        }
        binding = null;
        super.onDestroyView();
    }

}
