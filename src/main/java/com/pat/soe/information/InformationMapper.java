package com.pat.soe.information;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InformationMapper {
    InformationDto informationToInformationDto(Information information);

    Information informationDtoToInformation(InformationDto informationDto);

    Information informationDtoForSaveToInformation(InformationDtoForSave dtoForSave);
}
