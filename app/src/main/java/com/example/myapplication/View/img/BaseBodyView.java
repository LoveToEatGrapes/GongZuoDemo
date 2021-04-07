package com.example.myapplication.View.img;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 类说明：身体区域
 *      array必存三个点 圆中心坐标 x,y 半径 r
 * Author: gaobaiq
 * Date: 2016/7/23 11:22
 */
public abstract class BaseBodyView extends ImageView {
    protected Context mContext;

    // 保存所有点击区域
//    private Map<string, bodyarea=""> mBodyArea;
    private Map<String, BodyArea> mBodyArea;

    // 保存点击的区域
    private Set<String> mFocus;

    protected Paint paint = new Paint();

    // 点击时Path区域的转换，用于触摸点的判断
    protected RectF mPathRectF = new RectF();


    protected OnBodyClickListener mBodyClick;


    public BaseBodyView(Context context) {
        this(context, null);
    }


    public BaseBodyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public BaseBodyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initDatas();
    }


    private void initDatas() {
        mBodyArea = new HashMap<>();
        mFocus = new HashSet<>();
        //paint.setStyle(Paint.Style.FILL);
        //paint.setARGB(170, 0, 205, 0);
        initBodyArea();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 进行触摸区域绘制
        //for(String key : mFocus) {
        //    Path path = mBodyArea.get(key).getPath();
        //    canvas.drawPath(path, paint);
        //}
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mBodyClick != null) {
            checkAreas(event);
            if(!mFocus.isEmpty()) {
                BodyArea area = null;
                for(String key : mFocus){
                    area = mBodyArea.get(key);
                    //invalidate();     // 渲染选中区域颜色
                    mBodyClick.onBodyClick(0, area.getPtKeys());
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void checkAreas(MotionEvent event) {
        mFocus.clear();
        for(String key : mBodyArea.keySet()) {
            mPathRectF.setEmpty();
            Path path = mBodyArea.get(key).getPath();
            path.computeBounds(mPathRectF, true);
            if(mPathRectF.contains(event.getX(),event.getY())) {
                mFocus.add(key);
                break;
            }
        }
    }

    public void initBodyArea() {
        mBodyArea.clear();
        mFocus.clear();
        String[] keys = mContext.getResources().getStringArray(initArray());
        BodyArea bodyArea = null;
        int idenId = -1;
        if(keys != null) {
            for(String key : keys) {
                idenId = mContext.getResources().getIdentifier(key, "array", initPackage());
                int[] paths = mContext.getResources().getIntArray(idenId);
                idenId = mContext.getResources().getIdentifier(key + "_code", "array", initPackage());
                String[] ptKeys = mContext.getResources().getStringArray(idenId);
                bodyArea = new BodyArea(ptKeys, paths);
                mBodyArea.put(key, bodyArea);
            }
        }
    }



    /**
     * 进行不同材质机器的兼容
     * */
    private float toDip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue * scale + 0.5f);
    }


    protected abstract int initArray();
    protected abstract String initPackage();


    /**
     * 点击事件抽象方法
     */
    public void setOnBodyClickListener(OnBodyClickListener listener) {
        this.mBodyClick = listener;
    }

    public interface OnBodyClickListener {
        void onBodyClick(int position, String[] keys);
    }



    /**
     * 圆形区域对象
     * */
    class BodyArea {
        private String[] mPtKeys;
        private Path mPath;
        public BodyArea(String[] ptKeys, int[] paths) {
            super();
            this.mPtKeys = ptKeys;
            mPath = new Path();
            int len = paths.length;
            if (len >= 3) {
                mPath.addCircle(toDip(mContext, paths[0]), toDip(mContext,
                        paths[1]), toDip(mContext, paths[2]),
                        Path.Direction.CW);
            }

            mPath.close();
        }

        public String[] getPtKeys() {
            return mPtKeys;
        }

        public void setPtKeys(String[] ptKeys) {
            this.mPtKeys = ptKeys;
        }

        public Path getPath() {
            return mPath;
        }
    }
}