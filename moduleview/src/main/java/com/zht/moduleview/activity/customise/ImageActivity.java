package com.zht.moduleview.activity.customise;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2020/7/29
 */
@Route(path = "/moduleview/activity/ImageActivity")
public class ImageActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.image_activity;
    }



}
