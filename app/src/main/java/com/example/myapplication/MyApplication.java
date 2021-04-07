package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;

import java.io.File;

public class MyApplication extends Application {



    public static int screenWidth;
    public static int screenHeight;

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DisplayMetrics mDisplayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();
        screenWidth = mDisplayMetrics.widthPixels;
        screenHeight = mDisplayMetrics.heightPixels;
        initSmallVideo();
    }

    public static void initSmallVideo() {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 初始化拍摄，遇到问题可选择开启此标记，以方便生成日志
        JianXiCamera.initialize(false,null);
    }

    public static Context getContext(){
        return context;
    }
}
