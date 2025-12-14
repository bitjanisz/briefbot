package com.admeliora.briefbot.account.domain;

import com.admeliora.briefbot.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_accounts")
public class UserAccount {

    @EmbeddedId
    private UserAccountId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("accountId")
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountRole role;

    public UserAccount(User user, Account account, AccountRole role) {
        this.user = user;
        this.account = account;
        this.role = role;
        this.id = new UserAccountId(user.getId(), account.getId());
    }
}