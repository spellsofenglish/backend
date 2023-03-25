package com.pat.soe.user;

import lombok.Data;

@Data
public class UserDtoForAuth {
    private String email;
    private String password;
}
