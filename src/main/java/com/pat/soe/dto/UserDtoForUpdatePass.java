package com.pat.soe.dto;

import java.util.UUID;

public record UserDtoForUpdatePass(UUID id,
                                   String oldPassword,
                                   String newPassword) {

}
