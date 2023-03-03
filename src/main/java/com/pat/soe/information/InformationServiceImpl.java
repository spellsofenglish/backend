package com.pat.soe.information;

import com.pat.soe.service.dto.user.UserDto;
import com.pat.soe.service.dto.user.UserDtoForSave;
import com.pat.soe.service.dto.user.UserDtoForUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service(value = "informationService")
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;

    @Override
    public UserDto getById(Long id) {
        return null;
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public UserDto create(UserDtoForSave user) {
        return null;
    }

    @Override
    public UserDto update(UserDtoForUpdate user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
