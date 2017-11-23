package com.mytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建显示Intent
        Intent intent = new Intent(getApplicationContext(),com.mytest.Main2Activity.class);

        //启动Activity
        startActivity(intent);
    }
}
