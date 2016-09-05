package com.example.administrator.tiku;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import collector.ActivityCollector;
import dialog.LoadingDialog;

@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @ViewInject(R.id.ed_username)
    private EditText ed_username;

    @ViewInject(R.id.ed_pwd)
    private EditText ed_pwd;

    @ViewInject(R.id.btn_login)
    private Button btn_login;

    @ViewInject(R.id.tv_forget_pwd)
    private TextView tv_forget_pwd;

    @ViewInject(R.id.tv_register)
    private TextView tv_register;

    Handler handler;
    private LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ActivityCollector.addActivity(this);
        setTitle("登录");


        btn_login.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        tv_register.setOnClickListener(this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:{
                        finish();
                    }
                    break;
                }
            }
        };

        dialog = new LoadingDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:{
                dialog.show();
                LoginClick();

            }
            break;
            case R.id.tv_forget_pwd:{
                finish();
            }
            break;
            case R.id.tv_register:{
                Intent intent = new Intent(LoginActivity.this,ZhuceActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
            }
            break;
        }
    }
    private void LoginClick(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/login");
        params.addBodyParameter("username",ed_username.getText().toString());
        params.addBodyParameter("password",ed_pwd.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String arg0) {
//                System.out.println("===============================================");
                try {
//                    System.out.println(arg0+"================================================");


                        JSONObject jsonObject = new JSONObject(arg0);
                        Boolean success = jsonObject.getBoolean("success");
                        if (success){
                            String user = jsonObject.getString("user");
                            JSONObject json = new JSONObject(user);
                            int id = json.getInt("id");
                            String nickname = json.getString("nickname");
                            String password = json.getString("password");
                            String phone = json.getString("telephone");

                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);

                        }else{
                            String reason = jsonObject.getString("reason");
                            Toast.makeText(LoginActivity.this,reason,Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
