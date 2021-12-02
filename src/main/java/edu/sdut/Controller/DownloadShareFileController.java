package edu.sdut.Controller;

import edu.sdut.Service.DownloadShareFileService;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 下载分享的文件
 * @author qingyun
 * @version 1.0
 * @date 2021/11/15 8:48
 */
@RestController
@RequestMapping("/DownShareFile")
public class DownloadShareFileController {

    @Autowired
    DownloadShareFileService downloadShareFileService;
    /**
     * 解析token返回下载的文件路径
     * @param Token
     * @return
     */
    @PostMapping("/ByToken")
    public ReturnMsgBody DownloadByToken(String Token){
        return downloadShareFileService.downLoadByToken(Token);
    }
}
