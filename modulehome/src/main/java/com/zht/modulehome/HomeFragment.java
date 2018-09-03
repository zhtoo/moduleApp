package com.zht.modulehome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.modulelib.OpenFileBySystem;
import com.zht.modulelib.base.BaseFragment;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
@Route(path = "/modulehome/homefragment")
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private EditText editText;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        view.findViewById(R.id.tv_module_one).setOnClickListener(this);
        view.findViewById(R.id.tv_module_two).setOnClickListener(this);
        view.findViewById(R.id.tv_module_fragment).setOnClickListener(this);
        view.findViewById(R.id.bt_home_file_path).setOnClickListener(this);

        editText = view.findViewById(R.id.ed_home_file_path);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_module_one) {
            // 1. 应用内简单的activity跳转
            ARouter.getInstance().build("/moduleone/main")
                    .navigation();
        } else if (view.getId() == R.id.tv_module_two) {
            // 2. 应用内简带参数的activity跳转
            ARouter.getInstance().build("/moduletwo/main")
                    .withString("name", "zhanghaitao")
                    .navigation();
        } else if (view.getId() == R.id.tv_module_fragment) {
            // 3. 应用内fragment的获取
            Intent intent = new Intent(getContext(), ShowFragmentActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.bt_home_file_path){
            String s = editText.getText().toString();
            OpenFileBySystem.openFileByPath(getContext(),s);
        }
    }
}
