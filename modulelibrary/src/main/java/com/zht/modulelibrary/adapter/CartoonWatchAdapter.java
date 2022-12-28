package com.zht.modulelibrary.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.constant.ParamsConstants;
import com.zht.modulelibrary.R;
import com.zht.modulelibrary.bean.ChapterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/16 16:19
 * @Author zhanghaitao
 * @Description
 */
public class CartoonWatchAdapter extends RecyclerView.Adapter<CartoonWatchAdapter.CartoonWatchViewHolder> {

    private List<String> mData = new ArrayList<>();

    public void setNewData(List<String> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public CartoonWatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_cartoon_watch_rv, parent, false);
        return new CartoonWatchViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(CartoonWatchViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext())

                .load(mData.get(position))
                .apply(new RequestOptions().error(R.mipmap.ic_empty_large).placeholder(R.mipmap.ic_empty_large))

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("cartoon", String.format("第%d页加载失败：", (position + 1)) + isFirstResource);
                        Log.e("cartoon", mData.get(position));

                        if (e != null) {
                            e.printStackTrace();
                            Log.e("cartoon", e.getMessage());
                        }

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into((ImageView) holder.itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CartoonWatchViewHolder extends RecyclerView.ViewHolder {

        public CartoonWatchViewHolder(View itemView) {
            super(itemView);

        }
    }


}