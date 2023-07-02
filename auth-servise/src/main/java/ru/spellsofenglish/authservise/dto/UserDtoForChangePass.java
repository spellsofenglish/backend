package ru.spellsofenglish.authservise.dto;

import lombok.Data;

@Data
public class UserDtoForChangePass {
    private Long id;
    private String newPassword;
}
