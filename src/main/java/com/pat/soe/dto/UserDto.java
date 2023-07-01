package com.pat.soe.dto;

import com.pat.soe.entity.User;

import java.util.UUID;

public record UserDto(UUID id,
                      String email,
                      String password,
                      String nickName,
                      String result,
                      User.Role role,
                      boolean isActive) {
}
