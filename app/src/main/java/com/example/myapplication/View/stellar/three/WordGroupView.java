package com.example.myapplication.View.stellar.three;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WordGroupView extends View {
    private static final String TAG = "HHH";
    private static final String TAG_TWO = "LLL";

    //文字绘制画笔
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //辅助线绘制画笔
    private Paint mSublinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private List<WordGroupView.DrawPoint> drawPointList = new ArrayList<>();
    private List<String> keyWords = new ArrayList<>();
    private int[] colors = {
            Color.parseColor("#009f9d"),
            Color.parseColor("#cd4545"),
            Color.parseColor("#7ed3b2"),
            Color.parseColor("#d195f9"),
            Color.parseColor("#222831"),
            Color.parseColor("#b55400"),
    };

    //文字展示最小可用宽高
    private int mMinTextWidth;
    private int mMinTextHeight;

    //最小可展示文字大小
    private int mMinFontSize;

    private int[][] mPoints;
    private int mMaxXPointCount;
    private int mMaxYPointCount;
    //设置词条最大长度和最小长度
    private int mMaxLength = 0;
    private int mMinLength = 0;

    //View的x中间轴与y中间轴
    private int centerX;
    private int centerY;

    private int widthCount;
    private int heightCount;

    public WordGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //最小可展示文字大小
        mMinFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                context.getResources().getDisplayMetrics());

        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mMinFontSize);

        Rect textBound = new Rect();
        mTextPaint.getTextBounds("正", 0, 1, textBound);

        mMinTextWidth = mMinTextHeight = textBound.width();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthCount = w;
        heightCount = h;

        mMaxXPointCount = w / mMinTextWidth;
        mMaxYPointCount = h / mMinTextHeight;

        centerX = (getRight() - getLeft()) / 2;
        centerY = (getBottom() - getTop()) / 2;

        Log.e(TAG, "=============::" + getRight() + "===" + getX() + "===" + getY() + "=w=" + w + "=h=" + h);

        Log.e(TAG, mMinTextWidth + "-" + mMinTextHeight + "==" + centerX + "==" + centerY);

        Log.e(TAG, "=========aaa:" + w + "-" + h + "-" + oldw + "-" + oldh + "-" + mMaxXPointCount + "-" + mMaxYPointCount);

        mPoints = new int[mMaxXPointCount][mMaxYPointCount];
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                mPoints[i][j] = 0;
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#F2F2F2"));

        /**
         * 绘制辅助线
         */
        for (int i = 0; i <= mMaxXPointCount; i++) {
            canvas.drawLine(i * mMinTextWidth, 0, i * mMinTextWidth, mMinTextHeight * mMaxYPointCount, mSublinePaint);
        }

        for (int i = 0; i <= mMaxXPointCount; i++) {
            canvas.drawLine(0, i * mMinTextHeight, mMinTextWidth * mMaxXPointCount, i * mMinTextHeight, mSublinePaint);
        }

        //widget=4表示单个文字占用空间为4x4,其他类似
