package com.example.administrator.tiku;


import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;


import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
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
    TextView tv_shangyiti,tv_xiayiti,tv_shoucang;
    int qusId;
    int idIdList;
    int listSize;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();
        question = (Question) intent.getSerializableExtra("question");
        idIdList = intent.getIntExtra("idInList",0);
        listSize = intent.getIntExtra("listsize",0);
        qusId = question.getId();

        vp_viewPager = (MyViewPager) findViewById(R.id.vp_viewPager);
        tv_shangyiti = (TextView) findViewById(R.id.tv_shangyiti);
        tv_xiayiti = (TextView) findViewById(R.id.tv_xiayiti);
        tv_shoucang = (TextView) findViewById(R.id.tv_shoucang);

        tv_shangyiti.setOnClickListener(this);
        tv_xiayiti.setOnClickListener(this);
        tv_shoucang.setOnClickListener(this);

        list = new ArrayList<Fragment>();
        fragment_12 = new Fragment_12();
        fragment_34 = new Fragment_34();

        list.add(fragment_12);
        list.add(fragment_34);
        adapter = new VpAdapter(getSupportFragmentManager(),list);
        vp_viewPager.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.question_topbar);
        toolbar.setTitle("第"+(idIdList+1)+"/"+listSize+"题");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF")); //设置标题颜色
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
                question.setPubTime(jsonObject.getLong("pubTime"));
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
                if (idIdList<1){
                    Toast.makeText(QuestionActivity.this,"已经是第一题",Toast.LENGTH_SHORT).show();
                }else {
                    getPrevList(qusId);
                    idIdList--;
                    toolbar.setTitle("第"+(idIdList+1)+"/"+listSize+"题");



                }


            }
            break;
            case R.id.tv_xiayiti:{
                    if (idIdList>listSize-2){
                        Toast.makeText(QuestionActivity.this,"已经是最后一题",Toast.LENGTH_SHORT).show();
                    }else {
                        getNextList(qusId);
                        idIdList++;
                        toolbar.setTitle("第"+(idIdList+1)+"/"+listSize+"题");



                    }


            }
            break;
            case R.id.tv_shoucang:{
                shouCang(2,qusId);
            }
            break;
        }
    }
    private void Change(){
//        System.out.println(question.getContent());
        System.out.println(vp_viewPager.getCurrentItem()+"=========");
        if (question.getTypeid()==1||question.getTypeid()==2){
            if (vp_viewPager.getCurrentItem()==0){
                vp_viewPager.setCurrentItem(1);
            }
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




        }else {
            if (vp_viewPager.getCurrentItem()==1){
                vp_viewPager.setCurrentItem(0);
                System.out.println("执行到了");

            }
            System.out.println(question.getContent());
                fragment_12.SetTitle(question.getContent());
                fragment_12.SetAsk(question.getAnswer());




        }



    }
    public void shouCang(int i,int j){

        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/mng/store?method=add");
        params.addBodyParameter("userId", String.valueOf(i));
        params.addBodyParameter("questionId", String.valueOf(i));

        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    boolean success = jsonObject.getBoolean("success");
                    if (success){
                        Toast.makeText(QuestionActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    }else{
                        String reason = jsonObject.getString("reason");
                        Toast.makeText(QuestionActivity.this,reason,Toast.LENGTH_SHORT).show();
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
