package com.example.myapplication.View;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 二次贝塞尔曲线
 */
public class DrawQuadToView extends View {

    private static final String TAG = "TAG";
    private int centerX,centerY;
    private int startX,startY;
    private int endX,endY;
    private int eventX,eventY;
    private Paint mPaint;

    public DrawQuadToView(Context context) {
        super(context);
        init();
    }


    public DrawQuadToView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG,"-------------onSizeChanged w:" + w + "=h=" + h  + "=oldw=" + oldw + "=oldh=" + oldh);
        centerX = w/2;
        centerY = h/2;
        startX = centerX - 250;
        startY = centerY;
        endX = centerX + 250;
        endY = centerY;
        eventX = centerX;
        eventY = centerY - 250;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画点
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(startX,startY,10,mPaint);
        mPaint.setColor(Color.DKGRAY);
        canvas.drawCircle(eventX,eventY,10,mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(endX,endY,10,mPaint);

        //连线
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
        canvas.drawLine(startX,startY,eventX,eventY,mPaint);
        canvas.drawLine(eventX,eventY,endX,endY,mPaint);

        //画贝塞尔曲线
        Path path = new Path();
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.STROKE);
        path.moveTo(startX,startY);
        path.quadTo(eventX,eventY,endX,endY);
        canvas.drawPath(path,mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                eventX = (int) event.getX();
                eventY = (int) event.getY();
                invalidate();
                break;
        }
        //return super.onTouchEvent(event);
        return true;
    }
}
