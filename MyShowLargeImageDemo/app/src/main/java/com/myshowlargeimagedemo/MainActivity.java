package com.myshowlargeimagedemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // [1]找到关心的控件
        imageView = (ImageView) findViewById(R.id.iv_large);


        if (Build.VERSION.SDK_INT >= 23) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }else{
                showPicture();
            }
        }else{
            showPicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showPicture();
            }else {
                Log.d("MainActivity","Permission Failed");
            }
        }
    }

    private void showPicture() {
        // 获取WindowManager对象
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int widowWidth = wm.getDefaultDisplay().getWidth();//屏幕宽度
        int windowHeight = wm.getDefaultDisplay().getHeight();//屏幕高度

        // 创建BitmapFactory.Options()的对象，其实就是获取位图工厂的配置参数对象
        BitmapFactory.Options options = new BitmapFactory.Options();

        // 这个作用就是在BitmapFactory.decodeFile加载图片的时候，
        // 仅仅获取图片的参数，而不是真正的分配内存空间加载
        options.inJustDecodeBounds = true;

        // 获取图片的宽高参数
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        // 缩放参数的确定
        int scale = (imageWidth / widowWidth) >= (imageHeight / windowHeight) ? (imageWidth / widowWidth) : (imageHeight / windowHeight);

        Log.d(""," scale = " + scale);

        if (scale > 0){
            // 按照缩放比进行加载
            options.inSampleSize = scale;
        }

        // 将inJustDecodeBounds值还原后，BitmapFactory将会实际加载图片
        options.inJustDecodeBounds = false;

        // [2]获取BitmapFactory
        Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/32002560.png");

        // [3]显示图片
        imageView.setImageBitmap(bitmap);
    }


}
