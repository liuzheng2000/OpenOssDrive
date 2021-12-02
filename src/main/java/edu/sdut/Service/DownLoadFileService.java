package edu.sdut.Service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/3 22:36
 */
public interface DownLoadFileService {

    /**
     * 通过文件地址多线程下载文件
     * TODO
     * @param downFilePath
     * @return
     */
    HttpServletResponse DownLoadFile(String downFilePath);

    /**
     *
     * 通过文件地址返回文件流
     * @param downFilePath
     * @param response
     * @return
     */
    void downloadFileToFileInputStream(String downFilePath,HttpServletResponse response);
}
