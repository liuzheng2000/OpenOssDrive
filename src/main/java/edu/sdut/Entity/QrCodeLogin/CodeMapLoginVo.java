package edu.sdut.Entity.QrCodeLogin;

import lombok.AllArgsConstructor;

import java.util.HashMap;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/19 21:45
 */
@AllArgsConstructor
public class CodeMapLoginVo {

    final private static HashMap<String,LoginVo> CodeMap = new HashMap<String,LoginVo>();
    public static HashMap<String,LoginVo> getCodeMap() {
        return CodeMap;
    }

    public static void put(String ID ,LoginVo State ){
        CodeMap.put(ID,State);
    }

    public static LoginVo get(String uuid) {
        return CodeMap.get(uuid);
    }


}
