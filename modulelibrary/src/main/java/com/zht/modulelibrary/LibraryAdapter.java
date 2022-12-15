package com.zht.modulelibrary;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zht.modulelibrary.bean.LibraryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/12/15 16:55
 * @Author zhanghaitao
 * @Description
 */
public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private List<LibraryBean> mData = new ArrayList<>();

    public void updata(List<LibraryBean> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_library_rv, parent, false);
        return new LibraryViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(LibraryViewHolder holder, final int position) {
        holder.textView.setText(mData.get(position).getName());
        try {
            holder.imageView.setImageResource(mData.get(position).getIcon());
        } catch (Exception e) {
            holder.imageView.setImageDrawable(null);
        }
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

    class LibraryViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public LibraryViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.library_icon);
            textView = itemView.findViewById(R.id.library_name);
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