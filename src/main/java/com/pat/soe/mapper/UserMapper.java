package com.pat.soe.mapper;

import com.pat.soe.dto.UserDtoForResponse;
import com.pat.soe.dto.UserDtoForSave;
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

    public UserDtoForResponse userDtoToUserDtoForResponse(User userDto) {
        return new UserDtoForResponse(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getNickName(),
                userDto.getRole()
        );
    }
}
