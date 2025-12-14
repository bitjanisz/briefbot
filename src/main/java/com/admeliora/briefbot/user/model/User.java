package com.admeliora.briefbot.user.model;

import com.admeliora.briefbot.account.model.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oidc_sub", nullable = false)
    private String oidcSub;

    @Column(nullable = false)
    private String givenName;

    @Column(nullable = false)
    private String familyName;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column(name = "last_login", nullable = false)
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAccount> userAccounts = new HashSet<>();
}

