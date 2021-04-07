package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.View.DilatingDotsProgressBar;

public class AnimationActivity extends AppCompatActivity {


    private DilatingDotsProgressBar mDilatingDotsProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mDilatingDotsProgressBar = findViewById(R.id.tv_page_loading_blink);
        mDilatingDotsProgressBar.show(500);

    }



}