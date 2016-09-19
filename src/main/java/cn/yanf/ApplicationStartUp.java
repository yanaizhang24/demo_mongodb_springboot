package cn.yanf;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.Repository.TieBarRepository;
import cn.yanf.entity.Customer;
import cn.yanf.webmagic.demo.BaiDUT;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class ApplicationStartUp implements ApplicationListener<ContextRefreshedEvent> {
        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
//            SourceRepository sourceRepository = event.getApplicationContext().getBean(SourceRepository.class);
//            Source je =new Source("justice_eternal吧","http://tieba.baidu.com/f?kw=justice_eternal");
//            sourceRepository.save(je);
            CustomerRepository repository=event.getApplicationContext().getBean(CustomerRepository.class);
            		repository.deleteAll();

		//save a couple of customers
		repository.save(new Customer("yan","feng"));
		repository.save(new Customer("li","pan"));

		//fetch all customer
		System.out.println("Customer find with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer :repository.findAll()){
			System.out.println(customer);
		}
		System.out.println();

		//fetch an individual customer
		System.out.println("Customer found with findByFirstName('yan'):");
		System.out.println("--------------------------------------------");
		System.out.println(repository.findByFirstName("yan"));

		System.out.println("Customer found with findByLastName('yan'):");
		System.out.println("--------------------------------------------");
		for(Customer customer:repository.findByLastName("pan")){
            System.out.println(customer);
        }

//        TieBarRepository tieBarRepository=event.getApplicationContext().getBean(TieBarRepository.class);
//            BaiDUT pp=new BaiDUT();
//		pp.setTieBarRepository(tieBarRepository);
//		pp.main(new String[]{});

        }

}
