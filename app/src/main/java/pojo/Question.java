package pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/1.
 */
public class Question implements Serializable{
    String content;
    int id;
    long pubTime;
    int typeid;
    String answer;
    int cataid;
    String options;

    public Question() {
    }

    public Question(String content, int id, long pubTime, int typeid, String answer, int cataid, String options) {
        this.content = content;
        this.id = id;
        this.pubTime = pubTime;
        this.typeid = typeid;
        this.answer = answer;
        this.cataid = cataid;
        this.options = options;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPubTime() {
        return pubTime;
    }

    public void setPubTime(long pubTime) {
        this.pubTime = pubTime;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCataid() {
        return cataid;
    }

    public void setCataid(int cataid) {
        this.cataid = cataid;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
