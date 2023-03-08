package com.pat.soe.user;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String nickName;
    private String result;
    private UserSecurity userSecurity;
}
