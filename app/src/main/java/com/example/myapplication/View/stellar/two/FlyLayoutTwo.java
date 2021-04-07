package com.example.myapplication.View.stellar.two;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.myapplication.View.stellar.RandomLayout;

import java.util.ArrayList;
import java.util.List;

public class FlyLayoutTwo extends FrameLayout implements RandomLayoutTwo.OnItemClickListener, RandomLayoutTwo.OnAnimationEndListener {

    private Context mContext;

    public FlyLayoutTwo(Context context) {
        this(context, null);
    }

    public FlyLayoutTwo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlyLayoutTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setData(@NonNull String[]... params) {
        removeAllViews();
        for (String[] param : params) {
            RandomLayoutTwo randomLayout = new RandomLayoutTwo(mContext);
            randomLayout.setData(param);
            randomLayout.setOnItemClickListener(this);
            randomLayout.setOnAnimationEndListener(this);
            addView(randomLayout);
        }
    }

    public void setData(@NonNull List<String>... params) {
        removeAllViews();
        for (List<String> param : params) {
            RandomLayoutTwo randomLayout = new RandomLayoutTwo(mContext);
            randomLayout.setData((ArrayList<String>) param);
            randomLayout.setOnItemClickListener(this);
            randomLayout.setOnAnimationEndListener(this);
            addView(randomLayout);
        }
    }

    public void setData(@NonNull ArrayList<String[]> params) {
        removeAllViews();
        for (String[] param : params) {
            RandomLayoutTwo randomLayout = new RandomLayoutTwo(mContext);
            randomLayout.setData(param);
            randomLayout.setOnItemClickListener(this);
            randomLayout.setOnAnimationEndListener(this);
            addView(randomLayout);
        }
    }

    public void setData(@NonNull List<List<String>> params) {
        removeAllViews();
        for (List<String> param : params) {
            RandomLayoutTwo randomLayout = new RandomLayoutTwo(mContext);
            randomLayout.setData((ArrayList<String>) param);
            randomLayout.setOnItemClickListener(this);
            randomLayout.setOnAnimationEndListener(this);
            addView(randomLayout);
        }
    }


    public interface OnFlyEverythingListener{
        void onItemClick(View view, int position, String text);
        void onAnimationEnd(RandomLayoutTwo randomLayout, int animationCount);
    }

    private OnFlyEverythingListener mListener;

    public void setOnFlyEverythingListener(OnFlyEverythingListener listener) {
        mListener = listener;
    }

    public void startAnimation() {
        if (getChildCount() > 0) {
            ((RandomLayoutTwo) getChildAt(getChildCount() - 1)).startAnimation();
        }
    }

    @Override
    public void onItemClick(View view, int position, String text) {
        if (mListener != null) {
            mListener.onItemClick(view,position,text);
        }
    }

    @Override
    public void onAnimationEnd(RandomLayoutTwo randomLayout, int animationCount) {
        removeView(randomLayout);
        addView(randomLayout,0);
        if (mListener != null) {
            mListener.onAnimationEnd(randomLayout,animationCount);
        }
    }
}
