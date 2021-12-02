package edu.sdut.Utils.File;

import edu.sdut.Service.ServiceImpl.DownloadNetworkFileServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

/**
 * 多线程下载工具类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/10 18:43
 */
@Slf4j
public class DownloadThread implements Callable<Boolean> {

    /**
     * 每次读取的数据块大小
     */
    private static int BYTE_SIZE = 1024 * 100;

    /**
     * 下载链接
     */
    private String url;
    /**
     * 下载开始位置
     */
    private long startPos;

    /**
     * 下载结束位置
     */
    private Long endPos;

    /**
     * 表示线程下载切分的第几部分
     */
    private Integer part;

    /**
     * 文件总大小
     */
    private Long contentLength;

    public DownloadThread(String url, long startPos, Long endPos, Integer part, Long contentLength) {
        this.url = url;
        this.startPos = startPos;
        this.endPos = endPos;
        this.part = part;
        this.contentLength = contentLength;
    }


    @Override
    public Boolean call() throws Exception {
        if (url == null || url.trim() == ""){
            throw new RuntimeException("下载路径不正确");
        }
        //文件名
        String httpFileName = HttpUtils.getHttpFileName(url);
        //设置中央下载位置 目前无法更改  TODO
        httpFileName = FileUtils.setLocalFilePath(httpFileName,"G:\\OpenOss\\download\\");
        /**
         * 文件改名分区
         */
        if (part != null){
            //临时文件名称
            httpFileName = httpFileName + DownloadNetworkFileServiceImpl.FILE_TEMP_SUFFIX  + part;
        }
        //本地文件大小
        long localFileContentLength = FileUtils.getFileContentLength(httpFileName);
        if (localFileContentLength >= endPos - startPos){
        log.info("{} 已经下载完毕，无需重复下载", httpFileName);
        LogThread.DOWNLOAD_FINISH_THREAD.addAndGet(1);
        return true;
        }
        if (endPos.equals(contentLength)){
            endPos  = null;
        }
        HttpURLConnection httpUrlConnection = HttpUtils.getHttpUrlConnection(url, startPos + localFileContentLength, endPos);
        //获取输入流
        try(
            InputStream input = httpUrlConnection.getInputStream(); BufferedInputStream bis = new BufferedInputStream(input);
            RandomAccessFile oSavedFile = new RandomAccessFile(httpFileName,"rw")) {
                oSavedFile.seek(localFileContentLength);
                byte[] buffer = new byte[BYTE_SIZE];
                int len = -1;
                while ((len = bis.read(buffer)) != -1){
                    oSavedFile.write(buffer,0,len);
                    LogThread.DOWNLOAD_SIZE.addAndGet(len);
                }
        }catch (FileNotFoundException e){
            log.error("ERROR! 要下载的文件路径不存在 {} ", url);
            return false;
        }catch (Exception e){
            log.error("下载出现异常");
            e.printStackTrace();
            return false;
        }finally {
            httpUrlConnection.disconnect();
            LogThread.DOWNLOAD_FINISH_THREAD.addAndGet(1);
        }
        return true;
    }
}
