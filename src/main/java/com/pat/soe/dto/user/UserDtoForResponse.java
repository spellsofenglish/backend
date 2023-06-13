package com.pat.soe.dto.user;

import com.pat.soe.entity.User;

public record UserDtoForResponse(Long id,
                                 String email,
                                 String nickName,
                                 User.Role role) {
}
