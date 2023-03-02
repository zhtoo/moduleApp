package com.zht.measureheartrate.interfaces;

/**
 * @Date 2023/2/21 09:14
 * @Author zhanghaitao
 * @Description
 */
public interface HeartRateCallBack {

    void onCurrentHeartRate(int rate);

    default void onPulse(int pulse) {

    }

    void onResultHeartRate(int rate);

    void onStartHeartRate();

    default void onState(boolean inRedMode) {

    }

    void onWaitHeartRate();

}
