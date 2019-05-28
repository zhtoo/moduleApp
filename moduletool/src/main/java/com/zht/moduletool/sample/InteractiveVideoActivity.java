package com.zht.moduletool.sample;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.zht.moduletool.R;

/**
 * Created by ZhangHaitao on 2018/9/27.
 */

/**
 * 关于seekTo的时间不正确的问题？
 * 原因：
 * 1、seekTo是一个异步的方法。在seekTo还没有完成的时候就执行了start方法
 * 2、源视频有问题。seekTo跳转的位置是离参数所带的position最近的视频关键帧。
 * 解决方案：
 * 原因一：
 * // 在设置 VideoView 的 OnPrepared 监听，拿到 MediaPlayer 对象，然后设置MediaPlayer的OnSeekComplete监听
 * 代码如下：
 * videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
 *
 * @Override public void onPrepared(MediaPlayer mp) {
 * //设置 MediaPlayer 的 OnSeekComplete 监听
 * mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
 * @Override public void onSeekComplete(MediaPlayer mp) {
 * // seekTo 方法完成时的回调
 * if(isPause){
 * videoView.start();
 * isPause = !isPause;
 * }
 * }
 * });
 * }
 * });
 * <p>
 * 原因二：
 * 给视频添加关键帧  http://ffmpeg.org/
 * FFmpeg 相关命令行语句：
 * ffmpeg.exe -i "D:\in.mp4" -c:v libx264 -preset superfast -x264opts keyint=25 -acodec copy -f mp4 "D:\out.mp4"
 * <p>
 * 将源文件（D:\in.mp4）转换为每隔25帧设置一个关键帧，保存到"D:\out.mp4"
 */
@Route(path = "/sample/InteractiveVideoActivity")
public class InteractiveVideoActivity extends AppCompatActivity {

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
    private ImageView loading;
    private ImageView videoScreenOrientation;
    private TextView videoTotalTime;
    private FrameLayout internalContainer;
    private FrameLayout videoContent;

    private long timeDuration;
    private long currentDuration;
    private String timeDurationAtr;

    private static int pay_video = 666;
    private static int hide_menu = 888;
    private static int end_video = 999;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == pay_video) {
                changeSeekBar();
            } else if (what == hide_menu) {
                showMenu(false);
            } else if (what == end_video) {

            }
        }
    };


    private String[] listUrl = {

            "http://file.dev.zerobook.com/upload/vod_video/2019/01/11/92/1547185893799_video.mp4",
            "http://file.dev.zerobook.com/upload/vod_video/2019/01/11/92/1547185581694_video.mp4",
            "http://file.dev.zerobook.com/upload/vod_video/2019/01/11/92/1547185778082_video.mp4",
//            "http://hc.yinyuetai.com/uploads/videos/common/A7350166C4E3932730BD0B8AECFEEB9E.mp4",
//            "http://hc.yinyuetai.com/uploads/videos/common/F5F30167BFEEB0BE3F98772C53D4F530.mp4",
//            "http://221.228.226.5/15/t/s/h/v/tshvhsxwkbjlipfohhamjkraxuknsc/sh.yinyuetai.com/88DC015DB03C829C2126EEBBB5A887CB.mp4",
//            "http://hc.yinyuetai.com/uploads/videos/common/E51C0167C1D12CDF3376EA523803047F.mp4",
//            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
            //"http://www.zhanght.com:8080/0.mp4";
            //"http://10.0.2.2:8080/0.mp4";
    };
    private View mBrightness;
    private View mVolume;
    private View mProgressController;
    private ImageView mReplay;
    private TextView mReplayText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zb_common_interactive_video_watch);
        //隐藏状态栏
        //   getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Runtime runtime = Runtime.getRuntime();
        long l = runtime.maxMemory();//"最大可用内存
        long r = runtime.totalMemory();//"当前可用内存
        long l1 = runtime.freeMemory();//"当前空闲内存

        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化控件
        initView();
        //初始化点击事件
        initEvent();

        Glide.with(this)
                .asGif()
                .load(R.drawable.zb_video_loading)
                .into(loading);


        double random = Math.random();
        Log.e(TAG, "random: " + random);
        int position = (int) (random * listUrl.length);
        Log.e(TAG, "position: " + position);
