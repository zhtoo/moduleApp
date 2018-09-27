package com.zht.moduletool.sample;


import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.zht.moduletool.R;


/**
 * Created by ZhangHaitao on 2018/9/27.
 */
@Route(path = "/sample/VideoActivity")
public class VideoActivity extends AppCompatActivity {

    private final static String TAG = "VideoActivity";

    private VideoView videoView;
    private View bgView;
    private LinearLayout videoTop;
    private LinearLayout videoBottom;
    private SeekBar videoProgress;
    private LinearLayout loadingView;
    private TextView videoTime;
    private TextView videoTitle;
    private TextView videoErrorText;
    private LinearLayout videoError;
    private ImageView videoPlay;

    private Runnable progressRun;
    private long timeDuration;
    private long currentDuration;
    private String timeDurationAtr;

    private long mPlayTime = 0;
    private long mStartPlayTime = 0;

    private ImageView loading;


    private static int pay_video = 666;
    private static int hide_menu = 888;
    private static int ebd_video = 999;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == pay_video) {
                // 进度条控制
                currentDuration = videoView.getCurrentPosition();// 精确到秒数
                int percent = (int) ((float) currentDuration * videoProgress.getMax() / (float) timeDuration);

                videoProgress.setProgress(percent);

                Log.e(TAG, "progress:" + videoProgress.getProgress()
                        + "        Max:" + videoProgress.getMax());

                if (videoView.isPlaying()) {
                    sendEmptyMessageDelayed(pay_video, 50);
                } else {
                    // 显示工具条
                    showMenu(true);
                    // 设置成重新启动
                    videoPlay.setImageResource(R.drawable.zb_video_play);
                }

                if (percent >= videoProgress.getMax()) {
                    bgView.setVisibility(View.VISIBLE);

                }

                // 时间显示
                String currentDurationStr = getVideoTimeStr(currentDuration);
                SpannableStringBuilder style = new SpannableStringBuilder(currentDurationStr + "/" + timeDurationAtr);
                style.setSpan(new ForegroundColorSpan(Color.WHITE), 0, currentDurationStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new ForegroundColorSpan(Color.parseColor("#444444")), currentDurationStr.length() + 1, style.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                videoTime.setText(style);


            } else if (what == hide_menu) {
                showMenu(false);
            } else if (what == ebd_video) {

            }
        }
    };
    private ImageView videoScreenOrientation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zb_common_video_watch);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //在这里需要判断是否是刘海屏
        /**
         * 也不知道是那个ZZ非得学iphone弄刘海屏
         */

        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        videoView = (VideoView) findViewById(R.id.video_view);
        bgView = findViewById(R.id.bg_view);
        videoTop = (LinearLayout) findViewById(R.id.video_top);
        videoBottom = (LinearLayout) findViewById(R.id.video_bottom);
        videoProgress = (SeekBar) findViewById(R.id.video_progress);
        loadingView = (LinearLayout) findViewById(R.id.loading_view);
        videoTime = (TextView) findViewById(R.id.video_time);
        videoTitle = (TextView) findViewById(R.id.video_title);
        videoErrorText = (TextView) findViewById(R.id.video_error_text);
        videoError = (LinearLayout) findViewById(R.id.video_error);
        videoPlay = (ImageView) findViewById(R.id.video_play);
        videoScreenOrientation = (ImageView) findViewById(R.id.video_screen_orientation);

        initEvent();
        loading = findViewById(R.id.loading_view_image);

        Glide.with(this)
                .asGif()
                .load(R.drawable.zb_video_loading)
                .into(loading);


        String url =
        "http://www.zhanght.com:8080/0.mp4";

        videoProgress.setEnabled(false);
        videoTitle.setText("测试");// 标题
        videoView.setVideoPath(url);// 视频路径
        videoView.start();
    }


    private void initEvent() {
        // 视频播放
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                // 移除加载中提示
                removeLoading();
                videoProgress.setEnabled(true);
                //隐藏菜单栏
                handler.sendEmptyMessageDelayed(hide_menu, 1000);

                timeDuration = videoView.getDuration();// 精确到半秒数;

                timeDurationAtr = getVideoTimeStr(timeDuration);
                videoTime.setText("00:00/" + timeDuration);

                //在视频开始播放的时候记录当前时间
                startRecordTime();
                handler.sendEmptyMessageDelayed(pay_video, 50);

            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    loadError("Android版本过低，无法播放，可以到PC观看");
                    return true;
                }

                // 媒体服务器挂掉了。此时，程序必须释放MediaPlayer 对象，并重新new 一个新的。
                if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                    loadError("网络服务错误");
                    // 文件不存在或错误，或网络不可访问错误
                } else if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                    if (extra == MediaPlayer.MEDIA_ERROR_IO) {
                        loadError("网络文件错误");
                        // 超时
                    } else if (extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
                        loadError("网络超时");
                    } else {
                        loadError("播放失败");
                    }
                }
                return true;
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                pauseRecordTime();
                videoView.seekTo(0);
                handler.sendEmptyMessageDelayed(ebd_video, 1000);
            }
        });

        // 进度条拖动
        videoProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // 进度条改变
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int currentTime = (int) (((float) progress / (float) seekBar.getMax()) * timeDuration);
                    videoView.seekTo(currentTime);
                    String currentDurationStr = getVideoTimeStr(currentTime);
                    SpannableStringBuilder style = new SpannableStringBuilder(currentDurationStr + "/" + timeDurationAtr);
                    style.setSpan(new ForegroundColorSpan(Color.WHITE), 0, currentDurationStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#444444")), currentDurationStr.length() + 1, style.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    videoTime.setText(style);
                }
            }

            // 开始拖动
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                //停止计时
                pauseRecordTime();
                if (videoView.isPlaying()) {
                    //停止刷新进度条
                    handler.removeMessages(pay_video);
                }
            }

            // 停止拖动
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int currentTime = (int) (((float) seekBar.getProgress() / (float) seekBar.getMax()) * timeDuration);
                videoView.seekTo(currentTime);
                if (videoView.isPlaying()) {
                    startRecordTime();
                    handler.sendEmptyMessageDelayed(pay_video, 50);
                }
            }
        });

        // 暂停或继续播放
        videoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!videoView.isPlaying()) {
                    startVideo();
                } else {
                    pauseVideo();
                }
            }
        });


        //横竖屏
        videoScreenOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int requestedOrientation = getRequestedOrientation();
                //竖屏
                if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    videoScreenOrientation.setImageResource(R.drawable.zb_video_vertical);
                    //设置横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {//横屏
                    videoScreenOrientation.setImageResource(R.drawable.zb_video_horizontal);
                    //设置竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                //隐藏菜单栏
                handler.sendEmptyMessageDelayed(hide_menu, 1500);

            }
        });
    }

    /**
     * 移除等待页面
     */
    private void removeLoading() {
        if (loadingView != null) {
            ViewGroup parent = (ViewGroup) loadingView.getParent();
            parent.removeView(loadingView);
            loadingView = null;
            bgView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示菜单
     *
     * @param show
     */
    private void showMenu(boolean show) {
        if (show) {
            videoTop.setVisibility(View.VISIBLE);
            videoBottom.setVisibility(View.VISIBLE);
        } else {
            videoTop.setVisibility(View.INVISIBLE);
            videoBottom.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 开始播放视频
     */
    private void startVideo() {
        if (videoView.isPlaying()) {
            return;
        }
        videoView.start();
        //刷新进度条
        handler.sendEmptyMessageDelayed(pay_video, 50);
        videoPlay.setImageResource(R.drawable.zb_video_pause);
        //开始记录播放时间
        startRecordTime();
    }

    /**
     * 停止播放视频
     */
    private void pauseVideo() {
        if (videoView.isPlaying()) {
            videoPlay.setImageResource(R.drawable.zb_video_play);
            videoView.pause();
            //停止刷新进度条
            handler.removeMessages(pay_video);
            //停止记录播放时间
            pauseRecordTime();
        }
    }

    /**
     * onCreate -> onStart -> onResume -> onPause -> onStop -> onDestroy
     * onStart -> Activity开始被用户所见
     * onResume->Activity可以响应用户交互
     * <p>
     * <p>
     * 从ActivityA跳转到ActivityB时AB的生命周期：
     * 1、ActivityA的创建:onCreate ->  onStart -> onResume。
     * 2、从ActivityA跳转到B Activity，假设B全部遮挡住了A
     * 将依次执行A:onPause -> B:onCreate -> B:onStart -> B:onResume -> A:onStop
     * 3、此时如果点击Back键，
     * 将依次执行B:onPause -> A:onRestart -> A:onStart -> A:onResume -> B:onStop -> B:onDestroy。
     *
     * 4a、此时如果按下Back键，系统返回到桌面，
     * 并依次执行A:onPause -> A:onStop -> A:onDestroy。
     *
     * 4b、此时如果按下Home键（非长按），
     * 系统返回到桌面，并依次执行A:onPause -> A:onStop。
     * Back键和Home键主要区别在于是否会执行onDestroy。
     *
     * 此时如果长按Home键，不同手机可能弹出不同内容，
     * Activity生命周期未发生变化（由小米2s测的，不知道其他手机是否会对Activity生命周期有影响）。
     */


    @Override
    protected void onPause() {
        super.onPause();
        if(videoView.isPlaying()){
            videoView.pause();
            pauseRecordTime();
        }
    }





    /**
     * 开始计时
     */
    private void startRecordTime() {
        mStartPlayTime = System.currentTimeMillis();
        Log.e(TAG, "开始计时  mPlayTime:" + mPlayTime / 1000 + "s");
    }

    /**
     * 暂停计时
     */
    private void pauseRecordTime() {
        if (mStartPlayTime > 0) {
            mPlayTime += (System.currentTimeMillis() - mStartPlayTime);
            mStartPlayTime = -1;
        }
        Log.e(TAG, "结束计时  mPlayTime:" + mPlayTime / 1000 + "s");
    }

    /**
     * 显示错误信息
     *
     * @param msg
     */
    private void loadError(String msg) {
        // 移除等待页面
        removeLoading();
        // 错误提示
        videoErrorText.setText(msg);
        videoError.setVisibility(View.VISIBLE);
        bgView.setVisibility(View.VISIBLE);
    }

    /**
     * 返回上一页
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (inRangeOfView(videoTop, ev) || inRangeOfView(videoBottom, ev)) {
                    break;
                }
                showMenu(videoTop.getVisibility() == View.INVISIBLE);
                break;
            }
        }
        return true;
    }


    public static String getVideoTimeStr(long time) {
        String timeStr = "";
        //时
        long hour = time / 1000 / 60 / 60;
        if (hour > 0) {
            timeStr += hour + ":";
        }
        // 分
        long min = time / 1000 / 60 % 60;
        timeStr += String.format("%02d", min) + ":";
        // 秒
        long sec = time / 1000 % 60;
        timeStr += String.format("%02d", sec);
        return timeStr;
    }

    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x || ev.getRawX() > (x + view.getWidth()) || ev.getRawY() < y || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

}
