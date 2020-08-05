package com.zht.moduleview.activity.system;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2020/8/5
 */
@Route(path = "/moduleview/activity/CustomCardViewActivity")
public class CustomCardViewActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_cardview ;
    }
}
