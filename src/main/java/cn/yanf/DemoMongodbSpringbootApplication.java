package cn.yanf;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.Repository.TieBarBiaoQRepository;
import cn.yanf.Repository.TieBarRepository;
import cn.yanf.Repository.TieBarTieZRepository;
import cn.yanf.entity.Customer;
import cn.yanf.entity.TieBarBiaoQ;
import cn.yanf.webSocket.ExampleClient;
import cn.yanf.webmagic.demo.Alibaba;
import cn.yanf.webmagic.demo.BaiDUT;
import org.java_websocket.drafts.Draft_17;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class DemoMongodbSpringbootApplication implements CommandLineRunner{
	@Autowired
	private CustomerRepository repository;
	@Autowired
	private  TieBarRepository tieBarrepository;
	@Autowired
	private TieBarRepository<TieBarBiaoQ,String> tieBarRepository2;
	@Autowired
	private  TieBarBiaoQRepository tieBarBiaoQRepository;
	@Autowired
	private  TieBarTieZRepository tieBarTieZRepository;

	public static void main(String[] args) {
		SpringApplication springApplication=new SpringApplication(DemoMongodbSpringbootApplication.class);
		//springApplication.addListeners(new ApplicationStartUp());

		final ApplicationContext applicationContext=springApplication.run(args);
        BaiDUT pp=new BaiDUT();
        pp.setTieBarRepository(applicationContext.getBean(TieBarRepository.class));
        pp.setTieBarBiaoQRepository(applicationContext.getBean(TieBarBiaoQRepository.class));
        pp.setTieBarTieZRepository(applicationContext.getBean(TieBarTieZRepository.class));
        pp.main(new String[]{});

	}

	@Override
	public void run(String... strings) throws Exception {
		//tieBarBiaoQRepository.save(new TieBarBiaoQ("12","21131adsfas"));
//		repository.deleteAll();
//
//		//save a couple of customers
//		repository.save(new Customer("yan","feng"));
//		repository.save(new Customer("li","pan"));
//
//		//fetch all customer
//		System.out.println("Customer find with findAll():");
//		System.out.println("-------------------------------");
//		for (Customer customer :repository.findAll()){
//			System.out.println(customer);
//		}
//		System.out.println();
//
//		//fetch an individual customer
//		System.out.println("Customer found with findByFirstName('yan'):");
//		System.out.println("--------------------------------------------");
//		System.out.println(repository.findByFirstName("yan"));
//
//		System.out.println("Customer found with findByLastName('yan'):");
//		System.out.println("--------------------------------------------");
//		for(Customer customer:repository.findByLastName("pan"))
//			System.out.println(customer);
		//websocket
//		ExampleClient c = null; // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
//		try {
			//需要用到draft_17以上
//			c = new ExampleClient( new URI( "ws://192.168.40.240:8880/IoT_Harbor/websocketInterface" ), new Draft_17() );
//			c.setRepository(repository);
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		System.out.println("链接");
//		c.connect();

//        Alibaba ali=new Alibaba();
//        ali.setTieBarRepository(tieBarrepository);
//        ali.main(new String[]{});
	}
}
