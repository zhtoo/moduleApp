package com.zht.moduleview;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.mvc.BaseFragment;
import com.zht.common.constant.ARoutePathConstants;

/**
 * Created by ZhangHaitao on 2018/9/6.
 */

@Route (path = ARoutePathConstants.View.VIEW_FRAGMENT)
public class ViewFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout systemView;
    private LinearLayout customizeView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        systemView = view.findViewById(R.id.ll_system_view);
        customizeView = view.findViewById(R.id.ll_customize_view);
        systemView.setOnClickListener(this);
        customizeView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.ll_system_view){
            ARouter.getInstance().build(ARoutePathConstants.View.SYSTEM_VIEW_ACTIVITY)
                    .navigation();
        }else if(id == R.id.ll_customize_view){
            ARouter.getInstance().build(ARoutePathConstants.View.CUSTOMISE_VIEW_ACTIVITY)
                    .navigation();
        }
    }
}
