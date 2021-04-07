package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.example.myapplication.R


class AonTouchActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aon_touch)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("TAG","=====================嘿嘿=xxx:" )
//        return super.onTouchEvent(event)
        return true
    }

}