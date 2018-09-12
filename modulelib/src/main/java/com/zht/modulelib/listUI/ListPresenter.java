package com.zht.modulelib.listUI;

import android.util.Log;

/**
 * Created by ZhangHaitao on 2018/9/11.
 */

public abstract class ListPresenter implements ListContract.Presenter {

    protected ListContract.View viewRoot;

    public ListPresenter(ListContract.View viewRoot) {
        this.viewRoot = viewRoot;
    }

    public void loadDataFailure(String message) {
        Log.e("ListPresenter",message);
        if (viewRoot != null) {
            viewRoot.setError(message);
        }
    }

    public void onDestroy( ) {
        if (viewRoot != null) {
            viewRoot = null;
        }
    }
}
