package com.smartStudy.Planner.dto;

public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete"),
    USER_MODIFY("user:modify");

    private final String permission;

    public String getPermission() {
        return permission;
    }

    Permission(String permission){
        this.permission = permission;
    }


}
