package com.pat.soe.user;

import lombok.Data;

@Data
public class UserSecurityDto {
    private String password;
    private RoleDto role;
    private boolean isActive;
    private String secret;
    private boolean isUsing2FA;
}
