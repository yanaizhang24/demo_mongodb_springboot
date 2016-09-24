package cn.yanf.entity;

import java.io.Serializable;

/**
 * 贴吧回复分页json数据
 * Created by Administrator on 2016/9/24 0024.
 */
public class BaiduPageNum implements Serializable{
    //总共回复数量
    private String total_num;
    //总共页数
    private String total_page;

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getTotal_page() {
        return total_page;
    }

    public void setTotal_page(String total_page) {
        this.total_page = total_page;
    }
}
