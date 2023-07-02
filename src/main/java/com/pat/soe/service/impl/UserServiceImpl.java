package com.pat.soe.service.impl;

import com.pat.soe.dto.UserDtoForResponse;
import com.pat.soe.dto.UserDtoForSave;
import com.pat.soe.entity.SecurityUser;
import com.pat.soe.entity.User;
import com.pat.soe.exception.ExceptionLocations;
import com.pat.soe.exception.UserCustomException;
import com.pat.soe.mapper.UserMapper;
import com.pat.soe.message.UserInternalizationMessageManagerConfig;
import com.pat.soe.repository.UserRepository;
import com.pat.soe.security.JwtUtils;
import com.pat.soe.service.api.MailService;
import com.pat.soe.service.api.TokenLinkService;
import com.pat.soe.service.api.UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    public static final String EMAIL_NOT_CORRECT = "UserService.NotCorrectEmail";
    public static final String PASSWORD_NOT_CORRECT = "UserService.NotCorrectPassword";
    public static final String NICKNAME_NOT_CORRECT = "UserService.NotCorrectNickname";
    public static final String THE_LENGTH_OF_THE_NAME = "The length of the name must be from 2 to 255 characters";
    public static final String KET_FOR_EMAIL_RECOVERY_PASSWORD_SUBJECT = "UserService.EmailRecoveryPasswordSubject";
    private static final int REGISTER_TOKEN_ACTIVITY_SECONDS = 60 * 60;
    private static final int RECOVERY_TOKEN_ACTIVITY_SECONDS = 5 * 60;
    private static final int COOKIE_TOKEN_ACTIVITY_SECONDS = 24 * 60 * 60;
    public static final String KEY_FOR_EXCEPTION_USER_NOT_FOUND = "UserService.UserNotFound";
    public static final String KEY_FOR_EXCEPTION_EXISTING_EMAIL = "UserService.ExistingEmail";
    public static final String KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN = "UserService.ActivateLinkPattern";
    public static final String KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT = "UserService.UserConfirmationSubject";
    public static final String KEY_FOR_RECOVERY_PASS_LINK_PATTERN = "UserService.RecoveryPassLinkPattern";
    public static final String KEY_FOR_EXCEPTION_WRONG_OLD_PASSWORD = "UserService.WrongOldPassword";
    public static final String KEY_FOR_EXCEPTION_USER_NOT_ACTIVATED = "UserService.UserNotActivated";
    public static final int LOGIN_TOKEN_ACTIVITY_SECONDS = 24 * 60 * 60;

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
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           TokenLinkService tokenLinkService,
                           MailService mailService,
                           @Lazy AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenLinkService = tokenLinkService;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                        ExceptionLocations.USER_SERVICE_NOT_FOUND));
    }

    @Override
    public UserDtoForResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                        ExceptionLocations.USER_SERVICE_NOT_FOUND));
        return userMapper.userDtoToUserDtoForResponse(user);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User create(UserDtoForSave dtoForSave) {
        User entity = userMapper.userDtoForSaveToUser(dtoForSave);
        entity.setRole(User.Role.PLAYER);
        entity.setActive(true);
        String encodedPassword = passwordEncoder.encode(dtoForSave.password());
        entity.setPassword(encodedPassword);
        entity.setEmail(dtoForSave.email().trim());
        return userRepository.save(entity);
    }

    @Override
    public User update(User user) {
        Optional<User> existing = userRepository.findByEmailActive(user.getEmail());
        if (existing.isPresent() && !existing.get().getId().equals(user.getId())) {
            throw new UserCustomException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), user.getEmail()),
                    ExceptionLocations.USER_SERVICE_VALIDATION);
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                        ExceptionLocations.USER_SERVICE_NOT_FOUND));
        if (!user.isActive()) {
            throw new UserCustomException(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                    ExceptionLocations.USER_SERVICE_NOT_FOUND);
        }
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void registerUser(UserDtoForSave dtoForSave) {
        Optional<User> existing = userRepository.findByEmail(dtoForSave.email());
        if (existing.isPresent()) {
            throw new UserCustomException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), dtoForSave.email()),
                    ExceptionLocations.USER_SERVICE_VALIDATION);
        }
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
        String token = tokenLinkService.generateToken(REGISTER_TOKEN_ACTIVITY_SECONDS, created.getNickName());
        mailService.sendEmail(created.getEmail(), UserInternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT),
                String.format(UserInternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN), host, contextPath, token, created.getId()));
    }

    @Override
    public Cookie loginUser(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = tokenLinkService.generateToken(authentication, LOGIN_TOKEN_ACTIVITY_SECONDS);
        return jwtUtils.createCookieWithJwt(token, COOKIE_TOKEN_ACTIVITY_SECONDS);
    }

    @Override
    public void verify(String username, String code) {
        userRepository.findByEmail(username)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                        ExceptionLocations.USER_SERVICE_NOT_FOUND));
    }

    @Override
    public void activateUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                        ExceptionLocations.USER_SERVICE_NOT_FOUND));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void recoveryPassword(String email) {
        User existing = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                        ExceptionLocations.USER_SERVICE_NOT_FOUND));
        String token = tokenLinkService.generateToken(RECOVERY_TOKEN_ACTIVITY_SECONDS, existing.getNickName());
        mailService.sendEmail(email, UserInternalizationMessageManagerConfig
                        .getMessage(KET_FOR_EMAIL_RECOVERY_PASSWORD_SUBJECT),
                String.format(UserInternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_RECOVERY_PASS_LINK_PATTERN), host, contextPath, token, existing.getId()));
    }

    @Override
    public void changePassword(UUID userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                        ExceptionLocations.USER_SERVICE_NOT_FOUND));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(UUID userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND),
                        ExceptionLocations.USER_SERVICE_NOT_FOUND));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UserCustomException(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_WRONG_OLD_PASSWORD),
                    ExceptionLocations.USER_SERVICE_VALIDATION);
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailActive(email)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UserCustomException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_ACTIVATED),
                        ExceptionLocations.USER_SERVICE_VALIDATION));
    }
}
