package com.zht.moduletwo.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseFragment;
import com.zht.moduletwo.R;


@Route(path = "/moduletwo/showfragment")
public class ModuleTwoShowFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_module_two_show;
    }

}
