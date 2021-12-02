package edu.sdut.Utils.File;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 网络请求操作工具类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/9 22:00
 */
@Slf4j
@Configuration
public class HttpUtils {





    /**
     * 获取 HTTP 链接
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getHttpUrlConnection(String url) throws IOException{
        URL httpUrl = new URL(url);
        HttpURLConnection openConnection = (HttpURLConnection)  httpUrl.openConnection();
        openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
        return openConnection;
    }

    public static HttpURLConnection getHttpUrlConnection(String url , Long start , Long end) throws IOException{
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(url);
        log.debug("此线程下载区间为:"+start+"~"+end);
        if (end != null){
            httpUrlConnection.setRequestProperty("RANGE","bytes="+start+"-"+end);
        }else {
            httpUrlConnection.setRequestProperty("RANGE","bytes="+start+"-");
        }
        Map<String, List<String>> headerFields = httpUrlConnection.getHeaderFields();
        for (String s : headerFields.keySet()) {
            log.debug("此线程相应头{}:{}", s, headerFields.get(s));
        }
        return httpUrlConnection;
    }


    /**
     * 获取网络文件大小 bytes
     * @param url
     * @return
     * @throws IOException
     */
    public static long getHttpFileContentLength(String url) throws IOException{
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(url);
        int contentLength = httpUrlConnection.getContentLength();
        httpUrlConnection.disconnect();
        return contentLength;
    }

    /**
     * 获取网络文件Etag
     * @param url
     * @return
     * @throws IOException
     */
    public static String getHttpFileEtag(String url) throws IOException{
        HttpURLConnection httpUrlConnection = getHttpUrlConnection(url);
        Map<String, List<String>> headerFields = httpUrlConnection.getHeaderFields();
        List<String> eTagList = headerFields.get("ETag");
        httpUrlConnection.disconnect();
        return eTagList.get(0);
    }

    /**
     * 获取网络文件名
     * @param url
     * @return
     */
    public static String getHttpFileName(String url){
        int indexOf = url.lastIndexOf("/");
        return  url.substring(indexOf + 1);
    }

}
