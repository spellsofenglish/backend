package com.pat.SpellsOfEnglish.service.mapper;

import com.pat.SpellsOfEnglish.data.entity.User;
import com.pat.SpellsOfEnglish.service.dto.user.UserDto;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForResponse;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForSave;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForUpdate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoForSaveToUser(UserDtoForSave userDtoForSaving);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    User userDtoForResponseToUser(UserDtoForResponse userDtoForResponse);

    User userDtoForUpdateToUser(UserDtoForUpdate userDtoForUpdating);

    UserDtoForResponse userDtoToUserDtoForResponse(UserDto userDto);
}
