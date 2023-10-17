package ru.spellsofenglish.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.spellsofenglish.dto.AuthRequest;
import ru.spellsofenglish.models.User;
import ru.spellsofenglish.services.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody User user){
        return userService.createNewUser(user);
    }

    @GetMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        var authenticate=authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(authRequest.username(),authRequest.password()));
        if(authenticate.isAuthenticated()){
            return userService.generateToken(authRequest.username());
        }
        else{
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        userService.validateToken(token);
        return "Token is valid";
    }
}
