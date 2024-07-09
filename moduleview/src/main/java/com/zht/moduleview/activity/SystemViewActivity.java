package com.zht.moduleview.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.adapter.CommonAdapter;
import com.zht.common.mvc.BaseActivity;
import com.zht.common.bean.ItemBean;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;

import java.util.ArrayList;

/**
 * Created by ZhangHaitao on 2018/9/30.
 */
@Route(path = ARoutePathConstants.View.SYSTEM_VIEW_ACTIVITY)
public class SystemViewActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private CommonAdapter adapter;
    private ArrayList<ItemBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecycler = findViewById(R.id.view_system_rv);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CommonAdapter();
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {

        list = new ArrayList<>();

        list.add(new ItemBean("EditText", ARoutePathConstants.View.EDITTEXT_ACTIVITY));
        list.add(new ItemBean("Calendar", ARoutePathConstants.View.SCALENDAR_ACTIVITY));
        list.add(new ItemBean("ViewPager", ARoutePathConstants.View.VIEWPAGER_ACTIVITY));
        list.add(new ItemBean("ViewPager2", ARoutePathConstants.View.VIEWPAGER2_ACTIVITY));
        list.add(new ItemBean("自定义CustomCardViewActivity", ARoutePathConstants.View.CUSTOM_CARD_VIEW_ACTIVITY));
        list.add(new ItemBean("ProgressView", ARoutePathConstants.View.PROGRESS_ACTIVITY));

        adapter.setNewData(list);
    }


}
