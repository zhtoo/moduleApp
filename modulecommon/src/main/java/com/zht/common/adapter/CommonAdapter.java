package com.zht.common.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.R;
import com.zht.common.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/16 09:34
 * @Author zhanghaitao
 * @Description
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.CommonViewHolder> {

    private List<ItemBean> mData = new ArrayList<>();

    public void setNewData(List<ItemBean> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_common_rv, parent, false);
        return new CommonViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        holder.textView.setText(mData.get(position).getName());
        if (mData.get(position).getIcon() != 0) {
            holder.imageView.setImageResource(mData.get(position).getIcon());
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setImageDrawable(null);
            holder.imageView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(mData.get(position).getRouterPath())
                        .navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CommonViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public CommonViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_icon);
            textView = itemView.findViewById(R.id.item_name);
        }
    }


}