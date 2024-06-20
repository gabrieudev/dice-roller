package com.api.diceRoller.model;

import com.api.diceRoller.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "tb_roles")
public class Role {

    @Id
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

}
