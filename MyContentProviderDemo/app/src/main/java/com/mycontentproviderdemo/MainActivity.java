package com.mycontentproviderdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建数据库
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(this);
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();

        //遍历数据库数据
        Cursor cursor = db.query("phoneNumber", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            String phone = cursor.getString(2);

            Log.d("MainActivity","name : "+name+"  phone : " + phone);
        }
    }
}
