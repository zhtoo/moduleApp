package com.zht.moduletool;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
@Route(path = "/moduletool/toolsfragment")
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
        mRecyclerView.setLayoutManager(
                new GridLayoutManager(getContext(), 3));
        mAdapter = new ToolsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListener(this);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        activitys = new ArrayList<>();
        list.add("Toast");
        activitys.add("/sample/ToastActivity");
        list.add("StatusBar");
        activitys.add("/sample/StatusBarActivity");
        list.add("BitmapCache");
        activitys.add("/sample/BitmapCacheActivity");
        list.add("Video");
        activitys.add("/sample/VideoActivity");
        list.add("Permission");
        activitys.add("/sample/PermissionAcrivity");

        list.add("屏幕信息");
        activitys.add("/sample/ScreenActivity");


        list.add("交互视频播放");
        activitys.add("/sample/InteractiveVideoActivity");


        mAdapter.updata(list);
    }

    @Override
    public void onItemClick(int position) {
        ARouter.getInstance()
                .build(activitys.get(position))
                .navigation();
    }

}
