package cn.yanf.service;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.Repository.TieBarRepository;
import cn.yanf.webmagic.demo.BaiDUT;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
@org.springframework.stereotype.Service
public class StartService implements InitializingBean {

    @Autowired
    private CustomerRepository repository;
    @Autowired
    private TieBarRepository tieBarrepository;
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out .println ("start baidu ----------------------------" );

    }


}

