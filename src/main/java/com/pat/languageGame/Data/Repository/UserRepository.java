package com.pat.languageGame.Data.Repository;

import com.pat.languageGame.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
