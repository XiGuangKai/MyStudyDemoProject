package com.listviewtestdemo;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //查找对应的空间
        listView = (ListView) findViewById(R.id.lv);

        //设置关联
        listView.setAdapter(new MyAdapter());


    }

    //创建适配器
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 7;//注意次数
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            if (view == null) {
                textView = new TextView(getApplicationContext());
                Log.d(TAG, "New Item " + i);
            } else {
                textView = (TextView) view;
                Log.d(TAG, "Old Item " + i);
            }
            textView.setText("Item " + i);
            Log.d(TAG, "Item " + i);
            return textView;
        }
    }
}
