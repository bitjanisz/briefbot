package com.admeliora.briefbot.account.repository;

import com.admeliora.briefbot.account.domain.UserAccount;
import com.admeliora.briefbot.account.domain.UserAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount, UserAccountId> {
    List<UserAccount> findByUser_Id(Long userId);
    List<UserAccount> findByAccount_Id(Long accountId);
}