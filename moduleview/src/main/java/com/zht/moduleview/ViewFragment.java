package com.zht.moduleview;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseFragment;

/**
 * Created by ZhangHaitao on 2018/9/6.
 */

@Route (path = "/moduleview/ViewFragment")
public class ViewFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view;
    }
}
