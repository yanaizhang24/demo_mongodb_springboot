package cn.yanf.entity;

import org.springframework.data.annotation.Id;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class TieBarBiaoQ extends TieBar{
    @Id
    private String id;
    private String number;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TieBarBiaoQ(String number, String url) {
        this.number = number;
        this.url = url;
    }
}
