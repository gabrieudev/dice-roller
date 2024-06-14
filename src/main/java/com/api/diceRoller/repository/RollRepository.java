package com.api.diceRoller.repository;

import com.api.diceRoller.model.Roll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollRepository extends JpaRepository<Roll, Long> {
}
