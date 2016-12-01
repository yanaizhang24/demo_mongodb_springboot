package cn.yanf.mySpider;

/**
 * Created by Administrator on 2016/12/1.
 */

import java.util.HashSet;

/**
 * 已访问url队列
 *
 * @author liuyazhuang
 *
 */
public class VisitedUrlQueue {
    public static HashSet<String> visitedUrlQueue = new HashSet<String>();

    public synchronized static void addElem(String url) {
        visitedUrlQueue.add(url);
    }

    public synchronized static boolean isContains(String url) {
        return visitedUrlQueue.contains(url);
    }

    public synchronized static int size() {
        return visitedUrlQueue.size();
    }
}
//    Test.java 此类为测试类
//
//[java] view plain copy
//        import java.sql.SQLException;
//
//        import com.sreach.spider.UrlDataHanding;
//        import com.sreach.spider.UrlQueue;
///**
// * @author liuyazhuang
// *
// */
//public class Test {
//    public static void main(String[] args) throws SQLException {
//        String url = "http://www.oschina.net/code/explore/achartengine/client/AndroidManifest.xml";
//        String url1 = "http://www.oschina.net/code/explore";
//        String url2 = "http://www.oschina.net/code/explore/achartengine";
//        String url3 = "http://www.oschina.net/code/explore/achartengine/client";
//
//        UrlQueue.addElem(url);
//        UrlQueue.addElem(url1);
//        UrlQueue.addElem(url2);
//        UrlQueue.addElem(url3);
//
//        UrlDataHanding[] url_Handings = new UrlDataHanding[10];
//
//        for (int i = 0; i < 10; i++) {
//            url_Handings[i] = new UrlDataHanding();
//            new Thread(url_Handings[i]).start();
//        }
//
//    }
//}
