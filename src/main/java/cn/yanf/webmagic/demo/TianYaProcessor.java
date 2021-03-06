/*****************************************************
 * Copyright(C):南京莱斯信息技术股份有限公司
 * 文件名称      :TianYaProcessor.java
 * 编制人员      :yanfeng
 * 创建日期      :2017/10/27
 * 版本          :v1.0
 *
 *  修改记录
 *  版本信息     ：
 *  更改人员     ：
 *  更改日期     ：
 *  更改内容     ：
 *  更改原因     ：
 *
 ****************************************************/
package cn.yanf.webmagic.demo;

/**
 * @author yanfeng
 * @version v1.0
 * 2017/10/27
 */

import cn.yanf.StringUtils.SpringUtils;
import cn.yanf.entity.TieBarTieZReply;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.management.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class TianYaProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(500);
    public static final String URL_LIST = "http://tieba\\.baidu\\.com/f\\?kw=.*";
    public static final String BAI_TZ="(http://tieba\\.baidu\\.com/p/)(\\d*)";
    public static  int count=0;
    public void process(Page page) {
        //获取帖子id
        Pattern pattern=Pattern.compile(BAI_TZ);
        Matcher m=pattern.matcher(page.getUrl().toString());
        //m.find();
        String id_tiezi=m.group(2);
        int i_p;
        int i=0;
        //获取div[atl-pages]
        for(Selectable s:page.getHtml().xpath("//div[@class='atl-pages']").nodes()) {
            if (page.getResultItems().getAll().containsKey("id"))
                i_p = (Integer) page.getResultItems().get("id");
            else
                i_p = 1;
            //剔除热门推荐
            if ("热门推荐".equals(SpringUtils.trim(s.xpath("//a[@class='p_author_name j_click_stats']/text()").toString())))
                continue;
            page.putField("id", i_p);
            page.putField("author" + i_p, s.xpath("//a[@class='p_author_name j_user_card']/text()"));
            page.putField("href" + i_p, s.xpath("//a[@class='p_author_name j_user_card']/@href"));
            page.putField("content" + i_p, s.xpath("//div[@class='p_content  ']/cc/div[@class='d_post_content j_d_post_content ']/text()"));
            //page.addTargetRequests(page.getHtml().xpath("//li[@class='l_pager pager_theme_5 pb_list_pager']").links().all());
            //首先判断有没有回复(回复的第一列data-field有没有值)

            if(StringUtils.isNotEmpty(s.xpath("//img[@class='loading_reply']").toString())){
                i++;
                String[] strs=new String[]{};
                if(StringUtils.isNotEmpty(s.xpath("//cc/div[@class='d_post_content j_d_post_content ']/@id").toString()))
                    strs=s.xpath("//cc/div[@class='d_post_content j_d_post_content ']/@id").toString() .split("_");
                if(strs.length>=3){

                    try {
                        String url_replay="http://tieba.baidu.com/p/comment?tid="+id_tiezi+"&pid="+strs[2]+"&pn=1";
                        Document document= Jsoup.connect(url_replay).get();
                        Elements author=document.select("a.j_user_card.lzl_p_p");
                        Elements content = document.getElementsByClass("lzl_content_main");
                        List<TieBarTieZReply> list_tiebartiezreply=new ArrayList<>();
                        for(Element element:author){
                            String href=element.attr("href");
                            String author_name=element.attr("username");
                            TieBarTieZReply tieBarTieZReply=new TieBarTieZReply(author_name,href,null);
                            list_tiebartiezreply.add(tieBarTieZReply);
                        }
                        for(int i_content=0;i_content<content.size();i_content++){
                            list_tiebartiezreply.get(i_content).setContent(content.get(i_content).text());
                        }
                        Elements page_reply=document.select("li.lzl_li_pager.j_lzl_l_p.lzl_li_pager_s");
                        int pageNum=1;
                        if(page_reply.size()>0){
                            for(Element e:page_reply){
                                //{"total_num":19,"total_page":2}
                                String as=e.attr("data-field");
                                JSONObject jo= JSONObject.fromObject(as);
                                pageNum=Integer.parseInt(jo.getString("total_page"));
                            }
                        }
                        if(pageNum>1){
                            for(int i_n=2;i_n<=pageNum;i_n++){
                                SpringUtils.getReply("http://tieba.baidu.com/p/comment?tid="+id_tiezi+"&pid="+strs[2]+"&pn="+i_n);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        Spider git=Spider.create(new TianYaProcessor()).addUrl(
                //"http://tieba.baidu.com/f?kw=%CD%F8%C2%E7%C5%C0%B3%E6&fr=ala0&tpl=5"
                //"http://tieba.baidu.com/p/comment?tid=4736113607&pid=96242089508&pn=1"
                //"http://tieba.baidu.com/p/3618488603"
                //"http://tieba.baidu.com/p/4790730924"
                "http://bbs.tianya.cn/post-worldlook-223829-1.shtml"
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
    public static void getReply(String url_replay){
        //String url_replay="http://tieba.baidu.com/p/comment?tid="+id_tiezi+"&pid="+strs[2]+"&pn=1";
        Document document= null;
        try {
            document = Jsoup.connect(url_replay).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements content = document.getElementsByClass("lzl_content_main");
        for(Element element:content){
            //Elements links = element.getElementsByTag("img");
//                            for (Element link : links) {
//                                String linkHref = link.attr("href");
//                                String linkText = link.text();
//                            }
            System.out.println( element.text());
        }
    }
}

