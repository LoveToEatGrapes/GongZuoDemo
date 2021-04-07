package com.example.myapplication.View.stellar.three;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by chao on 2019/2/15.
 */

public class WordCloudView extends FrameLayout implements View.OnClickListener {

    Random random = new Random();
    String[] words;
    HashSet<View> placed = new HashSet<>();
    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public WordCloudView(@NonNull Context context) {
        this(context, null);
    }

    public WordCloudView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WordCloudView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int n = getChildCount();
        Log.e("TTT", "=========110========字数图总数==嘿嘿：" + n);
        for (int i = 0; i < n; i++) {
            View v = getChildAt(i);
            if (placed.contains(v)) {
                continue;
            }

            int w = v.getMeasuredWidth();
            int h = v.getMeasuredHeight();

//            int pivotX = getWidth() / 3 + random.nextInt(getWidth() / 3);
//            int pivotY = getHeight() / 3 + random.nextInt(getHeight() / 3);
            //设置绘画位置
            int pivotX = getWidth() / 2;
            int pivotY = getHeight() / 2;

            List<Point> spiral = generateSpiral();
            Log.e("TTT", "==============315======" + spiral.size() + "=w=" + w + "=h=" + h + "=pivotX=" + pivotX
                    + "=pivotY=" + pivotY);

            for (Point p : spiral) {
                pivotX += p.x;
                pivotY += p.y;

                //不允许超越屏幕
                if (pivotX < 0 || pivotX > getWidth() || pivotY < 0 || pivotY > getHeight()) {
                    break;
                }

                Log.e("chao", "place " + pivotX + "," + pivotY);
                Rect r1 = getVisualRect(pivotX, pivotY, w, h, v.getRotation());
                boolean isOverlap = false;
                for (View pv : placed) {
                    Rect r2 = getVisualRect(pv);
                    if (isOverlap(r1, r2)) {
                        isOverlap = true;
                        break;
                    }
                }
                Log.e("UUU", "-------12345678-----------:" + isOverlap);
                if (isOverlap) {

                } else {
                    Log.e("HHH", "placed");
                    Rect r = getRect(pivotX, pivotY, w, h);
                    v.layout(r.left, r.top, r.right, r.bottom);
                    break;
                }
            }
            placed.add(v);
        }
    }

    public Rect getRect(int pivotX, int pivotY, int width, int height) {
        return new Rect(
                pivotX - width / 2,
                pivotY - height / 2,
                pivotX + width / 2,
                pivotY + height / 2
        );
    }

    public Rect getVisualRect(int pivotX, int pivotY, int width, int height, float rotation) {
        if (rotation != 0) {
            int temp = width;
            width = height;
            height = temp;
        }
        return getRect(pivotX, pivotY, width, height);
    }

    public Rect getVisualRect(View v) {
        return getVisualRect(
                (v.getRight() + v.getLeft()) / 2,
                (v.getBottom() + v.getTop()) / 2,
                v.getMeasuredWidth(),
                v.getMeasuredHeight(),
                v.getRotation()
        );
    }

    private int r = 0;

    public boolean isOverlap(Rect r1, Rect r2) {
//        return r1.right  >= r2.left && r2.right >= r1.left
//                && r1.bottom >= r2.top && r2.bottom >= r1.top;
        return r1.right + r >= r2.left && r2.right >= r1.left - r
                && r1.bottom + r >= r2.top && r2.bottom >= r1.top - r;
    }


//    public void setWords(String[] words) {
//        this.words = words;
//        placed.clear();
//        removeAllViews();
//        for(final String word : words) {
//            addTextView(word);
//        }
//    }


    float[] rotates = {
            0f, 0f, 0f
            //0f,90f,270f
    };

    public void addTextView(String word, int weight, String colorId) {
        TextView tv = new TextView(getContext());
        tv.setText(word);
        tv.setTextSize(weight);
//        tv.setTextColor(ContextCompat.getColor(getContext(), Integer.parseInt(colorId)));
        tv.setTextColor(Color.parseColor(colorId));
//        tv.setRotation(rotates[random.nextInt(rotates.length)]);
        tv.setOnClickListener(this);
        addView(tv, params);
    }

    TextView lastText;

    @Override
    public void onClick(final View v) {
        if (v instanceof TextView) {
            Log.e("chao", "click " + ((TextView) v).getText());
//            ((TextView) v).setTextColor(Color.RED);
//            if(lastText != null) {
//                lastText.setTextColor(Color.BLACK);
//            }
//            lastText = (TextView) v;

            new XPopup.Builder(getContext())
                    .hasShadowBg(false)
//                        .popupAnimation(PopupAnimation.NoAnimation) //NoAnimation表示禁用动画
                    .isCenterHorizontal(true) //是否与目标水平居中对齐
//                        .offsetY(-60)
//                        .offsetX(80)
//                    .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
                    .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                    .asAttachList(new String[]{"1", "2", "3", "4"},
                            new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    ((TextView) v).setTextColor(Color.RED);
                                    if (lastText != null) {
                                        lastText.setTextColor(Color.BLACK);
                                    }
                                    lastText = (TextView) v;
                                }
                            })
//                        .bindLayout(R.layout.my_custom_attach_popup)
//                        .bindItemLayout(R.layout.my_custom_attach_popup)
                    .show();
        }
    }

    private List<Point> generateSpiral() {
        List<Point> res = new ArrayList<>();
        int A = 10;
        int B = 10;
        int w = 5;
        double sita = Math.PI;
        for (double t = 0; t < 10 * Math.PI; t += 0.1) {
            int x = Double.valueOf(A * Math.cos(w * t + sita)).intValue();
            int y = Double.valueOf(B * Math.sin(w * t + sita)).intValue();
            A += 2;
            B += 1;
            res.add(new Point(x, y));
            Log.e("LLL", x + ", " + y);
        }
        return res;
    }

//    private List<Point> generateSpiral() {
//        List<Point> res = new ArrayList<>();
//        int A = 10;
//        int w = 5;
//        double sita = Math.PI;
//        for(double t = 0; t < 10 * Math.PI; t+=0.1) {
//            int x = Double.valueOf(A * Math.cos(w * t + sita)).intValue();
//            int y = Double.valueOf(A * Math.sin(w * t + sita)).intValue();
//            A += 1;
//            res.add(new Point(x, y));
//            Log.e("LLL", x + ", " + y);
//        }
//        return res;
//    }
}
