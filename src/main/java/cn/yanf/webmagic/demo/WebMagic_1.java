package cn.yanf.webmagic.demo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class WebMagic_1 implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        //regex为正则
        //xpath为xml选择器
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        //
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //如果“name”是null就跳过
            System.out.println();
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        // 部分三：从页面发现后续的url地址来抓取
       // page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
//        这段代码的分为两部分，
//        page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all()
//        用于获取所有满足"(https:/ /github\.com/\w+/\w+)"这个正则表达式的链接，
//        page.addTargetRequests()则将这些链接加入到待抓取的队列中去。
        //List<String> urls = page.getHtml().css("div.pagination").links().regex(".*/search/\?l=java.*").all();
        //page.addTargetRequests(urls);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new WebMagic_1()).addUrl("https://github.com/code4craft").thread(5).run();
    }
}
