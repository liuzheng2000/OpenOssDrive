package edu.sdut.Service;

import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/3 23:10
 */
public interface UpLoadService {

    /**
     * 单文件上传
     * @param files
     * @param request
     * @return
     */
     ReturnMsgBody uploadsService(MultipartFile[] files, HttpServletRequest request);
}
