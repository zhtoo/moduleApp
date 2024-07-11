package com.zht.moduletool.activity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.view_binding.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.storage.StorageUtil;
import com.zht.moduletool.databinding.ActivityStorageBinding;

/**
 * @Date 2023/1/4 13:53
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Tool.STORAGE_ACTIVITY)
public class StorageActivity extends BaseViewBindingActivity<ActivityStorageBinding> {

    @Override
    protected void initView(Bundle savedInstanceState) {
        //设置TextView可以滚动
        binding.storageText.setMovementMethod(ScrollingMovementMethod.getInstance());

        StringBuffer sb = new StringBuffer();
        sb.append("手机存储：\n");
        sb.append(String.format("sd卡总大小：%s\n", StorageUtil.formatStorageSize(StorageUtil.getSystemTotalStorage())));
        sb.append(String.format("sd卡可用大小：%s\n", StorageUtil.formatStorageSize(StorageUtil.getSystemAvailStorage())));
        sb.append(String.format("系统内存大小：%s\n", StorageUtil.formatStorageSize(StorageUtil.getTotalMemory(this))));
        sb.append(String.format("系统内存可用大小：%s\n", StorageUtil.formatStorageSize(StorageUtil.getAvailMemory(this))));
        sb.append(String.format("app私有空间可用大小：%s\n", StorageUtil.formatStorageSize(StorageUtil.getAppAvailStorage(this))));

        binding.storageText.setText(sb.toString());
    }
}
