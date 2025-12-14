package com.admeliora.briefbot.adapter.out.persistence.user;

import com.admeliora.briefbot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJpa extends JpaRepository<User, Long> {
    Optional<User> findByOidcSub(String oidcSub);
}

