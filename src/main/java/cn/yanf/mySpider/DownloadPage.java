package cn.yanf.mySpider;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2016/12/1.
 */
public class DownloadPage {

    /**
     * 根据URL抓取网页内容
     *
     * @param url
     * @return
     */
    public static String getContentFormUrl(String url) {
        /* 实例化一个HttpClient客户端 */
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet getHttp = new HttpGet(url);

        String content = null;

        CloseableHttpResponse response;
        try {
            /* 获得信息载体 */
            response = client.execute(getHttp);
            HttpEntity entity = response.getEntity();

            VisitedUrlQueue.addElem(url);

            if (entity != null) {
                /* 转化为文本信息 */
                content = EntityUtils.toString(entity);

                /* 判断是否符合下载网页源代码到本地的条件 */
                if (FunctionUtils.isCreateFile(url)
                        && FunctionUtils.isHasGoalContent(content) != -1) {
                    FunctionUtils.createFile(
                            FunctionUtils.getGoalContent(content), url);
                }
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }

        return content;
    }

}
//Get方法：
//
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://targethost/homepage");
//        CloseableHttpResponse response1 = httpclient.execute(httpGet);
//        // The underlying HTTP connection is still held by the response object
//        // to allow the response content to be streamed directly from the network socket.
//        // In order to ensure correct deallocation of system resources
//        // the user MUST either fully consume the response content  or abort request
//        // execution by calling CloseableHttpResponse#close().
//        //建立的http连接，仍旧被response1保持着，允许我们从网络socket中获取返回的数据
//        //为了释放资源，我们必须手动消耗掉response1或者取消连接（使用CloseableHttpResponse类的close方法）
//
//        try {
//        System.out.println(response1.getStatusLine());
//        HttpEntity entity1 = response1.getEntity();
//        // do something useful with the response body
//        // and ensure it is fully consumed
//        EntityUtils.consume(entity1);
//        } finally {
//        response1.close();
//        }
//        Post方法：
//
//        HttpPost httpPost = new HttpPost("http://targethost/login");
//        //拼接参数
//        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "secret"));
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//        CloseableHttpResponse response2 = httpclient.execute(httpPost);
//
//        try {
//        System.out.println(response2.getStatusLine());
//        HttpEntity entity2 = response2.getEntity();
//        // do something useful with the response body
//        // and ensure it is fully consumed
//        //消耗掉response
//        EntityUtils.consume(entity2);
//        } finally {
//        response2.close();
//        }
//        再往下看HttpClients的源码，具体的实现都在HttpClientBuilder的build方法中，有兴趣的可以去apache看源码。
//
///**
// * Creates {@link CloseableHttpClient} instance with default
// * configuration.
// */
//public static CloseableHttpClient createDefault() {
//        return HttpClientBuilder.create().build();
//        }