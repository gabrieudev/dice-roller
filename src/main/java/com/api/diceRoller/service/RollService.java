package com.api.diceRoller.service;

import com.api.diceRoller.dto.ResultDTO;
import com.api.diceRoller.dto.RollDTO;
import com.api.diceRoller.exception.EntityNotFoundException;
import com.api.diceRoller.model.Roll;
import com.api.diceRoller.repository.RollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

@Service
public class RollService {

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private MappingService mappingService;

    /**
     * generates the result of roll
     *
     * @param rollDTO the roll's DTO
     * @return the result
     */
    public int generateResult(RollDTO rollDTO) {
        Random random = new Random();
        int result = 0;
        // array used to store roll results with advantage or disadvantage
        int[] results = new int[2];
        if (rollDTO.isAdvantage() && !rollDTO.isDisadvantage()) {
            for (int i = 0 ; i < rollDTO.getQuantity() ; i++) {
                results[0] = random.nextInt(rollDTO.getDiceType().getSides()) + 1;
                results[1] = random.nextInt(rollDTO.getDiceType().getSides()) + 1;
                result += Arrays.stream(results).max().getAsInt();
            }
        } else if (rollDTO.isDisadvantage() && !rollDTO.isAdvantage()) {
            for (int i = 0 ; i < rollDTO.getQuantity() ; i++) {
                results[0] = random.nextInt(rollDTO.getDiceType().getSides()) + 1;
                results[1] = random.nextInt(rollDTO.getDiceType().getSides()) + 1;
                result += Arrays.stream(results).min().getAsInt();
            }
        } else {
            for (int i = 0 ; i < rollDTO.getQuantity() ; i++) {
                result += random.nextInt(rollDTO.getDiceType().getSides()) + 1;
            }
        }
        return result + rollDTO.getModifier();
    }

    /**
     * Retrieves a roll by its id
     *
     * @param id the roll's id
     * @return the roll's DTO
     */
    public RollDTO getById(Long id) {
        Roll roll = rollRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Roll not found with this id: " + id)
        );
        return mappingService.toDto(roll);
    }

    /**
     * Saves a roll
     *
     * @param rollDTO the roll's DTO
     */
    public ResultDTO save(RollDTO rollDTO) {
        int result = generateResult(rollDTO);
        rollDTO.setResult(result);
        rollDTO.setTimestamp(Instant.now());
        Roll roll = mappingService.toModel(rollDTO);
        rollRepository.save(roll);
        return new ResultDTO(result);
    }

    /**
     * rerolls an existing roll
     *
     * @param rollId the roll's id
     * @return the result
     * @throws EntityNotFoundException if roll is not found
     */
    public ResultDTO reroll(Long rollId) {
        Roll roll = rollRepository.findById(rollId).orElseThrow(
                () -> new EntityNotFoundException("Roll not found with this id: " + rollId)
        );
        roll.setId(null);
        int result = generateResult(mappingService.toDto(roll));
        roll.setResult(result);
        roll.setTimestamp(Instant.now());
        rollRepository.save(roll);
        return new ResultDTO(result);
    }

    /**
     * Updates a roll
     *
     * @param id the roll's id
     * @param rollDTO the roll's DTO
     * @return the updated roll's DTO
     * @throws EntityNotFoundException if id is not found
     */
    public RollDTO update(Long id, RollDTO rollDTO) {
        Roll roll = rollRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Roll not found with this id: " + id)
        );
        mappingService.toModel(rollDTO, roll);
        Roll updatedRoll = rollRepository.save(roll);
        return mappingService.toDto(updatedRoll);
    }

    /**
     * Deletes a roll by its id
     *
     * @param id the roll's id
     * @throws EntityNotFoundException if id is not found
     */
    public void delete(Long id) {
        Roll roll = rollRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Roll not found with this id: " + id)
        );
        rollRepository.delete(roll);
    }

    /**
     * Retrieves the rolls
     *
     * @return the Page of rolls
     */
    public Page<RollDTO> getAll(Pageable pageable) {
        return rollRepository.findAll(pageable).map(
                roll -> mappingService.toDto(roll)
        );
    }

}
