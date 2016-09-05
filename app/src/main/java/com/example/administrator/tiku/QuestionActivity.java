package com.example.administrator.tiku;


import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.QuestionAdapter;
import adapter.VpAdapter;
import fragment.Fragment_12;
import fragment.Fragment_34;
import pojo.Question;
import widget.MyViewPager;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{
    Question question = new Question();

    MyViewPager vp_viewPager;
    List<Fragment> list;
    Fragment_12 fragment_12;
    Fragment_34 fragment_34;
    VpAdapter adapter;
    Handler handler;
    TextView tv_shangyiti,tv_xiayiti;
    int qusId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();
        question = (Question) intent.getSerializableExtra("question");
        qusId = question.getId();
        vp_viewPager = (MyViewPager) findViewById(R.id.vp_viewPager);
        tv_shangyiti = (TextView) findViewById(R.id.tv_shangyiti);
        tv_xiayiti = (TextView) findViewById(R.id.tv_xiayiti);

        tv_shangyiti.setOnClickListener(this);
        tv_xiayiti.setOnClickListener(this);

        list = new ArrayList<Fragment>();
        fragment_12 = new Fragment_12();
        fragment_34 = new Fragment_34();

        list.add(fragment_12);
        list.add(fragment_34);
        adapter = new VpAdapter(getSupportFragmentManager(),list);
        vp_viewPager.setAdapter(adapter);

        init();
        setQuestion(question);


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:{
                        Change();
                    }
                    break;
                }
            }
        };





    }
    private void init(){
        if (question.getTypeid()==1||question.getTypeid()==2){
            vp_viewPager.setCurrentItem(1);


        }else {
            vp_viewPager.setCurrentItem(0);


        }

    }

    public void setQuestion(Question q) {
        if (question.getTypeid()==1||question.getTypeid()==2){
            String content = question.getContent();
            fragment_34.SetTitle(content);
            String options = question.getOptions();
            try {
                JSONArray jsonArray = new JSONArray(options);
//                System.out.println(options+"*******************************");
                for (int i =0;i<jsonArray.length();i++){
                    String title = jsonArray.getJSONObject(i).getString("title");

                    boolean checked = Boolean.parseBoolean(jsonArray.getJSONObject(i).getString("checked"));

                    if (i==0){
                        fragment_34.SetCheckBox1(checked,title);
                    }else if (i==1){
                        fragment_34.SetCheckBox2(checked,title);
                    }else if (i==2){
                        fragment_34.SetCheckBox3(checked,title);
                    }else if (i==3){
                        fragment_34.SetCheckBox4(checked,title);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {


            fragment_12.changeTitle(question.getContent());
            fragment_12.changeAsk(question.getAnswer());
//
        }
    }


    public void getPrevList(int i){
        System.out.println("id= "+i+"************************");
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=prev");
        params.addBodyParameter("id", String.valueOf(i));
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                jsonList(result);

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
    public void getNextList(int i){
        System.out.println("id= "+i+"************************");
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=next");
        params.addBodyParameter("id", String.valueOf(i));

        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                jsonList(result);

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
    public void jsonList(String s){
//        System.out.println(s);


        try {

                JSONObject jsonObject = new JSONObject(s);
                question.setContent(jsonObject.getString("content"));
                question.setId(jsonObject.getInt("id"));
                qusId = jsonObject.getInt("id");
                question.setPubTime(jsonObject.getInt("pubTime"));
                question.setTypeid(jsonObject.getInt("typeid"));
                question.setAnswer(jsonObject.getString("answer"));
                question.setCataid(jsonObject.getInt("cataid"));
                if (question.getTypeid() == 1 || question.getTypeid() ==2){
                    question.setOptions(jsonObject.getString("options"));
//                    System.out.println(options+"=======================================");
                }else{
                    question.setOptions("");
                }
            System.out.println(question.getContent()+"8888888888888888888888");
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);



        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_shangyiti:{
                if (qusId==0){

                }else {
                    getPrevList(qusId);




                }


            }
            break;
            case R.id.tv_xiayiti:{
                    if (qusId==10){

                    }else {
                        getNextList(qusId);




                    }


            }
            break;
        }
    }
    private void Change(){
        if (question.getTypeid()==1||question.getTypeid()==2){
            if (vp_viewPager.getCurrentItem()==1){
                fragment_34.changeTitle(question.getContent());
                String options = question.getOptions();
                try {
                    JSONArray jsonArray = new JSONArray(options);
//                System.out.println(options+"*******************************");
                    for (int i =0;i<jsonArray.length();i++){
                        String title = jsonArray.getJSONObject(i).getString("title");

                        boolean checked = Boolean.parseBoolean(jsonArray.getJSONObject(i).getString("checked"));

                        if (i==0){
                            fragment_34.changCheckBox1(checked,title);
                        }else if (i==1){
                            fragment_34.changCheckBox2(checked,title);
                        }else if (i==2){
                            fragment_34.changCheckBox3(checked,title);
                        }else if (i==3){
                            fragment_34.changCheckBox4(checked,title);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                vp_viewPager.setCurrentItem(1);
                fragment_34.changeTitle(question.getContent());
                String options = question.getOptions();
                try {
                    JSONArray jsonArray = new JSONArray(options);
//                System.out.println(options+"*******************************");
                    for (int i =0;i<jsonArray.length();i++){
                        String title = jsonArray.getJSONObject(i).getString("title");

                        boolean checked = Boolean.parseBoolean(jsonArray.getJSONObject(i).getString("checked"));

                        if (i==0){
                            fragment_34.changCheckBox1(checked,title);
                        }else if (i==1){
                            fragment_34.changCheckBox2(checked,title);
                        }else if (i==2){
                            fragment_34.changCheckBox3(checked,title);
                        }else if (i==3){
                            fragment_34.changCheckBox4(checked,title);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }else {
            if (vp_viewPager.getCurrentItem()==0){
                fragment_12.SetTitle(question.getContent());
                fragment_12.SetAsk(question.getAnswer());
            }else{
                vp_viewPager.setCurrentItem(0);
                fragment_12.changeTitle(question.getContent());
                fragment_12.changeAsk(question.getAnswer());
            }



        }



    }

}
