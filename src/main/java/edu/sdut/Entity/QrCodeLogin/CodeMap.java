package edu.sdut.Entity.QrCodeLogin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据储存中心
 * @author qingyun
 * @version 1.0
 * @date 2021/11/19 16:40
 */
@AllArgsConstructor
public class CodeMap {
    final private static HashMap<String,String> CodeMap = new HashMap<String,String>();

    public static HashMap<String, String> getCodeMap() {
        return CodeMap;
    }

    public static void put(String ID ,String State ){
        CodeMap.put(ID,State);
    }

    public static String get(String uuid) {
        return CodeMap.get(uuid);
    }

    public static void replace(String uuid, String qrCodeStateSuccess) {
         CodeMap.replace(uuid,qrCodeStateSuccess);
    }
}
