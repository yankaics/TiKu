package com.example.administrator.tiku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class WelcomeActivity extends AppCompatActivity {
    private  static  final  int  GOTO_FRAGMENT_ACTIVITY = 0;
    private  static  final  int  GOTO_LOGIN_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_welcome,null);

        setContentView(view);
        ActionBar actionBar =getSupportActionBar();
//调用hide方法，隐藏actionbar
        actionBar.hide();
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(3000);
        view.startAnimation(aa);

        aa.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                boolean f = pref.getBoolean("falg",true);

                if (f){
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putBoolean("falg",false);
                    editor.commit();
                    mHandler.sendEmptyMessageDelayed(GOTO_FRAGMENT_ACTIVITY,2000);

                }else {
                    mHandler.sendEmptyMessageDelayed(GOTO_LOGIN_ACTIVITY,2000);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {}

        });
    }
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case GOTO_FRAGMENT_ACTIVITY:{
                    Intent intent = new Intent(WelcomeActivity.this,FragmentActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
                }
                break;
                case GOTO_LOGIN_ACTIVITY:{
                    Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
                }
                break;
            }
        }
    };

}