//        String url = listUrl[position];
        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

        LinearLayout.LayoutParams lp =
                (LinearLayout.LayoutParams) videoContent.getLayoutParams();
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        lp.height = (int) (9 * getResources().getDisplayMetrics().widthPixels / 16);
        videoContent.setLayoutParams(lp);

        videoProgress.setEnabled(false);
        videoTitle.setText("测试");// 标题
        videoView.setVideoPath(url);// 视频路径
        videoView.start();

        // 当横屏时接着播放
        if (savedInstanceState != null) {
            // 得到进度
            int time = savedInstanceState.getInt("currentTime");
            // 接着播放
            videoView.seekTo(time);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    private void initView() {
        videoView = (VideoView) findViewById(R.id.video_view);
        bgView = findViewById(R.id.bg_view);
        videoTop = (LinearLayout) findViewById(R.id.video_top);
        videoBottom = (LinearLayout) findViewById(R.id.video_bottom);
        videoProgress = (SeekBar) findViewById(R.id.video_progress);
        loadingView = (LinearLayout) findViewById(R.id.loading_view);
        videoTime = (TextView) findViewById(R.id.video_time);

        videoContent = (FrameLayout) findViewById(R.id.video_content);
        internalContainer = (FrameLayout) findViewById(R.id.internal_container);
        videoTotalTime = (TextView) findViewById(R.id.video_total_time);
        videoTitle = (TextView) findViewById(R.id.video_title);
        videoErrorText = (TextView) findViewById(R.id.video_error_text);
        videoError = (LinearLayout) findViewById(R.id.video_error);
        videoPlay = (ImageView) findViewById(R.id.video_play);
        videoScreenOrientation = (ImageView) findViewById(R.id.video_screen_orientation);
        loading = findViewById(R.id.loading_view_image);

        mReplay = findViewById(R.id.replay_icon);
        mReplayText = findViewById(R.id.video_replay_text);
        mBrightness = findViewById(R.id.video_Lightness);
        mVolume = findViewById(R.id.video_Volume);
        mProgressController = findViewById(R.id.video_progress_controller);

//        findViewById(R.id.text_remind).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // videoView.stopPlayback();
//                double random = Math.random();
//                Log.e(TAG, "random: " + random);
//                int position = (int) (random * listUrl.length);
//                Log.e(TAG, "position: " + position);
//                String url = listUrl[position];
//                //将MP4格式的视频路径修改为m3u8
//                videoView.setVideoURI(Uri.parse(url));// 视频路径
//                videoView.start();
//            }
//        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        // 记录当前播放进度
        outState.putInt("currentTime", videoView.getCurrentPosition());
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
                //记录视频时长
                timeDuration = videoView.getDuration();// 精确到半秒数;
                //将long类型的时长转换为String
                timeDurationAtr = getVideoTimeStr(timeDuration);
                videoTime.setText("00:00");
                videoTotalTime.setText(timeDurationAtr);
                //发送定时消息，更新进度条状态
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
                videoView.seekTo(0);
                handler.sendEmptyMessageDelayed(end_video, 1000);
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
                    videoTime.setText(currentDurationStr);
                }
            }

            // 开始拖动
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
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
                    //隐藏状态栏
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } else {//横屏
                    videoScreenOrientation.setImageResource(R.drawable.zb_video_horizontal);
                    //设置竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    //显示状态栏
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                //隐藏菜单栏
                handler.sendEmptyMessageDelayed(hide_menu, 1500);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(getApplicationContext(), "横屏", Toast.LENGTH_SHORT).show();
            LinearLayout.LayoutParams lp =
                    (LinearLayout.LayoutParams) videoContent.getLayoutParams();
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
            videoContent.setLayoutParams(lp);

            //隐藏状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
//            Toast.makeText(getApplicationContext(), "竖屏", Toast.LENGTH_SHORT).show();
            LinearLayout.LayoutParams lp =
                    (LinearLayout.LayoutParams) videoContent.getLayoutParams();
            lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            lp.height = (int) (9 * getResources().getDisplayMetrics().widthPixels / 16);
            videoContent.setLayoutParams(lp);

            //显示状态栏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 当返回按钮按下的时候，如果是横屏状态则切换为竖屏
     */
    @Override
    public void onBackPressed() {
        int requestedOrientation = getRequestedOrientation();
        //竖屏
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            super.onBackPressed();
        } else {//横屏
            videoScreenOrientation.setImageResource(R.drawable.zb_video_horizontal);
            //设置竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //显示状态栏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //隐藏菜单栏
            handler.sendEmptyMessageDelayed(hide_menu, 1500);
        }

    }

    /**
     * 改变进度条
     */
    private void changeSeekBar() {
        // 进度条控制
        currentDuration = videoView.getCurrentPosition();
        int percent = (int) ((float) currentDuration * videoProgress.getMax() / (float) timeDuration);
        videoProgress.setProgress(percent);
        // 设置视频的缓冲进度
        int bufferPercentage = videoView.getBufferPercentage();
        videoProgress.setSecondaryProgress(bufferPercentage * videoProgress.getMax() / 100);

        if (videoView.isPlaying()) {
            handler.sendEmptyMessageDelayed(pay_video, 50);
        } else {
            // 显示工具条
            showMenu(true);
            // 设置成重新启动
            videoPlay.setImageResource(R.drawable.zb_video_play);
        }

        if (percent >= videoProgress.getMax()) {
            bgView.setVisibility(View.VISIBLE);
        }

        // 更新时间显示
        String currentDurationStr = getVideoTimeStr(currentDuration);
        videoTime.setText(currentDurationStr);
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
        //开始记录播放时间
        handler.sendEmptyMessageDelayed(pay_video, 50);
        videoPlay.setImageResource(R.drawable.zb_video_pause);
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

        }
    }

    /**
     * onCreate -> onStart -> onResume -> onPause -> onStop -> onDestroy
     * onStart -> Activity开始被用户所见
     * onResume->Activity可以响应用户交互
     * 从ActivityA跳转到ActivityB时AB的生命周期：
     * 1、ActivityA的创建:onCreate ->  onStart -> onResume。
     * 2、从ActivityA跳转到B Activity，假设B全部遮挡住了A
     * 将依次执行A:onPause -> B:onCreate -> B:onStart -> B:onResume -> A:onStop
     * 3、此时如果点击Back键，
     * 将依次执行B:onPause -> A:onRestart -> A:onStart -> A:onResume -> B:onStop -> B:onDestroy。
     * <p>
     * 4a、此时如果按下Back键，系统返回到桌面，
     * 并依次执行A:onPause -> A:onStop -> A:onDestroy。
     * <p>
     * 4、此时如果按下Home键（非长按），
     * 系统返回到桌面，并依次执行A:onPause -> A:onStop。
     * Back键和Home键主要区别在于是否会执行onDestroy。
     * <p>
     * 此时如果长按Home键，不同手机可能弹出不同内容，
     * Activity生命周期未发生变化（由小米2s测的，不知道其他手机是否会对Activity生命周期有影响）。
     */


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

    float startX = 0;
    float startY = 0;

    boolean changVolume;
    boolean changBrightness;
    boolean changProgress;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                startX = ev.getX();
                startY = ev.getY();

                changVolume = inRangeOfView(mVolume, ev);
                changBrightness = inRangeOfView(mBrightness, ev);
                changProgress = inRangeOfView(mProgressController, ev);

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float moveX = ev.getX();
                float moveY = ev.getY();
                float dX = moveX - startX;
                float dY = moveY - startY;

                if (changVolume && Math.abs(dX) < Math.abs(dY)) {
                    changVolume(-dY, mVolume.getHeight());
                } else if (changBrightness && Math.abs(dX) < Math.abs(dY)) {
                    changBrightness(-dY, mBrightness.getHeight());
                } else if (changProgress && Math.abs(dX) > Math.abs(dY)) {

                }
