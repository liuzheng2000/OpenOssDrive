package edu.sdut.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 用户角色权限表
 * @author qingyun
 * @version 1.0
 * @date 2021/11/7 20:48
 */
@Entity
@Table(name = "edu_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 角色
     */
    @Column(name = "role")
    private String Role;

    /**
     * 权限
     */
    @Column(name = "Permissions")
    private String Permissions;

    public Role(String roleName, String role) {
        Role = roleName;
        Permissions = role;
    }
}
