package com.zht.moduletool;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/5.
 */
public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ToolsViewHolder> {

    private List<String> mData = new ArrayList<>();

    public void updata(List<String> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public ToolsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_tools_rv, parent, false);
        return new ToolsViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(ToolsViewHolder holder, final int position) {
        holder.textView.setText(mData.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ToolsViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ToolsViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.start_router);
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    private static ItemClickListener listener;

}


