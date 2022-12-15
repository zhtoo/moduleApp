package com.zht.modulepersonal;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseFragment;
import com.zht.common.constant.ARoutePathConstants;

/**
 * Created by ZhangHaitao on 2018/9/29.
 */
@Route(path = ARoutePathConstants.Personal.PERSONAL_FRAGMENT)
public class PersonalFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }
}
