package edu.sdut.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 路径匹配角色
 * @author qingyun
 * @version 1.0
 * @date 2021/11/7 15:51
 */
@Component
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {

            if ("Allow_User".equals(configAttribute.getAttribute())) {
                return;
            }
            if ("Error_User".equals(configAttribute.getAttribute())) {
                    log.error("非法请求");
                    throw new AccessDeniedException("非法请求");
            }

            //获取当前登录用户的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            String attribute = configAttribute.getAttribute();
            for (GrantedAuthority authority : authorities) {
                String authorityValue = authority.getAuthority();
                if (authorityValue.equals(attribute)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("非法请求");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
