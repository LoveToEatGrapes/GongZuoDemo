package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.View.EHorizontalSelectedView;

import java.util.ArrayList;
import java.util.List;

public class EHorizontalSelectActivity extends AppCompatActivity {

    private EHorizontalSelectedView horizontalSelectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_horizontal_select);

        horizontalSelectedView = findViewById(R.id.hsv);

        List<String> objects  = new ArrayList<>();
        objects.add("拍照");
        objects.add("扫码");
        horizontalSelectedView.setData(objects);
        horizontalSelectedView.setSelectNum(0);
        horizontalSelectedView.setSeeSize(4);

        horizontalSelectedView.setOnRollingListener(new EHorizontalSelectedView.OnRollingListener() {
            @Override
            public void onRolling(int position, String s) {

                Toast.makeText(EHorizontalSelectActivity.this,position + "==" + s,Toast.LENGTH_SHORT).show();

            }
        });

    }




}