package edu.sdut.Service;

import edu.sdut.Vo.ReturnMsgBody;

import java.util.HashMap;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/15 8:49
 */
public interface DownloadShareFileService {

    /**
     * 解析token返回下载的文件路径
     * @param Token
     * @return
     **/
    ReturnMsgBody downLoadByToken(String Token);
}
