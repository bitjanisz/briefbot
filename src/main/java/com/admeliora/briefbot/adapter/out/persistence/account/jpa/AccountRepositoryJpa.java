package com.admeliora.briefbot.adapter.out.persistence.account.jpa;

import com.admeliora.briefbot.application.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepositoryJpa extends JpaRepository<Account, Long> {
}

