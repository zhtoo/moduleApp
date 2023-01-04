package com.zht.moduletool;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.adapter.CommonAdapter;
import com.zht.common.base.BaseFragment;
import com.zht.common.bean.ItemBean;
import com.zht.common.constant.ARoutePathConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
@Route(path = ARoutePathConstants.Tool.TOOLS_FRAGMENT)
public class ToolsFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private CommonAdapter mAdapter;
    private List<ItemBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tools;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.tool_main_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommonAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        list = new ArrayList<>();

        list.add(new ItemBean("Toast", ARoutePathConstants.Tool.TOAST_ACTIVITY));
        list.add(new ItemBean("StatusBar", ARoutePathConstants.Tool.STATUS_BAR_ACTIVITY));
        list.add(new ItemBean("BitmapCache", ARoutePathConstants.Tool.BITMAP_CACHE_ACTIVITY));
        list.add(new ItemBean("Video", ARoutePathConstants.Tool.VIDEO_ACTIVITY));
        list.add(new ItemBean("Permission", ARoutePathConstants.Tool.PERMISSION_REQUEST_ACTIVITY));
        list.add(new ItemBean("屏幕信息", ARoutePathConstants.Tool.SCREEN_ACTIVITY));
        list.add(new ItemBean("交互视频播放", ARoutePathConstants.Tool.INTERACTIVE_VIDEO_ACTIVITY));
        list.add(new ItemBean("文件存储信息", ARoutePathConstants.Tool.STORAGE_ACTIVITY));

        mAdapter.setNewData(list);
    }


}
