package com.admeliora.briefbot.account.repository;

import com.admeliora.briefbot.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> { }