package com.zht.modulelibrary;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseFragment;

/**
 * Created by ZhangHaitao on 2018/9/29.
 */
@Route(path = "/moduleLibrary/LibraryFragment")
public class LibraryFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_library;
    }
}
