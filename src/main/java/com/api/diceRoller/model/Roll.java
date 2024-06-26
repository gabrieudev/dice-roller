package com.api.diceRoller.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "tb_rolls")
public class Roll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int sides;

    @Column(nullable = false)
    private int modifier;

    @Column(nullable = false)
    private boolean advantage;

    @Column(nullable = false)
    private boolean disadvantage;

    @Column(nullable = false)
    private int result;

    @Column(nullable = false)
    private Instant timestamp;

}
