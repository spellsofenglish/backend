package com.pat.SpellsOfEnglish.controllers.rest;

import com.pat.SpellsOfEnglish.service.TokenLinkService;
import com.pat.SpellsOfEnglish.service.UserService;
import com.pat.SpellsOfEnglish.service.dto.user.UserDtoForSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1.0/auth")
@RequiredArgsConstructor
@RestController
public class AuthRestController {
    private static final String CONFIRMATION_MESSAGE = "An email has been sent to your email address with a confirmation link.";
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
    private static final String USER_RECOVERY_SUCCESSFULLY = "User recovery password successfully";
    private static final String USER_RECOVERY_SEND_TO_EMAIL = "Token send to Email";
    private static final String USER_UPDATE_SUCCESSFULLY = "User update password successfully";
    private final UserService userService;
    private final TokenLinkService tokenLinkService;

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@ModelAttribute UserDtoForSave user, Model model) {
        userService.registerUser(user);
        model.addAttribute("message", CONFIRMATION_MESSAGE);
        return new ResponseEntity<>(USER_REGISTERED_SUCCESSFULLY, HttpStatus.OK);
    }

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
    public ResponseEntity<?> updatePassword( @PathVariable Long userId,
                                             @RequestParam String oldPassword,
                                             @RequestParam String newPassword) {
        userService.updatePassword(userId, oldPassword, newPassword);
        return new ResponseEntity<>(USER_UPDATE_SUCCESSFULLY, HttpStatus.OK);
    }
}