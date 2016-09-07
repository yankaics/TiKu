package com.example.administrator.tiku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

public class SousuoActivity extends AppCompatActivity implements View.OnClickListener{



    private EditText et_search;


    private TextView tv_search_btn;


    private ListView lv_question_sousuo;

    private  List<Question> mohuList = new ArrayList<Question>();
    Handler handler;

    private GetList getList = new GetList();
    private Toolbar toolbar;
    private QuestionAdapter adapter;
    private TextView tv_sousuoweibuju;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        ActivityCollector.addActivity(this);
        toolbar = (Toolbar) findViewById(R.id.sousuo_topbar);

        toolbar.setTitle("题目查找");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF")); //设置标题颜色
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_search = (EditText) findViewById(R.id.et_search);
        lv_question_sousuo = (ListView) findViewById(R.id.lv_question_sousuo);
        tv_search_btn = (TextView) findViewById(R.id.tv_search_btn);
        tv_sousuoweibuju = (TextView) findViewById(R.id.tv_sousuoweibuju);
        tv_search_btn.setOnClickListener(this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:{
                        adapter = new QuestionAdapter(mohuList,SousuoActivity.this);
                        lv_question_sousuo.setAdapter(adapter);
                        tv_sousuoweibuju.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }
        };

        lv_question_sousuo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(SousuoActivity.this,QuestionActivity.class);

                intent1.putExtra("question",mohuList.get(position));
                intent1.putExtra("idInList",position);
                intent1.putExtra("listsize",mohuList.size());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_search_btn:{
                mohuList.clear();
                String s = et_search.getText().toString();
                if (s == null){
                    s = "";
                }
                getMohuList(s);
                System.out.println("zhixingdaole1111");


            }
            break;
        }


    }
    public void getMohuList(String s){


        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
        params.addBodyParameter("questionName", s);

        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                jsonXList(result);
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
    public  void jsonXList(String s){
//        System.out.println(s);

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
                long pubTime = jsonObject.getLong("pubTime");
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
                mohuList.add(question);

                System.out.println(question.getContent());
            }
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
