package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edmodo.cropper.CropImageView;
import com.example.myapplication.R;

public class CropTwoActivity extends AppCompatActivity {


    private CropImageView crop_image_view;
    private ImageView iv;
    private TextView tv_crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_two);


        iv = findViewById(R.id.iv);
//        iv.setImageResource(R.mipmap.butterfly);

        tv_crop = findViewById(R.id.tv_crop);
        crop_image_view = findViewById(R.id.crop_image_view);
//        crop_image_view.setImageResource(R.mipmap.butterfly);


        tv_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bitmap croppedImage = crop_image_view.getCroppedImage();
                iv.setImageBitmap(croppedImage);
            }
        });

    }
}