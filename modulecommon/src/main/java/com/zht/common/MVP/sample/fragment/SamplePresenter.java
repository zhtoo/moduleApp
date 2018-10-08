package com.zht.common.MVP.sample.fragment;

/**
 * Created by ZhangHaitao on 2018/10/8
 */
public class SamplePresenter implements SampleContract.Presenter {

    SampleContract.View mView;

    public SamplePresenter(SampleContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void loadData() {

    }
}
