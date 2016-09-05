package com.example.administrator.tiku;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import collector.ActivityCollector;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextView tv_nichengshezhi,tv_bianhuadenglu,tv_bianhuatupian,tv_tuichudenglu,tv_qingchuhuancun,tv_guanyuwomen;
    private CheckBox cb_tupian,cb_denglu;
    boolean b,f;

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
        toolbar = (Toolbar) findViewById(R.id.tl_custom);

        toolbar.setTitle("设置");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF")); //设置标题颜色
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_nichengshezhi = (TextView) findViewById(R.id.tv_nichengshezhi);
        tv_bianhuatupian = (TextView) findViewById(R.id.tv_bianhuatupian);
        tv_bianhuadenglu = (TextView) findViewById(R.id.tv_bianhuadenglu);
        tv_tuichudenglu = (TextView) findViewById(R.id.tv_tuichudenglu);
        tv_qingchuhuancun = (TextView) findViewById(R.id.tv_qingchuhuancun);
        tv_guanyuwomen = (TextView) findViewById(R.id.tv_guanyuwomen);
        cb_denglu = (CheckBox) findViewById(R.id.cb_denglu);
        cb_tupian = (CheckBox) findViewById(R.id.cb_tupian);


        tv_guanyuwomen.setOnClickListener(this);
        tv_qingchuhuancun.setOnClickListener(this);
        tv_tuichudenglu.setOnClickListener(this);
        tv_nichengshezhi.setOnClickListener(this);
        cb_denglu.setOnClickListener(this);
        cb_tupian.setOnClickListener(this);


        SharedPreferences pref = getSharedPreferences("zddl",MODE_PRIVATE);
        b = pref.getBoolean("zddl",true);
        cb_denglu.setChecked(b);
        SharedPreferences pref2 = getSharedPreferences("zddl",MODE_PRIVATE);
        f = pref.getBoolean("xstp",true);
        cb_tupian.setChecked(f);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return  true;
            }
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_nichengshezhi:{

            }
            break;
            case R.id.tv_tuichudenglu:{

            }
            break;
            case R.id.tv_qingchuhuancun:{

            }
            break;
            case R.id.tv_guanyuwomen:{

            }
            break;
            case R.id.cb_tupian:{
                SharedPreferences pref = getSharedPreferences("xstp",MODE_PRIVATE);
                b = pref.getBoolean("xstp",true);
                if (b){
                    SharedPreferences.Editor editor = getSharedPreferences("xstp",MODE_PRIVATE).edit();
                    editor.putBoolean("xstp",false);
                    editor.commit();
                    cb_tupian.setChecked(false);
                    tv_bianhuatupian.setText("仅通过WIFI");
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("xstp",MODE_PRIVATE).edit();
                    editor.putBoolean("xstp",true);
                    editor.commit();
                    cb_tupian.setChecked(true);
                    tv_bianhuatupian.setText("在3G/4G和WIFI下都显示图片");

                }
            }
            break;
            case R.id.cb_denglu:{
                SharedPreferences pref = getSharedPreferences("zddl",MODE_PRIVATE);
                 b = pref.getBoolean("zddl",true);
                if (b){
                    SharedPreferences.Editor editor = getSharedPreferences("zddl",MODE_PRIVATE).edit();
                    editor.putBoolean("zddl",false);
                    editor.commit();
                    cb_denglu.setChecked(false);
                    tv_bianhuadenglu.setText("未开启");
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("zddl",MODE_PRIVATE).edit();
                    editor.putBoolean("zddl",true);
                    editor.commit();
                    cb_denglu.setChecked(true);
                    tv_bianhuadenglu.setText("已开启");
                }
            }
            break;



        }

    }
}
