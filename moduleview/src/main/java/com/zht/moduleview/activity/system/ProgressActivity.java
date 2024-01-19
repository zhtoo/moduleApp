package com.zht.moduleview.activity.system;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.mvc.BaseActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2018/10/15
 */
@Route(path = ARoutePathConstants.View.PROGRESS_ACTIVITY)
public class ProgressActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_progress;
    }
}
