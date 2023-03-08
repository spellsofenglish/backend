package com.pat.soe.user;

import com.pat.soe.security.JwtUtils;
import com.pat.soe.security.TotpManager;
import com.pat.soe.mail.MailServiceFacade;
import com.pat.soe.token.TokenLinkService;
import com.pat.soe.user.exception.UserNotFoundException;
import com.pat.soe.user.exception.UserException;
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
public class UserService implements UserServiceFacade, UserDetailsService {
    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenLinkService tokenLinkService;
    private final MailServiceFacade mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TotpManager totpManager;

    @Value("${app.host}")
    private String host;

    public static final String USER_S_IS_NOT_FOUND = "User %s is not found";
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
        Optional<User> existing = userRepository.findByEmailActive(dtoForSave.getEmail());
        if (existing.isPresent()) {
            throw new UserException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), dtoForSave.getEmail()));
        }
        UserSecurity entitySecurity = new UserSecurity();
        entitySecurity.setRole(UserSecurity.Role.ROLE_PLAYER);
        entitySecurity.setActive(true);
        String encodedPassword = passwordEncoder.encode(dtoForSave.getPassword());
        entitySecurity.setPassword(encodedPassword);
//        User entity = userMapper.userDtoForSaveToUser(dtoForSave);
        User entity = new User();
        entity.setEmail(dtoForSave.getEmail().trim());
        userSecurityRepository.save(entitySecurity);
        User created = userRepository.save(entity);
        return userMapper.userToUserDto(created);
    }

    @Override
    public UserDto update(UserDtoForUpdate user) {
        Optional<User> existing = userRepository.findByEmailActive(user.getEmail());
        if (existing.isPresent() && !existing.get().getId().equals(user.getId())) {
            throw new UserException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), user.getEmail()));
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
        UserSecurity userSecurity = user.getUserSecurity();
        if (!userSecurity.isActive()) {
            throw new UserNotFoundException(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND));
        }
        userSecurity.setActive(false);
        user.setUserSecurity(userSecurity);
        userRepository.save(user);
    }

    @Override
    public String registerUser(UserDtoForSave dtoForSave) {
        Optional<User> existing = userRepository.findByEmail(dtoForSave.getEmail());
        if (existing.isPresent()) {
            throw new UserException(String.format(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_EXISTING_EMAIL), dtoForSave.getEmail()));
        }
        User entity = userMapper.userDtoForSaveToUser(dtoForSave);
        UserSecurity entitySecurity = new UserSecurity();
        String encodedPassword = passwordEncoder.encode(dtoForSave.getPassword());
        entitySecurity.setPassword(encodedPassword);
        entity.setEmail(dtoForSave.getEmail().trim());
        if (entitySecurity.getRole() == null) {
            entitySecurity.setRole(UserSecurity.Role.ROLE_PLAYER);
        }
        if (dtoForSave.isUsing2FA()) {
            entitySecurity.setSecret(tokenLinkService.generate2FAToken());
        }
        entitySecurity.setActive(false);
        UserSecurity createdSecurity = userSecurityRepository.save(entitySecurity);
        entity.setUserSecurity(createdSecurity);
        User created = userRepository.save(entity);
        String token = tokenLinkService.generateToken(REGISTER_TOKEN_ACTIVITY_SECONDS);
//        mailService.sendEmail(created.getEmail(), UserInternalizationMessageManagerConfig
//                        .getMessage(KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT),
//                String.format(UserInternalizationMessageManagerConfig
//                        .getMessage(KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN), host, token, created.getId()));
        return totpManager.getUriForImage(createdSecurity.getSecret());
    }

    @Override
    public String loginUser(String username, String password, String code) {
        Authentication authentication2 = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager
                .authenticate(authentication2);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserAppDetails userDetails = (UserAppDetails) authentication.getPrincipal();
        UserDtoForResponse dtoForResponse = getByEmail(userDetails.getUsername());
        if (dtoForResponse.isUsing2FA()) {
            verify(username, code);
        }
        return jwtUtils.generateJwtCookie(userDetails).toString();
    }

    @Override
    public void verify(String username, String code) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        UserSecurity userSecurity = user.getUserSecurity();
        if (!totpManager.verifyCode(code, userSecurity.getSecret())) {
            throw new UserException("Code is incorrect");
        }
    }

    @Override
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        UserSecurity userSecurity = user.getUserSecurity();
        userSecurity.setActive(true);
        userSecurityRepository.save(userSecurity);
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
                        .getMessage(KEY_FOR_RECOVERY_PASS_LINK_PATTERN), host, token, existing.getId()));
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        String encodedPassword = passwordEncoder.encode(newPassword);
        UserSecurity userSecurity = user.getUserSecurity();
        userSecurity.setPassword(encodedPassword);
        userSecurityRepository.save(userSecurity);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(UserInternalizationMessageManagerConfig
                        .getExceptionMessage(KEY_FOR_EXCEPTION_USER_NOT_FOUND)));
        UserSecurity userSecurity = user.getUserSecurity();
        if (!passwordEncoder.matches(oldPassword, userSecurity.getPassword())) {
            throw new UserException(UserInternalizationMessageManagerConfig
                    .getExceptionMessage(KEY_FOR_EXCEPTION_WRONG_OLD_PASSWORD));
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        userSecurity.setPassword(encodedPassword);
        userSecurityRepository.save(userSecurity);
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
