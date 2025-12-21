package com.admeliora.briefbot.application.user.port.out;

import com.admeliora.briefbot.application.user.model.User;

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
