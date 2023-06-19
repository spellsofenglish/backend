package com.pat.soe.dto.user;

import com.pat.soe.entity.User;

import java.util.UUID;

public record UserDtoForResponse(UUID id,
                                 String email,
                                 String nickName,
                                 User.Role role) {
}
