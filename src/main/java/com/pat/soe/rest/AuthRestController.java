package com.pat.soe.rest;

import com.pat.soe.security.JwtUtils;
import com.pat.soe.security.TotpManager;
import com.pat.soe.token.TokenLinkService;
import com.pat.soe.user.UserServiceFacade;
import com.pat.soe.user.UserDtoForAuth;
import com.pat.soe.user.UserDtoForResponse;
import com.pat.soe.user.UserDtoForSave;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/v1.0/auth")
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
    private final UserServiceFacade userService;
    private final JwtUtils jwtUtils;
    private final TokenLinkService tokenLinkService;

    private final TotpManager totpManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDtoForAuth dtoForAuth) {
        String cooke = userService.loginUser(dtoForAuth.getEmail(), dtoForAuth.getPassword(), dtoForAuth.getSecret());
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
    public ResponseEntity<?> performRegistration(@RequestBody UserDtoForSave user, Model model) {
        userService.registerUser(user);
        return new ResponseEntity<>(CONFIRMATION_MESSAGE, HttpStatus.OK);
    }

    @PostMapping("/registration2fa")
    public ResponseEntity<?> performRegistration2fa(@RequestBody UserDtoForSave user, Model model) {
        String image = userService.registerUser(user);
        return ResponseEntity.ok().body(image);
    }


//    @GetMapping("/registrationGoogle")
//    public ResponseEntity<?> performRegistrationGoogle(OAuth2User auth2User) {
//        oAuth2AuthenticationToken.getPrincipal().getAttributes();
//        return ;
//    }

    @GetMapping("/activate/{token}/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable String token, @PathVariable Long userId) {
        tokenLinkService.activate(token);
        userService.activateUser(userId);
        return new ResponseEntity<>(USER_REGISTERED_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/recoveryPass")
    public ResponseEntity<?> performRecoveryPass(@RequestParam String email, Model model) {
        userService.recoveryPassword(email);
        return new ResponseEntity<>(USER_RECOVERY_SEND_TO_EMAIL, HttpStatus.OK);
    }

    @GetMapping("/recoveryPass/{token}/{userId}")
    public ResponseEntity<?> recoveryPass(Model model, @PathVariable String token, @PathVariable Long userId) {
        tokenLinkService.activate(token);
        return new ResponseEntity<>(USER_RECOVERY_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam Long userId, @RequestParam String newPassword) {
        userService.changePassword(userId, newPassword);
        return new ResponseEntity<>(USER_UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }

    @PostMapping("/updatePassword/{userId}")
    public ResponseEntity<?> updatePassword(@PathVariable Long userId,
                                            @RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        userService.updatePassword(userId, oldPassword, newPassword);
        return new ResponseEntity<>(USER_UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }
}