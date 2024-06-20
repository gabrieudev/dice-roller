package com.api.diceRoller.repository;

import com.api.diceRoller.model.ConfirmationToken;
import com.api.diceRoller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {
    Optional<ConfirmationToken> findByUser(User user);
}
