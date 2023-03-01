package com.pat.SpellsOfEnglish.service.dto.user;

import lombok.Data;

@Data
public class UserDtoForResponse {
    private Long id;
    private String email;
    private String nickName;
    private RoleDto role;
    private String UriForImage;
    private boolean isUsing2FA;
}
