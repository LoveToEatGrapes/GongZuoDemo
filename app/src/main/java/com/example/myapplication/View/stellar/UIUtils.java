package com.example.myapplication.View.stellar;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.example.myapplication.MyApplication;


/**
 * 专门提供为处理一些UI相关的问题而创建的工具类
 */
public class UIUtils{

    public static Context getContext(){
        return MyApplication.context;
    }

    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    public static View getXmlView(int viewId){
        return View.inflate(getContext(), viewId, null);
    }

    public static String[] getStringArray(int arrayId){
        return getContext().getResources().getStringArray(arrayId);
    }

    //将dp转化为px
    public static int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;

        return (int)(density * dp + 0.5);//四舍五入
    }

    //将px转化为dp
    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;

        return (int)(px / density + 0.5);//四舍五入
    }




}
