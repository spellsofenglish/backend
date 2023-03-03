package com.pat.soe.information;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InformationService {
    InformationDto getById(Long id);

    Page<InformationDto> getAll(Pageable pageable);

    InformationDto create(InformationDtoForSave dtoForSave);

    InformationDto update(InformationDto dto);

    void delete(Long id);
}
