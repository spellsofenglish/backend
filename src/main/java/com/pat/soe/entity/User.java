package com.pat.soe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private char[] password;

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
                ", password=" + Arrays.toString(password) +
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
        return isActive == user.isActive && Objects.equals(id, user.id) && Objects.equals(email, user.email) && Arrays.equals(password, user.password) && Objects.equals(nickName, user.nickName) && Objects.equals(result, user.result) && role == user.role;
    }

    @Override
    public int hashCode() {
        int result1 = Objects.hash(id, email, nickName, result, role, isActive);
        result1 = 31 * result1 + Arrays.hashCode(password);
        return result1;
    }

    public enum Role {
        PLAYER, MANAGER, ADMINISTRATOR
    }
}
