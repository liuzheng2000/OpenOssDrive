package edu.sdut.Filter;

import edu.sdut.Service.ServiceImpl.RoleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.awt.*;
import java.util.Collection;
import java.util.List;

/**
 * 路径匹配算法
 * @author qingyun
 * @version 1.0
 * @date 2021/11/7 15:53
 */
@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {



    @Autowired
    RoleImpl roleimpl;
    /**
     * 路径匹配符 直接用以匹配
     */
    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        /*根据请求地址 分析请求该地址需要什么角色*/
        //获取请求地址
        String url = ((FilterInvocation) object).getRequestUrl();
        String[] split = url.split("/");
        String Menu = "Error_User";
        if (split[1].contains("auth")){
            Menu = "Allow_User";
            return SecurityConfig.createList(Menu);
        }
        List<String> allRoleByPermissionsImpl = roleimpl.findAllRoleByPermissionsImpl(split[1]);

        if (!allRoleByPermissionsImpl.isEmpty()){
            String[] Roles = new String[allRoleByPermissionsImpl.size()];
            for (int i = 0; i < allRoleByPermissionsImpl.size(); i++) {
                Roles[i] = allRoleByPermissionsImpl.get(i);
            }
            return SecurityConfig.createList(Roles);
        }
        return SecurityConfig.createList(Menu);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
