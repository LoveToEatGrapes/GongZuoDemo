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

public class DrawQuadToViewB extends View {


    private Path mPath = new Path();
    private static final String TAG = "TAG";

    private float startX,startY,endX,endY;

    public DrawQuadToViewB(Context context) {
        super(context);
    }

    public DrawQuadToViewB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(),event.getY());
                startX = event.getX();
                startY = event.getY();
            break;
            case MotionEvent.ACTION_MOVE:

                endX = event.getX();
                endY = event.getY();
                mPath.quadTo(startX,startY,endX,endY);
                startX = endX;
                startY = endY;
               // mPath.lineTo(event.getX(),event.getY());

                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(mPath,paint);

    }


    /**
     * 清除
     */
    public void reset(){
        mPath.reset();
        invalidate();
    }





}
