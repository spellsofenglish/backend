package ru.spellsofenglish.authservise.mapper;

import org.springframework.stereotype.Component;
import ru.spellsofenglish.authservise.dto.UserDtoForResponse;
import ru.spellsofenglish.authservise.dto.UserDtoForSave;
import ru.spellsofenglish.authservise.entity.User;

@Component
public class UserMapper {
    public User userDtoForSaveToUser(UserDtoForSave userDtoForSave) {
        User user = new User();
        user.setEmail(userDtoForSave.email());
        user.setPassword(userDtoForSave.password());
        user.setNickName(userDtoForSave.nickName());
        return user;
    }

    public UserDtoForResponse userDtoToUserDtoForResponse(User user) {
        return new UserDtoForResponse(
                user.getId(),
                user.getEmail(),
                user.getNickName(),
                user.getRole()
        );
    }
}
