package edu.sdut.Service.ServiceImpl;



import edu.sdut.Entity.Role;
import edu.sdut.Service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/7 20:58
 */
@Service
@Slf4j
public class RoleImpl {


    @Autowired
    RoleService roleService;
    /**
     * 返回根据路径查找的角色
     */
    public List<String> findAllRoleByPermissionsImpl(String PermissionsName){
        return roleService.findAllRoleByPermissions(PermissionsName);
    }



}
