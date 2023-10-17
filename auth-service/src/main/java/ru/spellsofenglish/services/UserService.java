package ru.spellsofenglish.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spellsofenglish.exceptions.UserExistsException;
import ru.spellsofenglish.models.User;
import ru.spellsofenglish.repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public String createNewUser(User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new UserExistsException("The user with the same name already exists","The user already exists");
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "User added to the system";
        }
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
