/*****************************************************
 * Copyright(C):南京莱斯信息技术股份有限公司
 * 文件名称      :ImageDownloader.java
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
package cn.yanf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yanfeng
 * @version v1.0
 * 2017/10/27
 */
public class ResourceDownloader {
    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    public static void treadDownloadImage(String urlString, String filename,String savePath) throws Exception{
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    downloadImage(urlString,filename,savePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void downloadImage(String urlString, String filename,String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }
}
