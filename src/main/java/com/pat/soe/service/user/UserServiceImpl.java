package com.pat.soe.service.user;

import com.pat.soe.dto.user.UserDto;
import com.pat.soe.dto.user.UserDtoForResponse;
import com.pat.soe.dto.user.UserDtoForSave;
import com.pat.soe.dto.user.UserDtoForUpdate;
import com.pat.soe.entity.User;
import com.pat.soe.exception.UserNotFoundException;
import com.pat.soe.exception.UserValidationException;
import com.pat.soe.mapper.user.UserMapper;
import com.pat.soe.message.user.UserInternalizationMessageManagerConfig;
import com.pat.soe.repository.user.UserRepository;
import com.pat.soe.security.JwtUtils;
import com.pat.soe.service.mail.MailService;
import com.pat.soe.service.token.TokenLinkService;
import org.apache.commons.lang3.CharUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    public static final String EMAIL_NOT_CORRECT = "UserService.NotCorrectEmail";
    public static final String PASSWORD_NOT_CORRECT = "UserService.NotCorrectPassword";
    public static final String NICKNAME_NOT_CORRECT = "UserService.NotCorrectNickname";
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

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenLinkService tokenLinkService;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Value("${app.host}")
    private String host;
    @Value("${server.servlet.contextPath}")
    private String contextPath;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, TokenLinkService tokenLinkService, MailService mailService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenLinkService = tokenLinkService;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDtoForResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
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
        validation(dtoForSave);
        User entity = userMapper.userDtoForSaveToUser(dtoForSave);
        entity.setRole(User.Role.PLAYER);
        entity.setActive(true);
        String encodedPassword = passwordEncoder.encode(dtoForSave.password());
        entity.setPassword(encodedPassword);
        entity.setEmail(dtoForSave.email().trim());
        User created = userRepository.save(entity);
        return userMapper.userToUserDto(created);
    }

    @Override
    public UserDto update(UserDtoForUpdate user) {
        Optional<User> existing = userRepository.findByEmailActive(user.email());
        if (existing.isPresent() && !existing.get().getId().equals(user.id())) {
            throw new UserValidationException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), user.email()));
        }
        User newUser = userMapper.userDtoForUpdateToUser(user);
        User updated = userRepository.save(newUser);
        return userMapper.userToUserDto(updated);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        if (!user.isActive()) {
            throw new UserNotFoundException(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND));
        }
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void registerUser(UserDtoForSave dtoForSave) {
        validation(dtoForSave);
        User entity = userMapper.userDtoForSaveToUser(dtoForSave);
        String encodedPassword = passwordEncoder.encode(dtoForSave.password());
        entity.setPassword(encodedPassword);
        entity.setEmail(dtoForSave.email().trim());
        entity.setNickName(dtoForSave.nickName());
        if (entity.getRole() == null) {
            entity.setRole(User.Role.PLAYER);
        }
        entity.setActive(false);
        User created = userRepository.save(entity);
        String token = tokenLinkService.generateToken(REGISTER_TOKEN_ACTIVITY_SECONDS);
        mailService.sendEmail(created.getEmail(), UserInternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT),
                String.format(UserInternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN), host, contextPath, token, created.getId()));
    }

    private void validation(UserDtoForSave dtoForSave) {
        String email = dtoForSave.email();
        String password = dtoForSave.password();
        String nickname = dtoForSave.nickName();
        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new UserNotFoundException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), email));
        }
        if (nickname == null || nickname.isEmpty()) {
            throw new UserValidationException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(NICKNAME_NOT_CORRECT), nickname));
        }
        if (password == null || password.isEmpty()) {
            throw new UserValidationException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(PASSWORD_NOT_CORRECT), password));
        }
        if (password.length() < 8 || password.length() > 24) {
            throw new UserValidationException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(PASSWORD_NOT_CORRECT), password));
        }
        for (int i = 0; i < email.length(); i++) {
            char ch = email.charAt(i);
            if (!CharUtils.isAscii(ch)) {
                throw new UserValidationException(String.format(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(EMAIL_NOT_CORRECT), email));
            }
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$";
        String passwordRegex = "^[a-zA-Z0-9!@#$%^&*()-_+=~`{}\\[\\]|\\\\:;'<>,.?\\/]{8,24}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(email);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (!matcher.matches()) {
            throw new UserValidationException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(EMAIL_NOT_CORRECT), email));
        }
        if (!passwordMatcher.matches()) {
            throw new UserValidationException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(PASSWORD_NOT_CORRECT), password));
        }
    }

    @Override
    public String loginUser(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserAppDetails userDetails = (UserAppDetails) authentication.getPrincipal();
        return jwtUtils.generateJwtCookie(userDetails).toString();
    }

    @Override
    public void verify(String username, String code) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
    }

    @Override
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void recoveryPassword(String email) {
        User existing = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        String token = tokenLinkService.generateToken(RECOVERY_TOKEN_ACTIVITY_SECONDS);
        mailService.sendEmail(email, UserInternalizationMessageManagerConfig
                        .getMessage(KET_FOR_EMAIL_RECOVERY_PASSWORD_SUBJECT),
                String.format(UserInternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_RECOVERY_PASS_LINK_PATTERN), host, contextPath, token, existing.getId()));
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UserValidationException(UserInternalizationMessageManagerConfig
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
            throw new UserNotFoundException(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(String.format(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        }
        return new UserAppDetails(userRepository.findByEmailActive(email)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_ACTIVATED))));
    }
}
