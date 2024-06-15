package com.api.diceRoller.controller;

import com.api.diceRoller.dto.ResultDTO;
import com.api.diceRoller.dto.RollDTO;
import com.api.diceRoller.service.RollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/rolls")
public class RollController {

    @Autowired
    private RollService rollService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<ResultDTO> save(@Valid @RequestBody RollDTO rollDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(rollService.save(rollDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<RollDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(rollService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<RollDTO>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rollService.getAll(pageable).getContent());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<RollDTO> update(@PathVariable("id") Long id, @Valid @RequestBody RollDTO rollDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(rollService.update(id, rollDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        rollService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/history/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<List<RollDTO>> getHistory(@PathVariable("userId")UUID userId, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(rollService.getHistory(userId, pageable).getContent());
    }

}

