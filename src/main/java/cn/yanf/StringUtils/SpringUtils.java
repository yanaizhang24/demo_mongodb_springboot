package cn.yanf.StringUtils;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
@Component
public class SpringUtils implements Serializable,CommandLineRunner {
    @Autowired
    private CustomerRepository repository;
    private static String message;
    public  static void getMessage (String message) throws Exception {
        SpringUtils.message=message;
    }

    @Override
    public void run(String... strings) throws Exception {
        if(strings.length>0)
            repository.save(new Customer("102160613134951192168402401009",strings[0]));
    }
}
