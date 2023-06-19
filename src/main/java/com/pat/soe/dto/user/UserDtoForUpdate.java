package com.pat.soe.dto.user;

import com.pat.soe.entity.User;

import java.util.UUID;

public record UserDtoForUpdate(UUID id,
                               String email,
                               String password,
                               String nickName,
                               String result,
                               User.Role role,
                               boolean isActive) {

}
