package edu.sdut.Controller.Admin;

import edu.sdut.Service.ServiceImpl.UserImpl;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理者权限操作平台 edu_user表
 * @author qingyun
 * @version 1.0
 * @date 2021/11/15 14:40
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminUserController {

    @Autowired
    UserImpl userimpl;

    /**
     * 修改用户权限 Admin级别
     * @param userName
     * @param Role
     * @return
     */
    @PostMapping("/UpdateUserRole")
    public ReturnMsgBody ModifyUserPermissions(String userName, String Role){
        return userimpl.ModifyUserPermissions(userName,Role);
    }

    /**
     * 修改用户密码 Admin级别
     * @param userName
     * @param PassWord
     * @return
     */
    @PostMapping("/UpdateUserPassword")
    public ReturnMsgBody ModifyUserPassword(String userName, String PassWord){
        return userimpl.ModifyUserPassWord(userName,PassWord);
    }



}
