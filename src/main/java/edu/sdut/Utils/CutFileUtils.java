package edu.sdut.Utils;

import java.io.File;
import java.io.FileInputStream;

/**
 * 文件切割
 * @author qingyun
 * @version 1.0
 * @date 2021/10/30 17:12
 */
public class CutFileUtils {

    /**
     * 文件分割下载（并未完全实现）
     * @param threadID
     * @param downFilePath
     * @param startIndex
     * @param endIndex
     */
    public static byte[] cutFileDownLoad(int threadID ,String downFilePath,long startIndex,long endIndex){
        System.out.println("线程" + threadID + "读取" + startIndex +"字节~"+endIndex+"字节");
        byte[] buffer = new byte[1024];
        File file = new File(downFilePath);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.skip(startIndex);
            fileInputStream.read(buffer);
            for (byte b : buffer) {
                if (b!=0){
                    System.out.println(b);
                }
            }
            fileInputStream.close();
            return buffer;
        }catch (Exception e){
            e.printStackTrace();
        }
        return buffer;
    }
}
