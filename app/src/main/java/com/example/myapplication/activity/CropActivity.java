package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.crop.CropImageView;

public class CropActivity extends AppCompatActivity {


    private CropImageView crop_image_view;
    private ImageView iv;
    private TextView tv_crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        iv = findViewById(R.id.iv);
        tv_crop = findViewById(R.id.tv_crop);
        crop_image_view = findViewById(R.id.crop_image_view);

        tv_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bitmap croppedImage = crop_image_view.getCroppedImage();
                iv.setImageBitmap(croppedImage);
            }
        });

        findViewById(R.id.twocrop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CropActivity.this,CropTwoActivity.class));
            }
        });




    }





}