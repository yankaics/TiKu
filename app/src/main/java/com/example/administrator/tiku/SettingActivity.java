package com.example.administrator.tiku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import collector.ActivityCollector;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActivityCollector.addActivity(this);
    }
}
