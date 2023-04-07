package com.pat.soe.user;

import lombok.Data;

@Data
public class UserDtoForUpdatePass {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
