package edu.sdut.Service.ServiceImpl;

import edu.sdut.Service.DownloadShareFileService;
import edu.sdut.Utils.JwtTokenUtils;
import edu.sdut.Vo.ReturnMsgBody;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/15 8:49
 */
@Service
public class DownloadShareFileServiceImpl implements DownloadShareFileService {

    /**
     * @param Token 解析token返回下载的文件路径
     * @return
     */
    @Override
    public ReturnMsgBody downLoadByToken(String Token) {
        Claims map = JwtTokenUtils.getTokenBody(Token);
        return ReturnMsgBody.ReturnSuccess("Token解析成功",map);
    }
}
