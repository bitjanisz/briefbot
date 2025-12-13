package com.admeliora.briefbot.user.repository;

import com.admeliora.briefbot.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOidcSub(String oidcSub);
}