package com.example.demo.bean.enumeration;

import com.example.demo.bean.consts.UserAuthorities;

public enum Role {

    ROLE_USER(UserAuthorities.USER_AUTHORITIES),
    ROLE_HR(UserAuthorities.HR_AUTHORITIES),
    ROLE_MANAGER(UserAuthorities.MANAGER_AUTHORITIES),
    ROLE_ADMIN(UserAuthorities.ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(UserAuthorities.SUPER_USER_AUTHORITIES);

    private String[] authorities;

    Role(String... userAuthorities) {
        this.authorities = userAuthorities;
    }
    public String[] getAuthorities(){
        return authorities;
    }
}
