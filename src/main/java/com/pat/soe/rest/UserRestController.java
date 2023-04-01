package com.pat.soe.rest;

import com.pat.soe.user.UserService;
import com.pat.soe.user.UserDto;
import com.pat.soe.user.UserDtoForSave;
import com.pat.soe.user.UserDtoForUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${BASE_PATH:""}/${app.version}/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDtoForSave user) {
        return userService.create(user);
    }

    @GetMapping
    public Page<UserDto> getAll(Pageable pageable) {
        return userService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDtoForUpdate user) {
        user.setId(id);
        return userService.update(user);
    }

    @PatchMapping("/{id}")
    public UserDto updatePartly(@PathVariable Long id, @RequestBody UserDtoForUpdate user) {
        user.setId(id);
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
