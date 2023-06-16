package com.pat.soe.dto.user;

public record UserDtoForUpdatePass(Long id,
                                   String oldPassword,
                                   String newPassword) {

}
