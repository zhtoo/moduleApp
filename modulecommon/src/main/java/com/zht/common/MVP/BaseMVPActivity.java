package com.zht.common.MVP;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ZhangHaitao on 2018/10/8
 */
public abstract class BaseMVPActivity<T> extends AppCompatActivity {

    public T mPresenter;

   public abstract void initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }
}
