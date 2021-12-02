package edu.sdut.Controller;

import edu.sdut.Service.ServiceImpl.DownloadNetworkFileServiceImpl;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过URL传入下载网络文件
 * @author qingyun
 * @version 1.0
 * @date 2021/11/10 21:07
 */
@RestController
@RequestMapping(value = "/DownloadNetwork")
public class DownloadNetworkFilesByUrlController {

    @Autowired
    DownloadNetworkFileServiceImpl downloadNetworkFileService;

   @PostMapping(value = "/ByUrl")
    public ReturnMsgBody GetNetworkFiles(String url){

       try{
           downloadNetworkFileService.download(url);
       }catch (Exception e){
           e.printStackTrace();
       }
        return ReturnMsgBody.ReturnSuccess("下载成功","保存位置为aaa");
    }
}
