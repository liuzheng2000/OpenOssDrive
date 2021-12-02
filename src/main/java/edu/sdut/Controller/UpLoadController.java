package edu.sdut.Controller;

import edu.sdut.Service.ServiceImpl.UpLoadServiceImpl;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 上传文件控制中心
 * @author qingyun
 * @version 1.0
 * @date 2021/10/27 19:41
 */
@RestController
@RequestMapping(value = "/upload")
public class UpLoadController {


    @Autowired
    UpLoadServiceImpl upLoadService;
    /**
     * 文件上传
     * @param files
     * @return
     */
    @RequestMapping(value = "/FileOne",method = RequestMethod.POST)
    public ReturnMsgBody uploads(MultipartFile[] files, HttpServletRequest request){
            ReturnMsgBody returnMsgBody = upLoadService.uploadsService(files,request);
            return returnMsgBody;
    }

}
