package com.pat.soe.dto.user;

import com.pat.soe.entity.User;

public record UserDto(Long id,
                      String email,
                      String password,
                      String nickName,
                      String result,
                      User.Role role,
                      boolean isActive) {
}
