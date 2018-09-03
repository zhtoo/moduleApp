package com.zht.moduletwo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.modulelib.base.BaseActivity;
import com.zht.moduletwo.R;
import com.zht.moduletwo.R2;


import butterknife.BindView;



@Route(path = "/moduletwo/main")
public class ModuleTwoMainActivity extends BaseActivity {

    @BindView(R2.id.tv_parameter)
    TextView tv_parameter;

    @Autowired
    String name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module_two_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initData() {
        super.initData();
        tv_parameter.setText("我是接收到的参数name:"+name);
    }

}
