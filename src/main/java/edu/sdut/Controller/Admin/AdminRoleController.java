package edu.sdut.Controller.Admin;

import edu.sdut.Service.ServiceImpl.RoleImpl;
import edu.sdut.Service.ServiceImpl.RoleServiceImpl;
import edu.sdut.Vo.ReturnMsgBody;
import edu.sdut.Vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/15 15:37
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminRoleController {
    @Autowired
    RoleServiceImpl roleServiceImpl;

    /**
     *批量插入用户权限
     * @param roleVo
     */
    @PostMapping("/insertAllRoles")
    public ReturnMsgBody insertAllRoles(@RequestBody RoleVo roleVo){
        roleServiceImpl.insertAllRoles(roleVo.getRoleName(),roleVo.getRoleList());
        return ReturnMsgBody.ReturnSuccess("权限修改成功","1");
    }
}
