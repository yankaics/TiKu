package com.example.administrator.tiku;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.GirdAdapter;
import collector.ActivityCollector;
import http.GetList;
import pojo.GirdMessage;
import pojo.Question;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @ViewInject(R.id.ln_touxiang)
    private LinearLayout touxiang;

    @ViewInject(R.id.ln_fenlei)
    LinearLayout fenlei;

    @ViewInject(R.id.ln_chazhao)
    private LinearLayout chazhao;

    @ViewInject(R.id.ln_chengjiu)
    LinearLayout chengjiu;

    @ViewInject(R.id.ln_shoucang)
    LinearLayout shoucang;

    @ViewInject(R.id.tv_shezhi)
    private TextView shezhi;

    @ViewInject(R.id.tv_tuichu)
    private TextView tuichu;

    @ViewInject(R.id.test_gridview)
    private GridView girdview;
    @ViewInject(value = R.id.srl)
    private SwipeRefreshLayout srl;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private GirdMessage message;
    private List<GirdMessage> list = new ArrayList<GirdMessage>();
    private GirdAdapter adapter = new GirdAdapter(list,MainActivity.this);
    private Handler handler;
    GetList getList = new GetList();

















    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ActivityCollector.addActivity(this);

        getGirdList();

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:{
                        girdview.setAdapter(adapter);
                    }
                    break;
                }
            }
        };



        touxiang.setOnClickListener(this);
        fenlei.setOnClickListener(this);
        chazhao.setOnClickListener(this);
        chengjiu.setOnClickListener(this);
        shoucang.setOnClickListener(this);
        tuichu.setOnClickListener(this);
        shezhi.setOnClickListener(this);


        findViews(); //获取控件
        toolbar.setTitle("分类练习");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF")); //设置标题颜色
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        girdview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,QuestionList.class);
                intent.putExtra("leibie",list.get(position).getName());
                intent.putExtra("id",list.get(position).getId());
                startActivity(intent);
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);


            }
        });

        srl.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3,R.color.color4);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getGirdList();
                        srl.setRefreshing(false);
                    }
                }, 2000);


            }
        });


    }
    private void findViews() {

        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sousuo,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.im_sousuo:{
                //点击事件
                Intent intent = new Intent(MainActivity.this,SousuoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);

                return  true;
            }
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ln_touxiang:{
//                Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
//                intent2.setType("image/*");
//                startActivityForResult(intent2,3);

            }
            break;
            case R.id.ln_fenlei:{
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
                finish();
            }
            break;
            case R.id.ln_chazhao:{
                Intent intent = new Intent(MainActivity.this,SousuoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
//                Toast.makeText(MainActivity.this, "sousuo", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.ln_chengjiu:{

            }
            break;
            case R.id.ln_shoucang:{
                Intent intent = new Intent(MainActivity.this,QuestionList.class);
                intent.putExtra("leibie","收藏");
                startActivity(intent);
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
//                Toast.makeText(MainActivity.this, "sousuo", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.tv_shezhi:{
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_anim,R.anim.out_anim);
            }
            break;
            case R.id.tv_tuichu:{
                ActivityCollector.finishAll();
            }
            break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case 3:{
//                if (resultCode==RESULT_OK){
//                    handleImageOnKitKat(data);
//                }
//            }
//        }
//    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
//        String imagePath = null;
//        Uri uri = data.getData();
//        if (DocumentsContract.isDocumentUri(this,uri)){
//            String docId = DocumentsContract.getDocumentId(uri);
//            if (DocumentsContract)
//        }

    }

    private void getGirdList(){
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/catalog?method=list");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {

                    JSONArray jsonArray  = new JSONArray(result);
                    for (int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        int id = jsonObject.getInt("id");
                        String icon = jsonObject.getString("icon");
                        String name = jsonObject.getString("name");
                        message = new GirdMessage(id,icon,name);
                        list.add(message);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

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


