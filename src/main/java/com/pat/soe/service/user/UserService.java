package com.pat.soe.service.user;

import com.pat.soe.dto.user.UserDto;
import com.pat.soe.dto.user.UserDtoForResponse;
import com.pat.soe.dto.user.UserDtoForSave;
import com.pat.soe.dto.user.UserDtoForUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserDto getById(UUID id);

    UserDtoForResponse getByEmail(String email);

    Page<UserDto> getAll(Pageable pageable);

    UserDto create(UserDtoForSave user);

    UserDto update(UserDtoForUpdate user);

    void delete(UUID id);

    void registerUser(UserDtoForSave dto);

    String loginUser(String username, String password);

    void verify(String username, String code);

    void activateUser(UUID userId);

    void recoveryPassword(String email);

    void changePassword(UUID userId, String newPassword);

    void updatePassword(UUID userId, String oldPassword, String newPassword);
}
