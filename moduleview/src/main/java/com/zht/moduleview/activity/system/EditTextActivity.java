package com.zht.moduleview.activity.system;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2018/10/15
 */
@Route(path = "/moduleview/activity/EditTextActivity")
public class EditTextActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_text;
    }
}