//        for (int i = 4; i >= 1; i--) {
        for (int i = keyWords.size(); i >= 1; i--) {
            buildPoints(mPoints, getFullContent(i), i, 0, canvas);
        }

        Log.e(TAG, "=========bbb:" + drawPointList.size());

        /**
         * 绘制真实的效果
         */
        for (DrawPoint point : drawPointList) {
            point.draw(canvas);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private List<WordGroupBean> list = new ArrayList<>();
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    /**
     * 不断检索并构建指定widget大小的数据，直到不能容纳或者绘制完成
     */
    private void buildPoints(int[][] points, String[] maxItems, int widget, int index, Canvas canvas) {
        Log.e(TAG, "===================index:" + index + "=widget=" + widget + " =maxItems[index]=" + maxItems[index]);

        Paint paintTwo = new Paint();
        paintTwo.setAntiAlias(true);
        paintTwo.setColor(Color.parseColor("#0000FF"));
        paintTwo.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, mMinTextWidth / 4, paintTwo);

//        Rect rect = new Rect();
//        mTextPaint.getTextBounds(maxItems[index],0, maxItems[index].length(),rect);
//        int width = rect.width();
//        int height = rect.height();

//        WordGroupBean b = new WordGroupBean();
//        b.setLeft(rect.left);
//        b.setRight(rect.right);
//        b.setTop(rect.top);
//        b.setBottom(rect.bottom);
//        list.add(b);

//        int countWidth = (width % mMinTextWidth==0) ? width/mMinTextWidth : (width/mMinTextWidth + 1);
//        int countHeight = (height % mMinTextHeight==0) ? height/mMinTextHeight : (height/mMinTextHeight + 1);

//        Log.e(TAG,"left:" + rect.left + "=top=" + rect.top + "=right=" + rect.right + "=bottom=" + rect.bottom
//        + "=width=" + width + "=countWidth=" + countWidth + "=height=" + height + "=countHeight=" + countHeight);

//        if (widget == keyWords.size()){
        drawPointList.add(new DrawPoint(centerX, centerY, maxItems[index], widget));
//        }else {
//            WordGroupBean wordGroupBean = list.get(widget - 1);
//        }
    }

    private int lastHeight = 0; // draw上一次的高度
    private int lastWidth = 0; // draw上一次的宽度
    private int nextX = 0; //下一个x轴坐标
    private int nextY = 0; //下一个y轴坐标
    private List<WordGroupBean> wordGroupBeans = new ArrayList<>();
    int i = 0;

    public class DrawPoint {
        private int pointX;
        private int pointY;
        public String label;
        public int widget;

        DrawPoint(int pointX, int pointY, String label, int widget) {
            this.pointX = pointX;
            this.pointY = pointY;
            this.label = label;
            this.widget = widget;
        }

        public void draw(Canvas canvas) {
//          mTextPaint.setTextSize(mMinFontSize * widget - 8 * widget);
            mTextPaint.setTextSize(mMinFontSize * 2);
            mTextPaint.setColor(colors[(int) (Math.random() * colors.length)]);
            //Log.e(TAG, widget + "-" + label + "-" + pointX + "-" + pointY);

            Rect rect = new Rect();
            mTextPaint.getTextBounds(label, 0, label.length(), rect);
            int width = rect.width();
            int height = rect.height();

            int countWidth = (width % mMinTextWidth == 0) ? width / mMinTextWidth : (width / mMinTextWidth + 1);
            int countHeight = (height % mMinTextHeight == 0) ? height / mMinTextHeight : (height / mMinTextHeight + 1);

            WordGroupBean b = new WordGroupBean();
            b.setLeft(rect.left);
            b.setRight(rect.right);
            b.setTop(rect.top);
            b.setBottom(rect.bottom);
            list.add(b);

            Log.e(TAG, "left:" + rect.left + "=top=" + rect.top + "=right=" + rect.right + "=bottom=" + rect.bottom
                    + "=width=" + width + "=countWidth=" + countWidth + "=height=" + height + "=countHeight=" + countHeight
                    + "=centerX=" + rect.centerX() + "=centerY=" + rect.centerY());

            Log.e(TAG, "widget:" + widget);

            boolean is = true;
            boolean ii = false;
            if (is) {
                //存储的第一圈
                List<WordGroupBean.RecordXAndY> recordXAndYList = new ArrayList<>();
                WordGroupBean.RecordXAndY recordXAndY = new WordGroupBean.RecordXAndY();
                recordXAndY.setTextHeight(width);
                recordXAndY.setCountWidth(height);
                recordXAndY.setTextHeight(width);
                recordXAndY.setCountHeight(height);
                recordXAndY.setxPoint(pointX - width / 2);
                recordXAndY.setyPoint(pointY + height / 2);
                recordXAndYList.add(recordXAndY);
                WordGroupBean wordGroupBean = new WordGroupBean();
                wordGroupBean.setRecordXAndies(recordXAndYList);
                wordGroupBeans.add(wordGroupBean);
                if (i == 0){

                    i++;
                    canvas.drawText(label, pointX , pointY , mTextPaint);

                }else {

                    int xx = (int) (Math.random() * pointX);
                    int yy = (int) (Math.random() * pointY);
                    Log.e(TAG,"=========x:" + xx + "=y=" + yy );
                    canvas.drawText(label, xx , yy , mTextPaint);
                }

//                canvas.drawText(label, pointX - width / 2, pointY + height / 2, mTextPaint);
            }

            if (ii) {

                if (widget == 5) {
                    canvas.drawText(label, pointX - width / 2, pointY + height / 2, mTextPaint);
                    lastHeight += height;
                    Log.e(TAG, "55555555555555555555555555=xxxxxxxxxxxx=" + (pointX - width / 2) +
                            "=yyyyyyy=" + (pointY - lastHeight));
                }
                if (widget == 4) {
                    canvas.drawText(label, pointX - width / 2, pointY - height / 2, mTextPaint);
                    lastWidth += width;
                    Log.e(TAG, "44444444444444444444=xxxxxxxxxxxx=" +
                            (pointX - width / 2) + "=yyyyyyy=" + (pointY - height / 2));
                }
                if (widget == 3) {
                    canvas.drawText(label, pointX - width / 2 + lastWidth, pointY - height / 2, mTextPaint);
                    Log.e(TAG, "333333333333333333333=xxxxxxxxxxxx=" +
                            (pointX - width / 2 + lastWidth) + "=yyyyyyy=" + (pointY - height / 2));
                }
                if (widget == 2) {
                    Log.e(TAG, "222222222222222222222=xxxxxxxxxxxx=" +
                            (pointX - width / 2 + lastWidth) + "=yyyyyyy=" + (pointY + height / 2));
                    canvas.drawText(label, pointX - width / 2 + lastWidth, pointY + height / 2, mTextPaint);
                }
                if (widget == 1) {
                    Log.e(TAG, "111111111111111111111=xxxxxxxxxxxx=" +
                            (pointX - width / 2) + "=yyyyyyy=" + (pointY + height + height / 2));
                    canvas.drawText(label, pointX - width / 2, pointY + height + height / 2, mTextPaint);
                }
            }


            mTextPaint.setStyle(Paint.Style.FILL);
        }
    }

    public WordGroupBean BeanData() {
        WordGroupBean one = new WordGroupBean();
        WordGroupBean two = new WordGroupBean();
        List<String> s1 = new ArrayList<>();
        s1.add("超级新手计划");
        two.setTitle(s1);
        one.setBean(two);

        WordGroupBean three = new WordGroupBean();
        List<String> s2 = new ArrayList<>();
        s2.add("乐享");
        s2.add("钱包计划");
        s2.add("理财计划");
        three.setTitle(s2);
        one.setBean(three);

        WordGroupBean four = new WordGroupBean();
        List<String> s3 = new ArrayList<>();
        s3.add("人来人往");
        s3.add("人来");
        s3.add("人往");
        s3.add("自由自在");
        s3.add("自在");
        s3.add("自由");
        s3.add("白手起家");
        s3.add("起家");
        s3.add("白手");
        four.setTitle(s3);
        two.setBean(four);

        return one;
    }


    private String[] getFullContent(int widget) {

        int maxCount = mMaxXPointCount * mMaxYPointCount * widget * widget;
        int currMaxItemCount = (int) (Math.random() * maxCount);
//        String[] maxItems = new String[currMaxItemCount];
        String[] maxItems = new String[keyWords.size()];

        for (int i = 0; i < maxItems.length; i++) {
//            maxItems[i] = keyWords.get((int) (Math.random() * keyWords.size()));
            maxItems[i] = keyWords.get(i);
        }
//        if (maxItems.length == 0) {
//            maxItems = new String[]{
//                    keyWords.get(0)
//            };
//        }
        return maxItems;
    }

    /**
     * 超出可容纳范围
     */
    private boolean capacityOut(int[][] points, int widget, String maxItem) {
        boolean isCapacityOut = true;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                if (isCanDraw(points, i, j, maxItem, widget)) {
                    isCapacityOut = false;
                    break;
                }
            }
        }
        return isCapacityOut;
    }

    /**
     * 判断是否可以绘制
     */
    private boolean isCanDraw(int[][] points, int randomPointX, int randomPointY, String maxItem, int widget) {
        boolean isCanFull = false;
        if (isOutHorizontal(randomPointX + maxItem.length() * widget)
                && isOutVertical(randomPointY + widget)) {
            isCanFull = true;
            for (int i = randomPointX; i < randomPointX + maxItem.length() * widget; i++) {
                for (int j = randomPointY; j < randomPointY + widget; j++) {
                    if (!isCanFull(points, i, j)) {
                        isCanFull = false;
                        break;
                    }
                }
            }
        }
        return isCanFull;
    }

    /**
     * 判断水平方向是否越界
     */
    private boolean isOutHorizontal(int pointX) {
        return pointX <= mMaxXPointCount;
    }

    /**
     * 判断竖直方向是否越界
     */
    private boolean isOutVertical(int pointY) {
        return pointY <= mMaxYPointCount;
    }

    /**
     * 是否可以填充
     */
    private boolean isCanFull(int[][] point, int pointX, int pointY) {

        if (pointX < mMaxXPointCount && pointY < mMaxYPointCount) {
            return point[pointX][pointY] == 0;
        }
        return false;
    }

    public void random() {
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                mPoints[i][j] = 0;
            }
        }
        drawPointList.clear();
        invalidate();
    }


    public void setWords(List<String> words) {
        this.keyWords = words;
        for (int i = 0; i < keyWords.size(); i++) {
            if (i == 0) {
                mMaxLength = keyWords.get(i).length();
                mMinLength = keyWords.get(i).length();
                continue;
            }
            if (keyWords.get(i).length() > mMaxLength) {
                mMaxLength = keyWords.get(i).length();
            }
            if (keyWords.get(i).length() < mMinLength) {
                mMinLength = keyWords.get(i).length();
            }
        }
        drawPointList.clear();
        invalidate();
    }
}
