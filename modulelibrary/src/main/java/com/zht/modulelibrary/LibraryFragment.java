package com.zht.modulelibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.adapter.CommonAdapter;
import com.zht.common.base.BaseViewBindingFragment;
import com.zht.common.bean.ItemBean;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulelibrary.databinding.FragmentLibraryBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/29.
 */
@Route(path = ARoutePathConstants.Library.LIBRARY_FRAGMENT)
public class LibraryFragment extends BaseViewBindingFragment<FragmentLibraryBinding> {

    CommonAdapter mAdapter;

    @Override
    protected FragmentLibraryBinding getViewBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentLibraryBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        if (binding == null) {
            return;
        }
        binding.libraryMainRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommonAdapter();
        binding.libraryMainRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        List<ItemBean> list = new ArrayList<>();

        list.add(new ItemBean(R.mipmap.ic_jsoup_logo, "Jsoup", ARoutePathConstants.Library.JSOUP_ACTIVITY));

        mAdapter.setNewData(list);
    }

}
