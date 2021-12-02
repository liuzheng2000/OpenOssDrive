package edu.sdut.Service;

import edu.sdut.Entity.User;
import edu.sdut.Vo.ReturnMsgBody;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 用户实体类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/3 22:32
 */
public interface UserRepository extends CrudRepository<User,Integer> {
    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    User findByUsername(String username);


    /**
     *    //增删改操作必须要有这个注解
     *     // 事务的注解
     *修改用户权限
     * @param userName  用户名
     * @param Role      待修改的用户权限
     * @return
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "UPDATE edu_user set role  = :Role WHERE username = :userName ",nativeQuery = true)
    Integer modifyUserPermissions(String userName, String Role);

    /**
     *  修改用户密码
     * @param userName
     * @param passWord
     * @return
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "UPDATE edu_user set password  = :passWord WHERE username = :userName ",nativeQuery = true)
    Integer modifyUserPassWord(String userName,String passWord);

}
