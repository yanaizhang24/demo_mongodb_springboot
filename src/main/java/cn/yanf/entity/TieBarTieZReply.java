package cn.yanf.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/24 0024.
 */
public class TieBarTieZReply implements Serializable {
    private String author;
    private String href;
    private String content;

    public TieBarTieZReply(String author, String href, String content) {
        this.author = author;
        this.href = href;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TieBarTieZReply{" +
                "author='" + author + '\'' +
                ", href='" + href + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
