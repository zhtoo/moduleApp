package com.zht.moduletool.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.util.ToastUtil;
import com.zht.moduletool.R;

/**
 * Created by ZhangHaitao on 2018/10/15
 */
@Route(path = "/sample/PermissionAcrivity")
public class PermissionAcrivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission);

        button = findViewById(R.id.checkPermission);

        button.setOnClickListener(this);

    }


    private void checkPermission(View view){



        ToastUtil.showToast("可以了");
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.checkPermission){
            checkPermission(v);
        }


    }
}
