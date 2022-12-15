package com.zht.moduleview.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/30.
 */
@Route(path = ARoutePathConstants.View.CUSTOMISE_VIEW_ACTIVITY)
public class CustomiseViewActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private CustomiseViewAdapter adapter;
    private ArrayList<String> list;
    private ArrayList<String> activitys;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customise_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecycler = findViewById(R.id.view_customise_rv);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CustomiseViewAdapter();
        mRecycler.setAdapter(adapter);

        intData();

        adapter.setListener(new CustomiseViewAdapter.ItemClickListener() {
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
        list.add("日历");
        activitys.add(ARoutePathConstants.View.CALENDAR_ACTIVITY);
        list.add("验证码输入框");
        activitys.add(ARoutePathConstants.View.VERIFY_CODE_EDIT_ACTIVITY);
        list.add("索引");
        activitys.add(ARoutePathConstants.View.QUICK_INDEX_ACTIVITY);
        list.add("上下滑动改变View高度");
        activitys.add(ARoutePathConstants.View.DRAG_ACTIVITY);
        list.add("ImageActivity");
        activitys.add(ARoutePathConstants.View.IMAGE_ACTIVITY);


        adapter.updata(list);
    }


    public static class CustomiseViewAdapter extends RecyclerView.Adapter<CustomiseViewAdapter.CustomiseViewHolder> {

        private List<String> mData = new ArrayList<>();

        public void updata(List<String> newData) {
            mData.clear();
            mData.addAll(newData);
            notifyDataSetChanged();
        }

        @Override
        public CustomiseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewGroup.LayoutParams layoutParams
                    = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) dip2px(parent.getContext(), 50));
            TextView mText = new TextView(parent.getContext());
            mText.setTextColor(0xFF555555);
            mText.setGravity(Gravity.CENTER);
            mText.setLayoutParams(layoutParams);

            return new CustomiseViewHolder(mText);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomiseViewHolder holder, final int position) {
            holder.textView.setText(mData.get(position));

            int red = (int) (Math.random()*200);
            int green = (int) (Math.random()*200);
            int bule = (int) (Math.random()*200);

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

        class CustomiseViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public CustomiseViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }

        public interface ItemClickListener {
            void onItemClick(int position);
        }

        public void setListener( ItemClickListener listener) {
            this.listener = listener;
        }

        private static  ItemClickListener listener;

    }


    /**
     * dip转换px
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

}
