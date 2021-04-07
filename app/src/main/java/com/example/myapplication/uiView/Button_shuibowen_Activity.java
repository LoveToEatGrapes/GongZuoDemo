package com.example.myapplication.uiView;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.View.SpreadView;

public class Button_shuibowen_Activity extends AppCompatActivity {

    private ImageView icon_80 ,icon_70,icon_60;
    private SpreadView spreadView;

    AnimatorSet animSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_shuibowen_);

        icon_80 = findViewById(R.id.icon_80);
        icon_60 = findViewById(R.id.icon_60);
        icon_70 = findViewById(R.id.icon_70);
        spreadView = findViewById(R.id.spreadView);

        spreadView.onSizeChanged(500,500,100,100);

        findViewById(R.id.收).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animSet.end();
            }
        });

        icon_70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ObjectAnimator objectAnimatorX =  ObjectAnimator.ofFloat(icon_80,"scaleX",1,0.9F);
//                ObjectAnimator objectAnimatorY =  ObjectAnimator.ofFloat(icon_80,"scaleY",1,0.9F);
//
//                animSet = new AnimatorSet();
//                animSet.playTogether(objectAnimatorX,objectAnimatorY);
//                animSet.setDuration(100000);
//                animSet.setInterpolator(new CycleInterpolator(80));
//                animSet.start();

                setAnim1();
                setAnim2();
            }
        });
    }

    private void setAnim1() {
        AnimationSet as = new AnimationSet(true);
        //缩放动画，以中心从原始放大到1.4倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        as.setDuration(800);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);
        icon_60.startAnimation(as);
    }

    private void setAnim2() {
        AnimationSet as = new AnimationSet(true);
        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.8f, 1.4f, 1.8f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 0.1f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        as.setDuration(800);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);
        icon_80.startAnimation(as);
    }
}