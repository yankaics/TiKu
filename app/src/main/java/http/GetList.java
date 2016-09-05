package http;

import android.os.Message;

import com.example.administrator.tiku.QuestionList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pojo.Question;

/**
 * Created by Administrator on 2016/9/1.
 */
public class GetList {
    List<Question> list = new ArrayList<Question>();
    public List<Question> getFenleiList(int i){
//        System.out.println(i+"***************************************");
        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
        params.addBodyParameter("catalogId", String.valueOf(i));
        x.http().post(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
//                System.out.println(result);
                list = jsonXList(result);
                for (int i =0;i<list.size();i++){
                    System.out.println(list.get(i).getContent()+"====================");
                    System.out.println(list.get(i).getTypeid()+"**********************");
                    System.out.println(list.get(i).getPubTime()+"+++++++++++++++++++++");
                }
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

    public List<Question> getTimuList(int i){

        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=findone");
        params.addBodyParameter("id", String.valueOf(i));
        x.http().get(params, new Callback.CacheCallback<String>() {

            @Override
            public void onSuccess(String result) {
                list = jsonList(result);
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
    public List<Question> getPrevList(int i){

        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=prev");
        params.addBodyParameter("id", String.valueOf(i));
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list = jsonList(result);
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
    public List<Question> getNextList(int i){

        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=next");
        params.addBodyParameter("id", String.valueOf(i));
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list = jsonList(result);
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
    public List<Question> getTimuTypeList(int i,int j){

        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
        params.addBodyParameter("catalogId", String.valueOf(i));
        params.addBodyParameter("type", String.valueOf(j));
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list = jsonXList(result);
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
    public List<Question> getMohuList(String s){


        RequestParams params = new RequestParams("http://115.29.136.118:8080/web-question/app/question?method=list");
        params.addBodyParameter("questionName", s);

        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

                list = jsonXList(result);
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
    public  List<Question> jsonList(String s){
//        System.out.println(s);
        List<Question> lists = new ArrayList<Question>();
        Question question;
        try {

            JSONArray jsonArray = new JSONArray(s);
            String options;
            for (int i = 0 ;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String content = jsonObject.getString("content");
                int id = jsonObject.getInt("id");

                int pubTime = jsonObject.getInt("pubTime");
                int typeid = jsonObject.getInt("typeid");
                String answer =jsonObject.getString("answer");
                int cataid = jsonObject.getInt("cataid");
                if (typeid == 1 || typeid ==2){
                    options= jsonObject.getString("options");
//                    System.out.println(options+"=======================================");
                }else{
                    options = "";
                }
                question = new Question(content,id,pubTime,typeid,answer,cataid,options);
//                System.out.println(content+id+pubTime+typeid+answer+cataid+options+"====================================");

                lists.add(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return lists;
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
        for (int i=0;i<lists.size();i++){
//            System.out.println(lists.get(i).getPubTime()+"@@@@@@@@@@@@@@@@@@@@@@@@");
        }

        return lists;
    }
}
