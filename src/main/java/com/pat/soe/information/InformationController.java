package com.pat.soe.information;

import com.pat.soe.service.dto.user.UserDto;
import com.pat.soe.service.dto.user.UserDtoForSave;
import com.pat.soe.service.dto.user.UserDtoForUpdate;
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
@RequestMapping("api/${app.version}/information")
@RequiredArgsConstructor
public class InformationController {
    private final InformationService informationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDtoForSave user) {
        return informationService.create(user);
    }

    @GetMapping
    public Page<UserDto> getAll(Pageable pageable) {
        return informationService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        return informationService.getById(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDtoForUpdate user) {
        user.setId(id);
        return informationService.update(user);
    }

    @PatchMapping("/{id}")
    public UserDto updatePartly(@PathVariable Long id, @RequestBody UserDtoForUpdate user) {
        user.setId(id);
        return informationService.update(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        informationService.delete(id);
    }
}
