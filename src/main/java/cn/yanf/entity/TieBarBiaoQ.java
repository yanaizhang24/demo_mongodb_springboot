package cn.yanf.entity;

import org.springframework.data.annotation.Id;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class TieBarBiaoQ extends TieBar{
    @Id
    private String id;
    private String url;

}
