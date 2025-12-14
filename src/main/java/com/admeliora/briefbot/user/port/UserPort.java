package com.admeliora.briefbot.user.port;

import com.admeliora.briefbot.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    boolean existsById(Long id);

    Optional<User> findByOidcSub(String sub);

    User getReferenceById(Long id);

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);
}
