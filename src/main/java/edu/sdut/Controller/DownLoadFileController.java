package edu.sdut.Controller;

import edu.sdut.Service.ServiceImpl.DownLoadFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载类
 * @author qingyun
 * @version 1.0
 * @date 2021/10/30 14:57
 */
@RestController
@RequestMapping(value = "/download")
public class DownLoadFileController {

    @Autowired
    DownLoadFileServiceImpl downLoadFileService;
    @GetMapping("/downloadFile")
    public HttpServletResponse DownLoadFile(String downFilePath){
       return downLoadFileService.DownLoadFile(downFilePath);
    }


    @GetMapping("/downloadFileToFileInputStream")
    public void downloadFileToFileInputStream(String downFilePath ,HttpServletResponse response ){
         downLoadFileService.downloadFileToFileInputStream(downFilePath,response);
    }



}
