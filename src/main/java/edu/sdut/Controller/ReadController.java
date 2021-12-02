package edu.sdut.Controller;


import edu.sdut.Service.ServiceImpl.ReadServiceImpl;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 读取的主要控制类
 * @author qingyun
 * @version 1.0
 * @date 2021/10/28 8:41
 */
@RestController
@RequestMapping(value = "/read")
public class ReadController {

    @Autowired
    ReadServiceImpl readService;
    /**
     * 根据文件路径获取文件目录
     * @param FilePath
     * @return
     */
    @RequestMapping(value = "/GetFile",method = RequestMethod.POST)
    public ReturnMsgBody ReadFile(@RequestParam StringBuilder FilePath , HttpServletRequest httpServletRequest){
       return  readService.ReadFile(FilePath,httpServletRequest);
    }
}
