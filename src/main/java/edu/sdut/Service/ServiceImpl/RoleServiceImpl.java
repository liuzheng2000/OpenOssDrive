package edu.sdut.Service.ServiceImpl;

import edu.sdut.Entity.Role;
import edu.sdut.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/15 19:25
 */
@Service
public class RoleServiceImpl {
    @Autowired
    RoleService roleService;
    /**
     * 批量插入权限
     * @param RoleName
     * @param RoleList
     */
    public void insertAllRoles(String RoleName, List<String> RoleList){
        List<edu.sdut.Entity.Role> roles = new ArrayList<>();
        for (String role : RoleList) {
            edu.sdut.Entity.Role insertRole = new Role(RoleName, role);
            roles.add(insertRole);
        }
        roleService.saveAll(roles);
    }
}
