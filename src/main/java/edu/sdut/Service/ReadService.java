package edu.sdut.Service;

import edu.sdut.Vo.ReturnMsgBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/3 22:40
 */
public interface ReadService {
    /**
     * 根据文件路径获取文件目录
     * @param FilePath
     * @param httpServletRequest
     * @return
     */
     ReturnMsgBody ReadFile(StringBuilder FilePath, HttpServletRequest httpServletRequest);
}
