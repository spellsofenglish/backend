package com.pat.soe.service.api;

import com.pat.soe.dto.UserDtoForResponse;
import com.pat.soe.dto.UserDtoForSave;
import com.pat.soe.entity.User;
import jakarta.servlet.http.Cookie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    User getById(UUID id);

    UserDtoForResponse getByEmail(String email);

    Page<User> getAll(Pageable pageable);

    User create(UserDtoForSave user);

    User update(User user);

    void delete(UUID id);

    void registerUser(UserDtoForSave dto);

    Cookie loginUser(String username, String password);

    void verify(String username, String code);

    void activateUser(UUID userId);

    void recoveryPassword(String email);

    void changePassword(UUID userId, String newPassword);

    void updatePassword(UUID userId, String oldPassword, String newPassword);
}
