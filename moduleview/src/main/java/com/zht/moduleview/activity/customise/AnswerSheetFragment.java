package com.zht.moduleview.activity.customise;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zht.common.base.BaseFragment;
import com.zht.moduleview.R;

/**
 * Created by ZhangHaitao on 2019/5/30
 */
public class AnswerSheetFragment extends BaseFragment {

    private TextView remind;

    @Override
    protected int getLayoutId() {
        return  R.layout.fragment_answer_sheet;
    }

    private String type;
    public static AnswerSheetFragment newInstance(String type) {
        AnswerSheetFragment answerSheetFragment = new AnswerSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        answerSheetFragment.setArguments(bundle);
        return answerSheetFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.type = getArguments().getString("type");
        }
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        remind = view.findViewById(R.id.text_remaind);
        if(!TextUtils.isEmpty(type)){
            remind.setText(type);
        }
    }
}
