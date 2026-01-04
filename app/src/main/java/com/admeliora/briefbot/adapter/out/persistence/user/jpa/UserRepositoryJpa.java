package com.admeliora.briefbot.adapter.out.persistence.user.jpa;

import com.admeliora.briefbot.application.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Long> {
    Optional<User> findByOidcSub(String oidcSub);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
