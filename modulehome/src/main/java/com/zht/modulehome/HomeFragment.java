package com.zht.modulehome;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.adapter.CommonAdapter;
import com.zht.common.view_binding.BaseViewBindingFragment;
import com.zht.common.bean.ItemBean;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulehome.compose.ComposeFragment;
import com.zht.modulehome.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
@Route(path = ARoutePathConstants.Home.HOME_FRAGMENT)
public class HomeFragment extends BaseViewBindingFragment<FragmentHomeBinding> {


   private CommonAdapter mAdapter;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        if (binding == null) {
            return;
        }
        binding.homeRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommonAdapter();
        binding.homeRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

//        ComposeFragment fragment = new ComposeFragment();
        List<ItemBean> list = new ArrayList<>();
        list.add(new ItemBean(R.mipmap.ic_jetpack_logo, "Jetpack", ARoutePathConstants.Home.JETPACK_ACTIVITY));
        list.add(new ItemBean(R.mipmap.ic_camerax_logo, "CameraX", ARoutePathConstants.Home.CAMERAX_ACTIVITY));
        list.add(new ItemBean(R.mipmap.ic_camerax_logo, "Camera2", ARoutePathConstants.Home.CAMERA2_ACTIVITY));
        list.add(new ItemBean(R.mipmap.ic_datastore_logo, "DataStore", ARoutePathConstants.Home.DATA_STORE_ACTIVITY));
        list.add(new ItemBean(R.mipmap.ic_compose_logo, "Compose", ARoutePathConstants.Home.COMPOSE_ACTIVITY));
        list.add(new ItemBean(R.mipmap.ic_hilt_logo, "Hilt", ARoutePathConstants.Home.HILT_ACTIVITY));
        list.add(new ItemBean(R.mipmap.ic_security_logo, "Security", ARoutePathConstants.Home.SECURITY_ACTIVITY));
        list.add(new ItemBean(R.mipmap.ic_bottom_tab_logo, "底部tab实现方案", ARoutePathConstants.Home.BOTTOM_TAB_ACTIVITY));
        list.add(new ItemBean(R.mipmap.ic_account_book, "记账本", ARoutePathConstants.Home.ACCOUNT_BOOK_ACTIVITY));

        mAdapter.setNewData(list);
    }

    @Override
    public void onDestroyView() {
        mAdapter = null;
        super.onDestroyView();
    }
}
