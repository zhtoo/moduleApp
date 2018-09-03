package com.zht.moduletool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.modulelib.base.BaseFragment;
import com.zht.modulelib.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
@Route(path = "/moduletool/toolsfragment")
public class ToolsFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tools;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        view.findViewById(R.id.m_button).setOnClickListener(this);
        view.findViewById(R.id.m_button_center).setOnClickListener(this);
        view.findViewById(R.id.m_button_top).setOnClickListener(this);
        view.findViewById(R.id.m_button_long).setOnClickListener(this);
        view.findViewById(R.id.m_button_center_long).setOnClickListener(this);
        view.findViewById(R.id.m_button_top_long).setOnClickListener(this);
        view.findViewById(R.id.m_button_top_icon).setOnClickListener(this);
        view.findViewById(R.id.m_button_myself).setOnClickListener(this);

    }

    public void toastClick(View view) {
        int id = view.getId();
        if (id == R.id.m_button) {
            ToastUtil.showToast("showToast");
        } else if (id == R.id.m_button_center) {
            ToastUtil.showToastCenter("showToastCenter");
        } else if (id == R.id.m_button_top) {
            ToastUtil.showToastTop("showToastTop");
        } else if (id == R.id.m_button_long) {
            ToastUtil.showLongToast("showLongToast");
        } else if (id == R.id.m_button_center_long) {
            ToastUtil.showLongToastCenter("showLongToastCenter");
        } else if (id == R.id.m_button_top_long) {
            ToastUtil.showLongToastTop("showLongToastTop");
        }else
        if (id == R.id.m_button_top_icon) {
            ToastUtil.showImageToast("showImageToast", R.mipmap.ic_launcher);
        }else
        if (id == R.id.m_button_myself) {
            ToastUtil.showCustomerToast("showCustomerToast");
        }
    }

    @Override
    public void onClick(View view) {
        toastClick( view);
    }
}
