package com.workday.constant;

public enum Roles {
    ROL_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    public String getRolName() {
        return rolName;
    }

    private String rolName;

    Roles(String rolName) {
        this.rolName = rolName;
    }
}
