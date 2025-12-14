package com.admeliora.briefbot.infrastructure.adapter.out.persistence.user;

import com.admeliora.briefbot.infrastructure.adapter.out.persistence.user.jpa.UserRepositoryJpa;
import com.admeliora.briefbot.user.model.User;
import com.admeliora.briefbot.user.port.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort {

    private final UserRepositoryJpa userRepository;

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<User> findByOidcSub(String sub) {
        return userRepository.findByOidcSub(sub);
    }

    @Override
    public User getReferenceById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}

