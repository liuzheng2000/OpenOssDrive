package edu.sdut.Service;

import edu.sdut.Entity.Role;
import edu.sdut.Entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/7 20:52
 */
@Service
public interface RoleService extends CrudRepository<Role, Integer> {
    /**
     * 根据路径查找所有角色
     * @param PermissionsName
     * @return
     */
    @Query(value = "select Role from edu_role where Permissions = :PermissionsName",nativeQuery = true)
    List<String> findAllRoleByPermissions(String PermissionsName);

    /**
     * 批量插入权限
     * @param RoleName
     * @param RoleList
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void insertAllRoles(String RoleName, List<String> RoleList);

}
