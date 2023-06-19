package com.pat.soe.repository.user;

import com.pat.soe.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Qualifier(value = "UserRepository")
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM users u WHERE LOWER(u.email) = LOWER(:email)", nativeQuery = true)
//fixMe for JQL
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users u WHERE LOWER(u.email) = LOWER(:email) AND u.is_active = true", nativeQuery = true)
//fixMe for JQL
    Optional<User> findByEmailActive(@Param("email") String email);

}
