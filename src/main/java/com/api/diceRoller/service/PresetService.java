package com.api.diceRoller.service;

import com.api.diceRoller.dto.PresetDTO;
import com.api.diceRoller.dto.RollDTO;
import com.api.diceRoller.exception.EntityNotFoundException;
import com.api.diceRoller.model.Preset;
import com.api.diceRoller.repository.PresetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PresetService {

    @Autowired
    private PresetRepository presetRepository;

    @Autowired
    private RollService rollService;

    @Autowired
    private MappingService mappingService;

    /**
     * Retrieves a preset by its id
     *
     * @param id the preset's id
     * @return the preset's DTO
     */
    public PresetDTO getById(Long id) {
        Preset preset = presetRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Preset not found with this id: " + id)
        );
        return mappingService.toDto(preset);
    }

    /**
     * Saves a preset
     *
     * @param presetDTO the preset's DTO
     */
    public void save(PresetDTO presetDTO) {
        Preset preset = mappingService.toModel(presetDTO);
        presetRepository.save(preset);
    }

    /**
     * Updates a preset
     *
     * @param id the preset's id
     * @param presetDTO the preset's DTO
     * @return the updated preset's DTO
     * @throws EntityNotFoundException if id is not found
     */
    public PresetDTO update(Long id, PresetDTO presetDTO) {
        Preset preset = presetRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Preset not found with this id: " + id)
        );
        mappingService.toModel(presetDTO, preset);
        Preset updatedPreset = presetRepository.save(preset);
        return mappingService.toDto(updatedPreset);
    }

    /**
     * Deletes a preset by its id
     *
     * @param id the preset's id
     * @throws EntityNotFoundException if id is not found
     */
    public void delete(Long id) {
        Preset preset = presetRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Preset not found with this id: " + id)
        );
        presetRepository.delete(preset);
    }

    /**
     * generates the result of preset's roll
     *
     * @param presetId the preset's id
     * @return the result
     */
    public int generateResult(Long presetId) {
        Preset preset = presetRepository.findById(presetId).orElseThrow(
                () -> new EntityNotFoundException("Preset not found")
        );
        RollDTO rollDTO = mappingService.toDto(preset.getRoll());
        return rollService.generateResult(rollDTO);
    }

    /**
     * Retrieves the presets
     *
     * @return the Page of presets
     */
    public Page<PresetDTO> getAll(Pageable pageable) {
        return presetRepository.findAll(pageable).map(
                preset -> mappingService.toDto(preset)
        );
    }

}
