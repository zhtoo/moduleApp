package com.zht.moduleview.activity.system;

import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.fragment.LifecycleJavaFragment;
import com.zht.common.mvc.BaseActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangHaitao on 2018/12/21
 */
@Route(path = ARoutePathConstants.View.VIEWPAGER_ACTIVITY)
public class ViewPagerActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private TabAdapter adapter;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_pager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView = findViewById(R.id.recyclerView);
        mViewPager = findViewById(R.id.viewPager);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter = new TabAdapter();
        mRecyclerView.setAdapter(adapter);

        viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.setSelected(position);
                mRecyclerView.smoothScrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private int startIndex = 0;
    private int refreshTimes = 0;

    public void clickRefresh(View view) {
        startIndex = 0;
        List<ItemBean> list = new ArrayList<>();
        for (int i = startIndex; i < (startIndex + 10); i++) {
            list.add(new ItemBean(refreshTimes + "->" + i));
        }
        startIndex += 10;
        refreshTimes++;
        adapter.setData(list);
        adapter.setSelected(0);
        viewPagerAdapter.setData(list);
        mViewPager.setCurrentItem(0, false);
    }

    public void clickLoadMore(View view) {
//        List<ItemBean> list = new ArrayList<>();
//        for (int i = startIndex; i < (startIndex + 10); i++) {
//            list.add(new ItemBean(refreshTimes + "->" + i));
//        }
//        startIndex += 10;
//        adapter.addData(list);
//        viewPagerAdapter.addData(list);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<ItemBean> mData = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void setData(List<ItemBean> newData) {
            mData.clear();
            mData.addAll(newData);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return LifecycleJavaFragment.newInstance(mData.get(position).getItemName());
        }

        @Override
        public int getCount() {
            return mData.size();
        }
    }

    class TabAdapter extends RecyclerView.Adapter<TabViewHolder> {

        private ArrayList<ItemBean> mData = new ArrayList<>();
        private int selectedIndex = 0;

        public void setData(List<ItemBean> newData) {
            for (ItemBean bean : mData) {
                Log.e("test", "bean:" + bean);
            }
            mData.clear();
            Log.e("test", "setList:");
            mData.addAll(newData);
            for (ItemBean bean : mData) {
                Log.e("test", "bean:" + bean);
            }
            notifyDataSetChanged();
        }

        public void removeAt(int position) {
            if (position < 0 || position >= mData.size()) {
                return;
            }
            this.mData.remove(position);
            notifyItemRemoved(position);
        }

        public void addData(List<ItemBean> newData) {
            mData.addAll(newData);
            notifyItemRangeInserted(this.mData.size() - newData.size(), newData.size());
        }

        @NonNull
        @Override
        public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_view_pager2_tab, parent, false);
            return new TabViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
            holder.onBind(position, mData.get(position), selectedIndex == position);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public void setSelected(int position) {
            if (selectedIndex == position) {
                return;
            }
            int lastPosition = selectedIndex;
            selectedIndex = position;
            notifyItemChanged(lastPosition);
            notifyItemChanged(selectedIndex);
        }
    }

    class TabViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_name);
        }

        public void onBind(int position, ItemBean bean, boolean selected) {
            textView.setText(bean.getItemName());
            TextPaint paint = textView.getPaint();
            paint.setFakeBoldText(selected);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.setSelected(position);
                    mViewPager.setCurrentItem(position, false);
                }
            });

        }
    }

    class ItemBean {

        private String itemName;

        public ItemBean(String itemName) {
            this.itemName = itemName;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

    }


}



