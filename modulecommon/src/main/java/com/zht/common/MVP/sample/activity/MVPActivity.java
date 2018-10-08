package com.zht.common.MVP.sample.activity;

import com.zht.common.MVP.BaseMVPActivity;
import com.zht.common.MVP.sample.activity.MVPContract.View;

/**
 * Created by ZhangHaitao on 2018/10/8
 */
public class MVPActivity extends BaseMVPActivity implements View {
    @Override
    public void initPresenter() {
        mPresenter = new MVPPresenter(this);
    }
}
