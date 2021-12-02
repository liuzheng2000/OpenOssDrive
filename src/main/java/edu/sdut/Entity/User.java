package edu.sdut.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 用户实体类
 * @author qingyun
 * @version 1.0
 * @date 2021/11/3 21:34
 */
@Entity
@Table(name = "edu_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }
}
