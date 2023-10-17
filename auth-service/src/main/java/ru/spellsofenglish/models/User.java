package ru.spellsofenglish.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.UUID;

@Entity
@Table(name= "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Username can't be empty")
    @NotNull(message="Username can't be null")
    @Size(min = 2, max=30, message = "The name should be 2 to 30 symbol length")
    private String username;

    @NotEmpty(message = "Email can't be empty")
    @NotNull(message="Email can't be null")
    @Email
    private String email;

    @NotEmpty(message = "Password can't be empty")
    @NotNull(message="Password can't be null")
    private String password;

}
