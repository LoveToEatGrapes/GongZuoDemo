package com.example.myapplication.View.kedupan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DegreeView extends View {
    private Paint mPaint;
    private static final float RADIUS = Utils.px2dp(105);
    private static final float SWEEPANGLE = 300;
    private Path dash;
    private PathMeasure pathMeasure;
    private static final float INDICATOR = Utils.px2dp(100);
    private float currentAngle = 210f;
    private RectF rectF;
    private RectF rectF2;//内部刻度尺
    private float max = 10000;//最大值
    private int progress;//进度值
    private Paint mTextPaint; //中心文字画笔
    private Paint mTopTextPaint; //顶部文字画笔
    private String drawText = "";//中间的文字
    private String topText = "";//顶部的文字

    public DegreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();//初始化Paint
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//画线模式
        mPaint.setStrokeWidth(Utils.px2dp(2));//线宽度
        mPaint.setColor(Color.BLACK);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(56);
        mTextPaint.setColor(Color.WHITE);

        mTopTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTopTextPaint.setTextSize(18);
        mTopTextPaint.setColor(Color.WHITE);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画弧
        drawCircle(canvas);

        //绘制刻度
        drawDegree(canvas);

        //绘制指针
        // drawIndicator(canvas);

        //绘制进度
        drawProgress(canvas);

        //绘制中心文字
        drawText(canvas);

        //绘制顶部文字
        drawTopText(canvas);

    }

    private void drawText(Canvas canvas) {
        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        int baseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(drawText) / 2);

        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        int baseY = (int) ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));

        // 居中画一个文字
        canvas.drawText(drawText, baseX, baseY, mTextPaint);

    }

    private void drawTopText(Canvas canvas) {
        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        int baseX = (int) (canvas.getWidth() / 2 - mTopTextPaint.measureText(topText) / 2);

        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        int baseY = (int) ((canvas.getHeight() / 2) - RADIUS - ((mTopTextPaint.descent() + mTopTextPaint.ascent()) / 2) + Utils.px2dp(23));

        // 居中画一个文字
        canvas.drawText(topText, baseX, baseY, mTopTextPaint);


    }


    private void drawProgress(Canvas canvas) {
        int[] colors = {Color.parseColor("#ffff00"), Color.parseColor("#ff6d00")};
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//画线模式
        mPaint.setStrokeWidth(Utils.px2dp(2));//线宽度
        //circleWidth 圆的直径 取中心点
        SweepGradient sweepGradient = new SweepGradient(this.getWidth() / 2, this.getHeight() / 2, colors, null);
        //旋转 不然是从0度开始渐变
        Matrix matrix = new Matrix();
        matrix.setRotate(90, this.getWidth() / 2, this.getHeight() / 2);
        sweepGradient.setLocalMatrix(matrix);
        mPaint.setShader(sweepGradient);


        rectF2 = new RectF(getWidth() / 2 - RADIUS + Utils.px2dp(8), getHeight() / 2 - RADIUS + Utils.px2dp(8),
                getWidth() / 2 + RADIUS - Utils.px2dp(8), getHeight() / 2 + RADIUS - Utils.px2dp(8));
        //刻度的路径
        dash = new Path();
        //Path.Direction.CW顺时针方向 同时顺时针切线方向为X轴正向
        // dash.addRect(0, 0, Utils.px2dp(2), Utils.px2dp(10), Path.Direction.CW);
        dash.addRoundRect(new RectF(0, 0, Utils.px2dp(2), Utils.px2dp(9)), 3, 4, Path.Direction.CW);
        //弧线长度的路径
        Path length = new Path();
        length.addArc(rectF2, 90 + (360 - SWEEPANGLE) / 2, SWEEPANGLE);
        //测量弧线长度
        pathMeasure = new PathMeasure(length, false);
        //这里(pathMeasure.getLength()-mPaint.getStrokeWidth())/20 弧线长度之所以减去Paint的宽度跟我第一种方式去掉宽度是一个意思
        mPaint.setPathEffect(new PathDashPathEffect(dash,
                (pathMeasure.getLength() - mPaint.getStrokeWidth()) / 60, 0, PathDashPathEffect.Style.ROTATE));
        canvas.drawArc(rectF2, 90 + (360 - SWEEPANGLE) / 2, progress, false, mPaint);

        mPaint.setPathEffect(null);
        mPaint.setShader(null);

        mPaint.setColor(Color.argb(126, 255, 255, 255));
        //绘制中间长一点的刻度
        if (progress >= 150) {
            mPaint.setColor(Color.parseColor("#ffb600"));
        }
        mPaint.setStrokeWidth(Utils.px2dp(1));//线宽度
        RectF centerF = new RectF(getWidth() / 2 - Utils.px2dp(0.5f), getHeight() / 2 - RADIUS + Utils.px2dp(5),
                getWidth() / 2 + Utils.px2dp(0.5f), getHeight() / 2 - RADIUS + Utils.px2dp(16));
        canvas.drawRoundRect(centerF, 1, 1, mPaint);


        mPaint.setStrokeWidth(Utils.px2dp(2));//线宽度
    }


    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.argb(179, 255, 255, 255));
        rectF = new RectF(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, mPaint);
    }



    private void drawDegree(Canvas canvas) {
        mPaint.setColor(Color.argb(126, 255, 255, 255));
        rectF2 = new RectF(getWidth() / 2 - RADIUS + Utils.px2dp(8), getHeight() / 2 - RADIUS + Utils.px2dp(8),
                getWidth() / 2 + RADIUS - Utils.px2dp(8), getHeight() / 2 + RADIUS - Utils.px2dp(8));

        //刻度的路径
        dash = new Path();
        //Path.Direction.CW顺时针方向 同时顺时针切线方向为X轴正向
        // dash.addRect(0, 0, Utils.px2dp(2), Utils.px2dp(10), Path.Direction.CW);
        dash.addRoundRect(new RectF(0, 0, Utils.px2dp(2), Utils.px2dp(9)), 3, 4, Path.Direction.CW);
        //弧线长度的路径
        Path length = new Path();
        length.addArc(rectF2, 90 + (360 - SWEEPANGLE) / 2, SWEEPANGLE);
        //测量弧线长度
        pathMeasure = new PathMeasure(length, false);
        //这里(pathMeasure.getLength()-mPaint.getStrokeWidth())/20 弧线长度之所以减去Paint的宽度跟我第一种方式去掉宽度是一个意思
        mPaint.setPathEffect(new PathDashPathEffect(dash,
                (pathMeasure.getLength() - mPaint.getStrokeWidth()) / 60, 0, PathDashPathEffect.Style.ROTATE));
        //分为两次画 不画中间的线
        canvas.drawArc(rectF2, 90 + (360 - SWEEPANGLE) / 2, 149, false, mPaint);
        canvas.drawArc(rectF2, -85.5f, 149, false, mPaint);
        mPaint.setPathEffect(null);
    }

    private void drawIndicator(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawLine(0, 0,
                (float) Math.cos(Math.toRadians(currentAngle)) * INDICATOR,
                (float) Math.sin(Math.toRadians(currentAngle)) * INDICATOR,
                mPaint);
        canvas.translate(getWidth() / 2, getHeight() / 2);
    }

    public void setValue(int value) {
        this.progress = (int) (300.0 * (value / max));
        drawText = value + " mA";
        topText =  (max / 2) + "";
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
        this.progress = 0;
        topText = (max / 2) + "";
        invalidate();
    }
}
