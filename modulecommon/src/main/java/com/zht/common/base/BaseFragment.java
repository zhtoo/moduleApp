package com.zht.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    /**
     * ButterKnife
     */
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //加载布局
        View view = inflater.inflate(getLayoutId(),container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //注册butterknife
        mUnbinder = ButterKnife.bind(this,view);

        initView(view,savedInstanceState);
        initData();
    }

    /**
     * 获取当前layouty的布局ID,用于设置当前布局
     * 交由子类实现
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     * 子类实现 控件绑定、视图初始化等内容
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
        mUnbinder.unbind();
        super.onDestroyView();
    }

}
