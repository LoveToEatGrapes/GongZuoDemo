package com.example.myapplication.xiangji;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

public class CustomCamera1 {

    public volatile static CustomCamera1 customCamera1;

    public static CustomCamera1 newCustomCamera1(){
        if (customCamera1 == null){
            synchronized (CustomCamera1.class){
                if (customCamera1 == null){
                    customCamera1 = new CustomCamera1();
                }
            }
        }
        return customCamera1;
    }


    /**
     * 判断是否存在相机，存在true 不存在false
     */
    public boolean isCamera(Context context){
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 打开相机 （默认是后置相机）
     */
    public void openCamera(){
        Camera.open();
    }


    /**
     * 打开前置相机
     */

    /**
     * 打开后置相机
     */





}
