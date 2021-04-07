package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.WLM.WlmqActivity;
import com.example.myapplication.activity.AnimationActivity;
import com.example.myapplication.activity.AonTouchActivity;
import com.example.myapplication.activity.BesselActivity;
import com.example.myapplication.activity.BesselTwoActivity;
import com.example.myapplication.activity.BesselWaveActivity;
import com.example.myapplication.activity.CamareMainActivity;
import com.example.myapplication.activity.CropActivity;
import com.example.myapplication.activity.EHorizontalSelectActivity;
import com.example.myapplication.activity.EmailActivity;
import com.example.myapplication.activity.ImageViewButtonActivity;
import com.example.myapplication.activity.KeDuPanActivity;
import com.example.myapplication.activity.ImageComPressActivity;
import com.example.myapplication.activity.PopWindowActivity;
import com.example.myapplication.activity.RandomTextLayoutActivity;
import com.example.myapplication.activity.RandomTextLayoutTwoActivity;
import com.example.myapplication.activity.ZoomPicturesActivity;
import com.example.myapplication.activity.activity_test.AActivity;
import com.example.myapplication.tag_viewpage.TabViewPageActivity;
import com.example.myapplication.uiView.Button_shuibowen_Activity;
import com.example.myapplication.uiView.Video_3_2Activity;
import com.example.myapplication.video.VideoActivity;
import com.example.myapplication.xiangji.OpenCameraActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent ;
    private final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent();
        findViewById(R.id.shui_bo_wen).setOnClickListener(this);
        findViewById(R.id.video_compress).setOnClickListener(this);
        findViewById(R.id.popwin).setOnClickListener(this);
        findViewById(R.id.kedupan).setOnClickListener(this);
        findViewById(R.id.shipin).setOnClickListener(this);
        findViewById(R.id.xiangji).setOnClickListener(this);
        findViewById(R.id.tabviewpage).setOnClickListener(this);
        findViewById(R.id.bt_huadong).setOnClickListener(this);
        findViewById(R.id.bt_img_com).setOnClickListener(this);
        findViewById(R.id.bt_fragment).setOnClickListener(this);
        findViewById(R.id.bt_crop).setOnClickListener(this);
        findViewById(R.id.bt_zoom).setOnClickListener(this);
        findViewById(R.id.bt_email).setOnClickListener(this);
        findViewById(R.id.bt_beisaner).setOnClickListener(this);
        findViewById(R.id.bt_beisaner_two).setOnClickListener(this);
        findViewById(R.id.bt_beisaner_three).setOnClickListener(this);
        findViewById(R.id.bt_camare).setOnClickListener(this);
        findViewById(R.id.bt_animation).setOnClickListener(this);
        findViewById(R.id.bt_onthounc).setOnClickListener(this);
        findViewById(R.id.词云2).setOnClickListener(this);
        findViewById(R.id.bt_散乱文字).setOnClickListener(this);
        findViewById(R.id.bt_kedu_two).setOnClickListener(this);
        findViewById(R.id.bt_点击图片报点).setOnClickListener(this);
        findViewById(R.id.bt_测试Activity).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shui_bo_wen:
                intent.setClass(MainActivity.this, Button_shuibowen_Activity.class);
                break;
            case R.id.video_compress:
                intent.setClass(MainActivity.this, Video_3_2Activity.class);
                break;
            case R.id.popwin:
                intent.setClass(MainActivity.this, PopWindowActivity.class);
                break;
            case R.id.kedupan:
                intent.setClass(MainActivity.this, WlmqActivity.class);

                break;
            case R.id.shipin:
                intent.setClass(MainActivity.this, VideoActivity.class);
                break;
            case R.id.xiangji:    //自定义相机
                intent.setClass(MainActivity.this, OpenCameraActivity.class);

                break;
            case R.id.tabviewpage:    //viewpage 与 fragment
                intent.setClass(MainActivity.this, TabViewPageActivity.class);

                break;
            case R.id.bt_huadong:    //类似于tablayout的滑动
                intent.setClass(MainActivity.this, EHorizontalSelectActivity.class);
                break;

            case R.id.bt_img_com:    //图片压缩
                intent.setClass(MainActivity.this, ImageComPressActivity.class);
                break;
            case R.id.bt_fragment: //fragment横竖屏切换
                intent.setClass(MainActivity.this, ImageComPressActivity.class);

                break;
            case R.id.bt_crop: //图片剪裁
                intent.setClass(MainActivity.this, CropActivity.class);

                break;
            case R.id.bt_zoom: //图片剪裁
                intent.setClass(MainActivity.this, ZoomPicturesActivity.class);

                break;
            case R.id.bt_email: //邮箱校验
                intent.setClass(MainActivity.this, EmailActivity.class);

                break;
            case R.id.bt_beisaner: //贝塞尔曲线
                intent.setClass(MainActivity.this, BesselActivity.class);

                break;
            case R.id.bt_beisaner_two: //贝塞尔曲线
                intent.setClass(MainActivity.this, BesselTwoActivity.class);

                break;
            case R.id.bt_beisaner_three: //贝塞尔曲线
                intent.setClass(MainActivity.this, BesselWaveActivity.class);

                break;
            case R.id.bt_camare: //自定义相机
                ActivityCompat.requestPermissions(this,BASIC_PERMISSIONS,123);

                return;
            case R.id.bt_animation: //加载动画
                intent.setClass(MainActivity.this, AnimationActivity.class);

                break;
            case R.id.bt_onthounc: //加载动画
                intent.setClass(MainActivity.this, AonTouchActivity.class);

                break;
            case R.id.bt_散乱文字: //散乱文字 ，词云
                intent.setClass(MainActivity.this, RandomTextLayoutActivity.class);

                break;
            case R.id.词云2: //散乱文字 ，词云
                intent.setClass(MainActivity.this, RandomTextLayoutTwoActivity.class);
                break;
            case R.id.bt_kedu_two: //表格
                intent.setClass(MainActivity.this, KeDuPanActivity.class);

                break;
            case R.id.bt_点击图片报点: //点击图片报点
                intent.setClass(MainActivity.this, ImageViewButtonActivity.class);

                break;
            case R.id.bt_测试Activity:
                intent.setClass(MainActivity.this, AActivity.class);

                break;
        }

        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(isCameraGranted()) {
            intent.setClass(MainActivity.this, CamareMainActivity.class);
            startActivity(intent);
        }else{
            ActivityCompat.requestPermissions(this,BASIC_PERMISSIONS,123);
        }
    }

    /**
     * @return true 照相机权限
     */
    public static boolean isCameraGranted(){
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission( MyApplication.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission( MyApplication.getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission( MyApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission( MyApplication.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                result = false;
            } else {
                result = true;
            }
        } else {
            result = true;
        }
        return result;
    }

}