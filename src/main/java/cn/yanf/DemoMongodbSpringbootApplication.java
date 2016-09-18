package cn.yanf;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.Repository.TieBarRepository;
import cn.yanf.entity.Customer;
import cn.yanf.webSocket.ExampleClient;
import cn.yanf.webmagic.demo.BaiDUT;
import org.java_websocket.drafts.Draft_17;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import us.codecraft.webmagic.processor.PageProcessor;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class DemoMongodbSpringbootApplication implements CommandLineRunner{
	@Autowired
	private CustomerRepository repository;
	@Autowired
	private TieBarRepository repositoryTieBar;

	public static void main(String[] args) {
		SpringApplication.run(DemoMongodbSpringbootApplication.class, args);


	}

	@Override
	public void run(String... strings) throws Exception {
		//repository.deleteAll();
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
		BaiDUT pp=new BaiDUT();
		pp.setTieBarRepository(repositoryTieBar);
		pp.main(new String[]{});
	}
}
