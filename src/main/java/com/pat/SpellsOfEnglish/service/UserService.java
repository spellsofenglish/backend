package com.pat.SpellsOfEnglish.service;

import com.pat.SpellsOfEnglish.service.dto.user.UserDto;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForSave;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto getById(Long id);

    UserDto getByEmail(String email);

    Page<UserDto> getAll(Pageable pageable);

    UserDto create(UserDtoForSave user);

    UserDto update(UserDtoForUpdate user);

    void delete(Long id);

    void registerUser(UserDtoForSave dto);

    void activateUser(Long userId);

    void recoveryPassword(String email);

    void changePassword(Long userId, String newPassword);

    void updatePassword(Long userId, String oldPassword, String newPassword);
}
