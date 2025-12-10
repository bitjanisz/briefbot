package com.admeliora.briefbot.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    // Stores bcrypt hash (typically 60 chars)
    @Column(nullable = false, length = 60)
    private String password;
}