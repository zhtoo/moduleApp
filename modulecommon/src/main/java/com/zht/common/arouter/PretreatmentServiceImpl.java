package com.zht.common.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PretreatmentService;

/**
 * @Date 2023/1/17 12:15
 * @Author zhanghaitao
 * @Description
 */
@Route(path = "/modulecommon/arouter/PretreatmentServiceImpl")
public class PretreatmentServiceImpl implements PretreatmentService {
    @Override
    public boolean onPretreatment(Context context, Postcard postcard) {
        // 跳转前预处理，如果需要自行处理跳转，该方法返回 false 即可
        return PretreatmentUtil.onPretreatment(context, postcard);
    }

    @Override
    public void init(Context context) {

    }
}
