package com.zht.moduleone.activity;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.modulelib.base.BaseActivity;
import com.zht.moduleone.R;

@Route(path = "/moduleone/main")
public class ModuleOneMainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_one_main;
    }

}
