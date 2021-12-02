package edu.sdut.Entity.QrCodeLogin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/19 17:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {

    /**
     * //用户信息
     */
    private String UserName;

    /**
     * //二维码UUID
     */
    private String UUID;

}
