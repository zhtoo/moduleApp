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

    private long detectionTime = 30000;//测量时间

    private long detectionInterval = 500;// 检测间隔
    private HeartRateCallBack heartRateCallBack;//检测回调
    private long beginTime;//开始时间
    private int heartRate;// 心率
    private boolean isStart;//是否开始
    private long lastHeartRateTime;// 最后一次心率时间
    private long lastPulseTime;// 最后一次脉冲时间
    private HeartRateState currentHeartbeatState = HeartRateState.RED;
    private HeartRateState preHeartbeatState;
    private int pulse;//脉冲
    private double redPixelAverage;//平均值
    private int redPixelSize = 50;//数据长度
    private final List<Double> redPixelContainer = new ArrayList<>();//保存记录
    private final List<Double> heartRateContainer = new ArrayList<>();//心率容器

    private Handler mHandler;

    public HeartRateProcessing() {
        mHandler = new Handler(Looper.getMainLooper());
    }

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
        this.pulse = 0;
    }

    public final void reset() {
        mHandler.post(sendWait);
        cleanData();
    }

    public void release() {
        if (mHandler != null) {
            mHandler.removeCallbacks(sendStart);
            mHandler.removeCallbacks(sendCurrentHeartRate);
            mHandler.removeCallbacks(sendWait);
            mHandler.removeCallbacks(sendResult);
        }
        mHandler = null;
        cleanData();
    }

    public final void inputData(double data) {
        if (data < 150) {//当读取的红色像素过低时,不缓存平均像素值
            reset();
            return;
        }
        if (redPixelContainer.size() < this.redPixelSize) {//保存前50个像素值
            redPixelContainer.add(Double.valueOf(data));
            return;
        }
        // 计算当前平均像素值
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
        if (dTime > detectionInterval) {
            this.lastHeartRateTime = currentTimeMillis;
            double rate = ((double) heartRate / dTime) * 1000 * 60;
            if (rate > 20 && rate < 180) {
                heartRateContainer.add(Double.valueOf(rate));
            }
            this.heartRate = 0;
        }
        // 每隔1秒回调一次当前心率和脉冲次数
        if (currentTimeMillis - this.lastPulseTime > detectionInterval) {
            this.lastPulseTime = currentTimeMillis;
            mHandler.post(sendCurrentHeartRate);
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

    /**
     * 开始测量
     */
    private void begin() {
        this.isStart = true;
        mHandler.post(sendStart);
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
        mHandler.post(sendResult);
    }


    private Runnable sendStart = new Runnable() {
        @Override
        public void run() {
            if (heartRateCallBack != null) {
                heartRateCallBack.onStartHeartRate();
            }
        }
    };


    private Runnable sendResult = new Runnable() {
        @Override
        public void run() {
            if (heartRateCallBack != null) {
                heartRateCallBack.onResultHeartRate((int) (ListUtils.average(heartRateContainer) + 0.5d));
            }
            reset();
        }
    };

    private Runnable sendWait = new Runnable() {
        @Override
        public void run() {
            if (heartRateCallBack != null) {
                heartRateCallBack.onWaitHeartRate();
            }
        }
    };

    private Runnable sendCurrentHeartRate = new Runnable() {
        @Override
        public void run() {
            if (heartRateCallBack != null) {
                heartRateCallBack.onPulse(pulse);
            }
            pulse = 0;
            if (!(heartRateContainer == null || heartRateContainer.isEmpty())) {
                int average = (int) ListUtils.average(heartRateContainer);
                if (heartRateCallBack != null) {
                    heartRateCallBack.onCurrentHeartRate(average);
                }
            }
        }
    };

}
