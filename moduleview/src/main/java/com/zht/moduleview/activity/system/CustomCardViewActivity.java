package com.zht.moduleview.activity.system;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.mvc.BaseActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2020/8/5
 */
@Route(path = ARoutePathConstants.View.CUSTOM_CARD_VIEW_ACTIVITY)
public class CustomCardViewActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_cardview ;
    }
}
