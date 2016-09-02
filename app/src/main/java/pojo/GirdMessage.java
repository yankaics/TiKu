package pojo;

/**
 * Created by Administrator on 2016/9/1.
 */
public class GirdMessage {
    private int id;
    private String icon;
    String name;

    public GirdMessage(int id, String icon, String name) {
        this.id = id;
        this.icon = icon;
        this.name = name;
    }

    public GirdMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
