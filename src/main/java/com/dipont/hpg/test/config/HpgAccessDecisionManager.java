package com.dipont.hpg.test.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author alex
 * @date 2019/8/23  9:21
 */
@Component
public class HpgAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 无权限访问
        if(CollectionUtils.isEmpty(collection)){
            throw new AccessDeniedException("无访问权限.");
        }
        for (ConfigAttribute configAttribute : collection) {
            String needRole = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {

                if (needRole.trim().equals(grantedAuthority.getAuthority().trim())) {
                    return;
                }
            }
        }
        throw  new InsufficientAuthenticationException("无访问权限.");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }
}
