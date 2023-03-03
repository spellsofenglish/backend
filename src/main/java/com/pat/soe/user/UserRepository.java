package com.pat.soe.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users u WHERE LOWER(u.email) = LOWER(:email)", nativeQuery = true)//fixMe for JQL
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users u WHERE LOWER(u.email) = LOWER(:email) AND u.is_active = true", nativeQuery = true)//fixMe for JQL
    Optional<User> findByEmailActive(@Param("email") String email);

}
