package com.zht.modulehome.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.modulehome.R;

/**
 * @Date 2023/1/12 17:56
 * @Author zhanghaitao
 * @Description
 * Hilt 是一个依赖注入库
 * 什么是依赖注入？
 *
 * 实现方式：
 * 1、构造函数注入：依赖由客户对象的构造函数传入。
 * 2、Setter注入：客户对象暴露一个能接收依赖的 setter 方法。
 * 3、接口注入：依赖的接口提供一个注入器方法，该方法会把依赖注入到任意一个传给它的客户。客户实现一个setter接口，可设置依赖。
 *
 */
@Route(path = ARoutePathConstants.Home.HILT_ACTIVITY)
public class HiltActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilt);
    }


    //https://www.bilibili.com/video/BV1Ki4y1A7hA/?p=4&spm_id_from=pageDriver&vd_source=690f4fdde91d38fa23d492d469109105

}