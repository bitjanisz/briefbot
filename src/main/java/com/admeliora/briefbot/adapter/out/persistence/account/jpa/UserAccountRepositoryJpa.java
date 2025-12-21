package com.admeliora.briefbot.adapter.out.persistence.account.jpa;

import com.admeliora.briefbot.application.account.model.UserAccount;
import com.admeliora.briefbot.application.account.model.UserAccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepositoryJpa extends JpaRepository<UserAccount, UserAccountId> {
    List<UserAccount> findByUser_Id(Long userId);

    List<UserAccount> findByAccount_Id(Long accountId);
}

