package com.zht.kotlin.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @Date 2024/4/10 19:19
 * @Author zhanghaitao
 * @Description
 */
public class LifecycleLogger implements DefaultLifecycleObserver {

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.e("test", "onCreate");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.e("test", "onStart");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.e("test", "onResume");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.e("test", "onPause");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.e("test", "onStop");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.e("test", "onDestroy");
    }
}
