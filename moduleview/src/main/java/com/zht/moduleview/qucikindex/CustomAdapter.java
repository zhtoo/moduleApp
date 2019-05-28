package com.zht.moduleview.qucikindex;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zht.moduleview.R;

import java.util.ArrayList;

/**
 * Created by ZhangHaitao on 2019/4/18
 */
public class CustomAdapter extends BaseAdapter {

    private ArrayList<Custom> list;

    public CustomAdapter(ArrayList<Custom> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.list_adapter, null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //绑定数据
        Custom custom = list.get(position);
        //显示首字母
        String firstLetter = custom.pinyin.charAt(0) + "";
        firstLetter = firstLetter.toUpperCase();

        if (position > 0) {
            //上一个条目的首字母
            String lastLetter = list.get(position - 1).pinyin.charAt(0) + "";
            lastLetter = lastLetter.toUpperCase();
            //如果当前首字母和上一个相同，则隐藏首字母
            if (firstLetter.equalsIgnoreCase(lastLetter)) {
                holder.letter.setVisibility(View.GONE);
                holder.mItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemCilckListener != null) {
                            itemCilckListener.onItemCilck(position);
                        }
                    }
                });
            } else {
                //说明不相等，直接设置
                //由于是复用的，所以当需要显示的时候，要设置为可见
                holder.letter.setVisibility(View.VISIBLE);
                holder.letter.setText(firstLetter);
            }
        } else {
            holder.letter.setVisibility(View.VISIBLE);
            holder.letter.setText(firstLetter);
        }
        holder.name.setText(custom.name);

        return convertView;
    }


    static class ViewHolder {
        TextView letter;
        TextView name;
        LinearLayout mItem;

        ViewHolder(View view) {
            letter = (TextView) view.findViewById(R.id.letter);
            name = (TextView) view.findViewById(R.id.name);
            mItem = (LinearLayout) view.findViewById(R.id.item);
            view.setTag(this);
        }
    }

    private OnItemCilckListener itemCilckListener;

    public interface OnItemCilckListener {
        void onItemCilck(int position);
    }

    public void setItemCilckListener(OnItemCilckListener itemCilckListener) {
        this.itemCilckListener = itemCilckListener;
    }


}