package com.zht.modulehome.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zht.modulehome.R;

/**
 * @Date 2023/2/16 17:49
 * @Author zhanghaitao
 * @Description
 */
public class BottomTabFragment extends Fragment {

    private static final String TAG = "LazyFragment";

    private static final String TAB_NAME = "tabName";
    private String tabName = "";

    private boolean hasOnResume = false;
    private boolean isVisible = false;

    private void loadData() {
        Log.e(TAG, tabName + " ---> " + "loadData:" + this.getClass().getSimpleName());
    }

    public void setTabName(String name){
        tabName = name;
    }

    public static BottomTabFragment newInstance(String name) {
        BottomTabFragment fragment = new BottomTabFragment();
        fragment.setTabName(name);
        Bundle bundle = new Bundle();
        bundle.putString(TAB_NAME, name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.e(TAG, tabName + " ---> " + "onAttach:" + this.getClass().getSimpleName());
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            tabName = arguments.getString(TAB_NAME);
        }
        Log.e(TAG, tabName + " ---> " + "onCreate:" + this.getClass().getSimpleName());
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, tabName + " ---> " + "onCreateView:" + this.getClass().getSimpleName());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_tab, container, false);
        TextView textView = view.findViewById(R.id.tv_tab_name);
        textView.setText(tabName);
        return view;
    }

    @Override
    public void onStart() {
        Log.e(TAG, tabName + " ---> " + "onStart:" + this.getClass().getSimpleName());
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e(TAG, tabName + " ---> " + "onResume:" + this.getClass().getSimpleName());
        super.onResume();

        if (hasOnResume) {// 已经被加载到界面中
            if (isVisible) {//且当前状态是显示的
                loadData();
            }
        } else {//第一次被加载到界面中
            hasOnResume = true;
            loadData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(TAG, tabName + " ---> " + "setUserVisibleHint(" + isVisibleToUser + "):" + this.getClass().getSimpleName());
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.e(TAG, tabName + " ---> " + "onHiddenChanged:(" + hidden + ")" + this.getClass().getSimpleName());
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        if (!hidden) {
            loadData();
        }
    }

    @Override
    public void onPause() {
        Log.e(TAG, tabName + " ---> " + "onPause:" + this.getClass().getSimpleName());
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.e(TAG, tabName + " ---> " + "onSaveInstanceState:" + this.getClass().getSimpleName());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        Log.e(TAG, tabName + " ---> " + "onStop:" + this.getClass().getSimpleName());
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, tabName + " ---> " + "onDestroyView:" + this.getClass().getSimpleName());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, tabName + " ---> " + "onDestroy:" + this.getClass().getSimpleName());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e(TAG, tabName + " ---> " + "onDetach:" + this.getClass().getSimpleName());
        super.onDetach();
    }

}
