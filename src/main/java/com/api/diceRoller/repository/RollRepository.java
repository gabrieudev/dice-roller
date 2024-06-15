package com.api.diceRoller.repository;

import com.api.diceRoller.model.Roll;
import com.api.diceRoller.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollRepository extends JpaRepository<Roll, Long> {
    Page<Roll> findByUserOrderByTimestampDesc(User user, Pageable pageable);
}
