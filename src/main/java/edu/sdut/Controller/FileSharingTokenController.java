package edu.sdut.Controller;

import edu.sdut.Service.FileSharingTokenService;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 返回分享文件的Token
 * @author qingyun
 * @version 1.0
 * @date 2021/11/12 21:35
 */
@RestController
@RequestMapping("/share")
public class FileSharingTokenController {

    @Autowired
    FileSharingTokenService fileSharingTokenService;
    /**
     * 返回分享文件Token
     * @param filePathAndName  分享的文件  G:\OpenOss\data\qingyun
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/FileShareToken")
    public ReturnMsgBody ReturnFileShareToken(@RequestParam HashMap<String, String> filePathAndName, HttpServletRequest httpServletRequest){
        return fileSharingTokenService.returnFileShareToken(filePathAndName,httpServletRequest);
    }
}
