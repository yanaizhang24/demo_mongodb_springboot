package cn.yanf.mySpider;


import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/12/1.
 */
public class Test {
    public static void main(String[] args){
//        String url = "https://www.zhihu.com/#signin";
////        String url1 = "http://www.oschina.net/code/explore";
////        String url2 = "http://www.oschina.net/code/explore/achartengine";
////        String url3 = "http://www.oschina.net/code/explore/achartengine/client";
//
//        UrlQueue.addElem(url);
////        UrlQueue.addElem(url1);
////        UrlQueue.addElem(url2);
////        UrlQueue.addElem(url3);
//
//        UrlDataHanding[] url_Handings = new UrlDataHanding[10];
//
//        for (int i = 0; i < 1; i++) {
//            url_Handings[i] = new UrlDataHanding();
//            new Thread(url_Handings[i]).start();
//        }
        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

        HttpGet getHomePage = new HttpGet("http://www.zhihu.com/");
        try {
            //填充登陆请求中基本的参数
            CloseableHttpResponse response = httpClient.execute(getHomePage);
            String responseHtml = EntityUtils.toString(response.getEntity());
            String xsrfValue = responseHtml.split("<input type=\"hidden\" name=\"_xsrf\" value=\"")[1].split("\"/>")[0];
            System.out.println("_xsrf:" + xsrfValue);
            response.close();
            List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
            valuePairs.add(new BasicNameValuePair("_xsrf" , xsrfValue));
            valuePairs.add(new BasicNameValuePair("captcha_type", "cn"));
            valuePairs.add(new BasicNameValuePair("phone_num", "15195977600"));
            valuePairs.add(new BasicNameValuePair("password", "zhang704"));
           // valuePairs.add(new BasicNameValuePair("rememberme", "true"));
            if(true){
                //获取验证码
                HttpGet getCaptcha = new HttpGet("http://www.zhihu.com/captcha.gif?r=" + System.currentTimeMillis() + "&type=login");
                CloseableHttpResponse imageResponse = httpClient.execute(getCaptcha);
                FileOutputStream out = new FileOutputStream("c:/yanf/tmp/zhihu.gif");
                byte[] bytes = new byte[8192];
                int len;
                while ((len = imageResponse.getEntity().getContent().read(bytes)) != -1) {
                    out.write(bytes,0,len);
                }
                out.close();
                Runtime.getRuntime().exec("rundll32 c:\\Windows\\System32\\shimgvw.dll,ImageView_Fullscreen "+" c:\\yanf\\tmp\\zhihu.gif");//ubuntu下看图片的命令是eog

                //请用户输入验证码
                System.out.print("请输入验证码：");
                Scanner scanner = new Scanner(System.in);
                String captcha = scanner.next();
                valuePairs.add(new BasicNameValuePair("captcha", captcha));
            }


            //完成登陆请求的构造
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
            HttpPost post = new HttpPost("http://www.zhihu.com/login/phone_num");
            post.setHeader("Cookie","cap_id=\"MTgzM2YwNjNlMGI5NGVlYmJjNjJlNmE1MzI5ZWE1NDY=|1480589054|967e613c8dc75d1138a3b9f1ad7766b70752faa8\"");
            post.setEntity(entity);
            httpClient.execute(post);//登录

            HttpGet g = new HttpGet("http://www.zhihu.com/question/following");//获取“我关注的问题”页面
            CloseableHttpResponse r = httpClient.execute(g);
            System.out.println(EntityUtils.toString(r.getEntity()));
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
