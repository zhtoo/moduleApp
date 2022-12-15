package com.zht.moduletool;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseFragment;
import com.zht.common.constant.ARoutePathConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
@Route(path = ARoutePathConstants.Tool.TOOLS_FRAGMENT)
public class ToolsFragment extends BaseFragment implements ToolsAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private ToolsAdapter mAdapter;
    private List<String> list;
    private List<String> activitys;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tools;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.tool_main_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ToolsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        activitys = new ArrayList<>();
        list.add("Toast");
        activitys.add(ARoutePathConstants.Tool.TOAST_ACTIVITY);
        list.add("StatusBar");
        activitys.add(ARoutePathConstants.Tool.STATUS_BAR_ACTIVITY);
        list.add("BitmapCache");
        activitys.add(ARoutePathConstants.Tool.BITMAP_CACHE_ACTIVITY);
        list.add("Video");
        activitys.add(ARoutePathConstants.Tool.VIDEO_ACTIVITY);
        list.add("Permission");
        activitys.add(ARoutePathConstants.Tool.PERMISSION_REQUEST_ACTIVITY);
        list.add("屏幕信息");
        activitys.add(ARoutePathConstants.Tool.SCREEN_ACTIVITY);
        list.add("交互视频播放");
        activitys.add(ARoutePathConstants.Tool.INTERACTIVE_VIDEO_ACTIVITY);
        mAdapter.updata(list);
    }

    @Override
    public void onItemClick(int position) {
        ARouter.getInstance()
                .build(activitys.get(position))
                .navigation();
    }

}
