package edu.sdut.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录的实体类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/5 20:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String username;
    private String password;
}
