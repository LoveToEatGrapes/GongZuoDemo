package com.example.myapplication.camare;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import androidx.annotation.NonNull;

import com.example.myapplication.MyApplication;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import static com.mabeijianxi.smallvideorecord2.DeviceUtils.getScreenHeight;
import static com.mabeijianxi.smallvideorecord2.DeviceUtils.getScreenWidth;

/**
 * description:
 * Created by aserbao on 2018/5/15.
 */


public class CameraView extends SurfaceView implements  SurfaceHolder.Callback
        {

    /**相机实体*/
    private Camera mCamera;
    private int cameraId = 1;

    private int textureID;
    public SurfaceHolder mSurfaceHolder;
    private Context context;

    public CameraView(Context context) {
        this(context,null);
        this.context = context;
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    private void init() {

        setCameraDistance(100);//相机距离
        textureID = createTextureID();
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /**创建显示的texture*/
    private int createTextureID() {
        int[] texture = new int[1];
        GLES20.glGenTextures(1, texture,0);//第一个参数表示创建几个纹理对象，并将创建好的纹理对象放置到第二个参数中去
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        return texture[0];
    }

    private void open(int cameraId){

    }

//    @Override
//    public boolean onTouch(View view, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_UP:
//                float sRawX = event.getRawX();
//                float sRawY = event.getRawY();
//                float rawY = sRawY * MyApplication.screenWidth / MyApplication.screenHeight;
//                float temp = sRawX;
//                float rawX = rawY;
//                rawY = (MyApplication.screenWidth - temp) * MyApplication.screenHeight / MyApplication.screenWidth;
//
//                Point point = new Point((int) rawX, (int) rawY);
//                onFocus(point, new Camera.AutoFocusCallback() {
//                    @Override
//                    public void onAutoFocus(boolean success, Camera camera) {
//                        if (success) {
//                            touch.Listener("success");
//                        } else {
//                            touch.Listener("FocusFailed");
//                        }
//                    }
//                });
//                touchStartFocus.startFocus(new Point((int) sRawX, (int) sRawY));
//        }
//        return true;
//    }

//    public interface onTouch{
//        void Listener(String type);
//    }
//    private onTouch touch;
//    public void setOnTouch(onTouch touch){
//        this.touch = touch;
//    }
//
//    public interface onTouchStartFocus{
//        void startFocus(Point type);
//    }
//    private onTouchStartFocus touchStartFocus;
//    public void setOnTouchStartFocus(onTouchStartFocus touchStartFocus){
//        this.touchStartFocus = touchStartFocus;
//    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        try {
            mCamera = null;
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(surfaceHolder);
            Camera.Parameters parameters = mCamera.getParameters();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            }
            List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的预览大小
            Camera.Size bestSize = getOptimalPreviewSize(sizeList,getScreenWidth(context),
                    getScreenHeight(context));
            parameters.setPreviewSize(bestSize.width, bestSize.height);//设置预览大小
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.e("TAG","相机打开报错了：" + e.getLocalizedMessage());
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    /**
     * 手动聚焦
     *
     * @param point 触屏坐标 必须传入转换后的坐标
     */
    public void onFocus(Point point, Camera.AutoFocusCallback callback) {
        Camera.Parameters parameters = mCamera.getParameters();
        boolean supportFocus=true;
        boolean supportMetering=true;
        //不支持设置自定义聚焦，则使用自动聚焦，返回
        if (parameters.getMaxNumFocusAreas() <= 0) {
            supportFocus=false;
        }
        if (parameters.getMaxNumMeteringAreas() <= 0){
            supportMetering=false;
        }
        List<Camera.Area> areas = new ArrayList<Camera.Area>();
        List<Camera.Area> areas1 = new ArrayList<Camera.Area>();
        //再次进行转换
        point.x= (int) (((float)point.x)/ MyApplication.screenWidth*2000-1000);
        point.y= (int) (((float)point.y)/MyApplication.screenHeight*2000-1000);

        int left = point.x - 300;
        int top = point.y - 300;
        int right = point.x + 300;
        int bottom = point.y + 300;
        left = left < -1000 ? -1000 : left;
        top = top < -1000 ? -1000 : top;
        right = right > 1000 ? 1000 : right;
        bottom = bottom > 1000 ? 1000 : bottom;
        areas.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
        areas1.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
        if(supportFocus){
            parameters.setFocusAreas(areas);
        }
        if(supportMetering){
            parameters.setMeteringAreas(areas1);
        }

        try {
            mCamera.setParameters(parameters);// 部分手机 会出Exception（红米）
            mCamera.autoFocus(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取最佳预览大小
     *
     * @param sizes 所有支持的预览大小
     * @param w     SurfaceView宽
     * @param h     SurfaceView高
     * @return
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }


    public int getCameraId() {
        return cameraId;
    }
}
