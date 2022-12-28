package com.zht.modulelibrary.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.constant.ParamsConstants;
import com.zht.modulelibrary.adapter.CartoonAdapter;
import com.zht.modulelibrary.bean.ChapterBean;
import com.zht.modulelibrary.databinding.ActivityCartoonBinding;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Date 2022/12/16 16:09
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.CARTOON_ACTIVITY)
public class CartoonActivity extends BaseViewBindingActivity<ActivityCartoonBinding> {

    @Autowired(name = ParamsConstants.ACTIVITY_PARAMS.CHAPTER_LIST)
    ArrayList<ChapterBean> mChapterList;

    CartoonAdapter mAdapter;

    @Override
    protected ActivityCartoonBinding getViewBinding() {
        return ActivityCartoonBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (binding == null) {
            return;
        }
        binding.cartoonRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CartoonAdapter();
        binding.cartoonRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        if (mChapterList == null || mChapterList.isEmpty()) {
            return;
        }
        Collections.reverse(mChapterList);
        mAdapter.setNewData(mChapterList);
    }

}
