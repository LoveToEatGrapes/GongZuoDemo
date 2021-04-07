package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.camare.CameraView;
import com.example.myapplication.camare.FocusImageView;

public class CamareMainActivity extends AppCompatActivity implements View.OnTouchListener {

    private CameraView mRecordCameraView;
    private FocusImageView mRecorderFocusIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camare_main);

        mRecordCameraView = findViewById(R.id.camera);
        mRecorderFocusIv = findViewById(R.id.recorder_focus_iv);
        mRecordCameraView.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float sRawX = event.getRawX();
                float sRawY = event.getRawY();
                float rawY = sRawY * MyApplication.screenWidth / MyApplication.screenHeight;
                float temp = sRawX;
                float rawX = rawY;
                rawY = (MyApplication.screenWidth - temp) * MyApplication.screenHeight / MyApplication.screenWidth;

                Point point = new Point((int) rawX, (int) rawY);
                mRecordCameraView.onFocus(point, new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            mRecorderFocusIv.onFocusSuccess();
                        } else {
                            mRecorderFocusIv.onFocusFailed();
                        }
                    }
                });
                mRecorderFocusIv.startFocus(new Point((int) sRawX, (int) sRawY));
        }
        return true;
    }



}