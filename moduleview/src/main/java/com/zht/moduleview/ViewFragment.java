package com.zht.moduleview;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.modulelib.base.BaseFragment;

/**
 * Created by ZhangHaitao on 2018/9/6.
 */

@Route (path = "/moduleView/ViewFragment")
public class ViewFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view;
    }
}
