package cn.yanf.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by yanf on 2016/5/25 0025.
 */
public class WebMagicDemo1 implements PageProcessor {

        private Site site = Site.me().setRetryTimes(3).setSleepTime(100);


        public void process(Page page) {
            page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
            page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
            page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
            if (page.getResultItems().get("name")==null){
                //skip this page
                //new
                page.setSkip(true);
            }
            page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        }


        public Site getSite() {
            return site;
        }

        public static void main(String[] args) {
            Spider.create(new WebMagicDemo1()).addUrl("https://github.com/code4craft").thread(5).run();
        }

}
