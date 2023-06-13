package com.pat.soe.dto.user;

public record UserDtoForSave(String email,
                             char[] password,
                             String nickName) {
}
