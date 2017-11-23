package com.mysurfaceviewdemo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SurfaceView sv;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [1] 找到控件
        sv = (SurfaceView) findViewById(R.id.sv);

        // [2] 创建SurfaceView Holder，用于维护视频播放的内容
        holder = sv.getHolder();

        // [3] 增加holder的生命周期
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //[4] 创建播放器
                createMediaPlayer();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //当视频停止的时候，保存当前的播放位置
               if (mediaPlayer != null && mediaPlayer.isPlaying()){
                   //停止视频播放
                   mediaPlayer.stop();

                   //获取当前的播放位置
                   currentPosition = mediaPlayer.getCurrentPosition();
               }
            }
        });
    }

    private void createMediaPlayer() {
        // 创建MeidaPlayer的对象
        mediaPlayer = new MediaPlayer();

        // 重置mediaPlayer
        mediaPlayer.reset();

        // 设置播放源
        try {
            mediaPlayer.setDataSource("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 设置异步准备
        mediaPlayer.prepareAsync();

        // 设置显示给sv
        mediaPlayer.setDisplay(holder);

        // 准备完成监听
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                // 准备完成之后播放视频
                mediaPlayer.start();

                // seek到上次播放的位置继续播放
                mediaPlayer.seekTo(currentPosition);
            }
        });
    }
}
