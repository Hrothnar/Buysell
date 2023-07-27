package com.neo.buysell.model.enumeration;

import com.neo.buysell.model.dto.ImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum RoleType {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleWithPrefix;

    private final static String PREFIX = "ROLE_";
    private static final Logger LOG = LoggerFactory.getLogger(RoleType.class);

    RoleType(String roleWithPrefix) {
        this.roleWithPrefix = roleWithPrefix;
    }

    public static String getPreparedRole(String role) {
        if (role == null) {
            LOG.warn("Role has not been chosen");
            return RoleType.USER.roleWithPrefix;
        }
        return role.startsWith(PREFIX) ? role : PREFIX + role;
    }

    public String getRole() {
        return roleWithPrefix;
    }
}
