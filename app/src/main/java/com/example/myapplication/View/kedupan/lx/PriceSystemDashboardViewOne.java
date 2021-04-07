package com.example.myapplication.View.kedupan.lx;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class PriceSystemDashboardViewOne extends View {

    private int mRadius; // 扇形半径
    private Paint mPaint;
    private int mStartAngle = 180; // 起始角度
    private int mSweepAngle = 180; // 绘制角度
    private String[] mTexts;
    private Rect mRectText;
    private float mCenterX, mCenterY; // 圆心坐标
    private RectF mRectFArc; //圆弧所在的椭圆
    private int mLength1;
    private int mLength2;
    private int mRealTimeValue = 0; // 实时读数

    private int mStrokeWidth;

//    private RectF mRectF;
    private Path mPath;
    private RectF mRectFInnerArc;

    private int mPadding;

    private int mSection = 10;//值域（mMax-mMin）等分份数


    public PriceSystemDashboardViewOne(Context context) {
        this(context,null);
    }

    public PriceSystemDashboardViewOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PriceSystemDashboardViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStrokeWidth = dp2px(30);
        mLength1 = dp2px(8) + mStrokeWidth;
        mLength2 = mLength1 + dp2px(2);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.BUTT);

//        mRectF = new RectF();
        mPath = new Path();
        mRectFArc = new RectF();
        mRectFInnerArc = new RectF();
        mRectText = new Rect();

        mTexts = new String[11];
        for (int i = 0; i < mTexts.length; i++){
            mTexts[i] = String.valueOf(i);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPadding = Math.max(Math.max(getPaddingLeft(), getPaddingTop()),
                            Math.max(getPaddingRight(), getPaddingBottom()));
        setPadding(mPadding,mPadding,mPadding,mPadding);

        int width = resolveSize(dp2px(200), widthMeasureSpec);
        //mRadius = (width - mPadding * 2 - mStrokeWidth * 2) /2;
        mRadius = (width - mPadding * 2 ) /2;
        mPaint.setTextSize(sp2px(16));
//        mPaint.getTextBounds("2",0,"0".length(), mRectText);
        //文字的高度
        int height1 = (int) (mRadius + mRectText.height());
        //由起始角度确定的高度
        float[] point1 = getCoordinatePoint(mRadius, mStartAngle);
        //由结束角度确定的高度
        float[] point2 = getCoordinatePoint(mRadius, mStartAngle + mSweepAngle);
        //取最大值
        int max = (int) Math.max(height1,
                Math.max(point1[1] + mRadius + mStrokeWidth * 2, point2[1] + mRadius + mStrokeWidth * 2));

        setMeasuredDimension(width, max + getPaddingTop() + getPaddingBottom());

        mCenterX = mCenterY = getMeasuredWidth() / 2f;

        mPaint.setTextSize(sp2px(10));
        mPaint.getTextBounds("0", 0, "0".length(), mRectText);

        //设置0到10的位置
        mRectFInnerArc.set(getPaddingLeft() + mLength2 + mRectText.height(),
                getPaddingTop() + mLength2 + mRectText.height(),
                getMeasuredWidth() - getPaddingRight() - mLength2 - mRectText.height(),
                getMeasuredWidth() - getPaddingBottom() - mLength2 - mRectText.height() - sp2px(10)
                );

        //设置圆弧的四个边
        mRectFArc.set(
                getPaddingLeft() + mStrokeWidth + mLength2 + mRectText.height(),
                getPaddingTop() + mStrokeWidth + mLength2 + mRectText.height(),
                getMeasuredWidth() - getPaddingRight() - mStrokeWidth - (mLength2 + mRectText.height()),
                getMeasuredWidth() - getPaddingBottom() - mStrokeWidth - (mLength2 + mRectText.height())
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画圆弧
         */
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_F0F0F0));
        canvas.drawArc(mRectFArc,mStartAngle, mSweepAngle, false ,mPaint);

        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_14b7cc));
        canvas.drawArc(mRectFArc, mStartAngle, calculateRelativeAngleWithValue(mRealTimeValue), false, mPaint);

        mPaint.setTextSize(sp2px(10));
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_999999));
        for (int i = 0; i < mTexts.length; i++) {
            mPaint.getTextBounds(mTexts[i], 0, mTexts[i].length(), mRectText);
            float θ = (float) (180 * mRectText.width() / 2 / (Math.PI * (mRadius - mLength2 - mRectText.height())));

            mPath.reset();
            mPath.addArc(mRectFInnerArc, mStartAngle + i * (mSweepAngle / mSection) - θ,
                    mSweepAngle);
            canvas.drawTextOnPath(mTexts[i], mPath, 0 , 0 ,mPaint);

        }

        float a = calculateRelativeAngleWithValue(mRealTimeValue);
        float b = mStrokeWidth / 2f;
        canvas.save();
        b = mStrokeWidth / 2f;
        canvas.rotate(a - b, mCenterX, mCenterY);

        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_14b7cc));
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.reset();


        mPath.moveTo(mCenterX, mPadding + mLength2 + dp2px(30) + mStrokeWidth + mRectText.height());
        mPath.rLineTo(-dp2px(4), dp2px(10));
        mPath.rLineTo(dp2px(8), 0);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

    }



    public void setRealTimeValue(int realTimeValue) {
        if (mRealTimeValue == realTimeValue || realTimeValue < 0 || realTimeValue > 10) {
            return;
        }
        mRealTimeValue = realTimeValue;
        postInvalidate();
    }

    private float calculateRelativeAngleWithValue(int value){
        return mSweepAngle * value * 1f/ 10;
    }

    public float[] getCoordinatePoint(int radius, float angle){
        float[] point = new float[2];

        double arcAngle = Math.toRadians(angle);//将角度转换为弧度
        if (angle < 90){
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        }else if (angle == 90){
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        }else if (angle > 90 && angle < 180){
            arcAngle = Math.PI * (180 - angle) /180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        }else if (angle == 180){
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        }else if (angle > 180 && angle < 270){
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }else if (angle == 270){
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        }else {
            arcAngle = Math.PI * (360 -angle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }

        return point;
    }

    private int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

}
