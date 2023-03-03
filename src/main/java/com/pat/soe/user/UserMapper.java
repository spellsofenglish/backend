package com.pat.soe.user;

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
