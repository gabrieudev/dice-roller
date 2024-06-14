package com.api.dice_roller.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "tb_checker_users")
public class CheckerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
