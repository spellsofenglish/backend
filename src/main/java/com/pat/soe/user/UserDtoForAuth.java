package com.pat.soe.user;

import lombok.Data;

@Data
public class UserDtoForAuth {
    private String email;
    private String password;
    private String secret;
    private boolean isUsing2FA;
}