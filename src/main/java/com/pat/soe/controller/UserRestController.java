package com.pat.soe.controller;

import com.pat.soe.dto.UserDtoForSave;
import com.pat.soe.entity.User;
import com.pat.soe.service.api.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserRestController implements GlobalController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody UserDtoForSave user) {
        return userService.create(user);
    }

    @GetMapping
    public Page<User> getAll(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @PatchMapping("/{id}")
    public User updatePartly(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }
}
