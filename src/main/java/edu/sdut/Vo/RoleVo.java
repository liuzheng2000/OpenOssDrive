package edu.sdut.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/15 19:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo implements Serializable {
    public String roleName;
    public ArrayList<String> roleList;
}
