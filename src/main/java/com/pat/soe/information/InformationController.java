package com.pat.soe.information;

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
    public InformationDto create(@RequestBody InformationDtoForSave dtoForSave) {
        return informationService.create(dtoForSave);
    }

    @GetMapping
    public Page<InformationDto> getAll(Pageable pageable) {
        return informationService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public InformationDto getById(@PathVariable Long id){
        return informationService.getById(id);
    }

    @PutMapping("/{id}")
    public InformationDto update(@PathVariable Long id, @RequestBody InformationDto dto) {
        dto.setId(id);
        return informationService.update(dto);
    }

    @PatchMapping("/{id}")
    public InformationDto updatePartly(@PathVariable Long id, @RequestBody InformationDto informationDto) {
        informationDto.setId(id);
        return informationService.update(informationDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        informationService.delete(id);
    }
}
