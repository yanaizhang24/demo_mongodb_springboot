package cn.yanf.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class AlibabaEN extends TieBar implements Serializable{
    @Id
    private String id;
    private String url;
    private String target;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public AlibabaEN(String url, String target) {
        this.url = url;
        this.target = target;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id +"\""+
                ",\"url\":\"" + url + '\"' +
                ",\"target\":\"" + target + '\"' +
                '}';
    }
}
