package edu.sdut.Service.ServiceImpl;


import edu.sdut.Utils.File.DownloadThread;
import edu.sdut.Utils.File.FileUtils;
import edu.sdut.Utils.File.HttpUtils;
import edu.sdut.Config.ThreadPool.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 多线程下载服务类
 * 断点续传下载  demo
 * @author qingyun
 * @version 1.0
 * @date 2021/11/10 19:15
 */
@Slf4j
@Service
public class DownloadNetworkFileServiceImpl {


    /**
     * 引入线程池
     */
    @Autowired
    @Qualifier(value = "taskExecutor")
    public ThreadPoolTaskExecutor threadPoolConfig;


    /**
     * 文件下载地址
     */
    @Value("${download.path}")
    private String downloadPath;

    /**
     * 临时文件后缀
     */
    public static String FILE_TEMP_SUFFIX = ".temp";


    public void download(String url) throws Exception{
        String fileName = HttpUtils.getHttpFileName(url);
        //文件地址
        fileName = FileUtils.setLocalFilePath(fileName,downloadPath);

        long localFileSize = FileUtils.getFileContentLength(fileName);
        long httpFileContentLength = HttpUtils.getHttpFileContentLength(url);
        if (localFileSize >= httpFileContentLength){
            log.info("{}已经下载完毕，无需重新下载", fileName);
            return;
        }
        List<Future<Boolean>> futureList = new ArrayList<>();

        if (localFileSize > 0){
            log.info("开始断点续传 {}", fileName);
        } else {
           log.info("开始下载文件 {}", fileName);
        }
        log.info("开始下载时间 {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        long startTime = System.currentTimeMillis();
        // 任务切分
        splitDownload(url,futureList);
//        LogThread logThread = new LogThread(httpFileContentLength);
//        Future<Boolean> future = threadPoolConfig.submit(logThread);
//        futureList.add(future);
        //开始下载
        for (Future<Boolean> booleanFuture : futureList) {
            booleanFuture.get();
        }

        log.info("文件下载完毕 {}，本次下载耗时：{}", fileName, (System.currentTimeMillis() - startTime) / 1000 + "s");
        log.info("结束下载时间 {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        //文件合并
        boolean merge = merge(fileName);
        if (merge){
            //清理分段文件
            clearTemp(fileName);
        }
        log.info("本次文件下载结束");
    }

    /**
     * 清理分段文件
     * @param fileName
     * @return
     */
    private boolean clearTemp(String fileName) {
        log.info("开始清理临时文件 {}{}0-{}", fileName, FILE_TEMP_SUFFIX, (ThreadPoolConfig.corePoolSize - 1));
        for (int i = 0; i < ThreadPoolConfig.corePoolSize; i++) {
            File file = new File(fileName + DownloadNetworkFileServiceImpl.FILE_TEMP_SUFFIX + i);
            file.delete();
        }
        log.info("临时文件清理完毕 {}{}0-{}", fileName, FILE_TEMP_SUFFIX, (ThreadPoolConfig.corePoolSize - 1));
        return true;
    }

    /**
     * 文件合并
     * @param fileName　
     */
    private boolean merge(String fileName) throws IOException {
        log.info("开始合并文件 {}", fileName);
        byte[] buffer = new byte[1024 * 10];
        int len = -1;
        try (RandomAccessFile oSavedFile = new RandomAccessFile(fileName, "rw")) {
            for (int i = 0; i < ThreadPoolConfig.corePoolSize; i++) {
                try (BufferedInputStream bis = new BufferedInputStream(
                        new FileInputStream(fileName + FILE_TEMP_SUFFIX + i))) {
                    // 读到文件末尾则返回-1
                    while ((len = bis.read(buffer)) != -1) {
                        oSavedFile.write(buffer, 0, len);
                    }
                }
            }
            log.info("文件合并完毕 {}", fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
            }
        return true;
        }


    /**
     * 切分下载任务到多个线程
     * @param url
     * @param futureList
     */
    private  void splitDownload(String url, List<Future<Boolean>> futureList) throws IOException {
        long httpFileContentLength = HttpUtils.getHttpFileContentLength(url);
        //任务切分
        long size = httpFileContentLength / ThreadPoolConfig.corePoolSize;
        long lastSize = httpFileContentLength - (httpFileContentLength / ThreadPoolConfig.corePoolSize
        * (ThreadPoolConfig.corePoolSize - 1));
        for (int i = 0; i < ThreadPoolConfig.corePoolSize ; i++) {
            long start = i * size;
            Long downloadWindow = ( i == ThreadPoolConfig.corePoolSize - 1) ? lastSize : size;
            long end = start + downloadWindow;
            if (start != 0){
                start++;
            }
            DownloadThread downloadThread = new DownloadThread(url, start, end, i, httpFileContentLength);
            Future<Boolean> future = threadPoolConfig.submit(downloadThread);
            futureList.add(future);
        }
    }
}
