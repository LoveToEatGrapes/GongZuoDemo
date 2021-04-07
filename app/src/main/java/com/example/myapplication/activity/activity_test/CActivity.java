package com.example.myapplication.activity.activity_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class CActivity extends AppCompatActivity {

    Button bt_c;
    TextView tv_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        bt_c = findViewById(R.id.bt_c);
        tv_value = findViewById(R.id.tv_value);


        bt_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             setResult(111);
             finish();
            }
        });
    }
}