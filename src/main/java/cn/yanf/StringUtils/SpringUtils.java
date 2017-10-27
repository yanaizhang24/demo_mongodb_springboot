package cn.yanf.StringUtils;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.entity.Customer;
import cn.yanf.entity.TieBarTieZReply;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
@Component
public class SpringUtils implements Serializable,CommandLineRunner {
    @Autowired
    private CustomerRepository repository;
    private static String message;
    public  static void getMessage (String message) throws Exception {
        SpringUtils.message=message;
    }

    @Override
    public void run(String... strings) throws Exception {
        if(strings.length>0)
            repository.save(new Customer("102160613134951192168402401009",strings[0]));
    }
    public  static <T> boolean writeData(List<T> list,String fileName){
        Date date=new Date();
        DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd-HH:mm");
        File file=new File(fileName+dateFormat.format(date)+".txt");
        if(file.exists()){//文件已存在
            return false;
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter=new FileWriter(file.getName(),true);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);

            bufferedWriter.write(dateFormatter.format(date));
            bufferedWriter.newLine();
            for(T t:list){
                bufferedWriter.write(t.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String trim(String str){
        if(StringUtils.isNotEmpty(str)){
            return str.trim();

        }
        return null;
    }
    //获取帖子的回复
    public static List<TieBarTieZReply> getReply(String url_replay){
        //String url_replay="http://tieba.baidu.com/p/comment?tid="+id_tiezi+"&pid="+strs[2]+"&pn=1";
        List<TieBarTieZReply> list_tiebartiezreply=new ArrayList<>();
        Document document= null;
        try {
            document = Jsoup.connect(url_replay).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements author=document.select("a.j_user_card.lzl_p_p");
        Elements content = document.getElementsByClass("lzl_content_main");

        for(Element element:author){
            String href=element.attr("href");
            String author_name=element.attr("username");
            TieBarTieZReply tieBarTieZReply=new TieBarTieZReply(author_name,href,null);
            list_tiebartiezreply.add(tieBarTieZReply);
        }
        for(int i_content=0;i_content<content.size();i_content++){
            //Elements links = element.getElementsByTag("img");
//                            for (Element link : links) {
//                                String linkHref = link.attr("href");
//                                String linkText = link.text();
//                            }
            list_tiebartiezreply.get(i_content).setContent(content.get(i_content).text());
        }
        return list_tiebartiezreply;
    }
    public static String UUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
