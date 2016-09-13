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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 严峰 on 2016/6/16 0016.
 */
public class BaiDUT implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setSleepTime(500);
    public static final String URL_LIST = "http://tieba\\.baidu\\.com/f\\?kw=.*";
    public static  int count=0;
    public void process(Page page) {
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        count++;
        System.out.println(count);
       if(page.getUrl().regex(URL_LIST).match()) {//帖子标签页
           int i;//作为帖子的ID
           for (Selectable s : page.getHtml().xpath("//div[@class='threadlist_title pull_left j_th_tit']").nodes()) {
               if (page.getResultItems().getAll().containsKey("id"))
                   i = (Integer) page.getResultItems().get("id");
               else
                   i = 1;
               page.putField("title" + i, s.xpath("//a/text()"));
               page.putField("href" + i, s.xpath("//a/@href"));
               page.addTargetRequest(s.xpath("//a/@href").toString());
               page.putField("id", ++i);
           }


           // page.putField("title", page.getHtml().xpath("//div[@class='threadlist_title pull_left j_th_tit']/a/text()"));
           //System.out.println(page.getResultItems());
           List<String> al = new ArrayList();
           for (int l = 1; l <= (Integer) page.getResultItems().get("id"); l++)
              // page.addTargetRequest(page.getResultItems().get("href" + l).toString());
           page.addTargetRequests(page.getHtml().xpath("//div[@id='frs_list_pager']/a[@class=' pagination-item ']").links().all());

       }else{
           page.putField("title", page.getHtml().xpath("//h3[@class='core_title_txt pull-left text-overflow  ']/@title").toString());
           int i_p;
           for(Selectable s:page.getHtml().xpath("//div[@class='l_post l_post_bright j_l_post clearfix  ']").nodes()){
               if (page.getResultItems().getAll().containsKey("id"))
                   i_p = (Integer) page.getResultItems().get("id");
               else
                   i_p = 1;
               if("热门推荐".equals(s.xpath("//a[@class='p_author_name j_user_card']/text()").toString()))
                   continue;
               page.putField("id",i_p);
               page.putField("author"+i_p,s.xpath("//a[@class='p_author_name j_user_card']/text()"));
               page.putField("href"+i_p,s.xpath("//a[@class='p_author_name j_user_card']/@href"));
               page.putField("content"+i_p,s.xpath("//div[@class='p_content  ']/cc/div[@class='d_post_content j_d_post_content ']/text()"));
               page.addTargetRequests(page.getHtml().xpath("//li[@class='l_pager pager_theme_5 pb_list_pager']").links().all());
           }
       }
        //page.addTargetRequests(al);
    }

    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        //http://tieba.baidu.com/f?kw=%E5%AE%88%E6%9C%9B%E5%85%88%E9%94%8B&ie=utf-8

       // System.out.println(URLDecoder.decode("http://tieba.baidu.com/f?kw=%E5%AE%88%E6%9C%9B%E5%85%88%E9%94%8B&ie=utf-8"));
        //Spider git=Spider.create(new BaiDUT()).addUrl("http://tieba.baidu.com/f?kw=%E7%BD%91%E7%BB%9C%E7%88%AC%E8%99%AB&ie=utf-8").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("D:\\webmagic\\"));
        Spider git=Spider.create(new BaiDUT()).addUrl("http://tieba.baidu.com/f?kw=%E5%AE%88%E6%9C%9B%E5%85%88%E9%94%8B&ie=utf-8").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("D:\\webmagic\\"));

        try {
            SpiderMonitor.instance().register(git);
        } catch (JMException e) {
            e.printStackTrace();
        }
        git.thread(5).run();
    }
}
