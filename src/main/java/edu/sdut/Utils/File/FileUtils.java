package edu.sdut.Utils.File;

import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 文件操作工具类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/9 21:57
 */
@Configuration
public class FileUtils {

    public static long getFileContentLength(String name){
        File file = new File(name);
        return file.exists() && file.isFile()?file.length() : 0;
    }


    /**
     * 设置本地文件地址
     * @param fileName
     * @param Path
     * @return
     */
    public static String setLocalFilePath(String fileName , String Path){
        return Path+fileName;
    }
}
