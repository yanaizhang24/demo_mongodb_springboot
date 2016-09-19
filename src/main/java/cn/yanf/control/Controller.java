package cn.yanf.control;

import cn.yanf.StringUtils.SpringUtils;
import cn.yanf.entity.AlibabaEN;
import cn.yanf.entity.Customer;
import cn.yanf.entity.TieBar;
import cn.yanf.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
@RestController
@RequestMapping("/control")
@EnableAutoConfiguration
public class Controller {
    @Autowired
    //@Qualifier("veService")
    private Service service;
    @RequestMapping(value="/getAllVes1")
    public List<Customer> getAllVes(){

        List<Customer> list=service.findAll();
        //List<VePeSdto> list2=new ArrayList<VePeSdto>();
        list.add(new Customer("count",list.size()+""));

        //List<VePeSdto> list2=new ArrayList<VePeSdto>();
        // list.add(new Customer("count",list.size()+""));
        return list;
    }
    @RequestMapping(value="/getAllForT")
    @ResponseBody
    public List<AlibabaEN> getAllForT(){
        List list=new ArrayList();
        list=service.findAllForTieBar();
        List<AlibabaEN> list_ali=new ArrayList<>();
        for(Object o:list){
            try{
                AlibabaEN al=(AlibabaEN) o;
                list_ali.add(al);
            }catch(Exception e){

            }
        }
        SpringUtils.writeData(list_ali,"ali.txt");
        //List<VePeSdto> list2=new ArrayList<VePeSdto>();
       // list.add(new Customer("count",list.size()+""));
        return list_ali;
    }
}
