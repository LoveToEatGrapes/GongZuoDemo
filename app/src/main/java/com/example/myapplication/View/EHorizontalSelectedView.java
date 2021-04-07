package com.example.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ocr底部滚动按钮
 * @author ccx
 */
public class EHorizontalSelectedView extends View {
    private Context mContext;
    private Paint mOthers;
    private Paint mSelect;
    private List<String> data        = new ArrayList<>();
    /**
     * 可见数
     */
    private int          seeSize;
    private Rect mRect       = new Rect();
    /**
     * 选中位置
     */
    private int          selectNum   = 0;
    private float        downX;
    private int          mItemSize;
    private float        mOffset;
    private int          selectColor = Color.BLACK;
    private int          otherColor  = Color.RED;
    private float        otherTextSize;
    private float        selectTextSize;


    private int width;
    private int height;
    private float textWidthAll;
    private int index=-1;


    public EHorizontalSelectedView(Context context) {
        this(context, null);
    }

    public EHorizontalSelectedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EHorizontalSelectedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.EHorizontalSelectedView);
        otherTextSize = typedArray.getDimension(R.styleable.EHorizontalSelectedView_otherTextSize, 36);
        selectTextSize = typedArray.getDimension(R.styleable.EHorizontalSelectedView_selectTextSize, 36);
        seeSize = typedArray.getInteger(R.styleable.EHorizontalSelectedView_seeSize, 1);
        otherColor = typedArray.getColor(R.styleable.EHorizontalSelectedView_otherColor, Color.RED);
        selectColor = typedArray.getColor(R.styleable.EHorizontalSelectedView_selectColor, Color.BLACK);
        typedArray.recycle();
    }

    private void initPaint() {
        mOthers = new Paint();
        mOthers.setAntiAlias(true);
        mOthers.setTextSize(otherTextSize);
        mOthers.setColor(otherColor);

        mSelect = new Paint();
        mSelect.setAntiAlias(true);
        mSelect.setColor(selectColor);
        mSelect.setTextSize(selectTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取宽度
        int width  = getWidth();
        int height = getHeight();

        // 获取每个条目的大小
        if (seeSize == 0) {
            return;
        }
        mItemSize = width / seeSize;
        int tmp = 0;
        for (String datum : data) {
            mOthers.getTextBounds(datum, 0, datum.length(), mRect);
            int textWidth = mRect.width();
            if (textWidth > tmp) {
                tmp = textWidth;
            }
        }
        // 修正文字过大导致长度bug
        mItemSize = Math.max(mItemSize, tmp);
        seeSize = width / mItemSize;
        // | dfadf |  dsafa | afasdf |
        // 得到选中的条目
        // 画出第一个
        for (int j = 0; j < data.size(); j++) {
            String datum = data.get(j);
            mOthers.getTextBounds(datum, 0, datum.length(), mRect);
            int textWidth  = mRect.width();
            int textHeight = mRect.height();
            textWidthAll = textWidth;

            if (j != selectNum) {
                // 画其他的
                if (j < selectNum) {
                    int a = selectNum - j;
//
//                    Log.i("TAG","我是谁111：：：：：" + datum + "==" + (mItemSize * seeSize / 2 - textWidth / 2 + a * mItemSize + mOffset) +
//                            "=a=" + a + "=selectNum=" + selectNum);
                    canvas.drawText(datum, mItemSize * seeSize / 2 - textWidth / 2 - a * mItemSize + mOffset, height / 2 - textHeight / 2, mOthers);
                } else {
                    int a = j - selectNum;
//                    Log.i("TAG","我是谁2222：：：：：" + datum + "==" + (mItemSize * seeSize / 2 - textWidth / 2 + a * mItemSize + mOffset) +
//                            "=a=" + a + "=selectNum=" + selectNum);

                    canvas.drawText(datum, mItemSize * seeSize / 2 - textWidth / 2 + a * mItemSize + mOffset, height / 2 - textHeight / 2, mOthers);
                }
            } else {
//                Log.i("TAG","我是谁3333：：：：：" + datum+ "==" + (mItemSize * seeSize / 2 - textWidth / 2 + mOffset) +
//                        "=selectNum=" + selectNum);

                canvas.drawText(datum, mItemSize * seeSize / 2 - textWidth / 2 + mOffset, height / 2 - textHeight / 2, mSelect);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();

                //抬起手指时，偏移量归零，相当于回弹。
                float scrollXUp = event.getX();
                // Log.e("TAG","================::ACTION_UP:::居中的位置：" + scrollXUp  );

                mOffset = scrollXUp - downX;

                int w = width/2 - 10;
                if (downX > w){
                    Log.e("TAG","您好啊，，，我是扫码" +selectNum);
                    if (selectNum < data.size() - 1) {
                        mOffset = 0;
                        selectNum = selectNum + 1;
                        downX = scrollXUp;
                        if (mOnRollingListener != null) {
                            mOnRollingListener.onRolling(selectNum, data.get(selectNum));
                        }
                    }
                    //invalidate();
                }else if (downX < w){
                    Log.i("TAG","您好啊，，，我是拍照");
                    mOffset = 0;
                    if (selectNum > 0) {
                        selectNum = selectNum - 1;
                        downX = scrollXUp;
                        if (mOnRollingListener != null) {
                            mOnRollingListener.onRolling(selectNum, data.get(selectNum));
                        }

                    }

                    // invalidate();
                }

                // 向右滑动
                if (scrollXUp > downX) {
                    // 如果滑动距离大于一个条目的大小，则减1
                    if (scrollXUp - downX >= mItemSize) {

                    }
                } else {
                    //向左滑动大于一个条目的大小,则加1
                    if (downX - scrollXUp >= mItemSize) {

                    }
                }


                break;
            case MotionEvent.ACTION_MOVE:
                float scrollX = event.getX();

                mOffset = scrollX - downX;
                // 向右滑动
                if (scrollX > downX) {
                    // 如果滑动距离大于一个条目的大小，则减1
                    if (scrollX - downX >= mItemSize) {
                        if (selectNum > 0) {
                            mOffset = 0;
                            selectNum = selectNum - 1;
                            downX = scrollX;
                            if (mOnRollingListener != null) {
                                mOnRollingListener.onRolling(selectNum, data.get(selectNum));
                            }
                            //invalidate();
                        }
                    }
                } else {
                    //向左滑动大于一个条目的大小,则加1
                    if (downX - scrollX >= mItemSize) {
                        if (selectNum < data.size() - 1) {
                            mOffset = 0;
                            selectNum = selectNum + 1;
                            downX = scrollX;
                            if (mOnRollingListener != null) {
                                mOnRollingListener.onRolling(selectNum, data.get(selectNum));
                            }
                           // invalidate();
                        }
                    }
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                mOffset = 0;

                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    public int getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(int selectNum) {
        if (selectNum > data.size()) {
            selectNum = data.size() - 1;
        }
        this.selectNum = selectNum;
        invalidate();
    }

    public void setSelectTextColor(int color) {
        this.selectColor = color;
        invalidate();
    }

    public int getSelectColor() {
        return selectColor;
    }

    public void setOtherTextColor(int color) {
        this.otherColor = color;
        invalidate();
    }

    public String getSelectText() {
        return data.get(selectNum);
    }

    public void setData(List<String> data) {
        this.data = data;
        invalidate();
    }

    public void setSeeSize(int seeSize) {
        this.seeSize = seeSize;
        invalidate();
    }

    private OnRollingListener mOnRollingListener;

    public void setOnRollingListener(OnRollingListener onRollingListener) {
        mOnRollingListener = onRollingListener;
    }

    public interface OnRollingListener {
        /**
         * 滚动监听
         *
         * @param position 角标
         * @param s        滚动的文字
         */
        void onRolling(int position, String s);
    }

    public void setOtherTextSize(float otherTextSize) {
        this.otherTextSize = otherTextSize;
    }

    public void setSelectTextSize(float selectTextSize) {
        this.selectTextSize = selectTextSize;
    }


    //定义一个接口对象listerner
    private OnItemSelectListener listener;
    //获得接口对象的方法。
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }
    //定义一个接口
    public interface  OnItemSelectListener{
        public void onItemSelect(int index, String indexString);
    }

}
