package com.zht.moduleone.fragment;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseFragment;
import com.zht.moduleone.R;

@Route(path = "/moduleone/showfragment")
public class ModuleOneShowFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module_one_show;
    }

}
