package com.zht.common.listUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zht.common.R;

import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/11.
 */

public class ListFragment<T extends ListAdapter>
          extends Fragment
        implements ListContract.View {

//    private SmartRefreshLayout refreshView;
    private RecyclerView recyclerView;

    public T mAdapter;
    public ListPresenter mPresenter;

    public int currentPage;
    public int totalPages;
    private View mRootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView();
        return mRootView;
    }

    protected int getLayoutId() {
        return R.layout.fragment_common_list;
    }


    protected void initView() {

       // refreshView = (SmartRefreshLayout) mRootView;
        recyclerView = (RecyclerView) mRootView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (mAdapter != null) {
            recyclerView.setAdapter(mAdapter);
        }
        //设置刷新
//        refreshView.setOnRefreshLoadMoreListener(refreshListener);
//        refreshView.autoRefresh();
    }


//    private OnRefreshLoadMoreListener refreshListener = new OnRefreshLoadMoreListener() {
//        @Override
//        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//            if (currentPage == totalPages) {
//                refreshView.finishRefresh();
//                refreshView.finishLoadMore();
//
//                return;
//            }
//            if (mPresenter != null) {
//                mPresenter.getData(currentPage++);
//            }
//        }
//
//        @Override
//        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//            currentPage = 1;
//            totalPages = 1;
//            if(mAdapter !=null){
//                mAdapter.showNoMoreData(false);
//            }
//            if (mPresenter != null) {
//                mPresenter.getData(currentPage);
//            }
//
//            refreshView.setEnableLoadMore(true);
//        }
//    };

    public void setPresenter(ListPresenter presenter) {
        mPresenter = presenter;
    }

    public void setAdapter(T mAdapter) {
        this.mAdapter = mAdapter;
        if (recyclerView != null) {
            recyclerView.setAdapter(mAdapter);
        }
    }

    /**
     * 成功回调
     */
    @Override
    public void setData(int currentPage, int totalPages, List mData) {
//        refreshView.finishRefresh();
//        refreshView.finishLoadMore();

        this.currentPage = currentPage;
        this.totalPages = totalPages;

        if (mAdapter != null) {
            mAdapter.updata(mData, currentPage == 1);
            if(currentPage == totalPages){
                mAdapter.showNoMoreData(true);
            }
        }
    }

    /**
     * 错误回调
     *
     * @param text
     */
    @Override
    public void setError(String text) {
//        refreshView.setEnableLoadMore(false);
//        refreshView.finishRefresh();
//        refreshView.finishLoadMore();
        if (mAdapter != null) {
            mAdapter.showEmpty();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }
}
