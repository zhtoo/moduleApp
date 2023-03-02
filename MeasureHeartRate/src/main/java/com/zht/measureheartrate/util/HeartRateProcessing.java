package com.zht.measureheartrate.util;

import android.os.Handler;
import android.os.Looper;

import com.zht.measureheartrate.enums.HeartRateState;
import com.zht.measureheartrate.interfaces.HeartRateCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/2/21 09:12
 * @Author zhanghaitao
 * @Description
 */
public class HeartRateProcessing {

    public final static String TAG = "BeatProcessing";

    private long detectionTime = 30000;
    private HeartRateCallBack heartRateCallBack;
    private long beginTime;
    private int heartRate;
    private boolean isStart;
    private long lastHeartRateTime;
    private long lastPulseTime;
    private HeartRateState currentHeartbeatState = HeartRateState.RED;
    private HeartRateState preHeartbeatState;
    private int pulse;
    private double redPixelAverage;
    private int redPixelSize = 50;
    private final List<Double> redPixelContainer = new ArrayList<>();
    private final List<Double> heartRateContainer = new ArrayList<>();

    public final void setHeartRateCallBack(HeartRateCallBack heartRateCallBack) {
        this.heartRateCallBack = heartRateCallBack;
    }

    public void setDetectionTime(long detectionTime) {
        this.detectionTime = detectionTime;
    }

    public void setRedPixelSize(int redPixelSize) {
        this.redPixelSize = redPixelSize;
    }

    public final void cleanData() {
        this.isStart = false;
        this.redPixelContainer.clear();
        this.heartRateContainer.clear();
        this.preHeartbeatState = null;
        this.heartRate = 0;
    }

    public final void reset() {
        cleanData();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (heartRateCallBack != null) {
                    heartRateCallBack.onWaitHeartRate();
                }
            }
        });
    }

    public final void inputData(double data) {
        if (data < 150) {
            reset();
        } else if (redPixelContainer.size() < this.redPixelSize) {//取50组数据计算平均像素
            redPixelContainer.add(Double.valueOf(data));
        } else {
            this.redPixelAverage = ListUtils.average(redPixelContainer);
            long currentTimeMillis = System.currentTimeMillis();
            if (data > this.redPixelAverage) {
                this.currentHeartbeatState = HeartRateState.RED;
                if (preHeartbeatState != null && preHeartbeatState != HeartRateState.RED) {
                    if (heartRateCallBack != null) {
                        heartRateCallBack.onState(true);
                    }
                    if (!this.isStart) {
                        this.lastPulseTime = currentTimeMillis;
                        begin();
                    }
                    this.pulse++;
                    this.heartRate++;
                }
            } else {
                this.currentHeartbeatState = HeartRateState.GREEN;
                if (HeartRateState.GREEN != this.preHeartbeatState && heartRateCallBack != null) {
                    heartRateCallBack.onState(false);
                }
            }
            int dTime = (int) (currentTimeMillis - this.lastHeartRateTime);
            // 每隔1秒计算一次心率
            if (dTime > 1000) {
                this.lastHeartRateTime = currentTimeMillis;
                double rate = ((double) heartRate / dTime) * 1000 * 60;
                if (rate > 20 && rate < 180) {
                    heartRateContainer.add(Double.valueOf(rate));
                }
                this.heartRate = 0;
            }
            // 每隔1秒回调一次当前心率和脉冲次数
            if (currentTimeMillis - this.lastPulseTime > 1000) {
                this.lastPulseTime = currentTimeMillis;
                final int mPulse = this.pulse;
                this.pulse = 0;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (heartRateCallBack != null) {
                            heartRateCallBack.onPulse(mPulse);
                        }
                        if (!(heartRateContainer == null || heartRateContainer.isEmpty())) {
                            int average = (int) ListUtils.average(heartRateContainer);
                            if (heartRateCallBack != null) {
                                heartRateCallBack.onCurrentHeartRate(average);
                            }
                        }
                    }
                });
            }
            if (currentTimeMillis - this.beginTime >= detectionTime && this.isStart) {
                end();
            }
            if (redPixelContainer.size() >= this.redPixelSize) {
                redPixelContainer.remove(0);
                redPixelContainer.add(Double.valueOf(data));
            }
            this.preHeartbeatState = this.currentHeartbeatState;
        }
    }

    /**
     * 开始测量
     */
    private void begin() {
        this.isStart = true;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (heartRateCallBack != null) {
                    heartRateCallBack.onStartHeartRate();
                }
            }
        });
        long currentTimeMillis = System.currentTimeMillis();
        this.beginTime = currentTimeMillis;
        this.lastHeartRateTime = currentTimeMillis;
        this.lastPulseTime = currentTimeMillis;
    }

    /**
     * 结束测量
     */
    private void end() {
        this.isStart = false;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (heartRateCallBack != null) {
                    heartRateCallBack.onResultHeartRate((int) (ListUtils.average(heartRateContainer) + 0.5d));
                }
                reset();
            }
        });
    }

}
