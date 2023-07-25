package com.neo.buysell.model.util;

import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.enumeration.RoleType;
import com.neo.buysell.model.exception.particular.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class Permission {

    public static void checkPermission(User user, Authentication authentication) {
        if (!(isOwner(user, authentication) || isAdmin(authentication))) {
            throw new NotAuthorizedException(HttpStatus.FORBIDDEN);
        }
    }

    private static boolean isAdmin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = false;
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(RoleType.ADMIN.getRole())) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

    private static boolean isOwner(User user, Authentication authentication) {
        return user.getUsername().equals(authentication.getName());
    }

}
