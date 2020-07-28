package com.zht.moduleview.PagerIndicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zht.moduleview.R;


/**
 * @author KCrason
 * @date 2018/1/23
 */
public class CustomPagerTabView extends PagerTabView {

    private TextView mTextView;

    public CustomPagerTabView(Context context) {
        super(context);
    }

    public CustomPagerTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPagerTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public TextView getTitleTextView() {
        return mTextView;
    }

    @Override
    public void initPagerTabView(Context context) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_view, this, false);
        mTextView = view.findViewById(R.id.title);
        addView(view);
    }
}
