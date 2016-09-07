package com.example.administrator.tiku;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import collector.ActivityCollector;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private TextView tv_nichengshezhi,tv_bianhuadenglu,tv_bianhuatupian,tv_tuichudenglu,tv_qingchuhuancun,tv_guanyuwomen;
    private CheckBox cb_tupian,cb_denglu;
    boolean b,f;
    SharedPreferences pref;
    ActivityCollector activityCollector;

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

        pref = getSharedPreferences("nickname",MODE_PRIVATE);

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
    EditText ed_setname;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_nichengshezhi:{
                AlertDialog.Builder builder = new AlertDialog.Builder((SettingActivity.this));
                builder.setIcon(R.mipmap.login_user_hightlighted);
                builder.setTitle("昵称设置");
                LayoutInflater inflater = LayoutInflater.from(this);
                View nickname = inflater.inflate(R.layout.dialogitem,null);
                builder.setView(nickname);
                ed_setname = (EditText) nickname.findViewById(R.id.ed_setname);
                String nick = pref.getString("nickname","qwer");
                ed_setname.setText(nick);
                builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getSharedPreferences("nickname",MODE_PRIVATE).edit();
                        editor.putString("nickname",ed_setname.getText().toString());
                        editor.commit();
                    }
                });
                builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
            break;
            case R.id.tv_tuichudenglu:{
                Intent i = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(i);

            }
            break;
            case R.id.tv_qingchuhuancun:{
                Toast.makeText(SettingActivity.this,"清除缓存",Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.tv_guanyuwomen:{
                Toast.makeText(SettingActivity.this,"刘立臣制作",Toast.LENGTH_SHORT).show();
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
