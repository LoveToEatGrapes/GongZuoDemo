package com.example.myapplication.WLM;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.View.kedupan.PriceSystemDashboardView;

import java.util.Random;

public class WlmqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlmq);

        final DashBoard d = (DashBoard) findViewById(R.id.dash);
        final PriceSystemDashboardView psdv = (PriceSystemDashboardView) findViewById(R.id.psdv);
        final TextView tv_rand = findViewById(R.id.tv_rand);

        findViewById(R.id.rand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int max = 150;
                int min = 1;
                Random random = new Random();
                int p = random.nextInt(max) % (max - min + 1) + min;
                d.cgangePer(p / 120f);

                int i = new Random().nextInt(10);
                psdv.setRealTimeValue(i);
                tv_rand.setText(i+"");

            }
        });

        findViewById(R.id.retu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cgangePer(0);
                psdv.setRealTimeValue(0);
                tv_rand.setText("0");
            }
        });
    }
}