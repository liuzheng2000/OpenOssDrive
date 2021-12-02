package edu.sdut.Service.ServiceImpl;

import edu.sdut.Service.DownLoadFileService;
import edu.sdut.Utils.CutFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 下载文件处理
 * @author qingyun
 * @version 1.0
 * @date 2021/10/30 16:53
 */
@Slf4j
@Service
public class DownLoadFileServiceImpl implements DownLoadFileService {
    /**
     *  @Qualifier 指定注入类
     */
    @Autowired
    @Qualifier(value = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolConfig;

    @Override
    public HttpServletResponse DownLoadFile(String downFilePath){
        File file = new File(downFilePath);
        int length = (int) file.length();
        System.out.println("文件总大小:" + length);
        int blockSize =  (length / 5);
        try{
            for (int threadID = 1; threadID <= 5  ; threadID++) {
                //线程下载的开始位置
                int startIndex = (threadID-1) * blockSize;
                int endIndex = startIndex + blockSize - 1;
                if (threadID == 5){
                    //最后一个线程下载长度较长
                    endIndex = length;
                }
                int finalEndIndex = endIndex;
                int finalThreadID = threadID;
                threadPoolConfig.execute(()->{
                     CutFileUtils.cutFileDownLoad(finalThreadID, downFilePath, startIndex, finalEndIndex);
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 返回文件流
     * @param downFilePath
     * @return
     */
    @Override
    public void downloadFileToFileInputStream(String downFilePath,HttpServletResponse response) {
        if (downFilePath.equals("")){
            throw new NullPointerException();
        }
        File file = new File(downFilePath);

        if (file.exists()){
            try{
                // 强制性下载
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;filename="+file.getName());
                // 要下载的文件
                FileInputStream input = new FileInputStream(downFilePath);
//                Resource fileResource = new ClassPathResource(downFilePath);
//                InputStream input = fileResource.getInputStream();
                // 每次最多读取1024字节
                byte[] data = new byte[1024];
                // 每次读取的字节数
                int len = 0;
                while ((len = input.read(data)) != -1) {
                    response.getOutputStream().write(data, 0, len);
                }
            }catch (IOException e){
                log.error("文件流返回失败");
                e.printStackTrace();
            }

        }
    }




}
