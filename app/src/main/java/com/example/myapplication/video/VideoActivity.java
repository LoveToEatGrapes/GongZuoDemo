package com.example.myapplication.video;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.myapplication.R;

public class VideoActivity extends AppCompatActivity {


    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = findViewById(R.id.video);


        video.setVideoURI(Uri.parse("android.resource://com.example.myapplication/"+R.raw.ship));


        video.start();




    }





}