package com.zht.moduleview.activity.system;


import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.adapter.CommonAdapter;
import com.zht.common.bean.ItemBean;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.view_binding.BaseViewBindingActivity;
import com.zht.moduleview.databinding.ActivityCoordinatorBinding;

import java.util.ArrayList;

/**
 * @Date 2024/7/9 17:47
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.View.COORDINATOR_ACTIVITY)
public class CoordinatorActivity extends BaseViewBindingActivity<ActivityCoordinatorBinding> {

    private CommonAdapter adapter;
    private ArrayList<ItemBean> list;

    @Override
    protected void initView(Bundle savedInstanceState) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();

        list.add(new ItemBean("Coordinator Behavior", ARoutePathConstants.View.COORDINATOR_BEHAVIOR));
        list.add(new ItemBean("Coordinator", ARoutePathConstants.View.SCALENDAR_ACTIVITY));
        list.add(new ItemBean("Coordinator", ARoutePathConstants.View.VIEWPAGER_ACTIVITY));
        list.add(new ItemBean("Coordinator", ARoutePathConstants.View.VIEWPAGER2_ACTIVITY));


        adapter.setNewData(list);
    }
}
