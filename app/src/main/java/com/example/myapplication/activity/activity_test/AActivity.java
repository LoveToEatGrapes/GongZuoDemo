package com.example.myapplication.activity.activity_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class AActivity extends AppCompatActivity {


    Button bt_a;
    TextView tv_value;
    String s = "0000(%s)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        bt_a = findViewById(R.id.bt_a);
        tv_value = findViewById(R.id.tv_value);
        tv_value.setText("诶呀你来" + s);
        bt_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AActivity.this,BActivity.class);
                startActivityForResult(intent,111);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 111){
            tv_value.setText("11诶呀我去 我是a");
        }
    }
}