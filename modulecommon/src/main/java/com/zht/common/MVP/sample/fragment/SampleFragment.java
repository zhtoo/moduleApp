package com.zht.common.MVP.sample.fragment;

import android.os.Bundle;

import com.zht.common.MVP.BaseMVPFragment;

/**
 * Created by ZhangHaitao on 2018/10/8
 */
public class SampleFragment extends BaseMVPFragment implements SampleContract.View {

    private static final String ARGUMENT = "ARGUMENT";

    public static SampleFragment newInstance() {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, "可以作为参数传递");
        SampleFragment sampleFragment = new SampleFragment();
        sampleFragment.setArguments(bundle);
        //数据双向绑定
        new SamplePresenter(sampleFragment);
        return sampleFragment;
    }

    @Override
    public void setPresenter(SampleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String mArgument = bundle.getString(ARGUMENT);
        }
    }

}
