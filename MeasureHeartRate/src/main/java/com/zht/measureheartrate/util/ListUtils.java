package com.zht.measureheartrate.util;

import java.util.List;

/**
 * @Date 2023/2/21 09:35
 * @Author zhanghaitao
 * @Description
 */
public class ListUtils {

    public static double average(List<Double> doubleList) {
        if (doubleList == null || doubleList.size() == 0) {
            return 0d;
        }
        double sum = 0;
        for (Double num : doubleList) {
            sum += num;
        }
        return sum / doubleList.size();
    }

}
