package com.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //获取当前的action
        String action = intent.getAction();

        //对不同的action进行不同的操作
        if ("android.intent.action.HEADSET_PLUG".equals(action)){
            Toast.makeText(context,"插入耳机",Toast.LENGTH_SHORT).show();
        }else if ("android.intent.action.SCREEN_ON".equals(action)){
            Toast.makeText(context,"屏幕亮了",Toast.LENGTH_SHORT).show();
        }else if ("android.intent.action.SCREEN_OFF".equals(action)){
            //此处最好使用Log进行查看，因为屏幕黑了，Toast是显示不出来的
            Log.d("MyBroadcastReceiver","屏幕黑了");
        }
    }
}
