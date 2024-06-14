package com.api.diceRoller.repository;

import com.api.diceRoller.model.Preset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresetRepository extends JpaRepository<Preset, Long> {
}
