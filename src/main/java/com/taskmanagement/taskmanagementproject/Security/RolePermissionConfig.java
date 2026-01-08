package com.taskmanagement.taskmanagementproject.Security;

import com.taskmanagement.taskmanagementproject.Enum.Permission;
import com.taskmanagement.taskmanagementproject.Enum.Role;
import org.springframework.security.core.parameters.P;

import java.util.*;

public class RolePermissionConfig {
    public static Map<Role, Set<Permission>> getPermission(){
    Map<Role, Set<Permission>> map = new HashMap<>();
    map.put(Role.ADMIN,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,Permission.ISSUE_CREATE, Permission.ISSUE_EDIT,Permission.ISSUE_DELETE,Permission.COMMEMT_ADD,Permission.COMMEMT_DELETE,Permission.USER_MANAGE)));
    map.put(Role.MANAGER, new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,Permission.ISSUE_CREATE, Permission.ISSUE_EDIT,Permission.COMMEMT_ADD)));
    map.put(Role.DEVELOPER, new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,Permission.ISSUE_EDIT,Permission.COMMEMT_ADD)));
    map.put(Role.TESTER, new  HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,Permission.COMMEMT_ADD)));

    return map;
    }

}