package com.api.diceRoller.service;

import com.api.diceRoller.dto.ResultDTO;
import com.api.diceRoller.dto.RollDTO;
import com.api.diceRoller.exception.EntityNotFoundException;
import com.api.diceRoller.model.Roll;
import com.api.diceRoller.model.User;
import com.api.diceRoller.repository.RollRepository;
import com.api.diceRoller.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class RollService {

    @Autowired
    private RollRepository rollRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MappingService mappingService;

    /**
     * generates the result of roll
     *
     * @param rollDTO the roll's DTO
     * @return the result
     */
    public List<Integer> generateResult(RollDTO rollDTO) {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        // array used to store roll results with advantage or disadvantage
        int[] results = new int[2];
        if (rollDTO.isAdvantage() && !rollDTO.isDisadvantage()) {
            for (int i = 0 ; i < rollDTO.getQuantity() ; i++) {
                results[0] = random.nextInt(rollDTO.getSides()) + 1;
                results[1] = random.nextInt(rollDTO.getSides()) + 1;
                result.add(Arrays.stream(results).max().getAsInt());
            }
        } else if (rollDTO.isDisadvantage() && !rollDTO.isAdvantage()) {
            for (int i = 0 ; i < rollDTO.getQuantity() ; i++) {
                results[0] = random.nextInt(rollDTO.getSides()) + 1;
                results[1] = random.nextInt(rollDTO.getSides()) + 1;
                result.add(Arrays.stream(results).min().getAsInt());
            }
        } else {
            for (int i = 0 ; i < rollDTO.getQuantity() ; i++) {
                result.add(random.nextInt(rollDTO.getSides()) + 1);
            }
        }
        return result;
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
        List<Integer> result = generateResult(rollDTO);
        int total = result.stream().mapToInt(
                Integer::intValue
        ).sum() + rollDTO.getModifier();
        rollDTO.setResult(total);
        rollDTO.setTimestamp(Instant.now());
        Roll roll = mappingService.toModel(rollDTO);
        rollRepository.save(roll);
        return new ResultDTO(result, roll.getModifier(), total);
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
     * retrieves the history of user rolls
     *
     * @param userId the user's id
     * @return the Page of rolls
     */
    public Page<RollDTO> getHistory(UUID userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with this id: " + userId)
        );
        return rollRepository.findByUserOrderByTimestampDesc(user, pageable).map(
                roll -> mappingService.toDto(roll)
        );
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
