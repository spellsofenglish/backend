package com.pat.soe.dto.user;

public record UserDtoForUpdatePass(Long id,
                                   char[] oldPassword,
                                   char[] newPassword) {

}
