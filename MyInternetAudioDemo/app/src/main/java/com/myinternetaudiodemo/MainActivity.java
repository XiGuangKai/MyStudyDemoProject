package com.myinternetaudiodemo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        //初始化MediaPlayer
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        // 创建MediaPlayer的对象
        mediaPlayer = new MediaPlayer();

        // 重置mediaPlayer，避免出现状态异常、错误异常方法不能回调等异常
        mediaPlayer.reset();

        try {
            //设置网络音乐文件
            mediaPlayer.setDataSource("http://up.mcyt.net/md5/53/MjgxOTkwNTk=_Qq4329912.mp3");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //播放音乐准备
        mediaPlayer.prepareAsync();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

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

                // 开始播放
                mediaPlayer.start();
            }
        });

        //当设置完一次MediaPlayer的参数之后，认为不在是第一次播放了
        ISFIRSTPLAY = false;
    }


    private void setListen() {
        btn_paly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ISFIRSTPLAY) {
                    //初始化MediaPlayer
                    initMediaPlayer();
                }
                //开始播放音乐
                mediaPlayer.start();
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
        if (timer != null) {
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
}
