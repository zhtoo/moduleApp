package com.zht.modulelibrary.adapter;

import android.annotation.SuppressLint;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.constant.ParamsConstants;
import com.zht.modulelibrary.bean.ChapterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/16 16:19
 * @Author zhanghaitao
 * @Description
 */
public class CartoonAdapter extends RecyclerView.Adapter<CartoonAdapter.CartoonViewHolder> {

    private List<ChapterBean> mData = new ArrayList<>();

    public void setNewData(List<ChapterBean> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public CartoonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(com.zht.common.R.layout.item_cartoon_rv, parent, false);
        return new CartoonViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(CartoonViewHolder holder, final int position) {
        holder.textView.setText(mData.get(position).getChapterName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ARoutePathConstants.Library.CARTOON_WATCH_ACTIVITY)
                        .withInt(ParamsConstants.ACTIVITY_PARAMS.POSITION,position)
                        .withParcelableArrayList(ParamsConstants.ACTIVITY_PARAMS.CHAPTER_LIST, (ArrayList<? extends Parcelable>) mData)
                        .navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CartoonViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public CartoonViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(com.zht.common.R.id.item_name);
        }
    }


}