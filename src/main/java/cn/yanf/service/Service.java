package cn.yanf.service;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.Repository.TieBarRepository;
import cn.yanf.entity.AlibabaEN;
import cn.yanf.entity.Customer;
import cn.yanf.entity.TieBar;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
@org.springframework.stereotype.Service("Service")
public class Service  {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private TieBarRepository tieBarRepository;
    public List<Customer> findAll(){
        return repository.findAll();
    }
    public List<TieBar> findAllForTieBar(){
        return tieBarRepository.findAll();
    }
}
