package cn.yanf.webmagic.demo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.management.JMException;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class WebMagicForJava implements PageProcessor{
    private Site site = Site.me().setSleepTime(5000);
    public void process(Page page) {
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        ////*[@id="js-pjax-container"]/div[2]/div/div[2]/ul/li[6]/h3/a
        ////*[@id="js-pjax-container"]/div[2]/div/div[2]/ul/li[7]/h3/a
        ////*[@id="js-pjax-container"]/div[2]/div/div[2]/ul/li[1]/h3
        Selectable pageS=page.getHtml().xpath("//*[@id=\"js-pjax-container\"]/div[2]/div/div[2]/ul/li");
        int i=0;
        for(Selectable s:pageS.nodes()){
            page.putField("title"+i, s.xpath("//h3[@class='repo-list-name']/a/text()").toString());
            page.putField("url"+i, s.xpath("//h3[@class='repo-list-name']/a/@href").toString());
            page.putField("description"+i++,s.xpath("//p[@class='repo-list-description']/text()").toString());
        }
//        if (page.getResultItems().get("title")==null){
//            //skip this page
//            page.setSkip(true);
//        }
        // page.putField("title", page.getHtml().xpath("//div[@class='threadlist_title pull_left j_th_tit']/a/text()"));
        //System.out.println(page.getResultItems());
        page.addTargetRequests(page.getHtml().css("div.pagination").links().regex(".*/search\\?p=\\d*&q=java.*").all());
    }

    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        Spider gitHub_Java=Spider.create(new WebMagicForJava()).addUrl("https://github.com/search?p=96&q=java&type=Repositories&utf8=%E2%9C%93").addPipeline(new ConsolePipeline());
                //.addPipeline(new JsonFilePipeline("D:\\webmagic\\"));
        try {
            SpiderMonitor.instance().register(gitHub_Java);
        } catch (JMException e) {
            e.printStackTrace();
        }
        gitHub_Java.thread(1).run();
    }
}
