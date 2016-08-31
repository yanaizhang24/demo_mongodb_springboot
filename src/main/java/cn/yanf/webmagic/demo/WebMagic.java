package cn.yanf.webmagic.demo;

/**
 * Created by 严峰 on 2016/4/20 0020.
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.management.JMException;

public class WebMagic implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(10000);


    public void process(Page page) {

        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
//            //skip this page
//            page.setSkip(true);
//        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        //System.out.println(page);
        page.addTargetRequests(page.getHtml().links().all());
    }


    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider git=Spider.create(new WebMagic()).addUrl("https://github.com/code4craft").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("D:\\webmagic"));
        try {
            SpiderMonitor.instance().register(git);
        } catch (JMException e) {
            e.printStackTrace();
        }
        git.thread(5).run();
    }
}