package com.broadcastreceiverdemo;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //实例化BroadcastReceiver对象
    private final MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //实例化BroadcastReceiver子类 &  IntentFilter
        IntentFilter intentFilter = new IntentFilter();

        //设置接收广播的类型
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");

        //调用Context的registerReceiver（）方法进行动态注册
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //销毁在onResume()方法中的广播
        unregisterReceiver(myBroadcastReceiver);
    }
}
