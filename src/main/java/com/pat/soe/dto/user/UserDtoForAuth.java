package com.pat.soe.dto.user;

public record UserDtoForAuth(String email,
                             char[] password) {
}
