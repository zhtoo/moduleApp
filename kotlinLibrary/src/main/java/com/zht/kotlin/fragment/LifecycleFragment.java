package com.zht.kotlin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * @Date 2024/9/6 17:06
 * @Author zhanghaitao
 * @Description
 */
public abstract class LifecycleFragment extends Fragment {

    private final static String INDEX = "index";

    private static int lastIndex = -1;
    private int index = -1;

    @Override
    public void onAttach(@NonNull Context context) {
        lastIndex++;
        index = lastIndex;
        Log.e("test", "onAttach:" + index);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("test", "onCreate:" + index);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("test", "onCreateView:" + index);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.e("test", "onStart:" + index);
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e("test", "onResume:" + index);
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e("test", "setUserVisibleHint(" + isVisibleToUser + "):" + index);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e("test", "onHiddenChanged:(" + hidden + ")->" + index);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onPause() {
        Log.e("test", "onPause:" + index);
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.e("test", "onSaveInstanceState:" + index);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        Log.e("test", "onStop:" + index);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e("test", "onDestroyView:" + index);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("test", "onDestroy:" + index);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("test", "onDetach:" + index);
        super.onDetach();
    }

}
