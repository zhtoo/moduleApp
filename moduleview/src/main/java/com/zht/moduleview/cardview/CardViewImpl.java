package com.zht.moduleview.cardview;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.annotation.Nullable;

/**
 * Created by ZhangHaitao on 2020/8/5
 */
interface CardViewImpl {
    void initialize(CardViewDelegate cardView, Context context, ColorStateList backgroundColor,
                    int shadowStartColor, int shadowEndColor,
                    float radius,float elevation, float maxElevation);

    void setRadius(CardViewDelegate cardView, float radius);

    float getRadius(CardViewDelegate cardView);

    void setElevation(CardViewDelegate cardView, float elevation);

    float getElevation(CardViewDelegate cardView);

    void initStatic();

    void setMaxElevation(CardViewDelegate cardView, float maxElevation);

    float getMaxElevation(CardViewDelegate cardView);

    float getMinWidth(CardViewDelegate cardView);

    float getMinHeight(CardViewDelegate cardView);

    void updatePadding(CardViewDelegate cardView);

    void onCompatPaddingChanged(CardViewDelegate cardView);

    void onPreventCornerOverlapChanged(CardViewDelegate cardView);

    void setBackgroundColor(CardViewDelegate cardView, @Nullable ColorStateList color);

    ColorStateList getBackgroundColor(CardViewDelegate cardView);
}