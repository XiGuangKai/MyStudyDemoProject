package com.mytest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //创建隐式Intent
        Intent intent = new Intent();

        //为隐式Intent添加过滤Action，Data和Category的字段，
        // 不是说必须这三个字段同时有，这个设置那些是根据
        // 清单文件中Main3Activity的某一个intent-filter确定的
        intent.setAction("com.mytest.MyIntentAction2");
        intent.addCategory("android.intent.category.DEFAULT");

        //后边的110是随便加的，暂时没有什么具体的意思，这就是他的一个用法
        intent.setData(Uri.parse("myIntent2:" + 110));

        //启动Main3Activity
        startActivity(intent);
    }
}
