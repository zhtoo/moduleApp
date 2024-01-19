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
@Route(path = ARoutePathConstants.View.CUSTOMISE_VIEW_ACTIVITY)
public class CustomiseViewActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private CommonAdapter adapter;
    private ArrayList<ItemBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customise_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecycler = findViewById(R.id.view_customise_rv);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CommonAdapter();
        mRecycler.setAdapter(adapter);


    }


    @Override
    protected void initData() {

        list = new ArrayList<>();

        list.add(new ItemBean("日历", ARoutePathConstants.View.CALENDAR_ACTIVITY));
        list.add(new ItemBean("验证码输入框", ARoutePathConstants.View.VERIFY_CODE_EDIT_ACTIVITY));
        list.add(new ItemBean("索引", ARoutePathConstants.View.QUICK_INDEX_ACTIVITY));
        list.add(new ItemBean("上下滑动改变View高度", ARoutePathConstants.View.DRAG_ACTIVITY));
        list.add(new ItemBean("ImageActivity", ARoutePathConstants.View.IMAGE_ACTIVITY));

        adapter.setNewData(list);
    }


}
