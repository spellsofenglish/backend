package ru.spellsofenglish.authservise.dto;

import ru.spellsofenglish.authservise.entity.User;

import java.util.UUID;

public record UserDtoForResponse(UUID id,
                                 String email,
                                 String nickName,
                                 User.Role role) {
}
