package com.pat.soe.mapper;

import com.pat.soe.dto.UserDto;
import com.pat.soe.dto.UserDtoForResponse;
import com.pat.soe.dto.UserDtoForSave;
import com.pat.soe.dto.UserDtoForUpdate;
import com.pat.soe.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User userDtoForSaveToUser(UserDtoForSave userDtoForSave) {
        User user = new User();
        user.setEmail(userDtoForSave.email());
        user.setPassword(userDtoForSave.password());
        user.setNickName(userDtoForSave.nickName());
        return user;
    }

    public UserDto userToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getNickName(),
                user.getResult(),
                user.getRole(),
                user.isActive()
        );
    }

    public User userDtoForResponseToUser(UserDtoForResponse userDtoForResponse) {
        User user = new User();
        user.setId(userDtoForResponse.id());
        user.setEmail(userDtoForResponse.email());
        user.setNickName(userDtoForResponse.nickName());
        user.setRole(userDtoForResponse.role());
        return user;
    }

    public User userDtoForUpdateToUser(UserDtoForUpdate userDtoForUpdate) {
        User user = new User();
        user.setId(userDtoForUpdate.id());
        user.setEmail(userDtoForUpdate.email());
        user.setPassword(userDtoForUpdate.password());
        user.setNickName(userDtoForUpdate.nickName());
        user.setRole(userDtoForUpdate.role());
        user.setResult(userDtoForUpdate.result());
        return user;
    }

    public UserDtoForResponse userDtoToUserDtoForResponse(UserDto userDto) {
        return new UserDtoForResponse(
                userDto.id(),
                userDto.email(),
                userDto.nickName(),
                userDto.role()
        );
    }
}
