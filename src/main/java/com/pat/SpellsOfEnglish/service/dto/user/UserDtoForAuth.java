package com.pat.SpellsOfEnglish.service.dto.user;

import lombok.Data;

@Data
public class UserDtoForAuth {
    private String email;
    private String password;
}
