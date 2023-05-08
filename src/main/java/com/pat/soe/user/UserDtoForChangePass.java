package com.pat.soe.user;

import lombok.Data;

@Data
public class UserDtoForChangePass {
    private Long id;
    private String newPassword;
}
