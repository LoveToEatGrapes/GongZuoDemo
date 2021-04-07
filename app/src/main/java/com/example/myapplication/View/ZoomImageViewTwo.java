package com.example.myapplication.View;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class ZoomImageViewTwo extends ImageView implements
        View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener
    , ViewTreeObserver.OnGlobalLayoutListener
{

    private static final String TAG = ZoomImageViewTwo.class.getSimpleName();

    public static final float SCALE_MAX = 4.0f;
    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    private boolean once = true;

    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector = null;

    private final Matrix mScaleMatrix = new Matrix();

    public ZoomImageViewTwo(Context context) {
        this(context,null);
    }

    public ZoomImageViewTwo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }


    //检测缩放的手势 - 是否允许缩放
    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        mScaleMatrix.setValues(matrixValues);
        float scaleValue = matrixValues[Matrix.MSCALE_X];

        if (getDrawable() == null){
            return true;
        }

        if ((scaleValue < SCALE_MAX && scaleFactor > 1.0f)
        	|| (scaleValue > initScale && scaleFactor < 1.0f)) {
            /**
             * 最大值最小值判断
             */
            if (scaleFactor * scaleValue < initScale) {
                scaleFactor = initScale / scaleValue;
            }
            if (scaleFactor * scaleValue > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scaleValue;
            }
            /**
             * 设置缩放比例
             */
            mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2,
                    getHeight() / 2);
            setImageMatrix(mScaleMatrix);
        }
         return true;
    }

    //检测缩放的手势 - 缩放开始
    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    //检测缩放的手势 - 缩放结束
    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mScaleGestureDetector.onTouchEvent(motionEvent);
    }


    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (once)
        {
            Drawable d = getDrawable();
            if (d == null)
                return;
            Log.e(TAG, d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
            int width = getWidth();
            int height = getHeight();
            // 拿到图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height)
            {
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width)
            {
                scale = height * 1.0f / dh;
            }
            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if (dw > width && dh > height)
            {
                scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
            }
            initScale = scale;
            // 图片移动至屏幕中心
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            mScaleMatrix
                    .postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mScaleMatrix);
            once = false;
        }

    }
}
