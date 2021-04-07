package com.example.myapplication.View.img;

import android.content.Context;
import android.util.AttributeSet;

import com.example.myapplication.R;

public class ManHeadView extends BaseBodyView {

    public ManHeadView(Context context) {
        this(context, null);
    }


    public ManHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ManHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override protected int initArray() {
        return R.array.man_head;
    }


    @Override protected String initPackage() {
        return mContext.getPackageName();
    }
}
