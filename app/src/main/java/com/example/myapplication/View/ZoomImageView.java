package com.example.myapplication.View;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;

//https://blog.csdn.net/lmj623565791/article/details/39474553

public class ZoomImageView extends androidx.appcompat.widget.AppCompatImageView implements OnScaleGestureListener,
        OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener{//
    //private static final String TAG = ZoomImageView.class.getSimpleName();
    private static final String TAG = "TTT";

    /**
     * 自定义放大最大值
     */
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

    /**
     * 上次的手指触摸点的个数
     */
    private int lastPointerCount;

    /**
     * 上次一的触碰点
     */
    private float mLastX,mLastY;

    /**
     *
     */
    private boolean isCanDrag;

    /**
     * 最小推动距离（大于5表示推动）
     */
    private int mTouchSlop = 5;

    private boolean isCheckTopAndBottom , isCheckLeftAndRight;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        // 获得当前的缩放比例
        float scaleX = getScaleX();
        //将前一个缩放事件的缩放因子返回到当前事件。(自测大于1是放大，小于1是缩小)
        float scaleFactor = detector.getScaleFactor();

        //Log.e("TTT","===============当前缩放比例：" +scaleX + " ==scaleFactor: " + scaleFactor + "==initScale:" + initScale);
        if (getDrawable() == null)
            return true;

        /**
         * 缩放的范围控制
         * SCALE_MAX 自定义最大值
         * initScale 自定义最小值
         * scaleFactor 自测大于1是放大，小于1是缩小
         */
        if ((scaleX < SCALE_MAX && scaleFactor > 1.0f)
                || (scaleX > initScale && scaleFactor < 1.0f)) {
            /**
             * 最大值最小值判断
             */
            if (scaleFactor * scaleX < initScale) {
                Log.i("TTT","=========ddddd:" + scaleFactor);
                scaleFactor = initScale / scaleX;
            }
            if (scaleFactor * scaleX > SCALE_MAX) {
                Log.i("TTT","=========fffff:" + scaleFactor);
                scaleFactor = SCALE_MAX / scaleX;
            }
            /**
             * 设置缩放比例
             */
//            Log.e("TAG","====" +scaleFactor + "==" + scaleFactor + "==" +getWidth() / 2 + "==" +getHeight() / 2);

            //postScale 第一个参数是X轴的缩放大小，第二个参数是Y轴的缩放大小，第三四个参数是缩放中心点
            //缩放可以无限放大或缩小
            mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2,
                    getHeight() / 2);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        mScaleGestureDetector.onTouchEvent(event);

        float x = 0, y = 0;
        // 拿到触摸点的个数
        final int pointerCount = event.getPointerCount();
        // 得到多个触摸点的x与y均值
        for (int i = 0; i < pointerCount; i++)
        {
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;

        /**
         * 每当触摸点发生变化时，重置mLasX , mLastY
         */
        if (pointerCount != lastPointerCount)
        {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        lastPointerCount = pointerCount;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                //按下时的位置与移动后的位置差值
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag)
                {
                    isCanDrag = isCanDrag(dx, dy);
                }

                Log.e(TAG, "ACTION_MOVE  dx:" + dx + "== dy:" + dy  + "==isCanDrag:" + isCanDrag);

                if (isCanDrag)
                {
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null)
                    {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        // 如果宽度小于屏幕宽度，则禁止左右移动
                        if (rectF.width() <= getWidth())
                        {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        // 如果高度小于屏幕高度，则禁止上下移动
                        if (rectF.height() <= getHeight())
                        {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        //checkMatrixBounds();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "ACTION_UP");
                lastPointerCount = 0;
                break;
        }


        return true;
    }

    /**
     * 在缩放时，进行图片显示范围的控制
     * (防止在放大后进行移动操作，在缩小时，图片显示位置调整)
     */
    private void checkBorderAndCenterWhenScale()
    {
        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width)
        {
            if (rect.left > 0)
            {
                deltaX = -rect.left;
            }
            if (rect.right < width)
            {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height)
        {
            if (rect.top > 0)
            {
                deltaY = -rect.top;
            }
            if (rect.bottom < height)
            {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width)
        {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height)
        {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.e(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);

        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            //mapRect 意思就是将Matrix 的值映射到RecF中，
            matrix.mapRect(rect);
        }
        return rect;
    }



    /**
     * 移动时，进行边界判断，主要判断宽或高大于屏幕的
     * （主要是图片的上下左右四个边界与手机屏幕的四个边紧密相连）
     */
    private void checkMatrixBounds()
    {
        RectF rect = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom)
        {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom)
        {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight)
        {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight)
        {
            deltaX = viewWidth - rect.right;
        }
        Log.e("TTT","======移动时，进行边界判断，主要判断宽或高大于屏幕的 deltaX：" + deltaX + "==deltaY:" + deltaY);
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 是否是推动行为
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }
        /**
         * 获得当前的缩放比例
         *
         * @return
         */
    public final float getScaleX() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScaleY() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_Y];
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        Log.e("TAG","======12==========onAttachedToWindow==");
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        Log.e("TAG","======123==========onDetachedFromWindow==");
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = getDrawable();
            if (d == null)
                return;

            //拿到屏幕的宽高
            int width = getWidth();
            int height = getHeight();
            // 拿到图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            Log.e(TAG, dw + " , " + dh + "==" + width + "," + height);

            float scale = 1.0f;
            // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width) {
                scale = height * 1.0f / dh;
            }
            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if (dw > width && dh > height) {
//                scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
                scale =  width * 1.0f / dw;
            }

            Log.e("TTT","==================scale:"+ scale + "===dw * 1.0f / width:" + (dw * 1.0f / width)
            + "== dh * 1.0f / height:" +  (dh * 1.0f / height) + "= width * 1.0f / dw=" + ( width * 1.0f / dw));

            initScale = scale;
            // 图片移动至屏幕中心
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            mScaleMatrix.postScale(scale, scale, width / 2, height / 2);

            setImageMatrix(mScaleMatrix);
            once = false;
        }

    }

}