package com.admeliora.briefbot.adapter.out.persistence.account.jpa;

import com.admeliora.briefbot.application.account.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepositoryJpa extends JpaRepository<UserAccount, Long> {
    List<UserAccount> findByUserId(Long userId);

    List<UserAccount> findByAccountId(Long accountId);

    Optional<UserAccount> findByAccountIdAndUserId(Long accountId, Long userId);

    @Query(value = """
            SELECT ua.* 
            FROM user_accounts ua
            INNER JOIN users u ON ua.user_id = u.id
            WHERE u.email = :email
            """, nativeQuery = true)
    List<UserAccount> findByUserEmail(@Param("email") String email);

    boolean existsByUserIdAndAccountId(Long userId, Long accountId);
}
