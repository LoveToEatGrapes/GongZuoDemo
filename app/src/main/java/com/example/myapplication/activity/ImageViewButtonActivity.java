package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.View.img.ManHeadView;

public class ImageViewButtonActivity extends AppCompatActivity {

    private ManHeadView  mImgHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_button);

        mImgHead = findViewById(R.id.img_head);
        mImgHead.setOnBodyClickListener(new ManHeadView.OnBodyClickListener() {
            @Override public void onBodyClick(int position, String[] keys) {
                String message  ="Code:" + keys[0] + ", Name:" + keys[1];
                Toast.makeText(ImageViewButtonActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });


    }
}