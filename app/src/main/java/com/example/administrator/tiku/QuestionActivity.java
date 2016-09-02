package com.example.administrator.tiku;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


import adapter.QuestionAdapter;
import pojo.Question;

public class QuestionActivity extends AppCompatActivity {
    Question question;
    Question question2;
    Question question1;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        question = getTimuQuestion(id);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1: {
                        System.out.println(question.getContent() + "xxxxxxxxxxxxxx");
                    }


                    break;

                }

            }


        };
    }


    public Question getTimuQuestion(int i){
        question2 = new Question();
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=findone");
        params.addBodyParameter("id", String.valueOf(i));
        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                question2 = jsonList(result);

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
        Message message = new Message();
        message.what = 1;
        QuestionList.handler.sendMessage(message);

        return question2;
    }
    public  Question jsonList(String s){
//        System.out.println(s);

        question1 = new Question();
        try {


            String options;
                JSONObject jsonObject = new JSONObject(s);
                String content = jsonObject.getString("content");
//            System.out.println(content+"&&&&&&&&&&&&&&&&&&&&&&&&");
                int id = jsonObject.getInt("id");
//            System.out.println(id+"&&&&&&&&&&&&&&&&&&&&&&&&");
                int pubTime = jsonObject.getInt("pubTime");
//            System.out.println(pubTime+"&&&&&&&&&&&&&&&&&&&&&&&&");
                int typeid = jsonObject.getInt("typeid");
//            System.out.println(typeid+"&&&&&&&&&&&&&&&&&&&&&&&&");
                String answer =jsonObject.getString("answer");
//            System.out.println(answer+"&&&&&&&&&&&&&&&&&&&&&&&&");
                int cataid = jsonObject.getInt("cataid");
//            System.out.println(cataid+"&&&&&&&&&&&&&&&&&&&&&&&&");
                if (typeid == 1 || typeid ==2){
                    options= jsonObject.getString("options");
//                    System.out.println(options+"=======================================");
                }else{
                    options = "";
                }
//            System.out.println(options+"&&&&&&&&&&&&&&&&&&&&&&&&");
                question1 = new Question(content,id,pubTime,typeid,answer,cataid,options);
//                System.out.println(content+id+pubTime+typeid+answer+cataid+options+"====================================");



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return question1;
    }

}
