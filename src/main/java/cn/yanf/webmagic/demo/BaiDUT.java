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
import java.util.ArrayList;

/**
 * Created by 严峰 on 2016/6/16 0016.
 */
public class BaiDUT implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    public void process(Page page) {
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        int i;

        for(Selectable s:page.getHtml().xpath("//div[@class='threadlist_title pull_left j_th_tit']").nodes()){
            if(page.getResultItems().getAll().containsKey("id"))
                i=(Integer) page.getResultItems().get("id");
            else
                i=1;
            page.putField("title"+i, s.xpath("//a/text()"));
            page.putField("href"+i, s.xpath("//a/@href"));
            page.putField("id",++i);
        }


       // page.putField("title", page.getHtml().xpath("//div[@class='threadlist_title pull_left j_th_tit']/a/text()"));
        //System.out.println(page.getResultItems());
        ArrayList al=new ArrayList();
        for(int l=1;l<=(Integer) page.getResultItems().get("id");l++)
            al.add(page.getResultItems().get("href"+l));
        page.addTargetRequests(page.getHtml().xpath("//div[@id='frs_list_pager']/a[@class=' pagination-item ']").links().all());
        //page.addTargetRequests(al);
    }

    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        Spider git=Spider.create(new BaiDUT()).addUrl("http://tieba.baidu.com/f?kw=%E7%BD%91%E7%BB%9C%E7%88%AC%E8%99%AB&ie=utf-8").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("D:\\webmagic\\"));
        try {
            SpiderMonitor.instance().register(git);
        } catch (JMException e) {
            e.printStackTrace();
        }
        git.thread(5).run();
    }
}
