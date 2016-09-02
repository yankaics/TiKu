package com.example.administrator.tiku;

import android.icu.text.BreakIterator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_zhuce)
public class ZhuceActivity extends AppCompatActivity implements View.OnClickListener{
    @ViewInject(R.id.ed_zhuce_username)
    private EditText ed_username;

    @ViewInject(R.id.ed_zhuce_pwd)
    private EditText ed_pwd;

    @ViewInject(R.id.ed_zhuce_nickname)
    private EditText ed_nickname;

    @ViewInject(R.id.ed_zhuce_phone)
    private EditText ed_phone;

    @ViewInject(R.id.btn_zhuce_ok)
    private Button btn_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setTitle("注册账号");

        btn_ok.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_zhuce_ok:{
                ZhuceClick();
            }
            break;
        }
    }
    private void ZhuceClick(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/registe");
        params.addBodyParameter("username",ed_username.getText().toString());
        if (ed_username.getText().length()<4){
            Toast.makeText(ZhuceActivity.this,"用户名只能用字母以及3位以上",Toast.LENGTH_SHORT).show();

        }else {
            params.addBodyParameter("password", ed_pwd.getText().toString());
            if (ed_pwd.getText().toString().length() < 4) {
                Toast.makeText(ZhuceActivity.this, "密码需要4位以上", Toast.LENGTH_SHORT).show();
            }else{
                params.addBodyParameter("nickname",ed_nickname.getText().toString());
                params.addBodyParameter("telephone",ed_phone.getText().toString());

                x.http().post(params, new Callback.CacheCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            Boolean success = jsonObject.getBoolean("success");
                            System.out.println(jsonObject.toString()+"================");
                            if (success){
                                Toast.makeText(ZhuceActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            }else{
                                String reason = jsonObject.getString("reason");
                                Toast.makeText(ZhuceActivity.this,reason,Toast.LENGTH_SHORT).show();
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

                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }
                });
            }
        }

    }
}
