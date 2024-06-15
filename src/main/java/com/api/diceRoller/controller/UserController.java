package com.api.diceRoller.controller;

import com.api.diceRoller.dto.UserDTO;
import com.api.diceRoller.service.TokenService;
import com.api.diceRoller.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(pageable).getContent());
    }

    @PostMapping("/check/{userId}/{verificationId}")
    public ResponseEntity<Void> check(
            @PathVariable("userId") UUID userId,
            @PathVariable("verificationId") UUID verificationId
    ) {
        userService.check(userId, verificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
