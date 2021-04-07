package com.example.myapplication.View.kedupan;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class PriceSystemDashboardView extends View {

    private int mRadius; // 扇形半径
    private int mStartAngle = 180; // 起始角度
    private int mSweepAngle = 180; // 绘制角度
    private int mMin = 0; // 最小值
    private int mMax = 10; // 最大值
    private int mSection = 10; // 值域（mMax-mMin）等分份数
    private int mRealTimeValue = mMin; // 实时读数
    private boolean isShowValue = true; // 是否显示实时读数
    private int mStrokeWidth; // 画笔宽度
    private int mLength1; // 长刻度的相对圆弧的长度
    private int mLength2; // 刻度读数顶部的相对圆弧的长度

    private int mPadding;
    private float mCenterX, mCenterY; // 圆心坐标
    private Paint mPaint;
    private RectF mRectFArc; //圆弧所在的椭圆
    private Path mPath;
    private RectF mRectFInnerArc;
    private Rect mRectText;
    private String[] mTexts;

    public PriceSystemDashboardView(Context context) {
        this(context,null);
    }

    public PriceSystemDashboardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PriceSystemDashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        mRectFArc = new RectF();
        mPath = new Path();
        mRectFInnerArc = new RectF();
        mRectText = new Rect();

        mTexts = new String[mSection + 1]; // 需要显示mSection + 1个刻度读数
        for (int i = 0; i < mTexts.length; i++) {
            int n = (mMax - mMin) / mSection;
            Log.e("TAG","=======hhhhhhh:" + String.valueOf(mMin + i * n) + "=n=" +n);
            mTexts[i] = String.valueOf(mMin + i * n);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mPadding = Math.max(
                Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom())
        );
        setPadding(mPadding, mPadding, mPadding, mPadding);

        int width = resolveSize(dp2px(200), widthMeasureSpec);
        mRadius = (width - mPadding * 2 - mStrokeWidth * 2) / 2;

        Log.e("TAG","====================onMeasure:" + width + "=mPadding:" + mPadding + "=mRadius：" + mRadius
        + "=width/2=" + width/2 + "=Math.PI=" + Math.PI);

        mPaint.setTextSize(sp2px(16));
        if (isShowValue) { // 显示实时读数，View高度增加字体高度3倍
            mPaint.getTextBounds("2", 0, "0".length(), mRectText);
        } else {
            mPaint.getTextBounds("1", 0, 0, mRectText);
        }
        // 由半径+指针短半径+实时读数文字高度确定的高度
//        int height1 = mRadius + mStrokeWidth * 2 + mPSRadius + mRectText.height() * 3;
        int height1 = mRadius + mStrokeWidth * 2  + mRectText.height() * 3;
        // 由起始角度确定的高度
        float[] point1 = getCoordinatePoint(mRadius, mStartAngle);
        // 由结束角度确定的高度
        float[] point2 = getCoordinatePoint(mRadius, mStartAngle + mSweepAngle);
        // 取最大值
        int max = (int) Math.max(
                height1,
                Math.max(point1[1] + mRadius + mStrokeWidth * 2, point2[1] + mRadius + mStrokeWidth * 2)
        );
        Log.e("TAG","啦啦啦啦啦正正正正：：：" + point1[1] + mRadius + mStrokeWidth * 2 + "=反反反反反反=" +point2[1] + mRadius + mStrokeWidth * 2);
        setMeasuredDimension(width, max + getPaddingTop() + getPaddingBottom());

        mCenterX = mCenterY = getMeasuredWidth() / 2f;

        mPaint.setTextSize(sp2px(10));
        mPaint.getTextBounds("0", 0, "0".length(), mRectText);

        Log.e("TAG","==============mLength2:" + mLength2 + "=mLength1=" + mLength1);
        //设置0到10的位置
        mRectFInnerArc.set(
                getPaddingLeft() + mLength2 + mRectText.height(),
                getPaddingTop() + mLength2 + mRectText.height(),
                getMeasuredWidth() - getPaddingRight() - mLength2 - mRectText.height(),
                getMeasuredWidth() - getPaddingBottom() - mLength2 - mRectText.height() - sp2px(10)
        );

        //设置圆弧的四个边
        mRectFArc.set(
                getPaddingLeft() + mStrokeWidth + mLength2 + mRectText.height() ,
                getPaddingTop() + mStrokeWidth + mLength2 + mRectText.height(),
                getMeasuredWidth() - getPaddingRight() - mStrokeWidth - (mLength2 + mRectText.height()) ,
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
        canvas.drawArc(mRectFArc, mStartAngle, mSweepAngle, false, mPaint);

        /**
         * 画进度圆弧
         */
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_14b7cc));
        canvas.drawArc(mRectFArc, mStartAngle,  calculateRelativeAngleWithValue(mRealTimeValue), false, mPaint);

        /**
         * 画读数
         * 添加一个圆弧path，文字沿着path绘制
         */
        mPaint.setTextSize(sp2px(10));
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_999999));
        for (int i = 0; i < mTexts.length; i++) {
            mPaint.getTextBounds(mTexts[i], 0, mTexts[i].length(), mRectText);
            // 粗略把文字的宽度视为圆心角2*θ对应的弧长，利用弧长公式得到θ，下面用于修正角度
            float θ = (float) (180 * mRectText.width() / 2 /
                    (Math.PI * (mRadius - mLength2 - mRectText.height())));

            mPath.reset();
            mPath.addArc(
                    mRectFInnerArc,
                    mStartAngle + i * (mSweepAngle / mSection) - θ, // 正起始角度减去θ使文字居中对准长刻度
                    mSweepAngle
            );
            canvas.drawTextOnPath(mTexts[i], mPath, 0, 0, mPaint);
        }

        /**
         * 画信用分指示器
         */
        float a = calculateRelativeAngleWithValue(mRealTimeValue);
        float b = mSweepAngle / 2f;
        canvas.save();
        b = mSweepAngle / 2f;
        canvas.rotate(a - b, mCenterX, mCenterY);
        Log.e("TAG","------------------b：" +b + "=a=" + a  +
                "=mCenterX=" + mCenterX + "=mCenterY=" + mCenterY );
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_14b7cc));
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.reset();

        Log.e("TAG","====================圆心坐标X：" +mCenterX + "=mCenterY=" + mCenterY  +
                "=mLength2=" + mLength2 + "=mPadding=" + mPadding );

        mPath.moveTo(mCenterX, mPadding + mLength2 + dp2px(30) + mStrokeWidth + mRectText.height());
        mPath.rLineTo(-dp2px(4), dp2px(10));
        mPath.rLineTo(dp2px(8), 0);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        /**
         * 画指针围绕的镂空圆心
         */
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterX, mCenterY, dp2px(2), mPaint);

        /**
         * 画实时度数值
         */
        if (isShowValue) {
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_14b7cc));
            String value = "0";
            if (mRealTimeValue == 0){
                value =  "0";
            }else {
                value = String.valueOf(mRealTimeValue);
            }
            mPaint.getTextBounds(value, 0, value.length(), mRectText);
            mPaint.setTextSize(sp2px(32));
            canvas.drawText(value, mCenterX, mCenterY - sp2px(20), mPaint);
            mPaint.setTextSize(sp2px(12));
            canvas.drawText("专利平均价值度", mCenterX, mCenterY , mPaint);

        }
    }

    /**
     * 相对起始角度计算信用分所对应的角度大小
     */
    private float calculateRelativeAngleWithValue(int value) {
        return (float) (mSweepAngle * value * 1f / mMax);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    //每个圆是贴着x与y的边画的
    public float[] getCoordinatePoint(int radius, float angle) {
        float[] point = new float[2];

        double arcAngle = Math.toRadians(angle); //将角度转换为弧度

        //Math.cos(arcAngle)=获得余弦值， radius是半径代表斜边
        if (angle < 90) {
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        } else if (angle > 90 && angle < 180) {
            //注：这里是反着求的，先求小于90度的坐标，之后再用中心圆点减去
            arcAngle = Math.PI * (180 - angle) / 180.0; //角度转弧度（π * 角度 / 180）
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        } else if (angle > 180 && angle < 270) {
            arcAngle = Math.PI * (angle - 180) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        } else if (angle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        } else {
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }

        return point;
    }

    public void setRealTimeValue(int realTimeValue) {
        if (mRealTimeValue == realTimeValue || realTimeValue < mMin || realTimeValue > mMax) {
            return;
        }
        mRealTimeValue = realTimeValue;
        postInvalidate();
    }
}
