package com.mycontentresover;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ContentResolver";
    private ContentObserver contentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 创建uri
        Uri uri = Uri.parse("content://com.mycontentproviderdemo.provider/query");

        // 通过getContentResolver()获取到ContentResover。然后调用对应的方法，并填充上对应的uri。
        // 其他几个方法就不在演示了，都是一样的流程。
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            String phone = cursor.getString(2);

            Log.d(TAG,name + ":" + phone);
        }

        //使用观察者对象观察content://com.mycontentproviderdemo.provider 的变化（增、删、改）
        Uri ObserverUri = Uri.parse("content://com.mycontentproviderdemo.provider");
        contentObserver = new MyContentObserver(new Handler());
        getContentResolver().registerContentObserver(ObserverUri,true, contentObserver);

        Uri insertUri = Uri.parse("content://com.mycontentproviderdemo.provider/1");
        ContentValues values = new ContentValues();
        values.put("name","李柳");
        values.put("phone",109878);
        getContentResolver().insert(insertUri,values);
        values.clear();
    }

    public class MyContentObserver extends ContentObserver {
        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG,"database changed ");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消注册观察者
        getContentResolver().unregisterContentObserver(contentObserver);
    }
}
