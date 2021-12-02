package edu.sdut.Service.ServiceImpl;


import edu.sdut.Service.UpLoadService;
import edu.sdut.Utils.JwtTokenUtils;
import edu.sdut.Vo.ReturnMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/10/27 23:47
 */
@Service
@Slf4j
public class UpLoadServiceImpl implements UpLoadService {

    /**
     * 文件上传地址
     */
    @Value("${upload.path}")
    private String uploadPath;


    /**
     * 单文件上传
     * @param files
     * @return
     */
    @Override
    public ReturnMsgBody uploadsService(MultipartFile[] files, HttpServletRequest request){
        /**
         * 实现上传位置为个人地址
         */
        String userName = JwtTokenUtils.getUserNameByHttpServletRequest(request);
        try{
            //上传目录地址   例如:G:\OpenOss\data\qingyun/Java目标清单.md
            String uploadDir = new StringBuilder(uploadPath).append(userName).append("/").toString();
            //如果目录不存在则自动创建文件夹
            File dir = new File(String.valueOf(uploadDir));
            if (!dir.mkdir()){
                dir.mkdir();
            }
            //获取上传文件的文件名
            for (MultipartFile file : files) {
                StringBuilder oldName= new StringBuilder(file.getOriginalFilename());
                String filePath = new StringBuilder(uploadDir).append(oldName).toString();
                file.transferTo(Paths.get(filePath));
                // 例如: Java目标清单.md上传至G:\OpenOss\data\qingyun/Java目标清单.md
                log.info(oldName+"上传至"+uploadDir);
            }
            return ReturnMsgBody.ReturnSuccess("上传成功","1");
        }catch (Exception e){
            //打印错误信息
            e.printStackTrace();
            log.error("上传出现错误");
            return ReturnMsgBody.ReturnFail("上传失败");
        }

    }

}
