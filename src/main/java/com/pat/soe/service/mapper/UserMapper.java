package com.pat.soe.service.mapper;

import com.pat.soe.data.entity.User;
import com.pat.soe.service.dto.user.UserDto;
import com.pat.soe.service.dto.user.UserDtoForResponse;
import com.pat.soe.service.dto.user.UserDtoForSave;
import com.pat.soe.service.dto.user.UserDtoForUpdate;
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
