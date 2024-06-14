package com.api.dice_roller.model;

import com.api.dice_roller.model.enums.DiceType;
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

    @Column(name = "dice_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiceType diceType;

    @Column(nullable = false)
    private int modifier;

    @Column(nullable = false)
    private boolean advantage;

    @Column(nullable = false)
    private boolean disadvantage;

    @Column(nullable = false)
    private Instant timestamp;

}
