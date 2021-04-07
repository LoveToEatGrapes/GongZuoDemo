package com.example.myapplication.camare.interfaces;

import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.view.SurfaceHolder;

/**
 * description:
 * Created by aserbao on 2018/5/15.
 */


public interface ICamera {
    /**open the camera*/
    void open(int cameraId);

    void setPreviewTexture(SurfaceTexture texture);

    void setPreviewDisplay(SurfaceHolder surfaceHolder);

    /**set the camera config*/
    void setConfig(Config config);

    void setOnPreviewFrameCallback(PreviewFrameCallback callback);

    void preview();

    Point getPreviewSize();

    Point getPictureSize();
    /**close the camera*/
    boolean close();

    class Config{
        public float rate=1.778f; //宽高比
        public int minPreviewWidth;
        public int minPictureWidth;
    }

    interface PreviewFrameCallback{
        void onPreviewFrame(byte[] bytes, int width, int height);
    }
}
