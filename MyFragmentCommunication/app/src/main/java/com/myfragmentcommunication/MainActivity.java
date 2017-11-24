package com.myfragmentcommunication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取Fragment的管理者
        FragmentManager fragmentManager = getFragmentManager();

        // 开启Fragment事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 添加Fragment
        fragmentTransaction.replace(R.id.ll1,new LeftFragment(),"ll1Fragment");
        fragmentTransaction.replace(R.id.ll2,new RightFragment(),"ll2Fragment");

        // 提交事务
        fragmentTransaction.commit();

    }
}
