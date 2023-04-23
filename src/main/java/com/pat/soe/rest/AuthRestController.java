package com.pat.soe.rest;

import com.pat.soe.security.JwtUtils;
import com.pat.soe.token.TokenLinkService;
import com.pat.soe.user.UserDtoForChangePass;
import com.pat.soe.user.UserDtoForRecoveryPass;
import com.pat.soe.user.UserDtoForUpdatePass;
import com.pat.soe.user.UserService;
import com.pat.soe.user.UserDtoForAuth;
import com.pat.soe.user.UserDtoForResponse;
import com.pat.soe.user.UserDtoForSave;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("auth")
@RequiredArgsConstructor
@RestController
public class AuthRestController {
    private static final String CONFIRMATION_MESSAGE = "An email has been sent to your email address with a confirmation link.";
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
    private static final String USER_RECOVERY_SUCCESSFULLY = "User recovery password successfully";
    private static final String USER_RECOVERY_SEND_TO_EMAIL = "Token send to Email";
    private static final String USER_UPDATE_SUCCESSFULLY = "User update password successfully";
    public static final String USER_IS_NOT_AUTH = "User is not Auth";
    public static final String YOU_VE_BEEN_SIGNED_OUT = "You've been signed out!";
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final TokenLinkService tokenLinkService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDtoForAuth dtoForAuth) {
        String cooke = userService.loginUser(dtoForAuth.getEmail(), dtoForAuth.getPassword());
        UserDtoForResponse dtoForResponse = userService.getByEmail(dtoForAuth.getEmail());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cooke)
                .body(dtoForResponse);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(YOU_VE_BEEN_SIGNED_OUT);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody UserDtoForSave user) {
        userService.registerUser(user);
        return new ResponseEntity<>(CONFIRMATION_MESSAGE, HttpStatus.OK);
    }

    @GetMapping("/activate/{token}/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable String token, @PathVariable Long userId) {
        tokenLinkService.activate(token);
        userService.activateUser(userId);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://spells.hedgi.ru/auth")).build();
    }

    @PostMapping("/recoveryPass")
    public ResponseEntity<?> performRecoveryPass(@RequestBody UserDtoForRecoveryPass user) {
        userService.recoveryPassword(user.getEmail());
        return new ResponseEntity<>(USER_RECOVERY_SEND_TO_EMAIL, HttpStatus.OK);
    }

    @GetMapping("/recoveryPass/{token}/{userId}")
    public ResponseEntity<?> recoveryPass(Model model, @PathVariable String token, @PathVariable Long userId) {
        tokenLinkService.activate(token);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://spells.hedgi.ru/reset?" + userId)).build();
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody UserDtoForChangePass user) {
        userService.changePassword(user.getId(), user.getNewPassword());
        return new ResponseEntity<>(USER_UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UserDtoForUpdatePass user) {
        userService.updatePassword(user.getId(), user.getOldPassword(), user.getNewPassword());
        return new ResponseEntity<>(USER_UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }
}