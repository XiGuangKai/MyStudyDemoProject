package com.myaddfragmentbycodedemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取屏幕管理者
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        // 获取屏幕的宽和高
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 如果高大于宽则为竖屏，如果高小于宽则为横屏
        if (height >= width){
            // 竖屏。android.R.id.content代表设备当前的窗体
            fragmentTransaction.replace(android.R.id.content,new VerticalFragment());
        }else {
            // 横屏
            fragmentTransaction.replace(android.R.id.content,new horizontalFragment());
        }
        fragmentTransaction.commit();

    }
}
