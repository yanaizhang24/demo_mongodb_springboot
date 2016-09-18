package cn.yanf.entity;

import org.springframework.data.annotation.Id;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class TieBarTieZ extends TieBar{
    @Id
    private String id;
    private String url;
    private String title;
    private String author;
    private String content;
    private String href;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    public TieBarTieZ(){}

    public TieBarTieZ(String url, String title, String author, String content, String href) {
        this.url = url;
        this.title = title;
        this.author = author;
        this.content = content;
        this.href = href;
    }

    @Override
    public String toString() {
        return "TieBarTieZ{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
