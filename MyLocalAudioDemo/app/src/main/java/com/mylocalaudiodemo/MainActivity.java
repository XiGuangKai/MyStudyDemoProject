package com.mylocalaudiodemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button btn_paly;
    private Button btn_pause;
    private Button btn_stop;
    private SeekBar sb;
    private MediaPlayer mediaPlayer;
    private static boolean ALLOWPERMISSION = false; // 判断权限是否已经允许
    private static boolean ISFIRSTPLAY = true; // 判断当前是否是第一次播放
    private Timer timer;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到相关的控件
        findView();

        // 设置监听
        setListen();

        if (Build.VERSION.SDK_INT >= 23) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }else{
                ALLOWPERMISSION = true;
            }
        }else{
            ALLOWPERMISSION = true;
        }

    }

    private void initMediaPlayer() {
        // 创建MediaPlayer的对象
        mediaPlayer = new MediaPlayer();

        // 重置mediaPlayer，避免出现状态异常、错误异常方法不能回调等异常
        mediaPlayer.reset();

        try {
            //设置本地音乐文件
            mediaPlayer.setDataSource("/mnt/sdcard/ChengDu_ZhaoLei.mp3");

            //播放音乐准备
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //当设置完一次MediaPlayer的参数之后，认为不在是第一次播放了
        ISFIRSTPLAY = false;

        // 获取当前音乐的长度
        int duration = mediaPlayer.getDuration();

        // 设置seekbar的总长度
        sb.setMax(duration);

        // 创建计时器，用于时时更新seekbar
        timer = new Timer();

        // 每秒获取一次当前的进度，然后更新到UI
        timer.schedule(new TimerTask() {
            @Override
            public void run() {//该方法每1 秒被调用一次

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            //获取当前的播放进度
                            currentPosition = mediaPlayer.getCurrentPosition();
                            //设置给SeekBar
                            sb.setProgress(currentPosition);
                        }
                    });
                }
        }, 0, 1000);

    }


    private void setListen() {
        btn_paly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ALLOWPERMISSION) {
                    if (ISFIRSTPLAY) {
                        //初始化MediaPlayer
                        initMediaPlayer();
                    }

                    //开始播放音乐
                    mediaPlayer.start();
                }
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    //暂停播放音乐
                    mediaPlayer.pause();
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    //停止音乐播放
                    mediaPlayer.stop();
                }

                //释放资源
                releseSource();
            }
        });

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //进度改变的时候调用
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            //拖动开始的时候调用
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //拖动停止的时候调用
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    //进度条，拖到哪里播放到哪里
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });
    }

    private void releseSource() {

        // 将timer释放掉
        if (timer != null){
            timer.cancel();
            timer = null;
            sb.setProgress(0);
        }

        //释放MediaPlayer资源
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        //将ISFIRSTPLAY还原
        ISFIRSTPLAY = true;
    }

    private void findView() {
        btn_paly = (Button) findViewById(R.id.btn_paly);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        sb = (SeekBar) findViewById(R.id.sb);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //权限获取成功
                ALLOWPERMISSION = true;
            }else{
                Log.d("MainActivity","Permission Failed");
            }
        }
    }
}
