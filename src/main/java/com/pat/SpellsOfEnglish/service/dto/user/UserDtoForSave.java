package com.pat.SpellsOfEnglish.service.dto.user;

import lombok.Data;

@Data
public class UserDtoForSave {
    private String email;
    private String password;
    private String nickName;
    private String result;
    private RoleDto role;
    private boolean isActive;
    private String secret;
    private boolean isUsing2FA;
}
