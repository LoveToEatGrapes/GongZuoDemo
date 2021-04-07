package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class ImageComPressActivity extends AppCompatActivity implements View.OnClickListener {


    private Button start_compress ,start_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_com_press);

        start_compress = findViewById(R.id.start_compress);
        start_select = findViewById(R.id.start_select);

        start_compress.setOnClickListener(this);
        start_select.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

    }
}