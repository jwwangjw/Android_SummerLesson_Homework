package com.bytedance.videoplayer;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private customVideoView videoView;
    private SeekBar seekBar;
    private Button buttonPlay;
    private TextView textViewTime;
    private TextView textViewCurrentPosition;
    private TextView textViewStatus;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if (videoView.isPlaying()) {
                int current = videoView.getCurrentPosition();
                seekBar.setProgress(current);
                textViewCurrentPosition.setText(time(videoView.getCurrentPosition()));
            }
            handler.postDelayed(runnable, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (customVideoView) this.findViewById(R.id.videoView);
        videoView.setVideoPath(getVideoPath(R.raw.bytedance));
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                textViewTime.setText(time(videoView.getDuration()));
                buttonPlay.setEnabled(true);

            }
        });

        // 在播放完毕被回调
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // 发生错误重新播放
                play();
                Toast.makeText(MainActivity.this, "播放出错", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        textViewTime = (TextView) findViewById(R.id.textViewTime);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        // 为进度条添加进度更改事件
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        textViewCurrentPosition = (TextView) findViewById(R.id.textViewCurrentPosition);

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setEnabled(false);
        Button buttonInfo = (Button) findViewById(R.id.buttonInfo);

        buttonPlay.setOnClickListener(this);
        buttonInfo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPlay:
                play();
                break;
            case R.id.buttonInfo:
                stop();
                break;
            default:
                break;
        }
    }
    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        // 当进度条停止修改的时候触发
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            if (videoView.isPlaying()) {
                // 设置当前播放的位置
                videoView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

        }
    };

    protected void play() {

        if (buttonPlay.getText().equals("播放")) {
            buttonPlay.setText("暂停");
            handler.postDelayed(runnable, 0);
            videoView.start();
            seekBar.setMax(videoView.getDuration());

        } else

        {
            buttonPlay.setText("播放");
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        }

    }

    protected void stop() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置videoView全屏播放
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置videoView横屏播放

        //获取屏幕的宽高 height_01 ，width_01
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        android.view.Display display = wm.getDefaultDisplay();
        int height_01 = display.getHeight();
        int width_01 = display.getWidth();

        //我布局中videoView的父控件是RelativeLayout所以使用RelativeLayout.LayoutParams
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) videoView
                .getLayoutParams(); // 取控当前的布局参数
        layoutParams.height = height_01;//设置 当控件的高强
        layoutParams.width = width_01;
        videoView.setLayoutParams(layoutParams);
    }

    protected String time(long millionSeconds) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

}