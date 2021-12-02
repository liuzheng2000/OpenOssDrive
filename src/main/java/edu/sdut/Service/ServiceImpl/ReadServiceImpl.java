package edu.sdut.Service.ServiceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.sdut.Service.ReadService;
import edu.sdut.Utils.JwtTokenUtils;
import edu.sdut.Vo.ReturnMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户接口
 * @author qingyun
 * @version 1.0
 * @date 2021/10/28 9:17
 */
@Service
@Slf4j
public class ReadServiceImpl implements ReadService {

    /**
     * 根据文件路径获取文件目录  用户接口
     * @param FilePath
     * @return
     */
    @Override
    public ReturnMsgBody ReadFile(StringBuilder FilePath , HttpServletRequest httpServletRequest){
        /**
         * 获取URL
         */
        String userName = JwtTokenUtils.getUserNameByHttpServletRequest(httpServletRequest);
        Map<String, String> FileTypeAndFileName = new HashMap<>();
        try{
            File file = new File(FilePath.toString()+"/"+userName);
            File[] fileNameLists = file.listFiles();
            for (File fileNameList : fileNameLists) {
                FileTypeAndFileName.put(fileNameList.toString(), Files.probeContentType(fileNameList.toPath()));
            }
            log.info(FilePath+"路径文件列表获取完成");
            //将Map转为JSON并返回 //TODO
            return  ReturnMsgBody.ReturnSuccess("文件目录浏览完成",FileTypeAndFileName);
        }catch (Exception e){
            e.printStackTrace();
            log.error("文件列表获取失败");
            return ReturnMsgBody.ReturnFail("文件列表获取失败");
        }

    }
}
