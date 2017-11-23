package com.myvideoviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [1]找到相关的控件
        VideoView vv = (VideoView) findViewById(R.id.vv);

        // [2]设置播放的路径
        vv.setVideoPath("MP4视频链接");

        // [3]开启视频
        vv.start();
    }
}
