package edu.sdut.Service.ServiceImpl;

import edu.sdut.Entity.QrCodeLogin.CodeMap;
import edu.sdut.Entity.QrCodeLogin.CodeMapLoginVo;
import edu.sdut.Entity.QrCodeLogin.LoginVo;
import edu.sdut.Entity.QrCodeLogin.QrCodeState;
import edu.sdut.Entity.User;
import edu.sdut.Service.UserRepository;
import edu.sdut.Utils.JwtTokenUtils;
import edu.sdut.Utils.SnowflakeId.SnowflakeId;
import edu.sdut.Utils.Zxing.QRCodeUtil;
import edu.sdut.Vo.ReturnMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * 用户service
 * @author qingyun
 * @version 1.0
 * @date 2021/11/5 23:20
 */
@Slf4j
@Service
public class UserImpl  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    /**
     * 生成UUID
     */
    @Autowired
    SnowflakeId snowflakeId;
    /**
     * 注册用户
     * @param registerUser
     * @return
     */
    public ReturnMsgBody registerUser(Map<String, String> registerUser) {
        User user = new User();
        user.setUsername(registerUser.get("username"));
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        user.setRole("ROLE_USER");
        User save = userRepository.save(user);
        log.warn("用户注册成功");
        return ReturnMsgBody.ReturnSuccess("用户注册成功","1");
    }


    /**
     * 修改用户权限  Admin级别
     * @param userName  用户名
     * @param Role      待修改的用户权限
     * @return
     */
    public ReturnMsgBody ModifyUserPermissions(String userName,String Role){
        Integer  count = userRepository.modifyUserPermissions(userName,Role);
        return ReturnMsgBody.ReturnSuccess("权限修改成功",count);
    }

    /**
     * 修改用户密码 Admin级别
     * @param userName
     * @param passWord
     * @return
     */
    public ReturnMsgBody ModifyUserPassWord(String userName , String passWord){
        passWord = bCryptPasswordEncoder.encode(passWord);
        Integer count = userRepository.modifyUserPassWord(userName, passWord);
        return ReturnMsgBody.ReturnSuccess("密码修改成功",count);
    }


    /**
     * 实现二维码登录
     * 返回数组流
     * @return
     */
    public byte[] QRCodeSignIn(String id) throws Exception {
        String StringID = String.valueOf(id);
        CodeMap.put(StringID, QrCodeState.QrCodeStateWait);
        return QRCodeUtil.getQrCodeImageBytes(StringID,120,120,null);
    }


    /**
     * 二维码登录信息
     * @param loginVo
     * @return
     */
    public void QrCodeLoginByCodeTell(LoginVo loginVo){
        //判断手机端上传的二维码信息
        String uuid = loginVo.getUUID();
        String State = CodeMap.get(uuid);
        //如果现在是扫码确认成功状态
        if (QrCodeState.QrCodeStatePass.equals(State)){
            //更具用户名查询实体类
            User byUsername = userRepository.findByUsername(loginVo.getUserName());
            //如果是管理员类 报警
            if ("ROLE_ADMIN".equals(byUsername.getRole())){
                log.error("入侵提醒，扫码登录管理员账户");
                return;
            }
            //设置登录成功
            CodeMap.replace(uuid,QrCodeState.QrCodeStateSuccess);
            CodeMapLoginVo.put(uuid,loginVo);
        }else {

        }

    }


    /**
     * 电脑端登录信息
     * @return
     */
    public ReturnMsgBody QrCodeLoginByCodeComputer(String id){

        //尚未存在
        if (null==CodeMap.get(id)){
            return ReturnMsgBody.ReturnFail("未扫码状态");
        }

        switch (CodeMap.get(id)){
            case QrCodeState.QrCodeStateWait:{
                /**
                 * 未扫码状态
                 */
                return ReturnMsgBody.ReturnFail("未扫码状态");
            }

            /**
             * 扫码等待确认状态
             */
            case QrCodeState.QrCodeStatePass:{
                return ReturnMsgBody.ReturnFail("扫码等待确认状态");
            }
            /**
             * 扫码成功状态
             */
            case QrCodeState.QrCodeStateSuccess: {

                LoginVo loginVo = CodeMapLoginVo.get(id);

                System.out.println(loginVo.getUserName());

                User byUsername = userRepository.findByUsername(loginVo.getUserName());
                //如果是管理员类 报警
                if ("ROLE_ADMIN".equals(byUsername.getRole())){
                    log.error("入侵提醒，扫码登录管理员账户");
                    return ReturnMsgBody.ReturnFail("管理员类不可扫码");
                }
                String token = JwtTokenUtils.createToken(byUsername.getUsername(), byUsername.getRole());

                return ReturnMsgBody.ReturnSuccess("扫码成功状态",token);
            }
            default:
                return ReturnMsgBody.ReturnFail("未知错误");
        }

    }



}
