package com.aidltest2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aidlservicedemo.IService;

public class MainActivity extends AppCompatActivity {

    private Button bt_banjuzhuzheng;
    private banZhengConnectionService banZhengConn;
    private IService banzhengBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到对应的控件
        findView();

        // 连接到服务
        Intent intent = new Intent();
        intent.setAction("com.aidlservicedemo.Test");
        intent.setPackage("com.aidlservicedemo");
        banZhengConn = new banZhengConnectionService();
        bindService(intent,banZhengConn,BIND_AUTO_CREATE);

        // 设置监听器
        bt_banjuzhuzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    banzhengBinder.callBanJuZhuZheng();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void findView() {
        bt_banjuzhuzheng = (Button) findViewById(R.id.bt_banjuzhuzheng);
    }

    private class banZhengConnectionService implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取Ibinder对象，也就是Service的实例对象
            // java中多态性质的体现
            banzhengBinder = (IService) IService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(banZhengConn);
    }
}
