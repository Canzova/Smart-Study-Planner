package com.smartStudy.Planner.securtiy;

import com.smartStudy.Planner.dto.Permission;
import com.smartStudy.Planner.dto.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RolePermissionMatcher {

    private static final Map<Role,Set<Permission>> permissionMap = Map.of(
            Role.ADMIN, Set.of(Permission.USER_DELETE, Permission.USER_MODIFY, Permission.USER_WRITE, Permission.USER_READ),
            Role.GUEST, Set.of(Permission.USER_READ, Permission.USER_WRITE)
    );

    public static Set<SimpleGrantedAuthority> getPermissions(Role role) {
        return permissionMap.get(role).stream().
                map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
