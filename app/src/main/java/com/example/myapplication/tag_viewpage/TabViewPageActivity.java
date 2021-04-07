package com.example.myapplication.tag_viewpage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabViewPageActivity extends AppCompatActivity {

    private MarqueeTextView mar;

    PickerView minute_pv;
    PickerView second_pv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view_page);

        mar = findViewById(R.id.mar);

        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        linearLayout = findViewById(R.id.horizontalScrollViewItemContainer);



        minuteFun();

        bindData();

        //参数 3 是滚动速度
        mar.initScrollTextView(getWindowManager(), "今天星期五_滚动字", 2);
        mar.setText("显示文字");
        mar.starScroll();
    }

    private void minuteFun() {
        minute_pv = (PickerView) findViewById(R.id.minute_pv);
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 3; i++)
        {
            data.add("0" + i);
        }

        minute_pv.setData(data);
        minute_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(TabViewPageActivity.this, "选择了 " + text + " 分",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    // 初始化布局中的控件
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout linearLayout;
    // item的值
   private String[] names = new String[]{"刘一", "刘二", "刘三", "刘四", "刘五", "刘六","郑一", "郑二", "郑三", "郑四", "郑五", "郑六","张一", "张二", "张三", "张四", "张五", "张六","李一", "李二", "李三", "李四", "李五", "李六"};
    // private String[] names = new String[]{"刘一", "刘二"};
    private ArrayList<String> data = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();


    //  将集合中的数据绑定到HorizontalScrollView上
    private void bindData() {
        // 为布局中textview设置好相关属性
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(20, 10, 20, 10);

        // 将字符串数组与集合绑定起来
        Collections.addAll(data, names);

        for (int i = 0; i < data.size(); i++) {
            final TextView textView = new TextView(this);
            textView.setText(data.get(i));
            textView.setTextColor(Color.RED);
            textView.setTextSize(20);
            textView.setLayoutParams(layoutParams);

            textViews.add(textView);

            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    // 手指离开后使当前Item居中
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        // 使Item居中
                        getCenterItemTwo(view);
                    }
                    return true;
                }
            });

            Log.i("TAG","--------------------------1:");
            linearLayout.addView(textView);
            linearLayout.invalidate();
        }
    }

    private void getCenterItemTwo(View view) {
        ((TextView) view).setTextSize(50);
        for (int i = 0; i < textViews.size(); i++){
            if (!textViews.get(i).getText().toString().equals(((TextView) view).getText().toString())){
                textViews.get(i).setTextSize(20);
            }
        }

        // 获取horizontalScrollView的宽度
        int hsvWidth = horizontalScrollView.getWidth();
        // 获取textview左边缘的位置
        int textViewLeft = view.getLeft();
        // 获取textview Item的宽度
        int textViewWidth = view.getWidth();
        // 计算偏移量
        int offset = textViewLeft + textViewWidth / 2 - hsvWidth / 2;

        // 横向平滑滚动偏移
        horizontalScrollView.smoothScrollTo(offset, 0);

        // 得到当前居中的Item名字

        String s = "CenterLocked Item: " + ((TextView) view).getText();
        Log.e("TAG","performItemClick====="+ s);
    }


    private void getCenterItem(View view) {
        // 获取horizontalScrollView的宽度
        int hsvWidth = horizontalScrollView.getWidth();
        // 获取textview左边缘的位置
        int textViewLeft = view.getLeft();
        // 获取textview Item的宽度
        int textViewWidth = view.getWidth();
        // 计算偏移量
        int offset = textViewLeft + textViewWidth / 2 - hsvWidth / 2;

        // 横向平滑滚动偏移
        horizontalScrollView.smoothScrollTo(offset, 0);

        // 得到当前居中的Item名字
        ((TextView) view).setTextSize(50);
        for (int i = 0; i < textViews.size(); i++){
            if (!textViews.get(i).getText().toString().equals(((TextView) view).getText().toString())){
                textViews.get(i).setTextSize(20);
            }
        }
        String s = "CenterLocked Item: " + ((TextView) view).getText();
        Log.e("TAG","performItemClick====="+ s);
    }


}