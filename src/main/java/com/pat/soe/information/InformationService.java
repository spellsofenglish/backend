package com.pat.soe.information;

import com.pat.soe.service.dto.user.UserDto;
import com.pat.soe.service.dto.user.UserDtoForSave;
import com.pat.soe.service.dto.user.UserDtoForUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InformationService {
    UserDto getById(Long id);

    Page<UserDto> getAll(Pageable pageable);

    UserDto create(UserDtoForSave user);

    UserDto update(UserDtoForUpdate user);

    void delete(Long id);
}
