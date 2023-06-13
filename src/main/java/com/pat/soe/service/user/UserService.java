package com.pat.soe.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto getById(Long id);

    UserDtoForResponse getByEmail(String email);

    Page<UserDto> getAll(Pageable pageable);

    UserDto create(UserDtoForSave user);

    UserDto update(UserDtoForUpdate user);

    void delete(Long id);

    void registerUser(UserDtoForSave dto);

    String loginUser(String username, char[] password);

    void verify(String username, String code);

    void activateUser(Long userId);

    void recoveryPassword(String email);

    void changePassword(Long userId, char[] newPassword);

    void updatePassword(Long userId, char[] oldPassword, char[] newPassword);
}
