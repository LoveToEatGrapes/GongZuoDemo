package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.View.DrawQuadToViewB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BesselTwoActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawQuadToViewB idDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bessel_two);

        idDraw = findViewById(R.id.id_draw);

    }

    @Override
    public void onClick(View view) {

    }

    public void cc(View view){
        idDraw.reset();
    }

    public void save(View view){
        Log.e("TAG","-------1234567890--：");

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                saveImg();
            }
        };
        handler.post(runnable);

    }

    /**
     * 保存成图片
     */
    public void saveImg(){
        idDraw.setDrawingCacheEnabled(true);
        idDraw.setDrawingCacheBackgroundColor(Color.WHITE);
        idDraw.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        //创建bitmap
        Bitmap bb = createBitmap(idDraw);

        //添加水印
        Bitmap bitmap = addWatermark(bb,"incopat");

        FileInputStream fileInputStream;

        FileOutputStream fos;
        try {
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                File sdRoot = Environment.getExternalStorageDirectory();
                Log.e("TAG","------------我是路径啊：" + sdRoot.getPath());
                File file = new File(sdRoot, "test.PNG");
                fos = new FileOutputStream(file);
            } else
                throw new Exception("创建文件失败!");

            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        idDraw.destroyDrawingCache();
    }

    /**
     * 添加水印
     */
    private Bitmap addWatermark(Bitmap bitmap,String str) {

        //获取宽高
         int w = bitmap.getWidth();
         int h = bitmap.getHeight();

        //获取bitmap
        Bitmap bitmapWatermark = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //声明画布
        Canvas canvas = new Canvas(bitmapWatermark);
        //创建画笔
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(15);

        canvas.drawBitmap(bitmap,0,0,paint);

        //添加水印
        canvas.drawText(str,w/2,h/2,paint);

        //保存、关闭
        canvas.save();
        canvas.restore();

        return bitmapWatermark;
    }

    /**
     * 创建bitmap
     */
    private Bitmap createBitmap(View view) {
        int w = view.getWidth();
        int h = view.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.YELLOW);

        view.layout(0,0,w,h);
        view.draw(canvas);

        return bitmap;
    }


}