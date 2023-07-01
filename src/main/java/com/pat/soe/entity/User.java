package com.pat.soe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;
import java.util.UUID;

import static com.pat.soe.service.impl.UserServiceImpl.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @NotBlank(message = EMAIL_NOT_CORRECT)
    @Length(min = 2, max = 255, message = THE_LENGTH_OF_THE_NAME)
    @Email(message = EMAIL_NOT_CORRECT)
    @Column(name = "email")
    private String email;

    @NotBlank(message = PASSWORD_NOT_CORRECT)
    @Size(min = 8)
    @Column(name = "password")
    private String password;

    @NotBlank(message = NICKNAME_NOT_CORRECT)
    @Length(min = 2, max = 255, message = THE_LENGTH_OF_THE_NAME)
    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "results")
    private String result;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_active")
    private boolean isActive;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", result='" + result + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive == user.isActive && Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(nickName, user.nickName) && Objects.equals(result, user.result) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nickName, result, role, isActive);
    }

    public enum Role {
        PLAYER, MANAGER, ADMINISTRATOR
    }
}
