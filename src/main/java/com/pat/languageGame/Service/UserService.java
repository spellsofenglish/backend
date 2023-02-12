package com.pat.languageGame.Service.Impl;

import com.pat.languageGame.Data.Entity.User;

import java.util.List;

public interface UserService {
    User getById(Long id) throws Exception;

    List<User> getAll();

    User create(User user);

    User update(User user);

    void delete(Long id) throws Exception;
}
