package com.mycopyimagedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivsrc;
    private ImageView ivcopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 找到对应的控件
        ivsrc = (ImageView) findViewById(R.id.iv_src);
        ivcopy = (ImageView) findViewById(R.id.iv_copy);

        // [1]显示原图
        // 获取到原图的Bitmap
        Bitmap srcbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);

        // 显示原图，其实这一步没有啥意义，就是为了给下边的操作做一个对比。
        ivsrc.setImageBitmap(srcbitmap);

        // [2]复制图片
        // 创建一个和原图属性，大小都一样的副本
        // 但是现在图片还没有画上去，相当于一张和原图属性配置一样的白纸
        Bitmap copyBitmap = Bitmap.createBitmap(srcbitmap.getWidth(), srcbitmap.getHeight(), srcbitmap.getConfig());

        // 要做图就需要画笔和画布
        Paint paint = new Paint();
        Canvas canvas = new Canvas(copyBitmap);//相当于把报纸铺到了画布上

        // 设置镜面效果，为画图的一个参数
        // 不加效果的话，直接new一个Matrix()对象即可
        Matrix matrix = new Matrix();
        matrix.setScale(-1.0f,1);
        matrix.postTranslate(srcbitmap.getWidth(),0);

        //开始作画，按照原图进行画图,并且添加刚才我们设置的镜面效果
        canvas.drawBitmap(srcbitmap,matrix,paint);

        // 显示拷贝的，并且加了效果的图片
        ivcopy.setImageBitmap(copyBitmap);
    }
}
