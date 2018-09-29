package com.zht.common.listUI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/11.
 * List的适配器，因为刷新空件不能满足自己的刷新要求，
 * 所以基于RecyclerView的Adapter来写一个支持刷新的Adapter
 */

public abstract class ListAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mList = new ArrayList<>();
    public Context mContext;

    //    private SpecialView mEmptyView;
    private boolean showNoMoreData;

    //itemType
    private final int EMPTY_VIEW = -2;
    private final int No_More_Data = -1;

    public ListAdapter(Context context) {
        this.mContext = context;
        showNoMoreData = false;
//        mEmptyView = new SpecialView(context);
        // mEmptyView.showLoading();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            //展示空视图或者调用传入adapter方法
            //实例化一个LinearLayout
            LinearLayout linearLayout = new LinearLayout(parent.getContext());
            //LinearLayout默认是水平居中，现在改为垂直居中
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            //设置LinearLayout属性(宽和高)
            LinearLayout.LayoutParams layoutParams
                    = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            //设置边距
            layoutParams.setMargins(0, 0, 0, 0);
            //设置剧中
            linearLayout.setGravity(Gravity.CENTER);
            //将以上的属性赋给LinearLayout
            linearLayout.setLayoutParams(layoutParams);
            //  mEmptyView.setLayoutParams(layoutParams);
//            linearLayout.addView(mEmptyView);
            return new RecyclerView.ViewHolder(linearLayout) {
            };

        } else if (viewType == No_More_Data) {
            //展示空视图或者调用传入adapter方法
            //实例化一个LinearLayout
            LinearLayout linearLayout = new LinearLayout(mContext);
            //LinearLayout默认是水平居中，现在改为垂直居中
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            //设置LinearLayout属性(宽和高)
            LinearLayout.LayoutParams layoutParams
                    = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置边距
            layoutParams.setMargins(0, 0, 0, 0);
            //设置剧中
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setBackgroundColor(0xFFFFFFFF);
            //将以上的属性赋给LinearLayout
            linearLayout.setLayoutParams(layoutParams);
            TextView mText = new TextView(mContext);
            mText.setText("---已经到底啦---");
            //  mText.setLayoutParams(layoutParams);
            mText.setGravity(Gravity.CENTER);
            linearLayout.addView(mText);
            return new RecyclerView.ViewHolder(linearLayout) {
            };
        }

        return onCreateChildViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() != EMPTY_VIEW &&
                holder.getItemViewType() != No_More_Data) {
            bindView(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() == 0) {
            return 1;
        } else if (showNoMoreData) {
            return mList.size() + 1;
        }
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 0) {
            return EMPTY_VIEW;
        } else if (showNoMoreData && position == mList.size()) {
            return No_More_Data;
        }
        return getChildViewType(position);
    }

    /**
     * 父类只维护空数据视图和没有数据视图，其他视图交由子类实现
     *
     * @param position
     * @return
     */
    public abstract int getChildViewType(int position);

    public abstract RecyclerView.ViewHolder onCreateChildViewHolder(@NonNull ViewGroup parent, int viewType);

    public void bindView(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    public void updata(List<T> mData, boolean isRefresh) {
        if (isRefresh) {
            mList.clear();
            if (mList.size() == 0) {
                showEmpty();
            }
        }
        mList.addAll(mData);
        notifyDataSetChanged();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        public ListViewHolder(View itemView) {
            super(itemView);
        }
    }


    public void showEmpty() {
        this.mList.clear();
//        if (mEmptyView != null) {
//            mEmptyView.showEmpty("暂无数据", null, null);
//        }
    }

    public void showNoMoreData(boolean showNoMoreData) {
        this.showNoMoreData = showNoMoreData;
        notifyDataSetChanged();
    }
}
