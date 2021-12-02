package edu.sdut.Controller;

import edu.sdut.Entity.QrCodeLogin.LoginVo;
import edu.sdut.Service.ServiceImpl.UserImpl;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户控制类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/5 23:08
 */
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserImpl userImpl;

    /**
     * 用户注册接口
     * @param registerUser
     * @return
     */
    @PostMapping("/register")
    public ReturnMsgBody registerUser(@RequestBody Map<String,String> registerUser){
        return userImpl.registerUser(registerUser);
    }

    /**
     * 实现二维码登录
     * 返回二维码数组流
     * @return
     */
    @PostMapping("/qrCodeSignIn")
    public byte[] QRCodeSignIn(String id) throws Exception {
        return userImpl.QRCodeSignIn(id);
    }


    /**
     * (手机端发送)二维码登录信息
     * @param loginVo
     * @return
     */
    @PostMapping("/qrCodeSignInTell")
    public void QrCodeLoginByCodeTell(@RequestBody LoginVo loginVo){
        userImpl.QrCodeLoginByCodeTell(loginVo);
    }



    /**
     * (电脑端发送)二维码登录信息
     * @param
     * @return
     */
    @PostMapping("/qrCodeSignInComputer")
    public ReturnMsgBody QrCodeLoginByCodeComputer(String id){
        return userImpl.QrCodeLoginByCodeComputer(id);
    }



}