//                startX = moveX;
//                startY = moveY;
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (inRangeOfView(videoTop, ev) || inRangeOfView(videoBottom, ev)) {
                    break;
                } else if (inRangeOfView(videoContent, ev)) {
                    showMenu(videoTop.getVisibility() == View.INVISIBLE);
                } else if (videoTop.getVisibility() == View.VISIBLE) {
                    showMenu(videoTop.getVisibility() == View.INVISIBLE);
                }
                currentVolume = -1;
                currentBrightness = -1F;
                break;
            }
        }
        return true;
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

    /**
     * 时间转换
     *
     * @param time
     * @return
     */
    public static String getVideoTimeStr(long time) {
        long totalSeconds = time / 1000;
        int seconds = (int) (totalSeconds % 60);
        int minutes = (int) ((totalSeconds / 60) % 60);
        long hours = totalSeconds / 3600;
        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    private int currentVolume = -1;

    public void changVolume(float yDilta, int heightPixels) {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //当前音量
        if (currentVolume == -1) {
            currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        //获取最大音量
        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //计算音量的改变量
        float changeVolume = (yDilta / heightPixels) * streamMaxVolume;
        //改变后的音量
        int changeStreamVolume = (int) (currentVolume + changeVolume);
        if (changeStreamVolume <= 0) {
            changeStreamVolume = 0;
        } else if (changeStreamVolume > streamMaxVolume) {
            changeStreamVolume = streamMaxVolume;
        }
        //将改变后的音量设置给系统
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, changeStreamVolume, AudioManager.FLAG_SHOW_UI);
    }

    private float currentBrightness = -1F;

    public void changBrightness(float yDilta, int heightPixels) {
        //Android系统的亮度值是0~255,但是screenBrightness是一个0.0~1.0之间的float类型的参数
        float brightness = yDilta / heightPixels;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //获取界面当前的亮度
        if (currentBrightness == -1) {
            currentBrightness = lp.screenBrightness;
        }
        float changeBrightness = currentBrightness + brightness;
        if (changeBrightness <= 0) {
            changeBrightness= 0;
        } else if (changeBrightness > 1F) {
            changeBrightness = 1F;
        }
        lp.screenBrightness = changeBrightness;
        getWindow().setAttributes(lp);
    }


}