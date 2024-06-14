package com.api.diceRoller.controller;

import com.api.diceRoller.dto.PresetDTO;
import com.api.diceRoller.dto.ResultDTO;
import com.api.diceRoller.service.PresetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/presets")
public class PresetController {

    @Autowired
    private PresetService presetService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<Void> save(@Valid @RequestBody PresetDTO presetDTO) {
        presetService.save(presetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<PresetDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(presetService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<PresetDTO>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(presetService.getAll(pageable).getContent());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<PresetDTO> update(@PathVariable("id") Long id, @Valid @RequestBody PresetDTO presetDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(presetService.update(id, presetDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        presetService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/roll/{presetId}")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<ResultDTO> getResult(@PathVariable("presetId") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(presetService.generateResult(id));
    }

}

