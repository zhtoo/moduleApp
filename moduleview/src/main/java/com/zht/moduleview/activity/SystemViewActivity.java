package com.zht.moduleview.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseActivity;
import com.zht.moduleview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/30.
 */
@Route(path = "/moduleview/activity/SystemViewActivity")
public class SystemViewActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private SystemViewAdapter adapter;
    private ArrayList<String> list;
    private ArrayList<String> activitys;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecycler = findViewById(R.id.view_system_rv);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SystemViewAdapter();
        mRecycler.setAdapter(adapter);

        intData();

        adapter.setListener(new SystemViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ARouter.getInstance()
                        .build(activitys.get(position))
                        .navigation();
            }
        });
    }

    private void intData() {
        list = new ArrayList<>();
        activitys = new ArrayList<>();
        list.add("EditText");
        activitys.add("/moduleview/activity/EditTextActivity");

        adapter.updata(list);
    }


    public static class SystemViewAdapter extends RecyclerView.Adapter<SystemViewAdapter.SystemViewHolder> {

        private List<String> mData = new ArrayList<>();

        public void updata(List<String> newData) {
            mData.clear();
            mData.addAll(newData);
            notifyDataSetChanged();
        }

        @Override
        public SystemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewGroup.LayoutParams layoutParams
                    = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) dip2px(parent.getContext(), 30));
            TextView mText = new TextView(parent.getContext());
            mText.setTextColor(0xFF555555);
            mText.setGravity(Gravity.CENTER);
            mText.setLayoutParams(layoutParams);

            return new SystemViewHolder(mText);
        }

        @Override
        public void onBindViewHolder(@NonNull SystemViewHolder holder, final int position) {
            holder.textView.setText(mData.get(position));

            int red = (int) (Math.random()*256);
            int green = (int) (Math.random()*256);
            int bule = (int) (Math.random()*256);

            int color = Color.argb(255, red, green, bule);

            holder.textView.setBackgroundColor(color);

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

        class SystemViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public SystemViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
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


    /**
     * dip转换px
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

}
