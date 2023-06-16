package com.pat.soe.user;

import com.pat.soe.dto.user.UserDtoForSave;
import com.pat.soe.entity.User;
import com.pat.soe.exception.UserValidationException;
import com.pat.soe.mapper.user.UserMapper;
import com.pat.soe.message.user.UserInternalizationMessageManagerConfig;
import com.pat.soe.repository.user.UserRepository;
import com.pat.soe.service.mail.MailService;
import com.pat.soe.service.token.TokenLinkService;
import com.pat.soe.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenLinkService tokenLinkService;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserServiceImpl userService;

    @Value("${app.host}")
    private String host;
    @Value("${server.servlet.contextPath}")
    private String contextPath;

    public static final String KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN = "UserService.ActivateLinkPattern";
    public static final String KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT = "UserService.UserConfirmationSubject";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
//
//    @BeforeAll
//    static void beforeAll() {
//
//    }

    @Test
    void getById() {
    }

    @Test
    void getByEmail() {
    }

    @Test
    void getAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void registerUserValidDtoForSave() {
        UserDtoForSave dtoForSave = new UserDtoForSave("valid_test_mail@valid.test",
                "password123",
                "JUnitTest");

        String encodedPassword = "encodedPassword";

        User entity = new User();
        entity.setEmail(dtoForSave.email().trim());
        entity.setPassword(encodedPassword);
        entity.setNickName("JUnitTest");
        entity.setRole(User.Role.PLAYER);
        entity.setActive(false);

        User createdEntity = new User();
        createdEntity.setId(1L);
        createdEntity.setEmail(dtoForSave.email().trim());
        entity.setPassword(encodedPassword);
        createdEntity.setNickName("JUnitTest");
        createdEntity.setRole(User.Role.PLAYER);
        createdEntity.setActive(false);

        String token = "token";

        when(userMapper.userDtoForSaveToUser(dtoForSave)).thenReturn(entity);
        when(passwordEncoder.encode(java.nio.CharBuffer.wrap(dtoForSave.password()))).thenReturn(encodedPassword);
        when(userRepository.save(entity)).thenReturn(createdEntity);
        when(tokenLinkService.generateToken(ArgumentMatchers.anyInt())).thenReturn(token);

        userService.registerUser(dtoForSave);


        verify(mailService).sendEmail(createdEntity.getEmail(), UserInternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_EMAIL_USER_CONFIRMATION_SUBJECT),
                String.format(UserInternalizationMessageManagerConfig
                        .getMessage(KEY_FOR_EXCEPTION_ACTIVATE_LINK_PATTERN), host, contextPath, token, createdEntity.getId()));

        assertAll("Method calls",
                () -> assertEquals(entity, userMapper.userDtoForSaveToUser(dtoForSave)),
                () -> assertEquals(encodedPassword, passwordEncoder.encode(java.nio.CharBuffer.wrap(dtoForSave.password()))),
                () -> assertEquals(createdEntity, userRepository.save(entity)));
    }


    @Test
    void registerUserNotValidDtoForSaveEmailNotA() {
        UserDtoForSave dtoForSave = new UserDtoForSave("not_valid_test_mailnot_valid.test",
                "password123",
                "JUnitTest");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidDtoForSaveEmailNotPoint() {
        UserDtoForSave dtoForSave = new UserDtoForSave("not_valid_test_mail@not_validtest",
                "password123",
                "JUnitTest");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidDtoForSaveEmailCyrillic() {
        UserDtoForSave dtoForSave = new UserDtoForSave("тест_кирилицы@not_valid.test",
                "password123",
                "JUnitTest");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidDtoForSaveEmailWithSpecialSymbols() {
        UserDtoForSave dtoForSave = new UserDtoForSave("\\#@not_valid.test",
                "password123",
                "JUnitTest");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidPassword() {
        UserDtoForSave dtoForSave = new UserDtoForSave("testemail@gmail.com",
                "pas",
                "JUnitTest");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidPasswordIsNull() {
        UserDtoForSave dtoForSave = new UserDtoForSave("testemail@gmail.com",
                null,
                "JUnitTest");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidPasswordIsEmpty() {
        UserDtoForSave dtoForSave = new UserDtoForSave("testemail@gmail.com",
                " ",
                "JUnitTest");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidPasswordCyrillic() {
        UserDtoForSave dtoForSave = new UserDtoForSave("testemail@gmail.com",
                "пароль12345",
                "JUnitTest");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidNicknameIsEmpty() {
        UserDtoForSave dtoForSave = new UserDtoForSave("testemail@gmail.com",
                "password123",
                "");

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void registerUserNotValidNicknameIsNull() {
        UserDtoForSave dtoForSave = new UserDtoForSave("testemail@gmail.com",
                "password123",
                null);

        assertThrows(UserValidationException.class, () -> userService.registerUser(dtoForSave));
    }

    @Test
    void loginUser() {
    }

    @Test
    void activateUser() {
    }

    @Test
    void recoveryPassword() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void loadUserByUsername() {
    }
}