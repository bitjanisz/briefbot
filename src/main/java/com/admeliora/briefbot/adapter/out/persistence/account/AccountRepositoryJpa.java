package com.admeliora.briefbot.adapter.out.persistence.account;

import com.admeliora.briefbot.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepositoryJpa extends JpaRepository<Account, Long> {
}

