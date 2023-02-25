package com.pat.SpellsOfEnglish.service.Impl;

import com.pat.SpellsOfEnglish.data.entity.User;
import com.pat.SpellsOfEnglish.data.repository.UserRepository;
import com.pat.SpellsOfEnglish.security.JwtUtils;
import com.pat.SpellsOfEnglish.service.MailService;
import com.pat.SpellsOfEnglish.service.TokenLinkService;
import com.pat.SpellsOfEnglish.service.UserService;
import com.pat.SpellsOfEnglish.service.dto.user.UserDto;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForResponse;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForSave;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForUpdate;
import com.pat.SpellsOfEnglish.service.exception.NotFoundException;
import com.pat.SpellsOfEnglish.service.exception.SoeException;
import com.pat.SpellsOfEnglish.service.mapper.UserMapper;
import com.pat.SpellsOfEnglish.service.plugin.InternalizationMessageManagerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service(value = "userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    public static final String USER_S_IS_NOT_FOUND = "User %s is not found";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenLinkService tokenLinkService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TotpManager totpManager;


    @Value("${app.host}")
    private String host;

    public static final String KET_FOR_EMAIL_RECOVERY_PASSWORD_SUBJECT = "UserService.EmailRecoveryPasswordSubject";
    private static final int REGISTER_TOKEN_ACTIVITY_SECONDS = 60 * 60;
    private static final int RECOVERY_TOKEN_ACTIVITY_SECONDS = 5 * 60;
    public static final String KEY_FOR_EXCEPTION_USER_NOT_FOUND = "UserService.UserNotFound";
    public static final String KEY_FOR_EXCEPTION_EXISTING_EMAIL = "UserService.ExistingEmail";
    public static final String KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN = "UserService.ActivateLinkPattern";
    public static final String KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT = "UserService.UserConfirmationSubject";
    public static final String KEY_FOR_RECOVERY_PASS_LINK_PATTERN = "UserService.RecoveryPassLinkPattern";
    public static final String KEY_FOR_EXCEPTION_WRONG_OLD_PASSWORD = "UserService.WrongOldPassword";
    public static final String KEY_FOR_EXCEPTION_USER_NOT_ACTIVATED = "UserService.UserNotActivated";


    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDtoForResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        return userMapper.userDtoToUserDtoForResponse(userMapper.userToUserDto(user));
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::userToUserDto);
    }

    @Override
    public UserDto create(UserDtoForSave dtoForSave) {
        Optional<User> existing = userRepository.findByEmailActive(dtoForSave.getEmail());
        if (existing.isPresent()) {
            throw new SoeException(String.format(InternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), dtoForSave.getEmail()));
        }
        User entity = userMapper.userDtoForSaveToUser(dtoForSave);
        entity.setRole(User.Role.PLAYER);
        entity.setActive(true);
        String encodedPassword = passwordEncoder.encode(dtoForSave.getPassword());
        entity.setPassword(encodedPassword);
        entity.setEmail(dtoForSave.getEmail().trim());
        User created = userRepository.save(entity);
        return userMapper.userToUserDto(created);
    }

    @Override
    public UserDto update(UserDtoForUpdate user) {
        Optional<User> existing = userRepository.findByEmailActive(user.getEmail());
        if (existing.isPresent() && !existing.get().getId().equals(user.getId())) {
            throw new SoeException(String.format(InternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), user.getEmail()));
        }
        User newUser = userMapper.userDtoForUpdateToUser(user);
        User updated = userRepository.save(newUser);
        return userMapper.userToUserDto(updated);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        if (!user.isActive()) {
            throw new NotFoundException(InternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND));
        }
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public String registerUser(UserDtoForSave dtoForSave) {
        Optional<User> existing = userRepository.findByEmail(dtoForSave.getEmail());
        if (existing.isPresent()) {
            throw new SoeException(String.format(InternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), dtoForSave.getEmail()));
        }
        User entity = userMapper.userDtoForSaveToUser(dtoForSave);
        String encodedPassword = passwordEncoder.encode(dtoForSave.getPassword());
        entity.setPassword(encodedPassword);
        entity.setEmail(dtoForSave.getEmail().trim());
        if (entity.getRole() == null) {
            entity.setRole(User.Role.PLAYER);
        }
        if (dtoForSave.isUsing2FA()) {
            entity.setSecret(tokenLinkService.generate2FAToken());
        }
        entity.setActive(false);
        User created = userRepository.save(entity);
        String token = tokenLinkService.generateToken(REGISTER_TOKEN_ACTIVITY_SECONDS);
//        mailService.sendEmail(created.getEmail(), InternalizationMessageManagerConfig
//                        .getMessage(KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT),
//                String.format(InternalizationMessageManagerConfig
//                        .getMessage(KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN), host, token, created.getId()));
        return totpManager.getUriForImage(created.getSecret());
    }

    @Override
    public String loginUser(String username, String password, String code) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserAppDetails userDetails = (UserAppDetails) authentication.getPrincipal();
        UserDtoForResponse dtoForResponse = getByEmail(userDetails.getUsername());
        if(dtoForResponse.isUsing2FA()){
            verify(username, code);
        }
        return jwtUtils.generateJwtCookie(userDetails).toString();
    }

    @Override
    public void verify(String username, String code) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        if(!totpManager.verifyCode(code, user.getSecret())) {
            throw new SoeException("Code is incorrect");
        }
    }

    @Override
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void recoveryPassword(String email) {
        User existing = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        String token = tokenLinkService.generateToken(RECOVERY_TOKEN_ACTIVITY_SECONDS);
        mailService.sendEmail(email, InternalizationMessageManagerConfig
                        .getMessage(KET_FOR_EMAIL_RECOVERY_PASSWORD_SUBJECT),
                String.format(InternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_RECOVERY_PASS_LINK_PATTERN), host, token, existing.getId()));
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new SoeException(InternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_WRONG_OLD_PASSWORD));
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User existingUser = userMapper.userDtoForResponseToUser(getByEmail(email));
        if (Objects.isNull(existingUser)) {
            throw new NotFoundException(InternalizationMessageManagerConfig
                    .getExceptionMessage(String.format(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        }
        return new UserAppDetails(userRepository.findByEmailActive(email)
                .orElseThrow(() -> new NotFoundException(InternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_ACTIVATED))));
    }
}
