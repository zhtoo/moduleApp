package com.zht.moduleview.cardview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.RequiresApi;

/**
 * Created by ZhangHaitao on 2020/8/6
 */
@RequiresApi(17)
public class CardViewApi17Impl extends CardViewBaseImpl {

    @Override
    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper =
                new RoundRectDrawableWithShadow.RoundRectHelper() {
                    @Override
                    public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                              Paint paint) {
                        canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
                    }
                };
    }

}
