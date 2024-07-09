package com.zht.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @Date 2024/4/10 19:26
 * @Author zhanghaitao
 * @Description
 */
public class LifecycleJavaFragment extends Fragment {

    private static String KEY_INDEX = "keyIndex";
    private String index = "";

    public static LifecycleJavaFragment newInstance(
            String index
    ) {
        LifecycleJavaFragment fragment = new LifecycleJavaFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String getIndex() {
        if (TextUtils.isEmpty(index)) {
            index = getArguments().getString(KEY_INDEX);
        }
        return index;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.e("test", "onAttach:" + getIndex());
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("test", "onCreate:" + getIndex());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("test", "onCreateView:" + getIndex());
        FrameLayout view = new FrameLayout(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        TextView textView = new TextView(getContext());
        textView.setText("page:" + getIndex());
        textView.setGravity(Gravity.CENTER);
        view.addView(textView, layoutParams);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("test", "onViewCreated:" + getIndex());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("test", "onSaveInstanceState:" + getIndex());
    }


    @Override
    public void onStart() {
        Log.e("test", "onStart:" + getIndex());
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e("test", "onResume:" + getIndex());
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e("test", "setUserVisibleHint:" + getIndex() + "->" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e("test", "onHiddenChanged:" + getIndex() + "->" + hidden);
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onPause() {
        Log.e("test", "onPause:" + getIndex());
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("test", "onStop:" + getIndex());
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e("test", "onDestroyView:" + getIndex());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("test", "onDestroy:" + getIndex());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("test", "onDetach:" + getIndex());
        super.onDetach();
    }


}
