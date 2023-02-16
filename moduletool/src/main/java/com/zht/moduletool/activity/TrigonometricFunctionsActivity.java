package com.zht.moduletool.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduletool.databinding.ActivityTrigonometricFunctionsBinding;

/**
 * @Date 2023/2/3 17:45
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Tool.TRIGONOMETRIC_FUNCTIONS_ACTIVITY)
public class TrigonometricFunctionsActivity extends BaseViewBindingActivity<ActivityTrigonometricFunctionsBinding> {

    public void clickComputer(View view) {

        binding.tvSin.setText("" + Math.sin(Math.toRadians(Double.parseDouble(binding.etSin.getText().toString()))));
        binding.tvCos.setText("" + Math.cos(Math.toRadians(Double.parseDouble(binding.etCos.getText().toString()))));
        binding.tvTan.setText("" + Math.tan(Math.toRadians(Double.parseDouble(binding.etTan.getText().toString()))));

        binding.tvASin.setText("" + Math.toDegrees(Math.asin(Double.parseDouble(binding.etASin.getText().toString()))));
        binding.tvACos.setText("" + Math.toDegrees(Math.acos(Double.parseDouble(binding.etACos.getText().toString()))));
        binding.tvATan.setText("" + Math.toDegrees(Math.atan(Double.parseDouble(binding.etATan.getText().toString()))));




    }

}
