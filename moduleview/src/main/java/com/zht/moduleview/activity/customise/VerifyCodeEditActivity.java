package com.zht.moduleview.activity.customise;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.mvc.BaseActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.CustomKeyboard.CustomKeyboardManager;
import com.zht.moduleview.R;
import com.zht.moduleview.VerifyCode.VerifyCodeEditText;

/**
 * Created by ZhangHaitao on 2019/4/4
 */
@Route(path = ARoutePathConstants.View.VERIFY_CODE_EDIT_ACTIVITY)
public class VerifyCodeEditActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.verify_code_edit_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        VerifyCodeEditText mVerify = findViewById(R.id.VerifyCodeEditText);
        mVerify.setFocusable(true);
        mVerify.setFocusableInTouchMode(true);
        mVerify.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mVerify, 0);

        mVerify.setCompleteListener(new VerifyCodeEditText.OnInputCompleteListener() {
            @Override
            public void onComplete(String text) {
                Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
            }
        });

        edit = (EditText)findViewById(R.id.demo_edit);

        customKeyboardManager = new CustomKeyboardManager(this);
        customKeyboardManager.attachToEdit(edit,new Keyboard(this, R.xml.custom_number));

    }

    private EditText edit;
    private CustomKeyboardManager customKeyboardManager;


}
