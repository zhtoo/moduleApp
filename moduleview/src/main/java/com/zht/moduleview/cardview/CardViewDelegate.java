package com.zht.moduleview.cardview;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by ZhangHaitao on 2020/8/5
 */
interface CardViewDelegate {
    void setCardBackground(Drawable drawable);

    Drawable getCardBackground();

    boolean getUseCompatPadding();

    boolean getPreventCornerOverlap();

    void setShadowPadding(int left, int top, int right, int bottom);

    void setMinWidthHeightInternal(int width, int height);

    View getCardView();
}