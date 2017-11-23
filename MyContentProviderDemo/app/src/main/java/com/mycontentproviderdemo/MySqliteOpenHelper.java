package com.mycontentproviderdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    public MySqliteOpenHelper(Context context) {
        super(context, "ContentProviderTest.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表phoneNumber
        db.execSQL("create table phoneNumber(_id integer primary key " +
                "autoincrement,name varchar(20),phone varchar(20))");

        //插入两条数据
        ContentValues values = new ContentValues();
        values.put("name","张三");
        values.put("phone",18812132);
        db.insert("phoneNumber",null,values);
        values.clear();

        values.put("name","李四");
        values.put("phone",17710033);
        db.insert("phoneNumber",null,values);
        values.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
