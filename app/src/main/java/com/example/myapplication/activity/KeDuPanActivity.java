package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.example.myapplication.R;
import com.example.myapplication.View.kedupan.CircleView;
import com.example.myapplication.View.kedupan.DashboardView;
import com.example.myapplication.View.kedupan.DashboardView1;
import com.example.myapplication.View.kedupan.DashboardView2;
import com.example.myapplication.View.kedupan.DashboardView3;
import com.example.myapplication.View.kedupan.DashboardView4;
import com.example.myapplication.View.kedupan.DegreeView;
import com.example.myapplication.View.kedupan.HighlightCR;
import com.example.myapplication.View.kedupan.NoiseboardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeDuPanActivity extends AppCompatActivity implements View.OnClickListener {

    private NoiseboardView noiseboardView;
    private CircleView circleView;
    private DegreeView dashBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        noiseboardView = findViewById(R.id.noiseboardView);


        findViewById(R.id.bt_anniu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int i = random.nextInt(150);
                noiseboardView.setRealTimeValue(i);
            }
        });

//--------------------------start-----------------------------------------------------------------------------
        mDashboardView1 = (DashboardView1) findViewById(R.id.dashboard_view_1);
        mDashboardView2 = (DashboardView2) findViewById(R.id.dashboard_view_2);
        mDashboardView3 = (DashboardView3) findViewById(R.id.dashboard_view_3);
        mDashboardView4 = (DashboardView4) findViewById(R.id.dashboard_view_4);
        mDashboardView1.setOnClickListener(this);
        mDashboardView2.setOnClickListener(this);
        mDashboardView3.setOnClickListener(this);
        mDashboardView4.setOnClickListener(this);

        mDashboardView2.setCreditValueWithAnim(new Random().nextInt(600) + 350);

//----------------------------end---------------------------------------------------------------------------



//--------------------------start-----------------------------------------------------------------------------

        circleView = (CircleView)findViewById(R.id.cicleview);
        circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleView.changeAngle(200);
            }
        });

        circleView.setOnAngleColorListener(new CircleView.OnAngleColorListener() {
            @Override
            public void colorListener(int red, int green) {

            }
        });


//----------------------------end---------------------------------------------------------------------------



//--------------------------start-----------------------------------------------------------------------------

        final DashboardView dashboardView1 = (DashboardView) findViewById(R.id.dashboard_view_22);
        DashboardView dashboardView3 = (DashboardView) findViewById(R.id.dashboard_view_33);
        DashboardView dashboardView4 = (DashboardView) findViewById(R.id.dashboard_view_44);
        dashboardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardView1.setRealTimeValue(150.f, true, 100);
            }
        });

        List<HighlightCR> highlight1 = new ArrayList<>();
        highlight1.add(new HighlightCR(210, 60, Color.parseColor("#03A9F4")));
        highlight1.add(new HighlightCR(270, 60, Color.parseColor("#FFA000")));
        dashboardView1.setStripeHighlightColorAndRange(highlight1);

        List<HighlightCR> highlight2 = new ArrayList<>();
        highlight2.add(new HighlightCR(170, 140, Color.parseColor("#607D8B")));
        highlight2.add(new HighlightCR(310, 60, Color.parseColor("#795548")));
        dashboardView3.setStripeHighlightColorAndRange(highlight2);

        dashboardView4.setRadius(110);
        dashboardView4.setArcColor(getResources().getColor(android.R.color.black));
        dashboardView4.setTextColor(Color.parseColor("#212121"));
        dashboardView4.setBgColor(getResources().getColor(android.R.color.white));
        dashboardView4.setStartAngle(150);
        dashboardView4.setPointerRadius(80);
        dashboardView4.setCircleRadius(8);
        dashboardView4.setSweepAngle(240);
        dashboardView4.setBigSliceCount(12);
        dashboardView4.setMaxValue(240);
        dashboardView4.setRealTimeValue(80);
        dashboardView4.setMeasureTextSize(14);
        dashboardView4.setHeaderRadius(50);
        dashboardView4.setHeaderTitle("km/h");
        dashboardView4.setHeaderTextSize(16);
        dashboardView4.setStripeWidth(20);
        dashboardView4.setStripeMode(DashboardView.StripeMode.OUTER);
        List<HighlightCR> highlight3 = new ArrayList<>();
        highlight3.add(new HighlightCR(150, 100, Color.parseColor("#4CAF50")));
        highlight3.add(new HighlightCR(250, 80, Color.parseColor("#FFEB3B")));
        highlight3.add(new HighlightCR(330, 60, Color.parseColor("#F44336")));
        dashboardView4.setStripeHighlightColorAndRange(highlight3);



//----------------------------end---------------------------------------------------------------------------




//--------------------------start-----------------------------------------------------------------------------

        dashBoard = findViewById(R.id.dash_progress);
        dashBoard.setMax(10000);
        dashBoard.setValue(9000);


//----------------------------end---------------------------------------------------------------------------





    }

    public void changeValue(View view) {
        dashBoard.setValue(new Random().nextInt(10001));

    }

    private void init() {

        View rootView = findViewById(R.id.rl_root);

        RotateAnimation rotateAnima = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnima.setDuration(1000);// 设置动画持续时间
        rotateAnima.setFillAfter(true);// 设置动画执行完毕时, 停留在完毕的状态下.

        ScaleAnimation scaleAnima = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnima.setDuration(1000);
        scaleAnima.setFillAfter(true);

        AlphaAnimation alphaAnima = new AlphaAnimation(0, 1);
        alphaAnima.setDuration(2000);
        alphaAnima.setFillAfter(true);


        // 把三个动画合在一起, 组成一个集合动画
        AnimationSet setAnima = new AnimationSet(false);
        setAnima.addAnimation(rotateAnima);
        setAnima.addAnimation(scaleAnima);
        setAnima.addAnimation(alphaAnima);

        rootView.startAnimation(setAnima);
    }




//================二============================================================================================================

    private DashboardView1 mDashboardView1;
    private DashboardView2 mDashboardView2;
    private DashboardView3 mDashboardView3;
    private DashboardView4 mDashboardView4;

    private boolean isAnimFinished = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dashboard_view_1:
                mDashboardView1.setRealTimeValue(new Random().nextInt(100));

                break;
            case R.id.dashboard_view_2:
                mDashboardView2.setCreditValueWithAnim(new Random().nextInt(950 - 350) + 350);

                break;
            case R.id.dashboard_view_3:
                mDashboardView3.setCreditValue(new Random().nextInt(950 - 350) + 350);

                break;
            case R.id.dashboard_view_4:
                if (isAnimFinished) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(mDashboardView4, "mRealTimeValue",
                            mDashboardView4.getVelocity(), new Random().nextInt(180));
                    animator.setDuration(1500).setInterpolator(new LinearInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimFinished = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimFinished = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isAnimFinished = true;
                        }
                    });
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            mDashboardView4.setVelocity(value);
                        }
                    });
                    animator.start();
                }

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}