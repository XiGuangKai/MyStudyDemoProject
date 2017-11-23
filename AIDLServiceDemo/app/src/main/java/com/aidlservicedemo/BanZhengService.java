package com.aidlservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

public class BanZhengService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new BanZhengBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BanZhengService","onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BanZhengService","onDestroy");
    }

    public void xiSangNa(){
        Log.d("BanZhengService","洗桑拿");
    }

    public void duanChaShui(){
        Log.d("BanZhengService","端茶倒水");
    }

    public void banJuZhuZheng(){
        Log.d("BanZhengService","居住证办理好了");
    }

    private class BanZhengBinder extends IService.Stub {

        public void callDuanChaShui(){
            duanChaShui();
        }

        public void callXiSangNa(){
            xiSangNa();
        }

        public void callBanJuZhuZheng() {
            banJuZhuZheng();
        }
    }
}
