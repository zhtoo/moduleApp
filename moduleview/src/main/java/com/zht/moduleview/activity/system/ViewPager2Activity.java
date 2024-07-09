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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.fragment.LifecycleJavaFragment;
import com.zht.common.mvc.BaseActivity;
import com.zht.moduleview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZhangHaitao on 2024/04/25
 */
@Route(path = ARoutePathConstants.View.VIEWPAGER2_ACTIVITY)
public class ViewPager2Activity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private TabAdapter adapter;
    private ViewPager2 mViewPager;
    private ViewPager2Adapter viewPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_pager2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView = findViewById(R.id.recyclerView);
        mViewPager = findViewById(R.id.viewPager);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter = new TabAdapter();
        mRecyclerView.setAdapter(adapter);

        viewPagerAdapter = new ViewPager2Adapter(this);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                adapter.setSelected(position);
                mRecyclerView.smoothScrollToPosition(position);
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
        List<ItemBean> list = new ArrayList<>();
        for (int i = startIndex; i < (startIndex + 10); i++) {
            list.add(new ItemBean(refreshTimes + "->" + i));
        }
        startIndex += 10;
        adapter.addData(list);
        viewPagerAdapter.addData(list);
    }

    public void clickRemove(int position) {
        Toast.makeText(mContext, "删除：" + position, Toast.LENGTH_SHORT).show();
        adapter.removeAt(position);
        viewPagerAdapter.removeAt(position);
    }

    class ViewPager2Adapter extends FragmentStateAdapter {

        public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Log.e("test", "createFragment:" + position);
            return LifecycleJavaFragment.newInstance(mData.get(position).getItemName());
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
            Log.e("test", "onBindViewHolder:" + position);
            super.onBindViewHolder(holder, position, payloads);
        }

        @Override
        public long getItemId(int position) {
            return fragmentId.get(position);
        }

        @Override
        public boolean containsItem(long itemId) {
            Log.e("test", "containsItem:" + itemId);
            return fragmentId.contains(itemId);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        private ArrayList<ItemBean> mData = new ArrayList<>();
        private ArrayList<Long> fragmentId = new ArrayList<>();
        private long startId = System.nanoTime();

        public void setData(List<ItemBean> newData) {
            mData.clear();
            mData.addAll(newData);
            fragmentId.clear();
            for (int i = 0; i < mData.size(); i++) {
                fragmentId.add(startId++);
            }
            notifyDataSetChanged();
        }

        public void removeAt(int position) {
            if (position < 0 || position >= mData.size()) {
                return;
            }
            fragmentId.remove(position);
            this.mData.remove(position);
            notifyItemRemoved(position);
        }

        public void addData(List<ItemBean> newData) {
            startId = System.nanoTime();
            for (int i = 0; i < newData.size(); i++) {
                fragmentId.add(startId++);
            }
            mData.addAll(newData);
            notifyItemRangeInserted(this.mData.size() - newData.size(), newData.size());
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
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    clickRemove(position);
                    return true;
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


    /// 编码多个表情
    private List<Byte> encodeDeviceMultiEmoji(List<List<List<Integer>>> emojiPixels) {
        List<Byte> mergedEmojiPixels = new ArrayList<>();
        for (List<List<Integer>> singleEmojiPixels : emojiPixels) {
            mergedEmojiPixels.addAll(encodeDeviceEmoji(singleEmojiPixels));
        }
        return mergedEmojiPixels;
    }

    /// 编码单个表情
    private List<Byte> encodeDeviceEmoji(List<List<Integer>> emojiPixels) {
        List<Integer> mergedEmojiPixels = new ArrayList<>();
        for (List<Integer> rowEmojiPixels : emojiPixels) {
            mergedEmojiPixels.addAll(rowEmojiPixels);
        }

        int totalLength = mergedEmojiPixels.size();
        int remainder = totalLength % 8;
        int padding = remainder > 0 ? 8 - remainder : 0;

        for (int i = 0; i < padding; i++) {
            mergedEmojiPixels.add(remainder - 1, 0);
        }
        int unit8ListLength = mergedEmojiPixels.size() / 8;
        Byte[] encodeEmojiPixels = new Byte[unit8ListLength];
        for (int i = 0; i < mergedEmojiPixels.size(); i += 8) {
            byte byteValue = 0;
            for (int j = 0; j < 8; j++) {
                byteValue |= (mergedEmojiPixels.get(i + j) << (7 - j));
            }
            int index = unit8ListLength - (i / 8) - 1;
            encodeEmojiPixels[index] = byteValue;
        }
        return Arrays.asList(encodeEmojiPixels);
    }


}



