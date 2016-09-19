package cn.yanf.webmagic.demo;

import cn.yanf.Repository.TieBarRepository;
import cn.yanf.entity.AlibabaEN;
import cn.yanf.entity.TieBar;
import cn.yanf.entity.TieBarBiaoQ;
import cn.yanf.entity.TieBarTieZ;
import cn.yanf.webmagic.piplline.MongodbPipeline;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.management.JMException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class Alibaba implements PageProcessor{



        private Site site = Site.me().setRetryTimes(3).setSleepTime(500);
        public static final String URL_LIST = "http://tieba\\.baidu\\.com/f\\?kw=.*";
        public static  int count=0;
        private static TieBarRepository repository;

        public TieBarRepository getRepository() {
            return repository;
        }

        public void setTieBarRepository(TieBarRepository repository) {
            this.repository = repository;
        }
        public void process(Page page) {
            //ico-narr <span class="ico-narr"></span>
            List<AlibabaEN> list=new ArrayList<>() ;
            for(Selectable s:page.getHtml().xpath("//div[@class='item-main item-main-180 util-clearfix']").nodes()){
                if(s.xpath("//span[@class='ico-narr']").nodes().size()>0){
                    int i =1;
                    for(Selectable x:s.xpath("//span[@class='ico-narr']").nodes())
                    {
                        page.putField("page",page.getUrl());
                        page.putField("newTarget"+i,s.xpath("//div[@class='lwrap']/h2/a/@href"));
                        //page.putField("newTargetTitle"+i,s.xpath("//div[@class='lwrap']/h2/a/@href"));
                        AlibabaEN al=new AlibabaEN(page.getUrl().toString(),s.xpath("//div[@class='lwrap']/h2/a/@href").toString());
                        list.add(al);
                    }
                }
            }
            if(list.size()>0){
                page.putField("list_ali",list);
            }
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
            for(String s:page.getHtml().xpath("//div[@class='ui2-pagination-pages']//a").links().all())
            {
                s=s.replaceFirst("/products/fishing","");
                page.addTargetRequest(s);
            }

        }

        public Site getSite() {
            return site;
        }
        public static void main(String[] args) {


            //Spider git=Spider.create(new BaiDUT()).addUrl("http://tieba.baidu.com/f?kw=%E7%BD%91%E7%BB%9C%E7%88%AC%E8%99%AB&ie=utf-8").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("D:\\webmagic\\"));
            Spider git=Spider.create(new cn.yanf.webmagic.demo.Alibaba()).addUrl("http://offer.alibaba.com/products/fishing/3.html?spm=a2700.7906341.16.5.khLdcc").
                    addPipeline(new ConsolePipeline()).addPipeline(new MongodbPipeline(repository))
                    //.addPipeline(new FilePipeline("D:\\webmagic\\"))
                    ;

            try {
                SpiderMonitor.instance().register(git);
            } catch (JMException e) {
                e.printStackTrace();
            }
            git.thread(5).run();
        }

        //test selenium
        public void testSelenium() {
            System.getProperties().setProperty("webdriver.chrome.driver", "E:\\yanf_workspace\\demo_mongodb_springboot\\src\\main\\resources\\chromedriver");
            WebDriver webDriver = new ChromeDriver();
            webDriver.get("http://huaban.com/");
            WebElement webElement = webDriver.findElement(By.xpath("/html"));
            System.out.println(webElement.getAttribute("outerHTML"));
            webDriver.close();
        }
    }


