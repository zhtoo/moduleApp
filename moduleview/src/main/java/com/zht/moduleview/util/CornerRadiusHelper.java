package com.zht.moduleview.util;

/**
 * @Date 2024/2/2 17:44
 * @Author zhanghaitao
 * @Description
 */
public class CornerRadiusHelper {

    public float[] createCornerRadii(
            float radiusLeftTop,
            float radiusRightTop,
            float radiusRightBottom,
            float radiusLeftBottom
    ) {
        return new float[]{
                radiusLeftTop, radiusLeftTop,
                radiusRightTop, radiusRightTop,
                radiusRightBottom, radiusRightBottom,
                radiusLeftBottom, radiusLeftBottom};
    }


    public float[] createCornerRadii(
            float radius,
            float radiusLeftTop,
            float radiusRightTop,
            float radiusRightBottom,
            float radiusLeftBottom
    ) {
        if (radius > 0) {
            return new float[]{
                    radius, radius,
                    radius, radius,
                    radius, radius,
                    radius, radius};
        }
        return createCornerRadii(radiusLeftTop,
                radiusRightTop,
                radiusRightBottom,
                radiusLeftBottom);
    }

}
