package com.zht.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseViewBindingFragment<T extends ViewBinding> extends Fragment {

    public T binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = getViewBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view, savedInstanceState);
        initData();
    }

    /**
     * 获取当前ViewBinding,用于设置当前布局
     * 交由子类实现
     *
     * @return
     */
    protected abstract T getViewBinding(LayoutInflater inflater, @Nullable ViewGroup container);

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
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
