package com.zht.common.MVP.sample.activity;

/**
 * Created by ZhangHaitao on 2018/10/8
 * Activity的Presenter和Fragment的Presenter不相同。
 * 区别在于 M-V-P 双向绑定的实际操作
 */
public class MVPPresenter implements MVPContract.Presenter {

    private MVPContract.View mView;

    public MVPPresenter(MVPContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadData() {

    }
}
