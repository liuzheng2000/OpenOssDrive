package edu.sdut.Service.ServiceImpl;

import edu.sdut.Service.FileSharingTokenService;
import edu.sdut.Utils.JwtTokenUtils;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/12 21:53
 */
@Service
public class FileSharingTokenServiceImpl implements FileSharingTokenService {

    /**
     * 返回分享文件的名字与路径生成的Token
     * @param filePathAndName
     * @param httpServletRequest
     * @return
     */
    @Override
    public ReturnMsgBody returnFileShareToken(HashMap<String, String> filePathAndName, HttpServletRequest httpServletRequest) {
        String userName = JwtTokenUtils.getUserNameByHttpServletRequest(httpServletRequest);
        String shareFileToken = JwtTokenUtils.createShareFileToken(userName, filePathAndName);
        return ReturnMsgBody.ReturnSuccess("分享文件成功",shareFileToken);
    }
}
