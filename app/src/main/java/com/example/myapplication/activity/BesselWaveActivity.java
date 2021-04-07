package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.View.DrawQuadToWaveView;

public class BesselWaveActivity extends AppCompatActivity {

    DrawQuadToWaveView drawQuadToWaveView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bessel_wave);

        drawQuadToWaveView = findViewById(R.id.id_draw_quad_to_wave);

    }

    public void wave(View view){
        drawQuadToWaveView.startAnim();
    }


}