package com.zht.modulelibrary.activity;

import androidx.viewbinding.ViewBinding;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulelibrary.databinding.ActivityJsoupBinding;

/**
 * @Date 2022/12/15 17:47
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.JSOUP_ACTIVITY)
public class JsoupActivity extends BaseViewBindingActivity {

    @Override
    protected ViewBinding getViewBinding() {
        return ActivityJsoupBinding.inflate(getLayoutInflater());
    }
}
