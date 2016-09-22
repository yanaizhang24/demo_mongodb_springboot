package cn.yanf.webmagic.demo;


import cn.yanf.Repository.TieBarRepository;
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
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.management.JMException;
import java.io.UnsupportedEncodingException;
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
    private static TieBarRepository repository;

    public TieBarRepository getRepository() {
        return repository;
    }

    public void setTieBarRepository(TieBarRepository repository) {
        this.repository = repository;
    }
    public void process(Page page) {
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        List<TieBar> list=new ArrayList();
        List<TieBar> list2=new ArrayList();
        count++;
        //page.getHtml().links();
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
               TieBarBiaoQ tb=new TieBarBiaoQ(i+"",s.xpath("//a/@href").toString());
               page.putField("id", ++i);
               list.add(tb);
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
               //剔除热门推荐
               if("热门推荐".equals(s.xpath("//a[@class='p_author_name j_click_stats']/text()").toString().trim()))
                   continue;
               page.putField("id",i_p);
               page.putField("author"+i_p,s.xpath("//a[@class='p_author_name j_user_card']/text()"));
               page.putField("href"+i_p,s.xpath("//a[@class='p_author_name j_user_card']/@href"));
               page.putField("content"+i_p,s.xpath("//div[@class='p_content  ']/cc/div[@class='d_post_content j_d_post_content ']/text()"));
               //首先判断有没有回复
               //再判断有几页回复
               //只有一页，直接获取
               //后续的依靠拼接：
               // http://tieba.baidu.com/p/comment?tid=4791604128&pid=98137268553&pn=2   &t=1474561684093
               //这个直接用jsoup获取地址然后直接解析
               page.addTargetRequests(page.getHtml().xpath("//li[@class='l_pager pager_theme_5 pb_list_pager']").links().all());
               TieBarTieZ tbz=new TieBarTieZ(page.getUrl().toString(),
                       page.getHtml().xpath("//h3[@class='core_title_txt pull-left text-overflow  ']/@title").toString(),
                       s.xpath("//a[@class='p_author_name j_user_card']/text()").toString(),
                       s.xpath("//div[@class='p_content  ']/cc/div[@class='d_post_content j_d_post_content ']/text()").toString().trim(),
                       s.xpath("//a[@class='p_author_name j_user_card']/@href").toString());
               list2.add(tbz);
           }
       }
        //page.addTargetRequests(al);
        if(list.size()>0)
            page.putField("list",list);
        if(list2.size()>0)
            page.putField("list2",list2);
        //lzl_single_post j_lzl_s_p
        //<li class="lzl_single_post j_lzl_s_p first_no_border"
        // data-field="{'pid':'68598834140','spid':'68626823885','user_name':'水煮冰淇淋a','portrait':'1c98e6b0b4e785aee586b0e6b787e6b78b617515'}"><a data-field="{'un':'水煮冰淇淋a'}" target="_blank" class="j_user_card lzl_p_p" href="/home/main?un=%E6%B0%B4%E7%85%AE%E5%86%B0%E6%B7%87%E6%B7%8Ba&amp;ie=utf-8&amp;fr=pb" username="水煮冰淇淋a"><img src="http://tb.himg.baidu.com/sys/portrait/item/1c98e6b0b4e785aee586b0e6b787e6b78b617515"></a><div class="lzl_cnt"><a class="at j_user_card " data-field="{'un':'水煮冰淇淋a'}" alog-group="p_author" target="_blank" href="/home/main?un=%E6%B0%B4%E7%85%AE%E5%86%B0%E6%B7%87%E6%B7%8Ba&amp;ie=utf-8&amp;fr=pb" username="水煮冰淇淋a">水煮冰淇淋a</a>:&nbsp;<span class="lzl_content_main">？？</span><div class="lzl_content_reply"><span class="lzl_jb" style="display: none;"><a href="#" data-field="{'delete_mine':'0','user_name':'水煮冰淇淋a'}" class="lzl_jb_in">举报</a>&nbsp;|&nbsp;</span><span class="lzl_op_list j_lzl_o_l"></span><span class="lzl_time">2015-5-19&nbsp;10:55</span><a href="#" class="lzl_s_r">回复</a></div></div><div class="user-hide-post-down" style="top: 36px; right: 158px; display: none;"></div></li>
        //只要有隐藏的就会有
        //<p class="j_pager l_pager pager_theme_2 lzl_pager" style="display:none"><span class="tP">1</span><a href="#" index="2">2</a><a href="#" index="3">3</a><a href="#" index="2">下一页</a><a href="#" index="3">尾页</a></p>

    }

    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        //http://tieba.baidu.com/f?kw=%E5%AE%88%E6%9C%9B%E5%85%88%E9%94%8B&ie=utf-8
        //http://tieba.baidu.com/f?kw=%CD%F8%C2%E7%C5%C0%B3%E6&fr=ala0&tpl=5
        //Spider git2=Spider.create(new BaiDUT());
        //git2.test("http://tieba.baidu.com/p/comment?tid=4736113607&pid=96242089508&pn=1");
        try {
            System.out.println(URLDecoder.decode("http://tieba.baidu.com/f?kw=%E5%AE%88%E6%9C%9B%E5%85%88%E9%94%8B&ie=utf-8","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Spider git=Spider.create(new BaiDUT()).addUrl("http://tieba.baidu.com/f?kw=%E7%BD%91%E7%BB%9C%E7%88%AC%E8%99%AB&ie=utf-8").addPipeline(new ConsolePipeline()).addPipeline(new JsonFilePipeline("D:\\webmagic\\"));
        Spider git=Spider.create(new BaiDUT()).addUrl(
                //"http://tieba.baidu.com/f?kw=%CD%F8%C2%E7%C5%C0%B3%E6&fr=ala0&tpl=5"
                "http://tieba.baidu.com/p/comment?tid=4736113607&pid=96242089508&pn=1"
                )
                .addPipeline(new ConsolePipeline())
                //.addPipeline(new MongodbPipeline(repository))
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
