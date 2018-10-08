package com.zht.common.MVP.sample.fragment;

import com.zht.common.MVP.BasePresenter;
import com.zht.common.MVP.BaseView;

/**
 * Created by ZhangHaitao on 2018/10/8
 */
public class SampleContract {

    interface View extends BaseView<Presenter> {
        // TODO: 2018/10/8 在这里定义控制View状态的方法
    }

    interface Presenter extends BasePresenter {
        // TODO: 2018/10/8 在这里定义数据处理的方法
    }

}
