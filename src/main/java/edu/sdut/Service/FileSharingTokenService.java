package edu.sdut.Service;

import edu.sdut.Vo.ReturnMsgBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/12 21:51
 */
public interface FileSharingTokenService {

    /**
     * 返回分享文件的名字与路径生成的Token
     * @param filePathAndName
     * @param httpServletRequest
     * @return
     */
    ReturnMsgBody returnFileShareToken(HashMap<String,String> filePathAndName, HttpServletRequest httpServletRequest);

}
