package com.pat.languageGame.Service.Impl;

import com.pat.languageGame.Data.Entity.User;
import com.pat.languageGame.Data.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
@RequiredArgsConstructor
public class UserServiceImpl implements com.pat.languageGame.Service.Impl.UserService {
    public static final String USER_NOT_FOUND = "User not found";
    private final UserRepository userRepository;

    @Override
    public User getById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception(USER_NOT_FOUND));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception(USER_NOT_FOUND));
        user.setActive(false);
        userRepository.save(user);
    }
}
