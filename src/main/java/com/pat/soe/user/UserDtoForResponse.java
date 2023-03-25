package com.pat.soe.user;

import lombok.Data;

@Data
public class UserDtoForResponse {
    private Long id;
    private String email;
    private String nickName;
    private RoleDto role;
}
