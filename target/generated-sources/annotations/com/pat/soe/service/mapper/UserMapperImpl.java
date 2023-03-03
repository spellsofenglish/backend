package com.pat.soe.service.mapper;

import com.pat.soe.data.entity.User;
import com.pat.soe.service.dto.user.RoleDto;
import com.pat.soe.service.dto.user.UserDto;
import com.pat.soe.service.dto.user.UserDtoForResponse;
import com.pat.soe.service.dto.user.UserDtoForSave;
import com.pat.soe.service.dto.user.UserDtoForUpdate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-01T08:34:20+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userDtoForSaveToUser(UserDtoForSave userDtoForSaving) {
        if ( userDtoForSaving == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDtoForSaving.getEmail() );
        user.setPassword( userDtoForSaving.getPassword() );
        user.setNickName( userDtoForSaving.getNickName() );
        user.setResult( userDtoForSaving.getResult() );
        user.setRole( roleDtoToRole( userDtoForSaving.getRole() ) );
        user.setActive( userDtoForSaving.isActive() );
        user.setSecret( userDtoForSaving.getSecret() );
        user.setUsing2FA( userDtoForSaving.isUsing2FA() );

        return user;
    }

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setEmail( user.getEmail() );
        userDto.setPassword( user.getPassword() );
        userDto.setNickName( user.getNickName() );
        userDto.setResult( user.getResult() );
        userDto.setRole( roleToRoleDto( user.getRole() ) );
        userDto.setActive( user.isActive() );
        userDto.setSecret( user.getSecret() );
        userDto.setUsing2FA( user.isUsing2FA() );

        return userDto;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setEmail( userDto.getEmail() );
        user.setPassword( userDto.getPassword() );
        user.setNickName( userDto.getNickName() );
        user.setResult( userDto.getResult() );
        user.setRole( roleDtoToRole( userDto.getRole() ) );
        user.setActive( userDto.isActive() );
        user.setSecret( userDto.getSecret() );
        user.setUsing2FA( userDto.isUsing2FA() );

        return user;
    }

    @Override
    public User userDtoForResponseToUser(UserDtoForResponse userDtoForResponse) {
        if ( userDtoForResponse == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDtoForResponse.getId() );
        user.setEmail( userDtoForResponse.getEmail() );
        user.setNickName( userDtoForResponse.getNickName() );
        user.setRole( roleDtoToRole( userDtoForResponse.getRole() ) );
        user.setUsing2FA( userDtoForResponse.isUsing2FA() );

        return user;
    }

    @Override
    public User userDtoForUpdateToUser(UserDtoForUpdate userDtoForUpdating) {
        if ( userDtoForUpdating == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDtoForUpdating.getId() );
        user.setEmail( userDtoForUpdating.getEmail() );
        user.setPassword( userDtoForUpdating.getPassword() );
        user.setNickName( userDtoForUpdating.getNickName() );
        user.setResult( userDtoForUpdating.getResult() );
        user.setRole( roleDtoToRole( userDtoForUpdating.getRole() ) );
        user.setActive( userDtoForUpdating.isActive() );
        user.setSecret( userDtoForUpdating.getSecret() );
        user.setUsing2FA( userDtoForUpdating.isUsing2FA() );

        return user;
    }

    @Override
    public UserDtoForResponse userDtoToUserDtoForResponse(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserDtoForResponse userDtoForResponse = new UserDtoForResponse();

        userDtoForResponse.setId( userDto.getId() );
        userDtoForResponse.setEmail( userDto.getEmail() );
        userDtoForResponse.setNickName( userDto.getNickName() );
        userDtoForResponse.setRole( userDto.getRole() );
        userDtoForResponse.setUsing2FA( userDto.isUsing2FA() );

        return userDtoForResponse;
    }

    protected User.Role roleDtoToRole(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        User.Role role;

        switch ( roleDto ) {
            case PLAYER: role = User.Role.PLAYER;
            break;
            case MANAGER: role = User.Role.MANAGER;
            break;
            case ADMINISTRATOR: role = User.Role.ADMINISTRATOR;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + roleDto );
        }

        return role;
    }

    protected RoleDto roleToRoleDto(User.Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDto;

        switch ( role ) {
            case PLAYER: roleDto = RoleDto.PLAYER;
            break;
            case MANAGER: roleDto = RoleDto.MANAGER;
            break;
            case ADMINISTRATOR: roleDto = RoleDto.ADMINISTRATOR;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + role );
        }

        return roleDto;
    }
}
