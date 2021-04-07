package com.example.myapplication.activity.activity_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class BActivity extends AppCompatActivity {

    Button bt_b;
    Button bt_close;
    TextView tv_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        bt_b = findViewById(R.id.bt_b);
        bt_close = findViewById(R.id.bt_close);
        tv_value = findViewById(R.id.tv_value);

        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(111);
                finish();

            }
        });

        bt_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(BActivity.this,CActivity.class);
                startActivityForResult(intent, 111);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 111){
            setResult(111);
            Log.e("TAG","我是b");
            finish();
        }
    }
}