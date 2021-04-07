package com.example.myapplication.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

public class DrawQuadToWaveView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength = 400;//波浪的长度
    private int originY = 400;//波浪在Y轴的方向位置
    private int range = 100;//波浪幅度
    private int dx;

    public DrawQuadToWaveView(Context context) {
        super(context);
    }

    public DrawQuadToWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int halfWaveLen = mItemWaveLength/2;//半个波长，即一个贝塞尔曲线长度
        mPath.moveTo(-mItemWaveLength + dx, originY);
        //每一次for循环添加一个波浪长度到path中，根据view的宽度来计算一个可以添加多少个波浪长度
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength){
            mPath.rQuadTo(halfWaveLen/2 , -range, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen/2, range,halfWaveLen,0);
        }
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();//封闭path路径
        canvas.drawPath(mPath,mPaint);

    }

    public void startAnim(){
        //根据一个动画不断得到0-mItemWaveLength的值dx，通过dx的增加不断去改变波浪开始的位置，dx的变化范围刚好是一个波浪的长度
        //所以可以形成一个完整的波浪动画，假如dx最大小于mItemWaveLength的话， 就会不会画出一个完整的波浪形状
        final ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                dx = (int)animator.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

}
