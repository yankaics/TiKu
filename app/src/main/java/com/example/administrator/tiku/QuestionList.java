package com.example.administrator.tiku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.QuestionAdapter;
import collector.ActivityCollector;
import http.GetList;
import pojo.Question;

public class QuestionList extends AppCompatActivity {

    private Toolbar toolbar;
    private GetList getList = new GetList();
    private List<Question> list;
    public static Handler handler;
    int cataid;
    QuestionAdapter adapter;
    ListView lv_question;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        ActivityCollector.addActivity(this);

        Intent intent = getIntent();
        String leibie = intent.getStringExtra("leibie");
        cataid = intent.getIntExtra("id",10);
//        System.out.println("cataid:"+cataid+"==========================");

        lv_question = (ListView) findViewById(R.id.lv_question);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        toolbar.setTitle(leibie);//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF")); //设置标题颜色
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        list = getFenleiList(cataid);





        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:{


                        adapter = new QuestionAdapter(list,QuestionList.this);
                        lv_question.setAdapter(adapter);
                    }
                    break;
                    case 2:{


                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                    break;
                }
            }
        };

        lv_question.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(QuestionList.this,QuestionActivity.class);

                intent1.putExtra("question",list.get(position));
                startActivity(intent1);
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
            }
        });
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
    public List<Question> getFenleiList(int i){
//        System.out.println(i+"***************************************");
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
        params.addBodyParameter("catalogId", String.valueOf(i));
        x.http().post(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
//                System.out.println(result);
                list = jsonXList(result);

                Message message = new Message();
                message.what = 2;
                QuestionList.handler.sendMessage(message);
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
        return list;
    }
    public  List<Question> jsonXList(String s){
//        System.out.println(s);
        List<Question> lists = new ArrayList<Question>();
        Question question;
        String options;
        try {
            JSONObject json = new JSONObject(s);
            String c = json.getString("content");
//            System.out.println(c);
            JSONArray jsonArray = new JSONArray(c);
            for (int i = 0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String content = jsonObject.getString("content");
//                System.out.println(content+"=======================================");
                int id = jsonObject.getInt("id");
//                System.out.println(id+"=======================================");
                int pubTime = jsonObject.getInt("pubTime");
//                System.out.println(pubTime+"=======================================");
                int typeid = jsonObject.getInt("typeid");
//                System.out.println(typeid+"=======================================");
                String answer =jsonObject.getString("answer");
//                System.out.println(answer+"=======================================");
                int cataid = jsonObject.getInt("cataid");
//                System.out.println(cataid+"=======================================");
                if (typeid == 1 || typeid ==2){
                    options= jsonObject.getString("options");
//                    System.out.println(options+"=======================================");
                }else{
                    options = "";
                }

                question = new Question(content,id,pubTime,typeid,answer,cataid,options);
//                System.out.println(content+id+pubTime+typeid+answer+cataid+"====================================");
                lists.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return lists;
    }
}
