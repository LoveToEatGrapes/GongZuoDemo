package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailActivity extends AppCompatActivity {

    private EditText et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        et_email = findViewById(R.id.et_email);

        findViewById(R.id.tv_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = setCheck();
                boolean b1 = setCheckTwo();
                boolean b2 = setCheckThree();
                Log.e("TAG","1111111111111111==" + b + "==222==" + b1 + "==333="+setCheckThree());
            }
        });


    }


    private boolean setCheck(){
        String s1 = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        String s2 = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";

        boolean matches = et_email.getText().toString().matches(s1);
        return matches;
    }


    private boolean setCheckTwo(){
        String s1 = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,4}";
        String s2 = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";

        Matcher matcher = Pattern.compile(s2).matcher(et_email.getText().toString());
        return matcher.matches();
    }

    private boolean setCheckThree(){
        String s2 = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        Matcher matcher = Pattern.compile(s2).matcher(et_email.getText().toString());
        return matcher.matches();
    }

    /**
     * 教研邮箱
     * @param content
     * @return
     */
    public static boolean checkEmail(String content){
        String s1 = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        boolean matches = content.matches(s1);
        return matches;
    }

}